/*
 * Copyright 2023 anominy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.anominy.kotbot.service;

import club.minnced.discord.webhook.receive.ReadonlyMessage;
import io.github.anominy.kotbot.entity.*;
import io.github.anominy.kotbot.service.api.*;
import io.github.anominy.kotbot.bean.LocalBean;
import io.github.anominy.kotbot.discord.util.UDiscordString;
import io.github.anominy.kotbot.dto.GuildReviewMessagePostInput;
import io.github.anominy.kotbot.dto.GuildWebhookCreateMessagePostInput;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletionException;

@Service(LocalBean.GUILD_REVIEW_SERVICE)
public final class GuildReviewService implements IGuildReviewService {
	private final IConfigService configService;
	private final IPersonService personService;
	private final IPersonInfoService personInfoService;
	private final IPostService postService;
	private final IGuildWebhookService guildWebhookService;
	private final IPublishWebhookService publishWebhookService;

	public GuildReviewService(
			@Qualifier(LocalBean.CONFIG_SERVICE)
			IConfigService configService,

			@Qualifier(LocalBean.PERSON_SERVICE)
			IPersonService personService,

			@Qualifier(LocalBean.PERSON_INFO_SERVICE)
			IPersonInfoService personInfoService,

			@Qualifier(LocalBean.POST_SERVICE)
			IPostService postService,

			@Qualifier(LocalBean.GUILD_WEBHOOK_SERVICE)
			IGuildWebhookService guildWebhookService,

			@Qualifier(LocalBean.PUBLISH_WEBHOOK_SERVICE)
			IPublishWebhookService publishWebhookService
	) {
		this.configService = configService;
		this.personService = personService;
		this.personInfoService = personInfoService;
		this.postService = postService;
		this.guildWebhookService = guildWebhookService;
		this.publishWebhookService = publishWebhookService;
	}

	@Override
	public String reviewGuildMessagePost(GuildReviewMessagePostInput input) {
		Guild guild = input.getGuild();
		if (guild == null) {
			return UDiscordString.CANT_REVIEW_POST_FROM_NULL_GUILD;
		}

		long guildId = guild.getIdLong();

		ConfigEntity config = this.configService.findWithWebhooksByGuildId(guildId);
		if (config == null) {
			return UDiscordString.GUILD_HAS_NO_CONFIG_FOR_SUGGEST_REVIEW_PUBLISH;
		}

		PublishWebhookEntity publishWebhook = config.getPublishWebhook();
		if (publishWebhook == null) {
			return UDiscordString.GUILD_HAS_NO_CONFIG_FOR_PUBLISH_WEBHOOK;
		}

		boolean isPublishWebhookEnabled = publishWebhook.getIsEnabled();
		if (!isPublishWebhookEnabled) {
			return UDiscordString.GUILD_HAS_DISABLED_PUBLISH_WEBHOOK;
		}

		TextChannel publishWebhookChannel = guild.getTextChannelById(publishWebhook.getChannelId());
		if (publishWebhookChannel == null) {
			return UDiscordString.GUILD_HAS_NO_CONFIG_FOR_PUBLISH_WEBHOOK;
		}

		long discordWebhookCount = 0;
		try {
			discordWebhookCount = publishWebhookChannel.retrieveWebhooks()
					.submit()
					.join()
					.stream()
					.filter(it -> it.getIdLong() == publishWebhook.getWebhookId())
					.count();
		} catch (CancellationException | CompletionException ignored) {
		}

		if (discordWebhookCount != 1) {
			return UDiscordString.GUILD_HAS_NO_CONFIG_FOR_PUBLISH_WEBHOOK;
		}

		long userId = input.getUser()
				.getIdLong();

		PersonEntity person = this.personService.findOrCreateIfNotExistsByUserId(
				userId);

		Long personId = person.getId();

		PersonInfoEntity personInfo = this.personInfoService.findWithRoleTypesOrCreateIfNotExistsByPersonIdAndGuildId(
				personId, guildId);

		Set<EPersonRole> roleTypes = personInfo.getRoleTypes();

		if (roleTypes == null || !roleTypes.contains(EPersonRole.REVIEWER)) {
			return UDiscordString.ONLY_REVIEWERS_CAN_REVIEW_POSTS;
		}

		long messageId = input.getMessage()
				.getIdLong();

		PostEntity post = this.postService.findWithAuthorsAndImagesByMessageId(messageId);
		boolean isReviewed = post == null || post.getIsReviewed();

		if (isReviewed) {
			return UDiscordString.CANT_REVIEW_NON_SUGGEST_POST;
		}

		JDA discordApi = input.getDiscordApi();

		boolean isApprove = input.getIsApprove();
		if (isApprove) {
			GuildWebhookCreateMessagePostInput postInput = new GuildWebhookCreateMessagePostInput.Builder()
					.setDiscordApi(discordApi)
					.setPost(post)
					.setWebhookService(this.publishWebhookService)
					.build();

			ReadonlyMessage message = this.guildWebhookService.createGuildWebhookMessagePost(postInput);

			if (message == null) {
				return UDiscordString.CANT_PUBLISH_POST;
			}
		} else {
			post.setMessageId(null);
		}

		post.setIsReviewed(true);
		this.postService.save(post);

		MessageChannel channel = input.getChannel();
		try {
			channel.deleteMessageById(messageId)
					.submit()
					.join();
		} catch (CancellationException
				| CompletionException ignored) {
		}

		if (isApprove) {
			return UDiscordString.JUST_APPROVED_NEW_POST;
		}

		return UDiscordString.JUST_DECLINES_NEW_POST;
	}
}

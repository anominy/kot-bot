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
import io.github.anominy.kotbot.dto.GuildSuggestMessagePostInput;
import io.github.anominy.kotbot.dto.GuildWebhookCreateMessagePostInput;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletionException;

@Service(LocalBean.GUILD_SUGGEST_SERVICE)
public final class GuildSuggestService implements IGuildSuggestService {
	private final IConfigService configService;
	private final IImageService imageService;
	private final IPersonService personService;
	private final IPersonInfoService personInfoService;
	private final IPostService postService;
	private final IGuildWebhookService guildWebhookService;
	private final ISuggestWebhookService suggestWebhookService;
	private final IPublishWebhookService publishWebhookService;

	public GuildSuggestService(
			@Qualifier(LocalBean.CONFIG_SERVICE)
			IConfigService configService,

			@Qualifier(LocalBean.IMAGE_SERVICE)
			IImageService imageService,

			@Qualifier(LocalBean.PERSON_SERVICE)
			IPersonService personService,

			@Qualifier(LocalBean.PERSON_INFO_SERVICE)
			IPersonInfoService personInfoService,

			@Qualifier(LocalBean.POST_SERVICE)
			IPostService postService,

			@Qualifier(LocalBean.GUILD_WEBHOOK_SERVICE)
			IGuildWebhookService guildWebhookService,

			@Qualifier(LocalBean.SUGGEST_WEBHOOK_SERVICE)
			ISuggestWebhookService suggestWebhookService,

			@Qualifier(LocalBean.PUBLISH_WEBHOOK_SERVICE)
			IPublishWebhookService publishWebhookService
	) {
		this.configService = configService;
		this.imageService = imageService;
		this.personService = personService;
		this.personInfoService = personInfoService;
		this.postService = postService;
		this.guildWebhookService = guildWebhookService;
		this.suggestWebhookService = suggestWebhookService;
		this.publishWebhookService = publishWebhookService;
	}

	@Override
	public String suggestGuildMessagePost(GuildSuggestMessagePostInput input) {
		Guild guild = input.getGuild();
		if (guild == null) {
			return UDiscordString.CANT_SUGGEST_POST_FROM_NULL_GUILD;
		}

		Message.Attachment attachment = input.getAttachment();
		if (attachment == null || !attachment.isImage()) {
			return UDiscordString.CANT_SUGGEST_POST_UNSUPPORTED_IMAGE_FORMAT;
		}

		long guildId = guild.getIdLong();

		ConfigEntity config = this.configService.findWithWebhooksByGuildId(guildId);
		if (config == null) {
			return UDiscordString.GUILD_HAS_NO_CONFIG_FOR_SUGGEST_REVIEW_PUBLISH;
		}

		SuggestWebhookEntity suggestWebhook = config.getSuggestWebhook();
		if (suggestWebhook == null) {
			return UDiscordString.GUILD_HAS_NO_CONFIG_FOR_SUGGEST_WEBHOOK;
		}

		PublishWebhookEntity publishWebhook = config.getPublishWebhook();
		if (publishWebhook == null) {
			return UDiscordString.GUILD_HAS_NO_CONFIG_FOR_PUBLISH_WEBHOOK;
		}

		boolean isSuggestWebhookEnabled = suggestWebhook.getIsEnabled();
		boolean isPublishWebhookEnabled = publishWebhook.getIsEnabled();

		if (!isSuggestWebhookEnabled && !isPublishWebhookEnabled) {
			return UDiscordString.GUILD_HAS_DISABLED_SUGGEST_PUBLISH_WEBHOOKS;
		}

		if (!isSuggestWebhookEnabled) {
			return UDiscordString.GUILD_HAS_DISABLED_SUGGEST_WEBHOOK;
		}

		if (!isPublishWebhookEnabled) {
			return UDiscordString.GUILD_HAS_DISABLED_PUBLISH_WEBHOOK;
		}

		TextChannel suggestWebhookChannel = guild.getTextChannelById(suggestWebhook.getChannelId());
		if (suggestWebhookChannel == null) {
			return UDiscordString.GUILD_HAS_NO_CONFIG_FOR_SUGGEST_WEBHOOK;
		}

		TextChannel publishWebhookChannel = guild.getTextChannelById(publishWebhook.getChannelId());
		if (publishWebhookChannel == null) {
			return UDiscordString.GUILD_HAS_NO_CONFIG_FOR_PUBLISH_WEBHOOK;
		}

		List<Webhook> discordWebhooks = new ArrayList<>();
		try {
			List<Webhook> discordSuggestWebhooks = suggestWebhookChannel.retrieveWebhooks()
					.submit()
					.join();

			List<Webhook> discordPublishWebhooks = publishWebhookChannel.retrieveWebhooks()
					.submit()
					.join();

			discordWebhooks.addAll(discordSuggestWebhooks);
			discordWebhooks.addAll(discordPublishWebhooks);
		} catch (CancellationException | CompletionException ignored) {
		}

		long discordWebhookCount = discordWebhooks.stream()
				.filter(discordWebhook -> {
					long webhookId = discordWebhook.getIdLong();

					return webhookId == suggestWebhook.getWebhookId()
							|| webhookId == publishWebhook.getWebhookId();
				})
				.count();

		if (discordWebhookCount != 2) {
			return UDiscordString.GUILD_HAS_NO_CONFIG_FOR_SUGGEST_REVIEW_PUBLISH;
		}

		String content = input.getContent();

		List<PersonEntity> authors = new ArrayList<>(2);

		User author0 = input.getAuthor();

		PersonEntity person0 = this.personService.findOrCreateIfNotExistsByUserId(
				author0.getIdLong());

		authors.add(person0);

		User author1 = input.getCoAuthor();

		if (author1 != null) {
			PersonEntity person1 = this.personService.findOrCreateIfNotExistsByUserId(
					author1.getIdLong());

			authors.add(person1);
		}

		String imageUrl = attachment.getProxyUrl();

		Set<ImageEntity> images = new HashSet<>(1);
		ImageEntity image = this.imageService.save(
				new ImageEntity()
						.setUrl(imageUrl)
		);
		images.add(image);

		PostEntity post = new PostEntity()
				.setGuildId(guildId)
				.setContent(content)
				.setAuthors(authors)
				.setImages(images);

		Long personId0 = person0.getId();

		PersonInfoEntity personInfo0 = this.personInfoService.findWithRoleTypesOrCreateIfNotExistsByPersonIdAndGuildId(
				personId0, guildId);

		Set<EPersonRole> roleTypes0 = personInfo0.getRoleTypes();

		JDA discordApi = input.getDiscordApi();

		GuildWebhookCreateMessagePostInput.Builder postInputBuilder = new GuildWebhookCreateMessagePostInput.Builder()
				.setDiscordApi(discordApi)
				.setPost(post);

		String output;

		if (roleTypes0 == null || !roleTypes0.contains(EPersonRole.AUTHOR)) {
			GuildWebhookCreateMessagePostInput postInput = postInputBuilder.setWebhookService(
					this.suggestWebhookService).build();

			ReadonlyMessage message = this.guildWebhookService.createGuildWebhookMessagePost(
					postInput);

			if (message == null) {
				return UDiscordString.CANT_SUGGEST_POST;
			}

			post.setMessageId(message.getId());

			output = UDiscordString.JUST_PROPOSED_NEW_POST_CONGRATS;
		} else {
			GuildWebhookCreateMessagePostInput postInput = postInputBuilder.setWebhookService(
					this.publishWebhookService).build();

			ReadonlyMessage message = this.guildWebhookService.createGuildWebhookMessagePost(
					postInput);

			if (message == null) {
				return UDiscordString.CANT_PUBLISH_POST;
			}

			post.setIsReviewed(true);

			output = UDiscordString.JUST_PUBLISHED_NEW_POST_CONGRATS;
		}

		this.postService.save(post);

		return output;
	}
}

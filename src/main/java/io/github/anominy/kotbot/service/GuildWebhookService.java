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

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.receive.ReadonlyMessage;
import club.minnced.discord.webhook.send.WebhookMessage;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import io.github.anominy.kotbot.dto.*;
import io.github.anominy.kotbot.entity.*;
import io.github.anominy.kotbot.service.api.IConfigService;
import io.github.anominy.kotbot.service.api.IGuildWebhookService;
import io.github.anominy.kotbot.service.api.IWebhookService;
import io.github.anominy.kotbot.bean.LocalBean;
import io.github.anominy.kotbot.discord.util.UDiscordString;
import io.github.anominy.uwutils.UwFile;
import io.github.anominy.uwutils.UwObject;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("DuplicatedCode")

@Service(LocalBean.GUILD_WEBHOOK_SERVICE)
public final class GuildWebhookService implements IGuildWebhookService {
	private final IConfigService configService;
	private final IWebhookService<WebhookEntity<?>> webhookService;

	public GuildWebhookService(
			@Qualifier(LocalBean.CONFIG_SERVICE)
			IConfigService configService,

			@Qualifier(LocalBean.GENERIC_WEBHOOK_SERVICE)
			IWebhookService<WebhookEntity<?>> webhookService
	) {
		this.configService = configService;
		this.webhookService = webhookService;
	}

	@Override
	public String findGuildWebhookChannel(GuildWebhookFindChannelInput input) {
		String webhookName = input.getWebhookName();

		Guild guild = input.getGuild();
		if (guild == null) {
			return UDiscordString.CANT_GET_S_WEBHOOK_CHANNEL_NULL_GUILD(webhookName);
		}

		long guildId = guild.getIdLong();

		ConfigEntity config = this.configService.findWithWebhooksByGuildId(guildId);
		if (config == null) {
			return UDiscordString.NO_CONFIG_FOR_GUILD;
		}

		Function<ConfigEntity, WebhookEntity<?>> webhookGetterFunc
				= input.getWebhookGetterFunc();

		WebhookEntity<?> webhook = webhookGetterFunc.apply(config);
		if (webhook == null) {
			return UDiscordString.NO_S_WEBHOOK_FOR_GUILD(webhookName);
		}

		TextChannel webhookTextChannel = guild.getTextChannelById(webhook.getChannelId());
		if (webhookTextChannel == null) {
			return UDiscordString.NO_S_WEBHOOK_FOR_GUILD(webhookName);
		}

		Webhook discordWebhook = null;
		try {
			discordWebhook = webhookTextChannel.retrieveWebhooks()
					.submit()
					.join()
					.stream()
					.filter(it -> it.getIdLong() == webhook.getWebhookId())
					.findAny()
					.orElse(null);
		} catch (CancellationException | CompletionException ignored) {
		}

		if (discordWebhook == null) {
			return UDiscordString.NO_S_WEBHOOK_FOR_GUILD(webhookName);
		}

		String channelMention = webhookTextChannel.getAsMention();

		return UDiscordString.S_WEBHOOK_SUBSCRIBED_TO_S_CHANNEL(webhookName, channelMention);
	}

	@Override
	public String toggleGuildWebhook(GuildWebhookToggleInput input) {
		String webhookName = input.getWebhookName();
		String actionName = input.getActionName();

		Guild guild = input.getGuild();
		if (guild == null) {
			return UDiscordString.CANT_S_S_WEBHOOK_FOR_NULL_GUILD(actionName, webhookName);
		}

		long guildId = guild.getIdLong();

		ConfigEntity config = this.configService.findWithWebhooksByGuildId(guildId);
		if (config == null) {
			return UDiscordString.NO_CONFIG_FOR_GUILD;
		}

		Function<ConfigEntity, WebhookEntity<?>> webhookGetterFunc
				= input.getWebhookGetterFunc();

		WebhookEntity<?> webhook = webhookGetterFunc.apply(config);
		if (webhook == null) {
			return UDiscordString.NO_S_WEBHOOK_FOR_GUILD(webhookName);
		}

		TextChannel webhookTextChannel = guild.getTextChannelById(webhook.getChannelId());
		if (webhookTextChannel == null) {
			return UDiscordString.NO_S_WEBHOOK_FOR_GUILD(webhookName);
		}

		Webhook discordWebhook = null;
		try {
			discordWebhook = webhookTextChannel.retrieveWebhooks()
					.submit()
					.join()
					.stream()
					.filter(it -> it.getIdLong() == webhook.getWebhookId())
					.findAny()
					.orElse(null);
		} catch (CancellationException | CompletionException ignored) {
		}

		if (discordWebhook == null) {
			return UDiscordString.NO_S_WEBHOOK_FOR_GUILD(webhookName);
		}

		Boolean prevIsEnabled = webhook.getIsEnabled();

		Boolean isEnable = input.getIsEnable();
		webhook.setIsEnabled(isEnable);

		Boolean currIsEnabled = webhook.getIsEnabled();

		String pastActionName = input.getPastActionName();

		if (prevIsEnabled == currIsEnabled) {
			return UDiscordString.S_WEBHOOK_ALREADY_S(webhookName, pastActionName);
		}

		this.webhookService.save(webhook);

		return UDiscordString.S_WEBHOOK_SUCCESSFULLY_S(webhookName, pastActionName);
	}

	@Override
	public <T extends WebhookEntity<?>, U extends IWebhookService<T>> String moveGuildWebhookToChannel(GuildWebhookMoveToChannelInput<T, U> input) {
		String webhookName = input.getWebhookName();

		Guild guild = input.getGuild();
		if (guild == null) {
			return UDiscordString.CANT_MOVE_S_WEBHOOK_NULL_GUILD(webhookName);
		}

		long guildId = guild.getIdLong();

		ConfigEntity config = this.configService.findWithWebhooksByGuildId(guildId);
		if (config == null) {
			return UDiscordString.NO_CONFIG_FOR_GUILD;
		}

		Function<ConfigEntity, T> webhookGetterFunc
				= input.getWebhookGetterFunc();

		T webhook = webhookGetterFunc.apply(config);
		if (webhook == null) {
			return UDiscordString.NO_S_WEBHOOK_FOR_GUILD(webhookName);
		}

		GuildChannel moveGuildChannel = input.getChannel();
		if (moveGuildChannel == null) {
			return UDiscordString.CANT_MOVE_S_WEBHOOK_NULL_CHANNEL(webhookName);
		}

		TextChannel moveTextChannel;
		if (moveGuildChannel instanceof TextChannel channel) {
			moveTextChannel = channel;
		} else {
			return UDiscordString.CANT_MOVE_S_WEBHOOK_NON_TEXT_CHANNEL(webhookName);
		}

		TextChannel webhookTextChannel = guild.getTextChannelById(webhook.getChannelId());
		if (webhookTextChannel == null) {
			return UDiscordString.NO_S_WEBHOOK_FOR_GUILD(webhookName);
		}

		Webhook discordWebhook = null;
		try {
			discordWebhook = webhookTextChannel.retrieveWebhooks()
					.submit()
					.join()
					.stream()
					.filter(it -> it.getIdLong() == webhook.getWebhookId())
					.findAny()
					.orElse(null);
		} catch (CancellationException | CompletionException ignored) {
		}

		if (discordWebhook == null) {
			return UDiscordString.NO_S_WEBHOOK_FOR_GUILD(webhookName);
		}

		long moveTextChannelId = moveTextChannel.getIdLong();
		long webhookTextChannelId = webhookTextChannel.getIdLong();

		String channelMention = moveTextChannel.getAsMention();

		if (moveTextChannelId == webhookTextChannelId) {
			return UDiscordString.S_WEBHOOK_ALREADY_PLACED_IN_S_CHANNEL(webhookName, channelMention);
		}

		try {
			discordWebhook.getManager()
					.setChannel(moveTextChannel)
					.submit()
					.join();

			U webhookService = input.getWebhookService();

			//noinspection unchecked
			webhookService.save((T) webhook.setChannelId(moveTextChannelId));

			return UDiscordString.S_WEBHOOK_SUCCESSFULLY_MOVED_S_CHANNEL(webhookName, channelMention);
		} catch (CancellationException | CompletionException ignored) {
		}

		return UDiscordString.CANT_MOVE_S_WEBHOOK_TO_S_CHANNEL(webhookName, channelMention);
	}

	@Override
	public <T extends WebhookEntity<?>, U extends IWebhookService<T>> String unsubscribeGuildWebhookFromChannel(GuildWebhookUnsubscribeFromChannelInput<T, U> input) {
		String webhookName = input.getWebhookName();

		Guild guild = input.getGuild();
		if (guild == null) {
			return UDiscordString.CANT_UNSUBSCRIBE_S_WEBHOOK_NULL_GUILD(webhookName);
		}

		long guildId = guild.getIdLong();

		ConfigEntity config = this.configService.findWithWebhooksByGuildId(guildId);
		if (config == null) {
			return UDiscordString.NO_CONFIG_FOR_GUILD;
		}

		Function<ConfigEntity, T> webhookGetterFunc
				= input.getWebhookGetterFunc();

		T webhook = webhookGetterFunc.apply(config);
		if (webhook == null) {
			return UDiscordString.NO_S_WEBHOOK_FOR_GUILD(webhookName);
		}

		BiFunction<ConfigEntity, T, ?> webhookSetterFunc
				= input.getWebhookSetterFunc();

		webhookSetterFunc.apply(config, null);

		this.configService.save(config);

		U webhookService = input.getWebhookService();

		webhookService.delete(webhook);

		TextChannel webhookTextChannel = guild.getTextChannelById(webhook.getChannelId());
		if (webhookTextChannel == null) {
			return UDiscordString.NO_S_WEBHOOK_FOR_GUILD(webhookName);
		}

		String webhookId = Long.toUnsignedString(webhook.getWebhookId());
		String channelMention = webhookTextChannel.getAsMention();

		try {
			webhookTextChannel.deleteWebhookById(webhookId)
					.submit()
					.join();

			return UDiscordString.S_WEBHOOK_SUCCESSFULLY_UNSUBSCRIBED_S_CHANNEL(webhookName, channelMention);
		} catch (CancellationException | CompletionException ignored) {
		}

		return UDiscordString.S_WEBHOOK_ALREADY_UNSUBSCRIBED_S_CHANNEL(webhookName, channelMention);
	}

	@Override
	public <T extends WebhookEntity<?>, U extends IWebhookService<T>> String subscribeGuildWebhookToChannel(GuildWebhookSubscribeToChannelInput<T, U> input) {
		String webhookName = input.getWebhookName();

		Guild guild = input.getGuild();
		if (guild == null) {
			return UDiscordString.CANT_SUBSCRIBE_S_WEBHOOK_NULL_GUILD(webhookName);
		}

		GuildChannel guildChannel = input.getChannel();
		if (guildChannel == null) {
			return UDiscordString.CANT_SUBSCRIBE_S_WEBHOOK_NULL_CHANNEL(webhookName);
		}

		TextChannel textChannel;
		if (guildChannel instanceof TextChannel channel) {
			textChannel = channel;
		} else {
			return UDiscordString.CANT_SUBSCRIBE_S_WEBHOOK_NON_TEXT_CHANNEL(webhookName);
		}

		long guildId = guild.getIdLong();

		ConfigEntity config = this.configService.findWithWebhooksByGuildId(guildId);
		if (config == null) {
			config = new ConfigEntity()
					.setGuildId(guildId);
		}

		Function<ConfigEntity, T> webhookGetterFunc
				= input.getWebhookGetterFunc();

		T webhook = webhookGetterFunc.apply(config);
		if (webhook != null) {
			TextChannel webhookTextChannel = guild.getTextChannelById(webhook.getChannelId());

			if (webhookTextChannel != null) {
				Webhook discordWebhook = null;
				try {
					T finalWebhook = webhook;

					discordWebhook = webhookTextChannel.retrieveWebhooks()
							.submit()
							.join()
							.stream()
							.filter(it -> it.getIdLong() == finalWebhook.getWebhookId())
							.findAny()
							.orElse(null);
				} catch (CancellationException
						| CompletionException ignored) {
				}

				if (discordWebhook != null) {
					String channelMention = webhookTextChannel.getAsMention();

					return UDiscordString.S_WEBHOOK_ALREADY_SUBSCRIBED_S_CHANNEL(webhookName, channelMention);
				}
			}

			BiFunction<ConfigEntity, T, ?> webhookSetterFunc
					= input.getWebhookSetterFunc();

			webhookSetterFunc.apply(config, null);

			this.configService.save(config);

			U webhookService = input.getWebhookService();

			webhookService.delete(webhook);
		}

		String selfName = input.getDiscordApi()
				.getSelfUser()
				.getEffectiveName();

		String channelMention = textChannel.getAsMention();

		try {
			Webhook discordWebhook = textChannel.createWebhook(selfName)
					.submit()
					.get();

			long channelId = textChannel.getIdLong();

			long webhookId = discordWebhook.getIdLong();
			String webhookToken = discordWebhook.getToken();

			Supplier<T> webhookCreateFunc = input.getWebhookCreateFunc();

			//noinspection unchecked
			webhook = (T) webhookCreateFunc.get()
					.setGuildId(guildId)
					.setChannelId(channelId)
					.setWebhookId(webhookId)
					.setWebhookToken(webhookToken);

			BiFunction<ConfigEntity, T, ?> webhookSetterFunc
					= input.getWebhookSetterFunc();

			webhookSetterFunc.apply(config, webhook);

			U webhookService = input.getWebhookService();

			webhookService.save(webhook);
			this.configService.save(config);

			return UDiscordString.S_WEBHOOK_SUCCESSFULLY_SUBSCRIBED_S_CHANNEL(webhookName, channelMention);
		} catch (CancellationException | CompletionException
				| ExecutionException | InterruptedException ignored) {
		}

		return UDiscordString.CANT_CREATE_S_WEBHOOK_IN_S_CHANNEL(webhookName, channelMention);
	}

	@Override
	public ReadonlyMessage createGuildWebhookMessagePost(GuildWebhookCreateMessagePostInput input) {
		IWebhookService<? extends WebhookEntity<?>> webhookService
				= input.getWebhookService();

		PostEntity post = input.getPost();

		long guildId = post.getGuildId();

		WebhookEntity<?> webhook = webhookService.findOneEnabledByGuildId(guildId);
		if (webhook == null) {
			return null;
		}

		JDA discordApi = input.getDiscordApi();

		List<PersonEntity> authors = post.getAuthors();

		User author = null;
		if (authors != null) {
			author = authors.stream()
					.filter(Objects::nonNull)
					.map(PersonEntity::getUserId)
					.map(discordApi::getUserById)
					.filter(Objects::nonNull)
					.findAny()
					.orElse(null);
		}

		author = UwObject.ifNull(author, discordApi.getSelfUser());

		String authorName = author.getEffectiveName();
		String authorAvatar = author.getEffectiveAvatarUrl();

		String content = post.getContent();

		WebhookMessageBuilder webhookMessageBuilder = new WebhookMessageBuilder()
				.setUsername(authorName)
				.setAvatarUrl(authorAvatar)
				.setContent(content);

		Set<ImageEntity> images = post.getImages();
		List<InputStream> imageStreams = new ArrayList<>(images.size());

		for (ImageEntity image : images) {
			String imageUrl = image.getUrl();
			String imageName = UwFile.getNameOrNull(imageUrl);

			if (imageName == null) {
				continue;
			}

			InputStream imageStream;
			try {
				imageStream = new URL(imageUrl)
						.openStream();
			} catch (IOException ignored) {
				continue;
			}
			imageStreams.add(imageStream);

			webhookMessageBuilder.addFile(imageName, imageStream);
		}

		WebhookMessage webhookMessage = webhookMessageBuilder.build();

		long webhookId = webhook.getWebhookId();
		String webhookToken = webhook.getWebhookToken();

		WebhookClient webhookClient = WebhookClient.withId(webhookId, webhookToken);

		try {
			return webhookClient.send(webhookMessage)
					.get();
		} catch (CancellationException | CompletionException
				| ExecutionException | InterruptedException ignored) {
		} finally {
			for (InputStream inputStream : imageStreams) {
				try {
					inputStream.close();
				} catch (IOException ignored) {
				}
			}
		}

		return null;
	}
}

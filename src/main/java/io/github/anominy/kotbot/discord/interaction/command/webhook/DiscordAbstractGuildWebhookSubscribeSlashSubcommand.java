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

package io.github.anominy.kotbot.discord.interaction.command.webhook;

import io.github.anominy.kotbot.service.api.IGuildWebhookService;
import io.github.anominy.kotbot.service.api.IWebhookService;
import io.github.anominy.jda.prelude.interaction.command.IDiscordSlashCommandOption;
import io.github.anominy.jda.prelude.interaction.command.IDiscordSlashSubcommand;
import io.github.anominy.kotbot.dto.GuildWebhookSubscribeToChannelInput;
import io.github.anominy.kotbot.entity.ConfigEntity;
import io.github.anominy.kotbot.entity.WebhookEntity;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

abstract sealed class DiscordAbstractGuildWebhookSubscribeSlashSubcommand<T extends WebhookEntity<?>, U extends IWebhookService<T>> implements IDiscordSlashSubcommand
		permits DiscordGuildSuggestWebhookSubscribeSlashSubcommand, DiscordGuildPublishWebhookSubscribeSlashSubcommand {
	protected static final String NAME = "subscribe";

	private final String webhookName;
	private final IGuildWebhookService guildWebhookService;
	private final U webhookService;
	private final Function<ConfigEntity, T> webhookGetterFunc;
	private final BiFunction<ConfigEntity, T, ?> webhookSetterFunc;
	private final Supplier<T> webhookCreateFunc;

	protected DiscordAbstractGuildWebhookSubscribeSlashSubcommand(
			String webhookName,
			IGuildWebhookService guildWebhookService,
			U webhookService,
			Function<ConfigEntity, T> webhookGetterFunc,
			BiFunction<ConfigEntity, T, ?> webhookSetterFunc,
			Supplier<T> webhookCreateFunc
	) {
		if (webhookName == null || webhookName.isBlank()) {
			throw new IllegalArgumentException("Webhook name mustn't be <null/blank>");
		}

		if (guildWebhookService == null) {
			throw new IllegalArgumentException("Guild webhook service mustn't be <null>");
		}

		if (webhookService == null) {
			throw new IllegalArgumentException("Webhook service mustn't be <null>");
		}

		if (webhookGetterFunc == null) {
			throw new IllegalArgumentException("Webhook getter function mustn't be <null>");
		}

		if (webhookSetterFunc == null) {
			throw new IllegalArgumentException("Webhook setter function mustn't be <null>");
		}

		if (webhookCreateFunc == null) {
			throw new IllegalArgumentException("Webhook create function mustn't be <null>");
		}

		this.webhookName = webhookName;
		this.guildWebhookService = guildWebhookService;
		this.webhookService = webhookService;
		this.webhookGetterFunc = webhookGetterFunc;
		this.webhookSetterFunc = webhookSetterFunc;
		this.webhookCreateFunc = webhookCreateFunc;
	}

	@Override
	public final IDiscordSlashCommandOption[] getDiscordSlashCommandOptionArray() {
		return new IDiscordSlashCommandOption[] {
				new DiscordGuildWebhookChannelSlashCommandOption()
		};
	}

	@Override
	public final void onDiscordSlashCommandInteractionEvent(SlashCommandInteractionEvent event) {
		event.deferReply(true)
				.queue(hook -> {
					JDA discordApi = event.getJDA();

					Guild guild = event.getGuild();

					GuildChannel channel = event.getOption(DiscordGuildWebhookChannelSlashCommandOption.NAME,
							null, OptionMapping::getAsChannel);

					GuildWebhookSubscribeToChannelInput<T, U> input = new GuildWebhookSubscribeToChannelInput.Builder<T, U>()
							.setDiscordApi(discordApi)
							.setGuild(guild)
							.setChannel(channel)
							.setWebhookName(this.webhookName)
							.setWebhookService(this.webhookService)
							.setWebhookGetterFunc(this.webhookGetterFunc)
							.setWebhookSetterFunc(this.webhookSetterFunc)
							.setWebhookCreateFunc(this.webhookCreateFunc)
							.build();

					hook.sendMessage(this.guildWebhookService.subscribeGuildWebhookToChannel(input))
							.queue();
				});
	}
}

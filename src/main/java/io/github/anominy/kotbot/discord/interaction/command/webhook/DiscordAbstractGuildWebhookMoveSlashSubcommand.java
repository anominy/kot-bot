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
import io.github.anominy.kotbot.dto.GuildWebhookMoveToChannelInput;
import io.github.anominy.kotbot.entity.ConfigEntity;
import io.github.anominy.kotbot.entity.WebhookEntity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.function.Function;

abstract sealed class DiscordAbstractGuildWebhookMoveSlashSubcommand<T extends WebhookEntity<?>, U extends IWebhookService<T>> implements IDiscordSlashSubcommand
		permits DiscordGuildSuggestWebhookMoveSlashSubcommand, DiscordGuildPublishWebhookMoveSlashSubcommand {
	protected static final String NAME = "move";

	private final String webhookName;
	private final IGuildWebhookService guildWebhookService;
	private final U webhookService;
	private final Function<ConfigEntity, T> webhookGetterFunc;

	protected DiscordAbstractGuildWebhookMoveSlashSubcommand(
			String webhookName,
			IGuildWebhookService guildWebhookService,
			U webhookService,
			Function<ConfigEntity, T> webhookGetterFunc
	) {
		if (webhookName == null || webhookName.isBlank()) {
			throw new IllegalArgumentException("Webhook name mustn't be <null>");
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

		this.webhookName = webhookName;
		this.guildWebhookService = guildWebhookService;
		this.webhookService = webhookService;
		this.webhookGetterFunc = webhookGetterFunc;
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
					Guild guild = event.getGuild();

					GuildChannel channel = event.getOption(DiscordGuildWebhookChannelSlashCommandOption.NAME,
							null, OptionMapping::getAsChannel);

					GuildWebhookMoveToChannelInput<T, U> input = new GuildWebhookMoveToChannelInput.Builder<T, U>()
							.setGuild(guild)
							.setChannel(channel)
							.setWebhookName(this.webhookName)
							.setWebhookService(this.webhookService)
							.setWebhookGetterFunc(this.webhookGetterFunc)
							.build();

					hook.sendMessage(this.guildWebhookService.moveGuildWebhookToChannel(input))
							.queue();
				});
	}
}

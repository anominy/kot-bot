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
import io.github.anominy.jda.prelude.interaction.command.IDiscordSlashCommandOption;
import io.github.anominy.jda.prelude.interaction.command.IDiscordSlashSubcommand;
import io.github.anominy.kotbot.dto.GuildWebhookToggleInput;
import io.github.anominy.kotbot.entity.ConfigEntity;
import io.github.anominy.kotbot.entity.WebhookEntity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.function.Function;

@SuppressWarnings("EmptyMethod")
abstract sealed class DiscordAbstractGuildWebhookToggleSlashSubcommand implements IDiscordSlashSubcommand
		permits DiscordAbstractGuildWebhookEnableSlashSubcommand, DiscordAbstractGuildWebhookDisableSlashSubcommand {
	private final String webhookName;
	private final IGuildWebhookService guildWebhookService;
	private final String actionName;
	private final String pastActionName;
	private final Boolean isEnable;
	private final Function<ConfigEntity, WebhookEntity<?>> webhookGetterFunc;

	protected DiscordAbstractGuildWebhookToggleSlashSubcommand(
			String webhookName,
			IGuildWebhookService guildWebhookService,
			String actionName,
			Boolean isEnable,
			Function<ConfigEntity, WebhookEntity<?>> webhookGetterFunc
	) {
		if (webhookName == null || webhookName.isBlank()) {
			throw new IllegalArgumentException("Webhook name mustn't be <null/blank>");
		}

		if (guildWebhookService == null) {
			throw new IllegalArgumentException("Guild webhook service mustn't be <null>");
		}

		if (actionName == null || actionName.isBlank()) {
			throw new IllegalArgumentException("Action name mustn't be <null/blank>");
		}

		if (isEnable == null) {
			throw new IllegalArgumentException("Is enable mustn't be <null>");
		}

		if (webhookGetterFunc == null) {
			throw new IllegalArgumentException("Webhook getter function mustn't be <null>");
		}

		this.webhookName = webhookName;
		this.guildWebhookService = guildWebhookService;
		this.actionName = actionName;

		this.pastActionName = this.actionName.replaceAll("e$", "")
				+ "ed";

		this.isEnable = isEnable;
		this.webhookGetterFunc = webhookGetterFunc;
	}

	@Override
	public final IDiscordSlashCommandOption[] getDiscordSlashCommandOptionArray() {
		return IDiscordSlashSubcommand.super.getDiscordSlashCommandOptionArray();
	}

	@Override
	public final void onDiscordSlashCommandInteractionEvent(SlashCommandInteractionEvent event) {
		event.deferReply(true)
				.queue(hook -> {
					Guild guild = event.getGuild();

					GuildWebhookToggleInput input = new GuildWebhookToggleInput.Builder()
							.setGuild(guild)
							.setWebhookName(this.webhookName)
							.setActionName(this.actionName)
							.setPastActionName(this.pastActionName)
							.setIsEnable(this.isEnable)
							.setWebhookGetterFunc(this.webhookGetterFunc)
							.build();

					hook.sendMessage(this.guildWebhookService.toggleGuildWebhook(input))
							.queue();
				});
	}
}

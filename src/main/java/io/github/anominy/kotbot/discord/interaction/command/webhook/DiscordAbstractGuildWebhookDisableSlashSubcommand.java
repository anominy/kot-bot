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
import io.github.anominy.kotbot.entity.ConfigEntity;
import io.github.anominy.kotbot.entity.WebhookEntity;

import java.util.function.Function;

abstract sealed class DiscordAbstractGuildWebhookDisableSlashSubcommand extends DiscordAbstractGuildWebhookToggleSlashSubcommand
		permits DiscordGuildSuggestWebhookDisableSlashSubcommand, DiscordGuildPublishWebhookDisableSlashSubcommand {
	protected static final String NAME = "disable";

	protected DiscordAbstractGuildWebhookDisableSlashSubcommand(
			String webhookName,
			IGuildWebhookService guildWebhookService,
			Function<ConfigEntity, WebhookEntity<?>> webhookGetterFunc
	) {
		super(webhookName, guildWebhookService, NAME, false, webhookGetterFunc);
	}
}

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
import io.github.anominy.kotbot.service.api.ISuggestWebhookService;
import io.github.anominy.jda.prelude.interaction.command.IDiscordSlashSubcommand;
import io.github.anominy.jda.prelude.interaction.command.IDiscordSlashSubcommandGroup;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;

final class DiscordGuildSuggestWebhookSlashSubcommandGroup implements IDiscordSlashSubcommandGroup {
	public static final String NAME = "suggest";

	private final IGuildWebhookService guildWebhookService;
	private final ISuggestWebhookService webhookService;

	public DiscordGuildSuggestWebhookSlashSubcommandGroup(
			IGuildWebhookService guildWebhookService,
			ISuggestWebhookService webhookService
	) {
		this.guildWebhookService = guildWebhookService;
		this.webhookService = webhookService;
	}

	@Override
	public SubcommandGroupData initDiscordSlashSubcommandGroupData() {
		return new SubcommandGroupData(NAME, "Suggest Webhook!");
	}

	@Override
	public IDiscordSlashSubcommand[] getDiscordSlashSubcommandArray() {
		return new IDiscordSlashSubcommand[] {
				new DiscordGuildSuggestWebhookSubscribeSlashSubcommand(this.guildWebhookService, this.webhookService),
				new DiscordGuildSuggestWebhookUnsubscribeSlashSubcommand(this.guildWebhookService, this.webhookService),
				new DiscordGuildSuggestWebhookMoveSlashSubcommand(this.guildWebhookService, this.webhookService),
				new DiscordGuildSuggestWebhookChannelSlashSubcommand(this.guildWebhookService),
				new DiscordGuildSuggestWebhookEnableSlashSubcommand(this.guildWebhookService),
				new DiscordGuildSuggestWebhookDisableSlashSubcommand(this.guildWebhookService)
		};
	}
}

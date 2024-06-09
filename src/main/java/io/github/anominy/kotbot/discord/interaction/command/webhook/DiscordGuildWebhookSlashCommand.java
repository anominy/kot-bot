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
import io.github.anominy.kotbot.service.api.IPublishWebhookService;
import io.github.anominy.kotbot.service.api.ISuggestWebhookService;
import io.github.anominy.jda.prelude.interaction.command.IDiscordSlashCommand;
import io.github.anominy.jda.prelude.interaction.command.IDiscordSlashSubcommandGroup;
import io.github.anominy.kotbot.bean.LocalBean;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public final class DiscordGuildWebhookSlashCommand implements IDiscordSlashCommand {
	private final IGuildWebhookService guildWebhookService;
	private final ISuggestWebhookService suggestWebhookService;
	private final IPublishWebhookService publishWebhookService;

	public DiscordGuildWebhookSlashCommand(
			@Qualifier(LocalBean.GUILD_WEBHOOK_SERVICE)
			IGuildWebhookService guildWebhookService,

			@Qualifier(LocalBean.SUGGEST_WEBHOOK_SERVICE)
			ISuggestWebhookService suggestWebhookService,

			@Qualifier(LocalBean.PUBLISH_WEBHOOK_SERVICE)
			IPublishWebhookService publishWebhookService
	) {
		this.guildWebhookService = guildWebhookService;
		this.suggestWebhookService = suggestWebhookService;
		this.publishWebhookService = publishWebhookService;
	}

	@Override
	public SlashCommandData initDiscordSlashCommandData() {
		DefaultMemberPermissions defaultMemberPermissions
				= DefaultMemberPermissions.enabledFor(Permission.MANAGE_SERVER, Permission.MANAGE_WEBHOOKS);

		return Commands.slash("webhook", "Webhook!")
				.setDefaultPermissions(defaultMemberPermissions);
	}

	@Override
	public IDiscordSlashSubcommandGroup[] getDiscordSlashSubcommandGroupArray() {
		return new IDiscordSlashSubcommandGroup[] {
				new DiscordGuildSuggestWebhookSlashSubcommandGroup(this.guildWebhookService, this.suggestWebhookService),
				new DiscordGuildPublishWebhookSlashSubcommandGroup(this.guildWebhookService, this.publishWebhookService)
		};
	}

	@Override
	public void onDiscordSlashCommandInteractionEvent(SlashCommandInteractionEvent event) {
		throw new IllegalStateException();
	}
}

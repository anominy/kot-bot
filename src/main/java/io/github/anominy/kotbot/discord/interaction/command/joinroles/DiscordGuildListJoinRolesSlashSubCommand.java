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

package io.github.anominy.kotbot.discord.interaction.command.joinroles;

import io.github.anominy.kotbot.service.api.IGuildJoinRolesService;
import io.github.anominy.jda.prelude.interaction.command.IDiscordSlashSubcommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

final class DiscordGuildListJoinRolesSlashSubCommand implements IDiscordSlashSubcommand {
	private final IGuildJoinRolesService guildJoinRolesService;

	public DiscordGuildListJoinRolesSlashSubCommand(IGuildJoinRolesService guildJoinRolesService) {
		if (guildJoinRolesService == null) {
			throw new IllegalArgumentException("Guild join roles service mustn't be <null>");
		}

		this.guildJoinRolesService = guildJoinRolesService;
	}

	@Override
	public SubcommandData initDiscordSlashSubcommandData() {
		return new SubcommandData("list", "List Join Roles!");
	}

	@Override
	public void onDiscordSlashCommandInteractionEvent(SlashCommandInteractionEvent event) {
		event.deferReply(true)
				.queue(hook -> {
					Guild guild = event.getGuild();

					hook.sendMessage(this.guildJoinRolesService.listAllGuildJoinRoles(guild))
							.queue();
				});
	}
}

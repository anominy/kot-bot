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
import io.github.anominy.jda.prelude.interaction.command.IDiscordSlashCommand;
import io.github.anominy.jda.prelude.interaction.command.IDiscordSlashSubcommand;
import io.github.anominy.kotbot.bean.LocalBean;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public final class DiscordGuildJoinRolesSlashCommand implements IDiscordSlashCommand {
	private final IGuildJoinRolesService guildJoinRolesService;

	public DiscordGuildJoinRolesSlashCommand(
			@Qualifier(LocalBean.GUILD_JOIN_ROLES_SERVICE)
			IGuildJoinRolesService guildJoinRolesService
	) {
		this.guildJoinRolesService = guildJoinRolesService;
	}

	@Override
	public SlashCommandData initDiscordSlashCommandData() {
		DefaultMemberPermissions defaultMemberPermissions
				= DefaultMemberPermissions.enabledFor(Permission.MANAGE_SERVER, Permission.MANAGE_ROLES);

		return Commands.slash("join-roles", "Join Roles!")
				.setDefaultPermissions(defaultMemberPermissions);
	}

	@Override
	public IDiscordSlashSubcommand[] getDiscordSlashSubcommandArray() {
		return new IDiscordSlashSubcommand[] {
				new DiscordGuildAddJoinRoleSlashSubcommand(this.guildJoinRolesService),
				new DiscordGuildRemoveJoinRoleSlashSubcommand(this.guildJoinRolesService),
				new DiscordGuildListJoinRolesSlashSubCommand(this.guildJoinRolesService)
		};
	}

	@Override
	public void onDiscordSlashCommandInteractionEvent(SlashCommandInteractionEvent event) {
		throw new IllegalStateException();
	}
}

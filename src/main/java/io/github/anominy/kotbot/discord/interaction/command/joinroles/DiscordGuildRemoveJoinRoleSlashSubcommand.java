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
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

import java.util.Set;

final class DiscordGuildRemoveJoinRoleSlashSubcommand extends DiscordAbstractGuildModifyJoinRolesSlashSubcommand {
	private static final String NAME = "remove";

	public DiscordGuildRemoveJoinRoleSlashSubcommand(IGuildJoinRolesService guildJoinRolesService) {
		super(guildJoinRolesService, NAME, "from", true, Set::remove);
	}

	@Override
	public SubcommandData initDiscordSlashSubcommandData() {
		return new SubcommandData(NAME, "Remove Join Role!");
	}
}

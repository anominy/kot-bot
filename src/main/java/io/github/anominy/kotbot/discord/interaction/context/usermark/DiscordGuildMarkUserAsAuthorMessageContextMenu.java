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

package io.github.anominy.kotbot.discord.interaction.context.usermark;

import io.github.anominy.kotbot.service.api.IGuildUserRolesService;
import io.github.anominy.jda.prelude.interaction.context.IDiscordMessageContextMenu;
import io.github.anominy.kotbot.bean.LocalBean;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.beans.factory.annotation.Qualifier;

@SuppressWarnings("unused")

//@Component
public final class DiscordGuildMarkUserAsAuthorMessageContextMenu extends DiscordGuildMarkUserAsAuthorGenericContextMenu
		implements IDiscordMessageContextMenu {

	public DiscordGuildMarkUserAsAuthorMessageContextMenu(
			@Qualifier(LocalBean.GUILD_USER_ROLES_SERVICE)
			IGuildUserRolesService guildUserRolesService
	) {
		super(guildUserRolesService);
	}

	@Override
	public CommandData initDiscordContextMenuData() {
		return Commands.message(NAME)
				.setDefaultPermissions(DEFAULT_MEMBER_PERMISSIONS);
	}
}

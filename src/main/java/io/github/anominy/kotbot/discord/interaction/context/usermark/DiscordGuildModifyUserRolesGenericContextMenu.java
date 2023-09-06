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
import io.github.anominy.jda.prelude.interaction.context.IDiscordGenericContextMenu;
import io.github.anominy.kotbot.discord.util.UDiscordContext;
import io.github.anominy.kotbot.dto.GuildUserRolesModifyInput;
import io.github.anominy.kotbot.entity.EPersonRole;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.GenericContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;

import java.util.Set;
import java.util.function.BiFunction;

abstract sealed class DiscordGuildModifyUserRolesGenericContextMenu implements IDiscordGenericContextMenu
		permits DiscordGuildMarkUserAsRoleGenericContextMenu, DiscordGuildUnmarkUserAsRoleGenericContextMenu {
	protected static final DefaultMemberPermissions DEFAULT_MEMBER_PERMISSIONS
			= DefaultMemberPermissions.enabledFor(Permission.MANAGE_SERVER, Permission.MANAGE_ROLES);

	private final IGuildUserRolesService guildUserRolesService;
	private final String actionName;
	private final String prepName;
	private final String pastActionName;
	private final EPersonRole roleType;
	private final Boolean isReverseFilter;
	private final BiFunction<Set<EPersonRole>, EPersonRole, Boolean> roleTypesModFunc;

	protected DiscordGuildModifyUserRolesGenericContextMenu(
			IGuildUserRolesService guildUserRolesService,
			String actionName,
			String prepName,
			EPersonRole roleType,
			Boolean isReverseFilter,
			BiFunction<Set<EPersonRole>, EPersonRole, Boolean> roleTypesModFunc
	) {
		if (guildUserRolesService == null) {
			throw new IllegalArgumentException("Guild user roles service mustn't be <null>");
		}

		if (actionName == null || actionName.isBlank()) {
			throw new IllegalArgumentException("Action name mustn't be <null/blank>");
		}

		if (prepName == null || prepName.isBlank()) {
			throw new IllegalArgumentException("Prep name mustn't be <null/blank>");
		}

		if (roleType == null) {
			throw new IllegalArgumentException("Role type mustn't be <null>");
		}

		if (isReverseFilter == null) {
			throw new IllegalArgumentException("Is reverse filter mustn't be <null>");
		}

		if (roleTypesModFunc == null) {
			throw new IllegalArgumentException("Role types modification function mustn't be <null>");
		}

		this.guildUserRolesService = guildUserRolesService;
		this.actionName = actionName;
		this.prepName = prepName;

		this.pastActionName = this.actionName.replaceAll("e$", "")
				+ "ed";

		this.roleType = roleType;
		this.isReverseFilter = isReverseFilter;
		this.roleTypesModFunc = roleTypesModFunc;
	}

	@Override
	public final void onDiscordGenericContextInteractionEvent(GenericContextInteractionEvent<?> event) {
		event.deferReply(true)
				.queue(hook -> {
					Guild guild = event.getGuild();

					User user = UDiscordContext.getTargetUser(event);

					GuildUserRolesModifyInput input = new GuildUserRolesModifyInput.Builder()
							.setGuild(guild)
							.setUser(user)
							.setRoleType(this.roleType)
							.setActionName(this.actionName)
							.setPrepName(this.prepName)
							.setPastActionName(this.pastActionName)
							.setIsReverseFilter(this.isReverseFilter)
							.setRoleTypesModFunc(this.roleTypesModFunc)
							.build();

					hook.sendMessage(this.guildUserRolesService.modifyGuildUserRoles(input))
							.queue();
				});
	}

	@Override
	public final void onDiscordMessageContextInteractionEvent(MessageContextInteractionEvent event) {
		IDiscordGenericContextMenu.super.onDiscordMessageContextInteractionEvent(event);
	}

	@Override
	public final void onDiscordUserContextInteractionEvent(UserContextInteractionEvent event) {
		IDiscordGenericContextMenu.super.onDiscordUserContextInteractionEvent(event);
	}
}

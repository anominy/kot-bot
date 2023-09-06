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

package io.github.anominy.kotbot.service;

import io.github.anominy.kotbot.service.api.IConfigService;
import io.github.anominy.kotbot.service.api.IGuildJoinRolesService;
import io.github.anominy.kotbot.bean.LocalBean;
import io.github.anominy.kotbot.dto.GuildJoinRolesModifyInput;
import io.github.anominy.kotbot.discord.util.UDiscordString;
import io.github.anominy.kotbot.entity.ConfigEntity;
import io.github.anominy.uwutils.UwObject;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.BiFunction;

@Service(LocalBean.GUILD_JOIN_ROLES_SERVICE)
public final class GuildJoinRolesService implements IGuildJoinRolesService {
	private final IConfigService configService;

	public GuildJoinRolesService(IConfigService configService) {
		this.configService = configService;
	}

	@Override
	public void addGuildJoinRolesToMember(Guild guild, Member member) {
		long guildId = guild.getIdLong();

		ConfigEntity config = this.configService.findWithJoinRoleIdsByGuildId(guildId);
		if (config == null) {
			return;
		}

		Set<Long> joinRoleIds = config.getJoinRoleIds();
		if (joinRoleIds == null) {
			return;
		}

		boolean isModified = false;
		for (Long joinRoleId : joinRoleIds) {
			Role joinRole = guild.getRoleById(joinRoleId);

			if (joinRole == null) {
				isModified |= joinRoleIds.remove(joinRoleId);
				continue;
			}

			try {
				guild.addRoleToMember(member, joinRole)
						.queue();
			} catch (InsufficientPermissionException
					| HierarchyException ignored) {
			}
		}

		if (isModified) {
			this.configService.save(config);
		}
	}

	@Override
	public String modifyGuildJoinRoles(GuildJoinRolesModifyInput input) {
		String actionName = input.getActionName();
		String prepName = input.getPrepName();
		String pastActionName = input.getPastActionName();

		Guild guild = input.getGuild();
		if (guild == null) {
			return UDiscordString.CANT_S_JOIN_ROLES_S_NULL_GUILD(actionName, prepName);
		}

		Role joinRole = input.getJoinRole();
		if (joinRole == null) {
			return UDiscordString.CANT_S_NULL_ROLE_S_GUILD_JOIN_ROLES(actionName, prepName);
		}

		if (joinRole.isPublicRole()) {
			return UDiscordString.CANT_S_PUBLIC_ROLE_S_GUILD_JOIN_ROLES(actionName, prepName);
		}

		Member selfMember = guild.getMember(input.getDiscordApi()
				.getSelfUser());

		if (selfMember == null || !selfMember.canInteract(joinRole)) {
			return UDiscordString.CANT_S_UNMANAGEABLE_ROLE_S_GUILD_JOIN_ROLES(actionName, prepName);
		}

		long guildId = guild.getIdLong();

		ConfigEntity config = this.configService.findWithJoinRoleIdsByGuildId(guildId);
		if (config == null) {
			config = new ConfigEntity()
					.setGuildId(guildId);
		}

		Set<Long> joinRoleIds = UwObject.ifNull(config.getJoinRoleIds(), HashSet::new);

		long joinRoleId = joinRole.getIdLong();
		String joinRoleMention = joinRole.getAsMention();

		boolean bContains = joinRoleIds.contains(joinRoleId);
		boolean bReverseFilter = input.getIsReverseFilter();

		if (bContains == !bReverseFilter) {
			return UDiscordString.S_ALREADY_S_S_GUILD_JOIN_ROLES(joinRoleMention, pastActionName, prepName);
		}

		BiFunction<Set<Long>, Long, Boolean> joinRolesModFunc = input.getJoinRolesModFunc();
		if (!joinRolesModFunc.apply(joinRoleIds, joinRoleId)) {
			return UDiscordString.COULDNT_S_S_S_GUILD_JOIN_ROLES(actionName, joinRoleMention, prepName);
		}

		this.configService.save(config.setJoinRoleIds(joinRoleIds));

		return UDiscordString.S_SUCCESSFULLY_S_S_GUILD_JOIN_ROLES(joinRoleMention, pastActionName, prepName);
	}

	@Override
	public String listAllGuildJoinRoles(Guild guild) {
		if (guild == null) {
			return UDiscordString.CANT_LOOKUP_JOIN_ROLES_FOR_NULL_GUILD;
		}

		long guildId = guild.getIdLong();

		ConfigEntity config = this.configService.findWithJoinRoleIdsByGuildId(guildId);
		if (config == null) {
			return UDiscordString.NO_CONFIG_FOR_GUILD;
		}

		Set<Long> joinRoleIds = config.getJoinRoleIds();
		if (joinRoleIds == null || joinRoleIds.isEmpty()) {
			return UDiscordString.NO_JOIN_ROLES_FOR_GUILD;
		}

		StringJoiner output = new StringJoiner(", ");

		boolean isModified = false;
		for (Long joinRoleId : joinRoleIds) {
			Role joinRole = guild.getRoleById(joinRoleId);

			if (joinRole == null) {
				isModified |= joinRoleIds.remove(joinRoleId);
				continue;
			}

			output.add(joinRole.getAsMention());
		}

		if (isModified) {
			this.configService.save(config);
		}

		return output.toString();
	}
}

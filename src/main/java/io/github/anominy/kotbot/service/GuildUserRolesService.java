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

import io.github.anominy.kotbot.service.api.IGuildUserRolesService;
import io.github.anominy.kotbot.service.api.IPersonService;
import io.github.anominy.kotbot.bean.LocalBean;
import io.github.anominy.kotbot.discord.util.UDiscordString;
import io.github.anominy.kotbot.discord.util.UDiscordUser;
import io.github.anominy.kotbot.dto.GuildUserRolesModifyInput;
import io.github.anominy.kotbot.entity.EPersonRole;
import io.github.anominy.kotbot.entity.PersonEntity;
import io.github.anominy.kotbot.entity.PersonInfoEntity;
import io.github.anominy.kotbot.service.api.IPersonInfoService;
import io.github.anominy.uwutils.UwObject;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

@Service(LocalBean.GUILD_USER_ROLES_SERVICE)
public final class GuildUserRolesService implements IGuildUserRolesService {
	private final IPersonService personService;
	private final IPersonInfoService personInfoService;

	public GuildUserRolesService(
			@Qualifier(LocalBean.PERSON_SERVICE)
			IPersonService personService,

			@Qualifier(LocalBean.PERSON_INFO_SERVICE)
			IPersonInfoService personInfoService
	) {
		this.personService = personService;
		this.personInfoService = personInfoService;
	}

	@Override
	public String modifyGuildUserRoles(GuildUserRolesModifyInput input) {
		EPersonRole roleType = input.getRoleType();

		String roleName = roleType.getName();
		String actionName = input.getActionName();
		String prepName = input.getPrepName();
		String pastActionName = input.getPastActionName();

		Guild guild = input.getGuild();
		if (guild == null) {
			return UDiscordString.CANT_S_USER_ROLES_S_NULL_GUILD(actionName, prepName);
		}

		User user = input.getUser();
		if (user == null) {
			return UDiscordString.CANT_S_S_ROLE_NULL_USER(actionName, roleName, prepName);
		}

		if (!UDiscordUser.isPerson(user)) {
			return UDiscordString.CANT_S_S_ROLE_NON_USER(actionName, roleName, prepName);
		}

		PersonEntity person = this.personService.findOrCreateIfNotExistsByUserId(
				user.getIdLong());

		PersonInfoEntity personInfo = this.personInfoService.findWithRoleTypesOrCreateIfNotExistsByPersonIdAndGuildId(
				person.getId(), guild.getIdLong());

		Set<EPersonRole> roleTypes = UwObject.ifNull(personInfo.getRoleTypes(), HashSet::new);

		boolean bContains = roleTypes.contains(roleType);
		boolean bReverseFilter = input.getIsReverseFilter();

		String userMention = user.getAsMention();

		if (bContains == !bReverseFilter) {
			return UDiscordString.S_ALREADY_S_S_S(userMention, pastActionName, prepName, roleName);
		}

		BiFunction<Set<EPersonRole>, EPersonRole, Boolean> roleTypesModFunc = input.getRoleTypesModFunc();
		if (!roleTypesModFunc.apply(roleTypes, roleType)) {
			return UDiscordString.COULDNT_S_S_S_S(actionName, userMention, prepName, roleName);
		}

		this.personInfoService.save(personInfo);

		return UDiscordString.S_SUCCESSFULLY_S_S_S(userMention, pastActionName, prepName, roleName);
	}
}

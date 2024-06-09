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

package io.github.anominy.kotbot.dto;

import io.github.anominy.kotbot.entity.EPersonRole;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

import java.util.Set;
import java.util.function.BiFunction;

public final class GuildUserRolesModifyInput {
	private final Guild guild;
	private final User user;
	private final EPersonRole roleType;
	private final String actionName;
	private final String prepName;
	private final String pastActionName;
	private final Boolean isReverseFilter;
	private final BiFunction<Set<EPersonRole>, EPersonRole, Boolean> roleTypesModFunc;

	private GuildUserRolesModifyInput(
			Guild guild,
			User user,
			EPersonRole roleType,
			String actionName,
			String prepName,
			String pastActionName,
			Boolean isReverseFilter,
			BiFunction<Set<EPersonRole>, EPersonRole, Boolean> roleTypesModFunc
	) {
		this.guild = guild;
		this.user = user;
		this.roleType = roleType;
		this.actionName = actionName;
		this.prepName = prepName;
		this.pastActionName = pastActionName;
		this.isReverseFilter = isReverseFilter;
		this.roleTypesModFunc = roleTypesModFunc;
	}

	public Guild getGuild() {
		return this.guild;
	}

	public User getUser() {
		return this.user;
	}

	public EPersonRole getRoleType() {
		return this.roleType;
	}

	public String getActionName() {
		return this.actionName;
	}

	public String getPrepName() {
		return this.prepName;
	}

	public String getPastActionName() {
		return this.pastActionName;
	}

	public boolean getIsReverseFilter() {
		return this.isReverseFilter;
	}

	public BiFunction<Set<EPersonRole>, EPersonRole, Boolean> getRoleTypesModFunc() {
		return this.roleTypesModFunc;
	}

	public static final class Builder {
		private Guild guild;
		private User user;
		private EPersonRole roleType;
		private String actionName;
		private String prepName;
		private String pastActionName;
		private Boolean isReverseFilter;
		private BiFunction<Set<EPersonRole>, EPersonRole, Boolean> roleTypesModFunc;

		public Builder() {
			this.guild = null;
			this.user = null;
			this.roleType = null;
			this.actionName = null;
			this.prepName = null;
			this.pastActionName = null;
			this.isReverseFilter = null;
			this.roleTypesModFunc = null;
		}

		public GuildUserRolesModifyInput build() {
			return new GuildUserRolesModifyInput(
					this.guild,
					this.user,
					this.roleType,
					this.actionName,
					this.prepName,
					this.pastActionName,
					this.isReverseFilter,
					this.roleTypesModFunc
			);
		}

		public Builder setGuild(Guild guild) {
			this.guild = guild;
			return this;
		}

		public Builder setUser(User user) {
			this.user = user;
			return this;
		}

		public Builder setRoleType(EPersonRole roleType) {
			this.roleType = roleType;
			return this;
		}

		public Builder setActionName(String actionName) {
			this.actionName = actionName;
			return this;
		}

		public Builder setPrepName(String prepName) {
			this.prepName = prepName;
			return this;
		}

		public Builder setPastActionName(String pastActionName) {
			this.pastActionName = pastActionName;
			return this;
		}

		public Builder setIsReverseFilter(Boolean isReverseFilter) {
			this.isReverseFilter = isReverseFilter;
			return this;
		}

		public Builder setRoleTypesModFunc(BiFunction<Set<EPersonRole>, EPersonRole, Boolean> roleTypesModFunc) {
			this.roleTypesModFunc = roleTypesModFunc;
			return this;
		}
	}
}

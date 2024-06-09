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

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;

import java.util.Set;
import java.util.function.BiFunction;

public final class GuildJoinRolesModifyInput {
	private final JDA discordApi;
	private final Guild guild;
	private final Role joinRole;
	private final String actionName;
	private final String prepName;
	private final String pastActionName;
	private final Boolean isReverseFilter;
	private final BiFunction<Set<Long>, Long, Boolean> joinRolesModFunc;

	private GuildJoinRolesModifyInput(
			JDA discordApi,
			Guild guild,
			Role joinRole,
			String actionName,
			String prepName,
			String pastActionName,
			Boolean isReverseFilter,
			BiFunction<Set<Long>, Long, Boolean> joinRolesModFunc
	) {
		this.discordApi = discordApi;
		this.guild = guild;
		this.joinRole = joinRole;
		this.actionName = actionName;
		this.prepName = prepName;
		this.pastActionName = pastActionName;
		this.isReverseFilter = isReverseFilter;
		this.joinRolesModFunc = joinRolesModFunc;
	}

	public JDA getDiscordApi() {
		return this.discordApi;
	}

	public Guild getGuild() {
		return this.guild;
	}

	public Role getJoinRole() {
		return this.joinRole;
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

	public Boolean getIsReverseFilter() {
		return this.isReverseFilter;
	}

	public BiFunction<Set<Long>, Long, Boolean> getJoinRolesModFunc() {
		return this.joinRolesModFunc;
	}

	public static final class Builder {
		private JDA discordApi;
		private Guild guild;
		private Role joinRole;
		private String actionName;
		private String prepName;
		private String pastActionName;
		private Boolean isReverseFilter;
		private BiFunction<Set<Long>, Long, Boolean> joinRolesModFunc;

		public Builder() {
			this.discordApi = null;
			this.guild = null;
			this.joinRole = null;
			this.actionName = null;
			this.prepName = null;
			this.pastActionName = null;
			this.isReverseFilter = null;
			this.joinRolesModFunc = null;
		}

		public GuildJoinRolesModifyInput build() {
			return new GuildJoinRolesModifyInput(
					this.discordApi,
					this.guild,
					this.joinRole,
					this.actionName,
					this.prepName,
					this.pastActionName,
					this.isReverseFilter,
					this.joinRolesModFunc
			);
		}

		public Builder setDiscordApi(JDA discordApi) {
			this.discordApi = discordApi;
			return this;
		}

		public Builder setGuild(Guild guild) {
			this.guild = guild;
			return this;
		}

		public Builder setJoinRole(Role joinRole) {
			this.joinRole = joinRole;
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

		public Builder setJoinRolesModFunc(BiFunction<Set<Long>, Long, Boolean> joinRolesModFunc) {
			this.joinRolesModFunc = joinRolesModFunc;
			return this;
		}
	}
}

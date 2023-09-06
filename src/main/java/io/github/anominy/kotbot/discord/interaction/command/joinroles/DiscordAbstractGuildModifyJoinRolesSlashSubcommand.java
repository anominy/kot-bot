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
import io.github.anominy.jda.prelude.interaction.command.IDiscordSlashCommandOption;
import io.github.anominy.jda.prelude.interaction.command.IDiscordSlashSubcommand;
import io.github.anominy.kotbot.dto.GuildJoinRolesModifyInput;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.Set;
import java.util.function.BiFunction;

abstract sealed class DiscordAbstractGuildModifyJoinRolesSlashSubcommand implements IDiscordSlashSubcommand
		permits DiscordGuildAddJoinRoleSlashSubcommand, DiscordGuildRemoveJoinRoleSlashSubcommand {
	private final IGuildJoinRolesService guildJoinRolesService;
	private final String actionName;
	private final String prepName;
	private final String pastActionName;
	private final Boolean isReverseFilter;
	private final BiFunction<Set<Long>, Long, Boolean> joinRolesModFunc;

	protected DiscordAbstractGuildModifyJoinRolesSlashSubcommand(
			IGuildJoinRolesService guildJoinRolesService,
			String actionName,
			String prepName,
			Boolean isReverseFilter,
			BiFunction<Set<Long>, Long, Boolean> joinRolesModFunc
	) {
		if (guildJoinRolesService == null) {
			throw new IllegalArgumentException("Guild join roles service mustn't be <null>");
		}

		if (actionName == null || actionName.isBlank()) {
			throw new IllegalArgumentException("Action name mustn't be <null/blank>");
		}

		if (prepName == null || prepName.isBlank()) {
			throw new IllegalArgumentException("Prep name mustn't be <null/blank>");
		}

		if (isReverseFilter == null) {
			throw new IllegalArgumentException("Is reverse filter mustn't be <null>");
		}

		if (joinRolesModFunc == null) {
			throw new IllegalArgumentException("Join roles modifier function mustn't be <null>");
		}

		this.guildJoinRolesService = guildJoinRolesService;
		this.actionName = actionName;
		this.prepName = prepName;

		this.pastActionName = this.actionName.replaceAll("e$", "")
				+ "ed";

		this.isReverseFilter = isReverseFilter;
		this.joinRolesModFunc = joinRolesModFunc;
	}

	@Override
	public final IDiscordSlashCommandOption[] getDiscordSlashCommandOptionArray() {
		return new IDiscordSlashCommandOption[] {
				new DiscordGuildJoinRoleSlashCommandOption()
		};
	}

	@Override
	public final void onDiscordSlashCommandInteractionEvent(SlashCommandInteractionEvent event) {
		event.deferReply(true)
				.queue(hook -> {
					JDA discordApi = event.getJDA();

					Guild guild = event.getGuild();

					Role joinRole = event.getOption(DiscordGuildJoinRoleSlashCommandOption.NAME,
							null, OptionMapping::getAsRole);

					GuildJoinRolesModifyInput input = new GuildJoinRolesModifyInput.Builder()
							.setDiscordApi(discordApi)
							.setGuild(guild)
							.setJoinRole(joinRole)
							.setActionName(this.actionName)
							.setPrepName(this.prepName)
							.setPastActionName(this.pastActionName)
							.setIsReverseFilter(this.isReverseFilter)
							.setJoinRolesModFunc(this.joinRolesModFunc)
							.build();

					hook.sendMessage(this.guildJoinRolesService.modifyGuildJoinRoles(input))
							.queue();
				});
	}
}

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

package io.github.anominy.kotbot.discord.event;

import io.github.anominy.kotbot.discord.util.UDiscordUser;
import io.github.anominy.kotbot.service.api.IGuildJoinRolesService;
import io.github.anominy.kotbot.bean.LocalBean;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public final class DiscordGuildMemberJoinEventListener {
	private final IGuildJoinRolesService guildJoinRolesService;

	public DiscordGuildMemberJoinEventListener(
			@Qualifier(LocalBean.GUILD_JOIN_ROLES_SERVICE)
			IGuildJoinRolesService guildJoinRolesService
	) {
		this.guildJoinRolesService = guildJoinRolesService;
	}

	@EventListener(GuildMemberJoinEvent.class)
	public void onMemberGuildJoinEvent(GuildMemberJoinEvent event) {
		User user = event.getUser();
		if (!UDiscordUser.isPerson(user)) {
			return;
		}

		Guild guild = event.getGuild();
		Member member = event.getMember();

		this.guildJoinRolesService.addGuildJoinRolesToMember(guild, member);
	}
}

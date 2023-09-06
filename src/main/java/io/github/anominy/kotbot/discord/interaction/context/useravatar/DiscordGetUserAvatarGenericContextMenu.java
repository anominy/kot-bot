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

package io.github.anominy.kotbot.discord.interaction.context.useravatar;

import io.github.anominy.jda.prelude.interaction.context.IDiscordGenericContextMenu;
import io.github.anominy.kotbot.discord.util.UDiscordContext;
import io.github.anominy.kotbot.discord.util.UDiscordString;
import io.github.anominy.uwutils.UwObject;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.GenericContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;

abstract sealed class DiscordGetUserAvatarGenericContextMenu implements IDiscordGenericContextMenu
		permits DiscordGetUserAvatarUserContextMenu, DiscordGetUserAvatarMessageContextMenu  {
	protected static final String NAME = "Get User Avatar";

	protected DiscordGetUserAvatarGenericContextMenu() {
	}

	@Override
	public void onDiscordGenericContextInteractionEvent(GenericContextInteractionEvent<?> event) {
		User user = UDiscordContext.getTargetUser(event);

		String content = UwObject.ifNotNull(user, User::getEffectiveAvatarUrl,
				UDiscordString.CANT_GET_AVATAR_FOR_NULL_USER);

		event.reply(content)
				.setEphemeral(true)
				.queue();
	}

	@Override
	public void onDiscordMessageContextInteractionEvent(MessageContextInteractionEvent event) {
		IDiscordGenericContextMenu.super.onDiscordMessageContextInteractionEvent(event);
	}

	@Override
	public void onDiscordUserContextInteractionEvent(UserContextInteractionEvent event) {
		IDiscordGenericContextMenu.super.onDiscordUserContextInteractionEvent(event);
	}
}

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

package io.github.anominy.kotbot.discord.util;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.GenericContextInteractionEvent;

public final class UDiscordContext {

	public static User getTargetUser(GenericContextInteractionEvent<?> event) {
		Object targetObject = event.getTarget();

		User user = null;
		if (targetObject instanceof User targetUser) {
			user = targetUser;
		} else if (targetObject instanceof Message targetMessage) {
			user = targetMessage.getAuthor();
		}

		return user;
	}

	private UDiscordContext() {
		throw new UnsupportedOperationException();
	}
}

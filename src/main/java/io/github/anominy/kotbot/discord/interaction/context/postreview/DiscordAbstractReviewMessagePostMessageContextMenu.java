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

package io.github.anominy.kotbot.discord.interaction.context.postreview;

import io.github.anominy.kotbot.service.api.IGuildReviewService;
import io.github.anominy.jda.prelude.interaction.context.IDiscordMessageContextMenu;
import io.github.anominy.kotbot.dto.GuildReviewMessagePostInput;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

abstract sealed class DiscordAbstractReviewMessagePostMessageContextMenu implements IDiscordMessageContextMenu
		permits DiscordApproveMessagePostMessageContextMenu, DiscordDeclineMessagePostMessageContextMenu {
	private final String commandName;
	private final Boolean isApprove;
	private final IGuildReviewService guildReviewService;

	protected DiscordAbstractReviewMessagePostMessageContextMenu(
			String commandName, Boolean isApprove, IGuildReviewService guildReviewService) {
		if (commandName == null || commandName.isBlank()) {
			throw new IllegalArgumentException("Command name mustn't be <null/blank>");
		}

		if (isApprove == null) {
			throw new IllegalArgumentException("Is approve mustn't be <null>");
		}

		if (guildReviewService == null) {
			throw new IllegalArgumentException("Guild review service mustn't be <null>");
		}

		this.commandName = commandName;
		this.isApprove = isApprove;
		this.guildReviewService = guildReviewService;
	}

	@Override
	public final CommandData initDiscordContextMenuData() {
		return Commands.message(this.commandName);
	}

	@Override
	public final void onDiscordMessageContextInteractionEvent(MessageContextInteractionEvent event) {
		event.deferReply(true)
				.queue(hook -> {
					JDA discordApi = event.getJDA();

					Guild guild = event.getGuild();

					User user = event.getUser();

					MessageChannel channel = event.getChannel();

					Message message = event.getTarget();

					GuildReviewMessagePostInput input = new GuildReviewMessagePostInput.Builder()
							.setDiscordApi(discordApi)
							.setGuild(guild)
							.setUser(user)
							.setChannel(channel)
							.setMessage(message)
							.setIsApprove(this.isApprove)
							.build();

					hook.sendMessage(this.guildReviewService.reviewGuildMessagePost(input))
							.queue();
				});
	}

	@Override
	public final void onDiscordUserContextInteractionEvent(UserContextInteractionEvent event) {
		IDiscordMessageContextMenu.super.onDiscordUserContextInteractionEvent(event);
	}
}

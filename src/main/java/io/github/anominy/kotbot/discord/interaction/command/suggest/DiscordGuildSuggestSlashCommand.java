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

package io.github.anominy.kotbot.discord.interaction.command.suggest;

import io.github.anominy.kotbot.service.api.IGuildSuggestService;
import io.github.anominy.jda.prelude.interaction.command.IDiscordSlashCommand;
import io.github.anominy.jda.prelude.interaction.command.IDiscordSlashCommandOption;
import io.github.anominy.kotbot.bean.LocalBean;
import io.github.anominy.kotbot.dto.GuildSuggestMessagePostInput;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public final class DiscordGuildSuggestSlashCommand implements IDiscordSlashCommand {
	private final IGuildSuggestService guildSuggestService;

	public DiscordGuildSuggestSlashCommand(
			@Qualifier(LocalBean.GUILD_SUGGEST_SERVICE)
			IGuildSuggestService guildSuggestService
	) {
		this.guildSuggestService = guildSuggestService;
	}


	@Override
	public SlashCommandData initDiscordSlashCommandData() {
		return Commands.slash("suggest", "Suggest!");
	}

	@Override
	public IDiscordSlashCommandOption[] getDiscordSlashCommandOptionArray() {
		return new IDiscordSlashCommandOption[] {
				new DiscordGuildSuggestAttachmentSlashCommandOption(),
				new DiscordGuildSuggestContentSlashCommandOption(),
				new DiscordGuildSuggestCoAuthorSlashCommandOption()
		};
	}

	@Override
	public void onDiscordSlashCommandInteractionEvent(SlashCommandInteractionEvent event) {
		event.deferReply(true)
				.queue(hook -> {
					JDA discordApi = event.getJDA();

					Guild guild = event.getGuild();

					Message.Attachment attachment = event.getOption(DiscordGuildSuggestAttachmentSlashCommandOption.NAME,
							null, OptionMapping::getAsAttachment);

					String content = event.getOption(DiscordGuildSuggestContentSlashCommandOption.NAME,
							null, OptionMapping::getAsString);

					User author = event.getUser();

					User coAuthor = event.getOption(DiscordGuildSuggestCoAuthorSlashCommandOption.NAME,
							null, OptionMapping::getAsUser);

					GuildSuggestMessagePostInput input = new GuildSuggestMessagePostInput.Builder()
							.setDiscordApi(discordApi)
							.setGuild(guild)
							.setAttachment(attachment)
							.setContent(content)
							.setAuthor(author)
							.setCoAuthor(coAuthor)
							.build();

					hook.sendMessage(this.guildSuggestService.suggestGuildMessagePost(input))
							.queue();
				});
	}
}

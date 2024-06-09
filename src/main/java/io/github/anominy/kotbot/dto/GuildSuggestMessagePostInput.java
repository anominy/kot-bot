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
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

@SuppressWarnings("ClassCanBeRecord")
public final class GuildSuggestMessagePostInput {
	private final JDA discordApi;
	private final Guild guild;
	private final Message.Attachment attachment;
	private final String content;
	private final User author;
	private final User coAuthor;

	public GuildSuggestMessagePostInput(
			JDA discordApi,
			Guild guild,
			Message.Attachment attachment,
			String content,
			User author,
			User coAuthor
	) {
		this.discordApi = discordApi;
		this.guild = guild;
		this.attachment = attachment;
		this.content = content;
		this.author = author;
		this.coAuthor = coAuthor;
	}

	public JDA getDiscordApi() {
		return this.discordApi;
	}

	public Guild getGuild() {
		return this.guild;
	}

	public Message.Attachment getAttachment() {
		return this.attachment;
	}

	public String getContent() {
		return this.content;
	}

	public User getAuthor() {
		return this.author;
	}

	public User getCoAuthor() {
		return this.coAuthor;
	}

	public static final class Builder {
		private JDA discordApi;
		private Guild guild;
		private Message.Attachment attachment;
		private String content;
		private User author;
		private User coAuthor;

		public Builder() {
			this.discordApi = null;
			this.guild = null;
			this.attachment = null;
			this.content = null;
			this.author = null;
			this.coAuthor = null;
		}

		public GuildSuggestMessagePostInput build() {
			return new GuildSuggestMessagePostInput(
					this.discordApi,
					this.guild,
					this.attachment,
					this.content,
					this.author,
					this.coAuthor
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

		public Builder setAttachment(Message.Attachment attachment) {
			this.attachment = attachment;
			return this;
		}

		public Builder setContent(String content) {
			this.content = content;
			return this;
		}

		public Builder setAuthor(User author) {
			this.author = author;
			return this;
		}

		public Builder setCoAuthor(User coAuthor) {
			this.coAuthor = coAuthor;
			return this;
		}
	}
}

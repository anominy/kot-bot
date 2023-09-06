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
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public final class GuildReviewMessagePostInput {
	private final JDA discordApi;
	private final Guild guild;
	private final User user;
	private final MessageChannel channel;
	private final Message message;
	private final Boolean isApprove;

	private GuildReviewMessagePostInput(
			JDA discordApi,
			Guild guild,
			User user,
			MessageChannel channel,
			Message message,
			Boolean isApprove
	) {
		this.discordApi = discordApi;
		this.guild = guild;
		this.user = user;
		this.channel = channel;
		this.message = message;
		this.isApprove = isApprove;
	}

	public JDA getDiscordApi() {
		return this.discordApi;
	}

	public Guild getGuild() {
		return this.guild;
	}

	public User getUser() {
		return this.user;
	}

	public MessageChannel getChannel() {
		return this.channel;
	}

	public Message getMessage() {
		return this.message;
	}

	public Boolean getIsApprove() {
		return this.isApprove;
	}

	public static final class Builder {
		private JDA discordApi;
		private Guild guild;
		private User user;
		private MessageChannel channel;
		private Message message;
		private Boolean isApprove;

		public Builder() {
			this.discordApi = null;
			this.guild = null;
			this.user = null;
			this.channel = null;
			this.message = null;
			this.isApprove = null;
		}

		public GuildReviewMessagePostInput build() {
			return new GuildReviewMessagePostInput(
					this.discordApi,
					this.guild,
					this.user,
					this.channel,
					this.message,
					this.isApprove
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

		public Builder setUser(User user) {
			this.user = user;
			return this;
		}

		public Builder setChannel(MessageChannel channel) {
			this.channel = channel;
			return this;
		}

		public Builder setMessage(Message message) {
			this.message = message;
			return this;
		}

		public Builder setIsApprove(Boolean isApprove) {
			this.isApprove = isApprove;
			return this;
		}
	}
}

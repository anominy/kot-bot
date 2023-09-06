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

import io.github.anominy.kotbot.entity.PostEntity;
import io.github.anominy.kotbot.entity.WebhookEntity;
import io.github.anominy.kotbot.service.api.IWebhookService;
import net.dv8tion.jda.api.JDA;

public final class GuildWebhookCreateMessagePostInput {
	private final JDA discordApi;
	private final PostEntity post;
	private final IWebhookService<? extends WebhookEntity<?>> webhookService;

	private GuildWebhookCreateMessagePostInput(
			JDA discordApi,
			PostEntity post,
			IWebhookService<? extends WebhookEntity<?>> webhookService
	) {
		this.discordApi = discordApi;
		this.post = post;
		this.webhookService = webhookService;
	}

	public JDA getDiscordApi() {
		return this.discordApi;
	}

	public PostEntity getPost() {
		return this.post;
	}

	public IWebhookService<? extends WebhookEntity<?>> getWebhookService() {
		return this.webhookService;
	}

	public static final class Builder {
		private JDA discordApi;
		private PostEntity post;
		private IWebhookService<? extends WebhookEntity<?>> webhookService;

		public Builder() {
			this.discordApi = null;
			this.post = null;
			this.webhookService = null;
		}

		public GuildWebhookCreateMessagePostInput build() {
			return new GuildWebhookCreateMessagePostInput(
					this.discordApi,
					this.post,
					this.webhookService
			);
		}

		public Builder setDiscordApi(JDA discordApi) {
			this.discordApi = discordApi;
			return this;
		}

		public Builder setPost(PostEntity post) {
			this.post = post;
			return this;
		}

		public Builder setWebhookService(IWebhookService<? extends WebhookEntity<?>> webhookService) {
			this.webhookService = webhookService;
			return this;
		}
	}
}

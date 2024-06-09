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

import io.github.anominy.kotbot.entity.ConfigEntity;
import io.github.anominy.kotbot.entity.WebhookEntity;
import io.github.anominy.kotbot.service.api.IWebhookService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public final class GuildWebhookSubscribeToChannelInput<T extends WebhookEntity<?>, U extends IWebhookService<T>> {
	private final JDA discordApi;
	private final Guild guild;
	private final GuildChannel channel;
	private final String webhookName;
	private final U webhookService;
	private final Function<ConfigEntity, T> webhookGetterFunc;
	private final BiFunction<ConfigEntity, T, ?> webhookSetterFunc;
	private final Supplier<T> webhookCreateFunc;

	private GuildWebhookSubscribeToChannelInput(
			JDA discordApi,
			Guild guild,
			GuildChannel channel,
			String webhookName,
			U webhookService,
			Function<ConfigEntity, T> webhookGetterFunc,
			BiFunction<ConfigEntity, T, ?> webhookSetterFunc,
			Supplier<T> webhookCreateFunc
	) {
		this.discordApi = discordApi;
		this.guild = guild;
		this.channel = channel;
		this.webhookName = webhookName;
		this.webhookService = webhookService;
		this.webhookGetterFunc = webhookGetterFunc;
		this.webhookSetterFunc = webhookSetterFunc;
		this.webhookCreateFunc = webhookCreateFunc;
	}

	public JDA getDiscordApi() {
		return this.discordApi;
	}

	public Guild getGuild() {
		return this.guild;
	}

	public GuildChannel getChannel() {
		return this.channel;
	}

	public String getWebhookName() {
		return this.webhookName;
	}

	public U getWebhookService() {
		return this.webhookService;
	}

	public Function<ConfigEntity, T> getWebhookGetterFunc() {
		return this.webhookGetterFunc;
	}

	public BiFunction<ConfigEntity, T, ?> getWebhookSetterFunc() {
		return this.webhookSetterFunc;
	}

	public Supplier<T> getWebhookCreateFunc() {
		return this.webhookCreateFunc;
	}

	public static final class Builder<T extends WebhookEntity<?>, U extends IWebhookService<T>> {
		private JDA discordApi;
		private Guild guild;
		private GuildChannel channel;
		private String webhookName;
		private U webhookService;
		private Function<ConfigEntity, T> webhookGetterFunc;
		private BiFunction<ConfigEntity, T, ?> webhookSetterFunc;
		private Supplier<T> webhookCreateFunc;

		public Builder() {
			this.discordApi = null;
			this.guild = null;
			this.channel = null;
			this.webhookName = null;
			this.webhookService = null;
			this.webhookGetterFunc = null;
			this.webhookSetterFunc = null;
			this.webhookCreateFunc = null;
		}

		public GuildWebhookSubscribeToChannelInput<T, U> build() {
			return new GuildWebhookSubscribeToChannelInput<>(
					this.discordApi,
					this.guild,
					this.channel,
					this.webhookName,
					this.webhookService,
					this.webhookGetterFunc,
					this.webhookSetterFunc,
					this.webhookCreateFunc
			);
		}

		public Builder<T, U> setDiscordApi(JDA discordApi) {
			this.discordApi = discordApi;
			return this;
		}

		public Builder<T, U> setGuild(Guild guild) {
			this.guild = guild;
			return this;
		}

		public Builder<T, U> setChannel(GuildChannel channel) {
			this.channel = channel;
			return this;
		}

		public Builder<T, U> setWebhookName(String webhookName) {
			this.webhookName = webhookName;
			return this;
		}

		public Builder<T, U> setWebhookService(U webhookService) {
			this.webhookService = webhookService;
			return this;
		}

		public Builder<T, U> setWebhookGetterFunc(Function<ConfigEntity, T> webhookGetterFunc) {
			this.webhookGetterFunc = webhookGetterFunc;
			return this;
		}

		public Builder<T, U> setWebhookSetterFunc(BiFunction<ConfigEntity, T, ?> webhookSetterFunc) {
			this.webhookSetterFunc = webhookSetterFunc;
			return this;
		}

		public Builder<T, U> setWebhookCreateFunc(Supplier<T> webhookCreateFunc) {
			this.webhookCreateFunc = webhookCreateFunc;
			return this;
		}
	}
}

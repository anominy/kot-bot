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
import net.dv8tion.jda.api.entities.Guild;

import java.util.function.Function;

public final class GuildWebhookFindChannelInput {
	private final Guild guild;
	private final String webhookName;
	private final Function<ConfigEntity, WebhookEntity<?>> webhookGetterFunc;

	private GuildWebhookFindChannelInput(
			Guild guild,
			String webhookName,
			Function<ConfigEntity, WebhookEntity<?>> webhookGetterFunc
	) {
		this.guild = guild;
		this.webhookName = webhookName;
		this.webhookGetterFunc = webhookGetterFunc;
	}

	public Guild getGuild() {
		return this.guild;
	}

	public String getWebhookName() {
		return this.webhookName;
	}

	public Function<ConfigEntity, WebhookEntity<?>> getWebhookGetterFunc() {
		return this.webhookGetterFunc;
	}

	public static final class Builder {
		private Guild guild;
		private String webhookName;
		private Function<ConfigEntity, WebhookEntity<?>> webhookGetterFunc;

		public Builder() {
			this.guild = null;
			this.webhookName = null;
			this.webhookGetterFunc = null;
		}

		public GuildWebhookFindChannelInput build() {
			return new GuildWebhookFindChannelInput(
					this.guild,
					this.webhookName,
					this.webhookGetterFunc
			);
		}

		public Builder setGuild(Guild guild) {
			this.guild = guild;
			return this;
		}

		public Builder setWebhookName(String webhookName) {
			this.webhookName = webhookName;
			return this;
		}

		public Builder setWebhookGetterFunc(Function<ConfigEntity, WebhookEntity<?>> webhookGetterFunc) {
			this.webhookGetterFunc = webhookGetterFunc;
			return this;
		}
	}
}

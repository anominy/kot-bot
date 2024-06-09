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

package io.github.anominy.kotbot.entity.util;

import io.github.anominy.kotbot.entity.WebhookEntity;

public final class UWebhookEntity {
	public static final String SIMPLE_NAME = WebhookEntity.class.getSimpleName();

	public static final String ATTRIBUTE_NAME_GUILD_ID = "guildId";
	public static final String ATTRIBUTE_NAME_CHANNEL_ID = "channelId";
	public static final String ATTRIBUTE_NAME_WEBHOOK_ID = "webhookId";
	public static final String ATTRIBUTE_NAME_WEBHOOK_TOKEN = "webhookToken";
	public static final String ATTRIBUTE_NAME_IS_ENABLED = "isEnabled";

	private UWebhookEntity() {
		throw new UnsupportedOperationException();
	}
}

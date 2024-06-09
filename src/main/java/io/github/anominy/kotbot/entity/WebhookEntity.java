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

package io.github.anominy.kotbot.entity;

import io.github.anominy.kotbot.entity.util.UObjectEntity;
import io.github.anominy.kotbot.entity.util.UWebhookEntity;
import io.github.anominy.kotbot.table.util.UWebhookTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@SuppressWarnings({"unused", "UnusedReturnValue"})

@Entity
@Table(name = UWebhookTable.NAME)
public class WebhookEntity<T extends WebhookEntity<T>> extends ObjectEntity<T> {

	@Column(name = UWebhookTable.COLUMN_NAME_GUILD_ID, nullable = false)
	protected Long guildId;

	@Column(name = UWebhookTable.COLUMN_NAME_CHANNEL_ID, nullable = false)
	protected Long channelId;

	@Column(name = UWebhookTable.COLUMN_NAME_WEBHOOK_ID, nullable = false, unique = true)
	protected Long webhookId;

	@Column(name = UWebhookTable.COLUMN_NAME_WEBHOOK_TOKEN, nullable = false, unique = true)
	protected String webhookToken;

	@Column(name = UWebhookTable.COLUMN_NAME_IS_ENABLED, nullable = false)
	protected Boolean isEnabled;

	public WebhookEntity() {
		this.isEnabled = UWebhookTable.COLUMN_DEFAULT_IS_ENABLED;
	}

	public Long getGuildId() {
		return this.guildId;
	}

	public Long getChannelId() {
		return this.channelId;
	}

	public Long getWebhookId() {
		return this.webhookId;
	}

	public String getWebhookToken() {
		return this.webhookToken;
	}

	public Boolean getIsEnabled() {
		return this.isEnabled;
	}

	public T setGuildId(Long guildId) {
		this.guildId = guildId;
		return this.asT;
	}

	public T setChannelId(Long channelId) {
		this.channelId = channelId;
		return this.asT;
	}

	public T setWebhookId(Long webhookId) {
		this.webhookId = webhookId;
		return this.asT;
	}

	public T setWebhookToken(String webhookToken) {
		this.webhookToken = webhookToken;
		return this.asT;
	}

	public T setIsEnabled(Boolean isEnabled) {
		if (isEnabled == null) {
			isEnabled = UWebhookTable.COLUMN_DEFAULT_IS_ENABLED;
		}

		this.isEnabled = isEnabled;
		return this.asT;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}

		WebhookEntity<?> that = (WebhookEntity<?>) obj;

		return Objects.equals(this.guildId, that.guildId)
				&& Objects.equals(this.channelId, that.channelId)
				&& Objects.equals(this.webhookId, that.webhookId)
				&& Objects.equals(this.webhookToken, that.webhookToken)
				&& Objects.equals(this.isEnabled, that.isEnabled);
	}

	@Override
	public int hashCode() {
		return Objects.hash(
				this.id,
				this.createdAt,
				this.updatedAt,
				this.guildId,
				this.channelId,
				this.webhookId,
				this.webhookToken,
				this.isEnabled
		);
	}

	@Override
	public String toString() {
		return UWebhookEntity.SIMPLE_NAME + "["
				+ UObjectEntity.ATTRIBUTE_NAME_ID + "=" + this.id
				+ ", " + UObjectEntity.ATTRIBUTE_NAME_CREATED_AT + "=" + this.createdAt
				+ ", " + UObjectEntity.ATTRIBUTE_NAME_UPDATED_AT + "=" + this.updatedAt
				+ ", " + UWebhookEntity.ATTRIBUTE_NAME_GUILD_ID + "=" + this.guildId
				+ ", " + UWebhookEntity.ATTRIBUTE_NAME_CHANNEL_ID + "=" + this.channelId
				+ ", " + UWebhookEntity.ATTRIBUTE_NAME_WEBHOOK_ID + "=" + this.webhookId
				+ ", " + UWebhookEntity.ATTRIBUTE_NAME_WEBHOOK_TOKEN + "=\"" + this.webhookToken + "\""
				+ ", " + UWebhookEntity.ATTRIBUTE_NAME_IS_ENABLED + "=" + this.isEnabled
				+ "]";
	}
}

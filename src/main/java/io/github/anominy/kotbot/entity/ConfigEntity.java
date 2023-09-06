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

import io.github.anominy.kotbot.entity.util.UConfigEntity;
import io.github.anominy.kotbot.entity.util.UObjectEntity;
import io.github.anominy.kotbot.table.util.UConfigJoinRoleTable;
import io.github.anominy.kotbot.table.util.UConfigTable;
import jakarta.persistence.*;
import org.springframework.data.jpa.repository.EntityGraph;

import java.lang.annotation.*;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings({"unused", "UnusedReturnValue"})

@Entity
@Table(name = UConfigTable.NAME)
public class ConfigEntity extends ObjectEntity<ConfigEntity> {

	@Column(name = UConfigTable.COLUMN_NAME_GUILD_ID, nullable = false, unique = true)
	protected Long guildId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = UConfigTable.COLUMN_NAME_SUGGEST_WEBHOOK_ID, unique = true)
	protected SuggestWebhookEntity suggestWebhook;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = UConfigTable.COLUMN_NAME_PUBLISH_WEBHOOK_ID, unique = true)
	protected PublishWebhookEntity publishWebhook;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(
			name = UConfigJoinRoleTable.NAME,
			joinColumns = @JoinColumn(name = UConfigJoinRoleTable.COLUMN_NAME_CONFIG_ID, nullable = false)
	)
	@Column(name = UConfigJoinRoleTable.COLUMN_NAME_ROLE_ID, nullable = false, unique = true)
	protected Set<Long> joinRoleIds;

	public ConfigEntity() {
	}

	public Long getGuildId() {
		return this.guildId;
	}

	public SuggestWebhookEntity getSuggestWebhook() {
		return this.suggestWebhook;
	}

	public PublishWebhookEntity getPublishWebhook() {
		return this.publishWebhook;
	}

	public Set<Long> getJoinRoleIds() {
		return this.joinRoleIds;
	}

	public ConfigEntity setGuildId(Long guildId) {
		this.guildId = guildId;
		return this;
	}

	public ConfigEntity setSuggestWebhook(SuggestWebhookEntity suggestWebhook) {
		this.suggestWebhook = suggestWebhook;
		return this;
	}

	public ConfigEntity setPublishWebhook(PublishWebhookEntity publishWebhook) {
		this.publishWebhook = publishWebhook;
		return this;
	}

	public ConfigEntity setJoinRoleIds(Set<Long> joinRoleIds) {
		this.joinRoleIds = joinRoleIds;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}

		ConfigEntity that = (ConfigEntity) obj;

		return Objects.equals(this.guildId, that.guildId)
				&& Objects.equals(this.suggestWebhook, that.suggestWebhook)
				&& Objects.equals(this.publishWebhook, that.publishWebhook)
				&& Objects.equals(this.joinRoleIds, that.joinRoleIds);
	}

	@Override
	public int hashCode() {
		return Objects.hash(
				this.id,
				this.createdAt,
				this.updatedAt,
				this.guildId,
				this.suggestWebhook,
				this.publishWebhook,
				this.joinRoleIds
		);
	}

	@Override
	public String toString() {
		return UConfigEntity.SIMPLE_NAME + "["
				+ UObjectEntity.ATTRIBUTE_NAME_ID + "=" + this.id
				+ ", " + UObjectEntity.ATTRIBUTE_NAME_CREATED_AT + "=" + this.createdAt
				+ ", " + UObjectEntity.ATTRIBUTE_NAME_UPDATED_AT + "=" + this.updatedAt
				+ ", " + UConfigEntity.ATTRIBUTE_NAME_GUILD_ID + "=" + this.guildId
				+ ", " + UConfigEntity.ATTRIBUTE_NAME_SUGGEST_WEBHOOK + "=" + this.suggestWebhook
				+ ", " + UConfigEntity.ATTRIBUTE_NAME_PUBLISH_WEBHOOK + "=" + this.publishWebhook
				+ ", " + UConfigEntity.ATTRIBUTE_NAME_JOIN_ROLE_IDS + "=" + this.joinRoleIds
				+ "]";
	}

	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
	@EntityGraph(attributePaths = {
			UConfigEntity.ATTRIBUTE_NAME_SUGGEST_WEBHOOK,
			UConfigEntity.ATTRIBUTE_NAME_PUBLISH_WEBHOOK
	})
	public @interface GraphWithWebhooks {
	}

	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
	@EntityGraph(attributePaths = UConfigEntity.ATTRIBUTE_NAME_JOIN_ROLE_IDS)
	public @interface GraphWithJoinRoleIds {
	}
}

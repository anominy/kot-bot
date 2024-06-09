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
import io.github.anominy.kotbot.table.util.UObjectTable;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Objects;

@SuppressWarnings({"unused", "unchecked", "UnusedReturnValue"})

@Entity
@Table(name = UObjectTable.NAME)
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners(AuditingEntityListener.class)
public class ObjectEntity<T extends ObjectEntity<T>> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = UObjectTable.COLUMN_NAME_ID, nullable = false, unique = true)
	protected Long id;

	@CreatedDate
	@Column(name = UObjectTable.COLUMN_NAME_CREATED_AT, nullable = false)
	protected Instant createdAt;

	@LastModifiedDate
	@Column(name = UObjectTable.COLUMN_NAME_UPDATED_AT, nullable = false)
	protected Instant updatedAt;

	@Transient
	protected T asT;

	public ObjectEntity() {
		this.asT = (T) this;
	}

	public Long getId() {
		return this.id;
	}

	public Instant getCreatedAt() {
		return this.createdAt;
	}

	public Instant getUpdatedAt() {
		return this.updatedAt;
	}

	public T setId(Long id) {
		this.id = id;
		return this.asT;
	}

	public T setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
		return this.asT;
	}

	public T setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
		return this.asT;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}

		ObjectEntity<?> that = (ObjectEntity<?>) obj;

		return Objects.equals(this.id, that.id)
				&& Objects.equals(this.createdAt, that.createdAt)
				&& Objects.equals(that.createdAt, that.updatedAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(
				this.id,
				this.createdAt,
				this.updatedAt
		);
	}

	@Override
	public String toString() {
		return UObjectEntity.SIMPLE_NAME + "["
				+ UObjectEntity.ATTRIBUTE_NAME_ID + "=" + this.id
				+ ", " + UObjectEntity.ATTRIBUTE_NAME_CREATED_AT + "=" + this.createdAt
				+ ", " + UObjectEntity.ATTRIBUTE_NAME_UPDATED_AT + "=" + this.updatedAt
				+ "]";
	}
}

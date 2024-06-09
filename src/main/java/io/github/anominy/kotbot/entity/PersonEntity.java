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
import io.github.anominy.kotbot.entity.util.UPersonEntity;
import io.github.anominy.kotbot.table.util.UPersonTable;
import jakarta.persistence.*;

import java.util.*;

@SuppressWarnings({"unused", "UnusedReturnValue"})

@Entity
@Table(name = UPersonTable.NAME)
public class PersonEntity extends ObjectEntity<PersonEntity> {

	@Column(name = UPersonTable.COLUMN_NAME_USER_ID, nullable = false, unique = true)
	protected Long userId;

	public PersonEntity() {
	}

	public Long getUserId() {
		return this.userId;
	}

	public PersonEntity setUserId(Long userId) {
		this.userId = userId;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}

		PersonEntity that = (PersonEntity) obj;

		return Objects.equals(this.userId, that.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(
				this.id,
				this.createdAt,
				this.updatedAt,
				this.userId
		);
	}

	@Override
	public String toString() {
		return UPersonEntity.SIMPLE_NAME + "["
				+ UObjectEntity.ATTRIBUTE_NAME_ID + "=" + this.id
				+ ", " + UObjectEntity.ATTRIBUTE_NAME_CREATED_AT + "=" + this.createdAt
				+ ", " + UObjectEntity.ATTRIBUTE_NAME_UPDATED_AT + "=" + this.updatedAt
				+ ", " + UPersonEntity.ATTRIBUTE_NAME_USER_ID + "=" + this.userId
				+ "]";
	}
}

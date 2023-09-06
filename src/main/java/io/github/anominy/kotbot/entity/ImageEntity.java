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

import io.github.anominy.kotbot.entity.util.UImageEntity;
import io.github.anominy.kotbot.entity.util.UObjectEntity;
import io.github.anominy.kotbot.table.util.UImageTable;
import jakarta.persistence.*;

import java.util.Objects;

@SuppressWarnings({"unused", "UnusedReturnValue"})

@Entity
@Table(name = UImageTable.NAME)
public class ImageEntity extends ObjectEntity<ImageEntity> {

	@Column(name = UImageTable.COLUMN_NAME_URL, nullable = false)
	protected String url;

	public ImageEntity() {
	}

	public String getUrl() {
		return this.url;
	}

	public ImageEntity setUrl(String url) {
		this.url = url;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}

		ImageEntity that = (ImageEntity) obj;

		return Objects.equals(this.url, that.url);
	}

	@Override
	public int hashCode() {
		return Objects.hash(
				this.id,
				this.createdAt,
				this.updatedAt,
				this.url
		);
	}

	@Override
	public String toString() {
		return UImageEntity.SIMPLE_NAME + "["
				+ UObjectEntity.ATTRIBUTE_NAME_ID + "=" + this.id
				+ ", " + UObjectEntity.ATTRIBUTE_NAME_CREATED_AT + "=" + this.createdAt
				+ ", " + UObjectEntity.ATTRIBUTE_NAME_UPDATED_AT + "=" + this.updatedAt
				+ ", " + UImageEntity.ATTRIBUTE_NAME_URL + "=\"" + this.url + "\""
				+ "]";
	}
}

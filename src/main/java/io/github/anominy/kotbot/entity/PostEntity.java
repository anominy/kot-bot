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
import io.github.anominy.kotbot.entity.util.UPostEntity;
import io.github.anominy.kotbot.table.util.UPersonPostTable;
import io.github.anominy.kotbot.table.util.UPostImageTable;
import io.github.anominy.kotbot.table.util.UPostTable;
import jakarta.persistence.*;
import org.springframework.data.jpa.repository.EntityGraph;

import java.lang.annotation.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings({"unused", "UnusedReturnValue"})

@Entity
@Table(name = UPostTable.NAME)
public class PostEntity extends ObjectEntity<PostEntity> {

	@Column(name = UPostTable.COLUMN_NAME_GUILD_ID, nullable = false)
	protected Long guildId;

	@Column(name = UPostTable.COLUMN_NAME_MESSAGE_ID, unique = true)
	protected Long messageId;

	@Column(name = UPostTable.COLUMN_NAME_CONTENT)
	protected String content;

	@Column(name = UPostTable.COLUMN_NAME_IS_REVIEWED, nullable = false)
	protected Boolean isReviewed;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = UPersonPostTable.NAME,
			joinColumns = @JoinColumn(name = UPersonPostTable.COLUMN_NAME_POST_ID, nullable = false),
			inverseJoinColumns = @JoinColumn(name = UPersonPostTable.COLUMN_NAME_PERSON_ID, nullable = false)
	)
	protected List<PersonEntity> authors;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = UPostImageTable.NAME,
			joinColumns = @JoinColumn(name = UPostImageTable.COLUMN_NAME_POST_ID, nullable = false),
			inverseJoinColumns = @JoinColumn(name = UPostImageTable.COLUMN_NAME_IMAGE_ID, nullable = false)
	)
	protected Set<ImageEntity> images;

	public PostEntity() {
		this.isReviewed = UPostTable.COLUMN_DEFAULT_IS_REVIEWED;
	}

	public Long getGuildId() {
		return this.guildId;
	}

	public Long getMessageId() {
		return this.messageId;
	}

	public String getContent() {
		return this.content;
	}

	public Boolean getIsReviewed() {
		return this.isReviewed;
	}

	public List<PersonEntity> getAuthors() {
		return this.authors;
	}

	public Set<ImageEntity> getImages() {
		return this.images;
	}

	public PostEntity setGuildId(Long guildId) {
		this.guildId = guildId;
		return this;
	}

	public PostEntity setMessageId(Long messageId) {
		this.messageId = messageId;
		return this;
	}

	public PostEntity setContent(String content) {
		this.content = content;
		return this;
	}

	public PostEntity setAuthors(List<PersonEntity> authors) {
		this.authors = authors;
		return this;
	}

	public PostEntity setImages(Set<ImageEntity> images) {
		this.images = images;
		return this;
	}

	public PostEntity setIsReviewed(Boolean isReviewed) {
		if (isReviewed == null) {
			isReviewed = UPostTable.COLUMN_DEFAULT_IS_REVIEWED;
		}

		this.isReviewed = isReviewed;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}

		PostEntity that = (PostEntity) obj;

		return Objects.equals(this.guildId, that.guildId)
				&& Objects.equals(this.messageId, that.messageId)
				&& Objects.equals(this.content, that.content)
				&& Objects.equals(this.isReviewed, that.isReviewed)
				&& Objects.equals(this.authors, that.authors)
				&& Objects.equals(this.images, that.images);
	}

	@Override
	public int hashCode() {
		return Objects.hash(
				this.id,
				this.createdAt,
				this.updatedAt,
				this.guildId,
				this.messageId,
				this.content,
				this.isReviewed,
				this.authors,
				this.images
		);
	}

	@Override
	public String toString() {
		return UPostEntity.SIMPLE_NAME + "["
				+ UObjectEntity.ATTRIBUTE_NAME_ID + "=" + this.id
				+ ", " + UObjectEntity.ATTRIBUTE_NAME_CREATED_AT + "=" + this.createdAt
				+ ", " + UObjectEntity.ATTRIBUTE_NAME_UPDATED_AT + "=" + this.updatedAt
				+ ", " + UPostEntity.ATTRIBUTE_NAME_GUILD_ID + "=" + this.guildId
				+ ", " + UPostEntity.ATTRIBUTE_NAME_MESSAGE_ID + "=" + this.messageId
				+ ", " + UPostEntity.ATTRIBUTE_NAME_CONTENT + "=\"" + this.content + "\""
				+ ", " + UPostEntity.ATTRIBUTE_NAME_IS_REVIEWED + "=" + this.isReviewed
				+ ", " + UPostEntity.ATTRIBUTE_NAME_AUTHORS + "=" + this.authors
				+ ", " + UPostEntity.ATTRIBUTE_NAME_IMAGES + "=" + this.images
				+ "]";
	}

	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
	@EntityGraph(attributePaths = {
			UPostEntity.ATTRIBUTE_NAME_AUTHORS,
			UPostEntity.ATTRIBUTE_NAME_IMAGES
	})
	public @interface GraphWithAuthorsAndImages {
	}
}

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
import io.github.anominy.kotbot.entity.util.UPersonInfoEntity;
import io.github.anominy.kotbot.table.util.UPersonInfoTable;
import io.github.anominy.kotbot.table.util.UPersonInfoRoleTable;
import jakarta.persistence.*;
import org.springframework.data.jpa.repository.EntityGraph;

import java.lang.annotation.*;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings({"unused", "UnusedReturnValue"})

@Entity
@Table(name = UPersonInfoTable.NAME)
public class PersonInfoEntity extends ObjectEntity<PersonInfoEntity> {

	@Column(name = UPersonInfoTable.COLUMN_NAME_PERSON_ID, nullable = false)
	protected Long personId;

	@Column(name = UPersonInfoTable.COLUMN_NAME_GUILD_ID, nullable = false)
	protected Long guildId;

	@Enumerated(EnumType.ORDINAL)
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(
			name = UPersonInfoRoleTable.NAME,
			joinColumns = @JoinColumn(name = UPersonInfoRoleTable.COLUMN_NAME_INFO_ID, nullable = false)
	)
	@Column(name = UPersonInfoRoleTable.COLUMN_NAME_ROLE_TYPE, nullable = false)
	protected Set<EPersonRole> roleTypes;

	public PersonInfoEntity() {
	}

	public Long getPersonId() {
		return this.personId;
	}

	public Long getGuildId() {
		return this.guildId;
	}

	public Set<EPersonRole> getRoleTypes() {
		return this.roleTypes;
	}

	public PersonInfoEntity setPersonId(Long personId) {
		this.personId = personId;
		return this;
	}

	public PersonInfoEntity setGuildId(Long guildId) {
		this.guildId = guildId;
		return this;
	}

	public PersonInfoEntity setRoleTypes(Set<EPersonRole> roleTypes) {
		this.roleTypes = roleTypes;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}

		PersonInfoEntity that = (PersonInfoEntity) obj;

		return Objects.equals(this.roleTypes, that.roleTypes);
	}

	@Override
	public int hashCode() {
		return Objects.hash(
				this.id,
				this.createdAt,
				this.updatedAt,
				this.personId,
				this.guildId,
				this.roleTypes
		);
	}

	@Override
	public String toString() {
		return UPersonInfoEntity.NAME + "["
				+ UObjectEntity.ATTRIBUTE_NAME_ID + "=" + this.id
				+ ", " + UObjectEntity.ATTRIBUTE_NAME_CREATED_AT + "=" + this.createdAt
				+ ", " + UObjectEntity.ATTRIBUTE_NAME_UPDATED_AT + "=" + this.updatedAt
				+ ", " + UPersonInfoEntity.ATTRIBUTE_NAME_PERSON_ID + "=" + this.personId
				+ ", " + UPersonInfoEntity.ATTRIBUTE_NAME_GUILD_ID + "=" + this.guildId
				+ ", " + UPersonInfoEntity.ATTRIBUTE_NAME_ROLE_TYPES + "=" + this.roleTypes
				+ "]";
	}

	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
	@EntityGraph(attributePaths = UPersonInfoEntity.ATTRIBUTE_NAME_ROLE_TYPES)
	public @interface GraphWithRoleTypes {
	}
}

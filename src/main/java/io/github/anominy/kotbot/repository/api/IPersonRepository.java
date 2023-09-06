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

package io.github.anominy.kotbot.repository.api;

import io.github.anominy.kotbot.bean.LocalBean;
import io.github.anominy.kotbot.entity.PersonEntity;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")

@Repository(LocalBean.PERSON_REPOSITORY)
public interface IPersonRepository extends IObjectRepository<PersonEntity> {

	PersonEntity findByUserId(Long userId);

	default PersonEntity findOrCreateIfNotExistsByUserId(Long userId) {
		PersonEntity person = this.findByUserId(userId);

		if (person != null) {
			return person;
		}

		return this.save(new PersonEntity()
				.setUserId(userId));
	}
}

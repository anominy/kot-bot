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

package io.github.anominy.kotbot.service;

import io.github.anominy.kotbot.repository.api.IPersonInfoRepository;
import io.github.anominy.kotbot.service.api.IPersonInfoService;
import io.github.anominy.kotbot.bean.LocalBean;
import io.github.anominy.kotbot.entity.PersonInfoEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@SuppressWarnings("unused")

@Service(LocalBean.PERSON_INFO_SERVICE)
public final class PersonInfoService extends ObjectService<PersonInfoEntity> implements IPersonInfoService {
	private final IPersonInfoRepository repository;

	public PersonInfoService(
			@Qualifier(LocalBean.PERSON_INFO_REPOSITORY)
			IPersonInfoRepository repository
	) {
		super(repository);

		this.repository = repository;
	}

	@Override
	public PersonInfoEntity findWithRoleTypesOrCreateIfNotExistsByPersonIdAndGuildId(Long personId, Long guildId) {
		return this.repository.findWithRoleTypesOrCreateIfNotExistsByPersonIdAndGuildId(personId, guildId);
	}
}

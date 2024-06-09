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

import io.github.anominy.kotbot.repository.api.IConfigRepository;
import io.github.anominy.kotbot.service.api.IConfigService;
import io.github.anominy.kotbot.bean.LocalBean;
import io.github.anominy.kotbot.entity.ConfigEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@SuppressWarnings("unused")

@Service(LocalBean.CONFIG_SERVICE)
public final class ConfigService extends ObjectService<ConfigEntity> implements IConfigService {
	private final IConfigRepository repository;

	public ConfigService(
			@Qualifier(LocalBean.CONFIG_REPOSITORY)
			IConfigRepository repository
	) {
		super(repository);

		this.repository = repository;
	}

	@Override
	public ConfigEntity findWithWebhooksByGuildId(Long guildId) {
		return this.repository.findWithWebhooksByGuildId(guildId);
	}

	@Override
	public ConfigEntity findWithJoinRoleIdsByGuildId(Long guildId) {
		return this.repository.findWithJoinRoleIdsByGuildId(guildId);
	}
}

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

import io.github.anominy.kotbot.entity.WebhookEntity;
import io.github.anominy.kotbot.repository.api.IWebhookRepository;
import io.github.anominy.kotbot.service.api.IWebhookService;
import io.github.anominy.kotbot.bean.LocalBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("unused")

@Service(LocalBean.GENERIC_WEBHOOK_SERVICE)
public sealed class WebhookService<T extends WebhookEntity<?>> extends ObjectService<T> implements IWebhookService<T>
		permits SuggestWebhookService, PublishWebhookService {
	private final IWebhookRepository<T> repository;

	public WebhookService(
			@Qualifier(LocalBean.GENERIC_WEBHOOK_REPOSITORY)
			IWebhookRepository<T> repository
	) {
		super(repository);

		this.repository = repository;
	}

	@Override
	public List<T> findEnabledByGuildId(Long guildId) {
		return this.repository.findEnabledByGuildId(guildId);
	}

	@Override
	public T findOneEnabledByGuildId(Long guildId) {
		return this.repository.findOneEnabledByGuildId(guildId);
	}
}

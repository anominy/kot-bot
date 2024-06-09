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
import io.github.anominy.kotbot.entity.WebhookEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")

@Repository(LocalBean.GENERIC_WEBHOOK_REPOSITORY)
public interface IWebhookRepository<T extends WebhookEntity<?>> extends IObjectRepository<T> {

	List<T> findByIsEnabledAndGuildId(Boolean isEnabled, Long guildId);

	default List<T> findEnabledByGuildId(Long guildId) {
		return this.findByIsEnabledAndGuildId(true, guildId);
	}

	default T findOneEnabledByGuildId(Long guildId) {
		List<T> webhooks = this.findEnabledByGuildId(guildId);

		if (webhooks == null || webhooks.isEmpty()) {
			return null;
		}

		return webhooks.get(0);
	}
}

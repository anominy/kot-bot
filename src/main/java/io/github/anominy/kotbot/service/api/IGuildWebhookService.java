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

package io.github.anominy.kotbot.service.api;

import club.minnced.discord.webhook.receive.ReadonlyMessage;
import io.github.anominy.kotbot.dto.*;
import io.github.anominy.kotbot.entity.WebhookEntity;

public interface IGuildWebhookService {

	String findGuildWebhookChannel(GuildWebhookFindChannelInput input);

	String toggleGuildWebhook(GuildWebhookToggleInput input);

	<T extends WebhookEntity<?>, U extends IWebhookService<T>> String moveGuildWebhookToChannel(GuildWebhookMoveToChannelInput<T, U> input);

	<T extends WebhookEntity<?>, U extends IWebhookService<T>> String unsubscribeGuildWebhookFromChannel(GuildWebhookUnsubscribeFromChannelInput<T, U> input);

	<T extends WebhookEntity<?>, U extends IWebhookService<T>> String subscribeGuildWebhookToChannel(GuildWebhookSubscribeToChannelInput<T, U> input);

	ReadonlyMessage createGuildWebhookMessagePost(GuildWebhookCreateMessagePostInput input);
}
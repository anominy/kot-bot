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

package io.github.anominy.kotbot.discord.interaction.context.postreview;

import io.github.anominy.kotbot.service.api.IGuildReviewService;
import io.github.anominy.kotbot.bean.LocalBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public final class DiscordDeclineMessagePostMessageContextMenu extends DiscordAbstractReviewMessagePostMessageContextMenu {

	public DiscordDeclineMessagePostMessageContextMenu(
			@Qualifier(LocalBean.GUILD_REVIEW_SERVICE)
			IGuildReviewService guildReviewService
	) {
		super("Decline The Post", false, guildReviewService);
	}
}

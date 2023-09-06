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

package io.github.anominy.kotbot.entity.util;

import io.github.anominy.kotbot.entity.PostEntity;

public final class UPostEntity {
	public static final String SIMPLE_NAME = PostEntity.class.getSimpleName();

	public static final String ATTRIBUTE_NAME_GUILD_ID = "guildId";
	public static final String ATTRIBUTE_NAME_MESSAGE_ID = "messageId";
	public static final String ATTRIBUTE_NAME_CONTENT = "content";
	public static final String ATTRIBUTE_NAME_IS_REVIEWED = "isReviewed";
	public static final String ATTRIBUTE_NAME_AUTHORS = "authors";
	public static final String ATTRIBUTE_NAME_IMAGES = "images";

	private UPostEntity() {
		throw new UnsupportedOperationException();
	}
}

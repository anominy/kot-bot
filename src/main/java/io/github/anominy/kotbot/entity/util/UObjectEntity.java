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

import io.github.anominy.kotbot.entity.ObjectEntity;

public final class UObjectEntity {
	public static final String SIMPLE_NAME = ObjectEntity.class.getSimpleName();

	public static final String ATTRIBUTE_NAME_ID = "id";
	public static final String ATTRIBUTE_NAME_CREATED_AT = "createdAt";
	public static final String ATTRIBUTE_NAME_UPDATED_AT = "updatedAt";

	private UObjectEntity() {
		throw new UnsupportedOperationException();
	}
}

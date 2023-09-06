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

package io.github.anominy.kotbot.table.util;

@SuppressWarnings("unused")
public final class UPostTable {
	public static final String NAME = "post_table";

	public static final String COLUMN_NAME_GUILD_ID = "guild_id";
	public static final String COLUMN_NAME_MESSAGE_ID = "message_id";
	public static final String COLUMN_NAME_CONTENT = "content";
	public static final String COLUMN_NAME_IS_REVIEWED = "is_reviewed";

	public static final Boolean COLUMN_DEFAULT_IS_REVIEWED = false;

	private UPostTable() {
		throw new UnsupportedOperationException();
	}
}

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
public final class UConfigTable {
	public static final String NAME = "config_table";

	public static final String COLUMN_NAME_GUILD_ID = "guild_id";
	public static final String COLUMN_NAME_SUGGEST_WEBHOOK_ID = "suggest_webhook_id";
	public static final String COLUMN_NAME_PUBLISH_WEBHOOK_ID = "publish_webhook_id";

	private UConfigTable() {
		throw new UnsupportedOperationException();
	}
}

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

package io.github.anominy.kotbot.bean;

public final class LocalBean {
	public static final String GENERIC_OBJECT_REPOSITORY = "localGenericObjectRepository";
	public static final String PERSON_REPOSITORY = "localPersonRepository";
	public static final String PERSON_INFO_REPOSITORY = "localPersonInfoRepository";
	public static final String POST_REPOSITORY = "localPostRepository";
	public static final String IMAGE_REPOSITORY = "localImageRepository";
	public static final String GENERIC_WEBHOOK_REPOSITORY = "localGenericWebhookRepository";
	public static final String SUGGEST_WEBHOOK_REPOSITORY = "localSuggestWebhookRepository";
	public static final String PUBLISH_WEBHOOK_REPOSITORY = "localPublishWebhookRepository";
	public static final String CONFIG_REPOSITORY = "localConfigRepository";

	public static final String GENERIC_OBJECT_SERVICE = "localGenericObjectService";
	public static final String PERSON_SERVICE = "localPersonService";
	public static final String PERSON_INFO_SERVICE = "localPersonInfoService";
	public static final String POST_SERVICE = "localPostService";
	public static final String IMAGE_SERVICE = "localImageService";
	public static final String GENERIC_WEBHOOK_SERVICE = "localGenericWebhookService";
	public static final String SUGGEST_WEBHOOK_SERVICE = "localSuggestWebhookService";
	public static final String PUBLISH_WEBHOOK_SERVICE = "localPublishWebhookService";
	public static final String CONFIG_SERVICE = "localConfigService";

	public static final String GUILD_JOIN_ROLES_SERVICE = "localGuildJoinRolesService";
	public static final String GUILD_SUGGEST_SERVICE = "localGuildSuggestService";
	public static final String GUILD_REVIEW_SERVICE = "localGuildReviewService";
	public static final String GUILD_USER_ROLES_SERVICE = "localGuildUserRolesService";
	public static final String GUILD_WEBHOOK_SERVICE = "localGuildWebhookService";

	private LocalBean() {
		throw new UnsupportedOperationException();
	}
}

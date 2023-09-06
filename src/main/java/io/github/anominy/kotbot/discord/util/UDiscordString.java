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

package io.github.anominy.kotbot.discord.util;

@SuppressWarnings("DuplicatedCode")
public final class UDiscordString {
	public static final String NO_CONFIG_FOR_GUILD = "There's no configuration for the current guild.";
	public static final String NO_JOIN_ROLES_FOR_GUILD = "There's no join roles for the current guild.";
	public static final String CANT_GET_AVATAR_FOR_NULL_USER = "We can't get an avatar image for a <null> user.";

	public static final String CANT_SUGGEST_POST_FROM_NULL_GUILD = "You can't suggest post from a <null> guild.";
	public static final String CANT_SUGGEST_POST_UNSUPPORTED_IMAGE_FORMAT = "You can't suggest post with an unsupported image format.";
	public static final String GUILD_HAS_DISABLED_SUGGEST_WEBHOOK = "The current guild has disabled the suggest webhook. We're sorry!";
	public static final String GUILD_HAS_DISABLED_PUBLISH_WEBHOOK = "The current guild has disabled the publish webhook. We're sorry!";
	public static final String GUILD_HAS_DISABLED_SUGGEST_PUBLISH_WEBHOOKS = "The current guild has disabled the suggest & publish webhooks. We're sorry!";
	public static final String GUILD_HAS_NO_CONFIG_FOR_SUGGEST_REVIEW_PUBLISH = "The current guild has no configuration for the suggest/review/publish system. We're sorry!";
	public static final String GUILD_HAS_NO_CONFIG_FOR_SUGGEST_WEBHOOK = "The current guild has no configuration for the suggest webhook. We're sorry!";
	public static final String GUILD_HAS_NO_CONFIG_FOR_PUBLISH_WEBHOOK = "The current guild has no configuration for the publish webhook. We're sorry!";
	public static final String CANT_SUGGEST_POST = "We can't suggest your post. We're sorry!";
	public static final String CANT_PUBLISH_POST = "We can't publish your post. We're sorry!";
	public static final String JUST_PROPOSED_NEW_POST_CONGRATS = "You've just proposed a new post, congratulations!";
	public static final String JUST_PUBLISHED_NEW_POST_CONGRATS = "You've just published a new post, congratulations!";

	public static final String CANT_LOOKUP_JOIN_ROLES_FOR_NULL_GUILD = "We can't lookup join roles for a <null> guild.";

	public static final String CANT_REVIEW_POST_FROM_NULL_GUILD = "You can't review post from a <null> guild.";
	public static final String ONLY_REVIEWERS_CAN_REVIEW_POSTS = "Only users w/ reviewer role can review suggestion posts.";
	public static final String CANT_REVIEW_NON_SUGGEST_POST = "You can't review non-suggestion post. We're sorry!";
	public static final String JUST_APPROVED_NEW_POST = "You've just approved a new post.";
	public static final String JUST_DECLINES_NEW_POST = "You've just decline a new post.";

	public static String CANT_S_USER_ROLES_S_NULL_GUILD(String arg0, String arg1) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		if (arg1 == null || arg1.isBlank()) {
			throw new IllegalArgumentException("Argument #1 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();
		arg1 = arg1.toLowerCase();

		return "We can't " + arg0 + " user roles " + arg1 + " a <null> guild.";
	}

	public static String CANT_S_S_ROLE_NULL_USER(String arg0, String arg1, String arg2) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		if (arg1 == null || arg1.isBlank()) {
			throw new IllegalArgumentException("Argument #1 mustn't be <null/blank>");
		}

		if (arg2 == null || arg2.isBlank()) {
			throw new IllegalArgumentException("Argument #2 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();
		arg1 = arg1.toLowerCase();
		arg2 = arg2.toLowerCase();

		return "We can't " + arg0 + " the " + arg1 + " role " + arg2 + " a <null> user.";
	}

	public static String CANT_S_S_ROLE_NON_USER(String arg0, String arg1, String arg2) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		if (arg1 == null || arg1.isBlank()) {
			throw new IllegalArgumentException("Argument #1 mustn't be <null/blank>");
		}

		if (arg2 == null || arg2.isBlank()) {
			throw new IllegalArgumentException("Argument #2 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();
		arg1 = arg1.toLowerCase();
		arg2 = arg2.toLowerCase();

		return "We can't " + arg0 + " the " + arg1 + " role " + arg2 + " a non-user.";
	}

	public static String S_ALREADY_S_S_S(String arg0, String arg1, String arg2, String arg3) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		if (arg1 == null || arg1.isBlank()) {
			throw new IllegalArgumentException("Argument #1 mustn't be <null/blank>");
		}

		if (arg2 == null || arg2.isBlank()) {
			throw new IllegalArgumentException("Argument #2 mustn't be <null/blank>");
		}

		if (arg3 == null || arg3.isBlank()) {
			throw new IllegalArgumentException("Argument #3 mustn't be <null/blank>");
		}

		arg1 = arg1.toLowerCase();
		arg2 = arg2.toLowerCase();
		arg3 = arg3.toLowerCase();

		return arg0 + " is already " + arg1 + " " + arg2 + " the " + arg3 + "s.";
	}

	public static String COULDNT_S_S_S_S(String arg0, String arg1, String arg2, String arg3) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		if (arg1 == null || arg1.isBlank()) {
			throw new IllegalArgumentException("Argument #1 mustn't be <null/blank>");
		}

		if (arg2 == null || arg2.isBlank()) {
			throw new IllegalArgumentException("Argument #2 mustn't be <null/blank>");
		}

		if (arg3 == null || arg3.isBlank()) {
			throw new IllegalArgumentException("Argument #3 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();
		arg2 = arg2.toLowerCase();
		arg3 = arg3.toLowerCase();

		return "We couldn't " + arg0 + " " + arg1 + " " + arg2 + " " + arg3 + "s.";
	}

	public static String S_SUCCESSFULLY_S_S_S(String arg0, String arg1, String arg2, String arg3) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		if (arg1 == null || arg1.isBlank()) {
			throw new IllegalArgumentException("Argument #1 mustn't be <null/blank>");
		}

		if (arg2 == null || arg2.isBlank()) {
			throw new IllegalArgumentException("Argument #2 mustn't be <null/blank>");
		}

		if (arg3 == null || arg3.isBlank()) {
			throw new IllegalArgumentException("Argument #3 mustn't be <null/blank>");
		}

		arg1 = arg1.toLowerCase();
		arg2 = arg2.toLowerCase();
		arg3 = arg3.toLowerCase();

		return arg0 + " is successfully " + arg1 + " " + arg2 + " the " + arg3 + "s.";
	}

	public static String CANT_S_S_WEBHOOK_FOR_NULL_GUILD(String arg0, String arg1) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		if (arg1 == null || arg1.isBlank()) {
			throw new IllegalArgumentException("Argument #1 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();
		arg1 = arg1.toLowerCase();

		return "We can't " + arg0 + " a " + arg1 + " webhook for a <null> guild.";
	}

	public static String S_WEBHOOK_ALREADY_S(String arg0, String arg1) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		if (arg1 == null || arg1.isBlank()) {
			throw new IllegalArgumentException("Argument #1 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();
		arg1 = arg1.toLowerCase();

		return "The " + arg0 + " webhook is already " + arg1 + " for the current guild.";
	}

	public static String S_WEBHOOK_SUCCESSFULLY_S(String arg0, String arg1) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		if (arg1 == null || arg1.isBlank()) {
			throw new IllegalArgumentException("Argument #1 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();
		arg1 = arg1.toLowerCase();

		return "The " + arg0 + " webhook is successfully " + arg1 + " for the current guild.";
	}

	public static String S_WEBHOOK_SUBSCRIBED_TO_S_CHANNEL(String arg0, String arg1) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		if (arg1 == null || arg1.isBlank()) {
			throw new IllegalArgumentException("Argument #1 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();
		arg1 = arg1.toLowerCase();

		return "The " + arg0 + " webhook is currently subscribed to the " + arg1 + " channel.";
	}

	public static String CANT_GET_S_WEBHOOK_CHANNEL_NULL_GUILD(String arg0) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();

		return "We can't get a " + arg0 + " webhook channel for a <null> guild.";
	}

	public static String CANT_CREATE_S_WEBHOOK_IN_S_CHANNEL(String arg0, String arg1) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		if (arg1 == null || arg1.isBlank()) {
			throw new IllegalArgumentException("Argument #1 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();
		arg1 = arg1.toLowerCase();

		return "We can't create a " + arg0 + " webhook in the " + arg1 + " channel.";
	}

	public static String S_WEBHOOK_ALREADY_PLACED_IN_S_CHANNEL(String arg0, String arg1) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		if (arg1 == null || arg1.isBlank()) {
			throw new IllegalArgumentException("Argument #1 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();
		arg1 = arg1.toLowerCase();

		return "The " + arg0 + " webhook is already placed in the " + arg1 + " channel.";
	}

	public static String CANT_MOVE_S_WEBHOOK_TO_S_CHANNEL(String arg0, String arg1) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		if (arg1 == null || arg1.isBlank()) {
			throw new IllegalArgumentException("Argument #1 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();
		arg1 = arg1.toLowerCase();

		return "We can't move the " + arg0 + " webhook to the " + arg1 + " channel.";
	}

	public static String NO_S_WEBHOOK_FOR_GUILD(String arg0) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();

		return "There's no a " + arg0 + " webhook for the current guild.";
	}

	public static String CANT_S_JOIN_ROLES_S_NULL_GUILD(String arg0, String arg1) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		if (arg1 == null || arg1.isBlank()) {
			throw new IllegalArgumentException("Argument #1 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();
		arg1 = arg1.toLowerCase();

		return "We can't " + arg0 + " join roles " + arg1 + " a <null> guild.";
	}

	public static String CANT_S_NULL_ROLE_S_GUILD_JOIN_ROLES(String arg0, String arg1) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		if (arg1 == null || arg1.isBlank()) {
			throw new IllegalArgumentException("Argument #1 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();
		arg1 = arg1.toLowerCase();

		return "We can't " + arg0 + " a <null> role " + arg1 + " the current guild join roles.";
	}

	public static String CANT_S_PUBLIC_ROLE_S_GUILD_JOIN_ROLES(String arg0, String arg1) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		if (arg1 == null || arg1.isBlank()) {
			throw new IllegalArgumentException("Argument #1 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();
		arg1 = arg1.toLowerCase();

		return "We can't " + arg0 + " a public role " + arg1 + " the current guild join roles.";
	}

	public static String CANT_S_UNMANAGEABLE_ROLE_S_GUILD_JOIN_ROLES(String arg0, String arg1) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		if (arg1 == null || arg1.isBlank()) {
			throw new IllegalArgumentException("Argument #1 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();
		arg1 = arg1.toLowerCase();

		return "We can't " + arg0 + " an unmanageable role " + arg1 + " the current guild join roles.";
	}

	public static String S_ALREADY_S_S_GUILD_JOIN_ROLES(String arg0, String arg1, String arg2) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		if (arg1 == null || arg1.isBlank()) {
			throw new IllegalArgumentException("Argument #1 mustn't be <null/blank>");
		}

		if (arg2 == null || arg2.isBlank()) {
			throw new IllegalArgumentException("Argument #2 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();
		arg1 = arg1.toLowerCase();
		arg2 = arg2.toLowerCase();

		return arg0 + " is already " + arg1 + " " + arg2 + " the current guild join roles.";
	}

	public static String COULDNT_S_S_S_GUILD_JOIN_ROLES(String arg0, String arg1, String arg2) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		if (arg1 == null || arg1.isBlank()) {
			throw new IllegalArgumentException("Argument #1 mustn't be <null/blank>");
		}

		if (arg2 == null || arg2.isBlank()) {
			throw new IllegalArgumentException("Argument #2 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();
		arg1 = arg1.toLowerCase();
		arg2 = arg2.toLowerCase();

		return "We couldn't " + arg0 + " " + arg1 + " " + arg2 + " the current guild join roles.";
	}

	public static String S_SUCCESSFULLY_S_S_GUILD_JOIN_ROLES(String arg0, String arg1, String arg2) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		if (arg1 == null || arg1.isBlank()) {
			throw new IllegalArgumentException("Argument #1 mustn't be <null/blank>");
		}

		if (arg2 == null || arg2.isBlank()) {
			throw new IllegalArgumentException("Argument #2 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();
		arg1 = arg1.toLowerCase();
		arg2 = arg2.toLowerCase();

		return arg0 + " is successfully " + arg1 + " " + arg2 + " the current guild join roles.";
	}

	public static String CANT_SUBSCRIBE_S_WEBHOOK_NULL_GUILD(String arg0) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();

		return "We can't subscribe a " + arg0 + " webhook to a <null> guild.";
	}

	public static String CANT_SUBSCRIBE_S_WEBHOOK_NULL_CHANNEL(String arg0) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();

		return "We can't subscribe a " + arg0 + " webhook to a <null> channel.";
	}

	public static String CANT_SUBSCRIBE_S_WEBHOOK_NON_TEXT_CHANNEL(String arg0) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();

		return "We can't subscribe a " + arg0 + " webhook to a non-text channel";
	}

	public static String S_WEBHOOK_ALREADY_SUBSCRIBED_S_CHANNEL(String arg0, String arg1) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		if (arg1 == null || arg1.isBlank()) {
			throw new IllegalArgumentException("Argument #1 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();

		return "A " + arg0 + " webhook is already subscribed to the " + arg1 + " channel.";
	}

	public static String S_WEBHOOK_SUCCESSFULLY_SUBSCRIBED_S_CHANNEL(String arg0, String arg1) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		if (arg1 == null || arg1.isBlank()) {
			throw new IllegalArgumentException("Argument #1 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();

		return "A " + arg0 + " webhook is successfully subscribed to the " + arg1 + " channel.";
	}


	public static String CANT_UNSUBSCRIBE_S_WEBHOOK_NULL_GUILD(String arg0) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();

		return "We can't unsubscribe a " + arg0 + " webhook from a <null> guild.";
	}

	public static String S_WEBHOOK_ALREADY_UNSUBSCRIBED_S_CHANNEL(String arg0, String arg1) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		if (arg1 == null || arg1.isBlank()) {
			throw new IllegalArgumentException("Argument #1 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();

		return "The " + arg0 + " webhook is already unsubscribed from the " + arg1 + " channel.";
	}

	public static String S_WEBHOOK_SUCCESSFULLY_UNSUBSCRIBED_S_CHANNEL(String arg0, String arg1) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		if (arg1 == null || arg1.isBlank()) {
			throw new IllegalArgumentException("Argument #1 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();

		return "The " + arg0 + " webhook is successfully unsubscribed from the " + arg1 + " channel.";
	}

	public static String CANT_MOVE_S_WEBHOOK_NULL_GUILD(String arg0) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();

		return "We can't move a " + arg0 + " webhook to a <null> guild.";
	}

	public static String CANT_MOVE_S_WEBHOOK_NULL_CHANNEL(String arg0) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();

		return "We can't move a " + arg0 + " webhook to a <null> channel.";
	}

	public static String CANT_MOVE_S_WEBHOOK_NON_TEXT_CHANNEL(String arg0) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();

		return "We can't move a " + arg0 + " webhook to a non-text channel.";
	}

	public static String S_WEBHOOK_SUCCESSFULLY_MOVED_S_CHANNEL(String arg0, String arg1) {
		if (arg0 == null || arg0.isBlank()) {
			throw new IllegalArgumentException("Argument #0 mustn't be <null/blank>");
		}

		if (arg1 == null || arg1.isBlank()) {
			throw new IllegalArgumentException("Argument #1 mustn't be <null/blank>");
		}

		arg0 = arg0.toLowerCase();

		return "The " + arg0 + " webhook is successfully moved to the " + arg1 + " channel.";
	}

	private UDiscordString() {
		throw new UnsupportedOperationException();
	}
}

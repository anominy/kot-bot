/*
 * Copyright 2024 anominy
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

package io.github.anominy.kotbot;

import io.github.anominy.jda.prelude.interaction.DiscordInteractionRouter;
import io.github.anominy.jda.prelude.interaction.button.DiscordButtonConfiguration;
import io.github.anominy.jda.prelude.interaction.command.DiscordSlashCommandConfiguration;
import io.github.anominy.jda.prelude.interaction.context.DiscordContextMenuConfiguration;
import io.github.anominy.jda.prelude.interaction.modal.DiscordModalConfiguration;
import io.github.anominy.jda.prelude.interaction.select.DiscordSelectMenuConfiguration;
import io.github.anominy.kotbot.interaction.command.CatDiscordSlashCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.Collections;
import java.util.Set;

public final class Main {

    public static void main(String[] args) throws InterruptedException {
        String token = System.getenv("DISCORD_BOT_TOKEN");
        if (token == null || token.isBlank()) {
            throw new IllegalStateException("Discord bot token mustn't be <null/blank>");
        }

        String guildId = System.getenv("DISCORD_GUILD_ID");
        if (guildId == null || guildId.isBlank()) {
            throw new IllegalStateException("Discord guild identifier mustn't be <null/blank>");
        }

        JDA discordApi = JDABuilder.create(token, Collections.emptyList())
                .disableCache(
                        CacheFlag.ACTIVITY,
                        CacheFlag.VOICE_STATE,
                        CacheFlag.EMOJI,
                        CacheFlag.STICKER,
                        CacheFlag.CLIENT_STATUS,
                        CacheFlag.ONLINE_STATUS,
                        CacheFlag.SCHEDULED_EVENTS
                )
                .addEventListeners(SingletonInteractionRouter.INSTANCE)
                .build();

        discordApi.awaitReady();

        Guild guild = discordApi.getGuildById(guildId);
        if (guild == null) {
            throw new IllegalStateException("Guild mustn't be <null>");
        }

        guild.updateCommands()
                .addCommands(SingletonSlashCommandConfig.INSTANCE.getDiscordSlashCommandDataSet())
                .complete();
    }

    private static final class SingletonInteractionRouter {
        public static final DiscordInteractionRouter INSTANCE
                = new DiscordInteractionRouter(
                        SingletonSlashCommandConfig.INSTANCE.getDiscordSlashCommandMapByPath(),
                        SingletonSlashCommandConfig.INSTANCE.getDiscordSlashCommandOptionMapByPath(),
                        SingletonContextMenuConfig.INSTANCE.getDiscordUserContextMenuMapByName(),
                        SingletonContextMenuConfig.INSTANCE.getDiscordMessageContextMenuMapByName(),
                        SingletonButtonConfig.INSTANCE.getDiscordButtonMapById(),
                        SingletonSelectMenuConfig.INSTANCE.getDiscordEntitySelectMenuMapById(),
                        SingletonSelectMenuConfig.INSTANCE.getDiscordStringSelectMenuMapById(),
                        SingletonModalConfig.INSTANCE.getDiscordModalMapById()
                );

        private SingletonInteractionRouter() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class SingletonSlashCommandConfig {
        public static final DiscordSlashCommandConfiguration INSTANCE
                = new DiscordSlashCommandConfiguration(Set.of(
                        new CatDiscordSlashCommand()
                ));

        private SingletonSlashCommandConfig() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class SingletonContextMenuConfig {
        public static final DiscordContextMenuConfiguration INSTANCE
                = new DiscordContextMenuConfiguration(null);

        private SingletonContextMenuConfig() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class SingletonButtonConfig {
        public static final DiscordButtonConfiguration INSTANCE
                = new DiscordButtonConfiguration(null);

        private SingletonButtonConfig() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class SingletonSelectMenuConfig {
        public static final DiscordSelectMenuConfiguration INSTANCE
                = new DiscordSelectMenuConfiguration(null);

        private SingletonSelectMenuConfig() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class SingletonModalConfig {
        public static final DiscordModalConfiguration INSTANCE
                = new DiscordModalConfiguration(null);

        private SingletonModalConfig() {
            throw new UnsupportedOperationException();
        }
    }

    private Main() {
        throw new UnsupportedOperationException();
    }
}

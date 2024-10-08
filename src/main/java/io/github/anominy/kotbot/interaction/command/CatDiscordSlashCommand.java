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

package io.github.anominy.kotbot.interaction.command;

import io.github.anominy.jda.prelude.interaction.command.IDiscordSlashCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.utils.FileUpload;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public final class CatDiscordSlashCommand implements IDiscordSlashCommand {
    private static final int IMAGE_MIN_INDEX = 1;
    private static final int IMAGE_MAX_INDEX = 500;

    @Override
    public SlashCommandData initDiscordSlashCommandData() {
        return Commands.slash("cat", "Cat!");
    }

    @Override
    public void onDiscordSlashCommandInteractionEvent(SlashCommandInteractionEvent event) {
        event.deferReply()
                .queue(it -> {
                    int index = (int) ((Math.random() * (IMAGE_MAX_INDEX - IMAGE_MIN_INDEX)) + IMAGE_MIN_INDEX);

                    String url = "https://raw.githubusercontent.com/anominy/kitty-images/refs/heads/main/"
                            + index
                            + ".jpg";

                    try (InputStream is = new URL(url)
                            .openStream()) {
                        String[] split = url.split("/");
                        String name = split[split.length - 1];

                        try (FileUpload fileUpload = FileUpload.fromData(is.readAllBytes(), name)) {
                            it.sendFiles(fileUpload)
                                    .queue();
                        }
                    } catch (IOException ignored) {
                    }
                });
    }
}

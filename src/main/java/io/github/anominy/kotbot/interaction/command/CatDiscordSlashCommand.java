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

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import io.github.anominy.jda.prelude.interaction.command.IDiscordSlashCommand;
import io.github.anominy.jda.prelude.interaction.command.IDiscordSlashCommandOption;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.utils.FileUpload;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public final class CatDiscordSlashCommand implements IDiscordSlashCommand {
    @Override
    public SlashCommandData initDiscordSlashCommandData() {
        return Commands.slash("cat", "Cat!");
    }

    @Override
    public IDiscordSlashCommandOption[] getDiscordSlashCommandOptionArray() {
        return new IDiscordSlashCommandOption[] {
                new CountDiscordSlashCommandOption()
        };
    }

    @Override
    public void onDiscordSlashCommandInteractionEvent(SlashCommandInteractionEvent event) {
        event.deferReply()
                .queue(it -> {
                    Integer count = event.getOption(CountDiscordSlashCommandOption.NAME,
                            1, OptionMapping::getAsInt);

                    JsonArray jsonArray;
                    try {
                        jsonArray = JsonParser.array()
                                .from(new URL("https://api.thecatapi.com/v1/images/search?limit=" + count));
                    } catch (JsonParserException | MalformedURLException ignored) {
                        return;
                    }

                    FileUpload[] fileUploads = new FileUpload[count];
                    for (int i = 0; i < fileUploads.length; ++i) {
                        JsonObject jsonObject = jsonArray.getObject(i);
                        String url = jsonObject.getString("url");
                        try (InputStream is = new URL(url)
                                .openStream()) {
                            String[] split = url.split("/");
                            String name = split[split.length - 1];

                            //noinspection resource
                            fileUploads[i] = FileUpload.fromData(is.readAllBytes(), name);
                        } catch (IOException ignored) {
                        }
                    }

                    it.sendFiles(fileUploads)
                            .queue();
                });
    }

    private static final class CountDiscordSlashCommandOption implements IDiscordSlashCommandOption {
        public static final String NAME = "count";

        @Override
        public OptionData initDiscordSlashCommandOptionData() {
            return new OptionData(OptionType.INTEGER, NAME, "Count!")
                    .setMinValue(1)
                    .setMaxValue(10);
        }
    }
}

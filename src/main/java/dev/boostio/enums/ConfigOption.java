/*
 *
 *  * This file is part of ChatPro - https://github.com/Aaron2404/ChatPro
 *  * Copyright (C) 2022 Aaron and contributors
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package dev.boostio.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ConfigOption {
    UPDATE_CHECKER_ENABLED("update-checker.enabled", true),
    UPDATE_CHECKER_PRINT_TO_CONSOLE("update-checker.print-to-console", true),
    NOTIFY_IN_GAME("update-checker.notify-in-game", true),

    COLOURS_ENABLED("colours.enabled", true),
    DEFAULT_CHAT_COLOR("colours.default-chat-color", "default"),
    COLOR_COMMAND_NO_PERMISSION("colours.color-command-no-permission", "You do not have permission to execute this command."),

    BETTER_MESSAGE_FORMAT("format.better-message-format", true),

    FILTERED_WORDS("censoring.filtered-words", Arrays.asList("Fuck", "Shit")),
    FILTER_IP("censoring.filter-ip", true),
    BLOCK_MESSAGE("censoring.block-message", false),
    BLOCKED_MESSAGE_NOTIFICATION("censoring.blocked-message-notification", "Your message contained a filtered word, and it has been canceled."),
    REPLACE_WORD_IN_MESSAGE("censoring.replace-word-in-message", true),
    FILTERED_WORD_REPLACEMENT("censoring.filtered-word-replacement", "*");

    private final String key;
    private final Object defaultValue;

    <T> ConfigOption(String key, T defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public <T> T getDefaultValue() {
        return (T) defaultValue;
    }
}
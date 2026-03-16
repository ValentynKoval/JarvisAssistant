package com.valentyn.assistant.commands;

import com.valentyn.assistant.config.AliasConfig;
import com.valentyn.assistant.config.SiteConfig;
import com.valentyn.assistant.model.Command;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {

    private static final Pattern OPEN_COMMAND_PATTERN =
            Pattern.compile("^открой\\s+(.+)$");

    private final AliasConfig aliasConfig = new AliasConfig();
    private final SiteConfig siteConfig = new SiteConfig();

    public Command parse(String text) {
        if (text == null || text.isBlank()) {
            return new Command(CommandType.UNKNOWN, text, null);
        }

        String normalized = text.trim().toLowerCase();

        if (normalized.equals("выход") || normalized.equals("exit")) {
            return new Command(CommandType.EXIT, text, null);
        }

        if (normalized.equals("начни запись")
                || normalized.equals("запиши тест")
                || normalized.equals("старт записи")) {
            return new Command(CommandType.START_RECORDING, text, null);
        }

        if (normalized.equals("останови запись")
                || normalized.equals("стоп запись")
                || normalized.equals("закончить запись")) {
            return new Command(CommandType.STOP_RECORDING, text, null);
        }

        Matcher matcher = OPEN_COMMAND_PATTERN.matcher(normalized);

        if (matcher.matches()) {
            String rawTarget = matcher.group(1).trim();
            String resolvedTarget = aliasConfig.resolve(rawTarget);

            if (siteConfig.contains(resolvedTarget)) {
                return new Command(CommandType.OPEN_WEBSITE, text, resolvedTarget);
            }

            return new Command(CommandType.OPEN_APP, text, resolvedTarget);
        }

        return new Command(CommandType.UNKNOWN, text, null);
    }
}

package com.valentyn.assistant.commands;

import com.valentyn.assistant.model.Command;

public class CommandParser {
    public Command parse(String text) {
        if (text == null || text.isBlank()) {
            return new Command(CommandType.UNKNOWN, text);
        }

        String normalized = text.toLowerCase().trim();

        if (normalized.contains("открой браузер")) {
            return new Command(CommandType.OPEN_BROWSER, text);
        }

        if (normalized.contains("открой телеграм")) {
            return new Command(CommandType.OPEN_TELEGRAM, text);
        }

        if (normalized.contains("открой vscode") || normalized.contains("открой visual studio code")) {
            return new Command(CommandType.OPEN_VSCODE, text);
        }

        if (normalized.contains("открой калькулятор")) {
            return new Command(CommandType.OPEN_CALCULATOR, text);
        }

        if (normalized.contains("открой ютуб") || normalized.contains("открой youtube")) {
            return new Command(CommandType.OPEN_YOUTUBE, text);
        }

        if (normalized.equals("выход") || normalized.equals("exit")) {
            return new Command(CommandType.EXIT, text);
        }

        return new Command(CommandType.UNKNOWN, text);
    }
}

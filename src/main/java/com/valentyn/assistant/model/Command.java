package com.valentyn.assistant.model;

import com.valentyn.assistant.commands.CommandType;

public class Command {
    private final CommandType type;
    private final String originalText;

    public Command(CommandType type, String originalText) {
        this.type = type;
        this.originalText = originalText;
    }

    public CommandType getType() {
        return type;
    }

    public String getOriginalText() {
        return originalText;
    }
}
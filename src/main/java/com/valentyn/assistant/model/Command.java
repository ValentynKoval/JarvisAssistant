package com.valentyn.assistant.model;

import com.valentyn.assistant.commands.CommandType;

public class Command {
    private final CommandType type;
    private final String originalText;
    private final String target;

    public Command(CommandType type, String originalText, String target) {
        this.type = type;
        this.originalText = originalText;
        this.target = target;
    }

    public CommandType getType() {
        return type;
    }

    public String getOriginalText() {
        return originalText;
    }

    public String getTarget() {
        return target;
    }
}
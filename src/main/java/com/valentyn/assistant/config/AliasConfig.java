package com.valentyn.assistant.config;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class AliasConfig {

    private static final String FILE_NAME = "aliases.properties";

    private final Map<String, String> aliasToTarget = new HashMap<>();

    public AliasConfig() {
        loadAliases();
    }

    private void loadAliases() {
        Properties properties = new PropertiesLoader().load(FILE_NAME);

        for (String canonicalKey : properties.stringPropertyNames()) {
            String aliasesLine = properties.getProperty(canonicalKey);

            if (aliasesLine == null || aliasesLine.isBlank()) {
                continue;
            }

            String[] aliases = aliasesLine.split(",");

            for (String alias : aliases) {
                String normalizedAlias = alias.trim().toLowerCase(Locale.ROOT);
                if (!normalizedAlias.isBlank()) {
                    aliasToTarget.put(normalizedAlias, canonicalKey);
                }
            }
        }
    }

    public String resolve(String rawTarget) {
        if (rawTarget == null || rawTarget.isBlank()) {
            return null;
        }

        String normalized = rawTarget.trim().toLowerCase(Locale.ROOT);
        return aliasToTarget.getOrDefault(normalized, normalized);
    }
}

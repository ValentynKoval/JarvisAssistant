package com.valentyn.assistant.config;

import java.util.Properties;

public class SiteConfig {

    private static final String FILE_NAME = "sites.properties";

    private final Properties properties;

    public SiteConfig() {
        this.properties = new PropertiesLoader().load(FILE_NAME);
    }

    public String getUrl(String key) {
        return properties.getProperty(key);
    }

    public boolean contains(String key) {
        return properties.containsKey(key);
    }
}
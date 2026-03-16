package com.valentyn.assistant.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {

    private static final String FILE_NAME = "apps.properties";

    private final Properties properties;

    public AppConfig() {
        this.properties = new PropertiesLoader().load(FILE_NAME);
    }

    public String getAppPath(String key) {
        return properties.getProperty(key);
    }

    public boolean contains(String key) {
        return properties.containsKey(key);
    }
}
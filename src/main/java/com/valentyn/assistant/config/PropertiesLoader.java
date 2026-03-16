package com.valentyn.assistant.config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertiesLoader {

    public Properties load(String fileName) {
        Properties properties = new Properties();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IllegalStateException("Файл конфигурации не найден: " + fileName);
            }

            try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
                properties.load(reader);
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка загрузки файла: " + fileName, e);
        }

        return properties;
    }
}
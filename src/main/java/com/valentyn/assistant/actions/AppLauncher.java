package com.valentyn.assistant.actions;

import com.valentyn.assistant.config.AppConfig;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

public class AppLauncher {

    private final AppConfig appConfig = new AppConfig();

    public void openApp(String appKey) throws IOException {
        String path = appConfig.getAppPath(appKey);

        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("Для приложения не найден путь в apps.properties: " + appKey);
        }

        new ProcessBuilder(path).start();
    }

    public void openWebsite(String url) throws Exception {
        if (!Desktop.isDesktopSupported()) {
            throw new UnsupportedOperationException("Desktop API не поддерживается");
        }

        Desktop.getDesktop().browse(new URI(url));
    }
}
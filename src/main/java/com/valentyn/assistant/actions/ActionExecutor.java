package com.valentyn.assistant.actions;

import com.valentyn.assistant.audio.MicrophoneRecorder;
import com.valentyn.assistant.commands.CommandType;
import com.valentyn.assistant.config.SiteConfig;
import com.valentyn.assistant.model.Command;

import java.nio.file.Path;

public class ActionExecutor {

    private final AppLauncher appLauncher = new AppLauncher();
    private final SiteConfig siteConfig = new SiteConfig();
    private final MicrophoneRecorder microphoneRecorder = new MicrophoneRecorder();

    public boolean execute(Command command) {
        try {
            CommandType type = command.getType();

            switch (type) {
                case OPEN_APP -> {
                    System.out.println("Открываю приложение: " + command.getTarget());
                    appLauncher.openApp(command.getTarget());
                }
                case OPEN_WEBSITE -> {
                    String url = siteConfig.getUrl(command.getTarget());

                    if (url == null || url.isBlank()) {
                        System.out.println("Сайт не найден в конфиге: " + command.getTarget());
                        return true;
                    }

                    System.out.println("Открываю сайт: " + url);
                    appLauncher.openWebsite(url);
                }
                case START_RECORDING -> {
                    Path outputFile = microphoneRecorder.startRecording();
                    System.out.println("Запись началась.");
                    System.out.println("Файл будет сохранён в: " + outputFile.toAbsolutePath());
                }
                case STOP_RECORDING -> {
                    microphoneRecorder.stopRecording();
                    System.out.println("Запись остановлена.");
                    System.out.println("Файл сохранён в: " +
                            microphoneRecorder.getCurrentOutputFile().toAbsolutePath());
                }
                case EXIT -> {
                    if (microphoneRecorder.isRecording()) {
                        microphoneRecorder.stopRecording();
                    }
                    System.out.println("Завершаю работу ассистента...");
                    return false;
                }
                case UNKNOWN -> {
                    System.out.println("Команда не распознана: " + command.getOriginalText());
                }
            }

            return true;
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении команды: " + e.getMessage());
            return true;
        }
    }
}
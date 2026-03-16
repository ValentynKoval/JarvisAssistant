package com.valentyn.assistant.audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MicrophoneRecorder {

    private TargetDataLine line;
    private Thread recordingThread;
    private Path currentOutputFile;

    public synchronized Path startRecording() {
        if (isRecording()) {
            throw new IllegalStateException("Запись уже идёт");
        }

        try {
            AudioFormat format = buildAudioFormat();

            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            if (!AudioSystem.isLineSupported(info)) {
                throw new IllegalStateException("Микрофон или формат записи не поддерживается");
            }

            TargetDataLine dataLine = (TargetDataLine) AudioSystem.getLine(info);
            dataLine.open(format);
            dataLine.start();

            Files.createDirectories(Path.of("recordings"));

            String fileName = "recording-" +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")) +
                    ".wav";

            Path outputFile = Path.of("recordings", fileName);

            this.line = dataLine;
            this.currentOutputFile = outputFile;

            recordingThread = new Thread(() -> {
                try (AudioInputStream audioInputStream = new AudioInputStream(dataLine)) {
                    AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, outputFile.toFile());
                } catch (IOException e) {
                    System.out.println("Ошибка записи аудио: " + e.getMessage());
                } finally {
                    synchronized (MicrophoneRecorder.this) {
                        if (line == dataLine) {
                            line = null;
                        }
                    }
                }
            }, "audio-recording-thread");

            recordingThread.start();
            return outputFile;

        } catch (LineUnavailableException | IOException e) {
            throw new RuntimeException("Не удалось начать запись: " + e.getMessage(), e);
        }
    }

    public synchronized void stopRecording() {
        if (!isRecording()) {
            throw new IllegalStateException("Запись сейчас не идёт");
        }

        line.stop();
        line.close();
    }

    public synchronized boolean isRecording() {
        return line != null && line.isOpen();
    }

    public synchronized Path getCurrentOutputFile() {
        return currentOutputFile;
    }

    private AudioFormat buildAudioFormat() {
        return new AudioFormat(
                16000.0f,
                16,
                1,
                true,
                false
        );
    }
}
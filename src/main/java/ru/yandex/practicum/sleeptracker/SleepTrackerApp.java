package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class SleepTrackerApp {

    public static void main(String[] args) {
        //src/main/resources/sleep_log.txt
        if (args.length == 0) {
            System.out.println("Не указан путь к файлу");
            return;
        }
        Path path = Path.of(args[0]);
        if (!Files.exists(path)) {
            System.out.println("Файл не найден: " + path);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
        List<SleepSession> sleepSessions;
        try (Stream<String> lines = Files.lines(path)) {
            sleepSessions = lines
                    .map(s -> s.split(";"))
                    .map(p -> new SleepSession(
                            LocalDateTime.parse(p[0].strip(), formatter),
                            LocalDateTime.parse(p[1].strip(), formatter),
                            p[2].strip()))
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки файла", e);
        }

        List<Function<List<SleepSession>, ? extends SleepAnalysisResult>> functions = List.of(
                new SleepSessionsCounter(),
                new MinSleepSessionDuration(),
                new MaxSleepSessionDuration(),
                new AvgSleepSessionDuration(),
                new BadSleepSessionsCounter(),
                new NonSleepNightCounter(),
                new UserChronotype()
        );

        functions.forEach(f -> System.out.println((f.apply(sleepSessions))));
    }
}
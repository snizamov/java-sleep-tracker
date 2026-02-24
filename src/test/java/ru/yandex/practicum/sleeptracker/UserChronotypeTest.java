package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserChronotypeTest {
    @Test
    void testUserChronotypeSOVA() {
        List<SleepSession> testLog = List.of(
                //сова
                new SleepSession(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 9, 30),
                        "GOOD"
                ),
                //жаворонок
                new SleepSession(
                        LocalDateTime.of(2025, 10, 2, 21, 30),
                        LocalDateTime.of(2025, 10, 3, 6, 30),
                        "GOOD"
                ),
                //сова на границах
                new SleepSession(
                        LocalDateTime.of(2025, 10, 3, 23, 00),
                        LocalDateTime.of(2025, 10, 4, 9, 0),
                        "NORMAL"
                )
        );

        UserChronotype f = new UserChronotype();
        SleepAnalysisResult result = f.apply(testLog);
        assertEquals("СОВА", result.getValue().toString());
    }

    @Test
    void testUserChronotypeZHAVORONOK() {
        List<SleepSession> testLog = List.of(
                //сова
                new SleepSession(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 9, 30),
                        "GOOD"
                ),
                //жаворонок
                new SleepSession(
                        LocalDateTime.of(2025, 10, 2, 21, 30),
                        LocalDateTime.of(2025, 10, 3, 6, 30),
                        "GOOD"
                ),
                //жаворонок на границах
                new SleepSession(
                        LocalDateTime.of(2025, 10, 3, 22, 00),
                        LocalDateTime.of(2025, 10, 4, 7, 0),
                        "NORMAL"
                )
        );

        UserChronotype f = new UserChronotype();
        SleepAnalysisResult result = f.apply(testLog);
        assertEquals("ЖАВОРОНОК", result.getValue().toString());
    }

}

package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleepTrackerAppTest {

    List<SleepSession> testLog = List.of(
            //сова 10ч
            new SleepSession(
                    LocalDateTime.of(2025, 10, 1, 23, 30),
                    LocalDateTime.of(2025, 10, 2, 9, 30),
                    "GOOD"
            ),
            //жаворонок 9ч
            new SleepSession(
                    LocalDateTime.of(2025, 10, 2, 21, 30),
                    LocalDateTime.of(2025, 10, 3, 6, 30),
                    "GOOD"
            ),
            //голубь 9ч 30 мин
            new SleepSession(
                    LocalDateTime.of(2025, 10, 3, 22, 30),
                    LocalDateTime.of(2025, 10, 4, 8, 0),
                    "NORMAL"
            ),
            //голубь ночной сон 6ч
            new SleepSession(
                    LocalDateTime.of(2025, 10, 5, 2, 0),
                    LocalDateTime.of(2025, 10, 5, 8, 0),
                    "GOOD"
            ),
            //голубь ночной сон 2ч 30 мин
            new SleepSession(
                    LocalDateTime.of(2025, 10, 5, 23, 30),
                    LocalDateTime.of(2025, 10, 6, 2, 0),
                    "NORMAL"
            ),
            //голубь дневной сон 1ч 30 мин
            new SleepSession(
                    LocalDateTime.of(2025, 10, 6, 14, 0),
                    LocalDateTime.of(2025, 10, 6, 15, 30),
                    "BAD"
            ),
            //голубь бессонная ночь 3ч
            new SleepSession(
                    LocalDateTime.of(2025, 10, 7, 7, 0),
                    LocalDateTime.of(2025, 10, 7, 10, 0),
                    "NORMAL"
            )
            //общее 41 ч 30 мин за 7 сессий
    );

    @Test
    void testSleepSessionsCounterSevenCounts() {
        SleepSessionsCounter f = new SleepSessionsCounter();
        SleepAnalysisResult result = f.apply(testLog);
        assertEquals(7, result.getValue());
    }

    @Test
    void testMinSleepSessionDuration90Min() {
        MinSleepSessionDuration f = new MinSleepSessionDuration();
        SleepAnalysisResult result = f.apply(testLog);
        assertEquals(Duration.ofMinutes(90), result.getValue());
    }

    @Test
    void testMaxSleepSessionDuration600Min() {
        MaxSleepSessionDuration f = new MaxSleepSessionDuration();
        SleepAnalysisResult result = f.apply(testLog);
        assertEquals(Duration.ofMinutes(600), result.getValue());
    }

    @Test
    void testAvgSleepSessionDuration() {
        AvgSleepSessionDuration f = new AvgSleepSessionDuration();
        SleepAnalysisResult result = f.apply(testLog);
        assertEquals(Duration.ofMinutes((41 * 60 + 30) / 7), result.getValue());
    }

    @Test
    void testBadSleepSessionsCounter() {
        BadSleepSessionsCounter f = new BadSleepSessionsCounter();
        SleepAnalysisResult result = f.apply(testLog);
        assertEquals(1, result.getValue());
    }

    @Test
    void testNonSleepNightCounter() {
        NonSleepNightCounter f = new NonSleepNightCounter();
        SleepAnalysisResult result = f.apply(testLog);
        assertEquals(1L, result.getValue());
    }

    @Test
    void testUserChronotype() {
        UserChronotype f = new UserChronotype();
        SleepAnalysisResult result = f.apply(testLog);
        assertEquals("ГОЛУБЬ", result.getValue().toString());
    }
}
package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class MinSleepSessionDuration implements Function<List<SleepSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sessions) {
        SleepSession minSession = sessions.stream()
                .min(Comparator.comparingLong(
                        s -> Duration.between(
                                s.getBedTime(),
                                s.getAwakingTime())
                                .toMinutes()
                ))
                .orElseThrow();

        Duration minDuration = Duration.between(
                minSession.getBedTime(),
                minSession.getAwakingTime());

        long hours = minDuration.toHours();
        long minutes = minDuration.toMinutesPart();

        return new SleepAnalysisResult(
                "Минимальная продолжительность сессии сна",
                minDuration,
                d -> hours + " ч " + minutes + " минут "
                );
    }
}

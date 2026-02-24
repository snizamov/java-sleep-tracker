package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class MaxSleepSessionDuration implements Function<List<SleepSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sessions) {
        SleepSession maxSession = sessions.stream()
                .filter(s -> s.getBedTime() != null && s.getAwakingTime() != null)
                .max(Comparator.comparingLong(
                        s -> Duration.between(
                                        s.getBedTime(),
                                        s.getAwakingTime())
                                .toMinutes()
                ))
                .orElseThrow();

        Duration maxDuration = Duration.between(
                maxSession.getBedTime(),
                maxSession.getAwakingTime());
        long hours = maxDuration.toHours();
        long minutes = maxDuration.toMinutesPart();


        return new SleepAnalysisResult("Максимальная продолжительность сессии сна",
                maxDuration,
                d -> hours + " ч " + minutes + " минут "
        );
    }
}

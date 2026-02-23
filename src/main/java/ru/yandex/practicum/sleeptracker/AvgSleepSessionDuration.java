package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class AvgSleepSessionDuration implements Function<List<SleepSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sessions) {
        Duration totalDuration = sessions.stream()
                .map(s -> Duration.between(
                        s.getBedTime(),
                        s.getAwakingTime()
                ))
                .reduce(Duration.ZERO, Duration::plus);

        long avgMinutes = totalDuration.toMinutes()/sessions.size();
        Duration avgDuration = Duration.ofMinutes(avgMinutes);

        long hours = avgDuration.toHours();
        long minutes = avgDuration.toMinutesPart();

        return new SleepAnalysisResult(
                "Средняя продолжительность сессии сна",
                avgDuration,
                d -> hours + " ч " + minutes + " минут "
        );
    }
}

package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class BadSleepSessionsCounter implements Function<List<SleepSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sessions) {
        List<SleepSession> badSessions = sessions.stream()
                .filter(s -> s.getSleepQuality().equals("BAD"))
                .toList();

        return new SleepAnalysisResult<>(
                "Количество сессий с плохим качеством сна",
                badSessions.size(),
                Object::toString
        );
    }
}

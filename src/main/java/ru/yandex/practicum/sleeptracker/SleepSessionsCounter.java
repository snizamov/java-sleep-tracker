package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class SleepSessionsCounter implements Function<List<SleepSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sessions) {
        return new SleepAnalysisResult(
                "Общее количество сессий сна",
                sessions.size(),
                Object::toString);
    }
}

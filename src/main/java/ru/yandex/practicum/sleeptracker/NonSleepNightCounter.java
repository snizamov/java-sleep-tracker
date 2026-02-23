package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class NonSleepNightCounter implements Function<List<SleepSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sessions) {
        LocalDateTime firstNightDateTime = sessions.stream()
                .min(Comparator.comparing(SleepSession::getBedTime))
                .orElseThrow().getBedTime();

        LocalDate dateFrom;

        if (firstNightDateTime.toLocalTime().isBefore(LocalTime.of(12, 0))) {
            dateFrom = firstNightDateTime.toLocalDate().minusDays(1);
        } else {
            dateFrom = firstNightDateTime.toLocalDate();
        }

        LocalDate dateTo = sessions.stream()
                .max(Comparator.comparing(SleepSession::getAwakingTime))
                .orElseThrow().getAwakingTime().toLocalDate();

        long count = sessions.stream()
                .filter(s -> s.getBedTime().toLocalDate()
                        .isBefore(s.getAwakingTime().toLocalDate())
                        ||
                        s.getBedTime().toLocalTime()
                                .isBefore(LocalTime.of(6, 0))
                )
                .count();

        long nonSleepNights = (Period.between(dateFrom, dateTo).getDays()) - count;

        return new SleepAnalysisResult("Количество бессонных ночей",
                nonSleepNights,
                Object::toString);
    }
}

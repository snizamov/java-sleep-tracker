package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserChronotype implements Function<List<SleepSession>, SleepAnalysisResult<UserClassificators>> {
    @Override
    public SleepAnalysisResult<UserClassificators> apply(List<SleepSession> sessions) {

        Map<LocalDate, List<SleepSession>> nights = sessions.stream()
                .filter(s -> s.getAwakingTime() != null && s.getBedTime() != null)
                .filter(s -> s.getBedTime().toLocalDate()
                        .isBefore(s.getAwakingTime().toLocalDate()) ||
                        s.getBedTime().toLocalTime()
                                .isBefore(LocalTime.of(6, 0)))
                .collect(Collectors.groupingBy(s -> s.getAwakingTime().toLocalDate()));

        if (nights.isEmpty()) {
            return new SleepAnalysisResult<>(
                    "Ваш хронотип",
                    UserClassificators.ГОЛУБЬ,
                    Objects::toString
            );
        }

        Map<UserClassificators, Long> nightsClassification = nights.values().stream()
                .map(this::classifyNight)
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        long maxValue = nightsClassification.values().stream()
                .max(Long::compare)
                .orElse(0L);

        long counterMaxValue = nightsClassification.values().stream()
                .filter(v -> v == maxValue)
                .count();

        UserClassificators result;

        if (counterMaxValue > 1) {
            result = UserClassificators.ГОЛУБЬ;
        } else {
            result = nightsClassification.entrySet().stream()
                    .filter(k -> k.getValue() == maxValue)
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(UserClassificators.ГОЛУБЬ);
        }

        return new SleepAnalysisResult<>(
                "Ваш хронотип",
                result,
                Objects::toString
        );
    }

    private UserClassificators classifyNight(List<SleepSession> sessions) {
        //определение временного промежутка ночной сессии,
        //если за одну ночь было несколько сессий (с пробуждениями)
        LocalTime earliestBedTime = sessions.stream()
                .map(SleepSession::getBedTime)
                .min(Comparator.naturalOrder())
                .orElseThrow()
                .toLocalTime();

        LocalTime latestAwakingTime = sessions.stream()
                .map(SleepSession::getAwakingTime)
                .max(Comparator.naturalOrder())
                .orElseThrow()
                .toLocalTime();

        if (earliestBedTime.isBefore(LocalTime.of(22, 1,1)) &&
                latestAwakingTime.isBefore(LocalTime.of(7, 1,1))) {
            return UserClassificators.ЖАВОРОНОК;
        }

        if (earliestBedTime.isAfter(LocalTime.of(22, 59,59)) &&
                latestAwakingTime.isAfter(LocalTime.of(8, 59,59))) {
            return UserClassificators.СОВА;
        }

        return UserClassificators.ГОЛУБЬ;
    }
}

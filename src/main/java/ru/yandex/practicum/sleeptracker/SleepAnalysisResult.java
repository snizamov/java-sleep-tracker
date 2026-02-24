package ru.yandex.practicum.sleeptracker;

import java.util.function.Function;

public class SleepAnalysisResult<T> {
    private final String description;
    private final T value;
    private final Function<T, String> formatter;

    public SleepAnalysisResult(String description, T value, Function<T, String> formatter) {
        this.description = description;
        this.value = value;
        this.formatter = formatter;
    }

    @Override
    public String toString() {
        return description + ": " + formatter.apply(value);
    }

    public T getValue() {
        return value;
    }
}

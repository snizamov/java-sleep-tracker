package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;

public class SleepSession {
    LocalDateTime bedTime;
    LocalDateTime awakingTime;
    String sleepQuality;

    public SleepSession(LocalDateTime bedTime, LocalDateTime awakingTime, String sleepQuality) {
        this.bedTime = bedTime;
        this.awakingTime = awakingTime;
        this.sleepQuality = sleepQuality;
    }

    public LocalDateTime getBedTime() {
        return bedTime;
    }

    public LocalDateTime getAwakingTime() {
        return awakingTime;
    }

    public String getSleepQuality() {
        return sleepQuality;
    }
}

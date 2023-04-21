package com.example.mobile.driverlicense.mdoc.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DurationUtils {
    public static long getAge(LocalDateTime birthdate) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/New_York"));
        Duration duration = Duration.between(birthdate, now);
        long ageInYears = duration.toDays() / 365;
        return ageInYears;
    }
}
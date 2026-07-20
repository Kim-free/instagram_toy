package com.example.instagram.common.util;

import java.time.Duration;
import java.time.LocalDateTime;

public final class ElapsedTimeFormatter {

    private ElapsedTimeFormatter() {
    }

    public static String format(LocalDateTime createdAt) {
        long minutes = Duration.between(createdAt, LocalDateTime.now()).toMinutes();

        if (minutes < 1) {
            return "방금 전";
        }

        if (minutes < 60) {
            return minutes + "분 전";
        }

        long hours = minutes / 60;
        if (hours < 24) {
            return hours + "시간 전";
        }

        return hours / 24 + "일 전";
    }
}

package com.github.kokecena.skymus.commons;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    public static long parseToSeconds(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String[] data = time.split(":");
        LocalTime localTime = LocalTime.parse(data.length != 3 ? "00:".concat(time) : time, formatter);
        return localTime.toSecondOfDay();
    }
}

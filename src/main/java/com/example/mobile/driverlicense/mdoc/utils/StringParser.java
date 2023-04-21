package com.example.mobile.driverlicense.mdoc.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

public class StringParser {
    
    /**
     * default DateTimeFormatter patten is "yyyy-MM-dd"
     * @param dateStr
     * @return
     */
    public static LocalDateTime parseToLocalDateTime(String dateStr) {
        return parseToLocalDateTime(dateStr, null);
    }

    /**
     * example pattern to use "yyyy-MM-dd HH:mm" "yyyy-MM-dd'T'HH:mm:ss.SSSX"
     * @param dateStr
     * @param datePattern
     * @return
     */
    public static LocalDateTime parseToLocalDateTime(String dateStr, String datePattern) {
        String pattern = StringUtils.isNotBlank(datePattern)? datePattern : "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateStr, formatter);
    }
}

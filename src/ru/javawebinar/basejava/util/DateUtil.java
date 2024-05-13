package ru.javawebinar.basejava.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String format(LocalDate date) {
        String result;
        if (date == null) {
            result = "";
        } else if (date.equals(NOW)) {
            result = "Сейчас";
        } else {
            result = date.format(DATE_FORMATTER);
        }
        return result;
    }

    public static LocalDate parse(String date) {
        LocalDate result;
        if (date == null || date.trim().isEmpty() || "Сейчас".equals(date)) {
            result = NOW;
        } else {
            try {
                YearMonth yearMonth = YearMonth.parse(date, DATE_FORMATTER);
                result = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
            } catch (DateTimeParseException e) {
                result = NOW;
            }
        }
        return result.isAfter(LocalDate.now()) ? NOW : result;
    }
}

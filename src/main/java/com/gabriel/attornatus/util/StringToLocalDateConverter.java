package com.gabriel.attornatus.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateConverter {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ss/MM/yyyy");

    public static LocalDate convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        return LocalDate.parse(source, formatter);
    }
}

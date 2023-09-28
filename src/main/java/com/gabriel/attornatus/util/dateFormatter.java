package com.gabriel.attornatus.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class dateFormatter {

    public static LocalDate formattedDate(String date) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, format);
    }
}

package com.birzeit.myhospital.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalDateFormatter {
    static DateTimeFormatter dateObj = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    static DateTimeFormatter timeObj = DateTimeFormatter.ofPattern("HH:mm");
    static DateTimeFormatter dateTimeObj = DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss");


    public static String formatDate (LocalDate localDate) {
        if(localDate == null)
            return null;
        return localDate.format(dateObj);
    }

    public static String formatTime (LocalTime localTime) {
        if(localTime == null)
            return null;
        return localTime.format(timeObj);
    }

    public static String formatDateAndTime (LocalDateTime localDateTime) {
        if(localDateTime == null)
            return null;
        return localDateTime.format(dateTimeObj);
    }

    public static LocalDate makeDateFromString (String date) {
        return LocalDate.parse(date, dateObj);
    }

    public static LocalTime makeTimeFromString (String time) {
        return LocalTime.parse(time, timeObj);
    }

}
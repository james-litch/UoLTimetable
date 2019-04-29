package com.group51.uoltimetable.utilities;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeHelper {
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private DateTimeFormatter dateTimeFormatter;
    private DateTimeFormatter timeFormatter;
    private DateTimeFormatter dateFormatter;
    private String datePattern = "yyyy-MM-dd";
    private String dateTimePattern = "yyyy-MM-dd HH:mm:ss";
    private String timePattern = "HH:mm";

    public DateTimeHelper() {
    }

    public String getDateToday() {
        LocalDate dateToday = LocalDate.now();
        dateFormatter = DateTimeFormatter.ofPattern(datePattern);

        return dateToday.format(dateFormatter);
    }

    public int getDayOfToday() {

        return LocalDate.now().getDayOfWeek().getValue();
    }

    public String getDateOfDay(int numDays, Boolean nextWeek) {
        if (nextWeek) {
            numDays = numDays + 7;
        }

        dateFormat = new SimpleDateFormat(datePattern, Locale.UK);
        calendar = Calendar.getInstance(Locale.UK);

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());

        // adds number of days onto the start of monday
        calendar.add(Calendar.DATE, numDays);
        Date date = calendar.getTime();


        return dateFormat.format(date);
    }


    public String dateStringFromInts(int dayOfMonth, int month, int year) {
        String dayString = Integer.toString(dayOfMonth);
        String monthString = Integer.toString(month);
        String yearString = Integer.toString(year);
        if (dayOfMonth < 10) {
            dayString = "0" + dayString;
        }

        if (month < 10) {
            monthString = "0" + monthString;
        }
        return yearString + "-" + monthString + "-" + dayString;
    }


    public boolean inTimeRange(String lectureTime) {
        //broken
        dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern);

        LocalDateTime startTime = LocalDateTime.parse(lectureTime, dateTimeFormatter);
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1);

        return currentTime.isAfter(startTime) && currentTime.isBefore(endTime);
    }

    public String getStringFromDate(String dateTimeString, boolean addHour) {

        dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern);
        timeFormatter = DateTimeFormatter.ofPattern(timePattern);

        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, dateTimeFormatter);
        LocalTime startTime = dateTime.toLocalTime();

        if (addHour) {
            LocalTime endTime = startTime.plusHours(1);
            return endTime.format(timeFormatter);
        }
        return startTime.format(timeFormatter);
    }
}

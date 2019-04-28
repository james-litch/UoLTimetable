package com.group51.uoltimetable.utilities;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeHelper {
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String datePattern = "dd-MM-yyyy";

    public DateTimeHelper() {
    }

    public String getDateToday() {
        Date date = Calendar.getInstance().getTime();
        dateFormat = new SimpleDateFormat(datePattern, Locale.UK);
        return dateFormat.format(date);

    }

    public int getDayOfToday() {
        calendar = Calendar.getInstance(Locale.UK);
        int dayOfToday = calendar.get(Calendar.DAY_OF_WEEK);
        //monday is 1 not 0.
        return dayOfToday - 1;

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
        return dayString + "-" + monthString + "-" + yearString;
    }


    public boolean inTimeRange(String startString, String endString, String lectureDate) {
        LocalTime startTime = LocalTime.parse(startString);
        LocalTime endTime = LocalTime.parse(endString);
        LocalTime currentTime = LocalTime.now();
        return currentTime.isAfter(startTime) && currentTime.isBefore(endTime) && lectureDate.equals(getDateToday());
    }


}

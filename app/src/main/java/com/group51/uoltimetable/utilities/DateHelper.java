package com.group51.uoltimetable.utilities;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateHelper {
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String pattern = "dd-MM-yyyy";

    public DateHelper() {
    }

    public String getDateToday() {
        Date date = Calendar.getInstance().getTime();
        dateFormat = new SimpleDateFormat(pattern, Locale.UK);
        return dateFormat.format(date);

    }

    public int getDayOfToday() {
        calendar = Calendar.getInstance(Locale.UK);
        int dayOfToday = calendar.get(Calendar.DAY_OF_WEEK);
        //monday is 1 not 0.
        return dayOfToday - 1;

    }

    public String getDateOfDay(int numDays, Boolean nextWeek) {
        //TODO clean this up
        if (nextWeek) {
            numDays = numDays + 7;
        }

        dateFormat = new SimpleDateFormat(pattern, Locale.UK);
        calendar = Calendar.getInstance(Locale.UK);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date firstDay = calendar.getTime();
        // adds number of days onto the start of monday
        calendar.add(Calendar.DATE, numDays);
        Date date = calendar.getTime();
        System.out.println("this is the first day of week  " + firstDay + "  this is the new date : " + date);

        return dateFormat.format(date);
    }


}

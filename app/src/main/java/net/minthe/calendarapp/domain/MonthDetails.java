package net.minthe.calendarapp.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Utility class to extract details about a particular month, specified by
 * any millisecond since Jan 1, 1970.
 */
public class MonthDetails {

    private long date;

    public MonthDetails(long date) {
        this.date = date;
    }

    public MonthDetails getNextMonth() {
        Calendar c = GregorianCalendar.getInstance();
        c.clear();
        c.setTime(new Date(date));
        c.add(Calendar.MONTH, 1);
        return new MonthDetails(c.getTimeInMillis());
    }

    public MonthDetails getPrevMonth() {
        Calendar c = GregorianCalendar.getInstance();
        c.clear();
        c.setTime(new Date(date));
        c.add(Calendar.MONTH, -1);
        return new MonthDetails(c.getTimeInMillis());
    }

    /**
     * @return long - The instant which represents midnight on the first day of the month.
     */
    public long getMonthStart() {
        Calendar c = GregorianCalendar.getInstance();
        c.clear();
        c.setTime(new Date(date));
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTimeInMillis();
    }

    /**
     * @return long - The instant which represents 1 second before midnight on the last day
     * of the month.
     */
    public long getMonthEnd() {
        Calendar c = GregorianCalendar.getInstance();
        c.clear();
        c.setTime(new Date(date));
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTimeInMillis();
    }

    /**
     * @return int - The number of days in the month.
     */
    public int getNumDays() {
        Calendar c = GregorianCalendar.getInstance();
        c.clear();
        c.setTime(new Date(date));
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * @return String - The full month name and year, separated by a space. e.g. "December 2012"
     */
    public String getMonthName() {
        return new SimpleDateFormat("MMMM YYYY", Locale.US)
                .format(new Date(date));
    }

    /**
     * @return int - zero-indexed day of the week on which the month begins.
     */
    public int getFirstWeekday() {
        Calendar c = GregorianCalendar.getInstance();
        c.clear();
        c.setTime(new Date(date));
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * @return long - Some instant within the month, represented as milliseconds since
     * January 1, 1970.
     */
    public long getDate() {
        return date;
    }
}

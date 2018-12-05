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

    /**
     * @return A MonthDetails instance representing the next chronological month
     */
    public MonthDetails getNextMonth() {
        Calendar c = GregorianCalendar.getInstance();
        c.clear();
        c.setTime(new Date(date));
        c.add(Calendar.MONTH, 1);
        return new MonthDetails(c.getTimeInMillis());
    }

    /**
     * @return A MonthDetails instance representing the previous chronological month
     */
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
     * @return int - the current year on the Gregorian Calendar. Not defined for any dates
     * before 1 Jan, 1970
     */
    public int getYear() {
        Calendar c = GregorianCalendar.getInstance();
        c.clear();
        c.setTime(new Date(date));
        return c.get(Calendar.YEAR);
    }

    /**
     * @return int - the current month, zero-indexed (corresponds to Calendar.MONTH)
     */
    public int getMonth() {
        Calendar c = GregorianCalendar.getInstance();
        c.clear();
        c.setTime(new Date(date));
        return c.get(Calendar.MONTH);
    }

    /**
     * @param day Which day of the month
     * @return long - the instant representing 00:00:00 on the specified day
     */
    public long getDayStart(int day) {
        Calendar c = GregorianCalendar.getInstance();
        c.clear();
        c.setTime(new Date(date));
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTimeInMillis();
    }

    /**
     * @param day Which day of the month
     * @return long - the instant representing 23:59:59 on the specified day
     */
    public long getDayEnd(int day) {
        Calendar c = GregorianCalendar.getInstance();
        c.clear();
        c.setTime(new Date(date));
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTimeInMillis();
    }

    /**
     * @return long - Some instant within the month, represented as milliseconds since
     * January 1, 1970.
     */
    public long getDate() {
        return date;
    }
}

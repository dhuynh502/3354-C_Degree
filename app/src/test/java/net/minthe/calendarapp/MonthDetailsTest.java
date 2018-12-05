package net.minthe.calendarapp;

import net.minthe.calendarapp.domain.MonthDetails;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

public class MonthDetailsTest {

    @Test
    public void testFirstWeekday() {
        final int[] years = {2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007};
        // March of each year started on
        // Wed, Thur, Fri, Sat, Mon, Tue, Wed, Thur
        final int[] start_days = {3, 4, 5, 6, 1, 2, 3, 4};

        assertEquals(years.length, start_days.length); // Sanity check

        Calendar c = GregorianCalendar.getInstance();

        for (int i = 0; i < years.length; i++) {
            c.clear();
            c.set(Calendar.YEAR, years[i]);
            c.set(Calendar.MONTH, Calendar.MARCH);
            c.set(Calendar.DAY_OF_MONTH, 10); // arbitrary

            MonthDetails md = new MonthDetails(c.getTimeInMillis());

            assertEquals(start_days[i], md.getFirstWeekday());
        }
    }

    @Test
    public void testMonthName() {

    }

    @Test
    public void testNumDays() {
    }

    @Test
    public void testNextMonth() {

    }

    @Test
    public void testPrevMonth() {

    }

    @Test
    public void testMonth(){
        // These timestamps represent 15 {Month} 2018, 00:00:00
        final long[] instants = {
                1515974400000L, // Jan
                1518652800000L, // Feb
                1521072000000L, // Mar
                1523750400000L, // April
                1526342400000L, // May
                1529020800000L, // June
                1531612800000L, // July
                1534291200000L, // August
                1536969600000L, // September
                1539561600000L, // October
                1542240000000L, // November
                1544832000000L  // December
        };

        for (int i = 0; i < instants.length; i++) {
            MonthDetails md = new MonthDetails(instants[i]);

            // The index of each month matches the ordering
            assertEquals(i, md.getMonth());
        }
    }

    @Test
    public void testYear() {
        final int[] years = {2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007};
        //testing on MonthDetails.getYear() function
        Calendar c = GregorianCalendar.getInstance();
        for (int i = 0; i < years.length; i++) {
            c.clear();
            c.set(Calendar.YEAR, years[i]);
            MonthDetails md = new MonthDetails(c.getTimeInMillis());
            assertEquals(years[i], md.getYear());
        }
    }
}

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
}

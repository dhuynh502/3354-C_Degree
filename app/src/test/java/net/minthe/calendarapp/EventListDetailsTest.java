package net.minthe.calendarapp;

import org.junit.Test;
import net.minthe.calendarapp.domain.EventListDetails;
import net.minthe.calendarapp.domain.MonthDetails;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * Test class to test the getEventsForDay method
 * Created by Duc
 *
 */
public class EventListDetailsTest {

    @Test
    public void getEventsForDay() {
        Calendar c = GregorianCalendar.getInstance();
        int start =0;
        int end = 0;
        for (int i =0; i<31;i++)
        {
            end = start +24 * 60*60*1000;

        }
        assertEquals(end, start);
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
package net.minthe.calendarapp;

import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


import static org.junit.Assert.*;

/**
 * Test class to test the getDurationSeconds method in the CreateEventActivity class
 * Created by Kristie
 *
 */
public class CreateEventTest {

    CreateEventActivity createEventTest;

    // Constructor
    public CreateEventTest() {

    }

    @Before
    public void setUp() throws Exception {
        createEventTest = new CreateEventActivity();
    }

    @Test
    public void getDurationSeconds() throws Exception{
        Clock clock = Clock.systemDefaultZone();
        ZoneId zoneId = ZoneId.systemDefault();

        // Set starting time in past
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
        // Convert LocalDateTime format to Date format
        Date start = new Date();
        oneWeekAgo = LocalDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault());
        Date startDate = Date.from(oneWeekAgo.atZone(ZoneId.systemDefault()).toInstant());

        // Set ending time to current time
        LocalDateTime current = LocalDateTime.now();
        // Convert LocalDateTime format to Date format
        Date end = new Date();
        current = LocalDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault());
        Date endDate = Date.from(current.atZone(ZoneId.systemDefault()).toInstant());

        assertEquals(-1, createEventTest.getDurationSeconds(startDate, endDate));
    }
}
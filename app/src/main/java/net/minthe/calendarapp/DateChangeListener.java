package net.minthe.calendarapp;

import net.minthe.calendarapp.domain.MonthDetails;

/**
 * Class to listen for date changes
 *
 */
public interface DateChangeListener {
    void onDateChange(int day, MonthDetails md);
}

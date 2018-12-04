package net.minthe.calendarapp;

import net.minthe.calendarapp.domain.MonthDetails;

public interface DateChangeListener {
    void onDateChange(int day, MonthDetails md);
}

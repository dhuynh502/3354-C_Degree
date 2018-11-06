package net.minthe.calendarapp.domain;

import java.time.DayOfWeek;
import java.util.Date;
import java.util.List;

public class PeriodicEvent extends Event {
    private List<DayOfWeek> days;

    public PeriodicEvent(String eventName, Date dateTime, long duration, String notes, List<DayOfWeek> days, long primaryEventCategory) {
        super(eventName, dateTime, duration, notes, primaryEventCategory);
        this.days = days;
    }

    public PeriodicEvent(String eventName, Date dateTime, long duration, String notes, List<DayOfWeek> days) {
        super(eventName, dateTime, duration, notes);
        this.days = days;
    }

    @Override
    public boolean isPeriodic() {
        return true;
    }
}

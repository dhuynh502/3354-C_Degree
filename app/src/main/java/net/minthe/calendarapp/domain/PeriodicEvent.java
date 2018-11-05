package net.minthe.calendarapp.domain;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class PeriodicEvent extends Event {
    private List<DayOfWeek> days;

    public PeriodicEvent(Instant dateTime, Duration duration, List<DayOfWeek> days, EventCategory primaryEventCategory, List<EventCategory> eventCategories) {
        super(dateTime, duration, primaryEventCategory, eventCategories);
        this.days = days;
    }

    public PeriodicEvent(Instant dateTime, Duration duration, List<DayOfWeek> days) {
        super(dateTime, duration);
        this.days = days;
    }

    @Override
    public boolean isPeriodic() {
        return true;
    }
}

package net.minthe.calendarapp.domain;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private Instant dateTime;
    private Duration duration;
    private EventCategory primaryEventCategory;
    private List<EventCategory> eventCategories;

    public Event(Instant dateTime, Duration duration, EventCategory primaryEventCategory, List<EventCategory> eventCategories) {
        this.dateTime = dateTime;
        this.duration = duration;
        this.primaryEventCategory = primaryEventCategory;
        this.eventCategories = eventCategories;
    }

    public Event(Instant dateTime, Duration duration) {
        this.dateTime = dateTime;
        this.duration = duration;
        this.primaryEventCategory = null;
        this.eventCategories = new ArrayList<>();
    }

    public void addEventCategory(EventCategory category) {
        eventCategories.add(category);
    }

    public void removeEventCategory(EventCategory category) {
        eventCategories.remove(category);
    }

    public boolean isPeriodic() {
        return false;
    }

    public List<EventCategory> getEventCategories() {
        return eventCategories;
    }

    public EventCategory getPrimaryEventCategory() {
        return primaryEventCategory;
    }

    public void setPrimaryEventCategory(EventCategory primaryEventCategory) {
        this.primaryEventCategory = primaryEventCategory;
    }

    public Instant getDateTime() {
        return dateTime;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}

package net.minthe.calendarapp.domain;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "event", indices = {@Index("eventId"), @Index("event_unixtime")})
public class Event {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "event_id")
    private int eventId;
    @ColumnInfo(name = "event_unixtime")
    private Instant dateTime;
    @ColumnInfo(name = "duration")
    private Duration duration;
    @ColumnInfo(name = "primary_category_id")
    @ForeignKey(entity = EventCategory.class,
            parentColumns = "category_id",
            childColumns = "primary_category_id")
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

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
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

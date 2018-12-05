package net.minthe.calendarapp.domain;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "event", indices = {@Index("event_id"), @Index("event_unixtime")})
public class Event {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "event_id")
    private long eventId;
    @ColumnInfo(name = "event_name")
    String eventName;
    @ColumnInfo(name = "event_unixtime")
    private Date dateTime;

    // in seconds
    @ColumnInfo(name = "duration")
    private long duration;
    @ColumnInfo(name = "notes")
    private String notes;
    @ColumnInfo(name = "primary_category_id")
    @ForeignKey(entity = EventCategory.class,
            parentColumns = "category_id",
            childColumns = "primary_category_id")
    private long primaryEventCategory;

    public Event(String eventName, Date dateTime, long duration, String notes, long primaryEventCategory) {
        this.eventName = eventName;
        this.dateTime = dateTime;
        this.duration = duration;
        this.notes = notes;
        this.primaryEventCategory = primaryEventCategory;
    }

    @Ignore
    public Event(long eventId, String eventName, Date dateTime, long duration, String notes) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.dateTime = dateTime;
        this.duration = duration;
        this.notes = notes;
    }


    @Ignore
    public Event(String eventName, Date dateTime, long duration, String notes) {
        this.eventName = eventName;
        this.dateTime = dateTime;
        this.duration = duration;
        this.notes = notes;
        this.primaryEventCategory = -1;
    }

    public boolean isPeriodic() {
        return false;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getPrimaryEventCategory() {
        return primaryEventCategory;
    }

    public void setPrimaryEventCategory(long primaryEventCategory) {
        this.primaryEventCategory = primaryEventCategory;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}

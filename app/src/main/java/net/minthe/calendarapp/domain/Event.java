package net.minthe.calendarapp.domain;

import java.time.Duration;
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
    @ColumnInfo(name = "event_unixtime")
    private Date dateTime;
    @ColumnInfo(name = "duration")
    private long duration;
    @ColumnInfo(name = "notes")
    private String notes;
    @ColumnInfo(name = "primary_category_id")
    @ForeignKey(entity = EventCategory.class,
            parentColumns = "category_id",
            childColumns = "primary_category_id")
    private long primaryEventCategory;

    public Event(Date dateTime, long duration, String notes, long primaryEventCategory) {
        this.dateTime = dateTime;
        this.duration = duration;
        this.notes = notes;
        this.primaryEventCategory = primaryEventCategory;
    }

    @Ignore
    public Event(Date dateTime, long duration, String notes) {
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

package net.minthe.calendarapp.domain;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Class to handle user inputted data for events and store in database
 *
 */

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

    /**
     * Method to check if event is periodic
     *
     * @return true or false depending on if the event is periodic
     */
    public boolean isPeriodic() {
        return false;
    }

    /**
     * Method to get the event's id
     *
     * @return the id of the event
     */
    public long getEventId() {
        return eventId;
    }

    /**
     * Method to set the event's id
     *
     * @param eventId the id of the event (inputted by user)
     */
    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    /**
     * Method to collect the event's primary category, set by the user
     *
     * @return the event's primary category
     */
    public long getPrimaryEventCategory() {
        return primaryEventCategory;
    }

    /**
     * Method to set the event's primary category
     *
     * @param primaryEventCategory the event's primary category (inputted by user)
     */
    public void setPrimaryEventCategory(long primaryEventCategory) {
        this.primaryEventCategory = primaryEventCategory;
    }

    /**
     * Method to get the event's name
     *
     * @return the name of the event
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Method to set the event's name
     *
     * @param eventName the name of the event (inputted by user)
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Method to get the notes section of the event
     *
     * @return the event's notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Method to set the event's notes
     *
     * @param notes the event's notes (inputted by user)
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Method to get the date/time for event
     *
     * @return the date/time
     */
    public Date getDateTime() {
        return dateTime;
    }

    /**
     * Method to set the date/time
     *
     * @param dateTime the date/time (inputted by user)
     */
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Method to get the duration of an event
     *
     * @return the event's duration (length)
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Method to set the duration of the event
     *
     * @param duration the event's duration (length)
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }
}

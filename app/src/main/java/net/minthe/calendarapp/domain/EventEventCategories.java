package net.minthe.calendarapp.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

/**
 * Class to handle event categories
 *
 */
@Entity(tableName = "event_event_categories",
        primaryKeys = {"event_id", "category_id"},
        indices = {@Index("event_id"), @Index("category_id")},
        foreignKeys = {
                @ForeignKey(entity = Event.class,
                        parentColumns = "event_id",
                        childColumns = "event_id"),
                @ForeignKey(entity = EventCategory.class,
                        parentColumns = "category_id",
                        childColumns = "category_id")
        }
)
public class EventEventCategories {
    @ColumnInfo(name = "event_id")
    public final long eventId;
    @ColumnInfo(name = "category_id")
    public final long categoryId;

    public EventEventCategories(long eventId, long categoryId) {
        this.eventId = eventId;
        this.categoryId = categoryId;
    }
}

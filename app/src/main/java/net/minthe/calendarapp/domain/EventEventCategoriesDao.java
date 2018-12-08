package net.minthe.calendarapp.domain;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

/**
 * Class to insert event categories into database
 *
 */
@Dao
public interface EventEventCategoriesDao {
    @Insert
    void insert(EventEventCategories... eventEventCategories);

    @Query("SELECT event_category.* FROM event_event_categories, event_category " +
            "WHERE event_id = :event_id " +
            "AND event_event_categories.category_id = event_category.category_id")
    List<EventCategory> getCategoriesForEvent(long event_id);

    @Query("SELECT event.* FROM event_event_categories, event " +
            "WHERE category_id = :category_id " +
            "AND event_event_categories.event_id = event.event_id")
    List<Event> getEventsForCategory(long category_id);
}

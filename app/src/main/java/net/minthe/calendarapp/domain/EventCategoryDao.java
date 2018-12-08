package net.minthe.calendarapp.domain;

import androidx.room.Dao;
import androidx.room.Insert;

/**
 * Class to insert event categories into database
 *
 */
@Dao
public interface EventCategoryDao {
    @Insert
    void insert(EventCategory... eventCategories);
}

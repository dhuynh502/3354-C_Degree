package net.minthe.calendarapp.domain;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface EventCategoryDao {
    @Insert
    void insert(EventCategory... eventCategories);
}

package net.minthe.calendarapp.domain;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface EventDao {
    @Query("SELECT * FROM event " +
            "WHERE event_unixtime BETWEEN :from and :to " +
            "OR (event_unixtime + duration) BETWEEN :from and :to " +
            "ORDER BY event_unixtime ASC")
    List<Event> findEventsBetween(long from, long to);

    @Query("SELECT * FROM event " +
            "WHERE event_id = :event_id")
    Event findByID(long event_id);

    @Query("SELECT COUNT(*) from event")
    long getCount();

    @Query("SELECT * FROM event")
    List<Event> getAll();

    @Insert
    void insertAll(Event... events);

    @Delete
    void delete(Event event);
}

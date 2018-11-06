package net.minthe.calendarapp.domain;

import java.time.Instant;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface EventDao {
    @Query("SELECT * FROM event " +
            "WHERE event_unixtime BETWEEN :from and :to " +
            "OR (event_unixtime + duration) BETWEEN :from and :to")
    List<Event> findEventsBetween(long to, long from);

    @Query("SELECT * FROM event " +
            "WHERE event_id = :event_id")
    Event findByID(long event_id);

    @Insert
    void insertAll(Event... events);

    @Delete
    void delete(Event event);
}

package net.minthe.calendarapp.domain;

import java.util.Date;

import androidx.room.TypeConverter;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return new Date(value);
    }

    @TypeConverter
    public static long fromDate(Date value) {
        return value.getTime();
    }
}

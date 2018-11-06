package net.minthe.calendarapp.domain;

import android.graphics.Color;
import android.graphics.ColorSpace;
import android.util.Half;

import java.time.Duration;
import java.util.Date;

import androidx.annotation.HalfFloat;
import androidx.annotation.NonNull;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

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

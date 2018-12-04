package net.minthe.calendarapp.domain;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Event.class, EventCategory.class, EventEventCategories.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase ourInstance = null;

    public static AppDatabase getInstance() {
        return ourInstance;
    }

    public static void instantiate(Context context) {
        if (ourInstance != null) return;

        ourInstance = Room.databaseBuilder(context, AppDatabase.class, "event-db")
                .allowMainThreadQueries()
                .build();
    }

    public AppDatabase() {
    }

    public abstract EventDao eventDao();
    public abstract EventCategoryDao eventCategoryDao();
    public abstract EventEventCategoriesDao eventEventCategoriesDao();
}

package net.minthe.calendarapp.domain;

import android.graphics.Color;

import java.util.Objects;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Class for event categories
 *
 */
@Entity(tableName = "event_category",
        indices = {@Index("category_id")} )
public class EventCategory {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id")
    private int categoryId;
    @ColumnInfo(name = "category_name")
    private String categoryName;
    @ColumnInfo(name = "color")
    private int color;

    /**
     * Method to give events categories
     *
     * @param categoryName the name of the assigned category
     * @param color the category color
     */
    public EventCategory(String categoryName, int color) {
        this.categoryName = categoryName;
        this.color = color;
    }

    /**
     * Method to get the name of the category
     *
     * @return the name of the category
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Method to set the name of the category
     *
     * @param categoryName the name of the category
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Method to get the category color
     *
     * @return the category color
     */
    public int getColor() {
        return color;
    }

    /**
     * Method to set the category color
     *
     * @param color the category color
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Method to get the category id
     *
     * @return the category's id
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Method to set the id of the category
     *
     * @param categoryId the category's id
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventCategory that = (EventCategory) o;
        return categoryId == that.categoryId &&
                Objects.equals(categoryName, that.categoryName) &&
                Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, categoryName, color);
    }

    @Override
    public String toString() {
        return "EventCategory{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", color=" + color +
                '}';
    }
}

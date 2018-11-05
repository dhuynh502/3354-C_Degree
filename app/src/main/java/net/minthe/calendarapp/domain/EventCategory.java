package net.minthe.calendarapp.domain;

import android.graphics.Color;

import java.util.Objects;

public class EventCategory {
    private String categoryName;
    private Color color;

    public EventCategory(String categoryName, Color color) {
        this.categoryName = categoryName;
        this.color = color;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventCategory that = (EventCategory) o;
        return Objects.equals(categoryName, that.categoryName) &&
                Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {

        return Objects.hash(categoryName, color);
    }

    @Override
    public String toString() {
        return "EventCategory{" +
                "categoryName='" + categoryName + '\'' +
                ", color=" + color +
                '}';
    }
}

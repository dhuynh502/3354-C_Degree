package net.minthe.calendarapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.minthe.calendarapp.week.WeekViewActivity;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Class to show the main screen of the app
 *
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Agenda view button
     * Calls the EventListActivity
     *
     * @param view - contains the selected view
     */
    public void onEventList(View view) {
        Intent intent = new Intent(this, EventListActivity.class);
        startActivity(intent);
    }

    /**
     * Month view button
     * Calls the CustomMonthView
     *
     * @param view - contains the selected view
     */
    public void onCustomMonthView(View view) {
        Intent intent = new Intent(this, CustomMonthView.class);
        startActivity(intent);
    }

    /**
     * Weekly view button
     * Calls the WeekViewActivity
     *
     * @param view - contains the selected view
     */
    public void onWeekView(View view) {
        Intent intent = new Intent(this, WeekViewActivity.class);
        startActivity(intent);
    }
}

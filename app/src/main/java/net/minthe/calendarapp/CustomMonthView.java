package net.minthe.calendarapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.minthe.calendarapp.domain.Event;
import net.minthe.calendarapp.domain.MonthDetails;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class CustomMonthView extends AppCompatActivity
        implements EventFragment.OnListFragmentInteractionListener {

    private long selectedDate = 0;
    private int selectedDay = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_custom_month_view);

        Calendar c = GregorianCalendar.getInstance();
        c.setTime(new Date());
        long now = c.getTimeInMillis();

        MonthDetails md = new MonthDetails(now);

        MonthFragment monthFragment = MonthFragment.newInstance(now);
        EventFragment eventFragment = EventFragment.newInstance(1, md.getMonthStart(), md.getMonthEnd());

        monthFragment.setDateChangeListener(new DateChangeListener() {
            @Override
            public void onDateChange(int day, MonthDetails md) {
                EventFragment eventFragment = EventFragment.newInstance(
                        1,
                        md.getDayStart(day),
                        md.getDayEnd(day));
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.monthEventFragContainer, eventFragment, "evFrag")
                        .commit();

                TextView eventHeader = findViewById(R.id.eventHeader);
                eventHeader.setText("Events on " + day + " " + md.getMonthName());

                selectedDate = md.getDate();
                selectedDay = day;
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.monthEventFragContainer, eventFragment)
                .commit();

        fm.beginTransaction()
                .replace(R.id.monthFragContainer, monthFragment)
                .commit();
    }

    @Override
    public void onListFragmentInteraction(Event item) {
        Intent edit = new Intent(this, CreateEventActivity.class);
        edit.putExtra("NET_MINTHE_CALENDARAPP_SELECTED_DATE",
                new MonthDetails(selectedDate).getDayStart(selectedDay));
        edit.putExtra("NET_MINTHE_CALENDARAPP_EDIT_MODE", true);
        edit.putExtra("NET_MINTHE_CALENDARAPP_EVENT_ID", item.getEventId());
        startActivity(edit);
    }

    public void onEventAdd(View view) {
        Intent intent = new Intent(this, CreateEventActivity.class);
        intent.putExtra("NET_MINTHE_CALENDARAPP_SELECTED_DATE",
                new MonthDetails(selectedDate).getDayStart(selectedDay));
        startActivity(intent);
    }
}

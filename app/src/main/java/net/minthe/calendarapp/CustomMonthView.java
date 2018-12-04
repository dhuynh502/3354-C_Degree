package net.minthe.calendarapp;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_month_view);

        Calendar c = GregorianCalendar.getInstance();
        c.setTime(new Date());
        long date = c.getTimeInMillis();

        MonthDetails md = new MonthDetails(date);

        MonthFragment monthFragment = MonthFragment.newInstance(date);
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
        // TODO: Switch to edit/delete screen
    }
}

package net.minthe.calendarapp;

import android.os.Bundle;

import net.minthe.calendarapp.domain.Event;
import net.minthe.calendarapp.domain.MonthDetails;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class CustomMonthView extends AppCompatActivity implements EventFragment.OnListFragmentInteractionListener {

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

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.cmvFrame, eventFragment, "evFrag")
                .replace(R.id.cmvFrame, monthFragment, "cmFrag")
                .commit();
    }

    @Override
    public void onListFragmentInteraction(Event item) {

    }
}

package net.minthe.calendarapp;

import android.os.Bundle;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class CustomMonthView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_month_view);

        Calendar c = GregorianCalendar.getInstance();
        c.setTime(new Date());
        long date = c.getTimeInMillis();
        MonthFragment f = MonthFragment.newInstance(date);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.cmvFrame, f)
                .commit();
    }
}

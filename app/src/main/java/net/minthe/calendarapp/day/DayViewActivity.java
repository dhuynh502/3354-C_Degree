package net.minthe.calendarapp.day;

import android.net.Uri;
import android.os.Bundle;

import net.minthe.calendarapp.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class DayViewActivity extends AppCompatActivity
        implements DayViewFragment.OnFragmentInteractionListener {

    private final int EVENT_WIDTH = 750; // pixels

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view);

        long start = getIntent().getLongExtra("NET_MINTHE_CALENDARAPP_START_DATE", 0);
        long end = getIntent().getLongExtra("NET_MINTHE_CALENDARAPP_END_DATE", 0);

        DayViewFragment dvf = DayViewFragment.newInstance(start, end, EVENT_WIDTH, true);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.dayViewContainer, dvf)
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

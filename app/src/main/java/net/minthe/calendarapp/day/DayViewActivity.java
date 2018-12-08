package net.minthe.calendarapp.day;

import android.net.Uri;
import android.os.Bundle;

import net.minthe.calendarapp.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

/**
 * Class to show the day view
 *
 */
public class DayViewActivity extends AppCompatActivity
        implements DayViewFragment.OnFragmentInteractionListener {

    // Declare variables
    private final int EVENT_WIDTH = 750; // pixels

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view);

        // Get the start and end for the day view
        long start = getIntent().getLongExtra("NET_MINTHE_CALENDARAPP_START_DATE", 0);
        long end = getIntent().getLongExtra("NET_MINTHE_CALENDARAPP_END_DATE", 0);

        // Display events
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

package net.minthe.calendarapp;

import android.os.Bundle;

import net.minthe.calendarapp.domain.Event;

import java.util.Calendar;
import java.util.GregorianCalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

/**
 * This is a temporary activity meant to be used during development to ensure that
 * events are persisting correctly.
 */
public class EventListActivity extends AppCompatActivity implements EventFragment.OnListFragmentInteractionListener {

    private static long endTimestamp = 15440835000000L; // arbitrary date in 2459

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        Calendar c = GregorianCalendar.getInstance();

        EventFragment eventFragment = EventFragment.newInstance(
                1,
                c.getTimeInMillis(),
                endTimestamp);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.agendaContainer, eventFragment)
                .commit();
    }

    @Override
    public void onListFragmentInteraction(Event item) {

    }
}

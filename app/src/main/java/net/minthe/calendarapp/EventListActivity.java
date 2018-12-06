package net.minthe.calendarapp;

import android.content.Intent;
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

    // Declare variables
    private static long endTimestamp = 15440835000000L; // arbitrary date in 2459

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        replaceEventList();
    }

    /**
     * Method to update event list
     *
     */
    private void replaceEventList() {
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

    /**
     * Event list is updated when Agenda view is selected
     *
     */
    @Override
    protected void onPostResume() {
        super.onPostResume();
        replaceEventList();
    }

    /**
     * Method to create event list with details
     *
     * @param item - event instance
     */
    @Override
    public void onListFragmentInteraction(Event item) {
        //creating calendar object here and setting the time starting at 0
        Calendar c = GregorianCalendar.getInstance();
        c.clear();
        c.setTime(item.getDateTime());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        Intent edit = new Intent(this, CreateEventActivity.class);
        edit.putExtra("NET_MINTHE_CALENDARAPP_SELECTED_DATE",
                c.getTimeInMillis());
        edit.putExtra("NET_MINTHE_CALENDARAPP_EDIT_MODE", true);
        edit.putExtra("NET_MINTHE_CALENDARAPP_EVENT_ID", item.getEventId());
        startActivity(edit);
    }
}

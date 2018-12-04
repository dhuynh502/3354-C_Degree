package net.minthe.calendarapp;

import android.os.Bundle;

import net.minthe.calendarapp.domain.Event;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This is a temporary activity meant to be used during development to ensure that
 * events are persisting correctly.
 */
public class EventListActivity extends AppCompatActivity implements EventFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
    }

    @Override
    public void onListFragmentInteraction(Event item) {

    }
}

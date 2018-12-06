package net.minthe.calendarapp;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import net.minthe.calendarapp.domain.AppDatabase;
import net.minthe.calendarapp.domain.Event;
import net.minthe.calendarapp.domain.EventDao;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

// Class to handle event creation
public class CreateEventActivity extends AppCompatActivity {
    // Variables
    EditText eventName;
    EditText startTime;
    EditText endTime;
    EditText notes;
    long date;
    String amPm;

    long id;
    boolean edit;

    static SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aaa", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        AppDatabase.instantiate(getApplicationContext());

        eventName = findViewById(R.id.eventName);
        startTime = findViewById(R.id.startTime);

        // Shows TimePicker when user clicks the startTime EditText box
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timepicker, int hourOfDay, int minute) {

                        // Determines whether to display AM or PM
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }

                        // Fixes issue where 12:00 was displayed as 0:00
                        if (hourOfDay == 0) {
                            // Displays formatted time in EditText box
                            startTime.setText(String.format("%2d:%02d", hourOfDay + 12, minute) + " " + amPm);
                        } else if (hourOfDay == 12) {
                            // Displays formatted time in EditText box
                            startTime.setText(String.format("%2d:%02d", hourOfDay, minute) + " " + amPm);
                        } else {
                            // Displays formatted time in EditText box
                            startTime.setText(String.format("%2d:%02d", hourOfDay % 12, minute) + " " + amPm);
                        }

                    }
                }, 0, 0, false);

                // Displays the TimePicker window
                timePickerDialog.show();
            }
        });

        endTime = findViewById(R.id.endTime);

        // Shows TimePicker when user clicks the endTime EditText box
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timepicker, int hourOfDay, int minute) {

                        // Determines whether to display AM or PM
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }

                        // Fixes issue where 12:00 was displayed as 0:00
                        if (hourOfDay == 0) {
                            // Displays formatted time in EditText box
                            endTime.setText(String.format("%2d:%02d", hourOfDay + 12, minute) + " " + amPm);
                        } else if (hourOfDay == 12) {
                            // Displays formatted time in EditText box
                            endTime.setText(String.format("%2d:%02d", hourOfDay, minute) + " " + amPm);
                        } else {
                            // Displays formatted time in EditText box
                            endTime.setText(String.format("%2d:%02d", hourOfDay % 12, minute) + " " + amPm);
                        }

                    }
                }, 0, 0, false);

                // Displays the TimePicker window
                timePickerDialog.show();
            }
        });

        notes = findViewById(R.id.notes);
        date = getIntent().getLongExtra("NET_MINTHE_CALENDARAPP_SELECTED_DATE", 0);

        edit = getIntent().getBooleanExtra("NET_MINTHE_CALENDARAPP_EDIT_MODE", false);
        if (edit) {
            setupEditingEnvironment();
        }
    }

    private void setupEditingEnvironment() {
        id = getIntent().getLongExtra("NET_MINTHE_CALENDARAPP_EVENT_ID", -1);
        if (id == -1) {
            return;
        }

        Event e = AppDatabase.getInstance().eventDao().findByID(id);

        // Events are stored in the database in a (timestamp, duration) format, but we
        // need to convert that back to a (start time, end time) format for user interaction
        Date end = new Date(
                e.getDateTime().getTime()
                        + e.getDuration() * 1000
        );

        // Populate the form fields with the existing data
        eventName.setText(e.getEventName());
        startTime.setText(sdf.format(e.getDateTime()));
        endTime.setText(sdf.format(end));
        notes.setText(e.getNotes());

        // Change the text on the "Create" button to read "Update" instead
        Button submitButton = findViewById(R.id.createEventButton);
        submitButton.setText("Update");

        // The rest of this is just creating a button which will delete the selected event
        // and putting it in the right place

        ConstraintLayout layout = findViewById(R.id.createEventLayout);
        ConstraintSet constraintSet = new ConstraintSet();

        // Creating the button and setting its properties

        Button deleteButton = new Button(this);
        deleteButton.setId(View.generateViewId());
        deleteButton.setText("Delete");
        deleteButton.setBackgroundColor(Color.parseColor("#e05757"));
        ConstraintLayout.LayoutParams buttonParams = new ConstraintLayout.LayoutParams(submitButton.getLayoutParams());
        deleteButton.setLayoutParams(buttonParams);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDelete(v);
            }
        });

        // Add the button to the layout
        layout.addView(deleteButton);

        // Create a copy of the current set of constraints, as it is immutable
        constraintSet.clone(layout);

        // Add constraints on the button so that it appears in the correct place
        constraintSet.connect(deleteButton.getId(), ConstraintSet.TOP, submitButton.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(deleteButton.getId(), ConstraintSet.LEFT, layout.getId(), ConstraintSet.LEFT);
        constraintSet.connect(deleteButton.getId(), ConstraintSet.RIGHT, layout.getId(), ConstraintSet.RIGHT);
        constraintSet.connect(deleteButton.getId(), ConstraintSet.BOTTOM, layout.getId(), ConstraintSet.BOTTOM);

        // Change the previous set of constraints to the new set.
        constraintSet.applyTo(layout);
    }

    /**
     * Validate the user inputs to the event fields
     *
     * @return boolean - whether or not the input is valid
     */
    private boolean validate() {
        boolean valid = true;

        // If the event name field is left blank then set the color to red; not valid
        if (eventName.getText().toString().trim().isEmpty()) {
            eventName.setBackgroundColor(Color.parseColor("#e58989"));
            valid = false;
        }

        // If the start time field is left blank then set the color to red; not valid
        if (startTime.getText().toString().isEmpty()) {
            startTime.setBackgroundColor(Color.parseColor("#e58989"));
            valid = false;
        } else {
            try {
                sdf.parse(startTime.getText().toString());
            } catch (ParseException e) {
                startTime.setBackgroundColor(Color.parseColor("#e58989"));
                valid = false;
            }
        }

        // If the end time field is left blank then set the color to red; not valid
        if (endTime.getText().toString().isEmpty()) {
            endTime.setBackgroundColor(Color.parseColor("#e58989"));
            valid = false;
        } else {
            try {
                sdf.parse(endTime.getText().toString());
            } catch (ParseException e) {
                endTime.setBackgroundColor(Color.parseColor("#e58989"));
                valid = false;
            }
        }

        // Otherwise the input is valid
        return valid;
    }

    /**
     * Button callback for the Delete button
     */
    public void handleDelete(View view) {
        EventDao eventDao = AppDatabase.getInstance().eventDao();

        eventDao.delete(
                eventDao.findByID(id)
        );

        finish();
    }

    /**
     * Button callback for the create and update buttons
     */
    public void handleCreate(View view) {
        if (!validate()) {
            return;
        }

        Date startDate = null;
        Date endDate = null;
        try {
            startDate = sdf.parse(startTime.getText().toString());
            endDate = sdf.parse(endTime.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
            // this should _never_ happen as the same check is repeated in #validate
        }

        // Need to convert (start time, end time) into (start time, duration) for database format
        long secondsFromMidnight = getSecondsFromMidnight(startDate);
        long duration = getDurationSeconds(startDate, endDate);
        if (duration == -1) {
            return; // Passed invalid inputs to above function
        }

        Event event = new Event(
                eventName.getText().toString(),
                // startDate above does not actually contain the correct date, just the time
                new Date(date + secondsFromMidnight * 1000),
                duration,
                notes.getText().toString()
        );

        if (edit) { // Are we creating or updating?
            event.setEventId(id);
            new EventUpdateTask(this, event)
                    .execute();

        } else {
            new EventInsertionTask(this, event)
                    .execute();
        }

        finish(); // Return to previous activity
    }

    /**
     * @param date The input time
     * @return The number of seconds since midnight represented by date
     */
    private long getSecondsFromMidnight(Date date) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY) * 60 * 60 + cal.get(Calendar.MINUTE) * 60;
    }

    /**
     * Utility function to convert (start time, end time) UI elements to
     * (start time, duration) format for database insertion
     *
     * @param start The start time
     * @param end   The end time
     * @return The duration between the two times in seconds. -1 if the
     * inputs are not valid
     */
    private long getDurationSeconds(Date start, Date end) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(start);
        long startSecondsFromMidnight = getSecondsFromMidnight(start);

        cal.setTime(end);
        long endSecondsFromMidnight = cal.get(Calendar.HOUR_OF_DAY) * 60 * 60 + cal.get(Calendar.MINUTE) * 60;

        if (endSecondsFromMidnight <= startSecondsFromMidnight) {
            endTime.setBackgroundColor(Color.parseColor("#e58989"));
            return -1;
        }
        return endSecondsFromMidnight - startSecondsFromMidnight;
    }

    /**
     * Class representing a function which inserts an event into the database.
     * <p>
     * Needed because of UI thread constraints.
     */
    private static class EventInsertionTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<Activity> activity;
        private Event event;

        public EventInsertionTask(Activity activity, Event event) {
            this.activity = new WeakReference<>(activity);
            this.event = event;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            EventDao eventDao = AppDatabase.getInstance().eventDao();
            eventDao.insertAll(event);
            return null;
        }
    }

    /**
     * Class representing a function which updates an event in the database
     * <p>
     * Needed because of UI thread constraints
     */
    private static class EventUpdateTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<Activity> activity;
        private Event event;

        public EventUpdateTask(Activity activity, Event event) {
            this.activity = new WeakReference<>(activity);
            this.event = event;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            EventDao eventDao = AppDatabase.getInstance().eventDao();
            eventDao.update(event);
            return null;
        }
    }
}

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
        Date end = new Date(
                e.getDateTime().getTime()
                        + e.getDuration() * 1000
        );

        eventName.setText(e.getEventName());
        startTime.setText(sdf.format(e.getDateTime()));
        endTime.setText(sdf.format(end));
        notes.setText(e.getNotes());
        Button submitButton = findViewById(R.id.createEventButton);
        submitButton.setText("Update");

        ConstraintLayout layout = findViewById(R.id.createEventLayout);
        ConstraintSet constraintSet = new ConstraintSet();

        Button deleteButton = new Button(this);
        deleteButton.setId(View.generateViewId());
        deleteButton.setText("Delete");
        deleteButton.setBackgroundColor(Color.RED);
        ConstraintLayout.LayoutParams buttonParams = new ConstraintLayout.LayoutParams(submitButton.getLayoutParams());
        deleteButton.setLayoutParams(buttonParams);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDelete(v);
            }
        });

        layout.addView(deleteButton);

        constraintSet.clone(layout);
        constraintSet.connect(deleteButton.getId(), ConstraintSet.TOP, submitButton.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(deleteButton.getId(), ConstraintSet.LEFT, layout.getId(), ConstraintSet.LEFT);
        constraintSet.connect(deleteButton.getId(), ConstraintSet.RIGHT, layout.getId(), ConstraintSet.RIGHT);
        constraintSet.connect(deleteButton.getId(), ConstraintSet.BOTTOM, layout.getId(), ConstraintSet.BOTTOM);
        constraintSet.applyTo(layout);
    }

    // Method to validate the user inputs to the event fields
    private boolean validate() {
        boolean valid = true;

        // If the event name field is left blank then set the color to red; not valid
        if (eventName.getText().toString().isEmpty()) {
            eventName.setBackgroundColor(Color.RED);
            valid = false;
        }

        // If the start time field is left blank then set the color to red; not valid
        if (startTime.getText().toString().isEmpty()) {
            startTime.setBackgroundColor(Color.RED);
            valid = false;
        } else {
            try {
                sdf.parse(startTime.getText().toString());
            } catch (ParseException e) {
                startTime.setBackgroundColor(Color.RED);
                valid = false;
            }
        }

        // If the end time field is left blank then set the color to red; not valid
        if (endTime.getText().toString().isEmpty()) {
            endTime.setBackgroundColor(Color.RED);
            valid = false;
        } else {
            try {
                sdf.parse(endTime.getText().toString());
            } catch (ParseException e) {
                endTime.setBackgroundColor(Color.RED);
                valid = false;
            }
        }

        // Otherwise the input is valid
        return valid;
    }

    public void handleDelete(View view) {
        EventDao eventDao = AppDatabase.getInstance().eventDao();

        eventDao.delete(
                eventDao.findByID(id)
        );

        finish();
    }

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
            // god knows what happens here, as this should _never_ happen
            // as the same check is repeated in #validate
        }

        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(startDate);
        long startSecondsFromMidnight = cal.get(Calendar.HOUR_OF_DAY) * 60 * 60 + cal.get(Calendar.MINUTE) * 60;

        cal.setTime(endDate);
        long endSecondsFromMidnight = cal.get(Calendar.HOUR_OF_DAY) * 60 * 60 + cal.get(Calendar.MINUTE) * 60;

        if (endSecondsFromMidnight <= startSecondsFromMidnight) {
            endTime.setBackgroundColor(Color.RED);
            return;
        }
        long duration = endSecondsFromMidnight - startSecondsFromMidnight;

        if (edit) {
            new EventUpdateTask(this,
                    id,
                    eventName.getText().toString(),
                    new Date(date + startSecondsFromMidnight * 1000),
                    duration,
                    notes.getText().toString())
                    .execute();

        } else {
            new EventInsertionTask(this,
                    eventName.getText().toString(),
                    new Date(date + startSecondsFromMidnight * 1000),
                    duration,
                    notes.getText().toString())
                    .execute();

        }

        finish();
    }

    // Android forbids database insertion in the main thread, and we're using Java 7, so
    // enjoy this abomination
    private static class EventInsertionTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<Activity> activity;
        private String eventName;
        private Date date;
        private long duration;
        private String notes;

        public EventInsertionTask(Activity activity, String eventName, Date date, long duration, String notes) {
            this.activity = new WeakReference<>(activity);
            this.eventName = eventName;
            this.date = date;
            this.duration = duration;
            this.notes = notes;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            EventDao eventDao = AppDatabase.getInstance().eventDao();
            eventDao.insertAll(new Event(
                    eventName, date, duration, notes
            ));
            return null;
        }
    }

    private static class EventUpdateTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<Activity> activity;
        private long id;
        private String name;
        private Date date;
        private long duration;
        private String notes;

        public EventUpdateTask(Activity activity, long id, String name, Date date, long duration, String notes) {
            this.activity = new WeakReference<>(activity);
            this.id = id;
            this.name = name;
            this.date = date;
            this.duration = duration;
            this.notes = notes;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            EventDao eventDao = AppDatabase.getInstance().eventDao();
            eventDao.update(new Event(
                    id, name, date, duration, notes
            ));
            return null;
        }
    }
}

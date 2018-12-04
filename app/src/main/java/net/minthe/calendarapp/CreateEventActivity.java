package net.minthe.calendarapp;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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

// Class to handle event creation
public class CreateEventActivity extends AppCompatActivity {
    // Variables
    EditText eventName;
    EditText startTime;
    EditText endTime;
    EditText notes;
    long date;
    String amPm;

    static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

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
                              if(hourOfDay >= 12)
                              {
                                  amPm = "PM";
                              } else {
                                  amPm = "AM";
                              }

                              // Displays formatted time in EditText box
                              startTime.setText(String.format("%2d:%02d", hourOfDay % 12, minute) + " " + amPm);
                          }
                      },0,0,false);

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
                        if(hourOfDay >= 12)
                        {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }

                        // Displays formatted time in EditText box
                        endTime.setText(String.format("%2d:%02d", hourOfDay % 12, minute) + " " + amPm);
                    }
                },0,0,false);

                // Displays the TimePicker window
                timePickerDialog.show();
            }
        });

        notes = findViewById(R.id.notes);
        date = getIntent().getLongExtra("NET_MINTHE_CALENDARAPP_SELECTED_DATE", 0);
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

    public void handleCreate(View view) {
        if (!validate()) {
            return;
        }
        AppDatabase.instantiate(view.getContext());

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aaa", Locale.US);

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

        new EventInsertionTask(this, eventName.getText().toString(), new Date(date + startSecondsFromMidnight * 1000), duration, notes.getText().toString()).execute();
        Intent backtoMain = new Intent(this, MainActivity.class);
        startActivity(backtoMain);
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
}

package net.minthe.calendarapp;

import android.app.Activity;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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

public class CreateEventActivity extends AppCompatActivity {
    EditText eventName;
    EditText startTime;
    EditText endTime;
    EditText notes;
    long date;

    static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        eventName = findViewById(R.id.eventName);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        notes = findViewById(R.id.notes);
        date = getIntent().getLongExtra("NET_MINTHE_CALENDARAPP_SELECTED_DATE", 0);
    }

    private boolean validate() {
        boolean valid = true;
        if (eventName.getText().toString().isEmpty()) {
            eventName.setBackgroundColor(Color.RED);
            valid = false;
        }
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
        return valid;
    }

    public void handleCreate(View view) {
        if (!validate()) {
            return;
        }
        AppDatabase.instantiate(view.getContext());
        EventDao eventDao = AppDatabase.getInstance().eventDao();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);

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
        long startSecondsFromMidnight = cal.get(Calendar.HOUR) * 60 * 60 + cal.get(Calendar.MINUTE) * 60;

        cal.setTime(endDate);
        long endSecondsFromMidnight = cal.get(Calendar.HOUR) * 60 * 60  + cal.get(Calendar.MINUTE) * 60;

        if (endSecondsFromMidnight <= startSecondsFromMidnight) {
            endTime.setBackgroundColor(Color.RED);
            return;
        }
        long duration = endSecondsFromMidnight - startSecondsFromMidnight;

        Event e = new Event(
                new Date(date),
                duration,
                notes.getText().toString()
        );

        new EventInsertionTask(this, new Date(date), duration, notes.getText().toString()).execute();
    }

    // Android forbids database insertion in the main thread, and we're using Java 7, so
    // enjoy this abomination
    private static class EventInsertionTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<Activity> activity;
        private Date date;
        private long duration;
        private String notes;

        public EventInsertionTask(Activity activity, Date date, long duration, String notes) {
            this.activity = new WeakReference<>(activity);
            this.date = date;
            this.duration = duration;
            this.notes = notes;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            EventDao eventDao = AppDatabase.getInstance().eventDao();
            eventDao.insertAll(new Event(
                    date, duration, notes
            ));
            return null;
        }
    }
}

package net.minthe.calendarapp.week;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.minthe.calendarapp.R;
import net.minthe.calendarapp.day.DayViewFragment;
import net.minthe.calendarapp.domain.AppDatabase;
import net.minthe.calendarapp.domain.Event;
import net.minthe.calendarapp.domain.MonthDetails;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class WeekViewActivity extends AppCompatActivity
        implements DayViewFragment.OnFragmentInteractionListener {

    private static final int EVENT_WIDTH = 135;

    private long date;
    int day;
    private long nextWeek;
    private long prevWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_week_view);
        AppDatabase.instantiate(getApplicationContext());

        setDate(GregorianCalendar.getInstance().getTimeInMillis());
    }

    private void setDate(long newDate) {
        RelativeLayout layout = findViewById(R.id.weekLayout);
        layout.removeAllViews();

        date = newDate;
        setNextAndPrevWeek();
        Calendar c = GregorianCalendar.getInstance();
        c.setTimeInMillis(newDate);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        day = c.get(Calendar.DAY_OF_MONTH);

        String firstDateText = getDateText(c.getTimeInMillis());
        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        String endDateText = getDateText(c.getTimeInMillis());

        TextView tv = findViewById(R.id.weekHeader);
        tv.setText(firstDateText + " - " + endDateText);

        MonthDetails md = new MonthDetails(date);

        DayViewFragment.addHoursToLayout(layout);

        for (int i = 0; i < 7; i++) {
            List<Event> dayEvents = AppDatabase.getInstance().eventDao().findEventsBetween(
                    md.getDayStart(day + i),
                    md.getDayEnd(day + i)
            );

            DayViewFragment.addEventsToLayout(
                    layout,
                    dayEvents,
                    DayViewFragment.HOUR_WIDTH + DayViewFragment.HOUR_MARGIN +
                            EVENT_WIDTH * i,
                    EVENT_WIDTH
            );
        }
    }

    private String getDateText(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("d MMM");
        return sdf.format(new Date(date));
    }

    private void setNextAndPrevWeek() {
        Calendar c = GregorianCalendar.getInstance();
        c.clear();
        c.setTime(new Date(date));
        c.add(Calendar.WEEK_OF_YEAR, -1);
        prevWeek = c.getTimeInMillis();

        c.clear();
        c.setTime(new Date(date));
        c.add(Calendar.WEEK_OF_YEAR, 1);
        nextWeek = c.getTimeInMillis();
    }

    public void gotoNext(View v) {
        setDate(nextWeek);
    }

    public void gotoPrev(View v) {
        setDate(prevWeek);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

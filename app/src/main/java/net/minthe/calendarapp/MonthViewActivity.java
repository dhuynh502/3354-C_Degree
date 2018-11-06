package net.minthe.calendarapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import java.util.Calendar;
import java.util.GregorianCalendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MonthViewActivity extends AppCompatActivity  {

    private Button addEventButton;
    private CalendarView calendarView;
    private long selectedDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_month_view);
        addEventButton = findViewById(R.id.addEventButton);
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar c = GregorianCalendar.getInstance();
                c.set(year, month, dayOfMonth, 0, 0, 0);
                selectedDate = c.getTimeInMillis();
            }
        });
    }

    public void createEvent(View view) {
        Intent intent = new Intent(this, CreateEventActivity.class);
        intent.putExtra("NET_MINTHE_CALENDARAPP_SELECTED_DATE", this.selectedDate);
        startActivity(intent);
    }
}

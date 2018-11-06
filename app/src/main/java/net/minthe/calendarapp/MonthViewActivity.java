package net.minthe.calendarapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;


public class MonthViewActivity extends AppCompatActivity  {

    private Button addEventButton;
    private CalendarView calendarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_month_view);
        addEventButton = findViewById(R.id.addEventButton);
        calendarView = findViewById(R.id.calendarView);
    }

    public void createEvent(View view) {
        Intent intent = new Intent(this, CreateEventActivity.class);
        long date = calendarView.getDate();
        intent.putExtra("NET_MINTHE_CALENDARAPP_SELECTED_DATE", date);
        startActivity(intent);
    }
}

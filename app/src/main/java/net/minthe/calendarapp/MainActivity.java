package net.minthe.calendarapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.minthe.calendarapp.week.WeekViewActivity;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onEventList(View view) {
        Intent intent = new Intent(this, EventListActivity.class);
        startActivity(intent);
    }

    public void onCustomMonthView(View view) {
        Intent intent = new Intent(this, CustomMonthView.class);
        startActivity(intent);
    }

    public void onWeekView(View view) {
        Intent intent = new Intent(this, WeekViewActivity.class);
        startActivity(intent);
    }
}

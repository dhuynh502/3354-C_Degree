package net.minthe.calendarapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onMonthView(View view) {
        Intent intent = new Intent(this, MonthViewActivity.class);
        startActivity(intent);
    }

    public void onEventList(View view) {
        Intent intent = new Intent(this, EventListActivity.class);
        startActivity(intent);
    }
}

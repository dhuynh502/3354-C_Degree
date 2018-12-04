package net.minthe.calendarapp;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WeekView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);

        TableLayout tl = findViewById(R.id.weekTable);

        for (int i = 0; i < 7; i++) {
            TableRow tr = new TableRow(tl.getContext());
            for (int j = 0; j < 4; j++) {
                TextView text = new TextView(tr.getContext());
                text.setText("Element " + 4 * i + j);
                tr.addView(text);
            }
        }
    }
}

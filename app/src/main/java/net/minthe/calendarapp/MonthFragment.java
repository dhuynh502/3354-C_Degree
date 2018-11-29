package net.minthe.calendarapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import net.minthe.calendarapp.domain.AppDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MonthFragment extends Fragment implements View.OnClickListener {

    public static final String DATE_UNIXTIME = "date-unixtime";

    private long date = 0;
    private MonthViewModel mViewModel;

    private int firstDay;
    private int numDays;
    private String monthName;
    private long prevMonth;
    private long nextMonth;

    public MonthFragment() {
        // Best to leave this empty
    }

    @SuppressWarnings("unused")
    public static MonthFragment newInstance(long date) {
        MonthFragment fragment = new MonthFragment();
        Bundle args = new Bundle();
        args.putLong(DATE_UNIXTIME, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            date = getArguments().getLong(DATE_UNIXTIME);
        } else {
            Calendar c = GregorianCalendar.getInstance();
            date = c.getTimeInMillis();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.month_fragment, container, false);

        Button next = v.findViewById(R.id.nextMonthButton);
        next.setOnClickListener(this);
        Button prev = v.findViewById(R.id.prevMonthButton);
        prev.setOnClickListener(this);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppDatabase.instantiate(getContext());
        mViewModel = ViewModelProviders.of(this).get(MonthViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.date.observe(this.getViewLifecycleOwner(),
                new Observer<Long>() {
                    @Override
                    public void onChanged(Long aLong) {
                        Calendar c = GregorianCalendar.getInstance();
                        c.clear();
                        c.setTime(new Date(aLong));
                        c.set(Calendar.DAY_OF_MONTH, 1);
                        firstDay = c.get(Calendar.DAY_OF_WEEK) - 1;

                        long start = c.getTimeInMillis();
                        monthName = new SimpleDateFormat("MMMM YYYY", Locale.US)
                                .format(c.getTime());

                        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
                        c.set(Calendar.HOUR, 23);
                        c.set(Calendar.MINUTE, 59);
                        c.set(Calendar.SECOND, 0);
                        long end = c.getTimeInMillis();
                        numDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);


                        mViewModel.eventList.setValue(
                                AppDatabase.getInstance().eventDao().findEventsBetween(start, end)
                        );
                        c.clear();
                        c.setTime(new Date(aLong));
                        c.add(Calendar.MONTH, 1);
                        nextMonth = c.getTimeInMillis();

                        c.clear();
                        c.setTime(new Date(aLong));
                        c.add(Calendar.MONTH, -1);
                        prevMonth = c.getTimeInMillis();
                        redraw();
                    }
                });
        mViewModel.date.setValue(date);
    }

    private void redraw() {
        TextView monthDate = this.getView().findViewById(R.id.monthDate);
        monthDate.setText(monthName);


        TableLayout layout = this.getView().findViewById(R.id.monthTableLayout);
        layout.removeAllViewsInLayout();
        for (int i = 0; i < 6; i++) {
            TableRow row = new TableRow(layout.getContext());
            for (int j = 0; j < 7; j++) {
                int day = i * 7 + j - firstDay + 1;
                TextView text = new TextView(row.getContext());
                if ((i != 0 || j >= firstDay) && i * 7 + j - firstDay < numDays) {
                    text.setText(String.valueOf(day) + " ");
                }
                text.setMinEms(3);
                text.setMinHeight(100);
                row.addView(text);
            }
            layout.addView(row);
        }

    }

    public void nextMonth() {
        mViewModel.date.setValue(nextMonth);
    }

    public void prevMonth() {
        mViewModel.date.setValue(prevMonth);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        redraw();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.prevMonthButton:
                prevMonth();
                break;
            case R.id.nextMonthButton:
                nextMonth();
                break;
        }
    }
}

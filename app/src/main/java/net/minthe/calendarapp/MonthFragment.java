package net.minthe.calendarapp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import net.minthe.calendarapp.domain.AppDatabase;
import net.minthe.calendarapp.domain.EventListDetails;
import net.minthe.calendarapp.domain.MonthDetails;

import java.util.Calendar;
import java.util.GregorianCalendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MonthFragment extends Fragment implements View.OnClickListener {

    public static final String DATE_UNIXTIME = "date-unixtime";

    private long date = 0;
    private int selectedDay = 1;
    private MonthViewModel mViewModel;

    private int firstDay;
    private int numDays;
    private String monthName;
    private long prevMonth;
    private long nextMonth;

    private DateChangeListener dateChangeListener;

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

                        MonthDetails monthDetails = new MonthDetails(aLong);

                        firstDay = monthDetails.getFirstWeekday();
                        numDays = monthDetails.getNumDays();

                        long start = monthDetails.getMonthStart();
                        long end = monthDetails.getMonthEnd();

                        mViewModel.eventList.setValue(
                                AppDatabase.getInstance().eventDao().findEventsBetween(start, end)
                        );

                        nextMonth = monthDetails.getNextMonth().getDate();
                        prevMonth = monthDetails.getPrevMonth().getDate();
                        monthName = monthDetails.getMonthName();
                        date = aLong;

                        redraw();
                    }
                });
        mViewModel.date.setValue(date);
        if (dateChangeListener != null) {
            changeSelectedDay(1);
        }
    }

    private void changeSelectedDay(int day) {
        dateChangeListener.onDateChange(day, new MonthDetails(date));
        this.selectedDay = day;
    }

    @Override
    public void onResume() {
        super.onResume();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewModel.date.setValue(mViewModel.date.getValue()); // refresh calendar
                changeSelectedDay(selectedDay);
            }
        }, 200); // hopefully any database activity will be finished in 200ms
    }

    static final int N_ROWS = 6;
    static final int N_COLS = 7;

    private void redraw() {
        TextView monthDate = this.getView().findViewById(R.id.monthDate);
        monthDate.setText(monthName);


        TableLayout layout = this.getView().findViewById(R.id.monthTableLayout);
        layout.removeAllViewsInLayout();

        long monthStart = new MonthDetails(date).getMonthStart();
        EventListDetails listDetails = new EventListDetails(monthStart, mViewModel.eventList.getValue());

        for (int i = 0; i < N_ROWS; i++) {
            TableRow row = new TableRow(layout.getContext());
            for (int j = 0; j < N_COLS; j++) {
                int day = i * N_COLS + j - firstDay + 1;
                TextView text = new TextView(row.getContext());
                text.setTag(Integer.valueOf(day));
                text.setOnClickListener(this);
                if ((i != 0 || j >= firstDay) && i * N_COLS + j - firstDay < numDays) {
                    text.setText(String.valueOf(day) + " ");
                    // Colors in days with an event
                    if (!listDetails.getEventsForDay(day - 1).isEmpty()) { //doing the day -1 because starts at 0 index
                        text.setBackgroundColor(Color.parseColor("#e58989"));
                    }
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

    public void setDateChangeListener(DateChangeListener dateChangeListener) {
        this.dateChangeListener = dateChangeListener;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.prevMonthButton:
                prevMonth();
                MonthDetails mdPrev = new MonthDetails(date);
                this.dateChangeListener.onDateChange(1, mdPrev);
                selectedDay = 1;
                return;
            case R.id.nextMonthButton:
                nextMonth();
                MonthDetails mdNext = new MonthDetails(date);
                this.dateChangeListener.onDateChange(1, mdNext);
                selectedDay = 1;
                return;
        }

        if (v.getTag() instanceof Integer) {
            int day = (int) v.getTag();
            if (this.dateChangeListener != null && day > 0 && day <= numDays) {
                changeSelectedDay(day);
            }
        }
    }
}

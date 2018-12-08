package net.minthe.calendarapp.day;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.minthe.calendarapp.R;
import net.minthe.calendarapp.domain.AppDatabase;
import net.minthe.calendarapp.domain.Event;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DayViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DayViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DayViewFragment extends Fragment {
    // Declare variables

    // The fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "dayview-start-timestamp";
    private static final String ARG_PARAM2 = "dayview-end-timestamp";
    private static final String ARG_PARAM3 = "dayview-event-width";
    private static final String ARG_PARAM4 = "dayview-display-hours";

    public static final int PIXELS_PER_HOUR = 200;
    public static final int HOUR_WIDTH = 50;
    public static final int HOUR_MARGIN = 10;

    private long start;
    private long end;
    private int eventWidth;
    private boolean displayHours;

    private OnFragmentInteractionListener mListener;

    // Required empty public constructor
    public DayViewFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param start Parameter 1.
     * @param end   Parameter 2.
     * @return A new instance of fragment DayViewFragment.
     */
    public static DayViewFragment newInstance(long start, long end, int eventWidth, boolean displayHours) {
        DayViewFragment fragment = new DayViewFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, start);
        args.putLong(ARG_PARAM2, end);
        args.putInt(ARG_PARAM3, eventWidth);
        args.putBoolean(ARG_PARAM4, displayHours);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            start = getArguments().getLong(ARG_PARAM1);
            end = getArguments().getLong(ARG_PARAM2);
            eventWidth = getArguments().getInt(ARG_PARAM3);
            displayHours = getArguments().getBoolean(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_day_view, container, false);

        RelativeLayout layout = v.findViewById(R.id.dayLayout);

        AppDatabase.instantiate(getContext());
        List<Event> events = AppDatabase.getInstance().eventDao().findEventsBetween(start, end);

        addHoursToLayout(layout);

        addEventsToLayout(layout, events, HOUR_WIDTH + HOUR_MARGIN, eventWidth);

        return v;
    }

    /**
     * Method to add events to the day view
     *
     * @param layout layout of the daily view
     * @param events list of events
     * @param leftOffset offset events from the left
     * @param eventWidth width of the events
     */
    public static void addEventsToLayout(RelativeLayout layout, List<Event> events, int leftOffset, int eventWidth) {
        for (Event e : events) {
            Calendar c = GregorianCalendar.getInstance();
            c.setTime(e.getDateTime());

            double placementMultiplier = c.get(Calendar.HOUR_OF_DAY) + c.get(Calendar.MINUTE) / 60.00d;
            double sizeMultiplier = e.getDuration() / 3600.00d;
            TextView eventText = new TextView(layout.getContext());
            eventText.setText(e.getEventName());
            eventText.setGravity(Gravity.CENTER);
            eventText.setBackgroundColor(Color.parseColor("#e05757"));

            RelativeLayout.LayoutParams eventParams =
                    new RelativeLayout.LayoutParams(eventWidth, (int) (PIXELS_PER_HOUR * sizeMultiplier));
            eventParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            eventParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            eventParams.setMargins(leftOffset, (int) (PIXELS_PER_HOUR * placementMultiplier), 0, 0);

            eventText.setLayoutParams(eventParams);
            layout.addView(eventText);
        }
    }

    /**
     * Method to add the hours to the daily view layout
     *
     * @param layout layout of the daily view
     */
    public static void addHoursToLayout(RelativeLayout layout) {
        for (int i = 0; i < 24; i++) {
            TextView hour = new TextView(layout.getContext());
            hour.setText(Integer.toString(i));
            hour.setGravity(Gravity.CENTER);

            RelativeLayout.LayoutParams hourParams = new RelativeLayout.LayoutParams(HOUR_WIDTH, PIXELS_PER_HOUR);
            hourParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            hourParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            hourParams.setMargins(0, PIXELS_PER_HOUR * i, 1, 0);

            hour.setLayoutParams(hourParams);
            layout.addView(hour);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

package net.minthe.calendarapp;

import net.minthe.calendarapp.domain.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventListDetails {

    private HashMap<Integer, ArrayList<Event>> eventsByDay;

    public EventListDetails(long date, List<Event> eventList) {
        long startUnixTime = date;
        long endUnixTime;

        eventsByDay = new HashMap<>();

        for (int i = 0; i < 31; i++) {
            endUnixTime = startUnixTime + 24 * 60 * 60 * 1000;
            eventsByDay.put(i, new ArrayList<Event>());

            for (int j = 0; j < eventList.size(); j++) {
                long time = eventList.get(j).getDateTime().getTime();
                if (time >= startUnixTime &&
                        time <= endUnixTime) {
                    eventsByDay.get(i).add(eventList.get(j));
                }
            }

            startUnixTime = endUnixTime;
        }

        System.out.println();
    }

    public List<Event> getEventsForDay(int day) {
        return eventsByDay.get(day);
    }

}

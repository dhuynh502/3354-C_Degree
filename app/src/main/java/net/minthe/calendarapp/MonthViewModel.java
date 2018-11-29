package net.minthe.calendarapp;

import net.minthe.calendarapp.domain.Event;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MonthViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    public final MutableLiveData<List<Event>> eventList = new MutableLiveData<>();
    public final MutableLiveData<Long> date = new MutableLiveData<>();

    public MonthViewModel() {
        super();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}

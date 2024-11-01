package com.theanimegroup.movie_ticket_booking_client.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class BaseViewModel<T> extends ViewModel {
    protected MutableLiveData<List<T>> items = new MutableLiveData<>();

    public LiveData<List<T>> getItems() {
        return items;
    }

    public void updateItems(List<T> newItems) {
        items.setValue(newItems);
    }
}

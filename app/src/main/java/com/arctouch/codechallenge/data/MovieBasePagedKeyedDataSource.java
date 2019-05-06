package com.arctouch.codechallenge.data;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.arctouch.codechallenge.data.model.Movie;

public abstract class MovieBasePagedKeyedDataSource extends PageKeyedDataSource<Integer, Movie> {
    private MutableLiveData<Throwable> error;

    public MovieBasePagedKeyedDataSource() {
        error = new MutableLiveData<>();
    }

    public MutableLiveData<Throwable> getError() {
        return error;
    }
}

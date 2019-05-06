package com.arctouch.codechallenge.data;

import android.annotation.SuppressLint;
import android.util.Log;
import androidx.annotation.NonNull;
import com.arctouch.codechallenge.data.model.Movie;

public class MoviePagingListData extends MovieBasePagedKeyedDataSource {

    private MoviesDataSource dataSource;
    private static final int FIRST_PAGE = 1;


    public MoviePagingListData (MoviesDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Movie> callback) {
        dataSource.subscribeForAllmovies(null,FIRST_PAGE, null)
                .subscribe(movies -> callback.onResult(movies, null, FIRST_PAGE + 1)
                        , throwable -> getError().postValue(throwable));
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
        dataSource.subscribeForAllmovies(null, params.key, null).subscribe(movies -> {
            int adjacentKey = (params.key > 1) ? params.key -1 : FIRST_PAGE;
            callback.onResult(movies, adjacentKey);
        }, throwable -> getError().postValue(throwable));
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
        dataSource.subscribeForAllmovies(null, params.key, null).subscribe(movies -> {
            int key = params.key + 1;
            callback.onResult(movies, key);
        }, throwable -> getError().postValue(throwable));
    }
}

package com.arctouch.codechallenge.data;

import android.annotation.SuppressLint;
import androidx.paging.PageKeyedDataSource;
import androidx.annotation.NonNull;

import com.arctouch.codechallenge.data.MoviesDataSource;
import com.arctouch.codechallenge.data.model.Movie;

public class SearchMoviePagingListResultData extends MovieBasePagedKeyedDataSource {

    private MoviesDataSource dataSource;
    private static final int FIRST_PAGE = 1;
    private String movieTitle;

    public SearchMoviePagingListResultData(MoviesDataSource dataSource, String movieTitle) {
        this.dataSource = dataSource;
        this.movieTitle = movieTitle;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Movie> callback) {
        dataSource.subscribeForMoviesByTitle(movieTitle, FIRST_PAGE).subscribe(movies -> callback.onResult(movies, null, FIRST_PAGE + 1)
                , throwable -> getError().postValue(throwable));
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
        dataSource.subscribeForMoviesByTitle(movieTitle, params.key).subscribe(movies -> {
            int adjacentKey = (params.key > 1) ? params.key -1 : FIRST_PAGE;
            callback.onResult(movies, adjacentKey);
        }, throwable -> getError().postValue(throwable));

    }

    @SuppressLint("CheckResult")
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
        dataSource.subscribeForMoviesByTitle(movieTitle, params.key).subscribe(movies -> {
            int key = params.key + 1;
            callback.onResult(movies, key);
        }, throwable -> getError().postValue(throwable));
    }
}

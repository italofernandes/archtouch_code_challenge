package com.arctouch.codechallenge.data;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.arctouch.codechallenge.data.model.Movie;
import com.arctouch.codechallenge.data.remote.MovieRemoteDataSource;


public class MovieRepository extends DataSource.Factory {

    private MovieRemoteDataSource remoteDataSource;
    private static MovieRepository instance;
    private MutableLiveData<PageKeyedDataSource<Integer, Movie>> movieLiveDataSource;
    private MovieBasePagedKeyedDataSource currentDataSource;

    public static MovieRepository getInstance(){

        if (instance == null) {
            instance = new MovieRepository();
        }

        return instance;
    }

    private MovieRepository () {
        remoteDataSource = new MovieRemoteDataSource();
        movieLiveDataSource = new MutableLiveData<>();

        currentDataSource = new MoviePagingListData(remoteDataSource);
    }

    public void clearSearch() {
        currentDataSource = null;
        currentDataSource = new MoviePagingListData(remoteDataSource);
        movieLiveDataSource.getValue().invalidate();
    }

    public void searchMovies(String query) {
        currentDataSource = null;
        currentDataSource = new SearchMoviePagingListResultData(remoteDataSource, query);
        movieLiveDataSource.getValue().invalidate();
    }

    @Override
    public DataSource create() {

        movieLiveDataSource.postValue(currentDataSource);

        return currentDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Movie>> getMovieLiveDataSource() {
        return movieLiveDataSource;
    }

    public MutableLiveData<Throwable> getErrorLiveData (){
        return currentDataSource.getError();
    }
}

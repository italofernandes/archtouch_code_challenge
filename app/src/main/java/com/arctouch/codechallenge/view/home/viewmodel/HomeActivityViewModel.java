package com.arctouch.codechallenge.view.home.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.arctouch.codechallenge.data.MovieRepository;
import com.arctouch.codechallenge.data.model.Movie;

public class HomeActivityViewModel extends ViewModel {

    private LiveData<PagedList<Movie>> moviePagedList;
    private LiveData<PageKeyedDataSource<Integer, Movie>> liveDataSource;
    private MovieRepository movieRepository;
    private MutableLiveData<MutableLiveData<Throwable>> errorHandlerMutable;

    private static final int PAGE_SIZE = 20;

    public void init(){

        if (moviePagedList != null) {
            return;
        }

        movieRepository = MovieRepository.getInstance();

        liveDataSource = movieRepository.getMovieLiveDataSource();

        PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setPageSize(PAGE_SIZE).build();

        moviePagedList = new LivePagedListBuilder(movieRepository, pagedListConfig).build();
        errorHandlerMutable = new MutableLiveData<>();
        errorHandlerMutable.postValue(movieRepository.getErrorLiveData());
    }

    public LiveData<PagedList<Movie>> getMoviePagedList() {
        return moviePagedList;
    }

    public void searchItem (String query) {
        movieRepository.searchMovies(query);
    }

    public void clearSearch () {
        movieRepository.clearSearch();
    }

    public MutableLiveData<MutableLiveData<Throwable>> getErrorHandlerMutable() {
        return errorHandlerMutable;
    }
}

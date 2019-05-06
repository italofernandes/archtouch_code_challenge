package com.arctouch.codechallenge.data.remote;

import androidx.annotation.NonNull;

import com.arctouch.codechallenge.data.MoviesDataSource;
import com.arctouch.codechallenge.data.model.GenreResponse;
import com.arctouch.codechallenge.data.model.Movie;
import com.arctouch.codechallenge.network.MovieRemoteApi;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MovieRemoteDataSource extends MoviesDataSource {

    private MovieRemoteApi movieApi;

    public MovieRemoteDataSource(){
        movieApi = MovieRemoteApi.getInstance();
    }

    @Override
    public Observable<List<Movie>> subscribeForAllmovies(String language, int page, String region) {
        return subscribeForGenres(null)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .zipWith(movieApi.upcomingMovies(null, page, null),
                        (genreResponse, upcomingMoviesResponse) -> applyGenresToMovies(upcomingMoviesResponse, genreResponse));
    }

    @Override
    public Observable<List<Movie>> subscribeForMoviesByTitle(String query, int page) {
        return subscribeForGenres(null)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .zipWith(movieApi.searchMovies(null, page, null, query),
                (genreResponse, upcomingMoviesResponse) -> applyGenresToMovies(upcomingMoviesResponse, genreResponse));
    }

    @Override
    public Observable<GenreResponse> subscribeForGenres(String language) {
        return movieApi.genres(language);
    }
}

package com.arctouch.codechallenge.data;

import androidx.paging.PageKeyedDataSource;

import com.arctouch.codechallenge.data.model.Genre;
import com.arctouch.codechallenge.data.model.GenreResponse;
import com.arctouch.codechallenge.data.model.Movie;
import com.arctouch.codechallenge.data.model.UpcomingMoviesResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public abstract class MoviesDataSource /*extends PageKeyedDataSource<Integer, Movie>*/ {
    //public abstract Observable<UpcomingMoviesResponse> subscribeForAllmovies(String language, int page, String region);
    public abstract Observable<List<Movie>> subscribeForAllmovies(String language, int page, String region);
    public abstract Observable<List<Movie>> subscribeForMoviesByTitle(String name, int page);
    public abstract Observable<GenreResponse> subscribeForGenres(String language);

    public List<Movie> applyGenresToMovies(UpcomingMoviesResponse moviesResponse, GenreResponse genreResponse){
        for (Movie movie : moviesResponse.results) {
            movie.genres = new ArrayList<>();
            for (Genre genre : genreResponse.genres) {
                if (movie.genreIds.contains(genre.id)) {
                    movie.genres.add(genre);
                }
            }
        }

        return moviesResponse.results;
    }
}

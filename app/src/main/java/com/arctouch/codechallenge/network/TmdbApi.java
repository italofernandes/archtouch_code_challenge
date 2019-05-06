package com.arctouch.codechallenge.network;


import com.arctouch.codechallenge.data.model.GenreResponse;
import com.arctouch.codechallenge.data.model.Movie;
import com.arctouch.codechallenge.data.model.UpcomingMoviesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TmdbApi {

    @GET("genre/movie/list")
    Observable<GenreResponse> genres(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("movie/upcoming")
    Observable<UpcomingMoviesResponse> upcomingMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page,
            @Query("region") String region
    );

    @GET("movie/{id}")
    Observable<Movie> movie(
            @Path("id") Long id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("search/movie")
    Observable<UpcomingMoviesResponse> search(
            @Query("query") String query,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page,
            @Query("region") String region,
            @Query("include_adult") boolean includeAdult
    );
}

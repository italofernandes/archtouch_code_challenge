package com.arctouch.codechallenge.network;

import com.arctouch.codechallenge.data.model.GenreResponse;
import com.arctouch.codechallenge.data.model.UpcomingMoviesResponse;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MovieRemoteApi {

    private TmdbApi api;

    private final String URL = "https://api.themoviedb.org/3/";
    public static final String API_KEY = "1f54bd990f1cdfb230adb312546d765d";
//    private final String DEFAULT_LANGUAGE = "pt-BR";
//    private final String DEFAULT_REGION = "BR";
    private static MovieRemoteApi instance;

    public static MovieRemoteApi getInstance(){
        if (instance == null) {
            instance = new MovieRemoteApi();
        }

        return instance;
    }

    private MovieRemoteApi(){
        initMovieApi();
    }

    private void initMovieApi(){
        api = new Retrofit.Builder()
                .baseUrl(URL)
                .client(new OkHttpClient.Builder().build())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(TmdbApi.class);
    }


    public Observable<UpcomingMoviesResponse> upcomingMovies(String language, int page, String region) {
        return api.upcomingMovies(API_KEY, language, page, region);
    }

    public Observable<GenreResponse> genres (String language){
       return api.genres(API_KEY, language);
    }

    public Observable<UpcomingMoviesResponse> searchMovies(String language, int page, String region, String movieName) {
        return api.search(movieName, API_KEY, language, page, region, false);
    }
}

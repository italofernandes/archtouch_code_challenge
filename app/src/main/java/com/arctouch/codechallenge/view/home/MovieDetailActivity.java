package com.arctouch.codechallenge.view.home;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;

import android.widget.ImageView;
import android.widget.TextView;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.data.model.Movie;
import com.arctouch.codechallenge.util.MovieImageUrlBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.movie_detail_activity_layout);

        ImageView backDropImage = findViewById(R.id.detail_backdrop_image);
        ImageView posterImage = findViewById(R.id.detail_poster_image);

        TextView movieTitle = findViewById(R.id.detail_movie_name);
        TextView movieGenres = findViewById(R.id.detail_movie_genres);
        TextView movieReleaseDate = findViewById(R.id.detail_release_date);
        TextView movieOverview = findViewById(R.id.detail_overview);


        if (getIntent().getExtras().containsKey("movie")) {
            Movie movie = (Movie) getIntent().getSerializableExtra("movie");

            MovieImageUrlBuilder movieImageUrlBuilder = new MovieImageUrlBuilder();

            String backDrop = movie.backdropPath;
            String poster = movie.posterPath;

            Glide.with(findViewById(android.R.id.content))
                    .load(movieImageUrlBuilder.buildBackdropUrl(backDrop))
                    .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder).centerCrop())
                    .into(backDropImage);

            Glide.with(findViewById(android.R.id.content))
                    .load(movieImageUrlBuilder.buildPosterUrl(poster))
                    .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .into(posterImage);

            movieTitle.setText(movie.title);
            movieGenres.setText(TextUtils.join(", ", movie.genres));
            movieReleaseDate.setText(movie.releaseDate);
            movieOverview.setText(movie.overview);

        } else {
            finish();
        }
    }
}

package com.arctouch.codechallenge.view.home;

import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.data.model.Movie;
import com.arctouch.codechallenge.view.home.adpters.HomeAdapter;
import com.arctouch.codechallenge.view.home.viewmodel.HomeActivityViewModel;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener,
                                                                SearchView.OnQueryTextListener{

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private HomeActivityViewModel viewModel;
    private HomeAdapter homeAdapter;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.home_activity);

            this.recyclerView = findViewById(R.id.recyclerView);
            this.progressBar = findViewById(R.id.progressBar);

            viewModel = ViewModelProviders.of(this).get(HomeActivityViewModel.class);
            viewModel.init();

            viewModel.getMoviePagedList().observe(this, movies -> {
                this.progressBar.setVisibility(View.GONE);
                homeAdapter.submitList(movies);
            });

        viewModel.getErrorHandlerMutable().observe(this, throwableMutableLiveData -> {
            throwableMutableLiveData.observe(this, throwable -> {
                Toast.makeText(this, "An Error Has Ocurred", Toast.LENGTH_LONG).show();
            });
        });

        homeAdapter =  new HomeAdapter(this);
        recyclerView.setAdapter(homeAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() instanceof Movie) {
            Movie movie = (Movie) v.getTag();
            showMovieDetail(movie);
        }
    }

    private void showMovieDetail(Movie movie){
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("movie", movie);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.seach_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.searchBar);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.search_movies_hins));
        searchView.setOnQueryTextListener(this);
        searchView.setIconified(true);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        this.progressBar.setVisibility(View.VISIBLE);
        homeAdapter.submitList(null);
        viewModel.searchItem(query);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (newText.trim().equals("")) {
            this.progressBar.setVisibility(View.VISIBLE);
            homeAdapter.submitList(null);
            viewModel.clearSearch();
        }

        return true;
    }
}

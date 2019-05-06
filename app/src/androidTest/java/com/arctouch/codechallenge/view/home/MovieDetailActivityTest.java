package com.arctouch.codechallenge.view.home;

import android.text.TextUtils;
import android.view.View;

import androidx.test.rule.ActivityTestRule;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.data.model.Genre;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MovieDetailActivityTest {

    private List<Genre> genres;

    @Before
    public void setUp() throws Exception {
        Genre drama= new Genre();
        drama.name = "Drama";

        Genre  suspense= new Genre();
        suspense.name = "Suspense";

        Genre terror= new Genre();
        terror.name = "Terror";

        genres = new ArrayList<Genre>();
        genres.add(drama);
        genres.add(suspense);
        genres.add(terror);
    }

    @Test
    public void testGenreFormat() {
        String genes = TextUtils.join(", ", genres);

        assertNotNull(genes);
        assertNotNull(genes, "Drama, Suspense, Terror");

    }

    @After
    public void tearDown() throws Exception {
        genres = null;
    }
}
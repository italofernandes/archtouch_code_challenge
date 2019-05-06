package com.arctouch.codechallenge.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class MovieImageUrlBuilderTest {

    @Test
    public void buildPosterUrl() {
        MovieImageUrlBuilder builder = new MovieImageUrlBuilder();
        String url = builder.buildPosterUrl("/teste");

        assertNotNull(url);
        assertEquals("https://image.tmdb.org/t/p/w154teste?api_key=1f54bd990f1cdfb230adb312546d765d", url);
    }

    @Test
    public void buildBackdropUrl() {
        MovieImageUrlBuilder builder = new MovieImageUrlBuilder();
        String url = builder.buildBackdropUrl("/teste");

        assertNotNull(url);
        assertEquals("https://image.tmdb.org/t/p/w780/teste?api_key=1f54bd990f1cdfb230adb312546d765d", url);

    }
}
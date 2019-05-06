package com.arctouch.codechallenge.view.home;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import com.arctouch.codechallenge.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class HomeActivityTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);
    private HomeActivity homeActivity;

    @Before
    public void setUp() throws Exception {
        homeActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        View view = homeActivity.findViewById(R.id.recyclerView);
       assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {
        homeActivity = null;
    }
}
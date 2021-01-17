package com.mapbox.maps.testapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumentation test for [ExampleOverviewActivity] to test whether
 * the RecyclerView is displayed.
 */
@RunWith(AndroidJUnit4::class)
class ExampleOverviewActivityTest {

  @Rule
  @JvmField
  var rule = ActivityScenarioRule(ExampleOverviewActivity::class.java)

  @Test
  fun checkRecyclerViewIsDisplayed() {
    onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
  }

  @Test
  fun checkRecyclerViewClick() {
    onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    onView(withId(R.id.recyclerView)).perform(click())
  }

  @Test
  fun checkRecyclerViewSwipeUpAndDown() {
    onView(withId(R.id.recyclerView)).perform(swipeUp()).check(matches(isDisplayed()))
    onView(withId(R.id.recyclerView)).perform(swipeDown()).check(matches(isDisplayed()))
  }
}
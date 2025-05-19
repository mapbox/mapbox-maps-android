package com.mapbox.maps.testapp.integration.surface

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiObject2
import com.mapbox.maps.testapp.examples.SurfaceRecyclerViewActivity
import com.mapbox.maps.testapp.integration.BaseReuseIntegrationTest
import org.hamcrest.core.Is
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Regression test that validates if a SurfaceView surface in a recycler view can be recreated
 * without crashing.
 */
@RunWith(AndroidJUnit4::class)
class MapViewSurfaceModeWithRecyclerViewTest : BaseReuseIntegrationTest(SurfaceRecyclerViewActivity::class.java) {

  @Test
  @LargeTest
  fun scrollSurfaceRecyclerView() {
    // wait extra seconds to make sure recycler view is indeed added to hierarchy
    Thread.sleep(2_000)
    device.waitForIdle()
    val recyclerObject = By.clazz(RecyclerView::class.java)
    val recyclerView: UiObject2 = device.findObject(recyclerObject)
    repeat(2) {
      onMapView(matches(isDisplayed()))
      recyclerView.scroll(Direction.DOWN, 100.0f)
      device.waitForIdle()
      onMapView(doesNotExist())
      recyclerView.scroll(Direction.UP, 100.0f)
      device.waitForIdle()
      // Repeat up. Sometimes it didn't scroll all the way to first row
      recyclerView.scroll(Direction.UP, 100.0f)
      device.waitForIdle()
      onMapView(matches(isDisplayed()))
    }
  }

  private fun onMapView(matches: ViewAssertion?) {
    onView(withClassName(Is.`is`("com.mapbox.maps.MapView")))
      .check(matches)
  }
}
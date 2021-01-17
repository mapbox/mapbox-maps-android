package com.mapbox.maps.testapp.integration.surface

import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.*
import com.mapbox.maps.testapp.examples.SurfaceRecyclerViewActivity
import com.mapbox.maps.testapp.integration.BaseIntegrationTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Regression test that validates if a SurfaceView surface can be recreated without crashing.
 */
@RunWith(AndroidJUnit4::class)
class SurfaceViewReuseTest : BaseIntegrationTest() {

  @get:Rule
  var activityRule: ActivityScenarioRule<SurfaceRecyclerViewActivity> = ActivityScenarioRule(
    SurfaceRecyclerViewActivity::class.java
  )

  @Test
  @LargeTest
  fun scrollSurfaceRecyclerView() {
    val latch = CountDownLatch(1)
    // wait extra seconds to make sure recycler view is indeed added to hierarchy
    latch.await(3, TimeUnit.SECONDS)
    device.waitForIdle()
    val recyclerObject = By.clazz(RecyclerView::class.java)
    val recyclerView: UiObject2 = device.findObject(recyclerObject)
    recyclerView.scroll(Direction.DOWN, 100.0f)
    device.waitForIdle()
    recyclerView.scroll(Direction.UP, 100.0f)
    device.waitForIdle()
    recyclerView.scroll(Direction.DOWN, 100.0f)
    device.waitForIdle()
    recyclerView.scroll(Direction.UP, 100.0f)
    device.waitForIdle()
  }
}
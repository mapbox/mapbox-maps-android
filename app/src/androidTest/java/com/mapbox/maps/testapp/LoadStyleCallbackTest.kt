package com.mapbox.maps.testapp

import android.app.Activity
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.maps.Style
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
@LargeTest
class LoadStyleCallbackTest {

  @get:Rule
  var testActivity = ActivityScenarioRule(TestMapActivity::class.java)

  @Test
  fun loadStyleWhileStarted() {
    val activity = testActivity.getActivity()

    if (!activity.startLatch.await(2, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }

    val latch = CountDownLatch(1)
    testActivity.scenario.onActivity {
      activity.mapView.getMapboxMap().apply {
        subscribeMapLoadingError {
          throw AssertionError("onMapLoadError: $it")
        }
        loadStyle(
          style = Style.LIGHT,
          onStyleLoaded = {
            latch.countDown()
          }
        )
      }
    }

    if (!latch.await(10, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun stopAfterLoadStyle() {
    val activity = testActivity.getActivity()

    if (!activity.startLatch.await(2, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }

    val latch = CountDownLatch(1)
    testActivity.scenario.onActivity {
      activity.mapView.getMapboxMap().apply {
        subscribeMapLoadingError {
          throw AssertionError("onMapLoadError: $it")
        }
        loadStyle(
          style = Style.LIGHT,
          onStyleLoaded = {
            latch.countDown()
          }
        )
      }

      activity.mapView.onStop()
    }

    testActivity.scenario.moveToState(Lifecycle.State.CREATED)

    if (!activity.stopLatch.await(2, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }

    testActivity.scenario.moveToState(Lifecycle.State.RESUMED)

    if (!latch.await(10, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun loadStyleWhileStopped() {
    val activity = testActivity.getActivity()

    if (!activity.startLatch.await(2, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }

    testActivity.scenario.moveToState(Lifecycle.State.CREATED)

    if (!activity.stopLatch.await(2, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }

    val latch = CountDownLatch(1)
    testActivity.scenario.onActivity {
      activity.mapView.getMapboxMap().apply {
        subscribeMapLoadingError {
          throw AssertionError("onMapLoadError: $it")
        }
        loadStyle(
          style = Style.LIGHT,
          onStyleLoaded = {
            latch.countDown()
          }
        )
      }
    }

    Thread.sleep(100)

    testActivity.scenario.moveToState(Lifecycle.State.RESUMED)

    if (!latch.await(10, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  private fun <T : Activity> ActivityScenarioRule<T>.getActivity(): T {
    var activity: T? = null
    scenario.onActivity { activity = it }
    return activity!!
  }
}
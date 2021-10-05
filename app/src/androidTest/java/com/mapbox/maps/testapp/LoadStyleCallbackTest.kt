package com.mapbox.maps.testapp

import android.app.Activity
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.delegates.listeners.OnMapLoadErrorListener
import com.mapbox.maps.plugin.delegates.listeners.eventdata.MapLoadErrorType
import com.mapbox.maps.plugin.delegates.listeners.eventdata.TileID
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
      activity.mapView.getMapboxMap().loadStyleUri(
        styleUri = Style.LIGHT,
        onStyleLoaded = {
          latch.countDown()
        },
        onMapLoadErrorListener = object : OnMapLoadErrorListener {
          override fun onMapLoadError(
            mapLoadErrorType: MapLoadErrorType,
            message: String,
            sourceId: String?,
            tileId: TileID?
          ) {
            throw AssertionError("Load $mapLoadErrorType failed with $message, sourceId: $sourceId, tileId: $tileId")
          }
        }
      )
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
      activity.mapView.getMapboxMap().loadStyleUri(
        styleUri = Style.LIGHT,
        onStyleLoaded = {
          latch.countDown()
        },
        onMapLoadErrorListener = object : OnMapLoadErrorListener {
          override fun onMapLoadError(
            mapLoadErrorType: MapLoadErrorType,
            message: String,
            sourceId: String?,
            tileId: TileID?
          ) {
            throw AssertionError("Load $mapLoadErrorType failed with $message, sourceId: $sourceId, tileId: $tileId")
          }
        }
      )

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
      activity.mapView.getMapboxMap().loadStyleUri(
        styleUri = Style.LIGHT,
        onStyleLoaded = {
          latch.countDown()
        },
        onMapLoadErrorListener = object : OnMapLoadErrorListener {
          override fun onMapLoadError(
            mapLoadErrorType: MapLoadErrorType,
            message: String,
            sourceId: String?,
            tileId: TileID?
          ) {
            throw AssertionError("Load $mapLoadErrorType failed with $message, sourceId: $sourceId, tileId: $tileId")
          }
        }
      )
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
package com.mapbox.maps.testapp.gestures

import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.mapbox.maps.BuildConfig
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.animation.easeTo
import com.mapbox.maps.testapp.BaseMapTest
import com.mapbox.maps.testapp.runOnUiThread
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Set of tests making sure we trigger needed amount of MapIdle events when using gestures / animations.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class GestureMapIdleTest : BaseMapTest() {

  private var maxWidth = 0
  private var maxHeight = 0

  private val latch = CountDownLatch(1)
  @Volatile
  private var idleCount = 0

  @Before
  fun setUp() {
    super.before()
    maxWidth = mapView.measuredWidth
    maxHeight = mapView.measuredHeight
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.subscribeMapIdle {
          latch.countDown()
          idleCount++
        }
        mapboxMap.setCamera(
          cameraOptions {
            pitch(0.0)
            zoom(5.0)
          }
        )
      }
    }
    if (!BuildConfig.RUN_FROM_IDE) {
      with(InstrumentationRegistry.getInstrumentation().uiAutomation) {
        executeShellCommand("settings put global window_animation_scale 1")
        executeShellCommand("settings put global transition_animation_scale 1")
        executeShellCommand("settings put global animator_duration_scale 1")
      }
    }
  }

  @Test
  fun idleTestOnClickNoMovement() {
    if (!latch.await(EMPIRIC_IDLE_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException("Initial map style load IDLE event not called!")
    }
    Espresso.onView(ViewMatchers.withId(com.mapbox.maps.R.id.mapView)).perform(GesturesUiTestUtils.click())
    Thread.sleep(EMPIRIC_IDLE_TIMEOUT_MS)
    assert(idleCount == 1)
  }

  @Test
  fun idleTestPanWithFlingMovement() {
    if (!latch.await(EMPIRIC_IDLE_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException("Initial map style load IDLE event not called!")
    }
    Espresso.onView(ViewMatchers.withId(com.mapbox.maps.R.id.mapView)).perform(
      GesturesUiTestUtils.move(
        -maxWidth * 2f,
        0f,
        withVelocity = true,
      )
    )
    Thread.sleep(EMPIRIC_IDLE_TIMEOUT_MS)
    assert(idleCount == 2)
  }

  @Test
  fun idleTestPanWithoutFlingMovement() {
    if (!latch.await(EMPIRIC_IDLE_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException("Initial map style load IDLE event not called!")
    }
    Espresso.onView(ViewMatchers.withId(com.mapbox.maps.R.id.mapView)).perform(
      GesturesUiTestUtils.move(
        -maxWidth / 2f,
        0f,
        withVelocity = true,
      )
    )
    Thread.sleep(EMPIRIC_IDLE_TIMEOUT_MS)
    assert(idleCount == 2)
  }

  @Test
  fun idleTestPanMovementWithAnimationAfter() {
    if (!latch.await(EMPIRIC_IDLE_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException("Initial map style load IDLE event not called!")
    }
    Espresso.onView(ViewMatchers.withId(com.mapbox.maps.R.id.mapView)).perform(
      GesturesUiTestUtils.move(
        -maxWidth / 2f,
        0f,
        withVelocity = true,
      )
    )
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.easeTo(
          cameraOptions {
            pitch(mapboxMap.cameraState.pitch + 30.0)
            zoom(mapboxMap.cameraState.zoom + 2.0)
          }
        )
      }
    }
    Thread.sleep(EMPIRIC_IDLE_TIMEOUT_MS)
    assert(idleCount == 2)
  }

  @Test
  fun idleTestNoGestureJustAnimation() {
    if (!latch.await(EMPIRIC_IDLE_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException("Initial map style load IDLE event not called!")
    }
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.easeTo(
          cameraOptions {
            pitch(mapboxMap.cameraState.pitch + 30.0)
            zoom(mapboxMap.cameraState.zoom + 2.0)
          }
        )
      }
    }
    Thread.sleep(EMPIRIC_IDLE_TIMEOUT_MS)
    assert(idleCount == 2)
  }

  @After
  fun cleanUp() {
    val latch = CountDownLatch(1)
    rule.runOnUiThread {
      mapView.onDestroy()
      latch.countDown()
    }
    idleCount = 0
    if (!BuildConfig.RUN_FROM_IDE) {
      with(InstrumentationRegistry.getInstrumentation().uiAutomation) {
        executeShellCommand("settings put global window_animation_scale 0")
        executeShellCommand("settings put global transition_animation_scale 0")
        executeShellCommand("settings put global animator_duration_scale 0")
      }
    }
  }

  private companion object {
    private const val EMPIRIC_IDLE_TIMEOUT_MS = 5_000L
  }
}
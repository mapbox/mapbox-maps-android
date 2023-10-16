package com.mapbox.maps

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.camera
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Instrumentation test for Layers to test Layer properties.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
abstract class BaseAnimationMapTest {
  private lateinit var mapboxMap: MapboxMap
  protected lateinit var mapView: MapView
  protected lateinit var style: Style
  protected lateinit var cameraAnimationPlugin: CameraAnimationsPlugin

  @get:Rule
  var rule = ActivityScenarioRule(EmptyActivity::class.java)

  @Before
  fun before() {
    if (!BuildConfig.RUN_FROM_IDE) {
      with(InstrumentationRegistry.getInstrumentation().uiAutomation) {
        executeShellCommand("settings put global window_animation_scale 1")
        executeShellCommand("settings put global transition_animation_scale 1")
        executeShellCommand("settings put global animator_duration_scale 1")
      }
    }
    val latch = CountDownLatch(2)
    rule.scenario.onActivity {
      it.runOnUiThread {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        mapView = MapView(context)
        mapView.id = R.id.mapView
        cameraAnimationPlugin = mapView.camera
        it.setContentView(mapView)

        mapboxMap = mapView.mapboxMap.apply {
          loadStyle(
            Style.DARK
          ) { style ->
            this@BaseAnimationMapTest.style = style
            latch.countDown()
          }
          subscribeMapIdle {
            latch.countDown()
          }
        }
        mapView.onStart()
      }
    }

    if (!latch.await(10000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }

  @After
  fun tearDown() {
    if (!BuildConfig.RUN_FROM_IDE) {
      with(InstrumentationRegistry.getInstrumentation().uiAutomation) {
        executeShellCommand("settings put global window_animation_scale 0")
        executeShellCommand("settings put global transition_animation_scale 0")
        executeShellCommand("settings put global animator_duration_scale 0")
      }
    }
  }
}
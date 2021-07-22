package com.mapbox.maps

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.maps.plugin.PLUGIN_LIFE_CYCLE_CLASS_NAME
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
@LargeTest
class LifecycleTest {

  @get:Rule
  val rule = ActivityScenarioRule(EmptyActivity::class.java)

  private lateinit var mapView: MapView
  private lateinit var mapboxMap: MapboxMap
  private lateinit var countDownLatch: CountDownLatch

  @Before
  @UiThreadTest
  fun setUp() {
    rule.scenario.onActivity {
      mapView = MapView(it, MapInitOptions(it, plugins = listOf(PLUGIN_LIFE_CYCLE_CLASS_NAME)))
      mapboxMap = mapView.getMapboxMap()
      it.frameLayout.addView(mapView)
    }
  }

  @Test
  fun testOnStart() {
    countDownLatch = CountDownLatch(1)
    var callbackInvoked = false
    rule.scenario.onActivity {
      it.runOnUiThread {
        // Default style is loaded in onStart
        mapboxMap.getStyle {
          callbackInvoked = true
          countDownLatch.countDown()
        }
      }
    }
    countDownLatch.await(5, TimeUnit.SECONDS)
    assertTrue(callbackInvoked)
  }
}
package com.mapbox.maps

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.maps.exception.WorkerThreadException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ThreadCheckTest {

  @get:Rule
  val rule = ActivityScenarioRule(EmptyActivity::class.java)

  @Test
  @UiThreadTest
  fun testMainThread() {
    rule.scenario.onActivity {
      val mapView = MapView(it, MapInitOptions(it, plugins = listOf()))
      mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)
    }
  }

  @Test(expected = WorkerThreadException::class)
  @UiThreadTest
  fun testCoroutineIO() {
    rule.scenario.onActivity {
      val mapView = MapView(it, MapInitOptions(it, plugins = listOf()))
      runBlocking(Dispatchers.IO) {
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)
      }
    }
  }
}
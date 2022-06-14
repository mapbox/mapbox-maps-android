package com.mapbox.maps.renderer.egl

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import androidx.core.os.postDelayed
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.maps.EmptyActivity
import com.mapbox.maps.MapView
import com.mapbox.maps.renderer.RendererError
import org.junit.After
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
@LargeTest
class RendererSetupTest {

  private lateinit var countDownLatch: CountDownLatch
  private val eventList = CopyOnWriteArrayList<Event>()
  private val mainHandler = Handler(Looper.getMainLooper())

  @get:Rule
  val rule = ActivityScenarioRule(EmptyActivity::class.java)

  enum class Event {
    MAP_LOAD_SUCCESS,
    RENDERER_ERROR,
  }

  @Test
  fun regularCreateMapTest() {
    countDownLatch = CountDownLatch(1)
    eventList.clear()
    rule.scenario.onActivity {
      val mapView = MapView(it)
      it.frameLayout.addView(mapView)
      createMapView(it, mapView, withDelayMs = 0L)
    }
    countDownLatch.await(DEFAULT_LATCH_TIMEOUT_MS, TimeUnit.MILLISECONDS)
    Assert.assertArrayEquals(
      arrayOf(Event.MAP_LOAD_SUCCESS),
      eventList.toArray()
    )
  }

  @Test
  fun recreateMapImmediateOnErrorTest() {
    countDownLatch = CountDownLatch(1)
    eventList.clear()
    rule.scenario.onActivity {
      EGLConfigChooser.INVALID_CONFIG_FOR_TEST = true
      val mapView = MapView(it)
      it.frameLayout.addView(mapView)
      createMapView(it, mapView, withDelayMs = 0L)
    }
    countDownLatch.await(DEFAULT_LATCH_TIMEOUT_MS, TimeUnit.MILLISECONDS)
    Assert.assertArrayEquals(
      arrayOf(Event.RENDERER_ERROR, Event.MAP_LOAD_SUCCESS),
      eventList.toArray()
    )
  }

  @Test
  fun recreateMapWithDelayOnErrorTest() {
    countDownLatch = CountDownLatch(1)
    eventList.clear()
    rule.scenario.onActivity {
      EGLConfigChooser.INVALID_CONFIG_FOR_TEST = true
      val mapView = MapView(it)
      it.frameLayout.addView(mapView)
      createMapView(it, mapView, withDelayMs = 500L)
    }
    countDownLatch.await(DEFAULT_LATCH_TIMEOUT_MS, TimeUnit.MILLISECONDS)
    Assert.assertArrayEquals(
      arrayOf(Event.RENDERER_ERROR, Event.MAP_LOAD_SUCCESS),
      eventList.toArray()
    )
  }

  private fun createMapView(
    activity: Activity,
    mapView: MapView,
    withDelayMs: Long
  ) {
    mapView.onStart()
    mapView.getMapboxMap().addOnMapLoadedListener {
      eventList.add(Event.MAP_LOAD_SUCCESS)
      countDownLatch.countDown()
    }
    mainHandler.postDelayed(withDelayMs) {
      mapView.addRendererSetupErrorListener {
        when (it) {
          is RendererError.EglError -> { /** no-op **/ }
          RendererError.NoValidEglConfigFound -> {
            eventList.add(Event.RENDERER_ERROR)
            mapView.onStop()
            mapView.onDestroy()
            EGLConfigChooser.INVALID_CONFIG_FOR_TEST = false
            val parent = (mapView.parent as ViewGroup)
            val validMapView = MapView(activity)
            parent.removeView(mapView)
            parent.addView(validMapView)
            // create valid map view without delay to subscribe to new renderer errors asap
            createMapView(activity, validMapView, withDelayMs = 0)
          }
          RendererError.OutOfMemory -> { /** no-op **/ }
        }
      }
    }
  }

  @After
  fun cleanUp() {
    EGLConfigChooser.INVALID_CONFIG_FOR_TEST = false
  }

  private companion object {
    const val DEFAULT_LATCH_TIMEOUT_MS = 5_000L
  }
}
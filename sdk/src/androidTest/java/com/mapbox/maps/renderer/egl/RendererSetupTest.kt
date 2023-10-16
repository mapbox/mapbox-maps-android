package com.mapbox.maps.renderer.egl

import android.app.Activity
import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.common.Cancelable
import com.mapbox.maps.EmptyActivity
import com.mapbox.maps.MapView
import com.mapbox.maps.renderer.RendererError
import org.junit.*
import org.junit.Assert.assertArrayEquals
import org.junit.runner.RunWith
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
@LargeTest
class RendererSetupTest {

  private lateinit var countDownLatch: CountDownLatch
  private var cancelable: Cancelable? = null
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
    countDownLatch = CountDownLatch(2)
    rule.scenario.onActivity {
      val validMapView = createMapView(it, valid = true)
      setupMapView(it, validMapView, withDelayMs = 0L)
    }
    countDownLatch.await(DEFAULT_LATCH_TIMEOUT_MS, TimeUnit.MILLISECONDS)
    assertArrayEquals(
      arrayOf(Event.MAP_LOAD_SUCCESS),
      eventList.toArray()
    )
  }

  @Test
  fun recreateMapImmediateOnErrorTest() {
    countDownLatch = CountDownLatch(3)
    rule.scenario.onActivity {
      val invalidMapView = createMapView(it, valid = false)
      val delayMs = 0L
      setupMapView(it, invalidMapView, withDelayMs = delayMs)
      mainHandler.postDelayed(delayMs) {
        val validMapView = createMapView(it, valid = true)
        setupMapView(it, validMapView, withDelayMs = 0)
      }
    }
    countDownLatch.await(DEFAULT_LATCH_TIMEOUT_MS, TimeUnit.MILLISECONDS)
    // noting that number of RENDERER_ERROR is undermined as renderer automatically tries re-creating EGL
    // so we're using distinct function to make sure we eventually re-created the MapView as expected
    assertArrayEquals(
      arrayOf(Event.RENDERER_ERROR, Event.MAP_LOAD_SUCCESS),
      eventList.distinct().toTypedArray()
    )
  }

  @Test
  fun severalErrorListenersTest() {
    countDownLatch = CountDownLatch(2)
    rule.scenario.onActivity { activity ->
      val invalidMapView = createMapView(activity, valid = false)
      invalidMapView.addRendererSetupErrorListener {
        when (it) {
          RendererError.NO_VALID_EGL_CONFIG_FOUND -> {
            eventList.add(Event.RENDERER_ERROR)
            countDownLatch.countDown()
          }
          RendererError.OUT_OF_MEMORY -> { /** no-op **/ }
          else -> { /** no-op **/ }
        }
      }
      invalidMapView.addRendererSetupErrorListener {
        when (it) {
          RendererError.NO_VALID_EGL_CONFIG_FOUND -> {
            eventList.add(Event.RENDERER_ERROR)
            countDownLatch.countDown()
          }
          RendererError.OUT_OF_MEMORY -> { /** no-op **/ }
          else -> { /** no-op **/ }
        }
      }
    }
    countDownLatch.await(DEFAULT_LATCH_TIMEOUT_MS, TimeUnit.MILLISECONDS)
    assertArrayEquals(
      arrayOf(Event.RENDERER_ERROR, Event.RENDERER_ERROR),
      eventList.toArray()
    )
  }

  @Test
  fun recreateMapWithDelayOnErrorTest() {
    countDownLatch = CountDownLatch(3)
    rule.scenario.onActivity {
      val invalidMapView = createMapView(it, valid = false)
      val delayMs = 500L
      setupMapView(it, invalidMapView, withDelayMs = delayMs)
      mainHandler.postDelayed(delayMs) {
        val validMapView = createMapView(it, valid = true)
        setupMapView(it, validMapView, withDelayMs = 0)
      }
    }
    countDownLatch.await(DEFAULT_LATCH_TIMEOUT_MS, TimeUnit.MILLISECONDS)
    // noting that number of RENDERER_ERROR is undermined as renderer automatically tries re-creating EGL
    // so we're using distinct function to make sure we eventually re-created the MapView as expected
    assertArrayEquals(
      arrayOf(Event.RENDERER_ERROR, Event.MAP_LOAD_SUCCESS),
      eventList.distinct().toTypedArray()
    )
  }

  private fun createMapView(activity: EmptyActivity, valid: Boolean): MapView {
    if (valid) {
      EGLConfigChooser.STENCIL_SIZE = VALID_STENCIL_SIZE
    } else {
      EGLConfigChooser.STENCIL_SIZE = INVALID_STENCIL_SIZE
    }
    val mapView = MapView(activity)
    activity.frameLayout.addView(mapView)
    mapView.mapboxMap.loadStyle("{}")
    return mapView
  }

  private fun setupMapView(
    activity: Activity,
    mapView: MapView,
    withDelayMs: Long,
  ) {
    mapView.onStart()
    cancelable?.cancel()
    cancelable = mapView.mapboxMap.subscribeMapLoaded {
      eventList.add(Event.MAP_LOAD_SUCCESS)
      mapView.onStop()
      mapView.onDestroy()
      countDownLatch.countDown()
    }
    mainHandler.postDelayed(withDelayMs) {
      mapView.addRendererSetupErrorListener {
        when (it) {
          RendererError.NO_VALID_EGL_CONFIG_FOUND -> {
            eventList.add(Event.RENDERER_ERROR)
            mapView.onStop()
            mapView.onDestroy()
            (activity as EmptyActivity).frameLayout.removeView(mapView)
          }
          RendererError.OUT_OF_MEMORY -> { /** no-op **/ }
          else -> { /** no-op **/ }
        }
      }
      countDownLatch.countDown()
    }
  }

  @UiThreadTest
  @After
  fun cleanUp() {
    cancelable?.cancel()
    cancelable = null
    eventList.clear()
    EGLConfigChooser.STENCIL_SIZE = VALID_STENCIL_SIZE
    mainHandler.removeCallbacksAndMessages(null)
  }

  private companion object {
    const val DEFAULT_LATCH_TIMEOUT_MS = 10_000L
    const val INVALID_STENCIL_SIZE = 23423
    const val VALID_STENCIL_SIZE = 8
  }
}
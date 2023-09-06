package com.mapbox.maps.renderer.egl

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import androidx.core.os.postDelayed
import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
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
@Ignore("Tests are flaky")
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

  @Before
  @UiThreadTest
  fun setUp() {
    countDownLatch = CountDownLatch(1)
    eventList.clear()
  }

  @Test
  fun regularCreateMapTest() {
    rule.scenario.onActivity {
      val mapView = MapView(it)
      it.frameLayout.addView(mapView)
      createMapView(it, mapView, withDelayMs = 0L)
    }
    countDownLatch.await(DEFAULT_LATCH_TIMEOUT_MS, TimeUnit.MILLISECONDS)
    assertArrayEquals(
      arrayOf(Event.MAP_LOAD_SUCCESS),
      eventList.toArray()
    )
  }

  @Test
  fun recreateMapImmediateOnErrorTest() {
    rule.scenario.onActivity {
      EGLConfigChooser.STENCIL_SIZE = INVALID_STENCIL_SIZE
      val mapView = MapView(it)
      it.frameLayout.addView(mapView)
      createMapView(it, mapView, withDelayMs = 0L)
    }
    countDownLatch.await(DEFAULT_LATCH_TIMEOUT_MS, TimeUnit.MILLISECONDS)
    assertArrayEquals(
      arrayOf(Event.RENDERER_ERROR, Event.MAP_LOAD_SUCCESS),
      eventList.toArray()
    )
  }

  @Test
  fun severalErrorListenersTest() {
    countDownLatch = CountDownLatch(2)
    rule.scenario.onActivity { activity ->
      EGLConfigChooser.STENCIL_SIZE = INVALID_STENCIL_SIZE
      val mapView = MapView(activity)
      activity.frameLayout.addView(mapView)
      mapView.addRendererSetupErrorListener {
        when (it) {
          RendererError.NO_VALID_EGL_CONFIG_FOUND -> {
            eventList.add(Event.RENDERER_ERROR)
            countDownLatch.countDown()
          }
          RendererError.OUT_OF_MEMORY -> { /** no-op **/ }
          else -> { /** no-op **/ }
        }
      }
      mapView.addRendererSetupErrorListener {
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
    rule.scenario.onActivity {
      EGLConfigChooser.STENCIL_SIZE = INVALID_STENCIL_SIZE
      val mapView = MapView(it)
      it.frameLayout.addView(mapView)
      createMapView(it, mapView, withDelayMs = 500L)
    }
    countDownLatch.await(DEFAULT_LATCH_TIMEOUT_MS, TimeUnit.MILLISECONDS)
    assertArrayEquals(
      arrayOf(Event.RENDERER_ERROR, Event.MAP_LOAD_SUCCESS),
      eventList.toArray()
    )
  }

  private fun createMapView(
    activity: Activity,
    mapView: MapView,
    withDelayMs: Long,
  ) {
    mapView.onStart()
    mapView.getMapboxMap().subscribeMapLoaded {
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
            EGLConfigChooser.STENCIL_SIZE = VALID_STENCIL_SIZE
            val parent = (mapView.parent as ViewGroup)
            val validMapView = MapView(activity)
            parent.removeView(mapView)
            parent.addView(validMapView)
            // create valid map view without delay to subscribe to new renderer errors asap
            createMapView(activity, validMapView, withDelayMs = 0)
          }
          RendererError.OUT_OF_MEMORY -> { /** no-op **/ }
          else -> { /** no-op **/ }
        }
      }
    }
  }

  @After
  @UiThreadTest
  fun cleanUp() {
    EGLConfigChooser.STENCIL_SIZE = VALID_STENCIL_SIZE
  }

  private companion object {
    const val DEFAULT_LATCH_TIMEOUT_MS = 10_000L
    const val INVALID_STENCIL_SIZE = 23423
    const val VALID_STENCIL_SIZE = 8
  }
}
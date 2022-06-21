package com.mapbox.maps.extension.lifecycle

import android.content.ComponentCallbacks2
import android.content.ComponentCallbacks2.TRIM_MEMORY_BACKGROUND
import android.content.ComponentCallbacks2.TRIM_MEMORY_COMPLETE
import android.content.ComponentCallbacks2.TRIM_MEMORY_MODERATE
import android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL
import android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW
import android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE
import android.content.ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN
import android.content.Context
import android.widget.FrameLayout
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewTreeLifecycleOwner
import com.mapbox.maps.MapboxLifecycleObserver
import com.mapbox.maps.logW
import com.mapbox.maps.plugin.lifecycle.MapboxLifecyclePlugin
import com.mapbox.maps.plugin.lifecycle.MapboxLifecyclePluginImpl
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

class MapboxLifecyclePluginImplTest {
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  private lateinit var mapboxLifecyclePlugin: MapboxLifecyclePlugin
  private val mapView: FrameLayout = mockk(relaxed = true)
  private val context: Context = mockk(relaxed = true)
  private val lifecycleOwner: LifecycleOwner = mockk(relaxed = true)
  private val slot = slot<ComponentCallbacks2>()

  @Before
  fun setUp() {
    mockkStatic(ViewTreeLifecycleOwner::class)
    every { mapView.context } returns context
    every { ViewTreeLifecycleOwner.get(any()) } returns lifecycleOwner
    mapboxLifecyclePlugin = MapboxLifecyclePluginImpl()
  }

  @Test
  fun testOnLowMemory() {
    val mapboxLifecycleObserver: MapboxLifecycleObserver = mockk(relaxed = true)
    every { context.registerComponentCallbacks(capture(slot)) } answers {
      slot.captured.onLowMemory()
    }
    mapboxLifecyclePlugin.registerLifecycleObserver(mapView, mapboxLifecycleObserver)

    verify { mapboxLifecycleObserver.onLowMemory() }
  }
}

@RunWith(Parameterized::class)
class TrimMemoryLevelTest(private val level: Pair<Int, Int>) {
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  private lateinit var mapboxLifecyclePlugin: MapboxLifecyclePlugin
  private val mapView: FrameLayout = mockk(relaxed = true)
  private val context: Context = mockk(relaxed = true)
  private val lifecycleOwner: LifecycleOwner = mockk(relaxed = true)
  private val slot = slot<ComponentCallbacks2>()

  @Before
  fun setUp() {
    mockkStatic(ViewTreeLifecycleOwner::class)
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logW(any(), any()) } just Runs
    every { mapView.context } returns context
    every { ViewTreeLifecycleOwner.get(any()) } returns lifecycleOwner
    mapboxLifecyclePlugin = MapboxLifecyclePluginImpl()
  }

  @After
  fun cleanUp() {
    unmockkStatic(ViewTreeLifecycleOwner::class)
    unmockkStatic("com.mapbox.maps.MapboxLogger")
  }

  @Test
  fun checkOnLowMemoryIsCalled() {
    val mapboxLifecycleObserver: MapboxLifecycleObserver = mockk(relaxed = true)
    every { context.registerComponentCallbacks(capture(slot)) } answers {
      slot.captured.onTrimMemory(level.first)
    }
    mapboxLifecyclePlugin.registerLifecycleObserver(mapView, mapboxLifecycleObserver)

    verify(exactly = level.second) { mapboxLifecycleObserver.onLowMemory() }
  }

  companion object {
    @JvmStatic
    @Parameterized.Parameters(name = "The TrimMemoryLevel and number of onLowMemory calls should match: {0}")
    fun data() = listOf(
      TRIM_MEMORY_RUNNING_CRITICAL to 1,
      TRIM_MEMORY_RUNNING_LOW to 1,
      TRIM_MEMORY_BACKGROUND to 0,
      TRIM_MEMORY_COMPLETE to 0,
      TRIM_MEMORY_MODERATE to 0,
      TRIM_MEMORY_RUNNING_MODERATE to 0,
      TRIM_MEMORY_UI_HIDDEN to 0
    )
  }
}
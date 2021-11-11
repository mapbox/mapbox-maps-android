package com.mapbox.maps.extension.lifecycle

import android.content.ComponentCallbacks2
import android.content.Context
import android.widget.FrameLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewTreeLifecycleOwner
import com.mapbox.maps.MapboxLifecycleObserver
import com.mapbox.maps.plugin.lifecycle.MapboxLifecyclePlugin
import com.mapbox.maps.plugin.lifecycle.MapboxLifecyclePluginImpl
import io.mockk.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

class MapboxLifecyclePluginImplTest {
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
class AcceptedTrimMemoryLevelTest(private val level: Int) {
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
  fun checkOnLowMemoryIsCalled() {
    val mapboxLifecycleObserver: MapboxLifecycleObserver = mockk(relaxed = true)
    every { context.registerComponentCallbacks(capture(slot)) } answers {
      slot.captured.onTrimMemory(level)
    }
    mapboxLifecyclePlugin.registerLifecycleObserver(mapView, mapboxLifecycleObserver)

    verify { mapboxLifecycleObserver.onLowMemory() }
  }

  companion object {
    @JvmStatic
    @Parameterized.Parameters(name = "TrimMemoryLevel {0} should trigger onLowMemory")
    fun data() = listOf(
      ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL,
      ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW
    )
  }
}

@RunWith(Parameterized::class)
class IgnoredTrimMemoryLevelTest(private val level: Int) {
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
  fun checkOnLowMemoryIsNotCalled() {
    val mapboxLifecycleObserver: MapboxLifecycleObserver = mockk(relaxed = true)
    every { context.registerComponentCallbacks(capture(slot)) } answers {
      slot.captured.onTrimMemory(level)
    }
    mapboxLifecyclePlugin.registerLifecycleObserver(mapView, mapboxLifecycleObserver)

    verify(exactly = 0) { mapboxLifecycleObserver.onLowMemory() }
  }

  companion object {
    @JvmStatic
    @Parameterized.Parameters(name = "TrimMemoryLevel {0} should not trigger onLowMemory")
    fun data() = listOf(
      ComponentCallbacks2.TRIM_MEMORY_BACKGROUND,
      ComponentCallbacks2.TRIM_MEMORY_COMPLETE,
      ComponentCallbacks2.TRIM_MEMORY_MODERATE,
      ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE,
      ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN
    )
  }
}
package com.mapbox.maps.extension.lifecycle

import android.content.ComponentCallbacks
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

class MapboxLifecyclePluginImplTest {
  private lateinit var mapboxLifecyclePlugin: MapboxLifecyclePlugin
  private val mapView: FrameLayout = mockk(relaxed = true)
  private val context: Context = mockk(relaxed = true)
  private val lifecycleOwner: LifecycleOwner = mockk(relaxed = true)
  private val slot = slot<ComponentCallbacks>()

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
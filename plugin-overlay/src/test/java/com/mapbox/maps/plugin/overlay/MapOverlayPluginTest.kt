package com.mapbox.maps.plugin.overlay

import android.view.View
import android.widget.FrameLayout
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.CoordinateBounds
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import io.mockk.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(MapboxExperimental::class)
@RunWith(RobolectricTestRunner::class)
class MapOverlayPluginTest {

  private lateinit var mapOverlayPlugin: MapOverlayPluginImpl
  private val delegateProvider = mockk<MapDelegateProvider>(relaxUnitFun = true)
  private val mapCameraManagerDelegate = mockk<MapCameraManagerDelegate>()
  val mapView = mockk<FrameLayout>()

  @Before
  fun setUp() {
    every { delegateProvider.mapCameraManagerDelegate } returns mapCameraManagerDelegate
    mapOverlayPlugin = MapOverlayPluginImpl()
    mapOverlayPlugin.onDelegateProvider(delegateProvider)
    assertEquals(0, mapOverlayPlugin.width)
    assertEquals(0, mapOverlayPlugin.height)
    mapOverlayPlugin.initialize()
    mapOverlayPlugin.onSizeChanged(100, 100)
    assertEquals(100, mapOverlayPlugin.width)
    assertEquals(100, mapOverlayPlugin.height)
  }

  @After
  fun clean() {
    mapOverlayPlugin.cleanup()
  }

  @Test
  fun getEdgeInsets() {
    var edgeInsets = mapOverlayPlugin.getEdgeInsets()
    assertEquals(EdgeInsets(0.0, 0.0, 0.0, 0.0), edgeInsets)

    mapOverlayPlugin.setDisplayingAreaMargins(10, 20, 30, 40)
    edgeInsets = mapOverlayPlugin.getEdgeInsets()
    assertEquals(EdgeInsets(10.0, 20.0, 30.0, 40.0), edgeInsets)

    mapOverlayPlugin.setDisplayingAreaMargins(0, 0, 0, 0)

    val overlay = mockk<View>()
    every { overlay.top } returns 0
    every { overlay.left } returns 0
    every { overlay.bottom } returns 50
    every { overlay.right } returns 100

    mapOverlayPlugin.registerOverlay(overlay)

    edgeInsets = mapOverlayPlugin.getEdgeInsets()
    assertEquals(EdgeInsets(50.0, 0.0, 0.0, 0.0), edgeInsets)

    mapOverlayPlugin.setDisplayingAreaMargins(10, 20, 30, 40)
    edgeInsets = mapOverlayPlugin.getEdgeInsets()
    assertEquals(EdgeInsets(60.0, 20.0, 30.0, 40.0), edgeInsets)
    mapOverlayPlugin.setDisplayingAreaMargins(0, 0, 0, 0)

    val overlay1 = mockk<View>()
    every { overlay1.top } returns 50
    every { overlay1.left } returns 0
    every { overlay1.bottom } returns 100
    every { overlay1.right } returns 50

    mapOverlayPlugin.registerOverlay(overlay1)

    edgeInsets = mapOverlayPlugin.getEdgeInsets()
    assertEquals(EdgeInsets(50.0, 50.0, 0.0, 0.0), edgeInsets)

    mapOverlayPlugin.unregisterOverlay(overlay)
    mapOverlayPlugin.unregisterOverlay(overlay1)
    edgeInsets = mapOverlayPlugin.getEdgeInsets()
    assertEquals(EdgeInsets(0.0, 0.0, 0.0, 0.0), edgeInsets)
  }

  @Test
  fun getEdgeInsetsMultiOverlays() {
    var edgeInsets = mapOverlayPlugin.getEdgeInsets()
    assertEquals(EdgeInsets(0.0, 0.0, 0.0, 0.0), edgeInsets)

    val overlay = mockk<View>()
    every { overlay.top } returns 0
    every { overlay.left } returns 0
    every { overlay.bottom } returns 50
    every { overlay.right } returns 100

    val overlay1 = mockk<View>()
    every { overlay1.top } returns 50
    every { overlay1.left } returns 0
    every { overlay1.bottom } returns 100
    every { overlay1.right } returns 50

    mapOverlayPlugin.registerOverlays(listOf(overlay, overlay1))

    edgeInsets = mapOverlayPlugin.getEdgeInsets()
    assertEquals(EdgeInsets(50.0, 50.0, 0.0, 0.0), edgeInsets)

    val overlay2 = mockk<View>()
    every { overlay2.top } returns 80
    every { overlay2.left } returns 50
    every { overlay2.bottom } returns 100
    every { overlay2.right } returns 100

    val overlay3 = mockk<View>()
    every { overlay3.top } returns 50
    every { overlay3.left } returns 80
    every { overlay3.bottom } returns 80
    every { overlay3.right } returns 100

    mapOverlayPlugin.registerOverlays(listOf(overlay2, overlay3))
    edgeInsets = mapOverlayPlugin.getEdgeInsets()
    assertEquals(EdgeInsets(50.0, 50.0, 20.0, 20.0), edgeInsets)

    mapOverlayPlugin.unregisterOverlays(listOf(overlay, overlay1, overlay2, overlay3))
    edgeInsets = mapOverlayPlugin.getEdgeInsets()
    assertEquals(EdgeInsets(0.0, 0.0, 0.0, 0.0), edgeInsets)
  }

  @Test
  fun getReframeCameraOption() {
    val point = Point.fromLngLat(0.0, 0.0)
    mapOverlayPlugin.reframe {
      assertNull(it)
    }
    mapOverlayPlugin.registerMapOverlayCoordinatesProvider(object : MapOverlayCoordinatesProvider {
      override fun getShownCoordinates(): List<Point> {
        return listOf(point)
      }
    })

    val slot = slot<CoordinateBounds>()
    val paddingSlot = slot<EdgeInsets>()
    every {
      mapCameraManagerDelegate.cameraForCoordinateBounds(
        capture(slot),
        capture(paddingSlot),
        any(),
        any()
      )
    } answers { CameraOptions.Builder().center(point).build() }
    mapOverlayPlugin.reframe {
      assertNotNull(it)
      assertEquals(point, it!!.center)
    }

    assertEquals(
      CoordinateBounds(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0), false),
      slot.captured
    )
    assertEquals(EdgeInsets(0.0, .00, 0.0, 0.0), paddingSlot.captured)

    val cameraSlot = slot<CameraOptions>()
    every { mapCameraManagerDelegate.setCamera(capture(cameraSlot)) } just Runs
    mapOverlayPlugin.reframe()
    mapOverlayPlugin.reframe {
      assertEquals(it, cameraSlot.captured)
    }

    mapOverlayPlugin.unregisterMapOverlayCoordinatesProvider()
    mapOverlayPlugin.reframe {
      assertNull(it)
    }
  }
}
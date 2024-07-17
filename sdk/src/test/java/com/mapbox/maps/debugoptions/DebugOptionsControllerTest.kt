package com.mapbox.maps.debugoptions

import com.mapbox.maps.CameraChangedCallback
import com.mapbox.maps.MapDebugOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DebugOptionsControllerTest {
  private val mapboxMap: MapboxMap = mockk(relaxed = true)
  private val cameraDebugView: CameraDebugView = mockk(relaxed = true)
  private val paddingDebugView: PaddingDebugView = mockk(relaxed = true)
  private val mapView: MapView = mockk(relaxed = true)
  private lateinit var debugOptionsController: DebugOptionsController

  @Before
  fun setUp() {
    every { mapView.context } returns mockk(relaxed = true)
    every { cameraDebugView.parent } returns null
    every { paddingDebugView.parent } returns null

    debugOptionsController =
      DebugOptionsController(mapView, mapboxMap, { cameraDebugView }, { paddingDebugView })
  }

  @Test
  fun updatesDebugViewsWhenCameraUpdated() {
    val onCameraChangeListenerSlot = slot<CameraChangedCallback>()
    every { mapboxMap.subscribeCameraChanged(capture(onCameraChangeListenerSlot)) } returns mockk()

    debugOptionsController.onStart()
    debugOptionsController.options = setOf(MapViewDebugOptions.CAMERA, MapViewDebugOptions.PADDING)

    val onCameraChangeListener = onCameraChangeListenerSlot.captured
    onCameraChangeListener.run(mockk(relaxed = true))

    verify { cameraDebugView.update(any()) }
    verify { paddingDebugView.update(any()) }
  }

  @Test
  fun providesInitialDataToDebugViews() {
    debugOptionsController.options = setOf(MapViewDebugOptions.CAMERA, MapViewDebugOptions.PADDING)

    verify { cameraDebugView.update(any()) }
    verify { paddingDebugView.update(any()) }
  }

  @Test
  fun propagatesNativeDebugOptions() {
    debugOptionsController.options = setOf(
      MapViewDebugOptions.TILE_BORDERS,
      MapViewDebugOptions.COLLISION,
      MapViewDebugOptions.CAMERA
    )

    verify {
      mapboxMap.debugOptions = setOf(MapDebugOptions.TILE_BORDERS, MapDebugOptions.COLLISION)
    }
  }

  @Test
  fun addsDebugViewsWhenEnabled() {
    debugOptionsController.options = setOf(MapViewDebugOptions.CAMERA, MapViewDebugOptions.PADDING)

    verify { mapView.addView(cameraDebugView) }
    verify { mapView.addView(paddingDebugView) }
  }

  @Test
  fun removesCameraDebugViewsWhenEnabled() {
    debugOptionsController.options = setOf(MapViewDebugOptions.CAMERA, MapViewDebugOptions.PADDING)

    debugOptionsController.options = setOf()

    verify { mapView.removeView(cameraDebugView) }
    verify { mapView.removeView(paddingDebugView) }
  }
}
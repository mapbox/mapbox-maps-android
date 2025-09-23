package com.mapbox.maps.debugoptions

import androidx.test.core.app.ApplicationProvider
import com.mapbox.annotation.MapboxExperimental
import com.mapbox.common.Cancelable
import com.mapbox.maps.CameraChangedCoalescedCallback
import com.mapbox.maps.MapDebugOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.verifyNo
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(MapboxExperimental::class)
@RunWith(RobolectricTestRunner::class)
class DebugOptionsControllerTest {
  private val mapboxMap: MapboxMap = mockk(relaxed = true)
  private val cameraDebugView: CameraDebugView = mockk(relaxed = true)
  private val paddingDebugView: PaddingDebugView = mockk(relaxed = true)
  private val mapView: MapView = mockk(relaxed = true)
  private lateinit var debugOptionsController: DebugOptionsController

  @Before
  fun setUp() {
    clearAllMocks()
    every { mapView.context } returns ApplicationProvider.getApplicationContext()
    every { cameraDebugView.parent } returns null
    every { paddingDebugView.parent } returns null

    debugOptionsController =
      DebugOptionsController(mapView, mapboxMap, { cameraDebugView }, { paddingDebugView })
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun updatesDebugViewsWhenCameraUpdated() {
    val onCameraChangeCoalescedListenerSlot = slot<CameraChangedCoalescedCallback>()
    every { mapboxMap.subscribeCameraChangedCoalesced(capture(onCameraChangeCoalescedListenerSlot)) } returns mockk()

    debugOptionsController.started = true
    debugOptionsController.options = setOf(MapViewDebugOptions.CAMERA, MapViewDebugOptions.PADDING)

    // Verify the callback was captured, then invoke it
    verify { mapboxMap.subscribeCameraChangedCoalesced(any()) }
    val onCameraChangeCoalescedListener = onCameraChangeCoalescedListenerSlot.captured
    onCameraChangeCoalescedListener.run(mockk(relaxed = true))

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

  @Test
  fun subscribesToCameraUpdatesOnStart() {
    debugOptionsController.options = setOf(MapViewDebugOptions.CAMERA)

    verifyNo { mapboxMap.subscribeCameraChangedCoalesced(any()) }

    debugOptionsController.started = true

    verify { mapboxMap.subscribeCameraChangedCoalesced(any()) }
  }

  @Test
  fun doesNotSubscribeToCameraUpdatesOnStart() {
    debugOptionsController.options = setOf(MapViewDebugOptions.COLLISION)

    debugOptionsController.started = true

    verifyNo { mapboxMap.subscribeCameraChangedCoalesced(any()) }
  }

  @Test
  fun subscribesToCameraUpdatesAfterStart() {
    debugOptionsController.started = true

    verifyNo { mapboxMap.subscribeCameraChangedCoalesced(any()) }

    debugOptionsController.options = setOf(MapViewDebugOptions.PADDING)

    verify { mapboxMap.subscribeCameraChangedCoalesced(any()) }
  }

  @Test
  fun doesNotSubscribeToCameraUpdatesBeforeStart() {
    debugOptionsController.options = setOf(MapViewDebugOptions.CAMERA)

    verifyNo { mapboxMap.subscribeCameraChangedCoalesced(any()) }
  }

  @Test
  fun unsubscribesFromCameraUpdatesOnStop() {
    val cancelableMock = mockk<Cancelable>(relaxed = true)
    every { mapboxMap.subscribeCameraChangedCoalesced(any()) } returns cancelableMock

    debugOptionsController.options = setOf(MapViewDebugOptions.CAMERA)
    debugOptionsController.started = true

    debugOptionsController.started = false

    verify { cancelableMock.cancel() }
  }

  @Test
  fun unsubscribesFromCameraUpdatesWhenNeeded() {
    val cancelableMock = mockk<Cancelable>(relaxed = true)
    every { mapboxMap.subscribeCameraChangedCoalesced(any()) } returns cancelableMock
    debugOptionsController.options = setOf(MapViewDebugOptions.CAMERA)
    debugOptionsController.started = true

    debugOptionsController.options = setOf(MapViewDebugOptions.TILE_BORDERS)

    verify { cancelableMock.cancel() }
  }
}
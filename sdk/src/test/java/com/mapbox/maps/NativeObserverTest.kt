package com.mapbox.maps

import com.mapbox.common.Cancelable
import com.mapbox.maps.plugin.delegates.listeners.*
import com.mapbox.maps.shadows.ShadowCancelable
import com.mapbox.maps.shadows.ShadowObservable
import com.mapbox.verifyNo
import io.mockk.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(
  shadows = [
    ShadowObservable::class,
    ShadowCancelable::class
  ]
)
class NativeObserverTest {

  private val observableInterface = mockk<NativeMapImpl>(relaxed = true)
  private lateinit var nativeObserver: NativeObserver
  private val cancelable = mockk<Cancelable>(relaxUnitFun = true)

  @Before
  fun setUp() {
    nativeObserver = NativeObserver(observableInterface)
    every { cancelable.cancel() } just runs
  }

  @Test
  fun addOnCameraChangeListener() {
    val listener = mockk<OnCameraChangeListener>(relaxUnitFun = true)
    nativeObserver.addOnCameraChangeListener(listener)
    assertEquals(1, nativeObserver.onCameraChangeListeners.size)
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.CAMERA_CHANGED))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.CAMERA_CHANGED]?.size)

    val listener2 = mockk<OnCameraChangeListener>(relaxUnitFun = true)
    nativeObserver.addOnCameraChangeListener(listener2)
    assertEquals(2, nativeObserver.onCameraChangeListeners.size)
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.CAMERA_CHANGED]?.size)
  }

  @Test
  fun removeOnCameraChangeListener() {
    val listener = mockk<OnCameraChangeListener>(relaxUnitFun = true)
    val listener2 = mockk<OnCameraChangeListener>(relaxUnitFun = true)
    nativeObserver.addOnCameraChangeListener(listener)
    nativeObserver.addOnCameraChangeListener(listener2)
    assertEquals(2, nativeObserver.onCameraChangeListeners.size)
    nativeObserver.removeOnCameraChangeListener(listener)
    assertEquals(1, nativeObserver.onCameraChangeListeners.size)
    nativeObserver.removeOnCameraChangeListener(listener2)
    assertEquals(0, nativeObserver.onCameraChangeListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.containsKey(MapEvent.CAMERA_CHANGED))
    verifyNo { listener.onCameraChanged(any()) }
    verifyNo { listener2.onCameraChanged(any()) }
  }

  // Map events
  @Test
  fun addOnMapIdleListener() {
    val listener = mockk<OnMapIdleListener>(relaxUnitFun = true)
    nativeObserver.addOnMapIdleListener(listener)
    assertEquals(1, nativeObserver.onMapIdleListeners.size)
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.MAP_IDLE))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.MAP_IDLE]?.size)

    val listener2 = mockk<OnMapIdleListener>(relaxUnitFun = true)
    nativeObserver.addOnMapIdleListener(listener2)
    assertEquals(2, nativeObserver.onMapIdleListeners.size)
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.MAP_IDLE]?.size)
  }

  @Test
  fun removeOnMapIdleListener() {
    val listener = mockk<OnMapIdleListener>(relaxUnitFun = true)
    val listener2 = mockk<OnMapIdleListener>(relaxUnitFun = true)
    nativeObserver.addOnMapIdleListener(listener)
    nativeObserver.addOnMapIdleListener(listener2)
    assertEquals(2, nativeObserver.onMapIdleListeners.size)

    nativeObserver.removeOnMapIdleListener(listener)
    assertEquals(1, nativeObserver.onMapIdleListeners.size)

    nativeObserver.removeOnMapIdleListener(listener2)
    assertEquals(0, nativeObserver.onMapIdleListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.contains(MapEvent.MAP_IDLE))
    verifyNo { listener.onMapIdle(any()) }
    verifyNo { listener2.onMapIdle(any()) }
  }

  @Test
  fun addOnMapLoadErrorListener() {
    val listener = mockk<OnMapLoadErrorListener>(relaxUnitFun = true)
    nativeObserver.addOnMapLoadErrorListener(listener)
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.MAP_LOADING_ERROR))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.MAP_LOADING_ERROR]?.size)

    val listener2 = mockk<OnMapLoadErrorListener>(relaxUnitFun = true)
    nativeObserver.addOnMapLoadErrorListener(listener2)
    assertEquals(2, nativeObserver.onMapLoadErrorListeners.size)
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.MAP_LOADING_ERROR]?.size)
  }

  @Test
  fun removeOnMapLoadErrorListener() {
    val listener = mockk<OnMapLoadErrorListener>(relaxUnitFun = true)
    val listener2 = mockk<OnMapLoadErrorListener>(relaxUnitFun = true)
    nativeObserver.addOnMapLoadErrorListener(listener)
    nativeObserver.addOnMapLoadErrorListener(listener2)
    assertEquals(2, nativeObserver.onMapLoadErrorListeners.size)

    nativeObserver.removeOnMapLoadErrorListener(listener)
    assertEquals(1, nativeObserver.onMapLoadErrorListeners.size)

    nativeObserver.removeOnMapLoadErrorListener(listener2)
    assertEquals(0, nativeObserver.onMapLoadErrorListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.contains(MapEvent.MAP_LOADING_ERROR))
    verifyNo { listener.onMapLoadError(any()) }
    verifyNo { listener2.onMapLoadError(any()) }
  }

  @Test
  fun addOnMapLoadedListener() {
    val listener = mockk<OnMapLoadedListener>(relaxUnitFun = true)
    nativeObserver.addOnMapLoadedListener(listener)
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.MAP_LOADED))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.MAP_LOADED]?.size)

    val listener2 = mockk<OnMapLoadedListener>(relaxUnitFun = true)
    nativeObserver.addOnMapLoadedListener(listener2)
    assertEquals(2, nativeObserver.onMapLoadedListeners.size)
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.MAP_LOADED]?.size)
  }

  @Test
  fun removeOnMapLoadedListener() {
    val listener = mockk<OnMapLoadedListener>(relaxUnitFun = true)
    val listener2 = mockk<OnMapLoadedListener>(relaxUnitFun = true)
    nativeObserver.addOnMapLoadedListener(listener)
    nativeObserver.addOnMapLoadedListener(listener2)
    assertEquals(2, nativeObserver.onMapLoadedListeners.size)

    nativeObserver.removeOnMapLoadedListener(listener)
    assertEquals(1, nativeObserver.onMapLoadedListeners.size)

    nativeObserver.removeOnMapLoadedListener(listener2)
    assertEquals(0, nativeObserver.onMapLoadedListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.contains(MapEvent.MAP_LOADED))
    verifyNo { listener.onMapLoaded(any()) }
    verifyNo { listener2.onMapLoaded(any()) }
  }

  // Render frame events
  @Test
  fun addOnRenderFrameFinishedListener() {
    val listener = mockk<OnRenderFrameFinishedListener>(relaxUnitFun = true)
    nativeObserver.addOnRenderFrameFinishedListener(listener)
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.RENDER_FRAME_FINISHED))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.RENDER_FRAME_FINISHED]?.size)

    val listener2 = mockk<OnRenderFrameFinishedListener>(relaxUnitFun = true)
    nativeObserver.addOnRenderFrameFinishedListener(listener2)
    assertEquals(2, nativeObserver.onRenderFrameFinishedListeners.size)
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.RENDER_FRAME_FINISHED]?.size)
  }

  @Test
  fun removeOnRenderFrameFinishedListener() {
    val listener = mockk<OnRenderFrameFinishedListener>(relaxUnitFun = true)
    val listener2 = mockk<OnRenderFrameFinishedListener>(relaxUnitFun = true)
    nativeObserver.addOnRenderFrameFinishedListener(listener)
    nativeObserver.addOnRenderFrameFinishedListener(listener2)
    assertEquals(2, nativeObserver.onRenderFrameFinishedListeners.size)

    nativeObserver.removeOnRenderFrameFinishedListener(listener)
    assertEquals(1, nativeObserver.onRenderFrameFinishedListeners.size)

    nativeObserver.removeOnRenderFrameFinishedListener(listener2)
    assertEquals(0, nativeObserver.onRenderFrameFinishedListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.contains(MapEvent.RENDER_FRAME_FINISHED))
    verifyNo { listener.onRenderFrameFinished(any()) }
    verifyNo { listener2.onRenderFrameFinished(any()) }
  }

  @Test
  fun addOnRenderFrameStartedListener() {
    val listener = mockk<OnRenderFrameStartedListener>(relaxUnitFun = true)
    nativeObserver.addOnRenderFrameStartedListener(listener)
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.RENDER_FRAME_STARTED))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.RENDER_FRAME_STARTED]?.size)

    val listener2 = mockk<OnRenderFrameStartedListener>(relaxUnitFun = true)
    nativeObserver.addOnRenderFrameStartedListener(listener2)
    assertEquals(2, nativeObserver.onRenderFrameStartedListeners.size)
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.RENDER_FRAME_STARTED]?.size)
  }

  @Test
  fun removeOnRenderFrameStartedListener() {
    val listener = mockk<OnRenderFrameStartedListener>(relaxUnitFun = true)
    val listener2 = mockk<OnRenderFrameStartedListener>(relaxUnitFun = true)
    nativeObserver.addOnRenderFrameStartedListener(listener)
    nativeObserver.addOnRenderFrameStartedListener(listener2)
    assertEquals(2, nativeObserver.onRenderFrameStartedListeners.size)

    nativeObserver.removeOnRenderFrameStartedListener(listener)
    assertEquals(1, nativeObserver.onRenderFrameStartedListeners.size)

    nativeObserver.removeOnRenderFrameStartedListener(listener2)
    assertEquals(0, nativeObserver.onRenderFrameStartedListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.contains(MapEvent.RENDER_FRAME_STARTED))
    verifyNo { listener.onRenderFrameStarted(any()) }
    verifyNo { listener2.onRenderFrameStarted(any()) }
  }

  // Source events
  @Test
  fun addOnSourceAddedListener() {
    val listener = mockk<OnSourceAddedListener>(relaxUnitFun = true)
    nativeObserver.addOnSourceAddedListener(listener)
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.SOURCE_ADDED))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.SOURCE_ADDED]?.size)

    val listener2 = mockk<OnSourceAddedListener>(relaxUnitFun = true)
    nativeObserver.addOnSourceAddedListener(listener2)
    assertEquals(2, nativeObserver.onSourceAddedListeners.size)
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.SOURCE_ADDED]?.size)
  }

  @Test
  fun removeOnSourceAddedListener() {
    val listener = mockk<OnSourceAddedListener>(relaxUnitFun = true)
    val listener2 = mockk<OnSourceAddedListener>(relaxUnitFun = true)
    nativeObserver.addOnSourceAddedListener(listener)
    nativeObserver.addOnSourceAddedListener(listener2)
    assertEquals(2, nativeObserver.onSourceAddedListeners.size)

    nativeObserver.removeOnSourceAddedListener(listener)
    assertEquals(1, nativeObserver.onSourceAddedListeners.size)

    nativeObserver.removeOnSourceAddedListener(listener2)
    assertEquals(0, nativeObserver.onSourceAddedListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.contains(MapEvent.SOURCE_ADDED))
    verifyNo { listener.onSourceAdded(any()) }
    verifyNo { listener2.onSourceAdded(any()) }
  }

  @Test
  fun addOnSourceDataLoadedListener() {
    val listener = mockk<OnSourceDataLoadedListener>(relaxUnitFun = true)
    nativeObserver.addOnSourceDataLoadedListener(listener)
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.SOURCE_DATA_LOADED))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.SOURCE_DATA_LOADED]?.size)

    val listener2 = mockk<OnSourceDataLoadedListener>(relaxUnitFun = true)
    nativeObserver.addOnSourceDataLoadedListener(listener2)
    assertEquals(2, nativeObserver.onSourceDataLoadedListeners.size)
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.SOURCE_DATA_LOADED]?.size)
  }

  @Test
  fun removeOnSourceDataLoadedListener() {
    val listener = mockk<OnSourceDataLoadedListener>(relaxUnitFun = true)
    val listener2 = mockk<OnSourceDataLoadedListener>(relaxUnitFun = true)
    nativeObserver.addOnSourceDataLoadedListener(listener)
    nativeObserver.addOnSourceDataLoadedListener(listener2)
    assertEquals(2, nativeObserver.onSourceDataLoadedListeners.size)

    nativeObserver.removeOnSourceDataLoadedListener(listener)
    assertEquals(1, nativeObserver.onSourceDataLoadedListeners.size)

    nativeObserver.removeOnSourceDataLoadedListener(listener2)
    assertEquals(0, nativeObserver.onSourceDataLoadedListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.contains(MapEvent.SOURCE_DATA_LOADED))
    verifyNo { listener.onSourceDataLoaded(any()) }
    verifyNo { listener2.onSourceDataLoaded(any()) }
  }

  @Test
  fun addOnSourceRemovedListener() {
    val listener = mockk<OnSourceRemovedListener>(relaxUnitFun = true)
    nativeObserver.addOnSourceRemovedListener(listener)
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.SOURCE_REMOVED))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.SOURCE_REMOVED]?.size)

    val listener2 = mockk<OnSourceRemovedListener>(relaxUnitFun = true)
    nativeObserver.addOnSourceRemovedListener(listener2)
    assertEquals(2, nativeObserver.onSourceRemovedListeners.size)
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.SOURCE_REMOVED]?.size)
  }

  @Test
  fun removeOnSourceRemovedListener() {
    val listener = mockk<OnSourceRemovedListener>(relaxUnitFun = true)
    val listener2 = mockk<OnSourceRemovedListener>(relaxUnitFun = true)
    nativeObserver.addOnSourceRemovedListener(listener)
    nativeObserver.addOnSourceRemovedListener(listener2)
    assertEquals(2, nativeObserver.onSourceRemovedListeners.size)

    nativeObserver.removeOnSourceRemovedListener(listener)
    assertEquals(1, nativeObserver.onSourceRemovedListeners.size)

    nativeObserver.removeOnSourceRemovedListener(listener2)
    assertEquals(0, nativeObserver.onSourceRemovedListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.contains(MapEvent.SOURCE_REMOVED))
    verifyNo { listener.onSourceRemoved(any()) }
    verifyNo { listener2.onSourceRemoved(any()) }
  }

  // Style events
  @Test
  fun addOnStyleLoadedListener() {
    val listener = mockk<OnStyleLoadedListener>(relaxUnitFun = true)
    nativeObserver.addOnStyleLoadedListener(listener)
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.STYLE_LOADED))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.STYLE_LOADED]?.size)

    val listener2 = mockk<OnStyleLoadedListener>(relaxUnitFun = true)
    nativeObserver.addOnStyleLoadedListener(listener2)
    assertEquals(2, nativeObserver.onStyleLoadedListeners.size)
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.STYLE_LOADED]?.size)
  }

  @Test
  fun removeOnStyleLoadedListener() {
    val listener = mockk<OnStyleLoadedListener>(relaxUnitFun = true)
    val listener2 = mockk<OnStyleLoadedListener>(relaxUnitFun = true)
    nativeObserver.addOnStyleLoadedListener(listener)
    nativeObserver.addOnStyleLoadedListener(listener2)
    assertEquals(2, nativeObserver.onStyleLoadedListeners.size)

    nativeObserver.removeOnStyleLoadedListener(listener)
    assertEquals(1, nativeObserver.onStyleLoadedListeners.size)

    nativeObserver.removeOnStyleLoadedListener(listener2)
    assertEquals(0, nativeObserver.onStyleLoadedListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.contains(MapEvent.STYLE_LOADED))
    verifyNo { listener.onStyleLoaded(any()) }
    verifyNo { listener2.onStyleLoaded(any()) }
  }

  @Test
  fun addOnStyleImageMissingListener() {
    val listener = mockk<OnStyleImageMissingListener>(relaxUnitFun = true)
    nativeObserver.addOnStyleImageMissingListener(listener)
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.STYLE_IMAGE_MISSING))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.STYLE_IMAGE_MISSING]?.size)

    val listener2 = mockk<OnStyleImageMissingListener>(relaxUnitFun = true)
    nativeObserver.addOnStyleImageMissingListener(listener2)
    assertEquals(2, nativeObserver.onStyleImageMissingListeners.size)
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.STYLE_IMAGE_MISSING]?.size)
  }

  @Test
  fun removeOnStyleImageMissingListener() {
    val listener = mockk<OnStyleImageMissingListener>(relaxUnitFun = true)
    val listener2 = mockk<OnStyleImageMissingListener>(relaxUnitFun = true)
    nativeObserver.addOnStyleImageMissingListener(listener)
    nativeObserver.addOnStyleImageMissingListener(listener2)
    assertEquals(2, nativeObserver.onStyleImageMissingListeners.size)

    nativeObserver.removeOnStyleImageMissingListener(listener)
    assertEquals(1, nativeObserver.onStyleImageMissingListeners.size)

    nativeObserver.removeOnStyleImageMissingListener(listener2)
    assertEquals(0, nativeObserver.onStyleImageMissingListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.contains(MapEvent.STYLE_IMAGE_MISSING))
    verifyNo { listener.onStyleImageMissing(any()) }
    verifyNo { listener2.onStyleImageMissing(any()) }
  }

  @Test
  fun addOnStyleImageUnusedListener() {
    val listener = mockk<OnStyleImageUnusedListener>(relaxUnitFun = true)
    nativeObserver.addOnStyleImageUnusedListener(listener)
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.STYLE_IMAGE_REMOVE_UNUSED))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.STYLE_IMAGE_REMOVE_UNUSED]?.size)

    val listener2 = mockk<OnStyleImageUnusedListener>(relaxUnitFun = true)
    nativeObserver.addOnStyleImageUnusedListener(listener2)
    assertEquals(2, nativeObserver.onStyleImageUnusedListeners.size)
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.STYLE_IMAGE_REMOVE_UNUSED]?.size)
  }

  @Test
  fun removeOnStyleImageUnusedListener() {
    val listener = mockk<OnStyleImageUnusedListener>(relaxUnitFun = true)
    val listener2 = mockk<OnStyleImageUnusedListener>(relaxUnitFun = true)
    nativeObserver.addOnStyleImageUnusedListener(listener)
    nativeObserver.addOnStyleImageUnusedListener(listener2)
    assertEquals(2, nativeObserver.onStyleImageUnusedListeners.size)

    nativeObserver.removeOnStyleImageUnusedListener(listener)
    assertEquals(1, nativeObserver.onStyleImageUnusedListeners.size)

    nativeObserver.removeOnStyleImageUnusedListener(listener2)
    assertEquals(0, nativeObserver.onStyleImageUnusedListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.contains(MapEvent.STYLE_IMAGE_REMOVE_UNUSED))
    verifyNo { listener.onStyleImageUnused(any()) }
    verifyNo { listener2.onStyleImageUnused(any()) }
  }

  @Test
  fun addOnStyleDataLoadedListener() {
    val listener = mockk<OnStyleDataLoadedListener>(relaxUnitFun = true)
    nativeObserver.addOnStyleDataLoadedListener(listener)
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.STYLE_DATA_LOADED))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.STYLE_DATA_LOADED]?.size)

    val listener2 = mockk<OnStyleDataLoadedListener>(relaxUnitFun = true)
    nativeObserver.addOnStyleDataLoadedListener(listener2)
    assertEquals(2, nativeObserver.onStyleDataLoadedListeners.size)
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.STYLE_DATA_LOADED]?.size)
  }

  @Test
  fun removeOnStyleDataLoadedListener() {
    val listener = mockk<OnStyleDataLoadedListener>(relaxUnitFun = true)
    val listener2 = mockk<OnStyleDataLoadedListener>(relaxUnitFun = true)
    nativeObserver.addOnStyleDataLoadedListener(listener)
    nativeObserver.addOnStyleDataLoadedListener(listener2)
    assertEquals(2, nativeObserver.onStyleDataLoadedListeners.size)

    nativeObserver.removeOnStyleDataLoadedListener(listener)
    assertEquals(1, nativeObserver.onStyleDataLoadedListeners.size)

    nativeObserver.removeOnStyleDataLoadedListener(listener2)
    assertEquals(0, nativeObserver.onStyleDataLoadedListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.contains(MapEvent.STYLE_DATA_LOADED))
    verifyNo { listener.onStyleDataLoaded(any()) }
    verifyNo { listener2.onStyleDataLoaded(any()) }
  }

  // Test for subscribe methods.
  @Test
  fun subscribeCameraChange() {
    val listener = mockk<CameraChangedCallback>(relaxUnitFun = true)
    nativeObserver.subscribeCameraChanged(listener)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.CAMERA_CHANGED))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.CAMERA_CHANGED]?.size)

    val listener2 = mockk<CameraChangedCallback>(relaxUnitFun = true)
    nativeObserver.subscribeCameraChanged(listener2)
    verify { observableInterface.subscribe(listener) }
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.CAMERA_CHANGED]?.size)
  }

  @Test
  fun subscribeMapIdleListener() {
    val listener = mockk<MapIdleCallback>(relaxUnitFun = true)
    nativeObserver.subscribeMapIdle(listener)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.MAP_IDLE))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.MAP_IDLE]?.size)

    val listener2 = mockk<MapIdleCallback>(relaxUnitFun = true)
    nativeObserver.subscribeMapIdle(listener2)
    verify { observableInterface.subscribe(listener) }
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.MAP_IDLE]?.size)
  }

  @Test
  fun subscribeMapLoadErrorListener() {
    val listener = mockk<MapLoadingErrorCallback>(relaxUnitFun = true)
    nativeObserver.subscribeMapLoadingError(listener)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.MAP_LOADING_ERROR))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.MAP_LOADING_ERROR]?.size)

    val listener2 = mockk<MapLoadingErrorCallback>(relaxUnitFun = true)
    nativeObserver.subscribeMapLoadingError(listener2)
    verify { observableInterface.subscribe(listener) }
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.MAP_LOADING_ERROR]?.size)
  }

  @Test
  fun subscribeMapLoadedListener() {
    val listener = mockk<MapLoadedCallback>(relaxUnitFun = true)
    nativeObserver.subscribeMapLoaded(listener)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.MAP_LOADED))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.MAP_LOADED]?.size)

    val listener2 = mockk<MapLoadedCallback>(relaxUnitFun = true)
    nativeObserver.subscribeMapLoaded(listener2)
    verify { observableInterface.subscribe(listener) }
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.MAP_LOADED]?.size)
  }

  // Render frame events
  @Test
  fun subscribeRenderFrameFinishedListener() {
    val listener = mockk<RenderFrameFinishedCallback>(relaxUnitFun = true)
    nativeObserver.subscribeRenderFrameFinished(listener)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.RENDER_FRAME_FINISHED))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.RENDER_FRAME_FINISHED]?.size)

    val listener2 = mockk<RenderFrameFinishedCallback>(relaxUnitFun = true)
    nativeObserver.subscribeRenderFrameFinished(listener2)
    verify { observableInterface.subscribe(listener) }
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.RENDER_FRAME_FINISHED]?.size)
  }

  @Test
  fun subscribeRenderFrameStartedListener() {
    val listener = mockk<RenderFrameStartedCallback>(relaxUnitFun = true)
    nativeObserver.subscribeRenderFrameStarted(listener)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.RENDER_FRAME_STARTED))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.RENDER_FRAME_STARTED]?.size)

    val listener2 = mockk<RenderFrameStartedCallback>(relaxUnitFun = true)
    nativeObserver.subscribeRenderFrameStarted(listener2)
    verify { observableInterface.subscribe(listener) }
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.RENDER_FRAME_STARTED]?.size)
  }

  // Source events
  @Test
  fun subscribeSourceAddedListener() {
    val listener = mockk<SourceAddedCallback>(relaxUnitFun = true)
    nativeObserver.subscribeSourceAdded(listener)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.SOURCE_ADDED))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.SOURCE_ADDED]?.size)

    val listener2 = mockk<SourceAddedCallback>(relaxUnitFun = true)
    nativeObserver.subscribeSourceAdded(listener2)
    verify { observableInterface.subscribe(listener) }
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.SOURCE_ADDED]?.size)
  }

  @Test
  fun subscribeSourceDataLoadedListener() {
    val listener = mockk<SourceDataLoadedCallback>(relaxUnitFun = true)
    nativeObserver.subscribeSourceDataLoaded(listener)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.SOURCE_DATA_LOADED))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.SOURCE_DATA_LOADED]?.size)

    val listener2 = mockk<SourceDataLoadedCallback>(relaxUnitFun = true)
    nativeObserver.subscribeSourceDataLoaded(listener2)
    verify { observableInterface.subscribe(listener) }
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.SOURCE_DATA_LOADED]?.size)
  }

  @Test
  fun subscribeSourceRemovedListener() {
    val listener = mockk<SourceRemovedCallback>(relaxUnitFun = true)
    nativeObserver.subscribeSourceRemoved(listener)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.SOURCE_REMOVED))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.SOURCE_REMOVED]?.size)

    val listener2 = mockk<SourceRemovedCallback>(relaxUnitFun = true)
    nativeObserver.subscribeSourceRemoved(listener2)
    verify { observableInterface.subscribe(listener) }
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.SOURCE_REMOVED]?.size)
  }

  // Style events
  @Test
  fun subscribeStyleLoadedListener() {
    val listener = mockk<StyleLoadedCallback>(relaxUnitFun = true)
    nativeObserver.subscribeStyleLoaded(listener)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.STYLE_LOADED))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.STYLE_LOADED]?.size)

    val listener2 = mockk<StyleLoadedCallback>(relaxUnitFun = true)
    nativeObserver.subscribeStyleLoaded(listener2)
    verify { observableInterface.subscribe(listener) }
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.STYLE_LOADED]?.size)
  }

  @Test
  fun subscribeStyleImageMissingListener() {
    val listener = mockk<StyleImageMissingCallback>(relaxUnitFun = true)
    nativeObserver.subscribeStyleImageMissing(listener)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.STYLE_IMAGE_MISSING))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.STYLE_IMAGE_MISSING]?.size)

    val listener2 = mockk<StyleImageMissingCallback>(relaxUnitFun = true)
    nativeObserver.subscribeStyleImageMissing(listener2)
    verify { observableInterface.subscribe(listener) }
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.STYLE_IMAGE_MISSING]?.size)
  }

  @Test
  fun subscribeStyleImageUnusedListener() {
    val listener = mockk<StyleImageRemoveUnusedCallback>(relaxUnitFun = true)
    nativeObserver.subscribeStyleImageUnused(listener)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.STYLE_IMAGE_REMOVE_UNUSED))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.STYLE_IMAGE_REMOVE_UNUSED]?.size)

    val listener2 = mockk<StyleImageRemoveUnusedCallback>(relaxUnitFun = true)
    nativeObserver.subscribeStyleImageUnused(listener2)
    verify { observableInterface.subscribe(listener) }
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.STYLE_IMAGE_REMOVE_UNUSED]?.size)
  }

  @Test
  fun subscribeStyleDataLoaded() {
    val listener = mockk<StyleDataLoadedCallback>(relaxUnitFun = true)
    nativeObserver.subscribeStyleDataLoaded(listener)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.STYLE_DATA_LOADED))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.STYLE_DATA_LOADED]?.size)

    val listener2 = mockk<StyleDataLoadedCallback>(relaxUnitFun = true)
    nativeObserver.subscribeStyleDataLoaded(listener2)
    verify { observableInterface.subscribe(listener) }
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.STYLE_DATA_LOADED]?.size)
  }

  @Test
  fun subscribeResourceRequest() {
    val listener = mockk<ResourceRequestCallback>(relaxUnitFun = true)
    nativeObserver.subscribeResourceRequest(listener)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.RESOURCE_REQUEST))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.RESOURCE_REQUEST]?.size)

    val listener2 = mockk<ResourceRequestCallback>(relaxUnitFun = true)
    nativeObserver.subscribeResourceRequest(listener2)
    verify { observableInterface.subscribe(listener) }
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.RESOURCE_REQUEST]?.size)
  }

  @Test
  fun subscribeGenericEvent() {
    val event1 = "Event1"
    val event2 = "Event2"
    val listener = mockk<GenericEventCallback>(relaxUnitFun = true)
    nativeObserver.subscribeGenericEvent(event1, listener)
    verify { observableInterface.subscribe(event1, listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.GENERIC_EVENT))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.GENERIC_EVENT]?.size)

    val listener2 = mockk<GenericEventCallback>(relaxUnitFun = true)
    nativeObserver.subscribeGenericEvent(event2, listener2)
    verify { observableInterface.subscribe(event2, listener2) }
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.GENERIC_EVENT]?.size)
  }

  @Test
  fun clearListeners() {
    nativeObserver.onCameraChangeListeners.add(mockk(relaxed = true))

    nativeObserver.onMapIdleListeners.add(mockk(relaxed = true))
    nativeObserver.onMapLoadErrorListeners.add(mockk(relaxed = true))
    nativeObserver.onMapLoadedListeners.add(mockk(relaxed = true))

    nativeObserver.onRenderFrameFinishedListeners.add(mockk(relaxed = true))
    nativeObserver.onRenderFrameStartedListeners.add(mockk(relaxed = true))

    nativeObserver.onSourceAddedListeners.add(mockk(relaxed = true))
    nativeObserver.onSourceDataLoadedListeners.add(mockk(relaxed = true))
    nativeObserver.onSourceRemovedListeners.add(mockk(relaxed = true))

    nativeObserver.onStyleLoadedListeners.add(mockk(relaxed = true))
    nativeObserver.onStyleImageMissingListeners.add(mockk(relaxed = true))
    nativeObserver.onStyleImageUnusedListeners.add(mockk(relaxed = true))
    nativeObserver.onStyleDataLoadedListeners.add(mockk(relaxed = true))

    nativeObserver.onDestroy()
    assertTrue(nativeObserver.cancelableEventsMap.isEmpty())
    assertTrue(nativeObserver.onCameraChangeListeners.isEmpty())

    assertTrue(nativeObserver.onMapIdleListeners.isEmpty())
    assertTrue(nativeObserver.onMapLoadErrorListeners.isEmpty())
    assertTrue(nativeObserver.onMapLoadedListeners.isEmpty())

    assertTrue(nativeObserver.onRenderFrameFinishedListeners.isEmpty())
    assertTrue(nativeObserver.onRenderFrameStartedListeners.isEmpty())

    assertTrue(nativeObserver.onSourceAddedListeners.isEmpty())
    assertTrue(nativeObserver.onSourceDataLoadedListeners.isEmpty())
    assertTrue(nativeObserver.onSourceRemovedListeners.isEmpty())

    assertTrue(nativeObserver.onStyleLoadedListeners.isEmpty())
    assertTrue(nativeObserver.onStyleImageMissingListeners.isEmpty())
    assertTrue(nativeObserver.onStyleImageUnusedListeners.isEmpty())
    assertTrue(nativeObserver.onStyleDataLoadedListeners.isEmpty())
  }

  @Test
  fun resubscribesStyleLoadedEvents() {
    val listener1 = mockk<StyleLoadedCallback>()
    val listener2 = mockk<StyleDataLoadedCallback>()

    nativeObserver.subscribeStyleLoaded(listener1)
    nativeObserver.subscribeStyleDataLoaded(listener2)
    nativeObserver.resubscribeStyleLoadListeners(listener1, listener2)

    verify(exactly = 2) {
      observableInterface.subscribe(listener1)
      observableInterface.subscribe(listener2)
    }
  }
}
package com.mapbox.maps

import com.mapbox.common.Cancelable
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
    val listener = mockk<CameraChangedCallback>(relaxUnitFun = true)
    nativeObserver.addOnCameraChangeListener(listener)
    assertEquals(1, nativeObserver.onCameraChangeListeners.size)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.CAMERA_CHANGED))
    assertEquals(1, nativeObserver.cancelableEventsMap[MapEvent.CAMERA_CHANGED]?.size)

    val listener2 = mockk<CameraChangedCallback>(relaxUnitFun = true)
    nativeObserver.addOnCameraChangeListener(listener2)
    assertEquals(2, nativeObserver.onCameraChangeListeners.size)
    verify { observableInterface.subscribe(listener2) }
    assertEquals(2, nativeObserver.cancelableEventsMap[MapEvent.CAMERA_CHANGED]?.size)
  }

  @Test
  fun removeOnCameraChangeListener() {
    val listener = mockk<CameraChangedCallback>(relaxUnitFun = true)
    val listener2 = mockk<CameraChangedCallback>(relaxUnitFun = true)
    every { observableInterface.subscribe(listener) } answers { cancelable }
    every { observableInterface.subscribe(listener2) } answers { cancelable }
    nativeObserver.addOnCameraChangeListener(listener)
    nativeObserver.addOnCameraChangeListener(listener2)
    assertEquals(2, nativeObserver.onCameraChangeListeners.size)
    nativeObserver.removeOnCameraChangeListener(listener)
    assertEquals(1, nativeObserver.onCameraChangeListeners.size)
    nativeObserver.removeOnCameraChangeListener(listener2)
    assertEquals(0, nativeObserver.onCameraChangeListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.containsKey(MapEvent.CAMERA_CHANGED))
    verifyNo { listener.run(any()) }
    verifyNo { listener2.run(any()) }
  }

  // Map events
  @Test
  fun addOnMapIdleListener() {
    val listener = mockk<MapIdleCallback>(relaxUnitFun = true)
    nativeObserver.addOnMapIdleListener(listener)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.MAP_IDLE))

    val listener2 = mockk<MapIdleCallback>(relaxUnitFun = true)
    nativeObserver.addOnMapIdleListener(listener2)
    assertEquals(2, nativeObserver.onMapIdleListeners.size)
    verify { observableInterface.subscribe(listener2) }
  }

  @Test
  fun removeOnMapIdleListener() {
    val listener = mockk<MapIdleCallback>(relaxUnitFun = true)
    val listener2 = mockk<MapIdleCallback>(relaxUnitFun = true)
    nativeObserver.addOnMapIdleListener(listener)
    nativeObserver.addOnMapIdleListener(listener2)
    assertEquals(2, nativeObserver.onMapIdleListeners.size)

    nativeObserver.removeOnMapIdleListener(listener)
    assertEquals(1, nativeObserver.onMapIdleListeners.size)

    nativeObserver.removeOnMapIdleListener(listener2)
    assertEquals(0, nativeObserver.onMapIdleListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.contains(MapEvent.MAP_IDLE))
    verifyNo { listener.run(any()) }
    verifyNo { listener2.run(any()) }
  }

  @Test
  fun addOnMapLoadErrorListener() {
    val listener = mockk<MapLoadingErrorCallback>(relaxUnitFun = true)
    nativeObserver.addOnMapLoadErrorListener(listener)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.MAP_LOADING_ERROR))

    val listener2 = mockk<MapLoadingErrorCallback>(relaxUnitFun = true)
    nativeObserver.addOnMapLoadErrorListener(listener2)
    assertEquals(2, nativeObserver.onMapLoadErrorListeners.size)
    verify { observableInterface.subscribe(listener2) }
  }

  @Test
  fun removeOnMapLoadErrorListener() {
    val listener = mockk<MapLoadingErrorCallback>(relaxUnitFun = true)
    val listener2 = mockk<MapLoadingErrorCallback>(relaxUnitFun = true)
    nativeObserver.addOnMapLoadErrorListener(listener)
    nativeObserver.addOnMapLoadErrorListener(listener2)
    assertEquals(2, nativeObserver.onMapLoadErrorListeners.size)

    nativeObserver.removeOnMapLoadErrorListener(listener)
    assertEquals(1, nativeObserver.onMapLoadErrorListeners.size)

    nativeObserver.removeOnMapLoadErrorListener(listener2)
    assertEquals(0, nativeObserver.onMapLoadErrorListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.contains(MapEvent.MAP_LOADING_ERROR))
    verifyNo { listener.run(any()) }
    verifyNo { listener2.run(any()) }
  }

  @Test
  fun addOnMapLoadedListener() {
    val listener = mockk<MapLoadedCallback>(relaxUnitFun = true)
    nativeObserver.addOnMapLoadedListener(listener)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.MAP_LOADED))

    val listener2 = mockk<MapLoadedCallback>(relaxUnitFun = true)
    nativeObserver.addOnMapLoadedListener(listener2)
    assertEquals(2, nativeObserver.onMapLoadedListeners.size)
    verify { observableInterface.subscribe(listener2) }
  }

  @Test
  fun removeOnMapLoadedListener() {
    val listener = mockk<MapLoadedCallback>(relaxUnitFun = true)
    val listener2 = mockk<MapLoadedCallback>(relaxUnitFun = true)
    nativeObserver.addOnMapLoadedListener(listener)
    nativeObserver.addOnMapLoadedListener(listener2)
    assertEquals(2, nativeObserver.onMapLoadedListeners.size)

    nativeObserver.removeOnMapLoadedListener(listener)
    assertEquals(1, nativeObserver.onMapLoadedListeners.size)

    nativeObserver.removeOnMapLoadedListener(listener2)
    assertEquals(0, nativeObserver.onMapLoadedListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.contains(MapEvent.MAP_LOADED))
    verifyNo { listener.run(any()) }
    verifyNo { listener2.run(any()) }
  }

  // Render frame events
  @Test
  fun addOnRenderFrameFinishedListener() {
    val listener = mockk<RenderFrameFinishedCallback>(relaxUnitFun = true)
    nativeObserver.addOnRenderFrameFinishedListener(listener)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.RENDER_FRAME_FINISHED))

    val listener2 = mockk<RenderFrameFinishedCallback>(relaxUnitFun = true)
    nativeObserver.addOnRenderFrameFinishedListener(listener2)
    assertEquals(2, nativeObserver.onRenderFrameFinishedListeners.size)
    verify { observableInterface.subscribe(listener2) }
  }

  @Test
  fun removeOnRenderFrameFinishedListener() {
    val listener = mockk<RenderFrameFinishedCallback>(relaxUnitFun = true)
    val listener2 = mockk<RenderFrameFinishedCallback>(relaxUnitFun = true)
    nativeObserver.addOnRenderFrameFinishedListener(listener)
    nativeObserver.addOnRenderFrameFinishedListener(listener2)
    assertEquals(2, nativeObserver.onRenderFrameFinishedListeners.size)

    nativeObserver.removeOnRenderFrameFinishedListener(listener)
    assertEquals(1, nativeObserver.onRenderFrameFinishedListeners.size)

    nativeObserver.removeOnRenderFrameFinishedListener(listener2)
    assertEquals(0, nativeObserver.onRenderFrameFinishedListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.contains(MapEvent.RENDER_FRAME_FINISHED))
    verifyNo { listener.run(any()) }
    verifyNo { listener2.run(any()) }
  }

  @Test
  fun addOnRenderFrameStartedListener() {
    val listener = mockk<RenderFrameStartedCallback>(relaxUnitFun = true)
    nativeObserver.addOnRenderFrameStartedListener(listener)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.RENDER_FRAME_STARTED))

    val listener2 = mockk<RenderFrameStartedCallback>(relaxUnitFun = true)
    nativeObserver.addOnRenderFrameStartedListener(listener2)
    assertEquals(2, nativeObserver.onRenderFrameStartedListeners.size)
    verify { observableInterface.subscribe(listener2) }
  }

  @Test
  fun removeOnRenderFrameStartedListener() {
    val listener = mockk<RenderFrameStartedCallback>(relaxUnitFun = true)
    val listener2 = mockk<RenderFrameStartedCallback>(relaxUnitFun = true)
    nativeObserver.addOnRenderFrameStartedListener(listener)
    nativeObserver.addOnRenderFrameStartedListener(listener2)
    assertEquals(2, nativeObserver.onRenderFrameStartedListeners.size)

    nativeObserver.removeOnRenderFrameStartedListener(listener)
    assertEquals(1, nativeObserver.onRenderFrameStartedListeners.size)

    nativeObserver.removeOnRenderFrameStartedListener(listener2)
    assertEquals(0, nativeObserver.onRenderFrameStartedListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.contains(MapEvent.RENDER_FRAME_STARTED))
    verifyNo { listener.run(any()) }
    verifyNo { listener2.run(any()) }
  }

  // Source events
  @Test
  fun addOnSourceAddedListener() {
    val listener = mockk<SourceAddedCallback>(relaxUnitFun = true)
    nativeObserver.addOnSourceAddedListener(listener)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.SOURCE_ADDED))

    val listener2 = mockk<SourceAddedCallback>(relaxUnitFun = true)
    nativeObserver.addOnSourceAddedListener(listener2)
    assertEquals(2, nativeObserver.onSourceAddedListeners.size)
    verify { observableInterface.subscribe(listener2) }
  }

  @Test
  fun removeOnSourceAddedListener() {
    val listener = mockk<SourceAddedCallback>(relaxUnitFun = true)
    val listener2 = mockk<SourceAddedCallback>(relaxUnitFun = true)
    nativeObserver.addOnSourceAddedListener(listener)
    nativeObserver.addOnSourceAddedListener(listener2)
    assertEquals(2, nativeObserver.onSourceAddedListeners.size)

    nativeObserver.removeOnSourceAddedListener(listener)
    assertEquals(1, nativeObserver.onSourceAddedListeners.size)

    nativeObserver.removeOnSourceAddedListener(listener2)
    assertEquals(0, nativeObserver.onSourceAddedListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.contains(MapEvent.SOURCE_ADDED))
    verifyNo { listener.run(any()) }
    verifyNo { listener2.run(any()) }
  }

  @Test
  fun addOnSourceDataLoadedListener() {
    val listener = mockk<SourceDataLoadedCallback>(relaxUnitFun = true)
    nativeObserver.addOnSourceDataLoadedListener(listener)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.SOURCE_DATA_LOADED))

    val listener2 = mockk<SourceDataLoadedCallback>(relaxUnitFun = true)
    nativeObserver.addOnSourceDataLoadedListener(listener2)
    assertEquals(2, nativeObserver.onSourceDataLoadedListeners.size)
    verify { observableInterface.subscribe(listener2) }
  }

  @Test
  fun removeOnSourceDataLoadedListener() {
    val listener = mockk<SourceDataLoadedCallback>(relaxUnitFun = true)
    val listener2 = mockk<SourceDataLoadedCallback>(relaxUnitFun = true)
    nativeObserver.addOnSourceDataLoadedListener(listener)
    nativeObserver.addOnSourceDataLoadedListener(listener2)
    assertEquals(2, nativeObserver.onSourceDataLoadedListeners.size)

    nativeObserver.removeOnSourceDataLoadedListener(listener)
    assertEquals(1, nativeObserver.onSourceDataLoadedListeners.size)

    nativeObserver.removeOnSourceDataLoadedListener(listener2)
    assertEquals(0, nativeObserver.onSourceDataLoadedListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.contains(MapEvent.SOURCE_DATA_LOADED))
    verifyNo { listener.run(any()) }
    verifyNo { listener2.run(any()) }
  }

  @Test
  fun addOnSourceRemovedListener() {
    val listener = mockk<SourceRemovedCallback>(relaxUnitFun = true)
    nativeObserver.addOnSourceRemovedListener(listener)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.SOURCE_REMOVED))

    val listener2 = mockk<SourceRemovedCallback>(relaxUnitFun = true)
    nativeObserver.addOnSourceRemovedListener(listener2)
    assertEquals(2, nativeObserver.onSourceRemovedListeners.size)
    verify { observableInterface.subscribe(listener2) }
  }

  @Test
  fun removeOnSourceRemovedListener() {
    val listener = mockk<SourceRemovedCallback>(relaxUnitFun = true)
    val listener2 = mockk<SourceRemovedCallback>(relaxUnitFun = true)
    nativeObserver.addOnSourceRemovedListener(listener)
    nativeObserver.addOnSourceRemovedListener(listener2)
    assertEquals(2, nativeObserver.onSourceRemovedListeners.size)

    nativeObserver.removeOnSourceRemovedListener(listener)
    assertEquals(1, nativeObserver.onSourceRemovedListeners.size)

    nativeObserver.removeOnSourceRemovedListener(listener2)
    assertEquals(0, nativeObserver.onSourceRemovedListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.contains(MapEvent.SOURCE_REMOVED))
    verifyNo { listener.run(any()) }
    verifyNo { listener2.run(any()) }
  }

  // Style events
  @Test
  fun addOnStyleLoadedListener() {
    val listener = mockk<StyleLoadedCallback>(relaxUnitFun = true)
    nativeObserver.addOnStyleLoadedListener(listener)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.STYLE_LOADED))

    val listener2 = mockk<StyleLoadedCallback>(relaxUnitFun = true)
    nativeObserver.addOnStyleLoadedListener(listener2)
    assertEquals(2, nativeObserver.onStyleLoadedListeners.size)
    verify { observableInterface.subscribe(listener2) }
  }

  @Test
  fun removeOnStyleLoadedListener() {
    val listener = mockk<StyleLoadedCallback>(relaxUnitFun = true)
    val listener2 = mockk<StyleLoadedCallback>(relaxUnitFun = true)
    nativeObserver.addOnStyleLoadedListener(listener)
    nativeObserver.addOnStyleLoadedListener(listener2)
    assertEquals(2, nativeObserver.onStyleLoadedListeners.size)

    nativeObserver.removeOnStyleLoadedListener(listener)
    assertEquals(1, nativeObserver.onStyleLoadedListeners.size)

    nativeObserver.removeOnStyleLoadedListener(listener2)
    assertEquals(0, nativeObserver.onStyleLoadedListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.contains(MapEvent.STYLE_LOADED))
    verifyNo { listener.run(any()) }
    verifyNo { listener2.run(any()) }
  }

  @Test
  fun addOnStyleImageMissingListener() {
    val listener = mockk<StyleImageMissingCallback>(relaxUnitFun = true)
    nativeObserver.addOnStyleImageMissingListener(listener)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.STYLE_IMAGE_MISSING))

    val listener2 = mockk<StyleImageMissingCallback>(relaxUnitFun = true)
    nativeObserver.addOnStyleImageMissingListener(listener2)
    assertEquals(2, nativeObserver.onStyleImageMissingListeners.size)
    verify { observableInterface.subscribe(listener2) }
  }

  @Test
  fun removeOnStyleImageMissingListener() {
    val listener = mockk<StyleImageMissingCallback>(relaxUnitFun = true)
    val listener2 = mockk<StyleImageMissingCallback>(relaxUnitFun = true)
    nativeObserver.addOnStyleImageMissingListener(listener)
    nativeObserver.addOnStyleImageMissingListener(listener2)
    assertEquals(2, nativeObserver.onStyleImageMissingListeners.size)

    nativeObserver.removeOnStyleImageMissingListener(listener)
    assertEquals(1, nativeObserver.onStyleImageMissingListeners.size)

    nativeObserver.removeOnStyleImageMissingListener(listener2)
    assertEquals(0, nativeObserver.onStyleImageMissingListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.contains(MapEvent.STYLE_IMAGE_MISSING))
    verifyNo { listener.run(any()) }
    verifyNo { listener2.run(any()) }
  }

  @Test
  fun addOnStyleImageUnusedListener() {
    val listener = mockk<StyleImageRemoveUnusedCallback>(relaxUnitFun = true)
    nativeObserver.addOnStyleImageUnusedListener(listener)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.STYLE_IMAGE_REMOVE_UNUSED))

    val listener2 = mockk<StyleImageRemoveUnusedCallback>(relaxUnitFun = true)
    nativeObserver.addOnStyleImageUnusedListener(listener2)
    assertEquals(2, nativeObserver.onStyleImageUnusedListeners.size)
    verify { observableInterface.subscribe(listener2) }
  }

  @Test
  fun removeOnStyleImageUnusedListener() {
    val listener = mockk<StyleImageRemoveUnusedCallback>(relaxUnitFun = true)
    val listener2 = mockk<StyleImageRemoveUnusedCallback>(relaxUnitFun = true)
    nativeObserver.addOnStyleImageUnusedListener(listener)
    nativeObserver.addOnStyleImageUnusedListener(listener2)
    assertEquals(2, nativeObserver.onStyleImageUnusedListeners.size)

    nativeObserver.removeOnStyleImageUnusedListener(listener)
    assertEquals(1, nativeObserver.onStyleImageUnusedListeners.size)

    nativeObserver.removeOnStyleImageUnusedListener(listener2)
    assertEquals(0, nativeObserver.onStyleImageUnusedListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.contains(MapEvent.STYLE_IMAGE_REMOVE_UNUSED))
    verifyNo { listener.run(any()) }
    verifyNo { listener2.run(any()) }
  }

  @Test
  fun addOnStyleDataLoadedListener() {
    val listener = mockk<StyleDataLoadedCallback>(relaxUnitFun = true)
    nativeObserver.addOnStyleDataLoadedListener(listener)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.STYLE_DATA_LOADED))

    val listener2 = mockk<StyleDataLoadedCallback>(relaxUnitFun = true)
    nativeObserver.addOnStyleDataLoadedListener(listener2)
    assertEquals(2, nativeObserver.onStyleDataLoadedListeners.size)
    verify { observableInterface.subscribe(listener2) }
  }

  @Test
  fun removeOnStyleDataLoadedListener() {
    val listener = mockk<StyleDataLoadedCallback>(relaxUnitFun = true)
    val listener2 = mockk<StyleDataLoadedCallback>(relaxUnitFun = true)
    nativeObserver.addOnStyleDataLoadedListener(listener)
    nativeObserver.addOnStyleDataLoadedListener(listener2)
    assertEquals(2, nativeObserver.onStyleDataLoadedListeners.size)

    nativeObserver.removeOnStyleDataLoadedListener(listener)
    assertEquals(1, nativeObserver.onStyleDataLoadedListeners.size)

    nativeObserver.removeOnStyleDataLoadedListener(listener2)
    assertEquals(0, nativeObserver.onStyleDataLoadedListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.contains(MapEvent.STYLE_DATA_LOADED))
    verifyNo { listener.run(any()) }
    verifyNo { listener2.run(any()) }
  }

  @Test
  fun addOnResourceRequestListener() {
    val listener = mockk<ResourceRequestCallback>(relaxUnitFun = true)
    nativeObserver.addOnResourceRequestListener(listener)
    verify { observableInterface.subscribe(listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.RESOURCE_REQUEST))

    val listener2 = mockk<ResourceRequestCallback>(relaxUnitFun = true)
    nativeObserver.addOnResourceRequestListener(listener2)
    assertEquals(2, nativeObserver.onResourceRequestListeners.size)
    verify { observableInterface.subscribe(listener2) }
  }

  @Test
  fun removeOnResourceRequestListener() {
    val listener = mockk<ResourceRequestCallback>(relaxUnitFun = true)
    val listener2 = mockk<ResourceRequestCallback>(relaxUnitFun = true)
    nativeObserver.addOnResourceRequestListener(listener)
    nativeObserver.addOnResourceRequestListener(listener2)
    assertEquals(2, nativeObserver.onResourceRequestListeners.size)

    nativeObserver.removeOnResourceRequestListener(listener)
    assertEquals(1, nativeObserver.onResourceRequestListeners.size)

    nativeObserver.removeOnResourceRequestListener(listener2)
    assertEquals(0, nativeObserver.onResourceRequestListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.contains(MapEvent.RESOURCE_REQUEST))
    verifyNo { listener.run(any()) }
    verifyNo { listener2.run(any()) }
  }

  @Test
  fun addOnUntypedEventListener() {
    val event1 = "event1"
    val event2 = "event2"
    val listener = mockk<GenericEventCallback>(relaxUnitFun = true)
    nativeObserver.addOnGenericEventListener(event1, listener)
    verify { observableInterface.subscribe(event1, listener) }
    assertTrue(nativeObserver.cancelableEventsMap.containsKey(MapEvent.GENERIC_EVENT))

    val listener2 = mockk<GenericEventCallback>(relaxUnitFun = true)
    nativeObserver.addOnGenericEventListener(event2, listener2)
    assertEquals(2, nativeObserver.onGenericEventListeners.size)
    verify { observableInterface.subscribe(event2, listener2) }
  }

  @Test
  fun removeOnGenericEventListener() {
    val event1 = "event1"
    val event2 = "event2"
    val listener = mockk<GenericEventCallback>(relaxUnitFun = true)
    val listener2 = mockk<GenericEventCallback>(relaxUnitFun = true)
    nativeObserver.addOnGenericEventListener(event1, listener)
    nativeObserver.addOnGenericEventListener(event2, listener2)
    assertEquals(2, nativeObserver.onGenericEventListeners.size)

    nativeObserver.removeOnGenericEventListener(listener)
    assertEquals(1, nativeObserver.onGenericEventListeners.size)
    nativeObserver.removeOnGenericEventListener(listener2)
    assertEquals(0, nativeObserver.onGenericEventListeners.size)
    assertFalse(nativeObserver.cancelableEventsMap.contains(MapEvent.RESOURCE_REQUEST))
    verifyNo { listener.run(any()) }
    verifyNo { listener2.run(any()) }
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

    nativeObserver.addOnStyleLoadedListener(listener1)
    nativeObserver.addOnStyleDataLoadedListener(listener2)
    nativeObserver.resubscribeStyleLoadListeners(listener1, listener2)

    verify(exactly = 2) {
      observableInterface.subscribe(listener1)
      observableInterface.subscribe(listener2)
    }
  }
}
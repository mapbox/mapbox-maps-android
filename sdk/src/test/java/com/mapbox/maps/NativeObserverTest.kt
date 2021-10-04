package com.mapbox.maps

import com.mapbox.bindgen.Value
import com.mapbox.maps.plugin.delegates.listeners.*
import io.mockk.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.lang.ref.WeakReference

@RunWith(RobolectricTestRunner::class)
class NativeObserverTest {

  private val observableInterface = mockk<ObservableInterface>(relaxed = true)
  private lateinit var nativeObserver: NativeObserver

  @Before
  fun setUp() {
    nativeObserver = NativeObserver(WeakReference(observableInterface))
  }

  private fun notifyEvents(type: String, value: Value = Value(hashMapOf(Pair("id", Value(""))))) {
    nativeObserver.notify(Event(type, value))
  }

  @Test
  fun addOnCameraChangeListener() {
    val listener = mockk<OnCameraChangeListener>(relaxUnitFun = true)
    nativeObserver.addOnCameraChangeListener(listener)
    assertEquals(1, nativeObserver.onCameraChangeListeners.size)
    verify(exactly = 1) { observableInterface.subscribe(any(), listOf(MapEvents.CAMERA_CHANGED)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.CAMERA_CHANGED))
    notifyEvents(MapEvents.CAMERA_CHANGED)
    verify { listener.onCameraChanged() }

    val listener2 = mockk<OnCameraChangeListener>(relaxUnitFun = true)
    nativeObserver.addOnCameraChangeListener(listener2)
    assertEquals(2, nativeObserver.onCameraChangeListeners.size)
    verify(exactly = 1) { observableInterface.subscribe(any(), listOf(MapEvents.CAMERA_CHANGED)) }
    notifyEvents(MapEvents.CAMERA_CHANGED)
    verify { listener2.onCameraChanged() }
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
    verify(exactly = 0) { observableInterface.unsubscribe(any(), listOf(MapEvents.CAMERA_CHANGED)) }
    nativeObserver.removeOnCameraChangeListener(listener2)
    assertEquals(0, nativeObserver.onCameraChangeListeners.size)
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.CAMERA_CHANGED))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.CAMERA_CHANGED)) }
    notifyEvents(MapEvents.CAMERA_CHANGED)
    verify(exactly = 0) { listener.onCameraChanged() }
    verify(exactly = 0) { listener2.onCameraChanged() }
  }

  // Map events
  @Test
  fun addOnMapIdleListener() {
    val listener = mockk<OnMapIdleListener>(relaxUnitFun = true)
    nativeObserver.addOnMapIdleListener(listener)
    verify { observableInterface.subscribe(any(), listOf(MapEvents.MAP_IDLE)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.MAP_IDLE))
    notifyEvents(MapEvents.MAP_IDLE)
    verify { listener.onMapIdle() }

    val listener2 = mockk<OnMapIdleListener>(relaxUnitFun = true)
    nativeObserver.addOnMapIdleListener(listener2)
    assertEquals(2, nativeObserver.onMapIdleListeners.size)
    verify(exactly = 1) { observableInterface.subscribe(any(), listOf(MapEvents.MAP_IDLE)) }
    notifyEvents(MapEvents.MAP_IDLE)
    verify { listener2.onMapIdle() }
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
    verify(exactly = 0) { observableInterface.unsubscribe(any(), listOf(MapEvents.MAP_IDLE)) }
    nativeObserver.removeOnMapIdleListener(listener2)
    assertEquals(0, nativeObserver.onMapIdleListeners.size)
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.MAP_IDLE))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.MAP_IDLE)) }
    notifyEvents(MapEvents.MAP_IDLE)
    verify(exactly = 0) { listener.onMapIdle() }
    verify(exactly = 0) { listener2.onMapIdle() }
  }

  @Test
  fun addOnMapLoadErrorListener() {
    val listener = mockk<OnMapLoadErrorListener>(relaxUnitFun = true)
    nativeObserver.addOnMapLoadErrorListener(listener)
    verify { observableInterface.subscribe(any(), listOf(MapEvents.MAP_LOADING_ERROR)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.MAP_LOADING_ERROR))
    notifyEvents(MapEvents.MAP_LOADING_ERROR)
    verify { listener.onMapLoadError(any(), any(), any(), any()) }

    val listener2 = mockk<OnMapLoadErrorListener>(relaxUnitFun = true)
    nativeObserver.addOnMapLoadErrorListener(listener2)
    assertEquals(2, nativeObserver.onMapLoadErrorListeners.size)
    verify(exactly = 1) {
      observableInterface.subscribe(
        any(),
        listOf(MapEvents.MAP_LOADING_ERROR)
      )
    }
    notifyEvents(MapEvents.MAP_LOADING_ERROR)
    verify { listener2.onMapLoadError(any(), any(), any(), any()) }
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
    verify(exactly = 0) {
      observableInterface.unsubscribe(
        any(),
        listOf(MapEvents.MAP_LOADING_ERROR)
      )
    }
    nativeObserver.removeOnMapLoadErrorListener(listener2)
    assertEquals(0, nativeObserver.onMapLoadErrorListeners.size)
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.MAP_LOADING_ERROR))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.MAP_LOADING_ERROR)) }
    notifyEvents(MapEvents.MAP_IDLE)
    verify(exactly = 0) { listener.onMapLoadError(any(), any(), any(), any()) }
    verify(exactly = 0) { listener2.onMapLoadError(any(), any(), any(), any()) }
  }

  @Test
  fun addOnMapLoadedListener() {
    val listener = mockk<OnMapLoadedListener>(relaxUnitFun = true)
    nativeObserver.addOnMapLoadedListener(listener)
    verify { observableInterface.subscribe(any(), listOf(MapEvents.MAP_LOADED)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.MAP_LOADED))
    notifyEvents(MapEvents.MAP_LOADED)
    verify { listener.onMapLoaded() }

    val listener2 = mockk<OnMapLoadedListener>(relaxUnitFun = true)
    nativeObserver.addOnMapLoadedListener(listener2)
    assertEquals(2, nativeObserver.onMapLoadedListeners.size)
    verify(exactly = 1) { observableInterface.subscribe(any(), listOf(MapEvents.MAP_LOADED)) }
    notifyEvents(MapEvents.MAP_LOADED)
    verify { listener2.onMapLoaded() }
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
    verify(exactly = 0) { observableInterface.unsubscribe(any(), listOf(MapEvents.MAP_LOADED)) }
    nativeObserver.removeOnMapLoadedListener(listener2)
    assertEquals(0, nativeObserver.onMapLoadedListeners.size)
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.MAP_LOADED))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.MAP_LOADED)) }
    notifyEvents(MapEvents.MAP_LOADED)
    verify(exactly = 0) { listener.onMapLoaded() }
    verify(exactly = 0) { listener2.onMapLoaded() }
  }

  // Render frame events
  @Test
  fun addOnRenderFrameFinishedListener() {
    val listener = mockk<OnRenderFrameFinishedListener>(relaxUnitFun = true)
    nativeObserver.addOnRenderFrameFinishedListener(listener)
    verify { observableInterface.subscribe(any(), listOf(MapEvents.RENDER_FRAME_FINISHED)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.RENDER_FRAME_FINISHED))
    notifyEvents(MapEvents.RENDER_FRAME_FINISHED)
    verify { listener.onRenderFrameFinished(any(), any(), any()) }

    val listener2 = mockk<OnRenderFrameFinishedListener>(relaxUnitFun = true)
    nativeObserver.addOnRenderFrameFinishedListener(listener2)
    assertEquals(2, nativeObserver.onRenderFrameFinishedListeners.size)
    verify(exactly = 1) {
      observableInterface.subscribe(
        any(),
        listOf(MapEvents.RENDER_FRAME_FINISHED)
      )
    }
    notifyEvents(MapEvents.RENDER_FRAME_FINISHED)
    verify { listener2.onRenderFrameFinished(any(), any(), any()) }
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
    verify(exactly = 0) {
      observableInterface.unsubscribe(
        any(),
        listOf(MapEvents.RENDER_FRAME_FINISHED)
      )
    }
    nativeObserver.removeOnRenderFrameFinishedListener(listener2)
    assertEquals(0, nativeObserver.onRenderFrameFinishedListeners.size)
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.RENDER_FRAME_FINISHED))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.RENDER_FRAME_FINISHED)) }
    notifyEvents(MapEvents.RENDER_FRAME_FINISHED)
    verify(exactly = 0) { listener.onRenderFrameFinished(any(), any(), any()) }
    verify(exactly = 0) { listener2.onRenderFrameFinished(any(), any(), any()) }
  }

  @Test
  fun addOnRenderFrameStartedListener() {
    val listener = mockk<OnRenderFrameStartedListener>(relaxUnitFun = true)
    nativeObserver.addOnRenderFrameStartedListener(listener)
    verify { observableInterface.subscribe(any(), listOf(MapEvents.RENDER_FRAME_STARTED)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.RENDER_FRAME_STARTED))
    notifyEvents(MapEvents.RENDER_FRAME_STARTED)
    verify { listener.onRenderFrameStarted() }

    val listener2 = mockk<OnRenderFrameStartedListener>(relaxUnitFun = true)
    nativeObserver.addOnRenderFrameStartedListener(listener2)
    assertEquals(2, nativeObserver.onRenderFrameStartedListeners.size)
    verify(exactly = 1) {
      observableInterface.subscribe(
        any(),
        listOf(MapEvents.RENDER_FRAME_STARTED)
      )
    }
    notifyEvents(MapEvents.RENDER_FRAME_STARTED)
    verify { listener2.onRenderFrameStarted() }
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
    verify(exactly = 0) {
      observableInterface.unsubscribe(
        any(),
        listOf(MapEvents.RENDER_FRAME_STARTED)
      )
    }
    nativeObserver.removeOnRenderFrameStartedListener(listener2)
    assertEquals(0, nativeObserver.onRenderFrameStartedListeners.size)
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.RENDER_FRAME_STARTED))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.RENDER_FRAME_STARTED)) }
    notifyEvents(MapEvents.RENDER_FRAME_STARTED)
    verify(exactly = 0) { listener.onRenderFrameStarted() }
    verify(exactly = 0) { listener2.onRenderFrameStarted() }
  }

  // Source events
  @Test
  fun addOnSourceAddedListener() {
    val listener = mockk<OnSourceAddedListener>(relaxUnitFun = true)
    nativeObserver.addOnSourceAddedListener(listener)
    verify { observableInterface.subscribe(any(), listOf(MapEvents.SOURCE_ADDED)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.SOURCE_ADDED))
    notifyEvents(MapEvents.SOURCE_ADDED)
    verify { listener.onSourceAdded(any()) }

    val listener2 = mockk<OnSourceAddedListener>(relaxUnitFun = true)
    nativeObserver.addOnSourceAddedListener(listener2)
    assertEquals(2, nativeObserver.onSourceAddedListeners.size)
    verify(exactly = 1) { observableInterface.subscribe(any(), listOf(MapEvents.SOURCE_ADDED)) }
    notifyEvents(MapEvents.SOURCE_ADDED)
    verify { listener2.onSourceAdded(any()) }
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
    verify(exactly = 0) { observableInterface.unsubscribe(any(), listOf(MapEvents.SOURCE_ADDED)) }
    nativeObserver.removeOnSourceAddedListener(listener2)
    assertEquals(0, nativeObserver.onSourceAddedListeners.size)
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.SOURCE_ADDED))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.SOURCE_ADDED)) }
    notifyEvents(MapEvents.SOURCE_ADDED)
    verify(exactly = 0) { listener.onSourceAdded(any()) }
    verify(exactly = 0) { listener2.onSourceAdded(any()) }
  }

  @Test
  fun addOnSourceDataLoadedListener() {
    val listener = mockk<OnSourceDataLoadedListener>(relaxUnitFun = true)
    nativeObserver.addOnSourceDataLoadedListener(listener)
    verify { observableInterface.subscribe(any(), listOf(MapEvents.SOURCE_DATA_LOADED)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.SOURCE_DATA_LOADED))
    notifyEvents(MapEvents.SOURCE_DATA_LOADED)
    verify { listener.onSourceDataLoaded(any(), any(), any(), any()) }

    val listener2 = mockk<OnSourceDataLoadedListener>(relaxUnitFun = true)
    nativeObserver.addOnSourceDataLoadedListener(listener2)
    assertEquals(2, nativeObserver.onSourceDataLoadedListeners.size)
    verify(exactly = 1) {
      observableInterface.subscribe(
        any(),
        listOf(MapEvents.SOURCE_DATA_LOADED)
      )
    }
    notifyEvents(MapEvents.SOURCE_DATA_LOADED)
    verify { listener2.onSourceDataLoaded(any(), any(), any(), any()) }
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
    verify(exactly = 0) {
      observableInterface.unsubscribe(
        any(),
        listOf(MapEvents.SOURCE_DATA_LOADED)
      )
    }
    nativeObserver.removeOnSourceDataLoadedListener(listener2)
    assertEquals(0, nativeObserver.onSourceDataLoadedListeners.size)
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.SOURCE_DATA_LOADED))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.SOURCE_DATA_LOADED)) }
    notifyEvents(MapEvents.SOURCE_ADDED)
    verify(exactly = 0) { listener.onSourceDataLoaded(any(), any(), any(), any()) }
    verify(exactly = 0) { listener2.onSourceDataLoaded(any(), any(), any(), any()) }
  }

  @Test
  fun addOnSourceRemovedListener() {
    val listener = mockk<OnSourceRemovedListener>(relaxUnitFun = true)
    nativeObserver.addOnSourceRemovedListener(listener)
    verify { observableInterface.subscribe(any(), listOf(MapEvents.SOURCE_REMOVED)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.SOURCE_REMOVED))
    notifyEvents(MapEvents.SOURCE_REMOVED)
    verify { listener.onSourceRemoved(any()) }

    val listener2 = mockk<OnSourceRemovedListener>(relaxUnitFun = true)
    nativeObserver.addOnSourceRemovedListener(listener2)
    assertEquals(2, nativeObserver.onSourceRemovedListeners.size)
    verify(exactly = 1) { observableInterface.subscribe(any(), listOf(MapEvents.SOURCE_REMOVED)) }
    notifyEvents(MapEvents.SOURCE_REMOVED)
    verify { listener2.onSourceRemoved(any()) }
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
    verify(exactly = 0) { observableInterface.unsubscribe(any(), listOf(MapEvents.SOURCE_REMOVED)) }
    nativeObserver.removeOnSourceRemovedListener(listener2)
    assertEquals(0, nativeObserver.onSourceRemovedListeners.size)
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.SOURCE_REMOVED))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.SOURCE_REMOVED)) }
    notifyEvents(MapEvents.SOURCE_REMOVED)
    verify(exactly = 0) { listener.onSourceRemoved(any()) }
    verify(exactly = 0) { listener2.onSourceRemoved(any()) }
  }

  // Style events
  @Test
  fun addOnStyleLoadedListener() {
    val listener = mockk<OnStyleLoadedListener>(relaxUnitFun = true)
    nativeObserver.addOnStyleLoadedListener(listener)
    verify { observableInterface.subscribe(any(), listOf(MapEvents.STYLE_LOADED)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.STYLE_LOADED))
    notifyEvents(MapEvents.STYLE_LOADED)
    verify { listener.onStyleLoaded() }

    val listener2 = mockk<OnStyleLoadedListener>(relaxUnitFun = true)
    nativeObserver.addOnStyleLoadedListener(listener2)
    assertEquals(2, nativeObserver.onStyleLoadedListeners.size)
    verify(exactly = 1) { observableInterface.subscribe(any(), listOf(MapEvents.STYLE_LOADED)) }
    notifyEvents(MapEvents.STYLE_LOADED)
    verify { listener2.onStyleLoaded() }
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
    verify(exactly = 0) { observableInterface.unsubscribe(any(), listOf(MapEvents.STYLE_LOADED)) }
    nativeObserver.removeOnStyleLoadedListener(listener2)
    assertEquals(0, nativeObserver.onStyleLoadedListeners.size)
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.STYLE_LOADED))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.STYLE_LOADED)) }
    notifyEvents(MapEvents.STYLE_LOADED)
    verify(exactly = 0) { listener.onStyleLoaded() }
    verify(exactly = 0) { listener2.onStyleLoaded() }
  }

  @Test
  fun addOnStyleImageMissingListener() {
    val listener = mockk<OnStyleImageMissingListener>(relaxUnitFun = true)
    nativeObserver.addOnStyleImageMissingListener(listener)
    verify { observableInterface.subscribe(any(), listOf(MapEvents.STYLE_IMAGE_MISSING)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.STYLE_IMAGE_MISSING))
    notifyEvents(MapEvents.STYLE_IMAGE_MISSING)
    verify { listener.onStyleImageMissing(any()) }

    val listener2 = mockk<OnStyleImageMissingListener>(relaxUnitFun = true)
    nativeObserver.addOnStyleImageMissingListener(listener2)
    assertEquals(2, nativeObserver.onStyleImageMissingListeners.size)
    verify(exactly = 1) {
      observableInterface.subscribe(
        any(),
        listOf(MapEvents.STYLE_IMAGE_MISSING)
      )
    }
    notifyEvents(MapEvents.STYLE_IMAGE_MISSING)
    verify { listener2.onStyleImageMissing(any()) }
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
    verify(exactly = 0) {
      observableInterface.unsubscribe(
        any(),
        listOf(MapEvents.STYLE_IMAGE_MISSING)
      )
    }
    nativeObserver.removeOnStyleImageMissingListener(listener2)
    assertEquals(0, nativeObserver.onStyleImageMissingListeners.size)
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.STYLE_IMAGE_MISSING))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.STYLE_IMAGE_MISSING)) }
    notifyEvents(MapEvents.STYLE_IMAGE_MISSING)
    verify(exactly = 0) { listener.onStyleImageMissing(any()) }
    verify(exactly = 0) { listener2.onStyleImageMissing(any()) }
  }

  @Test
  fun addOnStyleImageUnusedListener() {
    val listener = mockk<OnStyleImageUnusedListener>(relaxUnitFun = true)
    nativeObserver.addOnStyleImageUnusedListener(listener)
    verify { observableInterface.subscribe(any(), listOf(MapEvents.STYLE_IMAGE_REMOVE_UNUSED)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.STYLE_IMAGE_REMOVE_UNUSED))
    notifyEvents(MapEvents.STYLE_IMAGE_REMOVE_UNUSED)
    verify { listener.onStyleImageUnused(any()) }

    val listener2 = mockk<OnStyleImageUnusedListener>(relaxUnitFun = true)
    nativeObserver.addOnStyleImageUnusedListener(listener2)
    assertEquals(2, nativeObserver.onStyleImageUnusedListeners.size)
    verify(exactly = 1) {
      observableInterface.subscribe(
        any(),
        listOf(MapEvents.STYLE_IMAGE_REMOVE_UNUSED)
      )
    }
    notifyEvents(MapEvents.STYLE_IMAGE_REMOVE_UNUSED)
    verify { listener2.onStyleImageUnused(any()) }
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
    verify(exactly = 0) {
      observableInterface.unsubscribe(
        any(),
        listOf(MapEvents.STYLE_IMAGE_REMOVE_UNUSED)
      )
    }
    nativeObserver.removeOnStyleImageUnusedListener(listener2)
    assertEquals(0, nativeObserver.onStyleImageUnusedListeners.size)
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.STYLE_IMAGE_REMOVE_UNUSED))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.STYLE_IMAGE_REMOVE_UNUSED)) }
    notifyEvents(MapEvents.STYLE_IMAGE_REMOVE_UNUSED)
    verify(exactly = 0) { listener.onStyleImageUnused(any()) }
    verify(exactly = 0) { listener2.onStyleImageUnused(any()) }
  }

  @Test
  fun addOnStyleDataLoadedListener() {
    val listener = mockk<OnStyleDataLoadedListener>(relaxUnitFun = true)
    nativeObserver.addOnStyleDataLoadedListener(listener)
    verify { observableInterface.subscribe(any(), listOf(MapEvents.STYLE_DATA_LOADED)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.STYLE_DATA_LOADED))
    notifyEvents(MapEvents.STYLE_DATA_LOADED)
    verify { listener.onStyleDataLoaded(any()) }

    val listener2 = mockk<OnStyleDataLoadedListener>(relaxUnitFun = true)
    nativeObserver.addOnStyleDataLoadedListener(listener2)
    assertEquals(2, nativeObserver.onStyleDataLoadedListeners.size)
    verify(exactly = 1) {
      observableInterface.subscribe(
        any(),
        listOf(MapEvents.STYLE_DATA_LOADED)
      )
    }
    notifyEvents(MapEvents.STYLE_DATA_LOADED)
    verify { listener2.onStyleDataLoaded(any()) }
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
    verify(exactly = 0) {
      observableInterface.unsubscribe(
        any(),
        listOf(MapEvents.STYLE_DATA_LOADED)
      )
    }
    nativeObserver.removeOnStyleDataLoadedListener(listener2)
    assertEquals(0, nativeObserver.onStyleDataLoadedListeners.size)
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.STYLE_DATA_LOADED))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.STYLE_DATA_LOADED)) }
    notifyEvents(MapEvents.STYLE_DATA_LOADED)
    verify(exactly = 0) { listener.onStyleDataLoaded(any()) }
    verify(exactly = 0) { listener2.onStyleDataLoaded(any()) }
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

    verify { observableInterface.unsubscribe(eq(nativeObserver)) }

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
}
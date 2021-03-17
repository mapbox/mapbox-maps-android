package com.mapbox.maps

import android.os.Handler
import com.mapbox.bindgen.Value
import com.mapbox.common.ShadowValueConverter
import com.mapbox.maps.plugin.delegates.listeners.*
import io.mockk.*
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.lang.ref.WeakReference

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowValueConverter::class])
class NativeObserverTest {

  private val mainHandler = mockk<Handler>()
  private val observableInterface = mockk<ObservableInterface>(relaxed = true)
  private lateinit var nativeObserver: NativeObserver
  private val runnableSlot = slot<Runnable>()

  @Before
  fun setUp() {
    every { mainHandler.post(capture(runnableSlot)) } returns true
    nativeObserver = NativeObserver(WeakReference(observableInterface), mainHandler)
  }

  private fun notifyEvents(type: String, value: Value = Value("")) {
    nativeObserver.notify(Event(type, value))
  }

  @Test
  fun addOnCameraChangeListener() {
    val listener = mockk<OnCameraChangeListener>(relaxUnitFun = true)
    nativeObserver.addOnCameraChangeListener(listener)
    runnableSlot.captured.run()
    verify { observableInterface.subscribe(any(), listOf(MapEvents.CAMERA_CHANGED)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.CAMERA_CHANGED))
    notifyEvents(MapEvents.CAMERA_CHANGED)
    verify { listener.onCameraChanged() }
  }

  @Test
  fun removeOnCameraChangeListener() {
    val listener = mockk<OnCameraChangeListener>(relaxUnitFun = true)
    nativeObserver.removeOnCameraChangeListener(listener)
    runnableSlot.captured.run()
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.CAMERA_CHANGED))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.CAMERA_CHANGED)) }
    notifyEvents(MapEvents.CAMERA_CHANGED)
    verify(exactly = 0) { listener.onCameraChanged() }
  }

  // Map events
  @Test
  fun addOnMapIdleListener() {
    val listener = mockk<OnMapIdleListener>(relaxUnitFun = true)
    nativeObserver.addOnMapIdleListener(listener)
    runnableSlot.captured.run()
    verify { observableInterface.subscribe(any(), listOf(MapEvents.MAP_IDLE)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.MAP_IDLE))
    notifyEvents(MapEvents.MAP_IDLE)
    verify { listener.onMapIdle() }
  }

  @Test
  fun removeOnMapIdleListener() {
    val listener = mockk<OnMapIdleListener>(relaxUnitFun = true)
    nativeObserver.removeOnMapIdleListener(listener)
    runnableSlot.captured.run()
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.MAP_IDLE))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.MAP_IDLE)) }
    notifyEvents(MapEvents.MAP_IDLE)
    verify(exactly = 0) { listener.onMapIdle() }
  }

  @Test
  fun addOnMapLoadErrorListener() {
    val listener = mockk<OnMapLoadErrorListener>(relaxUnitFun = true)
    nativeObserver.addOnMapLoadErrorListener(listener)
    runnableSlot.captured.run()
    verify { observableInterface.subscribe(any(), listOf(MapEvents.MAP_LOADING_ERROR)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.MAP_LOADING_ERROR))
    notifyEvents(MapEvents.MAP_LOADING_ERROR)
    verify { listener.onMapLoadError(any(), any()) }
  }

  @Test
  fun removeOnMapLoadErrorListener() {
    val listener = mockk<OnMapLoadErrorListener>(relaxUnitFun = true)
    nativeObserver.removeOnMapLoadErrorListener(listener)
    runnableSlot.captured.run()
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.MAP_LOADING_ERROR))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.MAP_LOADING_ERROR)) }
    notifyEvents(MapEvents.MAP_LOADING_ERROR)
    verify(exactly = 0) { listener.onMapLoadError(any(), any()) }
  }

  @Test
  fun addOnMapLoadedListener() {
    val listener = mockk<OnMapLoadedListener>(relaxUnitFun = true)
    nativeObserver.addOnMapLoadedListener(listener)
    runnableSlot.captured.run()
    verify { observableInterface.subscribe(any(), listOf(MapEvents.MAP_LOADED)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.MAP_LOADED))
    notifyEvents(MapEvents.MAP_LOADED)
    verify { listener.onMapLoaded() }
  }

  @Test
  fun removeOnMapLoadedListener() {
    val listener = mockk<OnMapLoadedListener>(relaxUnitFun = true)
    nativeObserver.removeOnMapLoadedListener(listener)
    runnableSlot.captured.run()
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.MAP_LOADED))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.MAP_LOADED)) }
    notifyEvents(MapEvents.MAP_LOADED)
    verify(exactly = 0) { listener.onMapLoaded() }
  }

  // Render frame events
  @Test
  fun addOnRenderFrameFinishedListener() {
    val listener = mockk<OnRenderFrameFinishedListener>(relaxUnitFun = true)
    nativeObserver.addOnRenderFrameFinishedListener(listener)
    runnableSlot.captured.run()
    verify { observableInterface.subscribe(any(), listOf(MapEvents.RENDER_FRAME_FINISHED)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.RENDER_FRAME_FINISHED))
    notifyEvents(MapEvents.RENDER_FRAME_FINISHED)
    verify { listener.onRenderFrameFinished(any(), any(), any()) }
  }

  @Test
  fun removeOnRenderFrameFinishedListener() {
    val listener = mockk<OnRenderFrameFinishedListener>(relaxUnitFun = true)
    nativeObserver.removeOnRenderFrameFinishedListener(listener)
    runnableSlot.captured.run()
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.RENDER_FRAME_FINISHED))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.RENDER_FRAME_FINISHED)) }
    notifyEvents(MapEvents.RENDER_FRAME_FINISHED)
    verify(exactly = 0) { listener.onRenderFrameFinished(any(), any(), any()) }
  }

  @Test
  fun addOnRenderFrameStartedListener() {
    val listener = mockk<OnRenderFrameStartedListener>(relaxUnitFun = true)
    nativeObserver.addOnRenderFrameStartedListener(listener)
    runnableSlot.captured.run()
    verify { observableInterface.subscribe(any(), listOf(MapEvents.RENDER_FRAME_STARTED)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.RENDER_FRAME_STARTED))
    notifyEvents(MapEvents.RENDER_FRAME_STARTED)
    verify { listener.onRenderFrameStarted() }
  }

  @Test
  fun removeOnRenderFrameStartedListener() {
    val listener = mockk<OnRenderFrameStartedListener>(relaxUnitFun = true)
    nativeObserver.removeOnRenderFrameStartedListener(listener)
    runnableSlot.captured.run()
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.RENDER_FRAME_STARTED))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.RENDER_FRAME_STARTED)) }
    notifyEvents(MapEvents.RENDER_FRAME_STARTED)
    verify(exactly = 0) { listener.onRenderFrameStarted() }
  }

  // Source events
  @Test
  fun addOnSourceAddedListener() {
    val listener = mockk<OnSourceAddedListener>(relaxUnitFun = true)
    nativeObserver.addOnSourceAddedListener(listener)
    runnableSlot.captured.run()
    verify { observableInterface.subscribe(any(), listOf(MapEvents.SOURCE_ADDED)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.SOURCE_ADDED))
    notifyEvents(MapEvents.SOURCE_ADDED)
    verify { listener.onSourceAdded(any()) }
  }

  @Test
  fun removeOnSourceAddedListener() {
    val listener = mockk<OnSourceAddedListener>(relaxUnitFun = true)
    nativeObserver.removeOnSourceAddedListener(listener)
    runnableSlot.captured.run()
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.SOURCE_ADDED))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.SOURCE_ADDED)) }
    notifyEvents(MapEvents.SOURCE_ADDED)
    verify(exactly = 0) { listener.onSourceAdded(any()) }
  }

  @Test
  fun addOnSourceDataLoadedListener() {
    val listener = mockk<OnSourceDataLoadedListener>(relaxUnitFun = true)
    nativeObserver.addOnSourceDataLoadedListener(listener)
    runnableSlot.captured.run()
    verify { observableInterface.subscribe(any(), listOf(MapEvents.SOURCE_DATA_LOADED)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.SOURCE_DATA_LOADED))
    notifyEvents(MapEvents.SOURCE_DATA_LOADED)
    verify { listener.onSourceDataLoaded(any(), any(), any(), any()) }
  }

  @Test
  fun removeOnSourceDataLoadedListener() {
    val listener = mockk<OnSourceDataLoadedListener>(relaxUnitFun = true)
    nativeObserver.removeOnSourceDataLoadedListener(listener)
    runnableSlot.captured.run()
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.SOURCE_DATA_LOADED))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.SOURCE_DATA_LOADED)) }
    notifyEvents(MapEvents.SOURCE_DATA_LOADED)
    verify(exactly = 0) { listener.onSourceDataLoaded(any(), any(), any(), any()) }
  }

  @Test
  fun addOnSourceRemovedListener() {
    val listener = mockk<OnSourceRemovedListener>(relaxUnitFun = true)
    nativeObserver.addOnSourceRemovedListener(listener)
    runnableSlot.captured.run()
    verify { observableInterface.subscribe(any(), listOf(MapEvents.SOURCE_REMOVED)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.SOURCE_REMOVED))
    notifyEvents(MapEvents.SOURCE_REMOVED)
    verify { listener.onSourceRemoved(any()) }
  }

  @Test
  fun removeOnSourceRemovedListener() {
    val listener = mockk<OnSourceRemovedListener>(relaxUnitFun = true)
    nativeObserver.removeOnSourceRemovedListener(listener)
    runnableSlot.captured.run()
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.SOURCE_REMOVED))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.SOURCE_REMOVED)) }
    notifyEvents(MapEvents.SOURCE_REMOVED)
    verify(exactly = 0) { listener.onSourceRemoved(any()) }
  }

  // Style events
  @Test
  fun addOnStyleLoadedListener() {
    val listener = mockk<OnStyleLoadedListener>(relaxUnitFun = true)
    nativeObserver.addOnStyleLoadedListener(listener)
    runnableSlot.captured.run()
    verify { observableInterface.subscribe(any(), listOf(MapEvents.STYLE_LOADED)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.STYLE_LOADED))
    notifyEvents(MapEvents.STYLE_LOADED)
    verify { listener.onStyleLoaded() }
  }

  @Test
  fun removeOnStyleLoadedListener() {
    val listener = mockk<OnStyleLoadedListener>(relaxUnitFun = true)
    nativeObserver.removeOnStyleLoadedListener(listener)
    runnableSlot.captured.run()
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.STYLE_LOADED))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.STYLE_LOADED)) }
    notifyEvents(MapEvents.STYLE_LOADED)
    verify(exactly = 0) { listener.onStyleLoaded() }
  }

  @Test
  fun addOnStyleImageMissingListener() {
    val listener = mockk<OnStyleImageMissingListener>(relaxUnitFun = true)
    nativeObserver.addOnStyleImageMissingListener(listener)
    runnableSlot.captured.run()
    verify { observableInterface.subscribe(any(), listOf(MapEvents.STYLE_IMAGE_MISSING)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.STYLE_IMAGE_MISSING))
    notifyEvents(MapEvents.STYLE_IMAGE_MISSING)
    verify { listener.onStyleImageMissing(any()) }
  }

  @Test
  fun removeOnStyleImageMissingListener() {
    val listener = mockk<OnStyleImageMissingListener>(relaxUnitFun = true)
    nativeObserver.removeOnStyleImageMissingListener(listener)
    runnableSlot.captured.run()
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.STYLE_IMAGE_MISSING))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.STYLE_IMAGE_MISSING)) }
    notifyEvents(MapEvents.STYLE_IMAGE_MISSING)
    verify(exactly = 0) { listener.onStyleImageMissing(any()) }
  }

  @Test
  fun addOnStyleImageUnusedListener() {
    val listener = mockk<OnStyleImageUnusedListener>(relaxUnitFun = true)
    nativeObserver.addOnStyleImageUnusedListener(listener)
    runnableSlot.captured.run()
    verify { observableInterface.subscribe(any(), listOf(MapEvents.STYLE_IMAGE_REMOVE_UNUSED)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.STYLE_IMAGE_REMOVE_UNUSED))
    notifyEvents(MapEvents.STYLE_IMAGE_REMOVE_UNUSED)
    verify { listener.onStyleImageUnused(any()) }
  }

  @Test
  fun removeOnStyleImageUnusedListener() {
    val listener = mockk<OnStyleImageUnusedListener>(relaxUnitFun = true)
    nativeObserver.removeOnStyleImageUnusedListener(listener)
    runnableSlot.captured.run()
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.STYLE_IMAGE_REMOVE_UNUSED))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.STYLE_IMAGE_REMOVE_UNUSED)) }
    notifyEvents(MapEvents.STYLE_IMAGE_REMOVE_UNUSED)
    verify(exactly = 0) { listener.onStyleImageUnused(any()) }
  }

  @Test
  fun addOnStyleDataLoadedListener() {
    val listener = mockk<OnStyleDataLoadedListener>(relaxUnitFun = true)
    nativeObserver.addOnStyleDataLoadedListener(listener)
    runnableSlot.captured.run()
    verify { observableInterface.subscribe(any(), listOf(MapEvents.STYLE_DATA_LOADED)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.STYLE_DATA_LOADED))
    notifyEvents(MapEvents.STYLE_DATA_LOADED)
    verify { listener.onStyleDataLoaded(any()) }
  }

  @Test
  fun removeOnStyleDataLoadedListener() {
    val listener = mockk<OnStyleDataLoadedListener>(relaxUnitFun = true)
    nativeObserver.removeOnStyleDataLoadedListener(listener)
    runnableSlot.captured.run()
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.STYLE_DATA_LOADED))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.STYLE_DATA_LOADED)) }
    notifyEvents(MapEvents.STYLE_DATA_LOADED)
    verify(exactly = 0) { listener.onStyleDataLoaded(any()) }
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

    nativeObserver.awaitingStyleGetters.add(mockk(relaxed = true))

    nativeObserver.clearListeners()

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

    assertTrue(nativeObserver.awaitingStyleGetters.isEmpty())
  }
}
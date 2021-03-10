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
import java.util.*

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowValueConverter::class])
class NativeMapObserverTest {

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
    nativeObserver.notify(Event(type, Date(), Date(), value))
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
  fun addOnMapLoadingFinishedListener() {
    val listener = mockk<OnMapLoadingFinishedListener>(relaxUnitFun = true)
    nativeObserver.addOnMapLoadingFinishedListener(listener)
    runnableSlot.captured.run()
    verify { observableInterface.subscribe(any(), listOf(MapEvents.MAP_LOADING_FINISHED)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.MAP_LOADING_FINISHED))
    notifyEvents(MapEvents.MAP_LOADING_FINISHED)
    verify { listener.onMapLoadingFinished() }
  }

  @Test
  fun removeOnMapLoadingFinishedListener() {
    val listener = mockk<OnMapLoadingFinishedListener>(relaxUnitFun = true)
    nativeObserver.removeOnMapLoadingFinishedListener(listener)
    runnableSlot.captured.run()
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.MAP_LOADING_FINISHED))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.MAP_LOADING_FINISHED)) }
    notifyEvents(MapEvents.MAP_LOADING_FINISHED)
    verify(exactly = 0) { listener.onMapLoadingFinished() }
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
  fun addOnSourceChangeListener() {
    val listener = mockk<OnSourceChangeListener>(relaxUnitFun = true)
    nativeObserver.addOnSourceChangeListener(listener)
    runnableSlot.captured.run()
    verify { observableInterface.subscribe(any(), listOf(MapEvents.SOURCE_CHANGED)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.SOURCE_CHANGED))
    notifyEvents(MapEvents.SOURCE_CHANGED)
    verify { listener.onSourceChanged(any()) }
  }

  @Test
  fun removeOnSourceChangeListener() {
    val listener = mockk<OnSourceChangeListener>(relaxUnitFun = true)
    nativeObserver.removeOnSourceChangeListener(listener)
    runnableSlot.captured.run()
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.SOURCE_CHANGED))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.SOURCE_CHANGED)) }
    notifyEvents(MapEvents.SOURCE_CHANGED)
    verify(exactly = 0) { listener.onSourceChanged(any()) }
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
  fun addOnStyleFullyLoadedListener() {
    val listener = mockk<OnStyleFullyLoadedListener>(relaxUnitFun = true)
    nativeObserver.addOnStyleFullyLoadedListener(listener)
    runnableSlot.captured.run()
    verify { observableInterface.subscribe(any(), listOf(MapEvents.STYLE_FULLY_LOADED)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.STYLE_FULLY_LOADED))
    notifyEvents(MapEvents.STYLE_FULLY_LOADED)
    verify { listener.onStyleFullyLoaded() }
  }

  @Test
  fun removeOnStyleFullyLoadedListener() {
    val listener = mockk<OnStyleFullyLoadedListener>(relaxUnitFun = true)
    nativeObserver.removeOnStyleFullyLoadedListener(listener)
    runnableSlot.captured.run()
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.STYLE_FULLY_LOADED))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.STYLE_FULLY_LOADED)) }
    notifyEvents(MapEvents.STYLE_FULLY_LOADED)
    verify(exactly = 0) { listener.onStyleFullyLoaded() }
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
  fun addOnStyleLoadingFinishedListener() {
    val listener = mockk<OnStyleLoadingFinishedListener>(relaxUnitFun = true)
    nativeObserver.addOnStyleLoadingFinishedListener(listener)
    runnableSlot.captured.run()
    verify { observableInterface.subscribe(any(), listOf(MapEvents.STYLE_LOADING_FINISHED)) }
    assertTrue(nativeObserver.observedEvents.contains(MapEvents.STYLE_LOADING_FINISHED))
    notifyEvents(MapEvents.STYLE_LOADING_FINISHED)
    verify { listener.onStyleLoadingFinished() }
  }

  @Test
  fun removeOnStyleLoadingFinishedListener() {
    val listener = mockk<OnStyleLoadingFinishedListener>(relaxUnitFun = true)
    nativeObserver.removeOnStyleLoadingFinishedListener(listener)
    runnableSlot.captured.run()
    assertFalse(nativeObserver.observedEvents.contains(MapEvents.STYLE_LOADING_FINISHED))
    verify { observableInterface.unsubscribe(any(), listOf(MapEvents.STYLE_LOADING_FINISHED)) }
    notifyEvents(MapEvents.STYLE_LOADING_FINISHED)
    verify(exactly = 0) { listener.onStyleLoadingFinished() }
  }

  @Test
  fun clearListeners() {
    nativeObserver.onCameraChangeListeners.add(mockk(relaxed = true))

    nativeObserver.onMapIdleListeners.add(mockk(relaxed = true))
    nativeObserver.onMapLoadErrorListeners.add(mockk(relaxed = true))
    nativeObserver.onMapLoadingFinishedListeners.add(mockk(relaxed = true))

    nativeObserver.onRenderFrameFinishedListeners.add(mockk(relaxed = true))
    nativeObserver.onRenderFrameStartedListeners.add(mockk(relaxed = true))

    nativeObserver.onSourceAddedListeners.add(mockk(relaxed = true))
    nativeObserver.onSourceChangeListeners.add(mockk(relaxed = true))
    nativeObserver.onSourceRemovedListeners.add(mockk(relaxed = true))

    nativeObserver.onStyleFullyLoadedListeners.add(mockk(relaxed = true))
    nativeObserver.onStyleImageMissingListeners.add(mockk(relaxed = true))
    nativeObserver.onStyleImageUnusedListeners.add(mockk(relaxed = true))
    nativeObserver.onStyleLoadingFinishedListeners.add(mockk(relaxed = true))

    nativeObserver.awaitingStyleGetters.add(mockk(relaxed = true))

    nativeObserver.clearListeners()

    assertTrue(nativeObserver.onCameraChangeListeners.isEmpty())

    assertTrue(nativeObserver.onMapIdleListeners.isEmpty())
    assertTrue(nativeObserver.onMapLoadErrorListeners.isEmpty())
    assertTrue(nativeObserver.onMapLoadingFinishedListeners.isEmpty())

    assertTrue(nativeObserver.onRenderFrameFinishedListeners.isEmpty())
    assertTrue(nativeObserver.onRenderFrameStartedListeners.isEmpty())

    assertTrue(nativeObserver.onSourceAddedListeners.isEmpty())
    assertTrue(nativeObserver.onSourceChangeListeners.isEmpty())
    assertTrue(nativeObserver.onSourceRemovedListeners.isEmpty())

    assertTrue(nativeObserver.onStyleFullyLoadedListeners.isEmpty())
    assertTrue(nativeObserver.onStyleImageMissingListeners.isEmpty())
    assertTrue(nativeObserver.onStyleImageUnusedListeners.isEmpty())
    assertTrue(nativeObserver.onStyleLoadingFinishedListeners.isEmpty())

    assertTrue(nativeObserver.awaitingStyleGetters.isEmpty())
  }
}
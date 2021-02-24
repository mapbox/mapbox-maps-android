package com.mapbox.maps

import android.os.Handler
import com.mapbox.common.ShadowLogger
import com.mapbox.maps.plugin.delegates.listeners.*
import io.mockk.*
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
class NativeMapObserverTest {

  private val mainHandler = mockk<Handler>()
  private lateinit var mapObserver: NativeMapObserver
  private val runnableSlot = slot<Runnable>()

  @Before
  fun setUp() {
    every { mainHandler.post(capture(runnableSlot)) } returns true
    mapObserver = NativeMapObserver(mainHandler)
  }

  @Test
  fun addOnMapChangedListener() {
    val listener = mockk<OnMapChangedListener>(relaxUnitFun = true)
    mapObserver.addOnMapChangedListener(listener)
    mapObserver.onMapChanged(MapChange.DID_FINISH_LOADING_MAP)
    runnableSlot.captured.run()
    verify { listener.onMapChange(MapChange.DID_FINISH_LOADING_MAP) }
  }

  @Test
  fun removeOnMapChangedListenerEmpty() {
    val listener = mockk<OnMapChangedListener>(relaxUnitFun = true)
    mapObserver.addOnMapChangedListener(listener)
    mapObserver.removeOnMapChangedListener(listener)
    mapObserver.onMapChanged(MapChange.DID_FINISH_LOADING_MAP)
    verify(exactly = 0) {
      listener.onMapChange(MapChange.DID_FINISH_LOADING_MAP)
    }
  }

  @Test
  fun removeOnMapChangedListener() {
    val listener = mockk<OnMapChangedListener>(relaxUnitFun = true)
    mapObserver.addOnMapChangedListener(mockk(relaxed = true))
    mapObserver.addOnMapChangedListener(listener)
    mapObserver.removeOnMapChangedListener(listener)
    mapObserver.onMapChanged(MapChange.DID_FINISH_LOADING_MAP)
    runnableSlot.captured.run()
    verify(exactly = 0) {
      listener.onMapChange(MapChange.DID_FINISH_LOADING_MAP)
    }
  }

  @Test
  fun addOnMapLoadErrorListener() {
    val listener = mockk<OnMapLoadErrorListener>(relaxUnitFun = true)
    mapObserver.addOnMapLoadErrorListener(listener)
    mapObserver.onMapLoadError(MapLoadError.STYLE_LOAD_ERROR, "foobar")
    runnableSlot.captured.run()
    verify { listener.onMapLoadError(MapLoadError.STYLE_LOAD_ERROR, "foobar") }
  }

  @Test
  fun removeOnMapLoadErrorListenerEmpty() {
    val listener = mockk<OnMapLoadErrorListener>(relaxUnitFun = true)
    mapObserver.addOnMapLoadErrorListener(listener)
    mapObserver.removeOnMapLoadErrorListener(listener)
    mapObserver.onMapLoadError(MapLoadError.STYLE_LOAD_ERROR, "foobar")
    verify(exactly = 0) {
      listener.onMapLoadError(MapLoadError.STYLE_LOAD_ERROR, "foobar")
    }
  }

  @Test
  fun removeOnMapLoadErrorListener() {
    val listener = mockk<OnMapLoadErrorListener>(relaxUnitFun = true)
    mapObserver.addOnMapLoadErrorListener(mockk(relaxed = true))
    mapObserver.addOnMapLoadErrorListener(listener)
    mapObserver.removeOnMapLoadErrorListener(listener)
    mapObserver.onMapLoadError(MapLoadError.STYLE_LOAD_ERROR, "foobar")
    runnableSlot.captured.run()
    verify(exactly = 0) {
      listener.onMapLoadError(MapLoadError.STYLE_LOAD_ERROR, "foobar")
    }
  }

  @Test
  fun addOnDidFinishRenderingFrameListener() {
    val renderFrameStatus = RenderFrameStatus(RenderMode.FULL, false, false)
    val listener = mockk<OnDidFinishRenderingFrameListener>(relaxUnitFun = true)
    mapObserver.addOnDidFinishRenderingFrameListener(listener)
    mapObserver.onDidFinishRenderingFrame(renderFrameStatus)
    runnableSlot.captured.run()
    verify { listener.onDidFinishRenderingFrame(renderFrameStatus) }
  }

  @Test
  fun removeOnDidFinishRenderingFrameListenerEmpty() {
    val renderFrameStatus = RenderFrameStatus(RenderMode.FULL, false, false)
    val listener = mockk<OnDidFinishRenderingFrameListener>(relaxUnitFun = true)
    mapObserver.addOnDidFinishRenderingFrameListener(listener)
    mapObserver.removeOnDidFinishRenderingFrameListener(listener)
    mapObserver.onDidFinishRenderingFrame(renderFrameStatus)
    verify(exactly = 0) {
      listener.onDidFinishRenderingFrame(renderFrameStatus)
    }
  }

  @Test
  fun removeOnDidFinishRenderingFrameListener() {
    val renderFrameStatus = RenderFrameStatus(RenderMode.FULL, false, false)
    val listener = mockk<OnDidFinishRenderingFrameListener>(relaxUnitFun = true)
    mapObserver.addOnDidFinishRenderingFrameListener(mockk(relaxed = true))
    mapObserver.addOnDidFinishRenderingFrameListener(listener)
    mapObserver.removeOnDidFinishRenderingFrameListener(listener)
    mapObserver.onDidFinishRenderingFrame(renderFrameStatus)
    runnableSlot.captured.run()
    verify(exactly = 0) {
      listener.onDidFinishRenderingFrame(renderFrameStatus)
    }
  }

  @Test
  fun addOnCameraChangeListenerDidChange() {
    val listener = mockk<OnCameraChangeListener>(relaxUnitFun = true)
    mapObserver.addOnCameraChangeListener(listener)
    mapObserver.onCameraChange(CameraChange.CAMERA_DID_CHANGE, CameraChangeMode.IMMEDIATE)
    verify { listener.onCameraChanged() }
  }

  @Test
  fun addOnCameraChangeListenerWillChange() {
    val listener = mockk<OnCameraChangeListener>(relaxUnitFun = true)
    mapObserver.addOnCameraChangeListener(listener)
    mapObserver.onCameraChange(CameraChange.CAMERA_WILL_CHANGE, CameraChangeMode.IMMEDIATE)
    verify(exactly = 0) { listener.onCameraChanged() }
  }

  @Test
  fun removeOnCameraChangeListenerEmpty() {
    val listener = mockk<OnCameraChangeListener>(relaxUnitFun = true)
    mapObserver.addOnCameraChangeListener(mockk(relaxed = true))
    mapObserver.addOnCameraChangeListener(listener)
    mapObserver.removeOnCameraChangeListener(listener)
    mapObserver.onCameraChange(CameraChange.CAMERA_DID_CHANGE, CameraChangeMode.IMMEDIATE)
    verify(exactly = 0) {
      listener.onCameraChanged()
    }
  }

  @Test
  fun removeOnCameraChangeListener() {
    val listener = mockk<OnCameraChangeListener>(relaxUnitFun = true)
    mapObserver.addOnCameraChangeListener(mockk(relaxed = true))
    mapObserver.addOnCameraChangeListener(listener)
    mapObserver.removeOnCameraChangeListener(listener)
    mapObserver.onCameraChange(CameraChange.CAMERA_DID_CHANGE, CameraChangeMode.IMMEDIATE)
    verify(exactly = 0) {
      listener.onCameraChanged()
    }
  }

  @Test
  fun addOnSourceChangeListener() {
    val listener = mockk<OnSourceChangeListener>(relaxUnitFun = true)
    mapObserver.addOnSourceChangeListener(listener)
    mapObserver.onSourceChanged("foorbar")
    runnableSlot.captured.run()
    verify { listener.onSourceChanged("foorbar") }
  }

  @Test
  fun removeOnSourceChangeListenerEmpty() {
    val listener = mockk<OnSourceChangeListener>(relaxUnitFun = true)
    mapObserver.addOnSourceChangeListener(listener)
    mapObserver.removeOnSourceChangeListener(listener)
    mapObserver.onSourceChanged("foobar")
    verify(exactly = 0) {
      listener.onSourceChanged("foobar")
    }
  }

  @Test
  fun removeOnSourceChangeListener() {
    val listener = mockk<OnSourceChangeListener>(relaxUnitFun = true)
    mapObserver.addOnSourceChangeListener(mockk(relaxed = true))
    mapObserver.addOnSourceChangeListener(listener)
    mapObserver.removeOnSourceChangeListener(listener)
    mapObserver.onSourceChanged("foobar")
    runnableSlot.captured.run()
    verify(exactly = 0) {
      listener.onSourceChanged("foobar")
    }
  }

  @Test
  fun addOnStyleImageChangeMissingListener() {
    val listener = mockk<OnStyleImageChangeListener>(relaxUnitFun = true)
    mapObserver.addOnStyleImageChangeListener(listener)
    mapObserver.onStyleImageMissing("foorbar")
    runnableSlot.captured.run()
    verify { listener.onStyleImageMissing("foorbar") }
  }

  @Test
  fun removeOnStyleImageChangeMissingListenerEmpty() {
    val listener = mockk<OnStyleImageChangeListener>(relaxUnitFun = true)
    mapObserver.addOnStyleImageChangeListener(listener)
    mapObserver.removeOnStyleImageChangeListener(listener)
    mapObserver.onStyleImageMissing("foobar")
    verify(exactly = 0) {
      listener.onStyleImageMissing("foobar")
    }
  }

  @Test
  fun removeOnStyleImageChangeMissingListener() {
    val listener = mockk<OnStyleImageChangeListener>(relaxUnitFun = true)
    mapObserver.addOnStyleImageChangeListener(listener)
    mapObserver.addOnStyleImageChangeListener(mockk(relaxUnitFun = true))
    mapObserver.removeOnStyleImageChangeListener(listener)
    mapObserver.onStyleImageMissing("foobar")
    verify(exactly = 0) {
      listener.onStyleImageMissing("foobar")
    }
  }

  @Test
  fun addOnStyleImageChangeObsoleteListener() {
    val listener = mockk<OnStyleImageChangeListener>(relaxUnitFun = true)
    every { listener.onCanRemoveUnusedStyleImage(any()) } returns true
    mapObserver.addOnStyleImageChangeListener(listener)
    mapObserver.onCanRemoveUnusedStyleImage("foorbar")
    verify { listener.onCanRemoveUnusedStyleImage("foorbar") }
  }

  @Test
  fun removeOnStyleImageChangeObsoleteListenerEmpty() {
    val listener = mockk<OnStyleImageChangeListener>(relaxUnitFun = true)
    mapObserver.addOnStyleImageChangeListener(mockk(relaxed = true))
    mapObserver.addOnStyleImageChangeListener(listener)
    mapObserver.removeOnStyleImageChangeListener(listener)
    mapObserver.onCanRemoveUnusedStyleImage("foobar")
    verify(exactly = 0) {
      listener.onCanRemoveUnusedStyleImage("foobar")
    }
  }
  @Test
  fun removeOnStyleImageChangeObsoleteListener() {
    val listener = mockk<OnStyleImageChangeListener>(relaxUnitFun = true)
    mapObserver.addOnStyleImageChangeListener(mockk(relaxed = true))
    mapObserver.addOnStyleImageChangeListener(listener)
    mapObserver.removeOnStyleImageChangeListener(listener)
    mapObserver.onCanRemoveUnusedStyleImage("foobar")
    verify(exactly = 0) {
      listener.onCanRemoveUnusedStyleImage("foobar")
    }
  }

  @Test
  fun addOnDidFinishRenderingMapListener() {
    val listener = mockk<OnDidFinishRenderingMapListener>(relaxUnitFun = true)
    mapObserver.addOnDidFinishRenderingMapListener(listener)
    mapObserver.onDidFinishRenderingMap(RenderMode.FULL)
    runnableSlot.captured.run()
    verify { listener.onDidFinishRenderingMap(RenderMode.FULL) }
  }

  @Test
  fun removeOnDidFinishRenderingMapListenerEmpty() {
    val listener = mockk<OnDidFinishRenderingMapListener>(relaxUnitFun = true)
    mapObserver.addOnDidFinishRenderingMapListener(listener)
    mapObserver.removeOnDidFinishRenderingMapListener(listener)
    mapObserver.onDidFinishRenderingMap(RenderMode.FULL)
    verify(exactly = 0) {
      listener.onDidFinishRenderingMap(RenderMode.FULL)
    }
  }

  @Test
  fun removeOnDidFinishRenderingMapListener() {
    val listener = mockk<OnDidFinishRenderingMapListener>(relaxUnitFun = true)
    mapObserver.addOnDidFinishRenderingMapListener(listener)
    mapObserver.addOnDidFinishRenderingMapListener(mockk(relaxed = true))
    mapObserver.removeOnDidFinishRenderingMapListener(listener)
    mapObserver.onDidFinishRenderingMap(RenderMode.FULL)
    runnableSlot.captured.run()
    verify(exactly = 0) {
      listener.onDidFinishRenderingMap(RenderMode.FULL)
    }
  }

  @Test
  fun clearListeners() {
    mapObserver.addOnDidFinishRenderingMapListener(mockk(relaxed = true))
    mapObserver.addOnStyleImageChangeListener(mockk(relaxed = true))
    mapObserver.addOnStyleImageChangeListener(mockk(relaxUnitFun = true))
    mapObserver.addOnSourceChangeListener(mockk(relaxed = true))
    mapObserver.addOnCameraChangeListener(mockk(relaxed = true))
    mapObserver.addOnDidFinishRenderingFrameListener(mockk(relaxed = true))
    mapObserver.addOnDidFinishRenderingMapListener(mockk(relaxed = true))
    mapObserver.clearListeners()
    assertTrue(mapObserver.onCameraChangeListeners.isEmpty())
    assertTrue(mapObserver.onDidFinishRenderingFrameListeners.isEmpty())
    assertTrue(mapObserver.onMapChangedListeners.isEmpty())
    assertTrue(mapObserver.onMapLoadErrorListeners.isEmpty())
    assertTrue(mapObserver.onSourceChangeListeners.isEmpty())
    assertTrue(mapObserver.onStyleImageChangeListeners.isEmpty())
    assertTrue(mapObserver.onDidFinishRenderingMapListeners.isEmpty())
    assertTrue(mapObserver.awaitingStyleGetters.isEmpty())
  }
}
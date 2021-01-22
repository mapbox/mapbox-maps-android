package com.mapbox.maps

import com.mapbox.bindgen.Value
import com.mapbox.common.ShadowLogger
import com.mapbox.maps.listener.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
class NativeMapObserverTest {

  private lateinit var mapObserver: NativeMapObserver

  @Before
  fun setUp() {
    mapObserver = NativeMapObserver()
    mapObserver.initialize(
      mockk(relaxed = true), mockk(relaxed = true), mockk(relaxed = true)
    )
  }

  @Test
  fun addOnDidFinishRenderingFrameListenerFull() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.RENDER_FRAME_FINISHED
    every { event.data } returns Value.valueOf(
      mapOf(
        "render-mode" to Value("full"),
        "needs-repaint" to Value("false"),
        "placement-changed" to Value("false"),
      ) as HashMap<String, Value>
    )

    val renderFrameStatus = RenderFrameStatus(RenderMode.FULL, false, false)
    val listener = mockk<OnDidFinishRenderingFrameListener>(relaxUnitFun = true)
    mapObserver.addOnDidFinishRenderingFrameListener(listener)
    mapObserver.notify(event)
    verify { listener.onDidFinishRenderingFrame(renderFrameStatus) }
  }

  @Test
  fun addOnDidFinishRenderingFrameListenerPartial() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.RENDER_FRAME_FINISHED
    every { event.data } returns Value.valueOf(
      mapOf(
        "render-mode" to Value("partial"),
        "needs-repaint" to Value("true"),
        "placement-changed" to Value("true"),
      ) as HashMap<String, Value>
    )

    val renderFrameStatus = RenderFrameStatus(RenderMode.PARTIAL, true, true)
    val listener = mockk<OnDidFinishRenderingFrameListener>(relaxUnitFun = true)
    mapObserver.addOnDidFinishRenderingFrameListener(listener)
    mapObserver.notify(event)
    verify { listener.onDidFinishRenderingFrame(renderFrameStatus) }
  }

  @Test
  fun removeOnDidFinishRenderingFrameListenerEmpty() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.RENDER_FRAME_FINISHED
    every { event.data } returns Value.valueOf(
      mapOf(
        "render-mode" to Value("partial"),
        "needs-repaint" to Value("true"),
        "placement-changed" to Value("true"),
      ) as HashMap<String, Value>
    )

    val renderFrameStatus = RenderFrameStatus(RenderMode.FULL, false, false)
    val listener = mockk<OnDidFinishRenderingFrameListener>(relaxUnitFun = true)
    mapObserver.addOnDidFinishRenderingFrameListener(listener)
    mapObserver.removeOnDidFinishRenderingFrameListener(listener)
    mapObserver.notify(event)
    verify(exactly = 0) {
      listener.onDidFinishRenderingFrame(renderFrameStatus)
    }
  }

  @Test
  fun removeOnDidFinishRenderingFrameListener() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.RENDER_FRAME_FINISHED
    every { event.data } returns Value.valueOf(
      mapOf(
        "render-mode" to Value("partial"),
        "needs-repaint" to Value("true"),
        "placement-changed" to Value("true"),
      ) as HashMap<String, Value>
    )

    val renderFrameStatus = RenderFrameStatus(RenderMode.FULL, false, false)
    val listener = mockk<OnDidFinishRenderingFrameListener>(relaxUnitFun = true)
    mapObserver.addOnDidFinishRenderingFrameListener(mockk(relaxed = true))
    mapObserver.addOnDidFinishRenderingFrameListener(listener)
    mapObserver.removeOnDidFinishRenderingFrameListener(listener)
    mapObserver.notify(event)
    verify(exactly = 0) {
      listener.onDidFinishRenderingFrame(renderFrameStatus)
    }
  }

  @Test
  fun addOnCameraChangeListener() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.CAMERA_DID_CHANGE

    val listener = mockk<OnCameraChangeListener>(relaxUnitFun = true)
    mapObserver.addOnCameraChangeListener(listener)
    mapObserver.notify(event)
    verify { listener.onCameraChanged() }
  }

  @Test
  fun removeOnCameraChangeListenerEmpty() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.CAMERA_DID_CHANGE

    val listener = mockk<OnCameraChangeListener>(relaxUnitFun = true)
    mapObserver.addOnCameraChangeListener(mockk(relaxed = true))
    mapObserver.addOnCameraChangeListener(listener)
    mapObserver.removeOnCameraChangeListener(listener)
    mapObserver.notify(event)
    verify(exactly = 0) {
      listener.onCameraChanged()
    }
  }

  @Test
  fun removeOnCameraChangeListener() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.CAMERA_DID_CHANGE

    val listener = mockk<OnCameraChangeListener>(relaxUnitFun = true)
    mapObserver.addOnCameraChangeListener(mockk(relaxed = true))
    mapObserver.addOnCameraChangeListener(listener)
    mapObserver.removeOnCameraChangeListener(listener)
    mapObserver.notify(event)
    verify(exactly = 0) {
      listener.onCameraChanged()
    }
  }

  @Test
  fun addOnSourceChangeListener() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.SOURCE_CHANGED
    every { event.data } returns Value.valueOf(
      mutableMapOf(
        "id" to Value("foobar"),
      ) as HashMap<String, Value>
    )
    val listener = mockk<OnSourceChangeListener>(relaxUnitFun = true)
    mapObserver.addOnSourceChangeListener(listener)
    mapObserver.notify(event)
    verify { listener.onSourceChanged("foobar") }
  }

  @Test
  fun removeOnSourceChangeListenerEmpty() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.SOURCE_CHANGED
    every { event.data } returns Value.valueOf(
      mutableMapOf(
        "id" to Value("foobar"),
      ) as HashMap<String, Value>
    )
    val listener = mockk<OnSourceChangeListener>(relaxUnitFun = true)
    mapObserver.addOnSourceChangeListener(listener)
    mapObserver.removeOnSourceChangeListener(listener)
    mapObserver.notify(event)
    verify(exactly = 0) {
      listener.onSourceChanged("foobar")
    }
  }

  @Test
  fun removeOnSourceChangeListener() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.SOURCE_CHANGED
    every { event.data } returns Value.valueOf(
      mutableMapOf(
        "id" to Value("foobar"),
      ) as HashMap<String, Value>
    )
    val listener = mockk<OnSourceChangeListener>(relaxUnitFun = true)
    mapObserver.addOnSourceChangeListener(mockk(relaxed = true))
    mapObserver.addOnSourceChangeListener(listener)
    mapObserver.removeOnSourceChangeListener(listener)
    mapObserver.notify(event)
    verify(exactly = 0) {
      listener.onSourceChanged("foobar")
    }
  }

  @Test
  fun addOnStyleImageChangeMissingListener() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.STYLE_IMAGE_MISSING
    every { event.data } returns Value.valueOf(
      mutableMapOf(
        "id" to Value("foobar"),
      ) as HashMap<String, Value>
    )
    val listener = mockk<OnStyleImageChangeListener>(relaxUnitFun = true)
    mapObserver.addOnStyleImageChangeListener(listener)
    mapObserver.notify(event)
    verify { listener.onStyleImageMissing("foobar") }
  }

  @Test
  fun removeOnStyleImageChangeMissingListenerEmpty() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.STYLE_IMAGE_MISSING
    every { event.data } returns Value.valueOf(
      mutableMapOf(
        "id" to Value("foobar"),
      ) as HashMap<String, Value>
    )
    val listener = mockk<OnStyleImageChangeListener>(relaxUnitFun = true)
    mapObserver.addOnStyleImageChangeListener(listener)
    mapObserver.removeOnStyleImageChangeListener(listener)
    mapObserver.notify(event)
    verify(exactly = 0) {
      listener.onStyleImageMissing("foobar")
    }
  }

  @Test
  fun removeOnStyleImageChangeMissingListener() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.STYLE_IMAGE_MISSING
    every { event.data } returns Value.valueOf(
      mutableMapOf(
        "id" to Value("foobar"),
      ) as HashMap<String, Value>
    )
    val listener = mockk<OnStyleImageChangeListener>(relaxUnitFun = true)
    mapObserver.addOnStyleImageChangeListener(listener)
    mapObserver.addOnStyleImageChangeListener(mockk(relaxUnitFun = true))
    mapObserver.removeOnStyleImageChangeListener(listener)
    mapObserver.notify(event)
    verify(exactly = 0) {
      listener.onStyleImageMissing("foobar")
    }
  }

  @Test
  fun addOnStyleImageChangeObsoleteListener() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.STYLE_IMAGE_REMOVE_UNUSED
    every { event.data } returns Value.valueOf(
      mutableMapOf(
        "id" to Value("foobar"),
      ) as HashMap<String, Value>
    )

    val listener = mockk<OnStyleImageChangeListener>(relaxUnitFun = true)
    every { listener.onCanRemoveUnusedStyleImage(any()) } returns true
    mapObserver.addOnStyleImageChangeListener(listener)
    mapObserver.notify(event)
    verify { listener.onCanRemoveUnusedStyleImage("foobar") }
  }

  @Test
  fun removeOnStyleImageChangeObsoleteListenerEmpty() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.STYLE_IMAGE_REMOVE_UNUSED
    every { event.data } returns Value.valueOf(
      mutableMapOf(
        "id" to Value("foobar"),
      ) as HashMap<String, Value>
    )

    val listener = mockk<OnStyleImageChangeListener>(relaxUnitFun = true)
    mapObserver.addOnStyleImageChangeListener(mockk(relaxed = true))
    mapObserver.addOnStyleImageChangeListener(listener)
    mapObserver.removeOnStyleImageChangeListener(listener)
    mapObserver.notify(event)
    verify(exactly = 0) {
      listener.onCanRemoveUnusedStyleImage("foobar")
    }
  }

  @Test
  fun removeOnStyleImageChangeObsoleteListener() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.STYLE_IMAGE_REMOVE_UNUSED
    every { event.data } returns Value.valueOf(
      mutableMapOf(
        "id" to Value("foobar"),
      ) as HashMap<String, Value>
    )

    val listener = mockk<OnStyleImageChangeListener>(relaxUnitFun = true)
    mapObserver.addOnStyleImageChangeListener(mockk(relaxed = true))
    mapObserver.addOnStyleImageChangeListener(listener)
    mapObserver.removeOnStyleImageChangeListener(listener)
    mapObserver.notify(event)
    verify(exactly = 0) {
      listener.onCanRemoveUnusedStyleImage("foobar")
    }
  }

  @Test
  fun addOnDidFinishRenderingMapListener() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.RENDER_MAP_FINISHED
    val data = hashMapOf("render-mode" to Value("FULL"))
    every { event.data } returns Value(data)
    val listener = mockk<OnDidFinishRenderingMapListener>(relaxUnitFun = true)
    mapObserver.addOnDidFinishRenderingMapListener(listener)
    mapObserver.notify(event)
    verify { listener.onDidFinishRenderingMap(any()) }
  }

  @Test
  fun removeOnDidFinishRenderingMapListenerEmpty() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.RENDER_MAP_FINISHED
    val listener = mockk<OnDidFinishRenderingMapListener>(relaxUnitFun = true)
    mapObserver.addOnDidFinishRenderingMapListener(listener)
    mapObserver.removeOnDidFinishRenderingMapListener(listener)
    mapObserver.notify(event)
    verify(exactly = 0) {
      listener.onDidFinishRenderingMap(any())
    }
  }

  @Test
  fun removeOnDidFinishRenderingMapListener() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.RENDER_MAP_FINISHED
    every { event.type } returns MapEvents.RENDER_MAP_FINISHED
    val data = hashMapOf("render-mode" to Value("FULL"))
    every { event.data } returns Value(data)
    val listener = mockk<OnDidFinishRenderingMapListener>(relaxUnitFun = true)
    mapObserver.addOnDidFinishRenderingMapListener(listener)
    mapObserver.addOnDidFinishRenderingMapListener(mockk(relaxed = true))
    mapObserver.removeOnDidFinishRenderingMapListener(listener)
    mapObserver.notify(event)
    verify(exactly = 0) {
      listener.onDidFinishRenderingMap(any())
    }
  }

  @Test
  fun addOnDidBecomeIdleListener() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.MAP_IDLE
    val listener = mockk<OnDidBecomeIdleListener>(relaxUnitFun = true)
    mapObserver.addOnDidBecomeIdleListener(listener)
    mapObserver.notify(event)
    verify { listener.onIdle() }
  }

  @Test
  fun removeOnDidBecomeListener() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.MAP_IDLE
    val listener = mockk<OnDidBecomeIdleListener>(relaxUnitFun = true)
    mapObserver.addOnDidBecomeIdleListener(listener)
    mapObserver.removeOnDidBecomeIdleListener(listener)
    mapObserver.notify(event)
    verify(exactly = 0) {
      listener.onIdle()
    }
  }

  @Test
  fun addOnDidFinishLoadingMapListener() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.MAP_LOADING_FINISHED
    val listener = mockk<OnDidFinishLoadingMapListener>(relaxUnitFun = true)
    mapObserver.addOnDidFinishLoadingMapListener(listener)
    mapObserver.notify(event)
    verify { listener.onDidFinishLoadingMapListener() }
  }

  @Test
  fun removeOnDidFinishLoadingMapListener() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.MAP_LOADING_FINISHED
    val listener = mockk<OnDidFinishLoadingMapListener>(relaxUnitFun = true)
    mapObserver.addOnDidFinishLoadingMapListener(listener)
    mapObserver.removeOnDidFinishLoadingMapListener(listener)
    mapObserver.notify(event)
    verify(exactly = 0) {
      listener.onDidFinishLoadingMapListener()
    }
  }

  @Test
  fun addOnWillStartLoadingMapListener() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.MAP_LOADING_STARTED
    val listener = mockk<OnWillStartLoadingMapListener>(relaxUnitFun = true)
    mapObserver.addOnWillStartLoadingMapListener(listener)
    mapObserver.notify(event)
    verify { listener.onWillStartLoadingMap() }
  }

  @Test
  fun removeOnWillStartLoadingMapListener() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.MAP_LOADING_STARTED
    val listener = mockk<OnWillStartLoadingMapListener>(relaxUnitFun = true)
    mapObserver.addOnWillStartLoadingMapListener(listener)
    mapObserver.removeOnWillStartLoadingMapListener(listener)
    mapObserver.notify(event)
    verify(exactly = 0) {
      listener.onWillStartLoadingMap()
    }
  }

  @Test
  fun addOnWillStartRenderingFrameListener() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.RENDER_FRAME_STARTED
    val listener = mockk<OnWillStartRenderingFrameListener>(relaxUnitFun = true)
    mapObserver.addOnWillStartRenderingFrameListener(listener)
    mapObserver.notify(event)
    verify { listener.onWillStartRenderingFrame() }
  }

  @Test
  fun removeOnWillStartRenderingFrameListener() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.RENDER_FRAME_STARTED
    val listener = mockk<OnWillStartRenderingFrameListener>(relaxUnitFun = true)
    mapObserver.addOnWillStartRenderingFrameListener(listener)
    mapObserver.removeOnWillStartRenderingFrameListener(listener)
    mapObserver.notify(event)
    verify(exactly = 0) {
      listener.onWillStartRenderingFrame()
    }
  }

  @Test
  fun addOnWillStartRenderingMapListener() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.RENDER_MAP_STARTED
    val listener = mockk<OnWillStartRenderingMapListener>(relaxUnitFun = true)
    mapObserver.addOnWillStartRenderingMapListener(listener)
    mapObserver.notify(event)
    verify { listener.onWillStartRenderingMap() }
  }

  @Test
  fun removeOnWillStartRenderingMapListener() {
    val event = mockk<Event>()
    every { event.type } returns MapEvents.RENDER_MAP_STARTED
    val listener = mockk<OnWillStartRenderingMapListener>(relaxUnitFun = true)
    mapObserver.addOnWillStartRenderingMapListener(listener)
    mapObserver.removeOnWillStartRenderingMapListener(listener)
    mapObserver.notify(event)
    verify(exactly = 0) {
      listener.onWillStartRenderingMap()
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
    mapObserver.addOnDidBecomeIdleListener(mockk(relaxed = true))
    mapObserver.addOnDidFinishLoadingMapListener(mockk(relaxed = true))
    mapObserver.addOnWillStartLoadingMapListener(mockk(relaxed = true))
    mapObserver.addOnWillStartRenderingFrameListener(mockk(relaxed = true))
    mapObserver.addOnWillStartRenderingMapListener(mockk(relaxed = true))
    mapObserver.clearListeners()
    assertTrue(mapObserver.onCameraChangeListeners.isEmpty())
    assertTrue(mapObserver.onDidFinishRenderingFrameListeners.isEmpty())
    assertTrue(mapObserver.onSourceChangeListeners.isEmpty())
    assertTrue(mapObserver.onStyleImageChangeListeners.isEmpty())
    assertTrue(mapObserver.onDidFinishRenderingMapListeners.isEmpty())
    assertTrue(mapObserver.awaitingStyleGetters.isEmpty())
    assertTrue(mapObserver.awaitingStyleErrors.isEmpty())
    assertTrue(mapObserver.onDidBecomeIdleListeners.isEmpty())
    assertTrue(mapObserver.onDidFinishLoadingMapListeners.isEmpty())
    assertTrue(mapObserver.onWillStartLoadingMapListeners.isEmpty())
    assertTrue(mapObserver.onWillStartRenderingFrameListeners.isEmpty())
    assertTrue(mapObserver.onWillStartRenderingMapListeners.isEmpty())
  }
}
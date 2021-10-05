package com.mapbox.maps

import com.mapbox.common.ShadowLogger
import com.mapbox.maps.plugin.delegates.listeners.OnMapLoadErrorListener
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.lang.ref.WeakReference

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
class StyleObserverTest {

  /**
   * Verifies if the correct listeners are attached to NativeMapObserver when StyleObserver is created
   */
  @Test
  fun onStyleObserverCreate() {
    val nativeObserver = mockk<NativeObserver>(relaxed = true)
    StyleObserver(mockk(), nativeObserver, 1.0f)
    verify { nativeObserver.addOnStyleLoadedListener(any()) }
    verify { nativeObserver.addOnMapLoadErrorListener(any()) }
  }

  /**
   * Verifies if the correct listeners are detached to NativeMapObserver when StyleObserver is destroyed
   */
  @Test
  fun onStyleObserverDestroy() {
    val nativeObserver = mockk<NativeObserver>(relaxed = true)
    StyleObserver(mockk(), nativeObserver, 1.0f).onDestroy()
    verify { nativeObserver.removeOnStyleLoadedListener(any()) }
    verify { nativeObserver.removeOnMapLoadErrorListener(any()) }
  }

  /**
   * Verifies if the user provided OnStyleLoaded is called when style loading finishes
   */
  @Test
  fun onStyleLoadSuccess() {
    val styleObserver = StyleObserver(
      mapboxMap = MapboxMap(WeakReference(mockk(relaxed = true)), mockk(relaxed = true), 1.0f),
      nativeObserver = mockk(relaxed = true),
      pixelRatio = 1.0f
    )
    val styleLoaded = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.onNewStyleLoad(styleLoaded, null)
    styleObserver.onStyleLoaded(mockk())
    verify { styleLoaded.onStyleLoaded(any()) }
  }

  /**
   * Verifies if the multiple user provided OnStyleLoaded are called when style loading finishes
   */
  @Test
  fun onStyleLoadSuccessMulti() {
    val styleObserver = StyleObserver(
      mapboxMap = MapboxMap(WeakReference(mockk(relaxed = true)), mockk(relaxed = true), 1.0f),
      nativeObserver = mockk(relaxed = true),
      pixelRatio = 1.0f
    )
    val styleLoaded = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.onNewStyleLoad(styleLoaded, null)
    val styleLoaded2 = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.addOnStyleLoadListener(styleLoaded2)
    styleObserver.onStyleLoaded(mockk())
    verify { styleLoaded.onStyleLoaded(any()) }
    verify { styleLoaded2.onStyleLoaded(any()) }
  }

  /**
   * Verifies if the user provided OnStyleLoaded is not called called when a new style has been loaded
   */
  @Test
  fun onStyleLoadSuccessNotCalled() {
    val styleObserver = StyleObserver(
      mapboxMap = MapboxMap(WeakReference(mockk(relaxed = true)), mockk(relaxed = true), 1.0f),
      nativeObserver = mockk(relaxed = true),
      pixelRatio = 1.0f
    )
    val styleLoadedFail = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.onNewStyleLoad(styleLoadedFail, null)
    val styleLoadedSucces = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.onNewStyleLoad(styleLoadedSucces, null)
    styleObserver.onStyleLoaded(mockk())
    verify(exactly = 0) { styleLoadedFail.onStyleLoaded(any()) }
    verify { styleLoadedSucces.onStyleLoaded(any()) }
  }

  /**
   * Verifies if the user provided OnMapLoadErrorListener is called when the style has produced an error
   */
  @Test
  fun onStyleLoadError() {
    val styleObserver = StyleObserver(mockk(relaxed = true), mockk(relaxed = true), 1.0f)
    val errorListener = mockk<OnMapLoadErrorListener>(relaxed = true)
    styleObserver.onNewStyleLoad(mockk(relaxed = true), errorListener)
    styleObserver.onMapLoadError(mockk(relaxed = true))
    verify { errorListener.onMapLoadError(any()) }
  }

  /**
   * Verifies if the user provided OnMapLoadListener is not called when a secondary style load occurs
   */
  @Test
  fun onStyleLoadErrorNotCalled() {
    val styleObserver = StyleObserver(mockk(relaxed = true), mockk(relaxed = true), 1.0f)
    val errorListenerFail = mockk<OnMapLoadErrorListener>(relaxed = true)
    styleObserver.onNewStyleLoad(mockk(relaxed = true), errorListenerFail)
    val errorListenerSuccess = mockk<OnMapLoadErrorListener>(relaxed = true)
    styleObserver.onNewStyleLoad(mockk(relaxed = true), errorListenerSuccess)
    styleObserver.onMapLoadError(mockk(relaxed = true))
    verify(exactly = 0) { errorListenerFail.onMapLoadError(any()) }
    verify { errorListenerSuccess.onMapLoadError(any()) }
  }
}
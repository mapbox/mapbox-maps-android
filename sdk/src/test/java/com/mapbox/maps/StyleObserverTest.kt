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
    StyleObserver(mockk(), mockk(relaxed = true), nativeObserver, 1.0f)
    verify { nativeObserver.addOnStyleLoadedListener(any()) }
    verify { nativeObserver.addOnMapLoadErrorListener(any()) }
  }

  /**
   * Verifies if the correct listeners are detached to NativeMapObserver when StyleObserver is destroyed
   */
  @Test
  fun onStyleObserverDestroy() {
    val nativeObserver = mockk<NativeObserver>(relaxed = true)
    StyleObserver(mockk(), mockk(relaxed = true), nativeObserver, 1.0f).onDestroy()
    verify { nativeObserver.removeOnStyleLoadedListener(any()) }
    verify { nativeObserver.removeOnMapLoadErrorListener(any()) }
  }

  /**
   * Verifies if the user provided OnStyleLoaded is called when style loading finishes
   */
  @Test
  fun onStyleLoadSuccess() {
    val mainStyleLoadedListener = mockk<Style.OnStyleLoaded>(relaxed = true)
    val styleObserver = StyleObserver(
      nativeMapWeakRef = WeakReference(mockk(relaxed = true)),
      styleLoadedListener = mainStyleLoadedListener,
      nativeObserver = mockk(relaxed = true),
      pixelRatio = 1.0f
    )
    val styleLoaded = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.setLoadStyleListener(styleLoaded, null)
    styleObserver.onStyleLoaded(mockk())
    verify { styleLoaded.onStyleLoaded(any()) }
    verify { mainStyleLoadedListener.onStyleLoaded(any()) }
  }

  /**
   * Verifies if the multiple user provided OnStyleLoaded are called when style loading finishes
   */
  @Test
  fun onStyleLoadSuccessMulti() {
    val styleObserver = StyleObserver(
      nativeMapWeakRef = WeakReference(mockk(relaxed = true)),
      styleLoadedListener = mockk(relaxed = true),
      nativeObserver = mockk(relaxed = true),
      pixelRatio = 1.0f
    )
    val loadStyleListener = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.setLoadStyleListener(loadStyleListener, null)
    val getStyleListener = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.addGetStyleListener(getStyleListener)
    val getStyleListener2 = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.addGetStyleListener(getStyleListener2)
    styleObserver.onStyleLoaded(mockk())
    verify { loadStyleListener.onStyleLoaded(any()) }
    verify { getStyleListener.onStyleLoaded(any()) }
    verify { getStyleListener2.onStyleLoaded(any()) }
  }

  /**
   * Verifies that loadStyle callback is overwritten with the consecutive call
   */
  @Test
  fun onStyleLoadedOverwritten() {
    val styleObserver = StyleObserver(
      nativeMapWeakRef = WeakReference(mockk(relaxed = true)),
      styleLoadedListener = mockk(relaxed = true),
      nativeObserver = mockk(relaxed = true),
      pixelRatio = 1.0f
    )
    val styleLoadedFail = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.setLoadStyleListener(styleLoadedFail, null)
    val styleLoadedSuccess = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.setLoadStyleListener(styleLoadedSuccess, null)
    styleObserver.onStyleLoaded(mockk())
    verify(exactly = 0) { styleLoadedFail.onStyleLoaded(any()) }
    verify { styleLoadedSuccess.onStyleLoaded(any()) }
  }

  /**
   * Verifies if the user provided OnMapLoadErrorListener is called when the style has produced an error
   */
  @Test
  fun onStyleLoadError() {
    val styleObserver = StyleObserver(
      nativeMapWeakRef = WeakReference(mockk(relaxed = true)),
      styleLoadedListener = mockk(relaxed = true),
      nativeObserver = mockk(relaxed = true),
      pixelRatio = 1.0f
    )
    val errorListener = mockk<OnMapLoadErrorListener>(relaxed = true)
    styleObserver.setLoadStyleListener(mockk(relaxed = true), errorListener)
    styleObserver.onMapLoadError(mockk(relaxed = true))
    verify { errorListener.onMapLoadError(any()) }
  }

  /**
   * Verifies that loadStyle error callback is overwritten with the consecutive call
   */
  @Test
  fun onStyleLoadErrorNotCalled() {
    val styleObserver = StyleObserver(
      nativeMapWeakRef = WeakReference(mockk(relaxed = true)),
      styleLoadedListener = mockk(relaxed = true),
      nativeObserver = mockk(relaxed = true),
      pixelRatio = 1.0f
    )
    val errorListenerFail = mockk<OnMapLoadErrorListener>(relaxed = true)
    styleObserver.setLoadStyleListener(mockk(relaxed = true), errorListenerFail)
    val errorListenerSuccess = mockk<OnMapLoadErrorListener>(relaxed = true)
    styleObserver.setLoadStyleListener(mockk(relaxed = true), errorListenerSuccess)
    styleObserver.onMapLoadError(mockk(relaxed = true))
    verify(exactly = 0) { errorListenerFail.onMapLoadError(any()) }
    verify { errorListenerSuccess.onMapLoadError(any()) }
  }
}
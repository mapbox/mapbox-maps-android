package com.mapbox.maps

import com.mapbox.maps.extension.observable.eventdata.StyleDataLoadedEventData
import com.mapbox.maps.extension.observable.model.StyleDataType
import com.mapbox.maps.plugin.delegates.listeners.OnMapLoadErrorListener
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class StyleObserverTest {

  @Before
  fun setUp() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logE(any(), any()) } just Runs
  }

  @After
  fun cleanUp() {
    unmockkStatic("com.mapbox.maps.MapboxLogger")
  }

  /**
   * Verifies if the correct listeners are attached to NativeMapObserver when StyleObserver is created
   */
  @Test
  fun onStyleObserverCreate() {
    val nativeObserver = mockk<NativeObserver>(relaxed = true)
    StyleObserver(mockk(), mockk(relaxed = true), nativeObserver, 1.0f)
    verify { nativeObserver.addOnStyleLoadedListener(any()) }
    verify { nativeObserver.addOnMapLoadErrorListener(any()) }
    verify { nativeObserver.addOnStyleDataLoadedListener(any()) }
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
    verify { nativeObserver.removeOnStyleDataLoadedListener(any()) }
  }

  /**
   * Verifies if the user provided OnStyleLoaded is called when style loading finishes.
   * Additionally verify we don't apply transition options if they were not specified.
   */
  @Test
  fun onStyleLoadSuccess() {
    val mainStyleLoadedListener = mockk<Style.OnStyleLoaded>(relaxed = true)
    val nativeMap = mockk<MapInterface>(relaxed = true)
    val styleObserver = StyleObserver(
      nativeMap = nativeMap,
      styleLoadedListener = mainStyleLoadedListener,
      nativeObserver = mockk(relaxed = true),
      pixelRatio = 1.0f
    )
    val styleLoaded = mockk<Style.OnStyleLoaded>(relaxed = true)
    val styleDataLoaded = mockk<StyleDataLoadedEventData>(relaxed = true)
    every { styleDataLoaded.type } returns StyleDataType.STYLE
    styleObserver.setLoadStyleListener(null, styleLoaded, null)
    styleObserver.onStyleLoaded(mockk())
    styleObserver.onStyleDataLoaded(styleDataLoaded)
    verify(exactly = 1) { styleLoaded.onStyleLoaded(any()) }
    verify(exactly = 1) { mainStyleLoadedListener.onStyleLoaded(any()) }
    verify(exactly = 0) { nativeMap.styleTransition = any() }
  }

  /**
   * Verifies if the multiple user provided OnStyleLoaded are called when style loading finishes
   */
  @Test
  fun onStyleLoadSuccessMulti() {
    val styleObserver = StyleObserver(
      nativeMap = mockk(relaxed = true),
      styleLoadedListener = mockk(relaxed = true),
      nativeObserver = mockk(relaxed = true),
      pixelRatio = 1.0f
    )
    val loadStyleListener = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.setLoadStyleListener(null, loadStyleListener, null)
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
      nativeMap = mockk(relaxed = true),
      styleLoadedListener = mockk(relaxed = true),
      nativeObserver = mockk(relaxed = true),
      pixelRatio = 1.0f
    )
    val styleLoadedFail = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.setLoadStyleListener(null, styleLoadedFail, null)
    val styleLoadedSuccess = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.setLoadStyleListener(null, styleLoadedSuccess, null)
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
      nativeMap = mockk(relaxed = true),
      styleLoadedListener = mockk(relaxed = true),
      nativeObserver = mockk(relaxed = true),
      pixelRatio = 1.0f
    )
    val errorListener = mockk<OnMapLoadErrorListener>(relaxed = true)
    styleObserver.setLoadStyleListener(null, mockk(relaxed = true), errorListener)
    styleObserver.onMapLoadError(mockk(relaxed = true))
    verify { errorListener.onMapLoadError(any()) }
  }

  /**
   * Verifies that loadStyle error callback is overwritten with the consecutive call
   */
  @Test
  fun onStyleLoadErrorNotCalled() {
    val styleObserver = StyleObserver(
      nativeMap = mockk(relaxed = true),
      styleLoadedListener = mockk(relaxed = true),
      nativeObserver = mockk(relaxed = true),
      pixelRatio = 1.0f
    )
    val errorListenerFail = mockk<OnMapLoadErrorListener>(relaxed = true)
    styleObserver.setLoadStyleListener(null, mockk(relaxed = true), errorListenerFail)
    val errorListenerSuccess = mockk<OnMapLoadErrorListener>(relaxed = true)
    styleObserver.setLoadStyleListener(null, mockk(relaxed = true), errorListenerSuccess)
    styleObserver.onMapLoadError(mockk(relaxed = true))
    verify(exactly = 0) { errorListenerFail.onMapLoadError(any()) }
    verify { errorListenerSuccess.onMapLoadError(any()) }
  }

  /**
   * Verify we trigger core set style transition when callback is triggered.
   */
  @Test
  fun onStyleDataLoadedCustomTransitionOptions() {
    val nativeMap = mockk<MapInterface>(relaxed = true)
    val styleObserver = StyleObserver(
      nativeMap = nativeMap,
      styleLoadedListener = mockk(relaxUnitFun = true),
      nativeObserver = mockk(relaxed = true),
      pixelRatio = 1.0f
    )
    val transitionOptions = mockk<TransitionOptions>()
    styleObserver.setLoadStyleListener(transitionOptions, mockk(), null)
    val styleDataLoaded = mockk<StyleDataLoadedEventData>()
    every { styleDataLoaded.type } returns StyleDataType.STYLE
    styleObserver.onStyleDataLoaded(styleDataLoaded)
    // verify we do call native method after style
    verify(exactly = 1) { nativeMap.styleTransition = transitionOptions }
    every { styleDataLoaded.type } returns StyleDataType.SOURCES
    styleObserver.onStyleDataLoaded(styleDataLoaded)
    every { styleDataLoaded.type } returns StyleDataType.SPRITE
    styleObserver.onStyleDataLoaded(styleDataLoaded)
    // verify no more calls did happen (meaning there should be one method call)
    verify(exactly = 1) { nativeMap.styleTransition = transitionOptions }
  }
}
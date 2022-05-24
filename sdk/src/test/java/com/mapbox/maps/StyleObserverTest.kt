package com.mapbox.maps

import com.mapbox.maps.extension.observable.eventdata.StyleDataLoadedEventData
import com.mapbox.maps.extension.observable.model.StyleDataType
import com.mapbox.maps.plugin.delegates.listeners.OnMapLoadErrorListener
import com.mapbox.verifyNo
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class StyleObserverTest {

  private lateinit var mainStyleLoadedListener: Style.OnStyleLoaded
  private lateinit var styleObserver: StyleObserver

  @Before
  fun setUp() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logE(any(), any()) } just Runs

    mainStyleLoadedListener = mockk(relaxed = true)
    styleObserver = StyleObserver(
      nativeMap = mockk(relaxUnitFun = true),
      styleLoadedListener = mainStyleLoadedListener,
      nativeObserver = mockk(relaxUnitFun = true),
      pixelRatio = 1.0f
    )
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
    val styleLoaded = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.setLoadStyleListener(styleLoaded, null, null, null, null)
    styleObserver.onStyleDataLoaded(StyleDataLoadedEventData(0, 0, StyleDataType.STYLE)) // needed to initialize style internally
    styleObserver.onStyleLoaded(mockk())
    verify { styleLoaded.onStyleLoaded(any()) }
    verify { mainStyleLoadedListener.onStyleLoaded(any()) }
  }

  /**
   * Verifies if the multiple user provided OnStyleLoaded are called when style loading finishes
   */
  @Test
  fun onStyleLoadSuccessMulti() {
    val userLoadStyleListener = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.setLoadStyleListener(
      userLoadStyleListener,
      null,
      null,
      null,
      null
    )
    val getStyleListener = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.addGetStyleListener(getStyleListener)
    val getStyleListener2 = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.addGetStyleListener(getStyleListener2)
    styleObserver.onStyleDataLoaded(StyleDataLoadedEventData(0, 0, StyleDataType.STYLE)) // needed to initialize style internally
    styleObserver.onStyleLoaded(mockk())
    verify { userLoadStyleListener.onStyleLoaded(any()) }
    verify { getStyleListener.onStyleLoaded(any()) }
    verify { getStyleListener2.onStyleLoaded(any()) }
  }

  /**
   * Verifies that loadStyle callback is overwritten with the consecutive call
   */
  @Test
  fun onStyleLoadedOverwritten() {
    val styleLoadedFail = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.setLoadStyleListener(styleLoadedFail, null, null, null, null)
    val styleLoadedSuccess = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.setLoadStyleListener(styleLoadedSuccess, null, null, null, null)
    styleObserver.onStyleDataLoaded(StyleDataLoadedEventData(0, 0, StyleDataType.STYLE)) // needed to initialize style internally
    styleObserver.onStyleLoaded(mockk())
    verify(exactly = 0) { styleLoadedFail.onStyleLoaded(any()) }
    verify { styleLoadedSuccess.onStyleLoaded(any()) }
  }

  /**
   * Verifies if the user provided OnMapLoadErrorListener is called when the style has produced an error
   */
  @Test
  fun onStyleLoadError() {
    val errorListener = mockk<OnMapLoadErrorListener>(relaxed = true)
    styleObserver.setLoadStyleListener(null, null, null, null, errorListener)
    styleObserver.onMapLoadError(mockk(relaxed = true))
    verify { errorListener.onMapLoadError(any()) }
  }

  /**
   * Verifies that loadStyle error callback is overwritten with the consecutive call
   */
  @Test
  fun onStyleLoadErrorNotCalled() {
    val errorListenerFail = mockk<OnMapLoadErrorListener>(relaxed = true)
    styleObserver.setLoadStyleListener(null, null, null, null, errorListenerFail)
    val errorListenerSuccess = mockk<OnMapLoadErrorListener>(relaxed = true)
    styleObserver.setLoadStyleListener(null, null, null, null, errorListenerSuccess)
    styleObserver.onMapLoadError(mockk(relaxed = true))
    verify(exactly = 0) { errorListenerFail.onMapLoadError(any()) }
    verify { errorListenerSuccess.onMapLoadError(any()) }
  }

  @Test
  fun onStyleDataLoadedNotifiesMapboxMap() {
    val styleCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    val styleSpritesCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    val styleSourcesCallback = mockk<Style.OnStyleLoaded>(relaxed = true)

    styleObserver.setLoadStyleListener(
      null,
      styleCallback,
      styleSpritesCallback,
      styleSourcesCallback,
      null
    )

    styleObserver.onStyleDataLoaded(StyleDataLoadedEventData(0, 0, StyleDataType.STYLE))

    verify { styleCallback.onStyleLoaded(any()) }
    verifyNo { styleSpritesCallback.onStyleLoaded(any()) }
    verifyNo { styleSourcesCallback.onStyleLoaded(any()) }
  }

  @Test
  fun onStyleDataSpritesLoadedNotifiesMapboxMap() {
    val styleCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    val styleSpritesCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    val styleSourcesCallback = mockk<Style.OnStyleLoaded>(relaxed = true)

    styleObserver.setLoadStyleListener(
      null,
      styleCallback,
      styleSpritesCallback,
      styleSourcesCallback,
      null
    )

    // STYLE event arrives first and initializes Style object internally
    styleObserver.onStyleDataLoaded(StyleDataLoadedEventData(0, 0, StyleDataType.STYLE))

    verify { styleCallback.onStyleLoaded(any()) }
    verifyNo { styleSpritesCallback.onStyleLoaded(any()) }

    styleObserver.onStyleDataLoaded(StyleDataLoadedEventData(0, 0, StyleDataType.SPRITE))

    verify { styleSpritesCallback.onStyleLoaded(any()) }
    verifyNo { styleSourcesCallback.onStyleLoaded(any()) }
  }

  @Test
  fun onStyleDataSourcesLoadedNotifiesMapboxMap() {
    val styleCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    val styleSpritesCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    val styleSourcesCallback = mockk<Style.OnStyleLoaded>(relaxed = true)

    styleObserver.setLoadStyleListener(
      null,
      styleCallback,
      styleSpritesCallback,
      styleSourcesCallback,
      null
    )

    // STYLE event arrives first and initializes Style object internally
    styleObserver.onStyleDataLoaded(StyleDataLoadedEventData(0, 0, StyleDataType.STYLE))

    verifyNo { styleSourcesCallback.onStyleLoaded(any()) }

    styleObserver.onStyleDataLoaded(StyleDataLoadedEventData(0, 0, StyleDataType.SOURCES))

    verify { styleSourcesCallback.onStyleLoaded(any()) }
    verify { styleCallback.onStyleLoaded(any()) }
    verifyNo { styleSpritesCallback.onStyleLoaded(any()) }
  }

  @Test
  fun onStyleDataOverwritten() {
    val styleNotCalled = mockk<Style.OnStyleLoaded>(relaxed = true)
    val spritesNotCalled = mockk<Style.OnStyleLoaded>(relaxed = true)
    val sourcesNotCalled = mockk<Style.OnStyleLoaded>(relaxed = true)

    val styleCalled = mockk<Style.OnStyleLoaded>(relaxed = true)
    val spritesCalled = mockk<Style.OnStyleLoaded>(relaxed = true)
    val sourcesCalled = mockk<Style.OnStyleLoaded>(relaxed = true)

    styleObserver.setLoadStyleListener(null, styleNotCalled, spritesNotCalled, sourcesNotCalled, null)
    styleObserver.setLoadStyleListener(null, styleCalled, spritesCalled, sourcesCalled, null)

    styleObserver.onStyleDataLoaded(StyleDataLoadedEventData(0, 0, StyleDataType.STYLE))
    styleObserver.onStyleDataLoaded(StyleDataLoadedEventData(0, 0, StyleDataType.SOURCES))
    styleObserver.onStyleDataLoaded(StyleDataLoadedEventData(0, 0, StyleDataType.SPRITE))

    verifyNo { styleNotCalled.onStyleLoaded(any()) }
    verifyNo { spritesNotCalled.onStyleLoaded(any()) }
    verifyNo { sourcesNotCalled.onStyleLoaded(any()) }

    verify { styleCalled.onStyleLoaded(any()) }
    verify { spritesCalled.onStyleLoaded(any()) }
    verify { sourcesCalled.onStyleLoaded(any()) }
  }

  @Test
  fun onStyleDataSourcesThrowsIfNoStyleData() {
    val styleCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    val styleSpritesCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    val styleSourcesCallback = mockk<Style.OnStyleLoaded>(relaxed = true)

    styleObserver.setLoadStyleListener(
      null,
      styleCallback,
      styleSpritesCallback,
      styleSourcesCallback,
      null
    )

    assertThrows(MapboxMapException::class.java) {
      styleObserver.onStyleDataLoaded(StyleDataLoadedEventData(0, 0, StyleDataType.SOURCES))
    }
  }

  @Test
  fun onStyleDataSpritesThrowsIfNoStyleData() {
    val styleCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    val styleSpritesCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    val styleSourcesCallback = mockk<Style.OnStyleLoaded>(relaxed = true)

    styleObserver.setLoadStyleListener(
      null,
      styleCallback,
      styleSpritesCallback,
      styleSourcesCallback,
      null
    )

    assertThrows(MapboxMapException::class.java) {
      styleObserver.onStyleDataLoaded(StyleDataLoadedEventData(0, 0, StyleDataType.SPRITE))
    }
  }

  @Test
  fun onStyleLoadedThrowsIfNoStyleData() {
    val styleCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    val styleSpritesCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    val styleSourcesCallback = mockk<Style.OnStyleLoaded>(relaxed = true)

    styleObserver.setLoadStyleListener(
      null,
      styleCallback,
      styleSpritesCallback,
      styleSourcesCallback,
      null
    )

    assertThrows(MapboxMapException::class.java) {
      styleObserver.onStyleLoaded(mockk())
    }
  }

  @Test
  fun onStyleLoadedCallsSpritesListenerIfNoStyleDataSpritesLoaded() {
    val styleCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    val styleSpritesCallback = mockk<Style.OnStyleLoaded>(relaxed = true)

    styleObserver.setLoadStyleListener(
      null,
      styleCallback,
      styleSpritesCallback,
      null,
      null
    )

    styleObserver.onStyleDataLoaded(StyleDataLoadedEventData(0, 0, StyleDataType.STYLE))
    styleObserver.onStyleDataLoaded(StyleDataLoadedEventData(0, 0, StyleDataType.SOURCES))

    verifyNo { styleSpritesCallback.onStyleLoaded(any()) }

    styleObserver.onStyleLoaded(mockk())

    verify { styleSpritesCallback.onStyleLoaded(any()) }
  }

  @Test
  fun onStyleLoadedCallsSourcesListenerIfNoStyleDataSourcesLoaded() {
    val styleCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    val styleSourcesCallback = mockk<Style.OnStyleLoaded>(relaxed = true)

    styleObserver.setLoadStyleListener(
      null,
      styleCallback,
      null,
      styleSourcesCallback,
      null
    )

    styleObserver.onStyleDataLoaded(StyleDataLoadedEventData(0, 0, StyleDataType.STYLE))
    styleObserver.onStyleDataLoaded(StyleDataLoadedEventData(0, 0, StyleDataType.SPRITE))

    verifyNo { styleSourcesCallback.onStyleLoaded(any()) }

    styleObserver.onStyleLoaded(mockk())

    verify { styleSourcesCallback.onStyleLoaded(any()) }
  }
}
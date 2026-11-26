package com.mapbox.maps

import com.mapbox.common.Cancelable
import com.mapbox.verifyNo
import com.mapbox.verifyOnce
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertSame
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.Date

@RunWith(RobolectricTestRunner::class)
class StyleObserverTest {

  private lateinit var mainStyleLoadedListener: Style.OnStyleLoaded
  private lateinit var styleObserver: StyleObserver
  private lateinit var nativeObserver: NativeObserver
  private val cancelable = mockk<Cancelable>(relaxUnitFun = true)

  @Before
  fun setUp() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    nativeObserver = mockk(relaxUnitFun = true)
    every { nativeObserver.subscribeStyleLoaded(any()) } returns cancelable
    every { nativeObserver.subscribeStyleDataLoaded(any()) } returns cancelable
    every { nativeObserver.subscribeMapLoadingError(any()) } returns cancelable
    every { cancelable.cancel() } just Runs
    every { logE(any(), any()) } just Runs
    mainStyleLoadedListener = mockk(relaxed = true)
    styleObserver = StyleObserver(
      styleManager = mockk(relaxUnitFun = true),
      styleLoadedListener = mainStyleLoadedListener,
      nativeObserver = nativeObserver,
      pixelRatio = 1.0f,
      mockk()
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
    StyleObserver(mockk(), mockk(relaxed = true), nativeObserver, 1.0f, mockk())
    verify { nativeObserver.subscribeStyleLoaded(any()) }
    verify { nativeObserver.subscribeMapLoadingError(any()) }
    verify { nativeObserver.subscribeStyleDataLoaded(any()) }
  }

  /**
   * Verifies if the correct listeners are detached to NativeMapObserver when StyleObserver is destroyed
   */
  @Test
  fun onStyleObserverDestroy() {
    val styleObserver = StyleObserver(mockk(), mockk(relaxed = true), nativeObserver, 1.0f, mockk())
    styleObserver.onDestroy()
    assertTrue(styleObserver.cancelableList.isEmpty())
  }

  /**
   * Verifies if the user provided OnStyleLoaded is called when style loading finishes.
   * Additionally verify we don't apply transition options if they were not specified.
   */
  @Test
  fun onStyleLoadSuccess() {
    val styleLoaded = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.setLoadStyleListener(styleLoaded, mockk(relaxed = true), null, null)
    styleObserver.run(
      StyleDataLoaded(
        StyleDataLoadedType.STYLE,
        EventTimeInterval(
          Date(0), Date(0)
        )
      )
    ) // needed to initialize style internally
    styleObserver.run(mockk<StyleLoaded>())
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
      mockk(relaxed = true),
      null,
      null,
    )
    val getStyleListener = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.addGetStyleListener(getStyleListener)
    val getStyleListener2 = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.addGetStyleListener(getStyleListener2)
    styleObserver.run(
      StyleDataLoaded(
        StyleDataLoadedType.STYLE,
        EventTimeInterval(
          Date(0), Date(0)
        )
      )
    ) // needed to initialize style internally
    styleObserver.run(mockk<StyleLoaded>())
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
    styleObserver.setLoadStyleListener(styleLoadedFail, mockk(relaxed = true), null, null)
    val styleLoadedSuccess = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.setLoadStyleListener(styleLoadedSuccess, mockk(relaxed = true), null, null)
    styleObserver.run(
      StyleDataLoaded(
        StyleDataLoadedType.STYLE,
        EventTimeInterval(
          Date(0), Date(0)
        )
      )
    ) // needed to initialize style internally
    styleObserver.run(mockk<StyleLoaded>())
    verifyNo { styleLoadedFail.onStyleLoaded(any()) }
    verify { styleLoadedSuccess.onStyleLoaded(any()) }
  }

  @Test
  fun onStyleDataLoadedNotifiesMapboxMap() {
    val styleCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    val styleSpritesCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    val styleSourcesCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    val styleUserCallback = mockk<Style.OnStyleLoaded>(relaxed = true)

    styleObserver.setLoadStyleListener(
      styleUserCallback,
      styleCallback,
      styleSpritesCallback,
      styleSourcesCallback,
    )

    styleObserver.run(
      StyleDataLoaded(
        StyleDataLoadedType.STYLE,
        EventTimeInterval(
          Date(0), Date(0)
        )
      )
    )

    verify { styleCallback.onStyleLoaded(any()) }
    verifyNo { styleSpritesCallback.onStyleLoaded(any()) }
    verifyNo { styleSourcesCallback.onStyleLoaded(any()) }

    verifyNo { styleSpritesCallback.onStyleLoaded(any()) }
    verifyNo { mainStyleLoadedListener.onStyleLoaded(any()) }
  }

  @Test
  fun onStyleDataSpritesLoadedNotifiesMapboxMap() {
    val styleCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    val styleSpritesCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    val styleSourcesCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    val styleUserCallback = mockk<Style.OnStyleLoaded>(relaxed = true)

    styleObserver.setLoadStyleListener(
      styleUserCallback,
      styleCallback,
      styleSpritesCallback,
      styleSourcesCallback,
    )

    // STYLE event arrives first and initializes Style object internally
    styleObserver.run(
      StyleDataLoaded(
        StyleDataLoadedType.STYLE,
        EventTimeInterval(
          Date(0), Date(0)
        )
      )
    )

    verify { styleCallback.onStyleLoaded(any()) }
    verifyNo { styleSpritesCallback.onStyleLoaded(any()) }

    styleObserver.run(
      StyleDataLoaded(
        StyleDataLoadedType.SPRITE,
        EventTimeInterval(
          Date(0), Date(0)
        )
      )
    )

    verify { styleSpritesCallback.onStyleLoaded(any()) }
    verifyNo { styleSourcesCallback.onStyleLoaded(any()) }

    verifyNo { styleUserCallback.onStyleLoaded(any()) }
    verifyNo { mainStyleLoadedListener.onStyleLoaded(any()) }
  }

  @Test
  fun onStyleDataSourcesLoadedNotifiesMapboxMap() {
    val styleCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    val styleSpritesCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    val styleSourcesCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    val styleUserCallback = mockk<Style.OnStyleLoaded>(relaxed = true)

    styleObserver.setLoadStyleListener(
      styleUserCallback,
      styleCallback,
      styleSpritesCallback,
      styleSourcesCallback,
    )

    // STYLE event arrives first and initializes Style object internally
    styleObserver.run(
      StyleDataLoaded(
        StyleDataLoadedType.STYLE,
        EventTimeInterval(
          Date(0), Date(0)
        )
      )
    )

    verifyNo { styleSourcesCallback.onStyleLoaded(any()) }

    styleObserver.run(
      StyleDataLoaded(
        StyleDataLoadedType.SOURCES,
        EventTimeInterval(
          Date(0), Date(0)
        )
      )
    )

    verify { styleSourcesCallback.onStyleLoaded(any()) }
    verify { styleCallback.onStyleLoaded(any()) }
    verifyNo { styleSpritesCallback.onStyleLoaded(any()) }

    verifyNo { styleSpritesCallback.onStyleLoaded(any()) }
    verifyNo { mainStyleLoadedListener.onStyleLoaded(any()) }
  }

  @Test
  fun onStyleDataOverwritten() {
    val styleNotCalled = mockk<Style.OnStyleLoaded>(relaxed = true)
    val spritesNotCalled = mockk<Style.OnStyleLoaded>(relaxed = true)
    val sourcesNotCalled = mockk<Style.OnStyleLoaded>(relaxed = true)

    val styleCalled = mockk<Style.OnStyleLoaded>(relaxed = true)
    val spritesCalled = mockk<Style.OnStyleLoaded>(relaxed = true)
    val sourcesCalled = mockk<Style.OnStyleLoaded>(relaxed = true)

    styleObserver.setLoadStyleListener(
      null,
      styleNotCalled,
      spritesNotCalled,
      sourcesNotCalled,
    )
    styleObserver.setLoadStyleListener(null, styleCalled, spritesCalled, sourcesCalled)

    styleObserver.run(
      StyleDataLoaded(
        StyleDataLoadedType.STYLE,
        EventTimeInterval(
          Date(0), Date(0)
        )
      )
    )
    styleObserver.run(
      StyleDataLoaded(
        StyleDataLoadedType.SOURCES,
        EventTimeInterval(
          Date(0), Date(0)
        )
      )
    )
    styleObserver.run(
      StyleDataLoaded(
        StyleDataLoadedType.SPRITE,
        EventTimeInterval(
          Date(0), Date(0)
        )
      )
    )
    verifyNo { styleNotCalled.onStyleLoaded(any()) }
    verifyNo { spritesNotCalled.onStyleLoaded(any()) }
    verifyNo { sourcesNotCalled.onStyleLoaded(any()) }

    verify { styleCalled.onStyleLoaded(any()) }
    verify { spritesCalled.onStyleLoaded(any()) }
    verify { sourcesCalled.onStyleLoaded(any()) }
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
    )

    assertThrows(MapboxMapException::class.java) {
      styleObserver.run(mockk<StyleLoaded>())
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
    )

    styleObserver.run(
      StyleDataLoaded(
        StyleDataLoadedType.STYLE,
        EventTimeInterval(
          Date(0), Date(0)
        )
      )
    )
    styleObserver.run(
      StyleDataLoaded(
        StyleDataLoadedType.SOURCES,
        EventTimeInterval(
          Date(0), Date(0)
        )
      )
    )

    verifyNo { styleSpritesCallback.onStyleLoaded(any()) }

    styleObserver.run(mockk<StyleLoaded>())

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
    )

    styleObserver.run(
      StyleDataLoaded(
        StyleDataLoadedType.STYLE,
        EventTimeInterval(
          Date(0), Date(0)
        )
      )
    )
    styleObserver.run(
      StyleDataLoaded(
        StyleDataLoadedType.SPRITE,
        EventTimeInterval(
          Date(0), Date(0)
        )
      )
    )

    verifyNo { styleSourcesCallback.onStyleLoaded(any()) }

    styleObserver.run(mockk<StyleLoaded>())

    verify { styleSourcesCallback.onStyleLoaded(any()) }
  }

  @Test
  fun `StyleDataType - Sources and Sprites events are ignored until StyleDataType - Style is not received on style change`() {
    val styleCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    val styleSourcesCallback = mockk<Style.OnStyleLoaded>(relaxed = true)
    val styleSpritesCallback = mockk<Style.OnStyleLoaded>(relaxed = true)

    styleObserver.setLoadStyleListener(
      null,
      styleCallback,
      styleSpritesCallback,
      styleSourcesCallback,
    )

    styleObserver.run(
      StyleDataLoaded(
        StyleDataLoadedType.SOURCES,
        EventTimeInterval(
          Date(0), Date(0)
        )
      )
    )
    styleObserver.run(
      StyleDataLoaded(
        StyleDataLoadedType.SPRITE,
        EventTimeInterval(
          Date(0), Date(0)
        )
      )
    )

    verifyNo { styleSourcesCallback.onStyleLoaded(any()) }
    verifyNo { styleSpritesCallback.onStyleLoaded(any()) }

    styleObserver.run(
      StyleDataLoaded(
        StyleDataLoadedType.STYLE,
        EventTimeInterval(
          Date(0), Date(0)
        )
      )
    )
    styleObserver.run(
      StyleDataLoaded(
        StyleDataLoadedType.SOURCES,
        EventTimeInterval(
          Date(0), Date(0)
        )
      )
    )
    styleObserver.run(
      StyleDataLoaded(
        StyleDataLoadedType.SPRITE,
        EventTimeInterval(
          Date(0), Date(0)
        )
      )
    )

    verifyOnce { styleCallback.onStyleLoaded(any()) }
    verifyOnce { styleSourcesCallback.onStyleLoaded(any()) }
    verifyOnce { styleSpritesCallback.onStyleLoaded(any()) }
  }

  private fun loadStyle() {
    styleObserver.setLoadStyleListener(null, mockk(relaxed = true), null, null)
    styleObserver.run(
      StyleDataLoaded(StyleDataLoadedType.STYLE, EventTimeInterval(Date(0), Date(0)))
    ) // needed to initialize style internally
    styleObserver.run(mockk<StyleLoaded>())
  }

  @Test
  fun getStyleListenerCancelledBeforeLoadIsNotInvoked() {
    val cancelled = mockk<Style.OnStyleLoaded>(relaxed = true)
    val kept = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.addGetStyleListener(cancelled).cancel()
    styleObserver.addGetStyleListener(kept)
    loadStyle()
    verifyNo { cancelled.onStyleLoaded(any()) }
    verifyOnce { kept.onStyleLoaded(any()) }
  }

  @Test
  fun getStyleListenerInvokedOnlyOnce() {
    val listener = mockk<Style.OnStyleLoaded>(relaxed = true)
    val registration = styleObserver.addGetStyleListener(listener)
    loadStyle()
    loadStyle()
    // cancelling after the listener fired is a no-op
    registration.cancel()
    registration.cancel()
    verifyOnce { listener.onStyleLoaded(any()) }
  }

  @Test
  fun getStyleListenerCancelledByEarlierListenerIsNotInvoked() {
    val second = mockk<Style.OnStyleLoaded>(relaxed = true)
    lateinit var secondRegistration: Cancelable
    styleObserver.addGetStyleListener { secondRegistration.cancel() }
    secondRegistration = styleObserver.addGetStyleListener(second)
    loadStyle()
    verifyNo { second.onStyleLoaded(any()) }
  }

  @Test
  fun getStyleCancelAfterInvokedDoesNotRemoveNewerRegistrationOfSameListener() {
    val listener = mockk<Style.OnStyleLoaded>(relaxed = true)
    val firstRegistration = styleObserver.addGetStyleListener(listener)
    loadStyle()
    styleObserver.addGetStyleListener(listener)
    // stale handle from the first, already invoked, registration
    firstRegistration.cancel()
    loadStyle()
    verify(exactly = 2) { listener.onStyleLoaded(any()) }
  }

  @Test
  fun getStyleCancelOfSatisfiedDuplicateDoesNotRemoveNewerRegistrationOfSameListener() {
    val listener = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.addGetStyleListener(listener)
    // duplicate registration while the first one is still pending
    val duplicateRegistration = styleObserver.addGetStyleListener(listener)
    loadStyle()
    styleObserver.addGetStyleListener(listener)
    // stale handle from the duplicate, already satisfied by the first load
    duplicateRegistration.cancel()
    loadStyle()
    verify(exactly = 2) { listener.onStyleLoaded(any()) }
  }

  @Test
  fun sameGetStyleListenerCanBeRegisteredAgainAfterCancel() {
    val listener = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.addGetStyleListener(listener).cancel()
    styleObserver.addGetStyleListener(listener)
    loadStyle()
    verifyOnce { listener.onStyleLoaded(any()) }
  }

  @Test
  fun sameGetStyleListenerRegisteredTwiceIsInvokedOnce() {
    val listener = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.addGetStyleListener(listener)
    styleObserver.addGetStyleListener(listener)
    loadStyle()
    verifyOnce { listener.onStyleLoaded(any()) }
  }

  @Test
  fun sameGetStyleListenerRegisteredTwiceIsRemovedByEitherCancel() {
    val listener = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.addGetStyleListener(listener)
    styleObserver.addGetStyleListener(listener).cancel()
    assertTrue(styleObserver.getStyleListeners.isEmpty())
    loadStyle()
    verifyNo { listener.onStyleLoaded(any()) }
  }

  @Test
  fun getStyleListenerCancelAfterDestroyIsNoOp() {
    val listener = mockk<Style.OnStyleLoaded>(relaxed = true)
    val registration = styleObserver.addGetStyleListener(listener)
    styleObserver.onDestroy()
    assertTrue(styleObserver.getStyleListeners.isEmpty())
    registration.cancel()
    verifyNo { listener.onStyleLoaded(any()) }
  }

  @Test
  fun getStyleListenerCancelRemovesRequest() {
    val registration = styleObserver.addGetStyleListener(mockk(relaxed = true))
    assertEquals(1, styleObserver.getStyleListeners.size)
    registration.cancel()
    assertTrue(styleObserver.getStyleListeners.isEmpty())
  }

  @Test
  fun getStyleListenerInvokedRequestIsRemoved() {
    styleObserver.addGetStyleListener(mockk(relaxed = true))
    loadStyle()
    assertTrue(styleObserver.getStyleListeners.isEmpty())
  }

  @Test
  fun getStyleRequestDropsListenerOnceFinished() {
    val listener = mockk<Style.OnStyleLoaded>(relaxed = true)
    val requests = mutableSetOf<StyleObserver.GetStyleRequest>()
    val fired = StyleObserver.GetStyleRequest(listener, requests)
    val cancelled = StyleObserver.GetStyleRequest(listener, requests)
    val released = StyleObserver.GetStyleRequest(listener, requests)
    val pending = StyleObserver.GetStyleRequest(listener, requests)
    val hash = pending.hashCode()
    assertEquals(pending, fired)
    assertEquals(pending, cancelled)
    assertEquals(pending, released)

    fired.onStyleLoaded(mockk())
    cancelled.cancel()
    released.release()

    // a finished request no longer references the listener, so it only equals itself
    listOf(fired, cancelled, released).forEach { finished ->
      assertNotEquals(pending, finished)
      assertNotEquals(finished, pending)
      assertEquals(finished, finished)
      assertEquals(hash, finished.hashCode())
    }
    verifyOnce { listener.onStyleLoaded(any()) }
  }

  @Test
  fun getStyleListenerAddedAfterDestroyIsNotRegistered() {
    val listener = mockk<Style.OnStyleLoaded>(relaxed = true)
    styleObserver.onDestroy()
    val registration = styleObserver.addGetStyleListener(listener)
    assertSame(EmptyCancelable, registration)
    assertTrue(styleObserver.getStyleListeners.isEmpty())
    verifyNo { listener.onStyleLoaded(any()) }
  }
}
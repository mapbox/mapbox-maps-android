package com.mapbox.maps

import com.mapbox.common.Cancelable
import com.mapbox.maps.coroutine.cameraChangedEvents
import com.mapbox.maps.coroutine.mapIdleEvents
import com.mapbox.maps.coroutine.mapLoadedEvents
import com.mapbox.maps.coroutine.mapLoadingErrorEvents
import com.mapbox.maps.coroutine.renderFrameFinishedEvents
import com.mapbox.maps.coroutine.renderFrameStartedEvents
import com.mapbox.maps.coroutine.resourceRequestEvents
import com.mapbox.maps.coroutine.sourceAddedEvents
import com.mapbox.maps.coroutine.sourceDataLoadedEvents
import com.mapbox.maps.coroutine.sourceRemovedEvents
import com.mapbox.maps.coroutine.styleDataLoadedEvents
import com.mapbox.maps.coroutine.styleImageMissingEvents
import com.mapbox.maps.coroutine.styleImageRemoveUnusedEvents
import com.mapbox.maps.coroutine.styleLoadedEvents
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
@Config(shadows = [ShadowMap::class])
class MapboxMapExtTest {

  private lateinit var nativeObserver: NativeObserver
  private val cancelable = mockk<Cancelable>(relaxUnitFun = true)
  private val nativeMap: NativeMapImpl = mockk(relaxed = true)

  private lateinit var styleObserver: StyleObserver
  private lateinit var mapboxMap: MapboxMap

  @Before
  fun setUp() {
    nativeObserver = mockk()
    every { cancelable.cancel() } just runs
    styleObserver = mockk(relaxUnitFun = true)
    mapboxMap = MapboxMap(nativeMap, nativeObserver, styleObserver)
  }

  @Test
  fun mapLoadedEvents() = runTest {
    collect(NativeObserver::subscribeMapLoaded, MapboxMap::mapLoadedEvents, MapLoadedCallback::run)
  }

  @Test
  fun mapLoadingErrorEvents() = runTest {
    collect(
      NativeObserver::subscribeMapLoadingError,
      MapboxMap::mapLoadingErrorEvents,
      MapLoadingErrorCallback::run
    )
  }

  @Test
  fun styleLoadedEvents() = runTest {
    collect(
      NativeObserver::subscribeStyleLoaded,
      MapboxMap::styleLoadedEvents,
      StyleLoadedCallback::run
    )
  }

  @Test
  fun styleDataLoadedEvents() = runTest {
    collect(
      NativeObserver::subscribeStyleDataLoaded,
      MapboxMap::styleDataLoadedEvents,
      StyleDataLoadedCallback::run
    )
  }

  @Test
  fun cameraChangedEvents() = runTest {
    collect(
      NativeObserver::subscribeCameraChanged,
      MapboxMap::cameraChangedEvents,
      CameraChangedCallback::run
    )
  }

  @Test
  fun mapIdleEvents() = runTest {
    collect(NativeObserver::subscribeMapIdle, MapboxMap::mapIdleEvents, MapIdleCallback::run)
  }

  @Test
  fun sourceAddedEvents() = runTest {
    collect(
      NativeObserver::subscribeSourceAdded,
      MapboxMap::sourceAddedEvents,
      SourceAddedCallback::run
    )
  }

  @Test
  fun sourceRemovedEvents() = runTest {
    collect(
      NativeObserver::subscribeSourceRemoved,
      MapboxMap::sourceRemovedEvents,
      SourceRemovedCallback::run
    )
  }

  @Test
  fun sourceDataLoadedEvents() = runTest {
    collect(
      NativeObserver::subscribeSourceDataLoaded,
      MapboxMap::sourceDataLoadedEvents,
      SourceDataLoadedCallback::run
    )
  }

  @Test
  fun styleImageMissingEvents() = runTest {
    collect(
      NativeObserver::subscribeStyleImageMissing,
      MapboxMap::styleImageMissingEvents,
      StyleImageMissingCallback::run
    )
  }

  @Test
  fun styleImageRemoveUnusedEvents() = runTest {
    collect(
      NativeObserver::subscribeStyleImageRemoveUnused,
      MapboxMap::styleImageRemoveUnusedEvents,
      StyleImageRemoveUnusedCallback::run
    )
  }

  @Test
  fun renderFrameStartedEvents() = runTest {
    collect(
      NativeObserver::subscribeRenderFrameStarted,
      MapboxMap::renderFrameStartedEvents,
      RenderFrameStartedCallback::run
    )
  }

  @Test
  fun renderFrameFinishedEvents() = runTest {
    collect(
      NativeObserver::subscribeRenderFrameFinished,
      MapboxMap::renderFrameFinishedEvents,
      RenderFrameFinishedCallback::run
    )
  }

  @Test
  fun resourceRequestEvents() = runTest {
    collect(
      NativeObserver::subscribeResourceRequest,
      MapboxMap::resourceRequestEvents,
      ResourceRequestCallback::run
    )
  }

  private inline fun <reified EventCallback : Any, reified EventData> collect(
    crossinline subscribe: NativeObserver.(EventCallback, (() -> Unit)?) -> Cancelable,
    crossinline getFlow: MapboxMap.() -> Flow<EventData>,
    crossinline callbackRun: EventCallback.(EventData) -> Unit
  ) = runTest {
    val styleDataLoadedCallbackSlot: CapturingSlot<EventCallback> = CapturingSlot()
    val onCancelSlot: CapturingSlot<() -> Unit> = slot()
    every {
      nativeObserver.subscribe(
        capture(styleDataLoadedCallbackSlot),
        capture(onCancelSlot)
      )
    } returns cancelable

    val values = mutableListOf<EventData>()
    backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
      mapboxMap.getFlow().toList(values)
    }
    val styleDataLoaded: EventData = mockk()
    styleDataLoadedCallbackSlot.captured.callbackRun(styleDataLoaded)
    styleDataLoadedCallbackSlot.captured.callbackRun(styleDataLoaded)
    val styleDataLoaded2: EventData = mockk()
    styleDataLoadedCallbackSlot.captured.callbackRun(styleDataLoaded2)
    onCancelSlot.captured.invoke()

    assertEquals(3, values.size)
    assertSame(styleDataLoaded, values[0])
    assertSame(styleDataLoaded, values[1])
    assertSame(styleDataLoaded2, values[2])
  }
}
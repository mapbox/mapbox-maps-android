package com.mapbox.maps

import com.mapbox.common.Cancelable
import com.mapbox.maps.coroutine.cameraChangedEvents
import com.mapbox.maps.coroutine.genericEvents
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
    testEventsFlow(NativeObserver::subscribeMapLoaded, MapboxMap::mapLoadedEvents, MapLoadedCallback::run)
  }

  @Test
  fun mapLoadingErrorEvents() = runTest {
    testEventsFlow(
      NativeObserver::subscribeMapLoadingError,
      MapboxMap::mapLoadingErrorEvents,
      MapLoadingErrorCallback::run
    )
  }

  @Test
  fun styleLoadedEvents() = runTest {
    testEventsFlow(
      NativeObserver::subscribeStyleLoaded,
      MapboxMap::styleLoadedEvents,
      StyleLoadedCallback::run
    )
  }

  @Test
  fun styleDataLoadedEvents() = runTest {
    testEventsFlow(
      NativeObserver::subscribeStyleDataLoaded,
      MapboxMap::styleDataLoadedEvents,
      StyleDataLoadedCallback::run
    )
  }

  @Test
  fun cameraChangedEvents() = runTest {
    testEventsFlow(
      NativeObserver::subscribeCameraChanged,
      MapboxMap::cameraChangedEvents,
      CameraChangedCallback::run
    )
  }

  @Test
  fun mapIdleEvents() = runTest {
    testEventsFlow(NativeObserver::subscribeMapIdle, MapboxMap::mapIdleEvents, MapIdleCallback::run)
  }

  @Test
  fun sourceAddedEvents() = runTest {
    testEventsFlow(
      NativeObserver::subscribeSourceAdded,
      MapboxMap::sourceAddedEvents,
      SourceAddedCallback::run
    )
  }

  @Test
  fun sourceRemovedEvents() = runTest {
    testEventsFlow(
      NativeObserver::subscribeSourceRemoved,
      MapboxMap::sourceRemovedEvents,
      SourceRemovedCallback::run
    )
  }

  @Test
  fun sourceDataLoadedEvents() = runTest {
    testEventsFlow(
      NativeObserver::subscribeSourceDataLoaded,
      MapboxMap::sourceDataLoadedEvents,
      SourceDataLoadedCallback::run
    )
  }

  @Test
  fun styleImageMissingEvents() = runTest {
    testEventsFlow(
      NativeObserver::subscribeStyleImageMissing,
      MapboxMap::styleImageMissingEvents,
      StyleImageMissingCallback::run
    )
  }

  @Test
  fun styleImageRemoveUnusedEvents() = runTest {
    testEventsFlow(
      NativeObserver::subscribeStyleImageRemoveUnused,
      MapboxMap::styleImageRemoveUnusedEvents,
      StyleImageRemoveUnusedCallback::run
    )
  }

  @Test
  fun renderFrameStartedEvents() = runTest {
    testEventsFlow(
      NativeObserver::subscribeRenderFrameStarted,
      MapboxMap::renderFrameStartedEvents,
      RenderFrameStartedCallback::run
    )
  }

  @Test
  fun renderFrameFinishedEvents() = runTest {
    testEventsFlow(
      NativeObserver::subscribeRenderFrameFinished,
      MapboxMap::renderFrameFinishedEvents,
      RenderFrameFinishedCallback::run
    )
  }

  @Test
  fun resourceRequestEvents() = runTest {
    val callbackSlot: CapturingSlot<ResourceRequestCallback> = CapturingSlot()
    val onCancelSlot: CapturingSlot<() -> Unit> = slot()
    every {
      nativeObserver.subscribeResourceRequest(
        capture(callbackSlot),
        capture(onCancelSlot),
      )
    } returns cancelable

    val values = mutableListOf<ResourceRequest>()
    backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
      mapboxMap.resourceRequestEvents.toList(values)
    }
    val event: ResourceRequest = mockk()
    callbackSlot.captured.run(event)
    callbackSlot.captured.run(event)
    val event2: ResourceRequest = mockk()
    callbackSlot.captured.run(event2)
    onCancelSlot.captured.invoke()

    assertEquals(3, values.size)
    assertSame(event, values[0])
    assertSame(event, values[1])
    assertSame(event2, values[2])
  }

  @Test
  fun genericEvents() = runTest {
    val eventName = "test-event"
    val callbackSlot: CapturingSlot<GenericEventCallback> = CapturingSlot()
    val onCancelSlot = mutableListOf<(() -> Unit)?>()
    every {
      nativeObserver.subscribeGenericEvent(
        eventName,
        capture(callbackSlot),
        captureNullable(onCancelSlot),
      )
    } returns cancelable

    val values = mutableListOf<GenericEvent>()
    backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
      mapboxMap.genericEvents(eventName).toList(values)
    }
    val genericEvent: GenericEvent = mockk()
    callbackSlot.captured.run(genericEvent)
    callbackSlot.captured.run(genericEvent)
    val genericEvent2: GenericEvent = mockk()
    callbackSlot.captured.run(genericEvent2)
    onCancelSlot.first()!!.invoke()

    assertEquals(3, values.size)
    assertSame(genericEvent, values[0])
    assertSame(genericEvent, values[1])
    assertSame(genericEvent2, values[2])
  }

  private inline fun <reified EventCallback : Any, reified EventListener : Any, reified EventData> testEventsFlow(
    crossinline subscribe: NativeObserver.(EventCallback, (() -> Unit)?, EventListener?) -> Cancelable,
    crossinline getFlow: MapboxMap.() -> Flow<EventData>,
    crossinline callbackRun: EventCallback.(EventData) -> Unit
  ) = runTest {
    val callbackSlot: CapturingSlot<EventCallback> = CapturingSlot()
    val listenerSlot = mutableListOf<EventListener?>()
    val onCancelSlot = slot<() -> Unit>()
    every {
      nativeObserver.subscribe(
        capture(callbackSlot),
        capture(onCancelSlot),
        captureNullable(listenerSlot),
      )
    } returns cancelable

    val values = mutableListOf<EventData>()
    backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
      mapboxMap.getFlow().toList(values)
    }
    val styleDataLoaded: EventData = mockk()
    callbackSlot.captured.callbackRun(styleDataLoaded)
    callbackSlot.captured.callbackRun(styleDataLoaded)
    val styleDataLoaded2: EventData = mockk()
    callbackSlot.captured.callbackRun(styleDataLoaded2)
    onCancelSlot.captured.invoke()

    assertEquals(3, values.size)
    assertSame(styleDataLoaded, values[0])
    assertSame(styleDataLoaded, values[1])
    assertSame(styleDataLoaded2, values[2])
  }
}
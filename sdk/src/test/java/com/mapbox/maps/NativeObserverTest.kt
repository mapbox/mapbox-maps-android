package com.mapbox.maps

import com.mapbox.common.Cancelable
import com.mapbox.maps.extension.observable.eventdata.RenderFrameStartedEventData
import com.mapbox.maps.plugin.delegates.listeners.*
import com.mapbox.maps.shadows.ShadowCancelable
import com.mapbox.maps.shadows.ShadowObservable
import com.mapbox.verifyNo
import io.mockk.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("DEPRECATION")
@RunWith(RobolectricTestRunner::class)
@Config(
  shadows = [
    ShadowObservable::class,
    ShadowCancelable::class
  ]
)
class NativeObserverTest {

  private val observableInterface = mockk<NativeMapImpl>(relaxed = true)
  private lateinit var nativeObserver: NativeObserver
  private val cancelable = mockk<Cancelable>(relaxUnitFun = true)

  @Before
  fun setUp() {
    nativeObserver = NativeObserver(observableInterface)
    every { cancelable.cancel() } just runs
  }

  @Test
  fun addAndRemoveOnCameraChangeListener() {
    addAndRemoveListener(
      NativeObserver::addOnCameraChangeListener,
      NativeObserver::removeOnCameraChangeListener,
      OnCameraChangeListener::onCameraChanged
    )
  }

  @Test
  fun addAndRemoveOnMapIdleListener() {
    addAndRemoveListener(
      NativeObserver::addOnMapIdleListener,
      NativeObserver::removeOnMapIdleListener,
      OnMapIdleListener::onMapIdle
    )
  }

  @Test
  fun addAndRemoveOnMapLoadErrorListener() {
    addAndRemoveListener(
      NativeObserver::addOnMapLoadErrorListener,
      NativeObserver::removeOnMapLoadErrorListener,
      OnMapLoadErrorListener::onMapLoadError
    )
  }

  @Test
  fun addAndRemoveOnMapLoadedListener() {
    addAndRemoveListener(
      NativeObserver::addOnMapLoadedListener,
      NativeObserver::removeOnMapLoadedListener,
      OnMapLoadedListener::onMapLoaded
    )
  }

  @Test
  fun addAndRemoveOnRenderFrameFinishedListener() {
    addAndRemoveListener(
      NativeObserver::addOnRenderFrameFinishedListener,
      NativeObserver::removeOnRenderFrameFinishedListener,
      OnRenderFrameFinishedListener::onRenderFrameFinished
    )
  }

  @Test
  fun addAndRemoveOnRenderFrameStartedListener() {
    addAndRemoveListener(
      NativeObserver::addOnRenderFrameStartedListener,
      NativeObserver::removeOnRenderFrameStartedListener,
      OnRenderFrameStartedListener::onRenderFrameStarted
    )
  }

  @Test
  fun addAndRemoveOnSourceAddedListener() {
    addAndRemoveListener(
      NativeObserver::addOnSourceAddedListener,
      NativeObserver::removeOnSourceAddedListener,
      OnSourceAddedListener::onSourceAdded
    )
  }

  @Test
  fun addAndRemoveOnSourceDataLoadedListener() {
    addAndRemoveListener(
      NativeObserver::addOnSourceDataLoadedListener,
      NativeObserver::removeOnSourceDataLoadedListener,
      OnSourceDataLoadedListener::onSourceDataLoaded
    )
  }

  @Test
  fun addAndRemoveOnSourceRemovedListener() {
    addAndRemoveListener(
      NativeObserver::addOnSourceRemovedListener,
      NativeObserver::removeOnSourceRemovedListener,
      OnSourceRemovedListener::onSourceRemoved
    )
  }

  @Test
  fun addAndRemoveOnStyleImageMissingListener() {
    addAndRemoveListener(
      NativeObserver::addOnStyleImageMissingListener,
      NativeObserver::removeOnStyleImageMissingListener,
      OnStyleImageMissingListener::onStyleImageMissing
    )
  }

  @Test
  fun addAndRemoveOnStyleImageUnusedListener() {
    addAndRemoveListener(
      NativeObserver::addOnStyleImageUnusedListener,
      NativeObserver::removeOnStyleImageUnusedListener,
      OnStyleImageUnusedListener::onStyleImageUnused
    )
  }

  @Test
  fun addAndRemoveOnStyleLoadedListener() {
    addAndRemoveListener(
      NativeObserver::addOnStyleLoadedListener,
      NativeObserver::removeOnStyleLoadedListener,
      OnStyleLoadedListener::onStyleLoaded,
      NativeObserver::_resubscribableSet
    )
  }

  @Test
  fun addAndRemoveOnStyleDataLoadedListener() {
    addAndRemoveListener(
      NativeObserver::addOnStyleDataLoadedListener,
      NativeObserver::removeOnStyleDataLoadedListener,
      OnStyleDataLoadedListener::onStyleDataLoaded,
      NativeObserver::_resubscribableSet
    )
  }

  /**
   * @param L the listener type (e.g. [OnRenderFrameStartedListener])
   * @param D the listener data type (e.g. [RenderFrameStartedEventData])
   */
  private inline fun <reified L, reified D> addAndRemoveListener(
    addListener: NativeObserver.(L) -> Unit,
    removeListener: NativeObserver.(L) -> Unit,
    crossinline listenerOn: L.(D) -> Unit,
    cancelableSet: NativeObserver.() -> MutableSet<*> = NativeObserver::_cancelableSet,
  ) {
    val listener: L = mockk(relaxUnitFun = true)
    nativeObserver.addListener(listener)
    assertEquals(1, nativeObserver.cancelableSet().size)

    val listener2: L = mockk(relaxUnitFun = true)
    nativeObserver.addListener(listener2)
    assertEquals(2, nativeObserver.cancelableSet().size)

    nativeObserver.removeListener(listener)
    assertEquals(1, nativeObserver.cancelableSet().size)

    nativeObserver.removeListener(listener2)
    assertEquals(0, nativeObserver.cancelableSet().size)

    verifyNo { listener.listenerOn(any()) }
    verifyNo { listener2.listenerOn(any()) }
  }

  // Test for subscribe methods.
  @Test
  fun subscribeCameraChange() {
    subscribe(NativeObserver::subscribeCameraChanged, NativeMapImpl::subscribe)
  }

  @Test
  fun subscribeMapIdleListener() {
    subscribe(NativeObserver::subscribeMapIdle, NativeMapImpl::subscribe)
  }

  @Test
  fun subscribeMapLoadErrorListener() {
    subscribe(NativeObserver::subscribeMapLoadingError, NativeMapImpl::subscribe)
  }

  @Test
  fun subscribeMapLoadedListener() {
    subscribe(NativeObserver::subscribeMapLoaded, NativeMapImpl::subscribe)
  }

  // Render frame events
  @Test
  fun subscribeRenderFrameFinishedListener() {
    subscribe(NativeObserver::subscribeRenderFrameFinished, NativeMapImpl::subscribe)
  }

  @Test
  fun subscribeRenderFrameStartedListener() {
    subscribe(NativeObserver::subscribeRenderFrameStarted, NativeMapImpl::subscribe)
  }

  // Source events
  @Test
  fun subscribeSourceAddedListener() {
    subscribe(NativeObserver::subscribeSourceAdded, NativeMapImpl::subscribe)
  }

  @Test
  fun subscribeSourceDataLoadedListener() {
    subscribe(NativeObserver::subscribeSourceDataLoaded, NativeMapImpl::subscribe)
  }

  @Test
  fun subscribeSourceRemovedListener() {
    subscribe(NativeObserver::subscribeSourceRemoved, NativeMapImpl::subscribe)
  }

  @Test
  fun subscribeResourceRequest() {
    subscribe(NativeObserver::subscribeResourceRequest, NativeMapImpl::subscribe)
  }

  // Style events
  @Test
  fun subscribeStyleLoadedListener() {
    subscribe(NativeObserver::subscribeStyleLoaded, NativeMapImpl::subscribe, NativeObserver::_resubscribableSet)
  }

  @Test
  fun subscribeStyleDataLoaded() {
    subscribe(NativeObserver::subscribeStyleDataLoaded, NativeMapImpl::subscribe, NativeObserver::_resubscribableSet)
  }

  @Test
  fun subscribeStyleImageMissingListener() {
    subscribe(NativeObserver::subscribeStyleImageMissing, NativeMapImpl::subscribe)
  }

  @Test
  fun subscribeStyleImageRemoveUnusedListener() {
    subscribe(NativeObserver::subscribeStyleImageRemoveUnused, NativeMapImpl::subscribe)
  }

  private inline fun <reified C> subscribe(
    subscribe: NativeObserver.(C) -> Cancelable,
    crossinline nativeSubscribe: NativeMapImpl.(C) -> Cancelable,
    cancelableSet: NativeObserver.() -> MutableSet<*> = NativeObserver::_cancelableSet,
  ) {
    val callback: C = mockk()
    val cancelable = nativeObserver.subscribe(callback)
    verify { observableInterface.nativeSubscribe(callback) }
    assertEquals(1, nativeObserver.cancelableSet().size)
    assertSame(cancelable, nativeObserver.cancelableSet().first())

    val callback2: C = mockk()
    val cancelable2 = nativeObserver.subscribe(callback2)
    verify { observableInterface.nativeSubscribe(callback2) }
    assertEquals(2, nativeObserver.cancelableSet().size)
    assertTrue(nativeObserver.cancelableSet().containsAll(listOf(cancelable, cancelable2)))

    cancelable.cancel()
    assertEquals(1, nativeObserver.cancelableSet().size)
    assertSame(cancelable2, nativeObserver.cancelableSet().first())

    cancelable2.cancel()
    assertTrue(nativeObserver.cancelableSet().isEmpty())
  }

  @OptIn(MapboxExperimental::class)
  @Test
  fun subscribeGenericEvent() {
    val event1 = "Event1"
    val event2 = "Event2"
    val callback = mockk<GenericEventCallback>(relaxUnitFun = true)
    val cancelable = nativeObserver.subscribeGenericEvent(event1, callback)
    verify { observableInterface.subscribe(event1, callback) }
    assertEquals(1, nativeObserver._cancelableSet.size)
    assertSame(cancelable, nativeObserver._cancelableSet.first())

    val callback2 = mockk<GenericEventCallback>(relaxUnitFun = true)
    val cancelable2 = nativeObserver.subscribeGenericEvent(event2, callback2)
    verify { observableInterface.subscribe(event2, callback2) }
    assertEquals(2, nativeObserver._cancelableSet.size)
    assertTrue(nativeObserver._cancelableSet.containsAll(listOf(cancelable, cancelable2)))

    cancelable.cancel()
    assertEquals(1, nativeObserver._cancelableSet.size)
    assertSame(cancelable2, nativeObserver._cancelableSet.first())

    cancelable2.cancel()
    assertTrue(nativeObserver._cancelableSet.isEmpty())
  }

  @Test
  fun clearListeners() {
    val onCancel: () -> Unit = mockk(relaxed = true)
    nativeObserver.subscribeCameraChanged(mockk(relaxed = true), onCancel)
    nativeObserver.subscribeStyleDataLoaded(mockk(relaxed = true), onCancel)
    nativeObserver.subscribeStyleLoaded(mockk(relaxed = true), onCancel)
    assertEquals(1, nativeObserver._cancelableSet.size)
    assertEquals(2, nativeObserver._resubscribableSet.size)

    nativeObserver.onDestroy()
    assertTrue(nativeObserver._cancelableSet.isEmpty())
    assertTrue(nativeObserver._resubscribableSet.isEmpty())

    verify(exactly = 3) { onCancel.invoke() }
  }

  @Test
  fun resubscribesStyleLoadedEvents() {
    val listener1 = mockk<StyleLoadedCallback>()
    val listener2 = mockk<StyleDataLoadedCallback>()
    assertEquals(0, nativeObserver._cancelableSet.size)
    assertEquals(0, nativeObserver._resubscribableSet.size)
    nativeObserver.subscribeStyleLoaded(listener1)
    assertEquals(1, nativeObserver._resubscribableSet.size)
    nativeObserver.subscribeStyleDataLoaded(listener2)
    assertEquals(2, nativeObserver._resubscribableSet.size)

    nativeObserver.resubscribeStyleLoadListeners()
    assertEquals(0, nativeObserver._cancelableSet.size)
    assertEquals(2, nativeObserver._resubscribableSet.size)

    verify(exactly = 2) {
      observableInterface.subscribe(listener1)
      observableInterface.subscribe(listener2)
    }
  }
}
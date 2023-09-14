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
      NativeObserver::onCameraChangeListeners,
      OnCameraChangeListener::onCameraChanged
    )
  }

  @Test
  fun addAndRemoveOnMapIdleListener() {
    addAndRemoveListener(
      NativeObserver::addOnMapIdleListener,
      NativeObserver::removeOnMapIdleListener,
      NativeObserver::onMapIdleListeners,
      OnMapIdleListener::onMapIdle
    )
  }

  @Test
  fun addAndRemoveOnMapLoadErrorListener() {
    addAndRemoveListener(
      NativeObserver::addOnMapLoadErrorListener,
      NativeObserver::removeOnMapLoadErrorListener,
      NativeObserver::onMapLoadErrorListeners,
      OnMapLoadErrorListener::onMapLoadError
    )
  }

  @Test
  fun addAndRemoveOnMapLoadedListener() {
    addAndRemoveListener(
      NativeObserver::addOnMapLoadedListener,
      NativeObserver::removeOnMapLoadedListener,
      NativeObserver::onMapLoadedListeners,
      OnMapLoadedListener::onMapLoaded
    )
  }

  @Test
  fun addAndRemoveOnRenderFrameFinishedListener() {
    addAndRemoveListener(
      NativeObserver::addOnRenderFrameFinishedListener,
      NativeObserver::removeOnRenderFrameFinishedListener,
      NativeObserver::onRenderFrameFinishedListeners,
      OnRenderFrameFinishedListener::onRenderFrameFinished
    )
  }

  @Test
  fun addAndRemoveOnRenderFrameStartedListener() {
    addAndRemoveListener(
      NativeObserver::addOnRenderFrameStartedListener,
      NativeObserver::removeOnRenderFrameStartedListener,
      NativeObserver::onRenderFrameStartedListeners,
      OnRenderFrameStartedListener::onRenderFrameStarted
    )
  }

  @Test
  fun addAndRemoveOnSourceAddedListener() {
    addAndRemoveListener(
      NativeObserver::addOnSourceAddedListener,
      NativeObserver::removeOnSourceAddedListener,
      NativeObserver::onSourceAddedListeners,
      OnSourceAddedListener::onSourceAdded
    )
  }

  @Test
  fun addAndRemoveOnSourceDataLoadedListener() {
    addAndRemoveListener(
      NativeObserver::addOnSourceDataLoadedListener,
      NativeObserver::removeOnSourceDataLoadedListener,
      NativeObserver::onSourceDataLoadedListeners,
      OnSourceDataLoadedListener::onSourceDataLoaded
    )
  }

  @Test
  fun addAndRemoveOnSourceRemovedListener() {
    addAndRemoveListener(
      NativeObserver::addOnSourceRemovedListener,
      NativeObserver::removeOnSourceRemovedListener,
      NativeObserver::onSourceRemovedListeners,
      OnSourceRemovedListener::onSourceRemoved
    )
  }

  @Test
  fun addAndRemoveOnStyleLoadedListener() {
    addAndRemoveListener(
      NativeObserver::addOnStyleLoadedListener,
      NativeObserver::removeOnStyleLoadedListener,
      NativeObserver::onStyleLoadedListeners,
      OnStyleLoadedListener::onStyleLoaded,
      NativeObserver::_styleLoadedCancelableSet
    )
  }

  @Test
  fun addAndRemoveOnStyleImageMissingListener() {
    addAndRemoveListener(
      NativeObserver::addOnStyleImageMissingListener,
      NativeObserver::removeOnStyleImageMissingListener,
      NativeObserver::onStyleImageMissingListeners,
      OnStyleImageMissingListener::onStyleImageMissing
    )
  }

  @Test
  fun addAndRemoveOnStyleImageUnusedListener() {
    addAndRemoveListener(
      NativeObserver::addOnStyleImageUnusedListener,
      NativeObserver::removeOnStyleImageUnusedListener,
      NativeObserver::onStyleImageUnusedListeners,
      OnStyleImageUnusedListener::onStyleImageUnused
    )
  }

  @Test
  fun addAndRemoveOnStyleDataLoadedListener() {
    addAndRemoveListener(
      NativeObserver::addOnStyleDataLoadedListener,
      NativeObserver::removeOnStyleDataLoadedListener,
      NativeObserver::onStyleDataLoadedListeners,
      OnStyleDataLoadedListener::onStyleDataLoaded,
      NativeObserver::_styleDataLoadedCancelableSet
    )
  }

  /**
   * @param L the listener type (e.g. [OnRenderFrameStartedListener])
   * @param D the listener data type (e.g. [RenderFrameStartedEventData])
   */
  private inline fun <reified L, reified D> addAndRemoveListener(
    addListener: NativeObserver.(L) -> Unit,
    removeListener: NativeObserver.(L) -> Unit,
    listenersSet: NativeObserver.() -> MutableSet<L>,
    crossinline listenerOn: L.(D) -> Unit,
    cancelableSet: NativeObserver.() -> MutableSet<*> = NativeObserver::cancelableSet,
  ) {
    val listener: L = mockk(relaxUnitFun = true)
    nativeObserver.addListener(listener)
    assertEquals(1, nativeObserver.listenersSet().size)
    assertEquals(1, nativeObserver.cancelableSet().size)

    val listener2: L = mockk(relaxUnitFun = true)
    nativeObserver.addListener(listener2)
    assertEquals(2, nativeObserver.listenersSet().size)
    assertEquals(2, nativeObserver.cancelableSet().size)

    nativeObserver.removeListener(listener)
    assertEquals(1, nativeObserver.listenersSet().size)
    // FIXME: MAPSAND-1218
    // assertEquals(1, nativeObserver.cancelableSet().size)

    nativeObserver.removeListener(listener2)
    assertEquals(0, nativeObserver.listenersSet().size)
    // FIXME: MAPSAND-1218
    // assertEquals(0, nativeObserver.cancelableSet().size)
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

  // Style events
  @Test
  fun subscribeStyleLoadedListener() {
    subscribe(NativeObserver::subscribeStyleLoaded, NativeMapImpl::subscribe, NativeObserver::_styleLoadedCancelableSet)
  }

  @Test
  fun subscribeStyleImageMissingListener() {
    subscribe(NativeObserver::subscribeStyleImageMissing, NativeMapImpl::subscribe)
  }

  @Test
  fun subscribeStyleImageRemoveUnusedListener() {
    subscribe(NativeObserver::subscribeStyleImageRemoveUnused, NativeMapImpl::subscribe)
  }

  @Test
  fun subscribeStyleDataLoaded() {
    subscribe(NativeObserver::subscribeStyleDataLoaded, NativeMapImpl::subscribe, NativeObserver::_styleDataLoadedCancelableSet)
  }

  @Test
  fun subscribeResourceRequest() {
    subscribe(NativeObserver::subscribeResourceRequest, NativeMapImpl::subscribe)
  }

  private inline fun <reified C> subscribe(
    subscribe: NativeObserver.(C) -> Cancelable,
    crossinline nativeSubscribe: NativeMapImpl.(C) -> Cancelable,
    cancelableSet: NativeObserver.() -> MutableSet<*> = NativeObserver::cancelableSet,
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
    assertEquals(1, nativeObserver.cancelableSet.size)
    assertSame(cancelable, nativeObserver.cancelableSet.first())

    val callback2 = mockk<GenericEventCallback>(relaxUnitFun = true)
    val cancelable2 = nativeObserver.subscribeGenericEvent(event2, callback2)
    verify { observableInterface.subscribe(event2, callback2) }
    assertEquals(2, nativeObserver.cancelableSet.size)
    assertTrue(nativeObserver.cancelableSet.containsAll(listOf(cancelable, cancelable2)))

    cancelable.cancel()
    assertEquals(1, nativeObserver.cancelableSet.size)
    assertSame(cancelable2, nativeObserver.cancelableSet.first())

    cancelable2.cancel()
    assertTrue(nativeObserver.cancelableSet.isEmpty())
  }

  @Test
  fun clearListeners() {
    nativeObserver.onCameraChangeListeners.add(mockk(relaxed = true))

    nativeObserver.onMapIdleListeners.add(mockk(relaxed = true))
    nativeObserver.onMapLoadErrorListeners.add(mockk(relaxed = true))
    nativeObserver.onMapLoadedListeners.add(mockk(relaxed = true))

    nativeObserver.onRenderFrameFinishedListeners.add(mockk(relaxed = true))
    nativeObserver.onRenderFrameStartedListeners.add(mockk(relaxed = true))

    nativeObserver.onSourceAddedListeners.add(mockk(relaxed = true))
    nativeObserver.onSourceDataLoadedListeners.add(mockk(relaxed = true))
    nativeObserver.onSourceRemovedListeners.add(mockk(relaxed = true))

    nativeObserver.onStyleLoadedListeners.add(mockk(relaxed = true))
    nativeObserver.onStyleImageMissingListeners.add(mockk(relaxed = true))
    nativeObserver.onStyleImageUnusedListeners.add(mockk(relaxed = true))
    nativeObserver.onStyleDataLoadedListeners.add(mockk(relaxed = true))

    var pendingCancel = 0
    val onCancel: () -> Unit = { pendingCancel-- }
    nativeObserver.subscribeCameraChanged(mockk(relaxed = true), onCancel)
    pendingCancel++
    nativeObserver.subscribeStyleDataLoaded(mockk(relaxed = true), onCancel)
    pendingCancel++
    nativeObserver.subscribeStyleLoaded(mockk(relaxed = true), onCancel)
    pendingCancel++
    assertEquals(1, nativeObserver.cancelableSet.size)
    assertEquals(1, nativeObserver._styleDataLoadedCancelableSet.size)
    assertEquals(1, nativeObserver._styleLoadedCancelableSet.size)

    nativeObserver.onDestroy()
    assertTrue(nativeObserver.cancelableSet.isEmpty())
    assertTrue(nativeObserver._styleDataLoadedCancelableSet.isEmpty())
    assertTrue(nativeObserver._styleLoadedCancelableSet.isEmpty())
    assertTrue(nativeObserver.onCameraChangeListeners.isEmpty())
    assertEquals(0, pendingCancel)

    assertTrue(nativeObserver.onMapIdleListeners.isEmpty())
    assertTrue(nativeObserver.onMapLoadErrorListeners.isEmpty())
    assertTrue(nativeObserver.onMapLoadedListeners.isEmpty())

    assertTrue(nativeObserver.onRenderFrameFinishedListeners.isEmpty())
    assertTrue(nativeObserver.onRenderFrameStartedListeners.isEmpty())

    assertTrue(nativeObserver.onSourceAddedListeners.isEmpty())
    assertTrue(nativeObserver.onSourceDataLoadedListeners.isEmpty())
    assertTrue(nativeObserver.onSourceRemovedListeners.isEmpty())

    assertTrue(nativeObserver.onStyleLoadedListeners.isEmpty())
    assertTrue(nativeObserver.onStyleImageMissingListeners.isEmpty())
    assertTrue(nativeObserver.onStyleImageUnusedListeners.isEmpty())
    assertTrue(nativeObserver.onStyleDataLoadedListeners.isEmpty())
  }

  @Test
  fun resubscribesStyleLoadedEvents() {
    val listener1 = mockk<StyleLoadedCallback>()
    val listener2 = mockk<StyleDataLoadedCallback>()
    assertEquals(0, nativeObserver.cancelableSet.size)
    assertEquals(0, nativeObserver._styleDataLoadedCancelableSet.size)
    assertEquals(0, nativeObserver._styleLoadedCancelableSet.size)
    nativeObserver.subscribeStyleLoaded(listener1)
    assertEquals(1, nativeObserver._styleLoadedCancelableSet.size)
    nativeObserver.subscribeStyleDataLoaded(listener2)
    assertEquals(1, nativeObserver._styleDataLoadedCancelableSet.size)

    nativeObserver.resubscribeStyleLoadListeners()
    assertEquals(0, nativeObserver.cancelableSet.size)
    assertEquals(1, nativeObserver._styleLoadedCancelableSet.size)
    assertEquals(1, nativeObserver._styleDataLoadedCancelableSet.size)

    verify(exactly = 2) {
      observableInterface.subscribe(listener1)
      observableInterface.subscribe(listener2)
    }
  }
}
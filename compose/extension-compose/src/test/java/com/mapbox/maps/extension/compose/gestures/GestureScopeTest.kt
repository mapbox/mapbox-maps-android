package com.mapbox.maps.extension.compose.gestures

import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import com.mapbox.maps.plugin.gestures.OnFlingListener
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.OnRotateListener
import com.mapbox.maps.plugin.gestures.OnScaleListener
import com.mapbox.maps.plugin.gestures.OnShoveListener
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class, MapboxExperimental::class)
public class GestureScopeTest {

  private val gesturesPlugin: GesturesPlugin = mockk(relaxed = true)
  private val mapboxMap: MapboxMap = mockk(relaxed = true)
  private lateinit var scope: GestureScope

  @Before
  public fun setup() {
    every { mapboxMap.gesturesPlugin(any()) } answers {
      val block = firstArg<GesturesPlugin.() -> Any?>()
      gesturesPlugin.block()
    }
    scope = GestureScope(mapboxMap)
  }

  @Test
  public fun detectMoveGestures_registersAndRemovesListener(): Unit = runTest {
    val addedSlot = slot<OnMoveListener>()
    val removedSlot = slot<OnMoveListener>()

    val job = launch {
      scope.detectMoveGestures()
    }
    testScheduler.advanceUntilIdle()
    verify { gesturesPlugin.addOnMoveListener(capture(addedSlot)) }

    job.cancel()
    testScheduler.advanceUntilIdle()
    verify { gesturesPlugin.removeOnMoveListener(capture(removedSlot)) }
    assert(addedSlot.captured === removedSlot.captured) {
      "The same listener instance must be added and removed"
    }
  }

  @Test
  public fun detectMoveGestures_callbacksAreInvoked(): Unit = runTest {
    val listenerSlot = slot<OnMoveListener>()
    val detector = mockk<MoveGestureDetector>(relaxed = true)

    var beginCalled = false
    var moveCalled = false
    var endCalled = false

    val job = launch {
      scope.detectMoveGestures(
        onMoveBegin = { beginCalled = true },
        onMoveEnd = { endCalled = true },
        onMove = { moveCalled = true; false },
      )
    }
    testScheduler.advanceUntilIdle()
    verify { gesturesPlugin.addOnMoveListener(capture(listenerSlot)) }

    listenerSlot.captured.onMoveBegin(detector)
    listenerSlot.captured.onMove(detector)
    listenerSlot.captured.onMoveEnd(detector)

    assert(beginCalled) { "onMoveBegin should have been called" }
    assert(moveCalled) { "onMove should have been called" }
    assert(endCalled) { "onMoveEnd should have been called" }

    job.cancel()
  }

  @Test
  public fun detectRotateGestures_registersAndRemovesListener(): Unit = runTest {
    val addedSlot = slot<OnRotateListener>()
    val removedSlot = slot<OnRotateListener>()

    val job = launch { scope.detectRotateGestures() }
    testScheduler.advanceUntilIdle()
    verify { gesturesPlugin.addOnRotateListener(capture(addedSlot)) }

    job.cancel()
    testScheduler.advanceUntilIdle()
    verify { gesturesPlugin.removeOnRotateListener(capture(removedSlot)) }
    assert(addedSlot.captured === removedSlot.captured)
  }

  @Test
  public fun detectScaleGestures_registersAndRemovesListener(): Unit = runTest {
    val addedSlot = slot<OnScaleListener>()
    val removedSlot = slot<OnScaleListener>()

    val job = launch { scope.detectScaleGestures() }
    testScheduler.advanceUntilIdle()
    verify { gesturesPlugin.addOnScaleListener(capture(addedSlot)) }

    job.cancel()
    testScheduler.advanceUntilIdle()
    verify { gesturesPlugin.removeOnScaleListener(capture(removedSlot)) }
    assert(addedSlot.captured === removedSlot.captured)
  }

  @Test
  public fun detectShoveGestures_registersAndRemovesListener(): Unit = runTest {
    val addedSlot = slot<OnShoveListener>()
    val removedSlot = slot<OnShoveListener>()

    val job = launch { scope.detectShoveGestures() }
    testScheduler.advanceUntilIdle()
    verify { gesturesPlugin.addOnShoveListener(capture(addedSlot)) }

    job.cancel()
    testScheduler.advanceUntilIdle()
    verify { gesturesPlugin.removeOnShoveListener(capture(removedSlot)) }
    assert(addedSlot.captured === removedSlot.captured)
  }

  @Test
  public fun detectFlingGesture_registersAndRemovesListener(): Unit = runTest {
    val addedSlot = slot<OnFlingListener>()
    val removedSlot = slot<OnFlingListener>()

    val job = launch { scope.detectFlingGesture {} }
    testScheduler.advanceUntilIdle()
    verify { gesturesPlugin.addOnFlingListener(capture(addedSlot)) }

    job.cancel()
    testScheduler.advanceUntilIdle()
    verify { gesturesPlugin.removeOnFlingListener(capture(removedSlot)) }
    assert(addedSlot.captured === removedSlot.captured)
  }

  @Test
  public fun detectFlingGesture_callbackIsInvoked(): Unit = runTest {
    val listenerSlot = slot<OnFlingListener>()
    var flingCalled = false

    val job = launch {
      scope.detectFlingGesture { flingCalled = true }
    }
    testScheduler.advanceUntilIdle()
    verify { gesturesPlugin.addOnFlingListener(capture(listenerSlot)) }

    listenerSlot.captured.onFling()
    assert(flingCalled) { "onFling should have been called" }

    job.cancel()
  }
}
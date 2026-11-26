package com.mapbox.maps

import com.mapbox.annotation.MapboxExperimental
import com.mapbox.common.MainMapboxThreadProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertSame
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * [MainMapboxThreadProvider]'s dispatcher must be the [Dispatchers.Main] proxy itself, so
 * `dispatcher.immediate` read at call time follows kotlinx-coroutines-test's setMain/resetMain.
 */
@OptIn(MapboxExperimental::class, ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class MainMapboxThreadDispatcherTest {

  @After
  fun tearDown() {
    Dispatchers.resetMain()
  }

  @Test
  fun dispatcher_isTheMainProxy() {
    val thread = MainMapboxThreadProvider().getOrCreate()

    assertSame(Dispatchers.Main, thread.dispatcher)
  }

  @Test
  fun dispatcherImmediate_followsSetMainSwappedAfterCreation() {
    val thread = MainMapboxThreadProvider().getOrCreate()
    val testMain = UnconfinedTestDispatcher(name = "TestMain")

    Dispatchers.setMain(testMain)

    assertSame(Dispatchers.Main.immediate, thread.dispatcher.immediate)
  }
}
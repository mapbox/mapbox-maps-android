package com.mapbox.maps.testapp.integration.fragment

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Integration tests using fragment and the AndroidX fragment test library.
 */
@RunWith(AndroidJUnit4::class)
class FragmentScenarioTest {

  @Test
  fun resumed() {
    val latch = CountDownLatch(1)
    val scenario = launchFragmentInContainer<MapFragment>()
    scenario.onFragment {
      it.activity!!.runOnUiThread {
        it.loadMap {
          latch.countDown()
        }
      }
    }
    if (!latch.await(10, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun backToCreated() {
    val latch = CountDownLatch(1)
    val scenario = launchFragmentInContainer<MapFragment>()
    scenario.onFragment {
      it.activity!!.runOnUiThread {
        it.loadMap {
          latch.countDown()
        }
      }
    }
    if (!latch.await(10, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
    scenario.moveToState(Lifecycle.State.CREATED)
  }

  @Test
  fun backToResumed() {
    val latch = CountDownLatch(1)
    val scenario = launchFragmentInContainer<MapFragment>()
    scenario.onFragment {
      it.activity!!.runOnUiThread {
        it.loadMap {
          latch.countDown()
        }
      }
    }
    if (!latch.await(10, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
    scenario.moveToState(Lifecycle.State.CREATED)
    scenario.moveToState(Lifecycle.State.RESUMED)
  }

  @Test
  fun recreated() {
    val latch = CountDownLatch(1)
    val scenario = launchFragmentInContainer<MapFragment>()
    scenario.onFragment {
      it.activity!!.runOnUiThread {
        it.loadMap {
          latch.countDown()
        }
      }
    }
    if (!latch.await(10, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
    scenario.recreate()
  }

  @Test
  fun destroyed() {
    val latch = CountDownLatch(1)
    val scenario = launchFragmentInContainer<MapFragment>()
    scenario.onFragment {
      it.activity!!.runOnUiThread {
        it.loadMap {
          latch.countDown()
        }
      }
    }
    if (!latch.await(10, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
    scenario.moveToState(Lifecycle.State.DESTROYED)
  }

  @Test
  fun stressTestRecreate() {
    val latch = CountDownLatch(1)
    val scenario = launchFragmentInContainer<MapFragment>()
    scenario.onFragment {
      it.activity!!.runOnUiThread {
        it.loadMap {
          latch.countDown()
        }
      }
    }
    if (!latch.await(10, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
    repeat(10) {
      scenario.recreate()
    }
  }

  @Test
  fun stressTestRecreateWithLoad() {
    val scenario = launchFragmentInContainer<MapFragment>()
    repeat(10) {
      val latch = CountDownLatch(1)
      scenario.onFragment {
        it.activity!!.runOnUiThread {
          it.loadMap {
            latch.countDown()
          }
        }
      }
      if (!latch.await(10, TimeUnit.SECONDS)) {
        throw TimeoutException()
      }
      scenario.recreate()
    }
  }

  @Test
  fun stressTestResumed() {
    val scenario = launchFragmentInContainer<MapFragment>()
    scenario.recreate()
    repeat(20) {
      scenario.moveToState(Lifecycle.State.CREATED)
      scenario.moveToState(Lifecycle.State.RESUMED)
    }
    scenario.recreate()
    scenario.moveToState(Lifecycle.State.DESTROYED)
  }
}
package com.mapbox.maps

import com.mapbox.maps.shadows.ShadowLogThrottler
import org.junit.Assert.assertNotSame
import org.junit.Assert.assertSame
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogThrottler::class])
class LogThrottlersTest {

  @Test
  fun getReturnsSameInstanceForSameKey() {
    val throttlers = LogThrottlers()
    assertSame(throttlers["setCamera"], throttlers["setCamera"])
  }

  @Test
  fun getReturnsDifferentInstancesForDifferentKeys() {
    val throttlers = LogThrottlers()
    assertNotSame(throttlers["setCamera"], throttlers["cameraState"])
  }

  @Test
  fun perCallIntervalOverloadCachesByKey() {
    val throttlers = LogThrottlers()
    // Once cached, subsequent calls return the same instance regardless of the interval arg.
    assertSame(throttlers["setCamera", 100L], throttlers["setCamera", 999L])
  }

  @Test
  fun defaultGetAndPerCallGetShareTheSameCache() {
    val throttlers = LogThrottlers(defaultInterval = 50L)
    val viaDefault = throttlers["setCamera"]
    val viaExplicit = throttlers["setCamera", 50L]
    assertSame(viaDefault, viaExplicit)
  }

  @Test
  fun clearDropsCachedThrottlers() {
    val throttlers = LogThrottlers()
    val before = throttlers["setCamera"]
    throttlers.clear()
    assertNotSame(before, throttlers["setCamera"])
  }
}
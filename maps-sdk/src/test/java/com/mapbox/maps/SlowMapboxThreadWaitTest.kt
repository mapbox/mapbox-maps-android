package com.mapbox.maps

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test

class SlowMapboxThreadWaitTest {

  @Before
  fun setUp() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logW(any(), any()) } just Runs
  }

  @After
  fun tearDown() {
    unmockkStatic("com.mapbox.maps.MapboxLogger")
  }

  @Test
  fun waitAtThresholdDoesNotWarn() {
    warnIfSlowMapboxThreadWait(SLOW_MAPBOX_THREAD_WAIT_MS)
    verify(exactly = 0) { logW(any(), any()) }
  }

  @Test
  fun waitAboveThresholdWarns() {
    warnIfSlowMapboxThreadWait(SLOW_MAPBOX_THREAD_WAIT_MS + 1)
    verify(exactly = 1) { logW(MAPBOX_THREAD_TAG, match { it.startsWith("Waited 101 ms") }) }
  }
}
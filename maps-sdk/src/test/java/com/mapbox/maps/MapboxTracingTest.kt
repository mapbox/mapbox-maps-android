package com.mapbox.maps

import com.mapbox.verifyNo
import com.mapbox.verifyOnce
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowTracing::class])
class MapboxTracingTest {

  @Before
  fun setUp() {
    mockkStatic("com.mapbox.maps.Tracing")
  }

  @After
  fun cleanUp() {
    unmockkStatic("com.mapbox.maps.Tracing")
    MapboxTracing.disableAll()
  }

  @Test
  fun enableAll() {
    MapboxTracing.enableAll()
    verifyOnce { Tracing.setTracingBackendType(TracingBackendType.PLATFORM) }
    assert(MapboxTracing.platformTracingEnabled)
  }

  @Test
  fun enablePlatform() {
    MapboxTracing.enablePlatform()
    verifyNo { Tracing.setTracingBackendType(any()) }
    assert(MapboxTracing.platformTracingEnabled)
  }

  @Test
  fun enableCore() {
    MapboxTracing.enableCore()
    verifyOnce { Tracing.setTracingBackendType(TracingBackendType.PLATFORM) }
    assert(!MapboxTracing.platformTracingEnabled)
  }

  @Test
  fun disableAll() {
    MapboxTracing.disableAll()
    verifyOnce { Tracing.setTracingBackendType(TracingBackendType.NOOP) }
    assert(!MapboxTracing.platformTracingEnabled)
  }
}
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
  }

  @Test
  fun enableAllTracerAvailable() {
    MapboxTracing.tracerAvailable = true
    MapboxTracing.enableAll()
    verifyOnce { Tracing.setTracingBackendType(TracingBackendType.PLATFORM) }
    assert(MapboxTracing.platformTracingEnabled)
  }

  @Test(expected = RuntimeException::class)
  fun enableAllTracerNotAvailable() {
    MapboxTracing.tracerAvailable = false
    MapboxTracing.enableAll()
  }

  @Test
  fun enablePlatformTracerAvailable() {
    MapboxTracing.tracerAvailable = true
    MapboxTracing.enablePlatform()
    verifyNo { Tracing.setTracingBackendType(any()) }
    assert(MapboxTracing.platformTracingEnabled)
  }

  @Test(expected = RuntimeException::class)
  fun enablePlatformTracerNotAvailable() {
    MapboxTracing.tracerAvailable = false
    MapboxTracing.enablePlatform()
  }

  @Test
  fun enableCoreTracerAvailable() {
    MapboxTracing.tracerAvailable = true
    MapboxTracing.enableCore()
    verifyOnce { Tracing.setTracingBackendType(TracingBackendType.PLATFORM) }
    assert(!MapboxTracing.platformTracingEnabled)
  }

  @Test(expected = RuntimeException::class)
  fun enableCoreTracerNotAvailable() {
    MapboxTracing.tracerAvailable = false
    MapboxTracing.enableCore()
  }

  @Test
  fun disableAllTracerAvailable() {
    MapboxTracing.tracerAvailable = true
    MapboxTracing.disableAll()
    verifyOnce { Tracing.setTracingBackendType(TracingBackendType.NOOP) }
    assert(!MapboxTracing.platformTracingEnabled)
  }

  @Test(expected = RuntimeException::class)
  fun disableAllTracerNotAvailable() {
    MapboxTracing.tracerAvailable = false
    MapboxTracing.disableAll()
  }
}
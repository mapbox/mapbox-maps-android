package com.mapbox.maps

/**
 * Allows to control several levels of tracing that could be useful to understand the performance of Mapbox Maps.
 * For more details about Android tracing refer to relevant section of DEVELOPING.md file.
 */
object MapboxTracing {

  internal const val MAPBOX_TRACE_ID = "mbx"

  internal var platformTracingEnabled = false

  /**
   * Enable all the traces: native rendering engine traces and Android Maps SDK traces.
   *
   * Enabling Mapbox tracing is only recommended for local performance measurements, to capture accurate results.
   * This tracing is designed to be usable in release builds to enable local profiling.
   */
  fun enableAll() {
    platformTracingEnabled = true
    Tracing.setTracingBackendType(TracingBackendType.PLATFORM)
  }

  /**
   * Enable only native rendering engine traces.
   *
   * @throws [RuntimeException] if build is not debuggable.
   */
  fun enablePlatform() {
    platformTracingEnabled = true
  }

  /**
   * Enable only Android Maps SDK traces.
   *
   * @throws [RuntimeException] if build is not debuggable.
   */
  fun enableCore() {
    Tracing.setTracingBackendType(TracingBackendType.PLATFORM)
  }

  /**
   * Disable all traces.
   *
   * @throws [RuntimeException] if build is not debuggable.
   */
  fun disableAll() {
    platformTracingEnabled = false
    Tracing.setTracingBackendType(TracingBackendType.NOOP)
  }
}
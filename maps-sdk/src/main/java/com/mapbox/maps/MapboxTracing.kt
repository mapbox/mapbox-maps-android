package com.mapbox.maps

/**
 * Allows to control several levels of tracing that could be useful to understand the performance of Mapbox Maps.
 * For more details about Android tracing refer to relevant section of DEVELOPING.md file.
 * API is deprecated, please use com.mapbox.common.MapboxTracing
 */
@Deprecated(
  "Please use com.mapbox.common.MapboxTracing instead.",
  replaceWith = ReplaceWith("MapboxTracing", "com.mapbox.common.MapboxTracing")
)
object MapboxTracing {
  private val impl = com.mapbox.common.MapboxTracing

  /**
   * Enable all the traces: native rendering engine traces and Android Maps SDK traces.
   *
   * Enabling Mapbox tracing is only recommended for local performance measurements, to capture accurate results.
   * This tracing is designed to be usable in release builds to enable local profiling.
   */
  fun enableAll() {
    impl.enableAll()
  }

  /**
   * Enable only native rendering engine traces.
   *
   * @throws [RuntimeException] if build is not debuggable.
   */
  fun enablePlatform() {
    impl.enablePlatform()
  }

  /**
   * Enable only Android Maps SDK traces.
   *
   * @throws [RuntimeException] if build is not debuggable.
   */
  fun enableCore() {
    impl.enableCore()
  }

  /**
   * Disable all traces.
   *
   * @throws [RuntimeException] if build is not debuggable.
   */
  fun disableAll() {
    impl.disableAll()
  }
}
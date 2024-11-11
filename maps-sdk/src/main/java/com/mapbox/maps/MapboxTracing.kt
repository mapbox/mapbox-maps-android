package com.mapbox.maps

import android.content.pm.ApplicationInfo
import androidx.annotation.VisibleForTesting
import com.mapbox.common.MapboxSDKCommon

/**
 * Allows to control several levels of tracing that could be useful to understand the performance of Mapbox Maps.
 * For more details about Android tracing refer to relevant section of DEVELOPING.md file.
 */
object MapboxTracing {

  internal const val MAPBOX_TRACE_ID = "mbx"

  internal var platformTracingEnabled = false
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var tracerAvailable: Boolean? = null

  private fun checkTracerEnabled() {
    if (tracerAvailable == null) {
      tracerAvailable =
        MapboxSDKCommon.getContext().applicationInfo.flags != 0 and ApplicationInfo.FLAG_DEBUGGABLE
    }
    if (tracerAvailable == false) {
      throw RuntimeException(
        "Mapbox Tracing could not be used as build is not debuggable!" +
          " You could enable it by setting android:debuggable=\"true\" in AndroidManifest <application> block."
      )
    }
  }

  /**
   * Enable all the traces: native rendering engine traces and Android Maps SDK traces.
   *
   * @throws [RuntimeException] if build is not debuggable.
   */
  fun enableAll() {
    checkTracerEnabled()
    platformTracingEnabled = true
    Tracing.setTracingBackendType(TracingBackendType.PLATFORM)
  }

  /**
   * Enable only native rendering engine traces.
   *
   * @throws [RuntimeException] if build is not debuggable.
   */
  fun enablePlatform() {
    checkTracerEnabled()
    platformTracingEnabled = true
  }

  /**
   * Enable only Android Maps SDK traces.
   *
   * @throws [RuntimeException] if build is not debuggable.
   */
  fun enableCore() {
    checkTracerEnabled()
    Tracing.setTracingBackendType(TracingBackendType.PLATFORM)
  }

  /**
   * Disable all traces.
   *
   * @throws [RuntimeException] if build is not debuggable.
   */
  fun disableAll() {
    checkTracerEnabled()
    platformTracingEnabled = false
    Tracing.setTracingBackendType(TracingBackendType.NOOP)
  }
}
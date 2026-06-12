package com.mapbox.maps.extension.compose.internal

import com.mapbox.maps.module.TelemetryEvent

internal object ComposeTelemetryEvents {
  val map = TelemetryEvent.create("compose/map")

  init {
    // The event is null when the Compose runtime version can't be resolved, so we emit nothing
    // rather than a misleading "compose/runtime/null" counter.
    val runtimeVersion: TelemetryEvent? =
      composeRuntimeVersion()?.let { TelemetryEvent.create("compose/runtime/$it") }
    runtimeVersion?.increment()
  }

  // androidx.compose.runtime.ComposeVersion.version is an `internal const val Int`. We can't
  // reference it directly (internal), and even if we could the const would be inlined at our
  // build time. Reflection reads the value from the consumer app's loaded Compose runtime,
  // which is what we want to report.
  private fun composeRuntimeVersion(): Int? = try {
    Class.forName("androidx.compose.runtime.ComposeVersion")
      .getDeclaredField("version")
      .also { it.isAccessible = true }
      .getInt(null)
  } catch (e: Throwable) {
    null
  }
}
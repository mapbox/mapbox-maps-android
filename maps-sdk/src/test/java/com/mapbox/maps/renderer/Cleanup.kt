package com.mapbox.maps.renderer

import org.robolectric.shadows.ShadowPausedSystemClock
import java.util.concurrent.CopyOnWriteArrayList

@Suppress("UNCHECKED_CAST")
private val staticListeners = ShadowPausedSystemClock::class.java
  .getDeclaredField("staticListeners")
  .apply { isAccessible = true }
  .get(null) as CopyOnWriteArrayList<Any?>

fun cleanupShadows() {
  // clean global state for shadow loopers
  staticListeners.run {
    val first = firstOrNull() ?: return@run
    clear()
    // intentionally keep the first listener as it is expected to be for the global Looper
    add(first)
  }
}
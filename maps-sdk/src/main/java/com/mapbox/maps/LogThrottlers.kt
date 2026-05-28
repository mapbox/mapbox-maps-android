package com.mapbox.maps

import com.mapbox.common.LogThrottler
import java.util.concurrent.ConcurrentHashMap

/**
 * Keyed pool of [LogThrottler]s. Each key gets an independent throttler so a burst on one
 * key doesn't suppress logs from another.
 */
internal class LogThrottlers(
  private val defaultInterval: Long = LOG_THROTTLE_INTERVAL_MS,
) {

  private val throttlers = ConcurrentHashMap<String, LogThrottler>()

  /**
   * Returns the throttler for [key], creating it on first access at [defaultInterval].
   * */
  operator fun get(key: String): LogThrottler = get(key, defaultInterval)

  /**
   * Returns the throttler for [key]; created at [interval] on first access, cached afterward.
   * */
  operator fun get(key: String, interval: Long): LogThrottler {
    throttlers[key]?.let { return it }
    val candidate = LogThrottler(interval)
    return throttlers.putIfAbsent(key, candidate) ?: candidate
  }

  /**
   * Drops all pooled throttlers.
   * */
  fun clear() {
    throttlers.clear()
  }

  companion object {
    /**
     * Default per-key interval.
     * */
    const val LOG_THROTTLE_INTERVAL_MS = 300L
  }
}
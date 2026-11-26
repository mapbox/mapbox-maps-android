@file:JvmName("MapboxLogger")

package com.mapbox.maps

import androidx.annotation.RestrictTo
import com.mapbox.common.LogConfiguration
import com.mapbox.common.LogThrottler
import com.mapbox.common.LoggingLevel
import com.mapbox.common.MapboxMapsAndroidLogger.SDK_IDENTIFIER
import com.mapbox.common.MapboxMapsAndroidLogger.internalGetLoggingLevel
import com.mapbox.common.MapboxMapsAndroidLogger.internalIsLoggableD
import com.mapbox.common.MapboxMapsAndroidLogger.internalLogD
import com.mapbox.common.MapboxMapsAndroidLogger.internalLogE
import com.mapbox.common.MapboxMapsAndroidLogger.internalLogI
import com.mapbox.common.MapboxMapsAndroidLogger.internalLogW
import com.mapbox.common.MapboxMapsAndroidLogger.internalSetLoggingLevel

/**
 * Log Android error entry in the following format: E/Mapbox: [[SDK_IDENTIFIER]\ [tag]] [message]
 */
fun logE(tag: String, message: String) = internalLogE(tag, message)

/**
 * Log Android error entry in the following format: E/Mapbox: [[SDK_IDENTIFIER]\ [tag]] [message]
 * Internal version with optional log throttling support.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun logE(tag: String, message: String, logThrottler: LogThrottler?) = internalLogE(tag, message, logThrottler)

/**
 * Log Android warning entry in the following format: W/Mapbox: [[SDK_IDENTIFIER]\ [tag]] [message]
 */
fun logW(tag: String, message: String) = internalLogW(tag, message)

/**
 * Log Android warning entry in the following format: W/Mapbox: [[SDK_IDENTIFIER]\ [tag]] [message]
 * Internal version with optional log throttling support.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun logW(tag: String, message: String, logThrottler: LogThrottler?) = internalLogW(tag, message, logThrottler)

/**
 * Log Android info entry in the following format: I/Mapbox: [[SDK_IDENTIFIER]\ [tag]] [message]
 */
fun logI(tag: String, message: String) = internalLogI(tag, message)

/**
 * Log Android info entry in the following format: I/Mapbox: [[SDK_IDENTIFIER]\ [tag]] [message]
 * Internal version with optional log throttling support.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun logI(tag: String, message: String, logThrottler: LogThrottler?) = internalLogI(tag, message, logThrottler)

/**
 * Log Android debug entry in the following format: D/Mapbox: [[SDK_IDENTIFIER]\ [tag]] [message]
 */
fun logD(tag: String, message: String) = internalLogD(tag, message)

/**
 * Log Android debug entry in the following format: D/Mapbox: [[SDK_IDENTIFIER]\ [tag]] [message]
 * Internal version with optional log throttling support.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun logD(tag: String, message: String, logThrottler: LogThrottler?) = internalLogD(tag, message, logThrottler)

/**
 * Log Android debug entry in the following format: D/Mapbox: [[SDK_IDENTIFIER]\ [tag]] [message]
 *
 * The [message] lambda is only evaluated if the `debug` priority is enabled for the [tag].
 */
inline fun logD(tag: String, message: () -> String) {
  if (getLoggingLevel(tag) == LoggingLevel.DEBUG) {
    logD(tag, message())
  }
}

/**
 * Check if debug priority logging is enabled for the default logging level.
 *
 * This can be used to conditionally execute expensive operations that are only needed
 * for debug logging, avoiding unnecessary computation when debug logs are disabled.
 */
fun isLoggableD() = internalIsLoggableD()

/**
 * Set the logging level for the given [tag],
 * overriding the default logging level set with [LogConfiguration.setLoggingLevel].
 *
 * @param tag the tag to set the logging level for.
 * @param level log messages with lower severity than this level are filtered out.
 * If null, logging is disabled for the [tag].
 */
fun setLoggingLevel(tag: String, level: LoggingLevel?) = internalSetLoggingLevel(tag, level)

/**
 * Get the logging level for the given [tag].
 *
 * @param tag the tag to get the logging level for.
 * @return the level set with [setLoggingLevel] for the [tag], or the default logging level
 * if the [tag] has no level of its own. Null if logging is disabled for the [tag].
 */
fun getLoggingLevel(tag: String): LoggingLevel? = internalGetLoggingLevel(tag)
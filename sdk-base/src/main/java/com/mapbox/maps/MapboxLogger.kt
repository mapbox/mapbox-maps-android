@file:JvmName("MapboxLogger")

package com.mapbox.maps

import com.mapbox.common.MapboxMapsAndroidLogger.SDK_IDENTIFIER
import com.mapbox.common.MapboxMapsAndroidLogger.internalIsLoggableD
import com.mapbox.common.MapboxMapsAndroidLogger.internalLogD
import com.mapbox.common.MapboxMapsAndroidLogger.internalLogE
import com.mapbox.common.MapboxMapsAndroidLogger.internalLogI
import com.mapbox.common.MapboxMapsAndroidLogger.internalLogW

/**
 * Log Android error entry in the following format: E/Mapbox: [[SDK_IDENTIFIER]\ [tag]] [message]
 */
fun logE(tag: String, message: String) = internalLogE(tag, message)

/**
 * Log Android warning entry in the following format: W/Mapbox: [[SDK_IDENTIFIER]\ [tag]] [message]
 */
fun logW(tag: String, message: String) = internalLogW(tag, message)

/**
 * Log Android info entry in the following format: I/Mapbox: [[SDK_IDENTIFIER]\ [tag]] [message]
 */
fun logI(tag: String, message: String) = internalLogI(tag, message)

/**
 * Log Android debug entry in the following format: D/Mapbox: [[SDK_IDENTIFIER]\ [tag]] [message]
 */
fun logD(tag: String, message: String) = internalLogD(tag, message)

/**
 * Log Android debug entry in the following format: D/Mapbox: [[SDK_IDENTIFIER]\ [tag]] [message]
 * `message` lambda is only evaluated if the `debug` priority is enabled.
 */
inline fun logD(tag: String, message: () -> String) {
  if (isLoggableD()) {
    logD(tag, message())
  }
}

/**
 * Check if debug priority logging is enabled.
 *
 * This can be used to conditionally execute expensive operations that are only needed
 * for debug logging, avoiding unnecessary computation when debug logs are disabled.
 */
fun isLoggableD() = internalIsLoggableD()
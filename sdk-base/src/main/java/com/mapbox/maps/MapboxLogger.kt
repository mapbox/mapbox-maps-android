@file:JvmName("MapboxLogger")

package com.mapbox.maps

import com.mapbox.common.MapboxCommonLogger.SDK_IDENTIFIER
import com.mapbox.common.MapboxCommonLogger.internalLogD
import com.mapbox.common.MapboxCommonLogger.internalLogE
import com.mapbox.common.MapboxCommonLogger.internalLogI
import com.mapbox.common.MapboxCommonLogger.internalLogW

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
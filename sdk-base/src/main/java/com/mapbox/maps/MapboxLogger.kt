@file:JvmName("MapboxLogger")

package com.mapbox.maps

import com.mapbox.common.Logger

/**
 * Log Android error entry in the following format: E/Mapbox: [[SDK_IDENTIFIER]\ [tag]] [message]
 */
fun logE(tag: String, message: String) = Logger.e("$SDK_IDENTIFIER\\$tag", message)

/**
 * Log Android warning entry in the following format: W/Mapbox: [[SDK_IDENTIFIER]\ [tag]] [message]
 */
fun logW(tag: String, message: String) = Logger.w("$SDK_IDENTIFIER\\$tag", message)

/**
 * Log Android info entry in the following format: I/Mapbox: [[SDK_IDENTIFIER]\ [tag]] [message]
 */
fun logI(tag: String, message: String) = Logger.i("$SDK_IDENTIFIER\\$tag", message)

/**
 * Log Android debug entry in the following format: D/Mapbox: [[SDK_IDENTIFIER]\ [tag]] [message]
 */
fun logD(tag: String, message: String) = Logger.d("$SDK_IDENTIFIER\\$tag", message)

private const val SDK_IDENTIFIER = "maps-android"
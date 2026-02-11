package com.mapbox.common

import com.mapbox.maps.base.BuildConfig

internal object MapboxMapsAndroidLogger {

  init {
    if (BuildConfig.DEBUG) {
      try {
        LogConfiguration.setLoggingLevel(LoggingLevel.DEBUG)
      } catch (e: UnsatisfiedLinkError) {
        // NOP
      }
    }
  }

  internal fun internalLogE(tag: String, message: String, logThrottler: LogThrottler? = null) {
    try {
      logThrottler?.let {
        Log.error(message, "$SDK_IDENTIFIER\\$tag", logThrottler)
      } ?: run {
        Log.error(message, "$SDK_IDENTIFIER\\$tag")
      }
    } catch (e: UnsatisfiedLinkError) {
      android.util.Log.e("$SDK_IDENTIFIER\\$tag", message)
    }
  }

  internal fun internalLogW(tag: String, message: String, logThrottler: LogThrottler? = null) {
    try {
      logThrottler?.let {
        Log.warning(message, "$SDK_IDENTIFIER\\$tag", logThrottler)
      } ?: run {
        Log.warning(message, "$SDK_IDENTIFIER\\$tag")
      }
    } catch (e: UnsatisfiedLinkError) {
      android.util.Log.w("$SDK_IDENTIFIER\\$tag", message)
    }
  }

  internal fun internalLogI(tag: String, message: String, logThrottler: LogThrottler? = null) {
    try {
      logThrottler?.let {
        Log.info(message, "$SDK_IDENTIFIER\\$tag", logThrottler)
      } ?: run {
        Log.info(message, "$SDK_IDENTIFIER\\$tag")
      }
    } catch (e: UnsatisfiedLinkError) {
      android.util.Log.i("$SDK_IDENTIFIER\\$tag", message)
    }
  }

  internal fun internalLogD(tag: String, message: String, logThrottler: LogThrottler? = null) {
    try {
      logThrottler?.let {
        Log.debug(message, "$SDK_IDENTIFIER\\$tag", logThrottler)
      } ?: run {
        Log.debug(message, "$SDK_IDENTIFIER\\$tag")
      }
    } catch (e: UnsatisfiedLinkError) {
      android.util.Log.d("$SDK_IDENTIFIER\\$tag", message)
    }
  }

  internal fun internalIsLoggableD(): Boolean {
    return try {
      LogConfiguration.getLoggingLevel() == LoggingLevel.DEBUG
    } catch (e: UnsatisfiedLinkError) {
      false
    }
  }

  internal const val SDK_IDENTIFIER = "maps-android"
}
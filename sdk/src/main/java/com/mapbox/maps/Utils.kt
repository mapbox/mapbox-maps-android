package com.mapbox.maps

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.WindowManager
import java.lang.ref.WeakReference

/**
 * Checks the reference and if valid, calls the requested method.
 */
internal fun <T, R> WeakReference<T>.call(method: T.() -> R): R {
  return (this.get() ?: throw IllegalStateException()).method()
}

/**
 * Obtains the approximate refresh time, in nanoseconds, of the default display associated
 * with the activity.
 *
 * The actual refresh rate can vary slightly (e.g. 58-62fps on a 60fps device).
 */
fun getDisplayRefreshNsec(context: Context): Long {
  val display = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
  val displayFps = display.refreshRate.toDouble()
  val refreshNs = Math.round(1000000000L / displayFps)
  logE("KIRYLDD", "Refresh rate nanos: $refreshNs")
  return refreshNs
}
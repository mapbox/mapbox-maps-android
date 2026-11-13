package com.mapbox.maps.plugin.attribution

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.widget.Toast

/**
 * Open an attribution link in the browser, ignoring anything that is not http(s) and reporting a
 * missing browser to the user.
 */
internal fun Context.openAttributionUrl(url: String) {
  val uri = Uri.parse(url)
  if (uri.scheme?.lowercase() !in setOf("http", "https")) {
    return
  }
  try {
    startActivity(
      Intent(Intent.ACTION_VIEW).apply {
        data = uri
        // A context outside an activity has no task to open the browser into, and starting one
        // without this flag throws.
        if (findActivity() == null) {
          addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
      }
    )
  } catch (exception: ActivityNotFoundException) {
    Toast.makeText(this, R.string.mapbox_attributionErrorNoBrowser, Toast.LENGTH_LONG).show()
  } catch (throwable: Throwable) {
    Toast.makeText(this, throwable.localizedMessage, Toast.LENGTH_LONG).show()
  }
}

/**
 * The [Activity] hosting this context, or null when there is none.
 *
 * Unwrapped rather than cast: the context is usually a [ContextWrapper] around the activity -
 * `android:theme` on the MapView or any ancestor, a themed inflater and Compose interop all wrap
 * it - and a plain cast would report no activity at all.
 */
internal fun Context.findActivity(): Activity? {
  var current: Context? = this
  while (current is ContextWrapper) {
    if (current is Activity) {
      return current
    }
    current = current.baseContext
  }
  return null
}
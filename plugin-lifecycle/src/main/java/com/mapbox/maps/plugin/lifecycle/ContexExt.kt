@file:JvmName("ContextUtils")
package com.mapbox.maps.plugin.lifecycle

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.lifecycle.LifecycleOwner
import com.mapbox.maps.logD

internal tailrec fun Context.recursiveUnwrap(): Context =
  if (this !is Activity && this is ContextWrapper) {
    logD("testtest", "recursiveUnwrap: $this")
    this.baseContext.recursiveUnwrap()
  } else {
    this
  }

internal fun Context.toLifecycleOwner(): LifecycleOwner {
  logD("testtest", "toLifecycleOwner: $this")
  val lifecycleOwner = this.recursiveUnwrap() as? LifecycleOwner
  checkNotNull(lifecycleOwner) {
    "Please ensure that the hosting Context is a valid LifecycleOwner"
  }
  return lifecycleOwner
}
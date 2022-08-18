package com.mapbox.maps.plugin.animation.threads

import android.os.Handler
import android.os.Looper
import androidx.annotation.VisibleForTesting

internal interface AnimatorThread {
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  val handler: Handler?

  fun onAttached(): AnimatorThread
  fun onDetached()

  fun post(runnable: Runnable): Boolean {
    return if (Looper.myLooper() == handler?.looper) {
      runnable.run()
      true
    } else {
      handler?.post(runnable) ?: false
    }
  }
}
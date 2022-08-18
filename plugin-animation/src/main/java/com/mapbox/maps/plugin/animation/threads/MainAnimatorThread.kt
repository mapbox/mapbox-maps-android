package com.mapbox.maps.plugin.animation.threads

import android.os.Handler
import android.os.Looper

internal class MainAnimatorThread : AnimatorThread {
  override var handler: Handler? = null
    private set

  override fun onAttached() = apply {
    handler = Handler(Looper.getMainLooper())
  }

  override fun onDetached() {
    handler?.removeCallbacksAndMessages(null)
    handler = null
  }
}
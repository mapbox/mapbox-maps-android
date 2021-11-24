package com.mapbox.maps

import android.view.View
import androidx.core.view.children
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

internal fun MapView.hasChildView(view: View): Boolean {
  children.forEach { v ->
    if (v == view) {
      return true
    }
  }
  return false
}

internal fun MapView.getChildViewIndex(view: View): Int {
  children.forEachIndexed { i, v ->
    if (v == view) {
      return i
    }
  }
  return -1
}

internal fun CountDownLatch.throwExceptionOnTimeoutMs(timeoutMs: Long) {
  if (!await(timeoutMs, TimeUnit.MILLISECONDS)) {
    throw TimeoutException()
  }
}
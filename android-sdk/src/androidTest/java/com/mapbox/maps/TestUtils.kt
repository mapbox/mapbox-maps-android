package com.mapbox.maps

import android.view.View
import android.widget.FrameLayout
import androidx.core.view.children
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

internal fun FrameLayout.hasChildView(view: View) = children.any { it == view }

internal fun FrameLayout.getChildViewIndex(view: View) = children.indexOf(view)

internal fun CountDownLatch.throwExceptionOnTimeoutMs(timeoutMs: Long = DEFAULT_LATCH_TIMEOUT_MS) {
  if (!await(timeoutMs, TimeUnit.MILLISECONDS)) {
    throw TimeoutException()
  }
}

internal const val DEFAULT_LATCH_TIMEOUT_MS = 10_000L
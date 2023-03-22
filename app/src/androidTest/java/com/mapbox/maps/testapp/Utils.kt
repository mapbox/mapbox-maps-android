package com.mapbox.maps.testapp

import androidx.test.ext.junit.rules.ActivityScenarioRule
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun withLatch(
  count: Int = 1,
  timeoutMillis: Long = 3000,
  lambda: (CountDownLatch) -> Unit
) {
  val latch = CountDownLatch(count)
  lambda(latch)
  if (!latch.await(timeoutMillis, TimeUnit.MILLISECONDS)) {
    throw TimeoutException()
  }
}

fun ActivityScenarioRule<EmptyActivity>.runOnUiThread(action: (EmptyActivity) -> Unit) {
  this.scenario.onActivity { activity ->
    activity.runOnUiThread { action(activity) }
  }
}
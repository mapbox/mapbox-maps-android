package com.mapbox.maps.testapp

import android.content.Context
import android.util.AttributeSet
import android.util.Xml
import androidx.annotation.LayoutRes
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.xmlpull.v1.XmlPullParser
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

/**
 * Attributes of the root view in [layoutId], to build that view from code.
 */
fun Context.layoutAttributes(@LayoutRes layoutId: Int): AttributeSet {
  val parser = resources.getLayout(layoutId)
  while (parser.next() != XmlPullParser.START_TAG) Unit
  return Xml.asAttributeSet(parser)
}
package com.mapbox.maps.plugin.attribution

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows

/**
 * Unit tests for [openAttributionUrl].
 *
 * Ported from the tests of the removed AlertDialog attribution list, which covered this same
 * scheme filtering while it still lived in [AttributionDialogManagerImpl].
 */
@RunWith(RobolectricTestRunner::class)
class AttributionUrlTest {

  private lateinit var activity: AppCompatActivity

  @Before
  fun setUp() {
    activity = Robolectric.buildActivity(AppCompatActivity::class.java).get()
  }

  private fun startedIntent(url: String): Intent? {
    activity.openAttributionUrl(url)
    return Shadows.shadowOf(activity).nextStartedActivity
  }

  @Test
  fun httpsUrlOpensWebPage() {
    val intent = startedIntent("https://www.openstreetmap.org/copyright")

    assertNotNull(intent)
    assertEquals(Intent.ACTION_VIEW, intent!!.action)
    assertEquals("https://www.openstreetmap.org/copyright", intent.data.toString())
  }

  @Test
  fun httpUrlOpensWebPage() {
    assertNotNull(startedIntent("http://www.openstreetmap.org/copyright"))
  }

  @Test
  fun telSchemeDoesNotLaunchIntent() {
    assertNull(startedIntent("tel:+19001234567"))
  }

  @Test
  fun smsSchemeDoesNotLaunchIntent() {
    assertNull(startedIntent("sms:+19001234567"))
  }

  @Test
  fun marketSchemeDoesNotLaunchIntent() {
    assertNull(startedIntent("market://details?id=com.malicious.app"))
  }

  @Test
  fun emptyUrlDoesNotLaunchIntent() {
    assertNull(startedIntent(""))
  }

  @Test
  fun activityContextDoesNotNeedANewTask() {
    val intent = startedIntent("https://www.mapbox.com/about/maps/")

    assertEquals(0, intent!!.flags and Intent.FLAG_ACTIVITY_NEW_TASK)
  }
}
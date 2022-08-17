package com.mapbox.maps.plugin.animation

import android.os.Build
import android.os.Looper
import android.os.Looper.getMainLooper
import com.mapbox.common.MapboxSDKCommon
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.logW
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@OptIn(MapboxExperimental::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
@LooperMode(LooperMode.Mode.PAUSED)
class MapboxAnimatorThreadTest {

  // All tests will keep track of the looper that was used for the runnable.
  private val looperSlot = mutableListOf<Looper?>()
  private val runnable = mockk<Runnable> {
    every { run() } answers {
      looperSlot.add(Looper.myLooper())
    }
  }

  private val manifestMetaData: AndroidManifestMetaData = mockk()
  private val mapboxAnimatorThread = MapboxAnimatorThread(manifestMetaData)

  @Before
  fun setup() {
    mockkObject(MapboxSDKCommon)
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logW(any(), any()) } just Runs
  }

  @After
  fun teardown() {
    unmockkAll()
  }

  @Test
  fun `should run post on main looper by default`() {
    every { manifestMetaData.backgroundAnimatorThreadEnabled() } returns false
    val mapboxAnimatorThread = MapboxAnimatorThread(manifestMetaData)

    mapboxAnimatorThread.onAttached()
    val posted = mapboxAnimatorThread.post(runnable)

    assertTrue(posted)
    assertEquals(1, looperSlot.size)
    assertEquals(getMainLooper(), looperSlot[0])
  }

  @Test
  fun `should run post on background looper when metadata is enabled`() {
    every { manifestMetaData.backgroundAnimatorThreadEnabled() } returns true

    mapboxAnimatorThread.onAttached()
    val posted = mapboxAnimatorThread.post(runnable)
    shadowOf(mapboxAnimatorThread.backgroundLooper).idle()

    assertTrue(posted)
    assertEquals(1, looperSlot.size)
    assertEquals(mapboxAnimatorThread.backgroundLooper, looperSlot[0])
  }

  @Test
  fun `should not post when animator thread is not attached`() {
    every { manifestMetaData.backgroundAnimatorThreadEnabled() } returns true

    val posted = mapboxAnimatorThread.post(runnable)
    val postedMain = mapboxAnimatorThread.postMain(runnable)

    assertFalse(posted)
    assertFalse(postedMain)
    shadowOf(getMainLooper()).idle()
    assertTrue(looperSlot.isEmpty())
  }

  @Test
  fun `should run immediately when called from the main looper`() {
    every { manifestMetaData.backgroundAnimatorThreadEnabled() } returns false

    mapboxAnimatorThread.onAttached()
    val posted = mapboxAnimatorThread.postMain(runnable)
    assertTrue(posted)

    assertEquals(1, looperSlot.size)
    assertEquals(getMainLooper(), looperSlot[0])
  }

  @Test
  fun `should run immediately when called from the background looper`() {
    every { manifestMetaData.backgroundAnimatorThreadEnabled() } returns true

    mapboxAnimatorThread.onAttached()
    mapboxAnimatorThread.post {
      // Run to next task will trigger this one but not the next. So this will ensure that the
      // background thread is called in place from the same background thread.
      mapboxAnimatorThread.post(runnable)
      assertEquals(1, looperSlot.size)
      assertEquals(mapboxAnimatorThread.backgroundLooper!!, looperSlot[0])
    }
    shadowOf(mapboxAnimatorThread.backgroundLooper!!).runToNextTask()
  }

  @Test
  fun `should not post runnable when detached`() {
    every { manifestMetaData.backgroundAnimatorThreadEnabled() } returns true

    mapboxAnimatorThread.onAttached()
    mapboxAnimatorThread.post(runnable)
    mapboxAnimatorThread.onDetached()
    mapboxAnimatorThread.post(runnable)

    assertEquals(1, looperSlot.size)
    assertNull(mapboxAnimatorThread.backgroundLooper)
  }

  @Test
  fun `attaching and detaching will create a new background looper`() {
    every { manifestMetaData.backgroundAnimatorThreadEnabled() } returns true

    mapboxAnimatorThread.onAttached()
    mapboxAnimatorThread.post(runnable)
    shadowOf(mapboxAnimatorThread.backgroundLooper!!).idle()
    mapboxAnimatorThread.onDetached()
    mapboxAnimatorThread.onAttached()
    mapboxAnimatorThread.post(runnable)
    shadowOf(mapboxAnimatorThread.backgroundLooper!!).idle()
    mapboxAnimatorThread.onDetached()

    assertEquals(2, looperSlot.size)
    assertNotEquals(looperSlot[0], looperSlot[1])
  }

  @Test
  fun `attaching and detaching will use the same main looper`() {
    every { manifestMetaData.backgroundAnimatorThreadEnabled() } returns false

    mapboxAnimatorThread.onAttached()
    mapboxAnimatorThread.post(runnable)
    shadowOf(getMainLooper()).idle()
    mapboxAnimatorThread.onDetached()
    mapboxAnimatorThread.onAttached()
    mapboxAnimatorThread.post(runnable)
    shadowOf(getMainLooper()).idle()
    mapboxAnimatorThread.onDetached()

    assertEquals(2, looperSlot.size)
    assertEquals(looperSlot[0], looperSlot[1])
  }
}
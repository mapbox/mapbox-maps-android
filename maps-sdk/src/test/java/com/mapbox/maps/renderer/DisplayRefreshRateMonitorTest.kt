package com.mapbox.maps.renderer

import android.hardware.display.DisplayManager
import android.os.Handler
import android.view.Display
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DisplayRefreshRateMonitorTest {

  private val displayManager: DisplayManager = mockk(relaxed = true)
  private val mainHandler: Handler = mockk(relaxed = true)
  private val display: Display = mockk()

  /** Captures invocations of the refresh-rate callback for assertion. */
  private val receivedRates = mutableListOf<Int>()
  private val onRefreshRateChanged: (Int) -> Unit = { receivedRates.add(it) }

  private fun newMonitor(): DisplayRefreshRateMonitor {
    every { displayManager.getDisplay(TARGET_DISPLAY_ID) } returns display
    every { display.refreshRate } returns 60f
    return DisplayRefreshRateMonitor(
      displayId = TARGET_DISPLAY_ID,
      onRefreshRateChanged = onRefreshRateChanged,
      displayManager = displayManager,
      mainHandler = mainHandler,
    )
  }

  @Test
  fun `start registers a display listener on the main handler`() {
    val monitor = newMonitor()

    monitor.start()

    verify(exactly = 1) {
      displayManager.registerDisplayListener(any(), mainHandler)
    }
  }

  @Test
  fun `stop unregisters the display listener`() {
    val monitor = newMonitor()
    monitor.start()

    monitor.stop()

    verify(exactly = 1) {
      displayManager.unregisterDisplayListener(any())
    }
  }

  @Test
  fun `onDisplayChanged for our display invokes callback with the current refresh rate`() {
    val listenerSlot = slot<DisplayManager.DisplayListener>()
    every { displayManager.registerDisplayListener(capture(listenerSlot), any()) } just Runs
    val monitor = newMonitor()
    monitor.start()

    // Simulate the panel switching to 30 Hz, then fire the listener as the framework would.
    every { display.refreshRate } returns 30f
    listenerSlot.captured.onDisplayChanged(TARGET_DISPLAY_ID)

    assertEquals(listOf(30), receivedRates)
  }

  @Test
  fun `onDisplayChanged for a different display is ignored`() {
    val listenerSlot = slot<DisplayManager.DisplayListener>()
    every { displayManager.registerDisplayListener(capture(listenerSlot), any()) } just Runs
    val monitor = newMonitor()
    monitor.start()

    // DisplayManager fires for every display on the device (foldable panels, external monitors,
    // virtual displays). A change on a display we're not rendering to must not push a rate
    // update -- it would set FpsManager to a rate that doesn't reflect our panel.
    listenerSlot.captured.onDisplayChanged(TARGET_DISPLAY_ID + 1)

    assertEquals(emptyList<Int>(), receivedRates)
  }

  @Test
  fun `onDisplayChanged fired after stop is a no-op`() {
    val listenerSlot = slot<DisplayManager.DisplayListener>()
    every { displayManager.registerDisplayListener(capture(listenerSlot), any()) } just Runs
    val monitor = newMonitor()
    monitor.start()
    monitor.stop()

    // unregisterDisplayListener doesn't remove pending Handler messages, so a callback enqueued
    // before stop() can still drain after. Replicate that race here: a queued callback firing
    // post-stop must not call onRefreshRateChanged. Reproduces the CI crash sequence at
    // screen-off where the queued display-change Runnable would otherwise invoke the listener
    // body on a torn-down view.
    listenerSlot.captured.onDisplayChanged(TARGET_DISPLAY_ID)

    assertEquals(emptyList<Int>(), receivedRates)
  }

  @Test
  fun `onDisplayChanged when DisplayManager returns no display is a no-op`() {
    val listenerSlot = slot<DisplayManager.DisplayListener>()
    every { displayManager.registerDisplayListener(capture(listenerSlot), any()) } just Runs
    // The target display was removed between start() and the callback firing.
    every { displayManager.getDisplay(TARGET_DISPLAY_ID) } returns null
    val monitor = DisplayRefreshRateMonitor(
      displayId = TARGET_DISPLAY_ID,
      onRefreshRateChanged = onRefreshRateChanged,
      displayManager = displayManager,
      mainHandler = mainHandler,
    )
    monitor.start()

    listenerSlot.captured.onDisplayChanged(TARGET_DISPLAY_ID)

    assertEquals(emptyList<Int>(), receivedRates)
  }

  @Test
  fun `null displayId skips display listener registration`() {
    // Resolver returned null at construction (non-visual context, no attached display, etc.).
    // The monitor should not subscribe to DisplayManager at all: registering a listener that
    // would filter out every event is wasted work and adds an unused entry to the system
    // listener list.
    val monitor = DisplayRefreshRateMonitor(
      displayId = null,
      onRefreshRateChanged = onRefreshRateChanged,
      displayManager = displayManager,
      mainHandler = mainHandler,
    )

    monitor.start()
    monitor.stop()

    verify(exactly = 0) { displayManager.registerDisplayListener(any(), any()) }
    verify(exactly = 0) { displayManager.unregisterDisplayListener(any()) }
    assertEquals(emptyList<Int>(), receivedRates)
  }

  @Test
  fun `start is idempotent`() {
    val monitor = newMonitor()

    monitor.start()
    monitor.start()

    verify(exactly = 1) {
      displayManager.registerDisplayListener(any(), mainHandler)
    }
  }

  @Test
  fun `stop without prior start is a no-op`() {
    val monitor = newMonitor()

    monitor.stop()

    verify(exactly = 0) {
      displayManager.unregisterDisplayListener(any())
    }
  }

  @Test
  fun `restart after stop registers the listener again`() {
    val monitor = newMonitor()

    monitor.start()
    monitor.stop()
    monitor.start()

    // started flag must reset on stop so a fresh start re-registers.
    verify(exactly = 2) {
      displayManager.registerDisplayListener(any(), mainHandler)
    }
    verify(exactly = 1) {
      displayManager.unregisterDisplayListener(any())
    }
  }

  private companion object {
    private const val TARGET_DISPLAY_ID = 0
  }
}
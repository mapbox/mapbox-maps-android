package com.mapbox.maps.renderer

import android.content.Context
import android.hardware.display.DisplayManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.Display
import android.view.WindowManager
import androidx.annotation.VisibleForTesting
import com.mapbox.maps.logI
import com.mapbox.maps.logW

/**
 * Listens for display configuration changes and forwards the current refresh rate. Used to keep
 * [FpsManager.setScreenRefreshRate] in sync with the panel's effective rate (which can change due
 * to VRR mode switches or a per-UID `frameRateOverride`, including the one triggered by
 * [android.view.Surface.setFrameRate]).
 *
 * Lifecycle: caller invokes [start] when the associated view is attached / active, and [stop] when
 * detached. Callback fires on the main thread.
 *
 * The target display is captured once at construction. If the host Activity is moved to a
 * different display while the monitor is alive, Android's default behavior is to recreate the
 * Activity (and the MapView with it), producing a fresh monitor with the new display id. Hosts
 * that opt out via `android:configChanges` won't see refresh-rate updates on the new display
 * until the next start/stop cycle.
 */
internal class DisplayRefreshRateMonitor @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE) constructor(
  private val displayId: Int?,
  private val onRefreshRateChanged: (Int) -> Unit,
  private val displayManager: DisplayManager,
  private val mainHandler: Handler,
) {

  /**
   * Tracks the display the [context] is currently bound to (Activity's display in the typical
   * single-display case; the relevant external/foldable panel in multi-display setups). If the
   * context is not associated with a display -- e.g. a caller passed `applicationContext` -- the
   * monitor degrades to a no-op and logs a warning; see [currentDisplayId].
   */
  constructor(context: Context, onRefreshRateChanged: (Int) -> Unit) : this(
    displayId = currentDisplayId(context),
    onRefreshRateChanged = onRefreshRateChanged,
    displayManager = context.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager,
    mainHandler = Handler(Looper.getMainLooper()),
  )

  private var started = false

  private val listener = object : DisplayManager.DisplayListener {
    override fun onDisplayAdded(displayId: Int) = Unit
    override fun onDisplayRemoved(displayId: Int) = Unit
    override fun onDisplayChanged(changedDisplayId: Int) {
      // unregisterDisplayListener doesn't remove pending Handler messages, so a callback queued
      // before stop() can still fire afterwards.
      if (!started) return
      // DisplayManager fires for every display on the device; ignore changes on others.
      if (changedDisplayId != displayId) return
      val rate = displayManager.getDisplay(displayId)?.refreshRate?.toInt() ?: return
      logI(TAG, "Display $displayId changed; refresh rate now ${rate}Hz")
      onRefreshRateChanged(rate)
    }
  }

  fun start() {
    if (started) return
    started = true
    // No display to monitor (non-visual context, etc.); skip the system-side registration
    // entirely rather than register a listener that would filter out every event.
    if (displayId == null) return
    displayManager.registerDisplayListener(listener, mainHandler)
    logI(TAG, "Started listening for display $displayId refresh rate changes")
  }

  fun stop() {
    if (!started) return
    started = false
    if (displayId == null) return
    displayManager.unregisterDisplayListener(listener)
    logI(TAG, "Stopped listening for display $displayId refresh rate changes")
  }

  private companion object {
    private const val TAG = "Mbgl-DisplayRefreshRateMonitor"

    @Suppress("DEPRECATION")
    private fun currentDisplayId(context: Context): Int? = try {
      val display: Display? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        context.display
      } else {
        // Pre-API-30: WindowManager.getDefaultDisplay() returns the display the WindowManager is
        // bound to. For an Activity-scoped context (the typical case), that's the activity's
        // current display -- so this correctly handles multi-display setups. Verified by the
        // "pre-API-30 reads activity's display rate, not the system-primary display rate" test.
        (context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager)?.defaultDisplay
      }
      display?.displayId
    } catch (e: UnsupportedOperationException) {
      // Caller passed a non-visual context (e.g. applicationContext). On API 30+,
      // Context.getDisplay() rejects those. Degrade to a no-op monitor rather than crashing --
      // the null displayId is filtered in onDisplayChanged so no rate updates flow, and the
      // FpsManager retains its DEFAULT_FPS until the caller wires up a visual context.
      logW(TAG, "Could not resolve display from context: ${e.message}. Refresh-rate updates disabled; pass a visual context (Activity / WindowContext / DisplayContext) to enable.")
      null
    }
  }
}
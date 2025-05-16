package com.mapbox.maps.testapp.examples

import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.os.Process
import android.util.Log
import com.mapbox.maps.testapp.examples.Watchdog.TIME_TO_FIRST_TRIGGER
import com.mapbox.maps.testapp.examples.Watchdog.TIME_TO_SUBSEQUENT_TRIGGER
import com.mapbox.maps.testapp.examples.Watchdog.reschedule
import com.mapbox.maps.testapp.examples.Watchdog.stop

/**
 * A simple watchdog that will trigger a [Process.SIGNAL_QUIT] signal if [reschedule] is not called
 * within [TIME_TO_FIRST_TRIGGER] milliseconds and continues to do so every
 * [TIME_TO_SUBSEQUENT_TRIGGER] until [reschedule] or [stop] is called.
 */
object Watchdog {
  var enabled = false
    set(value) {
      if (field == value) return
      field = value
      if (value) {
        // Start the watchdog thread when enabled to avoid unnecessary overhead
        watchdogHandlerThread = HandlerThread(TAG).apply { start() }
        watchdogHandler = Handler(watchdogHandlerThread!!.looper)
      } else {
        // Stop the watchdog thread and free properties when disabled to avoid unnecessary overhead
        stop()
        watchdogHandlerThread?.quit()
        watchdogHandler = null
        watchdogHandlerThread = null
      }
    }

  private var watchdogHandlerThread: HandlerThread? = null
  private var watchdogHandler: Handler? = null
  private var currentCounter = 0

  private val quitSignalTask: () -> Unit = {
    Log.w(TAG, "(${currentCounter++}) Task not rescheduled on time. Triggering SIGNAL_QUIT.")
    // Send a quit signal to the current process to write a thread dump to `/data/anr/`.
    Process.sendSignal(Process.myPid(), Process.SIGNAL_QUIT)
    scheduleQuitSignalTask()
  }

  private fun scheduleQuitSignalTask() {
    if (currentCounter >= MAX_CONSECUTIVE_TRIGGERS) {
      Log.w(
        TAG,
        "Max consecutive triggers ($currentCounter) reached. Not scheduling another trigger."
      )
      return
    }
    if (enabled) {
      watchdogHandler?.let {
        it.sendMessageDelayed(Message.obtain(it, quitSignalTask), TIME_TO_SUBSEQUENT_TRIGGER)
      }
    }
  }

  fun reschedule() {
    if (enabled) {
      stop()
      watchdogHandler?.let {
        it.sendMessageDelayed(Message.obtain(it, quitSignalTask), TIME_TO_FIRST_TRIGGER)
      }
    }
  }

  fun stop() {
    // Cancel all pending tasks
    watchdogHandler?.removeCallbacksAndMessages(null)
    currentCounter = 0
  }

  private const val TAG = "Watchdog"

  /**
   * The amount of time that need to pass before the watchdog triggers the first time.
   * That is, if [reschedule] is not called within this time, the watchdog task will trigger.
   * Unit is milliseconds.
   */
  private const val TIME_TO_FIRST_TRIGGER: Long = 50L

  /**
   * The amount of time that the task will wait before running again.
   * Unit is milliseconds.
   */
  private const val TIME_TO_SUBSEQUENT_TRIGGER: Long = 100L

  private const val MAX_CONSECUTIVE_TRIGGERS = 5
}
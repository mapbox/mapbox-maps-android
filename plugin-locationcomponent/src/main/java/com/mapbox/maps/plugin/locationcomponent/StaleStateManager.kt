package com.mapbox.maps.plugin.locationcomponent

import android.os.Handler
import android.os.Message
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import com.mapbox.maps.plugin.locationcomponent.listeneres.OnLocationStaleListener
import java.lang.ref.WeakReference

/**
 * Class controls the location stale state when the [android.location.Location] hasn't
 * been updated in 'x' amount of time. [LocationComponentSettings.staleStateTimeout] can be used to
 * control the amount of time before the location's considered stale.
 * [LocationComponentSettings.staleStateEnabled] is available for disabling this behaviour.
 */
internal class StaleStateManager(
  private val innerOnLocationStaleListeners: OnLocationStaleListener,
  private var isEnabled: Boolean,
  private var delayTime: Long
) {
  private val handler: StaleMessageHandler
  var isStale = true
    private set
  private val staleStateMessage = 1

  init {
    handler = StaleMessageHandler(this)
  }

  fun setEnabled(enabled: Boolean) {
    if (enabled) {
      setState(isStale)
    } else if (isEnabled) {
      onStop()
      innerOnLocationStaleListeners.onStaleStateChange(false)
    }
    isEnabled = enabled
  }

  fun updateLatestLocationTime() {
    setState(false)
    postTheCallback()
  }

  fun setDelayTime(delayTime: Long) {
    this.delayTime = delayTime
    if (handler.hasMessages(staleStateMessage)) {
      postTheCallback()
    }
  }

  fun onStart() {
    if (!isStale) {
      postTheCallback()
    }
  }

  fun onStop() {
    handler.removeCallbacksAndMessages(null)
  }

  private fun postTheCallback() {
    handler.removeCallbacksAndMessages(null)
    handler.sendEmptyMessageDelayed(staleStateMessage, delayTime)
  }

  private fun setState(stale: Boolean) {
    if (stale != isStale) {
      isStale = stale
      if (isEnabled) {
        innerOnLocationStaleListeners.onStaleStateChange(stale)
      }
    }
  }

  private class StaleMessageHandler internal constructor(staleStateManager: StaleStateManager) :
    Handler() {
    private val managerWeakReference: WeakReference<StaleStateManager> = WeakReference(
      staleStateManager
    )

    override fun handleMessage(msg: Message) {
      val manager =
        managerWeakReference.get()
      manager?.setState(true)
    }
  }
}
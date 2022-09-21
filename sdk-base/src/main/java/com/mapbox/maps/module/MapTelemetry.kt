package com.mapbox.maps.module

import android.os.Bundle

/**
 * Definition of map telemetry
 */
interface MapTelemetry {

  /**
   * Register the app user turnstile event
   */
  fun onAppUserTurnstileEvent()

  /**
   * Set the end-user selected state to participate or opt-out in telemetry collection.
   */
  fun setUserTelemetryRequestState(enabled: Boolean)

  /**
   * Disables a started telemetry service for this session only.
   */
  fun disableTelemetrySession()

  /**
   * Set the debug logging enabled states.
   *
   * Note this method is deprecated, there will be no operations when it is called.
   *
   * @param debugLoggingEnabled whether to enable the debug logging for telemetry.
   */
  @Deprecated("setDebugLoggingEnabled has been deprecated and will do no operations")
  fun setDebugLoggingEnabled(debugLoggingEnabled: Boolean)

  /**
   * Set the telemetry rotation session id interval.
   *
   * Note this method is deprecated, there will be no operations when it is called.
   *
   * @param interval the selected session interval
   * @return true if rotation session id was updated
   */
  @Deprecated("setSessionIdRotationInterval has been deprecated and will do no operations")
  fun setSessionIdRotationInterval(interval: Int): Boolean

  /**
   * Register a performance event.
   *
   * @param data performance event data
   */
  fun onPerformanceEvent(data: Bundle?)
}
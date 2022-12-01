package com.mapbox.maps.module;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.mapbox.common.TelemetryUtils;

/**
 * Definition of map telemetry
 */
public interface MapTelemetry {

  /**
   * Register the app user turnstile event
   */
  void onAppUserTurnstileEvent();

  /**
   * Set the end-user selected state to participate or opt-out in telemetry collection.
   */
  void setUserTelemetryRequestState(boolean enabled);

  /**
   * Disables a started telemetry service for this session only.
   */
  void disableTelemetrySession();

  /**
   * Set the debug logging enabled states.
   *
   * @deprecated
   * Note this method is deprecated, there will be no operations when it is called.
   *
   * @param debugLoggingEnabled whether to enable the debug logging for telemetry.
   */
  @Deprecated
  void setDebugLoggingEnabled(boolean debugLoggingEnabled);

  /**
   * Set the telemetry rotation session id interval.
   *
   * @deprecated
   * Note this method is deprecated, there will be no operations when it is called.
   *
   * @param interval the selected session interval
   * @return true if rotation session id was updated
   */
  @Deprecated
  boolean setSessionIdRotationInterval(int interval);

  /**
   * Register a performance event.
   *
   * @param data performance event data
   */
  void onPerformanceEvent(@Nullable Bundle data);

  /**
   * Get the end-user selected state to participate or opt-out in telemetry collection.
   *
   * @return true if end-user opted-in in telemetry collection, false otherwise.
   */
  default boolean getUserTelemetryRequestState() {
    return TelemetryUtils.getEventsCollectionState();
  }
}
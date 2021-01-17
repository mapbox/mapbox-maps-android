package com.mapbox.maps.module.telemetry

import android.content.Context
import android.os.Bundle
import androidx.annotation.VisibleForTesting
import com.mapbox.android.telemetry.*
import com.mapbox.annotation.module.MapboxModule
import com.mapbox.annotation.module.MapboxModuleType
import com.mapbox.maps.module.MapTelemetry
import java.util.*

/**
 * Concrete implementation of map telemetry.
 */
@MapboxModule(MapboxModuleType.MapTelemetry)
class MapTelemetryImpl : MapTelemetry {

  private val appContext: Context
  private val accessToken: String
  private val telemetry: MapboxTelemetry

  /**
   * Creates a map telemetry instance using appplication context and access token
   *
   * @param appContext the application context
   * @param accessToken the mapbox access token
   */
  constructor(appContext: Context, accessToken: String) {
    this.appContext = appContext
    this.accessToken = accessToken
    this.telemetry = MapboxTelemetry(appContext, accessToken, BuildConfig.MAPBOX_EVENTS_USER_AGENT)
    val telemetryState: TelemetryEnabler.State =
      TelemetryEnabler.retrieveTelemetryStateFromPreferences()
    if (TelemetryEnabler.State.ENABLED == telemetryState) {
      telemetry.enable()
    }
  }

  /**
   * Creates a map telemetry instance using telemetry, appplication context and access token
   *
   * @param telemetry the mapbox telemetry
   * @param appContext the application context
   * @param accessToken the mapbox access token
   */
  @VisibleForTesting
  constructor(telemetry: MapboxTelemetry, appContext: Context, accessToken: String) {
    this.appContext = appContext
    this.accessToken = accessToken
    this.telemetry = telemetry
  }

  /**
   * Register the app user turnstile event
   */
  override fun onAppUserTurnstileEvent() {
    val turnstileEvent = AppUserTurnstile(
      BuildConfig.MAPBOX_SDK_IDENTIFIER,
      BuildConfig.MAPBOX_SDK_VERSION
    )
    turnstileEvent.setSkuId("00") // id MAUS MAPS
    telemetry.push(turnstileEvent)
    telemetry.push(MapEventFactory.buildMapLoadEvent(PhoneState(appContext)))
  }

  /**
   * Set the end-user selected state to participate or opt-out in telemetry collection.
   *
   * @param enabled true if enabled, false otherwise
   */
  override fun setUserTelemetryRequestState(enabled: Boolean) {
    if (enabled) {
      TelemetryEnabler.updateTelemetryState(TelemetryEnabler.State.ENABLED)
      telemetry.enable()
    } else {
      telemetry.disable()
      TelemetryEnabler.updateTelemetryState(TelemetryEnabler.State.DISABLED)
    }
  }

  /**
   * Disables a started telemetry service for this session only.
   */
  override fun disableTelemetrySession() {
    telemetry.disable()
  }

  /**
   * Set the debug logging state of telemetry.
   *
   * @param debugLoggingEnabled true to enable logging
   */
  override fun setDebugLoggingEnabled(debugLoggingEnabled: Boolean) {
    telemetry.updateDebugLoggingEnabled(debugLoggingEnabled)
  }

  /**
   * Set the telemetry rotation session id interval
   *
   * @param interval the selected session interval
   * @return true if rotation session id was updated
   */
  override fun setSessionIdRotationInterval(interval: Int): Boolean {
    return telemetry.updateSessionIdRotationInterval(SessionInterval(interval))
  }

  /**
   * Register a performance event
   *
   * @param data performance event data
   */
  override fun onPerformanceEvent(data: Bundle?) {
    telemetry.push(
      MapEventFactory.buildPerformanceEvent(
        PhoneState(appContext),
        UUID.randomUUID().toString(), data ?: Bundle()
      )
    )
  }
}
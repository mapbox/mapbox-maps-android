package com.mapbox.maps.module.telemetry

import android.content.Context
import android.os.Bundle
import androidx.annotation.VisibleForTesting
import com.mapbox.annotation.module.MapboxModule
import com.mapbox.annotation.module.MapboxModuleType
import com.mapbox.bindgen.Value
import com.mapbox.common.*
import com.mapbox.maps.module.MapTelemetry

/**
 * Concrete implementation of map telemetry.
 */
@MapboxModule(MapboxModuleType.MapTelemetry)
class MapTelemetryImpl : MapTelemetry {

  private val appContext: Context
  private val accessToken: String
  private val eventsService: EventsService
  private val eventsServiceObserver = object : EventsServiceObserver() {
    override fun didEncounterError(error: EventsServiceError, events: Value) {
      Logger.e(
        TAG,
        "didEncounterError --> ${error.code}, ${error.message}, events: ${events.toJson()}"
      )
    }

    override fun didSendEvents(events: Value) {
      Logger.e(TAG, "didSendEvents --> events: ${events.toJson()}")
    }
  }

  /**
   * Creates a map telemetry instance using application context and access token
   *
   * @param appContext the application context
   * @param accessToken the mapbox access token
   */
  constructor(appContext: Context, accessToken: String) {
    this.appContext = appContext
    this.accessToken = accessToken
    this.eventsService =
      EventsService(EventsServiceOptions(accessToken, BuildConfig.MAPBOX_EVENTS_USER_AGENT, null))
    val telemetryState: TelemetryEnabler.State =
      TelemetryEnabler.retrieveTelemetryStateFromPreferences(appContext)
    if (TelemetryEnabler.State.ENABLED == telemetryState) {
      enableTelemetryCollection(true)
    }

    eventsService.registerObserver(eventsServiceObserver)
  }

  /**
   * Creates a map telemetry instance using telemetry, application context and access token
   *
   * @param eventsService the mapbox events service
   * @param appContext the application context
   * @param accessToken the mapbox access token
   */
  @VisibleForTesting
  constructor(eventsService: EventsService, appContext: Context, accessToken: String) {
    this.appContext = appContext
    this.accessToken = accessToken
    this.eventsService = eventsService
  }

  /**
   * Register the app user turnstile event
   */
  override fun onAppUserTurnstileEvent() {
    eventsService.sendTurnstileEvent(
      TurnstileEvent(
        SKUIdentifier.MAPS_MAUS,
        BuildConfig.MAPBOX_SDK_IDENTIFIER,
        BuildConfig.MAPBOX_SDK_VERSION
      )
    ) { error ->
      error.log()
    }
    // TODO: Add telemetry.push(MapEventFactory.buildMapLoadEvent(PhoneState(appContext)))
  }

  private fun enableTelemetryCollection(enabled: Boolean) {
    EventsService.setEventsCollectionState(enabled) { error ->
      error.log()
    }
  }

  /**
   * Set the end-user selected state to participate or opt-out in telemetry collection.
   *
   * @param enabled true if enabled, false otherwise
   */
  override fun setUserTelemetryRequestState(enabled: Boolean) {
    if (enabled) {
      TelemetryEnabler.updateTelemetryState(appContext, TelemetryEnabler.State.ENABLED)
      enableTelemetryCollection(true)
    } else {
      enableTelemetryCollection(false)
      TelemetryEnabler.updateTelemetryState(appContext, TelemetryEnabler.State.DISABLED)
    }
  }

  /**
   * Disables a started telemetry service for this session only.
   */
  override fun disableTelemetrySession() {
    enableTelemetryCollection(false)
  }

  /**
   * Set the debug logging state of telemetry.
   *
   * @param debugLoggingEnabled true to enable logging
   */
  override fun setDebugLoggingEnabled(debugLoggingEnabled: Boolean) {
    // TODO, no matching API for core telemetry
  }

  /**
   * Set the telemetry rotation session id interval
   *
   * @param interval the selected session interval
   * @return true if rotation session id was updated
   */
  override fun setSessionIdRotationInterval(interval: Int): Boolean {
    // TODO, no matching API for core telemetry
    return false
  }

  /**
   * Register a performance event
   *
   * @param data performance event data
   */
  override fun onPerformanceEvent(data: Bundle?) {
    // TODO
//    telemetry.push(
//      MapEventFactory.buildPerformanceEvent(
//        PhoneState(appContext),
//        UUID.randomUUID().toString(), data ?: Bundle()
//      )
//    )
  }

  override fun onDestroy() {
    eventsService.unregisterObserver(eventsServiceObserver)
  }

  private fun EventsServiceError?.log() {
    this?.let {
      Logger.e(TAG, "${it.code}: ${it.message}")
    }
  }

  companion object {
    private const val TAG = "MapTelemetryImpl"
  }
}
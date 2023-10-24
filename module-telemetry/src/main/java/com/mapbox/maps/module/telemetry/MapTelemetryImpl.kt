package com.mapbox.maps.module.telemetry

import android.content.Context
import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.Companion.PRIVATE
import com.google.gson.Gson
import com.mapbox.annotation.module.MapboxModule
import com.mapbox.annotation.module.MapboxModuleType
import com.mapbox.bindgen.Value
import com.mapbox.common.*
import com.mapbox.maps.base.BuildConfig
import com.mapbox.maps.logE
import com.mapbox.maps.module.MapTelemetry
import java.util.*

/**
 * Concrete implementation of map telemetry.
 */
@MapboxModule(MapboxModuleType.MapTelemetry)
class MapTelemetryImpl : MapTelemetry {

  private val appContext: Context
  private val eventsService: EventsServiceInterface
  // We need telemetryService for location and metrics events which will be sent automatically if telemetryService is initialized.
  private val telemetryService: TelemetryService
  private val eventsServiceOptions: EventsServerOptions

  /**
   * Creates a map telemetry instance using application context and access token
   *
   * @param appContext the application context
   */
  constructor(appContext: Context) {
    this.appContext = appContext

    eventsServiceOptions = EventsServerOptions(
      SdkInformation(
        BuildConfig.MAPBOX_SDK_IDENTIFIER,
        BuildConfig.MAPBOX_SDK_VERSION,
        null
      ),
      null
    )
    this.eventsService = EventsService.getOrCreate(eventsServiceOptions)
    this.telemetryService = TelemetryService.getOrCreate()
  }

  /**
   * Creates a map telemetry instance using telemetry, application context and access token
   *
   * @param appContext the application context
   * @param eventsService the mapbox EventsServiceInterface
   * @param telemetryService the mapbox TelemetryService
   */
  @VisibleForTesting(otherwise = PRIVATE)
  internal constructor(
    appContext: Context,
    eventsService: EventsServiceInterface,
    telemetryService: TelemetryService,
    eventsServerOptions: EventsServerOptions
  ) {
    this.appContext = appContext
    this.eventsService = eventsService
    this.telemetryService = telemetryService
    this.eventsServiceOptions = eventsServerOptions
  }

  /**
   * Register the app user turnstile event
   */
  override fun onAppUserTurnstileEvent() {
    eventsService.sendTurnstileEvent(TurnstileEvent(UserSKUIdentifier.MAPS_MAUS)) { response ->
      if (response.isError) {
        logE(TAG, "sendTurnstileEvent error: ${response.error}")
      }
    }

    if (shouldSendEvents()) {
      val mapLoadEvent = MapEventFactory.buildMapLoadEvent(PhoneState(appContext))
      sendEvent(Gson().toJson(mapLoadEvent))
    }
  }

  private fun shouldSendEvents() = TelemetryUtils.getClientServerEventsCollectionState() != TelemetryCollectionState.TURNSTILE_EVENTS_ONLY

  private fun sendEvent(event: String) {
    val eventAttributes = Value.fromJson(event)
    val mapEvent = eventAttributes.value?.let { Event(EventPriority.QUEUED, it, null) }
    if (mapEvent != null) {
      eventsService.sendEvent(mapEvent) { response ->
        if (response.isError) {
          logE(TAG, "sendEvent error: ${response.error}")
        }
      }
    }
  }

  private fun enableTelemetryCollection(enabled: Boolean) {
    TelemetryUtils.setEventsCollectionState(enabled) { response ->
      if (response.isError) {
        logE(TAG, "setEventsCollectionState error: ${response.error}")
      }
    }
  }

  /**
   * Set the end-user selected state to participate or opt-out in telemetry collection.
   *
   * @param enabled true if enabled, false otherwise
   */
  override fun setUserTelemetryRequestState(enabled: Boolean) {
    enableTelemetryCollection(enabled)
  }

  /**
   * Disables a started telemetry service for this session only.
   */
  override fun disableTelemetrySession() {
    enableTelemetryCollection(false)
  }

  /**
   * Register a performance event
   *
   * @param data performance event data
   */
  override fun onPerformanceEvent(data: Bundle?) {
    if (shouldSendEvents()) {
      val performanceEvent = MapEventFactory.buildPerformanceEvent(
        PhoneState(appContext),
        UUID.randomUUID().toString(), data ?: Bundle()
      )
      sendEvent(Gson().toJson(performanceEvent))
    }
  }

  /**
   * Static variables and methods.
   */
  private companion object {
    private const val TAG = "MapTelemetryImpl"
  }
}
package com.mapbox.maps.module.telemetry

import android.content.Context
import android.os.Bundle
import androidx.annotation.VisibleForTesting
import com.google.gson.Gson
import com.mapbox.android.telemetry.*
import com.mapbox.annotation.module.MapboxModule
import com.mapbox.annotation.module.MapboxModuleType
import com.mapbox.bindgen.Value
import com.mapbox.common.*
import com.mapbox.common.Event
import com.mapbox.common.TelemetryUtils
import com.mapbox.maps.logE
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
  // EventsService
  private val eventsService: EventsServiceInterface
  private val telemetryService: TelemetryService

  /**
   * Creates a map telemetry instance using appplication context and access token
   *
   * @param appContext the application context
   * @param accessToken the mapbox access token
   */
  constructor(appContext: Context, accessToken: String) {
    this.appContext = appContext

    val oldEventsTokenResId = getEventsResId(appContext, OLD_EVENTS_ACCESS_TOKEN_RESOURCE_NAME)
    val oldEventsToken =
      if (oldEventsTokenResId != 0) appContext.getString(oldEventsTokenResId) else accessToken
    this.accessToken = oldEventsToken

    this.telemetry = MapboxTelemetry(appContext, this.accessToken, BuildConfig.MAPBOX_EVENTS_USER_AGENT)
    val telemetryState: TelemetryEnabler.State =
      TelemetryEnabler.retrieveTelemetryStateFromPreferences()
    if (TelemetryEnabler.State.ENABLED == telemetryState) {
      telemetry.enable()
    }

    // EventsService
    // check in the resources
    val eventsTokenResId = getEventsResId(appContext, EVENTS_ACCESS_TOKEN_RESOURCE_NAME)
    val eventsToken =
      if (eventsTokenResId != 0) appContext.getString(eventsTokenResId) else accessToken
    // val eventsUrlResId = getEventsResId(appContext, EVENTS_URL_RESOURCE_NAME)
    // val eventsUrl = if (eventsUrlResId != 0) appContext.getString(eventsUrlResId) else null

    val eventsServiceOptions = EventsServerOptions(eventsToken, BuildConfig.MAPBOX_EVENTS_USER_AGENT)
    this.eventsService = EventsService.getOrCreate(eventsServiceOptions)

    this.telemetryService = TelemetryService(eventsServiceOptions)

    val coreTelemetryState = TelemetryUtils.getEventsCollectionState()
    if (coreTelemetryState == true) {
      enableTelemetryCollection(true)
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
  constructor(telemetry: MapboxTelemetry, appContext: Context, accessToken: String, eventsService: EventsServiceInterface, telemetryService: TelemetryService) {
    this.appContext = appContext
    this.accessToken = accessToken
    this.telemetry = telemetry
    this.eventsService = eventsService
    this.telemetryService = telemetryService
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

    val mapLoadEvent = MapEventFactory.buildMapLoadEvent(PhoneState(appContext))
    telemetry.push(mapLoadEvent)

    // EventsService
    eventsService.sendTurnstileEvent(
      TurnstileEvent(
        UserSKUIdentifier.MAPS_MAUS,
        BuildConfig.MAPBOX_SDK_IDENTIFIER,
        BuildConfig.MAPBOX_SDK_VERSION
      ),
      null
    )

    // EventsService
    sendEvent(Gson().toJson(mapLoadEvent))
  }

  private fun getEventsResId(context: Context, resourceName: String): Int = context.resources.getIdentifier(
    resourceName,
    "string",
    context.packageName
  )

  // EventsService
  private fun sendEvent(event: String) {
    val eventAttributes = Value.fromJson(event)
    val mapEvent = eventAttributes.value?.let { Event(EventPriority.IMMEDIATE, it) }
    if (mapEvent != null) {
      eventsService.sendEvent(mapEvent, null)
    }
  }

  // EventsService
  private fun enableTelemetryCollection(enabled: Boolean) {
    TelemetryUtils.setEventsCollectionState(enabled) { eventsServiceError ->
      eventsServiceError?.let {
        logE(TAG, "EventsServiceError: $it")
      }
    }
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

    // EventsService
    enableTelemetryCollection(enabled)
  }

  /**
   * Disables a started telemetry service for this session only.
   */
  override fun disableTelemetrySession() {
    telemetry.disable()

    // EventsService
    enableTelemetryCollection(false)
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
    val performanceEvent = MapEventFactory.buildPerformanceEvent(
      PhoneState(appContext),
      UUID.randomUUID().toString(), data ?: Bundle()
    )

    telemetry.push(performanceEvent)

    // EventsService
    sendEvent(Gson().toJson(performanceEvent))
  }

  /**
   * Static variables and methods.
   */
  companion object {
    private const val TAG = "MapTelemetryImpl"
    private const val EVENTS_ACCESS_TOKEN_RESOURCE_NAME = "mapbox_events_access_token"
    private const val OLD_EVENTS_ACCESS_TOKEN_RESOURCE_NAME = "old_mapbox_events_access_token"
    private val EVENTS_URL_RESOURCE_NAME = "mapbox_events_url"
  }
}
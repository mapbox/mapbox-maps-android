package com.mapbox.maps.module.telemetry

import android.content.Context
import android.os.Bundle
import androidx.annotation.VisibleForTesting
import com.google.gson.Gson
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
  private val eventsService: EventsServiceInterface
  // We need telemetryService for location and metrics event which will be sent automatically if telemetryService is initialized.
  private val telemetryService: TelemetryService

  /**
   * Creates a map telemetry instance using application context and access token
   *
   * @param appContext the application context
   * @param accessToken the mapbox access token
   */
  constructor(appContext: Context, accessToken: String) {
    this.appContext = appContext
    this.accessToken = accessToken

    // check in the resources
    val eventsTokenResId = getEventsResId(appContext, EVENTS_ACCESS_TOKEN_RESOURCE_NAME)
    val eventsToken =
      if (eventsTokenResId != 0) appContext.getString(eventsTokenResId) else accessToken

    // TODO deferredDeliveryServiceOptions?
    val eventsServiceOptions = EventsServerOptions(eventsToken, BuildConfig.MAPBOX_EVENTS_USER_AGENT, null)
    this.eventsService = EventsService.getOrCreate(eventsServiceOptions)

    this.telemetryService = TelemetryService.getOrCreate(eventsServiceOptions)

    if (TelemetryUtils.getEventsCollectionState()) {
      enableTelemetryCollection(true)
    }
  }

  /**
   * Creates a map telemetry instance using telemetry, application context and access token
   *
   * @param appContext the application context
   * @param accessToken the mapbox access token
   * @param eventsService the mapbox EventsServiceInterface
   * @param telemetryService the mapbox TelemetryService
   */
  @VisibleForTesting
  constructor(appContext: Context, accessToken: String, eventsService: EventsServiceInterface, telemetryService: TelemetryService) {
    this.appContext = appContext
    this.accessToken = accessToken
    this.eventsService = eventsService
    this.telemetryService = telemetryService
  }

  /**
   * Register the app user turnstile event
   */
  override fun onAppUserTurnstileEvent() {
    eventsService.sendTurnstileEvent(
      TurnstileEvent(
        UserSKUIdentifier.MAPS_MAUS,
        BuildConfig.MAPBOX_SDK_IDENTIFIER,
        BuildConfig.MAPBOX_SDK_VERSION
      ),
      null
    )

    val mapLoadEvent = MapEventFactory.buildMapLoadEvent(PhoneState(appContext))
    sendEvent(Gson().toJson(mapLoadEvent))
  }

  private fun getEventsResId(context: Context, resourceName: String): Int = context.resources.getIdentifier(
    resourceName,
    "string",
    context.packageName
  )

  private fun sendEvent(event: String) {
    val eventAttributes = Value.fromJson(event)
    // TODO: deferredOptions?
    val mapEvent = eventAttributes.value?.let { Event(EventPriority.IMMEDIATE, it, null) }
    if (mapEvent != null) {
      eventsService.sendEvent(mapEvent, null)
    }
  }

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
    enableTelemetryCollection(enabled)
  }

  /**
   * Disables a started telemetry service for this session only.
   */
  override fun disableTelemetrySession() {
    enableTelemetryCollection(false)
  }

  /**
   * Set the debug logging enabled states.
   *
   * @param debugLoggingEnabled whether to enable the debug logging for telemetry.
   */
  @Deprecated("setDebugLoggingEnabled has been deprecated and will do no operations")
  override fun setDebugLoggingEnabled(debugLoggingEnabled: Boolean) {
    // no-ops
  }

  /**
   * Set the telemetry rotation session id interval
   *
   * @param interval the selected session interval
   * @return true if rotation session id was updated
   */
  @Deprecated("setSessionIdRotationInterval has been deprecated and will do no operations")
  override fun setSessionIdRotationInterval(interval: Int): Boolean {
    // no-ops
    return false
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
    sendEvent(Gson().toJson(performanceEvent))
  }

  /**
   * Static variables and methods.
   */
  private companion object {
    private const val TAG = "MapTelemetryImpl"
    private const val EVENTS_ACCESS_TOKEN_RESOURCE_NAME = "mapbox_events_access_token"
  }
}
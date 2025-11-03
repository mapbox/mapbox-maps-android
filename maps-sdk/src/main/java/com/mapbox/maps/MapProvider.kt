package com.mapbox.maps

import android.content.Context
import com.mapbox.annotation.module.MapboxModuleType
import com.mapbox.common.EventsServerOptions
import com.mapbox.common.EventsService
import com.mapbox.common.SdkInformation
import com.mapbox.common.TelemetryService
import com.mapbox.common.module.provider.MapboxModuleProvider
import com.mapbox.common.module.provider.ModuleProviderArgument
import com.mapbox.maps.base.BuildConfig
import com.mapbox.maps.geofencing.MapGeofencingConsent
import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.plugin.MapDelegateProviderImpl
import com.mapbox.maps.plugin.MapPluginRegistry
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

internal object MapProvider {

  private lateinit var mapTelemetry: MapTelemetry

  /**
   * [CoroutineScope] that runs on Main dispatcher by default (NOTE that it is not using [MainCoroutineDispatcher.immediate]).
   */
  private val mainScope =
    CoroutineScope(CoroutineName(MapController.TAG) + SupervisorJob() + Dispatchers.Main)

  fun getNativeMapWrapper(
    mapInitOptions: MapInitOptions,
    mapClient: MapClient,
  ) = NativeMapImpl(
    Map(
      mapClient,
      mapInitOptions.mapOptions,
    )
  )

  fun getNativeMapCore(mapView: MapView): Map {
    return mapView.getController().getNativeMap().map
  }

  fun getMapboxMap(
    nativeMap: NativeMapImpl,
    nativeObserver: NativeObserver,
    pixelRatio: Float
  ) = MapboxMap(nativeMap, nativeObserver, pixelRatio)

  @OptIn(MapboxExperimental::class)
  fun getMapPluginRegistry(
    mapboxMap: MapboxMap,
    mapController: MapController,
    telemetry: MapTelemetry,
    mapGeofencingConsent: MapGeofencingConsent,
  ) = MapPluginRegistry(MapDelegateProviderImpl(mapboxMap, mapController, telemetry, mapGeofencingConsent))

  fun getMapTelemetryInstance(context: Context): MapTelemetry {
    if (!::mapTelemetry.isInitialized) {
      mapTelemetry = MapboxModuleProvider.createModule(MapboxModuleType.MapTelemetry) {
        paramsProvider(context, MapboxModuleType.MapTelemetry)
      }
    }
    // Schedule the turnstile event on the Main dispatcher to avoid blocking this call chain.
    mainScope.launch {
      mapTelemetry.onAppUserTurnstileEvent()
    }
    return mapTelemetry
  }

  @OptIn(MapboxExperimental::class)
  fun getMapGeofencingConsent(): MapGeofencingConsent = MapGeofencingConsentImpl()

  fun flushPendingEvents() {
    val eventsServerOptions =
      EventsServerOptions(
        SdkInformation(
          BuildConfig.MAPBOX_SDK_IDENTIFIER,
          BuildConfig.MAPBOX_SDK_VERSION,
          null
        ),
        null
      )
    EventsService.getOrCreate(eventsServerOptions).flush { expected ->
      expected.error?.let { error ->
        logE(MapController.TAG, "EventsService flush error: $error")
      }
    }
    TelemetryService.getOrCreate().flush { expected ->
      expected.error?.let { error ->
        logE(MapController.TAG, "TelemetryService flush error: $error")
      }
    }
  }

  /**
   * Provides parameters for Mapbox default modules, recursively if a module depends on other Mapbox modules.
   */
  private fun paramsProvider(
    context: Context,
    @Suppress("SameParameterValue")
    type: MapboxModuleType
  ): Array<ModuleProviderArgument> {
    return when (type) {
      MapboxModuleType.MapTelemetry -> arrayOf(
        ModuleProviderArgument(
          Context::class.java,
          context.applicationContext
        ),
      )
      else -> throw IllegalArgumentException("${type.name} module is not supported by the Maps SDK")
    }
  }

  private fun MapView.getController(): MapController = this.mapController
}
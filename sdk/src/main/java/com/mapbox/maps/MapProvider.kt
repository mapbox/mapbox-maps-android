package com.mapbox.maps

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.mapbox.annotation.module.MapboxModuleType
import com.mapbox.common.EventsServerOptions
import com.mapbox.common.EventsService
import com.mapbox.common.TelemetryService
import com.mapbox.common.module.provider.MapboxModuleProvider
import com.mapbox.common.module.provider.ModuleProviderArgument
import com.mapbox.maps.base.BuildConfig
import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.plugin.MapDelegateProviderImpl
import com.mapbox.maps.plugin.MapPluginRegistry

internal object MapProvider {

  private lateinit var mapTelemetry: MapTelemetry

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

  fun getMapPluginRegistry(
    mapboxMap: MapboxMap,
    mapController: MapController,
    telemetry: MapTelemetry
  ) = MapPluginRegistry(MapDelegateProviderImpl(mapboxMap, mapController, telemetry))

  fun getMapTelemetryInstance(context: Context): MapTelemetry {
    if (!::mapTelemetry.isInitialized) {
      mapTelemetry = MapboxModuleProvider.createModule(MapboxModuleType.MapTelemetry) {
        paramsProvider(context, MapboxModuleType.MapTelemetry)
      }
    }
    Handler(Looper.getMainLooper()).post {
      mapTelemetry.onAppUserTurnstileEvent()
    }
    return mapTelemetry
  }

  fun flushPendingEvents() {
    val eventsServerOptions =
      EventsServerOptions(BuildConfig.MAPBOX_EVENTS_USER_AGENT, null)
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
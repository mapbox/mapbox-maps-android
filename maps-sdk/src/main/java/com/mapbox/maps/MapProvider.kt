package com.mapbox.maps

import android.content.Context
import com.mapbox.annotation.module.MapboxModuleType
import com.mapbox.common.EventsServerOptions
import com.mapbox.common.EventsService
import com.mapbox.common.MapboxThread
import com.mapbox.common.SdkInformation
import com.mapbox.common.TelemetryService
import com.mapbox.common.mapboxThread
import com.mapbox.common.module.provider.MapboxModuleProvider
import com.mapbox.common.module.provider.ModuleProviderArgument
import com.mapbox.maps.base.BuildConfig
import com.mapbox.maps.geofencing.MapGeofencingConsent
import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.module.telemetry.MapTelemetryMetadata
import com.mapbox.maps.module.telemetry.UiFramework
import com.mapbox.maps.plugin.MapDelegateProviderImpl
import com.mapbox.maps.plugin.MapPluginRegistry
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

internal object MapProvider {

  private lateinit var mapTelemetry: MapTelemetry

  /**
   * [CoroutineScope] that runs on Main/Mapbox thread dispatcher by default (NOTE that it is not
   * using [MainCoroutineDispatcher.immediate]).
   *
   * Lazy so that merely touching [MapProvider] (e.g. calling [flushPendingEvents], which doesn't
   * use this scope) doesn't force [mapboxThread]'s resolution as a side effect of loading this
   * `object`; only actually using [mainScope] does.
   */
  private val mainScope by lazy {
    CoroutineScope(CoroutineName("MapProvider") + SupervisorJob() + mapboxThread.dispatcher)
  }

  fun getNativeMapWrapper(
    mapInitOptions: MapInitOptions,
    mapClient: MapClient,
    mapboxThread: MapboxThread,
  ) = NativeMapImpl(
    Map(
      mapClient,
      mapInitOptions.mapOptions,
    ),
    mapboxThread,
  )

  fun getNativeMapCore(mapView: MapView): Map {
    return mapView.getController().getNativeMap().map
  }

  fun getMapboxMap(
    nativeMap: NativeMapImpl,
    nativeObserver: NativeObserver,
    pixelRatio: Float,
    mapName: String,
    mapboxThread: MapboxThread,
    threadChecker: MapThreadChecker,
  ) = MapboxMap(nativeMap, nativeObserver, pixelRatio, mapName, mapboxThread, threadChecker)

  @OptIn(MapboxExperimental::class)
  fun getMapPluginRegistry(
    mapboxMap: MapboxMap,
    mapController: MapController,
    telemetry: MapTelemetry,
    mapGeofencingConsent: MapGeofencingConsent,
  ) = MapPluginRegistry(MapDelegateProviderImpl(mapboxMap, mapController, telemetry, mapGeofencingConsent))

  @JvmOverloads
  fun getMapTelemetryInstance(
    context: Context,
    uiFramework: UiFramework? = null,
  ): MapTelemetry {
    if (!::mapTelemetry.isInitialized) {
      mapTelemetry = MapboxModuleProvider.createModule(MapboxModuleType.MapTelemetry) {
        paramsProvider(context, MapboxModuleType.MapTelemetry)
      }
    }
    // Schedule the turnstile event on the Main dispatcher to avoid blocking this call chain.
    mainScope.launch {
      val metadata = MapTelemetryMetadata.Builder()
        .uiFramework(uiFramework)
        .build()
      mapTelemetry.onAppUserTurnstileEvent(metadata)
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
        logW(MapController.TAG, "EventsService flush error: $error")
      }
    }
    TelemetryService.getOrCreate().flush { expected ->
      expected.error?.let { error ->
        logW(MapController.TAG, "TelemetryService flush error: $error")
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
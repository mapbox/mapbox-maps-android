package com.mapbox.maps

import android.content.Context
import android.view.MotionEvent
import androidx.annotation.VisibleForTesting
import com.mapbox.annotation.module.MapboxModuleType
import com.mapbox.common.module.provider.MapboxModuleProvider
import com.mapbox.common.module.provider.ModuleProviderArgument
import com.mapbox.maps.assets.AssetManagerProvider
import com.mapbox.maps.extension.observable.model.StyleDataType
import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.plugin.InvalidViewPluginHostException
import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.MapPluginRegistry
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.Plugin.Companion.MAPBOX_ANNOTATION_PLUGIN_ID
import com.mapbox.maps.plugin.Plugin.Companion.MAPBOX_ATTRIBUTION_PLUGIN_ID
import com.mapbox.maps.plugin.Plugin.Companion.MAPBOX_CAMERA_PLUGIN_ID
import com.mapbox.maps.plugin.Plugin.Companion.MAPBOX_COMPASS_PLUGIN_ID
import com.mapbox.maps.plugin.Plugin.Companion.MAPBOX_GESTURES_PLUGIN_ID
import com.mapbox.maps.plugin.Plugin.Companion.MAPBOX_LIFECYCLE_PLUGIN_ID
import com.mapbox.maps.plugin.Plugin.Companion.MAPBOX_LOCATION_COMPONENT_PLUGIN_ID
import com.mapbox.maps.plugin.Plugin.Companion.MAPBOX_LOGO_PLUGIN_ID
import com.mapbox.maps.plugin.Plugin.Companion.MAPBOX_MAP_OVERLAY_PLUGIN_ID
import com.mapbox.maps.plugin.Plugin.Companion.MAPBOX_SCALEBAR_PLUGIN_ID
import com.mapbox.maps.plugin.Plugin.Companion.MAPBOX_VIEWPORT_PLUGIN_ID
import com.mapbox.maps.plugin.animation.CameraAnimationsPluginImpl
import com.mapbox.maps.plugin.annotation.AnnotationPluginImpl
import com.mapbox.maps.plugin.attribution.AttributionViewPlugin
import com.mapbox.maps.plugin.compass.CompassViewPlugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.delegates.listeners.OnCameraChangeListener
import com.mapbox.maps.plugin.delegates.listeners.OnStyleDataLoadedListener
import com.mapbox.maps.plugin.gestures.GesturesPluginImpl
import com.mapbox.maps.plugin.lifecycle.MapboxLifecyclePluginImpl
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPluginImpl
import com.mapbox.maps.plugin.logo.LogoViewPlugin
import com.mapbox.maps.plugin.overlay.MapOverlayPluginImpl
import com.mapbox.maps.plugin.scalebar.ScaleBarPluginImpl
import com.mapbox.maps.plugin.viewport.ViewportPluginImpl
import com.mapbox.maps.renderer.MapboxRenderer
import com.mapbox.maps.renderer.OnFpsChangedListener
import com.mapbox.maps.renderer.widget.Widget

internal class MapController : MapPluginProviderDelegate, MapControllable {

  private val renderer: MapboxRenderer
  private val nativeObserver: NativeObserver
  private val mapInitOptions: MapInitOptions
  private val nativeMap: MapInterface
  private val mapboxMap: MapboxMap
  internal val pluginRegistry: MapPluginRegistry
  private val onStyleDataLoadedListener: OnStyleDataLoadedListener
  private val onCameraChangedListener: OnCameraChangeListener
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var lifecycleState: LifecycleState = LifecycleState.STATE_STOPPED

  constructor(
    renderer: MapboxRenderer,
    mapInitOptions: MapInitOptions,
  ) {
    this.renderer = renderer
    this.mapInitOptions = mapInitOptions
    AssetManagerProvider().initialize(mapInitOptions.context.assets)
    this.nativeMap = MapProvider.getNativeMap(
      mapInitOptions,
      renderer,
    )
    this.nativeObserver = NativeObserver(nativeMap)
    this.mapboxMap =
      MapProvider.getMapboxMap(nativeMap, nativeObserver, mapInitOptions.mapOptions.pixelRatio)
    this.mapboxMap.renderHandler = renderer.renderThread.renderHandlerThread.handler
    this.pluginRegistry = MapProvider.getMapPluginRegistry(
      mapboxMap,
      this,
      dispatchTelemetryTurnstileEvent()
    )
    this.onCameraChangedListener = OnCameraChangeListener {
      pluginRegistry.onCameraMove(nativeMap.cameraState)
    }
    this.onStyleDataLoadedListener = OnStyleDataLoadedListener { eventData ->
      if (eventData.type == StyleDataType.STYLE) {
        mapboxMap.getStyle { style ->
          pluginRegistry.onStyleChanged(style)
        }
      }
    }
    renderer.setMap(nativeMap)
    this.mapInitOptions.cameraOptions?.let {
      mapboxMap.setCamera(it)
    }
  }

  @VisibleForTesting(otherwise = VisibleForTesting.NONE)
  constructor(
    renderer: MapboxRenderer,
    nativeObserver: NativeObserver,
    mapInitOptions: MapInitOptions,
    nativeMap: MapInterface,
    mapboxMap: MapboxMap,
    pluginRegistry: MapPluginRegistry,
    onStyleLoadingFinishedListener: OnStyleDataLoadedListener
  ) {
    this.renderer = renderer
    this.nativeObserver = nativeObserver
    this.mapInitOptions = mapInitOptions
    this.nativeMap = nativeMap
    this.mapboxMap = mapboxMap
    this.pluginRegistry = pluginRegistry
    this.onCameraChangedListener = OnCameraChangeListener {
      pluginRegistry.onCameraMove(nativeMap.cameraState)
    }
    this.onStyleDataLoadedListener = onStyleLoadingFinishedListener
  }

  override fun getMapboxMap(): MapboxMap {
    return mapboxMap
  }

  override fun onStart() {
    if (lifecycleState == LifecycleState.STATE_STARTED) {
      return
    }
    lifecycleState = LifecycleState.STATE_STARTED

    nativeObserver.apply {
      addOnCameraChangeListener(onCameraChangedListener)
      addOnStyleDataLoadedListener(onStyleDataLoadedListener)
    }
    renderer.onStart()
    if (!mapboxMap.isStyleLoadInitiated) {
      // Load the style in mapInitOptions if no style has loaded yet.
      mapInitOptions.styleUri?.let {
        mapboxMap.loadStyleUri(it)
      }
    }
    pluginRegistry.onStart()
  }

  override fun onStop() {
    if (lifecycleState == LifecycleState.STATE_STOPPED) {
      return
    }
    lifecycleState = LifecycleState.STATE_STOPPED

    nativeObserver.apply {
      removeOnCameraChangeListener(onCameraChangedListener)
      removeOnStyleDataLoadedListener(onStyleDataLoadedListener)
    }
    renderer.onStop()
    pluginRegistry.onStop()
  }

  override fun onDestroy() {
    if (lifecycleState == LifecycleState.STATE_DESTROYED) {
      return
    }
    lifecycleState = LifecycleState.STATE_DESTROYED
    pluginRegistry.cleanup()
    nativeObserver.onDestroy()
    renderer.onDestroy()
    mapboxMap.onDestroy()
  }

  override fun onLowMemory() {
    mapboxMap.reduceMemoryUse()
  }

  override fun onTouchEvent(event: MotionEvent): Boolean {
    return pluginRegistry.onTouch(event)
  }

  override fun onGenericMotionEvent(event: MotionEvent): Boolean {
    return pluginRegistry.onGenericMotionEvent(event)
  }

  override fun onSizeChanged(w: Int, h: Int) {
    pluginRegistry.onSizeChanged(w, h)
  }

  override fun queueEvent(event: Runnable, needRender: Boolean) {
    if (needRender) {
      renderer.queueRenderEvent(event)
    } else {
      renderer.queueEvent(event)
    }
  }

  override fun snapshot() = renderer.snapshot()

  override fun snapshot(listener: MapView.OnSnapshotReady) {
    renderer.snapshot(listener)
  }

  override fun setMaximumFps(fps: Int) {
    if (fps <= 0) {
      return
    }
    renderer.setMaximumFps(fps)
  }

  override fun setOnFpsChangedListener(listener: OnFpsChangedListener) {
    renderer.setOnFpsChangedListener(listener)
  }

  override fun addWidget(widget: Widget) {
    renderer.renderThread.addWidget(widget)
  }

  override fun removeWidget(widget: Widget) = renderer.renderThread.removeWidget(widget)

  //
  // Telemetry
  //

  private fun dispatchTelemetryTurnstileEvent(): MapTelemetry {
    val telemetry: MapTelemetry =
      MapboxModuleProvider.createModule(MapboxModuleType.MapTelemetry) {
        paramsProvider(MapboxModuleType.MapTelemetry)
      }
    telemetry.onAppUserTurnstileEvent()
    return telemetry
  }

  /**
   * Provides parameters for Mapbox default modules, recursively if a module depends on other Mapbox modules.
   */
  private fun paramsProvider(type: MapboxModuleType): Array<ModuleProviderArgument> {
    val context = mapInitOptions.context
    return when (type) {
      MapboxModuleType.CommonLibraryLoader -> arrayOf(
        ModuleProviderArgument(
          Context::class.java,
          context.applicationContext
        )
      )
      MapboxModuleType.CommonHttpClient -> arrayOf()
      MapboxModuleType.CommonLogger -> arrayOf()
      MapboxModuleType.MapTelemetry -> arrayOf(
        ModuleProviderArgument(
          Context::class.java,
          context.applicationContext
        ),
        ModuleProviderArgument(String::class.java, mapInitOptions.resourceOptions.accessToken)
      )
      else -> throw IllegalArgumentException("${type.name} module is not supported by the Maps SDK")
    }
  }

  //
  // Plugin API
  //

  override fun <T : MapPlugin> getPlugin(id: String): T? = pluginRegistry.getPlugin(id)

  fun createPlugin(
    mapView: MapView?,
    plugin: Plugin
  ) = pluginRegistry.createPlugin(mapView, mapInitOptions, plugin)

  fun initializePlugins(
    options: MapInitOptions,
    mapView: MapView? = null,
  ) {
    for (plugin in options.plugins) {
      try {
        val pluginObject = when (plugin.id) {
          MAPBOX_GESTURES_PLUGIN_ID -> {
            val attrs = options.attrs
            if (attrs != null) {
              GesturesPluginImpl(options.context, attrs, options.mapOptions.pixelRatio)
            } else {
              GesturesPluginImpl(options.context, options.mapOptions.pixelRatio)
            }
          }
          MAPBOX_CAMERA_PLUGIN_ID -> {
            CameraAnimationsPluginImpl()
          }
          MAPBOX_ANNOTATION_PLUGIN_ID -> {
            AnnotationPluginImpl()
          }
          MAPBOX_COMPASS_PLUGIN_ID -> {
            CompassViewPlugin()
          }
          MAPBOX_ATTRIBUTION_PLUGIN_ID -> {
            AttributionViewPlugin()
          }
          MAPBOX_LIFECYCLE_PLUGIN_ID -> {
            MapboxLifecyclePluginImpl()
          }
          MAPBOX_LOCATION_COMPONENT_PLUGIN_ID -> {
            LocationComponentPluginImpl()
          }
          MAPBOX_LOGO_PLUGIN_ID -> {
            LogoViewPlugin()
          }
          MAPBOX_MAP_OVERLAY_PLUGIN_ID -> {
            MapOverlayPluginImpl()
          }
          MAPBOX_SCALEBAR_PLUGIN_ID -> {
            ScaleBarPluginImpl()
          }
          MAPBOX_VIEWPORT_PLUGIN_ID -> {
            ViewportPluginImpl()
          }
          else -> {
            plugin.instance ?: throw MapboxConfigurationException("Custom non Mapbox plugins must have non-null `instance` parameter!")
          }
        }
        createPlugin(mapView, Plugin.Custom(plugin.id, pluginObject))
        if (pluginObject is CameraAnimationsPluginImpl) {
          mapboxMap.setCameraAnimationPlugin(pluginObject)
        }
        if (pluginObject is GesturesPluginImpl) {
          mapboxMap.setGesturesAnimationPlugin(pluginObject)
        }
      } catch (ex: NoClassDefFoundError) {
        logI(TAG, PLUGIN_MISSING_TEMPLATE.format(plugin.id))
      } catch (ex: InvalidViewPluginHostException) {
        logI(TAG, VIEW_HIERARCHY_MISSING_TEMPLATE.format(plugin))
      }
    }
  }

  internal fun onAttachedToWindow(mapView: MapView) {
    pluginRegistry.onAttachedToWindow(mapView)
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal enum class LifecycleState {
    STATE_STOPPED,
    STATE_STARTED,
    STATE_DESTROYED
  }

  companion object {
    const val TAG = "MapController"
    private const val PLUGIN_MISSING_TEMPLATE =
      "Add %s plugin dependency to the classpath take automatically load the plugin implementation."
    private const val VIEW_HIERARCHY_MISSING_TEMPLATE =
      "%s plugin requires a View hierarchy to be injected, plugin is ignored."
  }
}
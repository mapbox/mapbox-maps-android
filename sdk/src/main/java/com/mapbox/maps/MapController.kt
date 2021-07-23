package com.mapbox.maps

import android.content.Context
import android.view.MotionEvent
import com.mapbox.annotation.module.MapboxModuleType
import com.mapbox.common.Logger
import com.mapbox.common.module.provider.MapboxModuleProvider
import com.mapbox.common.module.provider.ModuleProviderArgument
import com.mapbox.maps.assets.AssetManagerProvider
import com.mapbox.maps.loader.MapboxMapStaticInitializer
import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.plugin.*
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.CameraAnimationsPluginImpl
import com.mapbox.maps.plugin.annotation.AnnotationPluginImpl
import com.mapbox.maps.plugin.attribution.AttributionViewPlugin
import com.mapbox.maps.plugin.compass.CompassViewPlugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.delegates.listeners.OnCameraChangeListener
import com.mapbox.maps.plugin.delegates.listeners.OnStyleDataLoadedListener
import com.mapbox.maps.plugin.delegates.listeners.eventdata.StyleDataType
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import com.mapbox.maps.plugin.gestures.GesturesPluginImpl
import com.mapbox.maps.plugin.lifecycle.MapboxLifecyclePluginImpl
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPluginImpl
import com.mapbox.maps.plugin.logo.LogoViewPlugin
import com.mapbox.maps.plugin.overlay.MapOverlayPluginImpl
import com.mapbox.maps.plugin.scalebar.ScaleBarPluginImpl
import com.mapbox.maps.renderer.MapboxRenderer
import com.mapbox.maps.renderer.OnFpsChangedListener
import java.lang.ref.WeakReference

internal class MapController : MapPluginProviderDelegate, MapControllable {

  private val renderer: MapboxRenderer
  private val nativeObserver: NativeObserver
  private val mapInitOptions: MapInitOptions
  private val nativeMap: MapInterface
  private val mapboxMap: MapboxMap
  private val pluginRegistry: MapPluginRegistry
  private val onStyleDataLoadedListener: OnStyleDataLoadedListener
  private val onCameraChangedListener: OnCameraChangeListener
  private var lifecycleState: LifecycleState = LifecycleState.STATE_STOPPED

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
    this.nativeObserver = NativeObserver(WeakReference(nativeMap))
    this.mapboxMap =
      MapProvider.getMapboxMap(nativeMap, nativeObserver, mapInitOptions.mapOptions.pixelRatio)
    this.mapboxMap.renderHandler = renderer.renderThread.handlerThread.handler
    this.pluginRegistry = MapProvider.getMapPluginRegistry(
      mapboxMap,
      this,
      dispatchTelemetryTurnstileEvent()
    )
    this.onCameraChangedListener = OnCameraChangeListener {
      pluginRegistry.onCameraMove(nativeMap.cameraState)
    }
    this.onStyleDataLoadedListener = OnStyleDataLoadedListener { type ->
      if (type == StyleDataType.STYLE) {
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
    this.mapboxMap.renderHandler = renderer.renderThread.handlerThread.handler
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
      onStart()
      addOnCameraChangeListener(onCameraChangedListener)
      addOnStyleDataLoadedListener(onStyleDataLoadedListener)
    }
    renderer.onStart()
    pluginRegistry.onStart()
    if (!mapboxMap.isStyleLoadInitiated) {
      // Load the style in mapInitOptions if no style has loaded yet.
      mapInitOptions.styleUri?.let {
        mapboxMap.loadStyleUri(it)
      }
    }
  }

  override fun onStop() {
    if (lifecycleState == LifecycleState.STATE_STOPPED) {
      return
    }
    lifecycleState = LifecycleState.STATE_STOPPED

    nativeObserver.apply {
      removeOnCameraChangeListener(onCameraChangedListener)
      removeOnStyleDataLoadedListener(onStyleDataLoadedListener)
      onStop()
    }
    renderer.onStop()
    pluginRegistry.onStop()
  }

  override fun onDestroy() {
    if (lifecycleState == LifecycleState.STATE_DESTROYED) {
      return
    }
    lifecycleState = LifecycleState.STATE_DESTROYED

    mapboxMap.onDestroy()
    nativeObserver.clearListeners()
    renderer.onDestroy()
    pluginRegistry.cleanup()
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

  override fun getPlugin(type: PluginType): MapPlugin? {
    return pluginRegistry.getPlugin(type)
  }

  fun createPlugin(
    mapView: MapView?,
    type: PluginType,
    plugin: MapPlugin
  ) = pluginRegistry.createPlugin(mapView, mapInitOptions, type, plugin)

  fun initializePlugins(
    options: MapInitOptions,
    mapView: MapView? = null,
  ) {
    for (plugin in options.pluginTypes) {
      try {
        if (plugin == PluginType.GESTURES) {
          val attrs = options.attrs
          if (attrs != null) {
            createPlugin(
              mapView,
              PluginType.GESTURES,
              GesturesPluginImpl(options.context, attrs, options.mapOptions.pixelRatio)
            )
          } else {
            createPlugin(
              mapView,
              PluginType.GESTURES,
              GesturesPluginImpl(options.context, options.mapOptions.pixelRatio)
            )
          }
        }
        val pluginObject = when (plugin) {
          PluginType.CAMERA -> {
            CameraAnimationsPluginImpl()
          }
          PluginType.ANNOTATION -> {
            AnnotationPluginImpl()
          }
          PluginType.COMPASS -> {
            CompassViewPlugin()
          }
          PluginType.ATTRIBUTION -> {
            AttributionViewPlugin()
          }
          PluginType.LIFECYCLE -> {
            MapboxLifecyclePluginImpl()
          }
          PluginType.LOCATION_COMPONENT -> {
            LocationComponentPluginImpl()
          }
          PluginType.LOGO -> {
            LogoViewPlugin()
          }
          PluginType.MAP_OVERLAY -> {
            MapOverlayPluginImpl()
          }
          PluginType.SCALEBAR -> {
            ScaleBarPluginImpl()
          }
          else -> {
            throw RuntimeException("Unknown plugin $plugin")
          }
        }
        createPlugin(mapView, plugin, pluginObject)
        if (pluginObject is CameraAnimationsPlugin) {
          mapboxMap.setCameraAnimationPlugin(pluginObject)
        }
        if (pluginObject is GesturesPlugin) {
          mapboxMap.setGesturesAnimationPlugin(pluginObject)
        }
      } catch (ex: ClassNotFoundException) {
        Logger.d(TAG, PLUGIN_MISSING_TEMPLATE.format(plugin))
      } catch (ex: InvalidViewPluginHostException) {
        Logger.d(TAG, VIEW_HIERARCHY_MISSING_TEMPLATE.format(plugin))
      }
    }
  }

  internal fun onAttachedToWindow(mapView: MapView) {
    pluginRegistry.onAttachedToWindow(mapView)
  }

  private enum class LifecycleState {
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

    init {
      MapboxMapStaticInitializer.loadMapboxMapNativeLib()
    }
  }
}
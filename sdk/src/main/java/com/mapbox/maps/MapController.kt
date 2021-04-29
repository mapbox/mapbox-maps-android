package com.mapbox.maps

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import com.mapbox.annotation.module.MapboxModuleType
import com.mapbox.common.Logger
import com.mapbox.common.module.provider.MapboxModuleProvider
import com.mapbox.common.module.provider.ModuleProviderArgument
import com.mapbox.mapboxsdk.assets.AssetManagerProvider
import com.mapbox.maps.loader.MapboxMapStaticInitializer
import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.plugin.*
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.annotation.AnnotationPluginImpl
import com.mapbox.maps.plugin.attribution.AttributionPlugin
import com.mapbox.maps.plugin.compass.CompassPlugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.delegates.listeners.OnCameraChangeListener
import com.mapbox.maps.plugin.delegates.listeners.OnStyleDataLoadedListener
import com.mapbox.maps.plugin.delegates.listeners.eventdata.StyleDataType
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin
import com.mapbox.maps.plugin.logo.LogoPlugin
import com.mapbox.maps.plugin.overlay.MapOverlayPlugin
import com.mapbox.maps.plugin.scalebar.ScaleBarPlugin
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
    this.nativeObserver = NativeObserver(WeakReference(nativeMap), Handler(Looper.getMainLooper()))
    this.mapboxMap =
      MapProvider.getMapboxMap(nativeMap, nativeObserver, mapInitOptions.mapOptions.pixelRatio)
    this.pluginRegistry = MapProvider.getMapPluginRegistry(
      mapboxMap,
      this,
      dispatchTelemetryTurnstileEvent()
    )
    this.onCameraChangedListener = OnCameraChangeListener {
      pluginRegistry.onCameraMove(nativeMap.getCameraOptions(null))
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
    this.pluginRegistry = pluginRegistry
    this.onCameraChangedListener = OnCameraChangeListener {
      pluginRegistry.onCameraMove(nativeMap.getCameraOptions(null))
    }
    this.onStyleDataLoadedListener = onStyleLoadingFinishedListener
  }

  override fun getMapboxMap(): MapboxMap {
    return mapboxMap
  }

  override fun onStart() {
    nativeObserver.apply {
      onStart()
      addOnCameraChangeListener(onCameraChangedListener)
      addOnStyleDataLoadedListener(onStyleDataLoadedListener)
    }
    renderer.onStart()
    pluginRegistry.onStart()
    if (!mapboxMap.isStyleLoadInited) {
      // Load the style in mapInitOptions if no style has loaded yet.
      mapInitOptions.styleUri?.let {
        mapboxMap.loadStyleUri(it)
      }
    }
  }

  fun reduceMemoryUse() {
    mapboxMap.reduceMemoryUse()
  }

  override fun onStop() {
    nativeObserver.apply {
      removeOnCameraChangeListener(onCameraChangedListener)
      removeOnStyleDataLoadedListener(onStyleDataLoadedListener)
      onStop()
    }
    renderer.onStop()
    pluginRegistry.onStop()
  }

  override fun onDestroy() {
    nativeObserver.clearListeners()
    renderer.onDestroy()
    pluginRegistry.cleanup()
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

  override fun <T> getPlugin(clazz: Class<T>): T? = pluginRegistry.getPlugin(clazz)

  override fun <T> getPlugin(className: String): T? {
    @Suppress("UNCHECKED_CAST")
    return getPlugin(
      Class.forName(
        className
      )
    ) as T
  }

  fun <T> createPlugin(
    mapView: MapView?,
    clazz: Class<T>,
    vararg constructorArguments: Pair<Class<*>, Any>
  ): T? = pluginRegistry.createPlugin(mapView, mapInitOptions, clazz, *constructorArguments)

  fun initializePlugins(
    mapView: MapView?
  ) {
    try {
      val cameraAnimationsPluginClass =
        Class.forName(PLUGIN_CAMERA_ANIMATIONS_CLASS_NAME) as Class<CameraAnimationsPlugin>
      mapboxMap.setCameraAnimationPlugin(createPlugin(mapView, cameraAnimationsPluginClass))
    } catch (ex: ClassNotFoundException) {
      Logger.d(
        TAG,
        "Add camera animations plugin dependency to take advantage of the default Mapbox animations implementation."
      )
    }

    try {
      val compassPluginClass = Class.forName(PLUGIN_COMPASS_CLASS_NAME) as Class<CompassPlugin>
      createPlugin(mapView, compassPluginClass)
    } catch (ex: ClassNotFoundException) {
      Logger.i(
        TAG,
        "Add compass plugin dependency to take advantage of the default Mapbox compass implementation."
      )
    } catch (ex: InvalidViewPluginHostException) {
      Logger.d(
        TAG,
        "Compass plugin requires a View hierarchy to be injected, plugin is ignored."
      )
    }

    try {
      val logoPluginClass = Class.forName(PLUGIN_LOGO_CLASS_NAME) as Class<LogoPlugin>
      createPlugin(mapView, logoPluginClass)
    } catch (ex: ClassNotFoundException) {
      Logger.w(TAG, mapInitOptions.context.getString(R.string.mapbox_warning_logo_disabled))
    } catch (ex: InvalidViewPluginHostException) {
      Logger.d(
        TAG,
        "Logo plugin requires a View hierarchy to be injected, plugin is ignored."
      )
    }

    try {
      val gesturePluginClass =
        Class.forName(PLUGIN_GESTURE_CLASS_NAME) as Class<GesturesPlugin>

      mapInitOptions.attrs?.let {
        val plugin = createPlugin(
          mapView,
          gesturePluginClass,
          Pair(Context::class.java, mapInitOptions.context),
          Pair(AttributeSet::class.java, it),
          Pair(Float::class.java, mapInitOptions.mapOptions.pixelRatio)
        )
        mapboxMap.setGesturesAnimationPlugin(plugin)
      } ?: run {
        val plugin = createPlugin(
          mapView,
          gesturePluginClass,
          Pair(Context::class.java, mapInitOptions.context),
          Pair(Float::class.java, mapInitOptions.mapOptions.pixelRatio)
        )
        mapboxMap.setGesturesAnimationPlugin(plugin)
      }
    } catch (ex: ClassNotFoundException) {
      Logger.d(
        TAG,
        "Add gesture plugin dependency to take advantage of the default Mapbox gestures implementation."
      )
    }

    try {
      val attributionPluginClass =
        Class.forName(PLUGIN_ATTRIBUTION_CLASS_NAME) as Class<AttributionPlugin>
      createPlugin(mapView, attributionPluginClass)
    } catch (ex: ClassNotFoundException) {
      Logger.w(TAG, mapInitOptions.context.getString(R.string.mapbox_warning_attribution_disabled))
    } catch (ex: InvalidViewPluginHostException) {
      Logger.d(
        TAG,
        "Attribution plugin requires a View hierarchy to be injected, plugin is ignored."
      )
    }

    try {
      val locationComponentPluginClass =
        Class.forName(PLUGIN_LOCATION_COMPONENT_CLASS_NAME) as Class<LocationComponentPlugin>
      createPlugin(mapView, locationComponentPluginClass)
    } catch (ex: ClassNotFoundException) {
      Logger.d(
        TAG,
        "Add location component plugin dependency to take advantage of the default Mapbox location component implementation."
      )
    }

    try {
      val scaleBarPluginClass =
        Class.forName(PLUGIN_SCALE_BAR_CLASS_NAME) as Class<ScaleBarPlugin>
      createPlugin(mapView, scaleBarPluginClass)
    } catch (ex: ClassNotFoundException) {
      Logger.d(
        TAG,
        "Add scale bar plugin dependency to take advantage of the default Mapbox scale bar implementation."
      )
    } catch (ex: InvalidViewPluginHostException) {
      Logger.d(
        TAG,
        "Compass plugin requires a View hierarchy to be injected, plugin is ignored."
      )
    }

    try {
      val mapOverlayPlugin =
        Class.forName(PLUGIN_MAPOVERLAY_CLASS_NAME) as Class<MapOverlayPlugin>
      createPlugin(mapView, mapOverlayPlugin)
    } catch (ex: ClassNotFoundException) {
      Logger.d(
        TAG,
        "Add map overlay plugin dependency to take advantage of the default Mapbox MapOverlay plugin implementation."
      )
    }

    try {
      val annotationPlugin =
        Class.forName(PLUGIN_ANNOTATION_CLASS_NAME) as Class<AnnotationPluginImpl>
      createPlugin(mapView, annotationPlugin)
    } catch (ex: ClassNotFoundException) {
      Logger.d(
        TAG,
        "Add annotation plugin dependency to take advantage of the default Mapbox Annotation plugin implementation."
      )
    }
  }

  companion object {
    const val TAG = "MapController"

    init {
      MapboxMapStaticInitializer.loadMapboxMapNativeLib()
    }
  }
}
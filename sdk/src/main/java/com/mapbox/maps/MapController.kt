package com.mapbox.maps

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import com.mapbox.annotation.module.MapboxModuleType
import com.mapbox.bindgen.Value
import com.mapbox.common.Logger
import com.mapbox.common.module.provider.MapboxModuleProvider
import com.mapbox.common.module.provider.ModuleProviderArgument
import com.mapbox.mapboxsdk.assets.AssetManagerProvider
import com.mapbox.maps.RenderCache.Companion.RENDER_CACHE_SETTING
import com.mapbox.maps.loader.MapboxMapStaticInitializer
import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.plugin.*
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.delegates.listeners.OnCameraChangeListener
import com.mapbox.maps.plugin.delegates.listeners.OnStyleDataLoadedListener
import com.mapbox.maps.plugin.delegates.listeners.eventdata.StyleDataType
import com.mapbox.maps.plugin.gestures.GesturesPlugin
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
    mapboxMap.onDestroy()
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

  @MapboxExperimental
  override fun setRenderCache(cache: RenderCache) {
    if (cache.cacheSizeMb >= 0) {
      Settings.set(RENDER_CACHE_SETTING, Value(cache.cacheSizeMb))
    } else {
      Logger.e(TAG, "Render cache size must be >= 0!")
    }
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
    options: MapInitOptions,
    mapView: MapView? = null,
  ) {
    for (pluginName in options.plugins) {
      try {
        val pluginClass = Class.forName(pluginName)
        val plugin = if (pluginName == PLUGIN_GESTURE_CLASS_NAME) {
          val attrs = options.attrs
          if (attrs != null) {
            createPlugin(
              mapView,
              pluginClass,
              Pair(Context::class.java, options.context),
              Pair(AttributeSet::class.java, attrs),
              Pair(Float::class.java, options.mapOptions.pixelRatio)
            )
          } else {
            createPlugin(
              mapView,
              pluginClass,
              Pair(Context::class.java, options.context),
              Pair(Float::class.java, options.mapOptions.pixelRatio)
            )
          }
        } else {
          createPlugin(mapView, pluginClass)
        }
        if (plugin is CameraAnimationsPlugin) {
          mapboxMap.setCameraAnimationPlugin(plugin)
        }
        if (plugin is GesturesPlugin) {
          mapboxMap.setGesturesAnimationPlugin(plugin)
        }
      } catch (ex: ClassNotFoundException) {
        Logger.d(TAG, PLUGIN_MISSING_TEMPLATE.format(pluginName))
      } catch (ex: InvalidViewPluginHostException) {
        Logger.d(TAG, VIEW_HIERARCHY_MISSING_TEMPLATE.format(pluginName))
      }
    }
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
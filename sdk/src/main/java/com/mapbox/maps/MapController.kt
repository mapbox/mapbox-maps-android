package com.mapbox.maps

import android.view.MotionEvent
import androidx.annotation.VisibleForTesting
import com.mapbox.common.MapboxOptions
import com.mapbox.maps.assets.AssetManagerProvider
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
import com.mapbox.maps.plugin.gestures.GesturesPluginImpl
import com.mapbox.maps.plugin.lifecycle.MapboxLifecyclePluginImpl
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPluginImpl
import com.mapbox.maps.plugin.logo.LogoViewPlugin
import com.mapbox.maps.plugin.overlay.MapOverlayPluginImpl
import com.mapbox.maps.plugin.scalebar.ScaleBarPluginImpl
import com.mapbox.maps.plugin.viewport.ViewportPluginImpl
import com.mapbox.maps.renderer.MapboxRenderer
import com.mapbox.maps.renderer.OnFpsChangedListener
import com.mapbox.maps.renderer.RendererSetupErrorListener
import com.mapbox.maps.renderer.widget.Widget

internal class MapController : MapPluginProviderDelegate, MapControllable {

  internal val renderer: MapboxRenderer
  private val nativeObserver: NativeObserver
  private val mapInitOptions: MapInitOptions
  private val nativeMap: NativeMapImpl
  private val mapboxMap: MapboxMap
  internal val pluginRegistry: MapPluginRegistry
  private val styleDataLoadedCallback: StyleDataLoadedCallback
  private val cameraChangedCallback: CameraChangedCallback

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var lifecycleState: LifecycleState = LifecycleState.STATE_STOPPED
  private var style: Style? = null

  private var contextMode: ContextMode? = null

  constructor(
    renderer: MapboxRenderer,
    mapInitOptions: MapInitOptions,
  ) {
    if (MapboxOptions.accessToken.isBlank()) {
      throw MapboxConfigurationException()
    }

    this.renderer = renderer
    this.mapInitOptions = mapInitOptions
    this.contextMode = mapInitOptions.mapOptions.contextMode
    AssetManagerProvider().initialize(mapInitOptions.context.assets)
    this.nativeMap = MapProvider.getNativeMapWrapper(
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
      MapProvider.getMapTelemetryInstance(
        mapInitOptions.context
      )
    )
    this.cameraChangedCallback = CameraChangedCallback {
      pluginRegistry.onCameraMove(nativeMap.getCameraState())
    }
    this.styleDataLoadedCallback = StyleDataLoadedCallback { eventData ->
      if (eventData.type == StyleDataLoadedType.STYLE) {
        mapboxMap.getStyle { style ->
          this.style = style
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
    contextMode: ContextMode?,
    nativeMap: NativeMapImpl,
    mapboxMap: MapboxMap,
    pluginRegistry: MapPluginRegistry,
    onStyleLoadingFinishedListener: StyleDataLoadedCallback
  ) {
    this.renderer = renderer
    this.nativeObserver = nativeObserver
    this.mapInitOptions = mapInitOptions
    this.contextMode = contextMode
    this.nativeMap = nativeMap
    this.mapboxMap = mapboxMap
    this.pluginRegistry = pluginRegistry
    this.cameraChangedCallback = CameraChangedCallback {
      pluginRegistry.onCameraMove(nativeMap.getCameraState())
    }
    this.styleDataLoadedCallback = onStyleLoadingFinishedListener
  }

  fun getNativeMap(): NativeMapImpl {
    return nativeMap
  }

  override fun getMapboxMap(): MapboxMap {
    return mapboxMap
  }

  override fun onStart() {
    if (lifecycleState == LifecycleState.STATE_STARTED) {
      return
    }
    lifecycleState = LifecycleState.STATE_STARTED

    mapboxMap.getStyle()?.let {
      if (it != style) {
        style = it
        pluginRegistry.onStyleChanged(it)
      }
    }
    nativeObserver.apply {
      addOnCameraChangeListener(cameraChangedCallback)
      addOnStyleDataLoadedListener(styleDataLoadedCallback)
    }
    renderer.onStart()
    if (!mapboxMap.isStyleLoadInitiated) {
      // Load the style in mapInitOptions if no style has loaded yet.
      mapInitOptions.styleUri?.let {
        mapboxMap.loadStyle(it)
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
      removeOnCameraChangeListener(cameraChangedCallback)
      removeOnStyleDataLoadedListener(styleDataLoadedCallback)
    }
    renderer.onStop()
    pluginRegistry.onStop()
    // flush the queued events before destroy to avoid lost telemetry events
    MapProvider.flushPendingEvents()
  }

  override fun onDestroy() {
    if (lifecycleState == LifecycleState.STATE_DESTROYED) {
      return
    }
    lifecycleState = LifecycleState.STATE_DESTROYED
    pluginRegistry.onDestroy()
    nativeObserver.onDestroy()
    renderer.onDestroy()
    mapboxMap.onDestroy()
    this.style = null
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
    renderer.queueRenderEvent {
      renderer.onSurfaceChanged(w, h)
    }
    pluginRegistry.onSizeChanged(w, h)
  }

  override fun queueEvent(event: Runnable, needRender: Boolean) {
    if (needRender) {
      renderer.queueRenderEvent(event)
    } else {
      renderer.queueNonRenderEvent(event)
    }
  }

  override fun snapshot() = renderer.snapshot()

  override fun snapshot(listener: MapView.OnSnapshotReady) {
    renderer.snapshot(listener)
  }

  override fun setMaximumFps(fps: Int) {
    renderer.setMaximumFps(fps)
  }

  override fun setOnFpsChangedListener(listener: OnFpsChangedListener) {
    renderer.setOnFpsChangedListener(listener)
  }

  @OptIn(MapboxExperimental::class)
  override fun addWidget(widget: Widget) {
    if (contextMode != ContextMode.SHARED) {
      throw RuntimeException("Map view or map surface must be init with MapInitOptions.mapOptions.contextMode = ContextMode.SHARED when using widgets!")
    }
    widget.setTriggerRepaintAction {
      renderer.scheduleRepaint()
    }
    renderer.renderThread.addWidget(widget)
    renderer.scheduleRepaint()
  }

  @OptIn(MapboxExperimental::class)
  override fun removeWidget(widget: Widget): Boolean {
    val wasRemoved = renderer.renderThread.removeWidget(widget)
    if (wasRemoved) {
      renderer.scheduleRepaint()
    }
    return wasRemoved
  }

  override fun addRendererSetupErrorListener(rendererSetupErrorListener: RendererSetupErrorListener) {
    renderer.renderThread.renderHandlerThread.post {
      renderer.renderThread.eglCore.addRendererStateListener(rendererSetupErrorListener)
    }
  }

  override fun removeRendererSetupErrorListener(rendererSetupErrorListener: RendererSetupErrorListener) {
    renderer.renderThread.renderHandlerThread.post {
      renderer.renderThread.eglCore.removeRendererStateListener(rendererSetupErrorListener)
    }
  }

  internal fun setScreenRefreshRate(refreshRate: Int) {
    if (refreshRate <= 0) {
      logE(
        TAG,
        "Screen refresh rate could not be <= 0! " +
          "Setting max fps and fps counter will not work properly."
      )
      return
    }
    renderer.renderThread.setScreenRefreshRate(refreshRate)
  }

  //
  // Plugin API
  //

  override fun <T : MapPlugin> getPlugin(id: String): T? = pluginRegistry.getPlugin(id)

  fun createPlugin(
    mapView: MapView?,
    plugin: Plugin
  ) = pluginRegistry.createPlugin(mapView, mapInitOptions, plugin)

  @OptIn(MapboxExperimental::class)
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
            plugin.instance
              ?: throw MapboxConfigurationException("Custom non Mapbox plugins must have non-null `instance` parameter!")
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
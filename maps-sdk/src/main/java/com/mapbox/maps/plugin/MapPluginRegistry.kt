package com.mapbox.maps.plugin

import android.view.MotionEvent
import androidx.annotation.RestrictTo
import com.mapbox.maps.*
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import com.mapbox.maps.plugin.lifecycle.MapboxLifecyclePlugin
import java.util.concurrent.CopyOnWriteArraySet

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class MapPluginRegistry(private val mapDelegateProvider: MapDelegateProvider) {

  private enum class State {
    STARTED,
    STOPPED,
  }

  private var mapState = State.STOPPED
    set(value) {
      if (value != field) {
        field = value
        when (field) {
          State.STARTED -> plugins.values.forEach {
            if (it is LifecyclePlugin) {
              it.onStart()
            }
          }

          State.STOPPED -> plugins.values.forEach {
            if (it is LifecyclePlugin) {
              it.onStop()
            }
          }
        }
      }
    }

  private var mapSize: Pair<Int, Int>? = null

  private val plugins = mutableMapOf<String, MapPlugin>()
  private val cameraPlugins = CopyOnWriteArraySet<MapCameraPlugin>()
  private val gesturePlugins = CopyOnWriteArraySet<GesturesPlugin>()
  private val styleObserverPlugins = CopyOnWriteArraySet<MapStyleObserverPlugin>()
  private val mapSizePlugins = CopyOnWriteArraySet<MapSizePlugin>()
  private var mapboxLifecyclePlugin: MapboxLifecyclePlugin? = null

  fun createPlugin(
    mapView: MapView?,
    mapInitOptions: MapInitOptions,
    plugin: Plugin
  ) {
    val mapPlugin = plugin.instance
    if (mapPlugin == null) {
      throw MapboxConfigurationException("MapPlugin instance is missing for ${plugin.id}!")
    } else {
      if (!plugins.containsKey(plugin.id)) {
        if (plugin.instance is ViewPlugin && mapView == null) {
          // throw if view plugin if host is not a MapView (eg. MapSurface)
          throw InvalidViewPluginHostException("Cause: ${mapPlugin.javaClass}")
        }

        plugins[plugin.id] = mapPlugin
        mapPlugin.onDelegateProvider(mapDelegateProvider)

        if (mapPlugin is ViewPlugin) {
          val pluginView = mapPlugin.bind(
            mapView!!,
            mapInitOptions.attrs,
            mapInitOptions.mapOptions.pixelRatio
          )
          mapView.addView(pluginView)
          mapPlugin.onPluginView(pluginView)
        }

        if (mapPlugin is ContextBinder) {
          mapPlugin.bind(
            mapInitOptions.context,
            mapInitOptions.attrs,
            mapInitOptions.mapOptions.pixelRatio
          )
        }

        if (mapPlugin is MapSizePlugin) {
          mapSizePlugins.add(mapPlugin)
          mapSize?.let {
            mapPlugin.onSizeChanged(it.first, it.second)
          }
        }

        if (mapPlugin is MapCameraPlugin) {
          cameraPlugins.add(mapPlugin)
        }

        if (mapPlugin is GesturesPlugin) {
          gesturePlugins.add(mapPlugin)
        }

        if (mapPlugin is MapStyleObserverPlugin) {
          styleObserverPlugins.add(mapPlugin)
        }

        if (mapPlugin is MapboxLifecyclePlugin) {
          mapboxLifecyclePlugin = mapPlugin
        }

        mapPlugin.initialize()

        if (mapState == State.STARTED && mapPlugin is LifecyclePlugin) {
          mapPlugin.onStart()
        }
      } else {
        plugins[plugin.id]?.initialize()
      }
    }
  }

  @Suppress("UNCHECKED_CAST")
  fun <T> getPlugin(id: String): T? = plugins[id] as T

  fun removePlugin(id: String) {
    val plugin = plugins[id]
    when (plugin) {
      is MapCameraPlugin -> cameraPlugins.remove(plugin)
      is GesturesPlugin -> gesturePlugins.remove(plugin)
      is MapStyleObserverPlugin -> styleObserverPlugins.remove(plugin)
      is MapSizePlugin -> mapSizePlugins.remove(plugin)
      is MapboxLifecyclePlugin -> mapboxLifecyclePlugin = null
      else -> Unit
    }
    plugin?.cleanup()
    plugins.remove(id)
    logI(TAG, "Removed plugin: $id from the Map.")
  }

  fun onStart() {
    mapState = State.STARTED
  }

  fun onStop() {
    mapState = State.STOPPED
  }

  fun onDestroy() {
    plugins.forEach {
      it.value.cleanup()
    }
  }

  fun onTouch(event: MotionEvent?): Boolean {
    var onTouchResult = false
    gesturePlugins.forEach {
      onTouchResult = it.onTouchEvent(event) || onTouchResult
    }
    return onTouchResult
  }

  fun onGenericMotionEvent(event: MotionEvent): Boolean {
    var onGenericMotionEventResult = false
    gesturePlugins.forEach {
      onGenericMotionEventResult = it.onGenericMotionEvent(event) || onGenericMotionEventResult
    }
    return onGenericMotionEventResult
  }

  fun onSizeChanged(width: Int, height: Int) {
    mapSize = width to height
    for (mapSizePlugin in mapSizePlugins) {
      mapSizePlugin.onSizeChanged(width, height)
    }
  }

  fun onCameraMove(cameraState: CameraState) {
    cameraPlugins.forEach {
      it.onCameraMove(
        cameraState.center,
        cameraState.zoom,
        cameraState.pitch,
        cameraState.bearing,
        cameraState.padding
      )
    }
  }

  fun onStyleChanged(style: Style) {
    styleObserverPlugins.forEach {
      it.onStyleChanged(style)
    }
  }

  fun onAttachedToWindow(mapView: MapView) {
    mapboxLifecyclePlugin?.registerLifecycleObserver(mapView, mapView)
  }

  private companion object {
    private const val TAG = "MapPluginRegistry"
  }
}
package com.mapbox.maps.plugin

import android.view.MotionEvent
import android.view.View
import com.mapbox.maps.*
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import java.util.concurrent.CopyOnWriteArrayList

@Suppress("UNCHECKED_CAST")
internal class
MapPluginRegistry(private val mapDelegateProvider: MapDelegateProvider) {

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

  private val plugins = mutableMapOf<Class<*>, MapPlugin>()
  private val viewPlugins = mutableMapOf<ViewPlugin, View>()
  private val cameraPlugins = CopyOnWriteArrayList<MapCameraPlugin>()
  private val gesturePlugins = CopyOnWriteArrayList<GesturesPlugin>()
  private val styleObserverPlugins = CopyOnWriteArrayList<MapStyleObserverPlugin>()
  private val mapSizePlugins = CopyOnWriteArrayList<MapSizePlugin>()

  fun <T> createPlugin(
    mapView: MapView?,
    mapInitOptions: MapInitOptions,
    pluginImplementationClazz: Class<T>,
    vararg constructorArguments: Pair<Class<*>, Any>
  ): T {
    if (!plugins.containsKey(pluginImplementationClazz)) {
      val instance = pluginImplementationClazz.instantiate(*constructorArguments) as MapPlugin

      if (instance is ViewPlugin && mapView == null) {
        // throw if view plugin if host is not a MapView (eg. MapSurface)
        throw InvalidViewPluginHostException("Cause: $pluginImplementationClazz")
      }

      plugins[pluginImplementationClazz] = instance
      instance.onDelegateProvider(mapDelegateProvider)

      if (instance is ViewPlugin) {
        val pluginView = instance.bind(
          mapView!!,
          mapInitOptions.attrs,
          mapInitOptions.mapOptions.pixelRatio
        )
        mapView.addView(pluginView)
        instance.onPluginView(pluginView)
        viewPlugins[instance] = pluginView
      }

      if (instance is ContextBinder) {
        instance.bind(
          mapInitOptions.context,
          mapInitOptions.attrs,
          mapInitOptions.mapOptions.pixelRatio
        )
      }

      if (instance is MapSizePlugin) {
        mapSizePlugins.add(instance)
      }

      if (instance is MapCameraPlugin) {
        cameraPlugins.add(instance)
      }

      if (instance is GesturesPlugin) {
        gesturePlugins.add(instance)
      }

      if (instance is MapStyleObserverPlugin) {
        styleObserverPlugins.add(instance)
      }

      instance.initialize()

      if (mapState == State.STARTED && instance is LifecyclePlugin) {
        instance.onStart()
      }
      return instance as T
    } else {
      plugins[pluginImplementationClazz]?.initialize()
      return plugins[pluginImplementationClazz] as T
    }
  }

  fun <T> getPlugin(clazz: Class<T>): T? {
    return try {
      plugins[clazz] as T
    } catch (ex: Exception) {
      null
    }
  }

  fun onStart() {
    mapState = State.STARTED
  }

  fun onStop() {
    mapState = State.STOPPED
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
    for (mapSizePlugin in mapSizePlugins) {
      mapSizePlugin.onSizeChanged(width, height)
    }
  }

  fun onCameraMove(cameraOptions: CameraOptions) {
    cameraPlugins.forEach {
      it.onCameraMove(
        cameraOptions.center?.latitude() ?: 0.0,
        cameraOptions.center?.longitude() ?: 0.0,
        cameraOptions.zoom ?: 0.0,
        cameraOptions.pitch ?: 0.0,
        cameraOptions.bearing ?: 0.0,
        cameraOptions.padding?.let { insets ->
          arrayOf(insets.left, insets.top, insets.right, insets.bottom)
        },
        cameraOptions.anchor?.let { screenCoordinate ->
          Pair(screenCoordinate.x, screenCoordinate.y)
        }
      )
    }
  }

  fun onStyleChanged(style: Style) {
    styleObserverPlugins.forEach {
      it.onStyleChanged(style)
    }
  }

  fun cleanup() {
    plugins.forEach {
      it.value.cleanup()
    }
  }
}
package com.mapbox.maps

import com.mapbox.bindgen.Value
import com.mapbox.maps.listener.*
import com.mapbox.maps.plugin.MapPluginRegistry
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.collections.HashMap

internal class NativeMapObserver : Observer() {

  private lateinit var mapboxMap: MapboxMap
  private lateinit var nativeMap: MapInterface
  private lateinit var pluginRegistry: MapPluginRegistry

  // Style handlers
  internal val awaitingStyleGetters = mutableListOf<Style.OnStyleLoaded>()
  internal val awaitingStyleErrors = mutableListOf<Style.OnStyleError>()
  internal var onStyleLoaded: Style.OnStyleLoadedInternal? = null

  // High level listeners
  internal val onDidFinishRenderingFrameListeners =
    CopyOnWriteArrayList<OnDidFinishRenderingFrameListener>()
  internal val onCameraChangeListeners = CopyOnWriteArrayList<OnCameraChangeListener>()
  internal val onSourceChangeListeners = CopyOnWriteArrayList<OnSourceChangeListener>()
  internal val onStyleImageChangeListeners = CopyOnWriteArrayList<OnStyleImageChangeListener>()
  internal val onDidFinishRenderingMapListeners =
    CopyOnWriteArrayList<OnDidFinishRenderingMapListener>()
  internal val onDidBecomeIdleListeners = CopyOnWriteArrayList<OnDidBecomeIdleListener>()
  internal val onDidFinishLoadingMapListeners =
    CopyOnWriteArrayList<OnDidFinishLoadingMapListener>()
  internal val onWillStartLoadingMapListeners =
    CopyOnWriteArrayList<OnWillStartLoadingMapListener>()
  internal val onWillStartRenderingFrameListeners =
    CopyOnWriteArrayList<OnWillStartRenderingFrameListener>()
  internal val onWillStartRenderingMapListeners =
    CopyOnWriteArrayList<OnWillStartRenderingMapListener>()

  fun initialize(pluginRegistry: MapPluginRegistry, nativeMap: MapInterface, mapboxMap: MapboxMap) {
    this.pluginRegistry = pluginRegistry
    this.nativeMap = nativeMap
    this.mapboxMap = mapboxMap
    mapboxMap.subscribe(
      this,
      listOf(
        MapEvents.CAMERA_DID_CHANGE,
        MapEvents.CAMERA_WILL_CHANGE,
        MapEvents.CAMERA_IS_CHANGING,
        MapEvents.MAP_IDLE,
        MapEvents.MAP_LOADING_FINISHED,
        MapEvents.STYLE_LOADING_FINISHED,
        MapEvents.RENDER_FRAME_FINISHED,
        MapEvents.RENDER_MAP_FINISHED,
        MapEvents.MAP_LOADING_ERROR,
        MapEvents.SOURCE_CHANGED,
        MapEvents.STYLE_IMAGE_MISSING,
        MapEvents.STYLE_IMAGE_REMOVE_UNUSED,
        MapEvents.MAP_LOADING_STARTED,
        MapEvents.RENDER_FRAME_STARTED,
        MapEvents.RENDER_MAP_STARTED
      )
    )
  }

  fun clearListeners() {
    // unsubscribe core
    mapboxMap.unsubscribe(this)
    // style
    onStyleLoaded = null
    awaitingStyleErrors.clear()
    awaitingStyleGetters.clear()

    // listeners
    onDidFinishRenderingFrameListeners.clear()
    onCameraChangeListeners.clear()
    onSourceChangeListeners.clear()
    onStyleImageChangeListeners.clear()
    onDidFinishRenderingMapListeners.clear()
    onDidBecomeIdleListeners.clear()
    onDidFinishLoadingMapListeners.clear()
    onWillStartLoadingMapListeners.clear()
    onWillStartRenderingFrameListeners.clear()
    onWillStartRenderingMapListeners.clear()
  }

  override fun notify(event: Event) {
    when (event.type) {
      MapEvents.CAMERA_DID_CHANGE -> {
        pluginRegistry.onCameraMove(nativeMap.getCameraOptions(null))
        if (!onCameraChangeListeners.isEmpty()) {
          for (onCameraChangeListener in onCameraChangeListeners) {
            onCameraChangeListener.onCameraChanged()
          }
        }
        return
      }
      MapEvents.MAP_IDLE -> {
        if (!onDidBecomeIdleListeners.isEmpty()) {
          for (listener in onDidBecomeIdleListeners) {
            listener.onIdle()
          }
        }
        return
      }
      MapEvents.MAP_LOADING_FINISHED -> {
        if (!onDidFinishLoadingMapListeners.isEmpty()) {
          for (listener in onDidFinishLoadingMapListeners) {
            listener.onDidFinishLoadingMapListener()
          }
        }
        return
      }
      MapEvents.STYLE_LOADING_FINISHED -> {
        onStyleLoaded?.let {
          val style = it.onLoad()
          pluginRegistry.onStyleChanged(style)
        }
        return
      }
      MapEvents.RENDER_FRAME_FINISHED -> {
        if (!onDidFinishRenderingFrameListeners.isEmpty()) {
          for (listener in onDidFinishRenderingFrameListeners) {
            val data = event.data.contents as HashMap<String, Value>
            val renderFrameStatus = RenderFrameStatus(
              RenderMode.valueOf(
                data["render-mode"]!!.contents.toString().toUpperCase(Locale.getDefault())
              ),
              data["needs-repaint"]!!.contents.toString() == "true",
              data["placement-changed"]!!.contents.toString() == "true"
            )
            listener.onDidFinishRenderingFrame(renderFrameStatus)
          }
        }
        return
      }
      MapEvents.RENDER_MAP_FINISHED -> {
        if (!onDidFinishRenderingMapListeners.isEmpty()) {
          val data = event.data.contents as HashMap<String, Value>
          val renderMode = RenderMode.valueOf(
            data["render-mode"]!!.contents.toString().toUpperCase(Locale.getDefault())
          )
          for (listener in onDidFinishRenderingMapListeners) {
            listener.onDidFinishRenderingMap(renderMode)
          }
        }
        return
      }
      MapEvents.SOURCE_CHANGED -> {
        if (!onSourceChangeListeners.isEmpty()) {
          val data: HashMap<String, Value> = event.data.contents as HashMap<String, Value>
          data["id"]?.contents.let {
            for (listener in onSourceChangeListeners) {
              listener.onSourceChanged(it.toString())
            }
          }
        }
        return
      }
      MapEvents.STYLE_IMAGE_MISSING -> {
        if (!onStyleImageChangeListeners.isEmpty()) {
          val data: HashMap<String, Value> = event.data.contents as HashMap<String, Value>
          data["id"]?.contents.let {
            for (listener in onStyleImageChangeListeners) {
              listener.onStyleImageMissing(it.toString())
            }
          }
        }
        return
      }
      MapEvents.STYLE_IMAGE_REMOVE_UNUSED -> {
        if (!onStyleImageChangeListeners.isEmpty()) {
          val data: HashMap<String, Value> = event.data.contents as HashMap<String, Value>
          data["id"]?.contents.let {
            for (listener in onStyleImageChangeListeners) {
              listener.onCanRemoveUnusedStyleImage(it.toString())
            }
          }
        }
        return
      }
      MapEvents.MAP_LOADING_STARTED -> {
        pluginRegistry.onStyleLoading()
        if (!onWillStartLoadingMapListeners.isEmpty()) {
          for (listener in onWillStartLoadingMapListeners) {
            listener.onWillStartLoadingMap()
          }
        }
        return
      }
      MapEvents.RENDER_FRAME_STARTED -> {
        if (!onWillStartRenderingFrameListeners.isEmpty()) {
          for (listener in onWillStartRenderingFrameListeners) {
            listener.onWillStartRenderingFrame()
          }
        }
        return
      }
      MapEvents.RENDER_MAP_STARTED -> {
        if (!onWillStartRenderingMapListeners.isEmpty()) {
          for (listener in onWillStartRenderingMapListeners) {
            listener.onWillStartRenderingMap()
          }
        }
        return
      }
      MapEvents.MAP_LOADING_ERROR -> {
        val data: HashMap<String, Value> = event.data.contents as HashMap<String, Value>
        val error = "${data["error"]!!.contents} ${data["description"]!!.contents}"
        for (awaitingStyleError in awaitingStyleErrors) {
          awaitingStyleError.onStyleError(error)
        }
        awaitingStyleErrors.clear()
        awaitingStyleGetters.clear()
        return
      }
    }
  }

//
// Add / Remove
//

  fun addOnDidFinishRenderingFrameListener(onDidFinishRenderingFrameListener: OnDidFinishRenderingFrameListener) {
    onDidFinishRenderingFrameListeners.add(onDidFinishRenderingFrameListener)
  }

  fun removeOnDidFinishRenderingFrameListener(onDidFinishRenderingFrameListener: OnDidFinishRenderingFrameListener) {
    onDidFinishRenderingFrameListeners.remove(onDidFinishRenderingFrameListener)
  }

  fun addOnCameraChangeListener(onCameraChangeListener: OnCameraChangeListener) {
    onCameraChangeListeners.add(onCameraChangeListener)
  }

  fun removeOnCameraChangeListener(onCameraChangeListener: OnCameraChangeListener) {
    onCameraChangeListeners.remove(onCameraChangeListener)
  }

  fun addOnSourceChangeListener(onSourceChangeListener: OnSourceChangeListener) {
    onSourceChangeListeners.add(onSourceChangeListener)
  }

  fun removeOnSourceChangeListener(onSourceChangeListener: OnSourceChangeListener) {
    onSourceChangeListeners.remove(onSourceChangeListener)
  }

  fun addOnStyleImageChangeListener(onStyleImageChangeListener: OnStyleImageChangeListener) {
    onStyleImageChangeListeners.add(onStyleImageChangeListener)
  }

  fun removeOnStyleImageChangeListener(onStyleImageChangeListener: OnStyleImageChangeListener) {
    onStyleImageChangeListeners.remove(onStyleImageChangeListener)
  }

  fun addOnDidFinishRenderingMapListener(onDidFinishRenderingMapListener: OnDidFinishRenderingMapListener) {
    onDidFinishRenderingMapListeners.add(onDidFinishRenderingMapListener)
  }

  fun removeOnDidFinishRenderingMapListener(onDidFinishRenderingMapListener: OnDidFinishRenderingMapListener) {
    onDidFinishRenderingMapListeners.remove(onDidFinishRenderingMapListener)
  }

  fun addOnDidBecomeIdleListener(onDidBecomeIdleListener: OnDidBecomeIdleListener) {
    onDidBecomeIdleListeners.add(onDidBecomeIdleListener)
  }

  fun removeOnDidBecomeIdleListener(onDidBecomeIdleListener: OnDidBecomeIdleListener) {
    onDidBecomeIdleListeners.remove(onDidBecomeIdleListener)
  }

  fun addOnDidFinishLoadingMapListener(onDidFinishLoadingMapListener: OnDidFinishLoadingMapListener) {
    onDidFinishLoadingMapListeners.add(onDidFinishLoadingMapListener)
  }

  fun removeOnDidFinishLoadingMapListener(onDidFinishLoadingMapListener: OnDidFinishLoadingMapListener) {
    onDidFinishLoadingMapListeners.remove(onDidFinishLoadingMapListener)
  }

  fun addOnWillStartLoadingMapListener(onWillStartLoadingMapListener: OnWillStartLoadingMapListener) {
    onWillStartLoadingMapListeners.add(onWillStartLoadingMapListener)
  }

  fun removeOnWillStartLoadingMapListener(onWillStartLoadingMapListener: OnWillStartLoadingMapListener) {
    onWillStartLoadingMapListeners.remove(onWillStartLoadingMapListener)
  }

  fun addOnWillStartRenderingFrameListener(onWillStartRenderingFrameListener: OnWillStartRenderingFrameListener) {
    onWillStartRenderingFrameListeners.add(onWillStartRenderingFrameListener)
  }

  fun removeOnWillStartRenderingFrameListener(onWillStartRenderingFrameListener: OnWillStartRenderingFrameListener) {
    onWillStartRenderingFrameListeners.remove(onWillStartRenderingFrameListener)
  }

  fun addOnWillStartRenderingMapListener(onWillStartRenderingMapListener: OnWillStartRenderingMapListener) {
    onWillStartRenderingMapListeners.add(onWillStartRenderingMapListener)
  }

  fun removeOnWillStartRenderingMapListener(onWillStartRenderingMapListener: OnWillStartRenderingMapListener) {
    onWillStartRenderingMapListeners.remove(onWillStartRenderingMapListener)
  }
}
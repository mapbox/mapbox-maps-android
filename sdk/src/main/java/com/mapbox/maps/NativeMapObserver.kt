package com.mapbox.maps

import android.os.Handler
import com.mapbox.common.Logger
import com.mapbox.maps.plugin.delegates.listeners.*
import java.util.concurrent.CopyOnWriteArrayList

internal class NativeMapObserver(private val mainHandler: Handler) : MapObserver() {

  val onMapChangedListeners = CopyOnWriteArrayList<OnMapChangedListener>()
  val onMapLoadErrorListeners = CopyOnWriteArrayList<OnMapLoadErrorListener>()
  val onDidFinishRenderingFrameListeners =
    CopyOnWriteArrayList<OnDidFinishRenderingFrameListener>()
  val onCameraChangeListeners = CopyOnWriteArrayList<OnCameraChangeListener>()
  val onSourceChangeListeners = CopyOnWriteArrayList<OnSourceChangeListener>()
  val onStyleImageChangeListeners =
    CopyOnWriteArrayList<OnStyleImageChangeListener>()
  val onDidFinishRenderingMapListeners =
    CopyOnWriteArrayList<OnDidFinishRenderingMapListener>()
  val awaitingStyleGetters = mutableListOf<Style.OnStyleLoaded>()

  //
  // Internal callbacks
  //

  override fun onMapChanged(mapViewChange: MapChange) {
    if (onMapChangedListeners.isEmpty()) {
      return
    }

    mainHandler.post {
      onMapChangedListeners.forEach { it.onMapChange(mapViewChange) }
    }
  }

  override fun onMapLoadError(mapViewLoadError: MapLoadError, msg: String) {
    Logger.e(TAG, "$mapViewLoadError, msg: $msg")
    if (onMapLoadErrorListeners.isEmpty()) {
      return
    }
    mainHandler.post {
      onMapLoadErrorListeners.forEach { it.onMapLoadError(mapViewLoadError, msg) }
    }
  }

  override fun onDidFinishRenderingFrame(status: RenderFrameStatus) {
    if (onDidFinishRenderingFrameListeners.isEmpty()) {
      return
    }
    mainHandler.post {
      onDidFinishRenderingFrameListeners.forEach { it.onDidFinishRenderingFrame(status) }
    }
  }

  override fun onCameraChange(
    changeEvent: CameraChange,
    mode: CameraChangeMode
  ) {
    if (onCameraChangeListeners.isEmpty()) {
      return
    }
    mainHandler.post {
      if (changeEvent == CameraChange.CAMERA_DID_CHANGE) {
        onCameraChangeListeners.forEach { it.onCameraChanged() }
      }
    }
  }

  override fun onSourceChanged(id: String) {
    if (onSourceChangeListeners.isEmpty()) {
      return
    }
    mainHandler.post {
      onSourceChangeListeners.forEach {
        it.onSourceChanged(id)
      }
    }
  }

  override fun onStyleImageMissing(id: String) {
    if (onStyleImageChangeListeners.isEmpty()) {
      return
    }
    mainHandler.post {
      onStyleImageChangeListeners.forEach {
        it.onStyleImageMissing(id)
      }
    }
  }

  override fun onCanRemoveUnusedStyleImage(id: String): Boolean {
    if (onStyleImageChangeListeners.isEmpty()) {
      return false
    }
    onStyleImageChangeListeners.forEach {
      if (it.onCanRemoveUnusedStyleImage(id)) {
        return true
      }
    }
    return false
  }

  override fun onDidFinishRenderingMap(mode: RenderMode) {
    if (onDidFinishRenderingMapListeners.isEmpty()) {
      return
    }
    mainHandler.post {
      onDidFinishRenderingMapListeners.forEach {
        it.onDidFinishRenderingMap(mode)
      }
    }
  }

  //
  // Add / Remove
  //

  fun addOnMapChangedListener(onMapChangeListener: OnMapChangedListener) {
    onMapChangedListeners.add(onMapChangeListener)
  }

  fun removeOnMapChangedListener(onMapChangedListener: OnMapChangedListener) {
    onMapChangedListeners.remove(onMapChangedListener)
  }

  fun addOnMapLoadErrorListener(onMapLoadErrorListener: OnMapLoadErrorListener) {
    onMapLoadErrorListeners.add(onMapLoadErrorListener)
  }

  fun removeOnMapLoadErrorListener(onMapLoadErrorListener: OnMapLoadErrorListener) {
    onMapLoadErrorListeners.remove(onMapLoadErrorListener)
  }

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

  fun clearListeners() {
    onMapChangedListeners.clear()
    onMapLoadErrorListeners.clear()
    onDidFinishRenderingFrameListeners.clear()
    onCameraChangeListeners.clear()
    onSourceChangeListeners.clear()
    onStyleImageChangeListeners.clear()
    onDidFinishRenderingMapListeners.clear()
    awaitingStyleGetters.clear()
  }

  companion object {
    private const val TAG = "Mapbox-MapController"
  }
}
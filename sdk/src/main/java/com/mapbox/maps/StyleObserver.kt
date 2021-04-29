package com.mapbox.maps

import com.mapbox.common.Logger
import com.mapbox.maps.plugin.delegates.listeners.OnMapLoadErrorListener
import com.mapbox.maps.plugin.delegates.listeners.OnStyleLoadedListener
import com.mapbox.maps.plugin.delegates.listeners.eventdata.MapLoadErrorType
import java.lang.ref.WeakReference
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Class that listens to style error and load events
 * and maintains and invokes user added listeners.
 */
internal class StyleObserver(
  private val mapboxMap: MapboxMap,
  private val nativeMap: WeakReference<MapInterface>,
  private val nativeObserver: NativeObserver,
  private val pixelRatio: Float
) : OnStyleLoadedListener, OnMapLoadErrorListener {

  private val awaitingStyleLoadListeners = CopyOnWriteArrayList<Style.OnStyleLoaded>()
  private var awaitingStyleErrorListener: OnMapLoadErrorListener? = null

  init {
    nativeObserver.addOnStyleLoadedListener(this)
    nativeObserver.addOnMapLoadErrorListener(this)
  }

  /**
   * Clears previous added listeners and setup to receive callback for new style loaded or error events.
   */
  fun onNewStyleLoad(loadedListener: Style.OnStyleLoaded?, onMapLoadErrorListener: OnMapLoadErrorListener?) {
    awaitingStyleLoadListeners.clear()
    loadedListener?.let {
      awaitingStyleLoadListeners.add(loadedListener)
    }
    awaitingStyleErrorListener = onMapLoadErrorListener
  }

  /**
   * Add a style listener invoked when a new style loaded events occurs.
   */
  fun addOnStyleLoadListener(loadedListener: Style.OnStyleLoaded) {
    awaitingStyleLoadListeners.add(loadedListener)
  }

  /**
   * Invoked when a style has loaded
   */
  override fun onStyleLoaded() {
    nativeMap.get()?.let {
      mapboxMap.style = Style(it as StyleManagerInterface, pixelRatio)
      val iterator = awaitingStyleLoadListeners.iterator()
      while (iterator.hasNext()) {
        iterator.next().onStyleLoaded(mapboxMap.style)
      }
      awaitingStyleLoadListeners.clear()
    }
    awaitingStyleErrorListener = null
  }

  override fun onMapLoadError(mapLoadErrorType: MapLoadErrorType, message: String) {
    Logger.e(TAG, "OnMapLoadError: $mapLoadErrorType: $message")
    awaitingStyleErrorListener?.onMapLoadError(mapLoadErrorType, message)
    awaitingStyleLoadListeners.clear()
  }

  fun onDestroy() {
    awaitingStyleLoadListeners.clear()
    awaitingStyleErrorListener = null
    nativeObserver.removeOnMapLoadErrorListener(this)
    nativeObserver.removeOnStyleLoadedListener(this)
  }

  companion object {
    const val TAG = "Mbgl-StyleObserver"
  }
}
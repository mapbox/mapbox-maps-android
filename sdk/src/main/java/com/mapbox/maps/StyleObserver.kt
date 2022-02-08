package com.mapbox.maps

import com.mapbox.common.Logger
import com.mapbox.maps.extension.observable.eventdata.MapLoadingErrorEventData
import com.mapbox.maps.extension.observable.eventdata.StyleLoadedEventData
import com.mapbox.maps.plugin.delegates.listeners.OnMapLoadErrorListener
import com.mapbox.maps.plugin.delegates.listeners.OnStyleLoadedListener
import java.lang.ref.WeakReference
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Class that listens to style error and load events
 * and maintains and invokes user added listeners.
 */
internal class StyleObserver(
  private val nativeMapWeakRef: WeakReference<MapInterface>,
  private val styleLoadedListener: Style.OnStyleLoaded,
  private val nativeObserver: NativeObserver,
  private val pixelRatio: Float
) : OnStyleLoadedListener, OnMapLoadErrorListener {

  private var loadStyleListener: Style.OnStyleLoaded? = null
  private var loadStyleErrorListener: OnMapLoadErrorListener? = null

  private val getStyleListeners = CopyOnWriteArrayList<Style.OnStyleLoaded>()

  init {
    nativeObserver.addOnStyleLoadedListener(this)
    nativeObserver.addOnMapLoadErrorListener(this)
  }

  /**
   * Set a style listener coming from loadStyle request.
   * Overwrites listener from previous loadStyle request.
   * NOTE : listener is invoked only once after successful style load.
   */
  fun setLoadStyleListener(
    loadedListener: Style.OnStyleLoaded?,
    onMapLoadErrorListener: OnMapLoadErrorListener?
  ) {
    loadStyleListener = loadedListener
    loadStyleErrorListener = onMapLoadErrorListener
  }

  /**
   * Add a style listener coming from getStyle requests.
   * NOTE : listener is invoked only once after successful style load.
   */
  fun addGetStyleListener(loadedListener: Style.OnStyleLoaded) {
    getStyleListeners.add(loadedListener)
  }

  /**
   * Invoked when a style has loaded
   */
  override fun onStyleLoaded(eventData: StyleLoadedEventData) {
    nativeMapWeakRef.get()?.let {
      val style = Style(WeakReference(it), pixelRatio)
      styleLoadedListener.onStyleLoaded(style)

      loadStyleListener?.onStyleLoaded(style)
      loadStyleListener = null

      getStyleListeners.forEach { listener ->
        listener.onStyleLoaded(style)
      }
      getStyleListeners.clear()
    }
  }

  override fun onMapLoadError(eventData: MapLoadingErrorEventData) {
    Logger.e(
      TAG,
      "OnMapLoadError: ${eventData.type}, message: ${eventData.message}, sourceID: ${eventData.sourceId}, tileID: ${eventData.tileId}"
    )
    loadStyleErrorListener?.onMapLoadError(eventData)
  }

  fun onDestroy() {
    loadStyleListener = null
    loadStyleErrorListener = null
    getStyleListeners.clear()
    nativeObserver.removeOnMapLoadErrorListener(this)
    nativeObserver.removeOnStyleLoadedListener(this)
  }

  companion object {
    const val TAG = "Mbgl-StyleObserver"
  }
}
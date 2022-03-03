package com.mapbox.maps

import com.mapbox.common.Logger
import com.mapbox.maps.extension.observable.eventdata.MapLoadingErrorEventData
import com.mapbox.maps.extension.observable.eventdata.StyleDataLoadedEventData
import com.mapbox.maps.extension.observable.eventdata.StyleLoadedEventData
import com.mapbox.maps.extension.observable.model.StyleDataType
import com.mapbox.maps.plugin.delegates.listeners.OnMapLoadErrorListener
import com.mapbox.maps.plugin.delegates.listeners.OnStyleDataLoadedListener
import com.mapbox.maps.plugin.delegates.listeners.OnStyleLoadedListener
import java.lang.ref.WeakReference
import java.util.concurrent.CopyOnWriteArraySet

/**
 * Class that listens to style error and load events
 * and maintains and invokes user added listeners.
 */
internal class StyleObserver(
  private val nativeMapWeakRef: WeakReference<MapInterface>,
  private val styleLoadedListener: Style.OnStyleLoaded,
  private val nativeObserver: NativeObserver,
  private val pixelRatio: Float
) : OnStyleLoadedListener, OnMapLoadErrorListener, OnStyleDataLoadedListener {

  private var loadStyleListener: Style.OnStyleLoaded? = null
  private var loadStyleErrorListener: OnMapLoadErrorListener? = null
  private var loadStyleTransitionOptions: TransitionOptions? = null

  private val getStyleListeners = CopyOnWriteArraySet<Style.OnStyleLoaded>()

  init {
    nativeObserver.addOnStyleLoadedListener(this)
    nativeObserver.addOnMapLoadErrorListener(this)
    nativeObserver.addOnStyleDataLoadedListener(this)
  }

  /**
   * Set a style listener coming from loadStyle request.
   * Overwrites listener from previous loadStyle request.
   * NOTE : listener is invoked only once after successful style load.
   */
  fun setLoadStyleListener(
    transitionOptions: TransitionOptions?,
    loadedListener: Style.OnStyleLoaded?,
    onMapLoadErrorListener: OnMapLoadErrorListener?
  ) {
    loadStyleTransitionOptions = transitionOptions
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
      val style = Style(it, pixelRatio)
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

  override fun onStyleDataLoaded(eventData: StyleDataLoadedEventData) {
    // style data arrives in following order: STYLE, SOURCES, SPRITE
    // transition options must be applied after style but before sprite and sources to take effect
    loadStyleTransitionOptions?.let {
      if (eventData.type == StyleDataType.STYLE) {
        nativeMapWeakRef.get()?.styleTransition = it
        // per gl-native docs style transition options should be reset for a new style so resetting them here
        loadStyleTransitionOptions = null
      }
    }
  }

  fun onDestroy() {
    loadStyleListener = null
    loadStyleErrorListener = null
    loadStyleTransitionOptions = null
    getStyleListeners.clear()
    nativeObserver.removeOnMapLoadErrorListener(this)
    nativeObserver.removeOnStyleLoadedListener(this)
    nativeObserver.removeOnStyleDataLoadedListener(this)
  }

  companion object {
    const val TAG = "Mbgl-StyleObserver"
  }
}
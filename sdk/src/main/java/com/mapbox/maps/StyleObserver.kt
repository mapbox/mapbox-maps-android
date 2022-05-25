package com.mapbox.maps

import com.mapbox.maps.extension.observable.eventdata.MapLoadingErrorEventData
import com.mapbox.maps.extension.observable.eventdata.StyleDataLoadedEventData
import com.mapbox.maps.extension.observable.eventdata.StyleLoadedEventData
import com.mapbox.maps.extension.observable.model.StyleDataType
import com.mapbox.maps.plugin.delegates.listeners.OnMapLoadErrorListener
import com.mapbox.maps.plugin.delegates.listeners.OnStyleDataLoadedListener
import com.mapbox.maps.plugin.delegates.listeners.OnStyleLoadedListener
import java.util.concurrent.CopyOnWriteArraySet

/**
 * Class that listens to style error and load events
 * and maintains and invokes user added listeners.
 */
internal class StyleObserver(
  private val nativeMap: MapInterface,
  private val styleLoadedListener: Style.OnStyleLoaded,
  private val nativeObserver: NativeObserver,
  private val pixelRatio: Float
) : OnStyleLoadedListener, OnMapLoadErrorListener, OnStyleDataLoadedListener {

  private var userStyleLoadedListener: Style.OnStyleLoaded? = null

  private var styleDataStyleLoadedListener: Style.OnStyleLoaded? = null
  private var styleDataSpritesLoadedListener: Style.OnStyleLoaded? = null
  private var styleDataSourcesLoadedListener: Style.OnStyleLoaded? = null

  private var loadStyleErrorListener: OnMapLoadErrorListener? = null

  private val getStyleListeners = CopyOnWriteArraySet<Style.OnStyleLoaded>()
  private var loadedStyle: Style? = null

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
    userOnStyleLoaded: Style.OnStyleLoaded?,
    styleDataStyleLoadedListener: Style.OnStyleLoaded? = null,
    styleDataSpritesLoadedListener: Style.OnStyleLoaded? = null,
    styleDataSourcesLoadedListener: Style.OnStyleLoaded? = null,
    onMapLoadErrorListener: OnMapLoadErrorListener?
  ) {
    this.userStyleLoadedListener = userOnStyleLoaded
    this.styleDataStyleLoadedListener = styleDataStyleLoadedListener
    this.styleDataSpritesLoadedListener = styleDataSpritesLoadedListener
    this.styleDataSourcesLoadedListener = styleDataSourcesLoadedListener
    this.loadStyleErrorListener = onMapLoadErrorListener
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
    val style = loadedStyle ?: throw MapboxMapException("Style is not initialized on onStyleLoaded callback!")

    onStyleSpritesReady()
    onStyleSourcesReady()
    styleLoadedListener.onStyleLoaded(style)

    userStyleLoadedListener?.let {
      userStyleLoadedListener = null
      it.onStyleLoaded(style)
    }

    getStyleListeners.forEach { listener ->
      listener.onStyleLoaded(style)
    }
    getStyleListeners.clear()
  }

  override fun onMapLoadError(eventData: MapLoadingErrorEventData) {
    logE(
      TAG,
      "OnMapLoadError: ${eventData.type}, message: ${eventData.message}, sourceID: ${eventData.sourceId}, tileID: ${eventData.tileId}"
    )
    loadStyleErrorListener?.onMapLoadError(eventData)
  }

  override fun onStyleDataLoaded(eventData: StyleDataLoadedEventData) {
    when (eventData.type) {
      StyleDataType.STYLE -> {
        loadedStyle?.markInvalid()
        loadedStyle = Style(nativeMap, pixelRatio).also {
          styleDataStyleLoadedListener?.onStyleLoaded(it)
        }
        styleDataStyleLoadedListener = null
      }
      StyleDataType.SPRITE -> {
        onStyleSpritesReady()
      }
      StyleDataType.SOURCES -> {
        onStyleSourcesReady()
      }
    }
  }

  private fun onStyleSourcesReady() {
    // sources listener is not null - it means style does not contain them thus StyleDataType.SOURCES
    // was not fired, explicitly call it to bind all the necessary style extensions upstream
    styleDataSourcesLoadedListener?.let {
      // reset listener first, firing onStyleLoaded
      // may produce another StyleDataType.SOURCES event if sources are added
      styleDataSourcesLoadedListener = null
      it.onStyleLoaded(
        loadedStyle ?: throw MapboxMapException("Style is not initialized yet although SOURCES event has arrived!")
      )
    }
  }

  private fun onStyleSpritesReady() {
    // sprites listener is not null - it means style does not contain them thus StyleDataType.SPRITE
    // was not fired, explicitly call it to bind all the necessary style extensions upstream
    styleDataSpritesLoadedListener?.let {
      styleDataSpritesLoadedListener = null
      it.onStyleLoaded(
        loadedStyle ?: throw MapboxMapException("Style is not initialized yet although SPRITES event has arrived!")
      )
    }
  }

  fun onDestroy() {
    userStyleLoadedListener = null
    styleDataStyleLoadedListener = null
    styleDataSpritesLoadedListener = null
    styleDataSourcesLoadedListener = null
    loadStyleErrorListener = null
    loadedStyle?.markInvalid()
    loadedStyle = null
    getStyleListeners.clear()
    nativeObserver.removeOnMapLoadErrorListener(this)
    nativeObserver.removeOnStyleLoadedListener(this)
    nativeObserver.removeOnStyleDataLoadedListener(this)
  }

  companion object {
    const val TAG = "Mbgl-StyleObserver"
  }
}
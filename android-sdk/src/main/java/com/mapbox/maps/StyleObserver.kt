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

  // fired when [StyleDataType.STYLE] arrives, first of all style data loaded events
  private var styleDataStyleLoadedListener: Style.OnStyleLoaded? = null
  // if the listener is not null - style load has been started
  // but the initial event has NOT arrived yet
  private val isWaitingStyleDataStyleEvent
    get() = styleDataStyleLoadedListener != null
  // fired when [StyleDataType.SPRITE] arrives
  private var styleDataSpritesLoadedListener: Style.OnStyleLoaded? = null
  // fired when [StyleDataType.SOURCES] arrives
  private var styleDataSourcesLoadedListener: Style.OnStyleLoaded? = null

  private var loadStyleErrorListener: OnMapLoadErrorListener? = null

  private val getStyleListeners = CopyOnWriteArraySet<Style.OnStyleLoaded>()
  /** Initialized after [MapEvents.STYLE_LOADED] event from [preLoadedStyle], sent to user and all plugins. */
  private var loadedStyle: Style? = null
  /** Initialized after [MapEvents.STYLE_DATA_LOADED] event to notify [styleDataStyleLoadedListener]
   *  listener to apply style extension properties. They should be applied ASAP (before the final
   *  [MapEvents.STYLE_LOADED] event) to make sure map is rendered correctly from the very beginning
   *  (e.g. with the correct [Projection]). */
  private var preLoadedStyle: Style? = null

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
    styleDataStyleLoadedListener: Style.OnStyleLoaded,
    styleDataSpritesLoadedListener: Style.OnStyleLoaded? = null,
    styleDataSourcesLoadedListener: Style.OnStyleLoaded? = null,
    onMapLoadErrorListener: OnMapLoadErrorListener?
  ) {
    // needed to prevent receiving onStyleLoaded for the old style in some rare cases
    nativeObserver.resubscribeStyleLoadListeners()
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
    loadedStyle?.markInvalid()
    loadedStyle = preLoadedStyle
    val style = loadedStyle
      ?: throw MapboxMapException("Style is not initialized on onStyleLoaded callback!")

    // notify style extension DSL in case StyleDataType.SPRITES was not fired
    onStyleSpritesReady()
    // notify style extension DSL in case StyleDataType.SOURCES was not fired
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
        preLoadedStyle = Style(nativeMap, pixelRatio).also {
          styleDataStyleLoadedListener?.onStyleLoaded(it)
        }
        styleDataStyleLoadedListener = null
      }
      StyleDataType.SPRITE -> {
        // if we're waiting for the [StyleDataType.STYLE] event - then this [StyleDataType.SPRITE]
        // is related to the previously loaded style, don't notify style extension DSL about it
        if (!isWaitingStyleDataStyleEvent) {
          onStyleSpritesReady()
        }
      }
      StyleDataType.SOURCES -> {
        // if we're waiting for the [StyleDataType.STYLE] event - then this [StyleDataType.SOURCES]
        // is related to the previously loaded style, don't notify style extension DSL about it
        if (!isWaitingStyleDataStyleEvent) {
          onStyleSourcesReady()
        }
      }
    }
  }

  private fun onStyleSourcesReady() {
    styleDataSourcesLoadedListener?.let { listener ->
      // reset listener first, firing onStyleLoaded
      // may produce another StyleDataType.SOURCES event if sources are added
      styleDataSourcesLoadedListener = null
      preLoadedStyle?.let(listener::onStyleLoaded)
        ?: logE(TAG, "Style is not initialized yet although SOURCES event has arrived!")
    }
  }

  private fun onStyleSpritesReady() {
    styleDataSpritesLoadedListener?.let { listener ->
      styleDataSpritesLoadedListener = null
      preLoadedStyle?.let(listener::onStyleLoaded)
        ?: logE(TAG, "Style is not initialized yet although SPRITES event has arrived!")
    }
  }

  fun onDestroy() {
    userStyleLoadedListener = null
    styleDataStyleLoadedListener = null
    styleDataSpritesLoadedListener = null
    styleDataSourcesLoadedListener = null
    loadStyleErrorListener = null
    preLoadedStyle?.markInvalid()
    preLoadedStyle = null
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
package com.mapbox.maps

import androidx.annotation.RestrictTo
import androidx.annotation.VisibleForTesting
import com.mapbox.common.Cancelable
import java.util.concurrent.CopyOnWriteArraySet
import java.util.concurrent.atomic.AtomicReference

/**
 * Class that listens to style error and load events
 * and maintains and invokes user added listeners.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class StyleObserver(
  private val styleManager: StyleManager,
  private val styleLoadedListener: Style.OnStyleLoaded,
  private val nativeObserver: NativeObserver,
  private val pixelRatio: Float,
  private val mapLoadingErrorDelegate: MapLoadingErrorDelegate,
  private val threadChecker: MapThreadChecker,
) : StyleLoadedCallback, MapLoadingErrorCallback, StyleDataLoadedCallback {

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
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  val getStyleListeners = CopyOnWriteArraySet<GetStyleRequest>()

  /** Initialized after [MapEvent.STYLE_LOADED] event from [preLoadedStyle], sent to user and all plugins. */
  private var loadedStyle: Style? = null

  /** Initialized after [MapEvent.STYLE_DATA_LOADED] event to notify [styleDataStyleLoadedListener]
   *  listener to apply style extension properties. They should be applied ASAP (before the final
   *  [MapEvent.STYLE_LOADED] event) to make sure map is rendered correctly from the very beginning
   *  (e.g. with the correct [Projection]). */
  private var preLoadedStyle: Style? = null
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  val cancelableList = CopyOnWriteArraySet<Cancelable>()

  init {
    cancelableList.apply {
      add(nativeObserver.subscribeStyleLoaded(this@StyleObserver))
      add(nativeObserver.subscribeMapLoadingError(this@StyleObserver))
      add(nativeObserver.subscribeStyleDataLoaded(this@StyleObserver))
    }
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
  ) {
    // needed to prevent receiving onStyleLoaded for the old style in some rare cases
    nativeObserver.resubscribeStyleLoadListeners()
    this.userStyleLoadedListener = userOnStyleLoaded
    this.styleDataStyleLoadedListener = styleDataStyleLoadedListener
    this.styleDataSpritesLoadedListener = styleDataSpritesLoadedListener
    this.styleDataSourcesLoadedListener = styleDataSourcesLoadedListener
  }

  /**
   * Add a style listener coming from getStyle requests.
   * NOTE : listener is invoked only once after successful style load.
   *
   * After [onDestroy] the listener is not registered, because it could never be invoked
   * and would only stay referenced.
   *
   * @return a [Cancelable] that removes this registration. Cancelling after the listener
   * was invoked, or cancelling more than once, has no effect.
   */
  fun addGetStyleListener(loadedListener: Style.OnStyleLoaded): Cancelable {
    // filled in init and cleared only by onDestroy, so empty means destroyed
    if (cancelableList.isEmpty()) return EmptyCancelable
    val request = GetStyleRequest(loadedListener, getStyleListeners)
    if (getStyleListeners.add(request)) return request
    return getStyleListeners.firstOrNull { it == request } ?: request
  }

  /**
   * Invoked when a style has loaded
   */
  override fun run(eventData: StyleLoaded) {
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

  /**
   * Invoked when map loading error occurred.
   */
  override fun run(eventData: MapLoadingError) {
    logE(
      TAG,
      "OnMapLoadError: ${eventData.type}, message: ${eventData.message}, sourceID: ${eventData.sourceId}, tileID: ${eventData.tileId}"
    )
  }

  /**
   * Invoked when style data is loaded.
   */
  override fun run(eventData: StyleDataLoaded) {
    when (eventData.type) {
      StyleDataLoadedType.STYLE -> {
        preLoadedStyle = Style(styleManager, pixelRatio, mapLoadingErrorDelegate, threadChecker).also {
          styleDataStyleLoadedListener?.onStyleLoaded(it)
        }
        styleDataStyleLoadedListener = null
      }
      StyleDataLoadedType.SPRITE -> {
        // if we're waiting for the [StyleDataType.STYLE] event - then this [StyleDataType.SPRITE]
        // is related to the previously loaded style, don't notify style extension DSL about it
        if (!isWaitingStyleDataStyleEvent) {
          onStyleSpritesReady()
        }
      }
      StyleDataLoadedType.SOURCES -> {
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
    preLoadedStyle?.markInvalid()
    preLoadedStyle = null
    loadedStyle?.markInvalid()
    loadedStyle = null
    getStyleListeners.forEach { it.release() }
    getStyleListeners.clear()
    cancelableList.forEach { it.cancel() }
    cancelableList.clear()
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  class GetStyleRequest(
    listener: Style.OnStyleLoaded,
    requests: MutableSet<GetStyleRequest>,
  ) : Style.OnStyleLoaded, Cancelable {
    private class State(
      val listener: Style.OnStyleLoaded,
      val requests: MutableSet<GetStyleRequest>,
    )

    /**
     * The listener plus the set this request was added to, so [cancel] can remove it.
     * The set is passed in instead of using an inner class to avoid holding a reference to the
     * whole [StyleObserver].
     *
     * Doubles as a one-shot flag: whichever of [onStyleLoaded], [cancel] or [release] nulls it
     * first wins, the others are no-ops. Nulling it also drops both references, so a
     * [Cancelable] kept by the caller retains neither the listener nor the set.
     */
    private val state = AtomicReference<State?>(State(listener, requests))

    private val hash = listener.hashCode()

    override fun onStyleLoaded(style: Style) {
      state.getAndSet(null)?.listener?.onStyleLoaded(style)
    }

    override fun cancel() {
      // remove(this) still matches after the state is cleared, through the identity check in equals
      state.getAndSet(null)?.requests?.remove(this)
    }

    fun release() {
      state.set(null)
    }

    /**
     * A pending request equals another pending request for the same listener.
     * A finished request only equals itself.
     */
    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (other !is GetStyleRequest) return false
      val listener = state.get()?.listener ?: return false
      return listener == other.state.get()?.listener
    }

    override fun hashCode(): Int = hash
  }

  companion object {
    const val TAG = "Mbgl-StyleObserver"
  }
}
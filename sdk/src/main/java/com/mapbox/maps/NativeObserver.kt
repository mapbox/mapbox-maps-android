package com.mapbox.maps

import androidx.annotation.UiThread
import androidx.annotation.VisibleForTesting
import com.mapbox.common.Cancelable
import com.mapbox.maps.extension.observable.*
import com.mapbox.maps.plugin.delegates.listeners.*
import java.util.Objects
import java.util.concurrent.CopyOnWriteArraySet

@Suppress("DeprecatedCallableAddReplaceWith", "DEPRECATION")
@UiThread
internal class NativeObserver(
  private val observable: NativeMapImpl
) {
  /**
   * A [Cancelable] that will add itself to the given [cancelableSet] when created and remove
   * itself when [cancel]ed.
   *
   * @param originalCancelable the original [Cancelable]. Its [Cancelable.cancel] will be called when [cancel] is executed
   * @param cancelableSet a [MutableSet] that will hold this cancelable while it is active.
   * @param onCancel function to be called after [cancel] is executed
   */
  private open inner class ExtendedCancelable(
    open val originalCancelable: Cancelable,
    val onCancel: (() -> Unit)? = null,
    private val cancelableSet: MutableSet<Cancelable> = this@NativeObserver.cancelableSet,
  ) : Cancelable {
    init {
      @Suppress("LeakingThis")
      cancelableSet.add(this)
    }

    /**
     * Calls cancel on [originalCancelable] and also removes itself from [cancelableSet].
     */
    override fun cancel() {
      cancelableSet.remove(this)
      originalCancelable.cancel()
      onCancel?.invoke()
    }

    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (javaClass != other?.javaClass) return false

      other as ExtendedCancelable

      if (originalCancelable != other.originalCancelable) return false
      if (cancelableSet != other.cancelableSet) return false
      if (onCancel != other.onCancel) return false

      return true
    }

    override fun hashCode(): Int = Objects.hash(originalCancelable, cancelableSet, onCancel)
  }

  /**
   * An [ExtendedCancelable] that holds the original [callback] to be able to resubscribe later.
   *
   * @param callback the callback to use when [resubscribe] is called.
   * @param originalCancelable the original [Cancelable]. Its [Cancelable.cancel] will be called when [cancel] is executed. It will be updated when [resubscribe] is executed.
   * @param cancelableSet a [MutableSet] that will hold this cancelable while it is active.
   * @param onCancel function to be called after [cancel] is executed
   */
  @Suppress("UNCHECKED_CAST")
  private inner class ResubscribeExtendedCancelable<C>(
    private val callback: C,
    override var originalCancelable: Cancelable,
    cancelableSet: MutableSet<ResubscribeExtendedCancelable<C>>,
    onCancel: (() -> Unit)? = null
  ) : ExtendedCancelable(originalCancelable, onCancel, cancelableSet as MutableSet<Cancelable>) {
    /**
     * Resubscribes [callback] and stores the returned [Cancelable] from calling [subscribe]
     * in [originalCancelable]
     *
     * @param subscribe the method responsible to subscribe [callback]
     */
    fun resubscribe(subscribe: (C) -> Cancelable) {
      originalCancelable.cancel()
      originalCancelable = subscribe(callback)
    }

    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (javaClass != other?.javaClass) return false
      if (!super.equals(other)) return false

      other as ResubscribeExtendedCancelable<*>

      return callback == other.callback
    }

    override fun hashCode(): Int = Objects.hash(super.hashCode(), callback)
  }

  /**
   * Track old listeners, we keep them in order to have minimum breaking change.
   * will be removed in next major release.
   */
  val onCameraChangeListeners = CopyOnWriteArraySet<OnCameraChangeListener>()
  val onMapIdleListeners = CopyOnWriteArraySet<OnMapIdleListener>()
  val onMapLoadErrorListeners = CopyOnWriteArraySet<OnMapLoadErrorListener>()
  val onMapLoadedListeners = CopyOnWriteArraySet<OnMapLoadedListener>()
  val onRenderFrameFinishedListeners = CopyOnWriteArraySet<OnRenderFrameFinishedListener>()
  val onRenderFrameStartedListeners = CopyOnWriteArraySet<OnRenderFrameStartedListener>()
  val onSourceAddedListeners = CopyOnWriteArraySet<OnSourceAddedListener>()
  val onSourceRemovedListeners = CopyOnWriteArraySet<OnSourceRemovedListener>()
  val onSourceDataLoadedListeners = CopyOnWriteArraySet<OnSourceDataLoadedListener>()
  val onStyleDataLoadedListeners = CopyOnWriteArraySet<OnStyleDataLoadedListener>()
  val onStyleLoadedListeners = CopyOnWriteArraySet<OnStyleLoadedListener>()
  val onStyleImageMissingListeners = CopyOnWriteArraySet<OnStyleImageMissingListener>()
  val onStyleImageUnusedListeners = CopyOnWriteArraySet<OnStyleImageUnusedListener>()

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal val cancelableSet = CopyOnWriteArraySet<Cancelable>()

  private val styleDataLoadedCancelableSet = CopyOnWriteArraySet<ResubscribeExtendedCancelable<StyleDataLoadedCallback>>()
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  // ResubscribeExtendedCancelable is private so we expose the set as generic Cancelable set
  internal val _styleDataLoadedCancelableSet: MutableSet<out Cancelable> = styleDataLoadedCancelableSet

  private val styleLoadedCancelableSet = CopyOnWriteArraySet<ResubscribeExtendedCancelable<StyleLoadedCallback>>()
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  // ResubscribeExtendedCancelable is private so we expose the set as generic Cancelable set
  internal val _styleLoadedCancelableSet: MutableSet<out Cancelable> = styleLoadedCancelableSet

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnCameraChangeListener(onCameraChangeListener: OnCameraChangeListener) {
    subscribeCameraChanged({
      onCameraChangeListener.onCameraChanged(it.toCameraChangedEventData())
    })
    onCameraChangeListeners.add(onCameraChangeListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnCameraChangeListener(onCameraChangeListener: OnCameraChangeListener) {
    onCameraChangeListeners.remove(onCameraChangeListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnMapIdleListener(onMapIdleListener: OnMapIdleListener) {
    subscribeMapIdle({
      onMapIdleListener.onMapIdle(it.toMapIdleEventData())
    })
    onMapIdleListeners.add(onMapIdleListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnMapIdleListener(onMapIdleListener: OnMapIdleListener) {
    onMapIdleListeners.remove(onMapIdleListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnMapLoadErrorListener(onMapLoadErrorListener: OnMapLoadErrorListener) {
    subscribeMapLoadingError({
      onMapLoadErrorListener.onMapLoadError(it.toMapLoadingErrorEventData())
    })
    onMapLoadErrorListeners.add(onMapLoadErrorListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnMapLoadErrorListener(onMapLoadErrorListener: OnMapLoadErrorListener) {
    onMapLoadErrorListeners.remove(onMapLoadErrorListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnMapLoadedListener(onMapLoadedListener: OnMapLoadedListener) {
    subscribeMapLoaded({
      onMapLoadedListener.onMapLoaded(it.toMapLoadedEventData())
    })
    onMapLoadedListeners.add(onMapLoadedListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnMapLoadedListener(onMapLoadedListener: OnMapLoadedListener) {
    onMapLoadedListeners.remove(onMapLoadedListener)
  }

  // Render frame events
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnRenderFrameFinishedListener(onRenderFrameFinishedListener: OnRenderFrameFinishedListener) {
    subscribeRenderFrameFinished({
      onRenderFrameFinishedListener.onRenderFrameFinished(it.toRenderFrameFinishedEventData())
    })
    onRenderFrameFinishedListeners.add(onRenderFrameFinishedListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnRenderFrameFinishedListener(onRenderFrameFinishedListener: OnRenderFrameFinishedListener) {
    onRenderFrameFinishedListeners.remove(onRenderFrameFinishedListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnRenderFrameStartedListener(onRenderFrameStartedListener: OnRenderFrameStartedListener) {
    subscribeRenderFrameStarted({
      onRenderFrameStartedListener.onRenderFrameStarted(it.toRenderFrameStartedEventData())
    })
    onRenderFrameStartedListeners.add(onRenderFrameStartedListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnRenderFrameStartedListener(onRenderFrameStartedListener: OnRenderFrameStartedListener) {
    onRenderFrameStartedListeners.remove(onRenderFrameStartedListener)
  }

  // Source events
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnSourceAddedListener(onSourceAddedListener: OnSourceAddedListener) {
    subscribeSourceAdded({
      onSourceAddedListener.onSourceAdded(it.toSourceAddedEventData())
    })
    onSourceAddedListeners.add(onSourceAddedListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnSourceAddedListener(onSourceAddedListener: OnSourceAddedListener) {
    onSourceAddedListeners.remove(onSourceAddedListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnSourceDataLoadedListener(onSourceDataLoadedListener: OnSourceDataLoadedListener) {
    subscribeSourceDataLoaded({
      onSourceDataLoadedListener.onSourceDataLoaded(it.toSourceDataLoadedEventData())
    })
    onSourceDataLoadedListeners.add(onSourceDataLoadedListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnSourceDataLoadedListener(onSourceDataLoadedListener: OnSourceDataLoadedListener) {
    onSourceDataLoadedListeners.remove(onSourceDataLoadedListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnSourceRemovedListener(onSourceRemovedListener: OnSourceRemovedListener) {
    subscribeSourceRemoved({
      onSourceRemovedListener.onSourceRemoved(it.toSourceRemovedEventData())
    })
    onSourceRemovedListeners.add(onSourceRemovedListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnSourceRemovedListener(onSourceRemovedListener: OnSourceRemovedListener) {
    onSourceRemovedListeners.remove(onSourceRemovedListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnStyleLoadedListener(onStyleLoadedListener: OnStyleLoadedListener) {
    subscribeStyleLoaded({
      onStyleLoadedListener.onStyleLoaded(it.toStyleLoadedEventData())
    })
    onStyleLoadedListeners.add(onStyleLoadedListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnStyleLoadedListener(onStyleLoadedListener: OnStyleLoadedListener) {
    onStyleLoadedListeners.remove(onStyleLoadedListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnStyleDataLoadedListener(onStyleDataLoadedListener: OnStyleDataLoadedListener) {
    subscribeStyleDataLoaded({
      onStyleDataLoadedListener.onStyleDataLoaded(it.toStyleDataLoadedEventData())
    })
    onStyleDataLoadedListeners.add(onStyleDataLoadedListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnStyleDataLoadedListener(onStyleDataLoadedListener: OnStyleDataLoadedListener) {
    onStyleDataLoadedListeners.remove(onStyleDataLoadedListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnStyleImageMissingListener(onStyleImageMissingListener: OnStyleImageMissingListener) {
    subscribeStyleImageMissing({
      onStyleImageMissingListener.onStyleImageMissing(it.getStyleImageMissingEventData())
    })
    onStyleImageMissingListeners.add(onStyleImageMissingListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnStyleImageMissingListener(onStyleImageMissingListener: OnStyleImageMissingListener) {
    onStyleImageMissingListeners.remove(onStyleImageMissingListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnStyleImageUnusedListener(onStyleImageUnusedListener: OnStyleImageUnusedListener) {
    subscribeStyleImageRemoveUnused({
      onStyleImageUnusedListener.onStyleImageUnused(it.toStyleImageUnusedEventData())
    })
    onStyleImageUnusedListeners.add(onStyleImageUnusedListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnStyleImageUnusedListener(onStyleImageUnusedListener: OnStyleImageUnusedListener) {
    onStyleImageUnusedListeners.remove(onStyleImageUnusedListener)
  }

  fun subscribeCameraChanged(
    cameraChangedCallback: CameraChangedCallback,
    onCancel: (() -> Unit)? = null
  ): Cancelable = ExtendedCancelable(
    observable.subscribe(cameraChangedCallback),
    onCancel
  )

  fun subscribeMapLoaded(
    mapLoadedCallback: MapLoadedCallback,
    onCancel: (() -> Unit)? = null
  ): Cancelable = ExtendedCancelable(
    observable.subscribe(mapLoadedCallback),
    onCancel
  )

  fun subscribeMapIdle(
    mapIdleCallback: MapIdleCallback,
    onCancel: (() -> Unit)? = null
  ): Cancelable = ExtendedCancelable(
    observable.subscribe(mapIdleCallback),
    onCancel
  )

  fun subscribeMapLoadingError(
    mapLoadingErrorCallback: MapLoadingErrorCallback,
    onCancel: (() -> Unit)? = null
  ): Cancelable = ExtendedCancelable(
    observable.subscribe(mapLoadingErrorCallback),
    onCancel
  )

  fun subscribeStyleLoaded(
    styleLoadedCallback: StyleLoadedCallback,
    onCancel: (() -> Unit)? = null
  ): Cancelable = ResubscribeExtendedCancelable(
    styleLoadedCallback,
    observable.subscribe(styleLoadedCallback),
    styleLoadedCancelableSet,
    onCancel
  )

  fun subscribeStyleDataLoaded(
    styleDataLoadedCallback: StyleDataLoadedCallback,
    onCancel: (() -> Unit)? = null
  ): Cancelable = ResubscribeExtendedCancelable(
    styleDataLoadedCallback,
    observable.subscribe(styleDataLoadedCallback),
    styleDataLoadedCancelableSet,
    onCancel
  )

  fun subscribeSourceDataLoaded(
    sourceDataLoadedCallback: SourceDataLoadedCallback,
    onCancel: (() -> Unit)? = null
  ): Cancelable = ExtendedCancelable(
    observable.subscribe(sourceDataLoadedCallback),
    onCancel
  )

  fun subscribeSourceAdded(
    sourceAddedCallback: SourceAddedCallback,
    onCancel: (() -> Unit)? = null
  ): Cancelable = ExtendedCancelable(
    observable.subscribe(sourceAddedCallback),
    onCancel
  )

  fun subscribeSourceRemoved(
    sourceRemovedCallback: SourceRemovedCallback,
    onCancel: (() -> Unit)? = null
  ): Cancelable = ExtendedCancelable(
    observable.subscribe(sourceRemovedCallback),
    onCancel
  )

  fun subscribeStyleImageMissing(
    styleImageMissingCallback: StyleImageMissingCallback,
    onCancel: (() -> Unit)? = null
  ): Cancelable = ExtendedCancelable(
    observable.subscribe(styleImageMissingCallback),
    onCancel
  )

  fun subscribeStyleImageRemoveUnused(
    styleImageRemoveUnusedCallback: StyleImageRemoveUnusedCallback,
    onCancel: (() -> Unit)? = null
  ): Cancelable = ExtendedCancelable(
    observable.subscribe(styleImageRemoveUnusedCallback),
    onCancel
  )

  fun subscribeRenderFrameStarted(
    renderFrameStartedCallback: RenderFrameStartedCallback,
    onCancel: (() -> Unit)? = null
  ): Cancelable = ExtendedCancelable(
    observable.subscribe(renderFrameStartedCallback),
    onCancel
  )

  internal fun subscribeRenderFrameFinished(
    renderFrameFinishedCallback: RenderFrameFinishedCallback,
    onCancel: (() -> Unit)? = null
  ): Cancelable = ExtendedCancelable(
    observable.subscribe(renderFrameFinishedCallback),
    onCancel
  )

  internal fun subscribeResourceRequest(
    resourceRequestCallback: ResourceRequestCallback,
    onCancel: (() -> Unit)? = null
  ): Cancelable = ExtendedCancelable(
    observable.subscribe(resourceRequestCallback),
    onCancel
  )

  @MapboxExperimental
  fun subscribeGenericEvent(
    eventName: String,
    genericEventCallback: GenericEventCallback,
    onCancel: (() -> Unit)? = null
  ): Cancelable = ExtendedCancelable(
    observable.subscribe(eventName, genericEventCallback),
    onCancel
  )

  // This method is needed to prevent receiving styleLoaded events with the old style
  // when new style is loaded. For example loading empty style and another style immediately after
  // that will notify STYLE_LOADED event for the first style after second style has already started
  // loading
  fun resubscribeStyleLoadListeners() {
    // we subscribe again but re-use extended cancelable/s that could be cached on user side
    // to make sure that calling extendedCancelable.cancel will actually hit the correct new originalCancelable.cancel
    styleLoadedCancelableSet.forEach {
      it.resubscribe(observable::subscribe)
    }
    styleDataLoadedCancelableSet.forEach {
      it.resubscribe(observable::subscribe)
    }
  }

  fun onDestroy() {
    // cancel all cancelable
    cancelableSet.forEach(Cancelable::cancel)
    styleDataLoadedCancelableSet.forEach(Cancelable::cancel)
    styleLoadedCancelableSet.forEach(Cancelable::cancel)

    onCameraChangeListeners.clear()

    onMapIdleListeners.clear()
    onMapLoadErrorListeners.clear()
    onMapLoadedListeners.clear()

    onRenderFrameFinishedListeners.clear()
    onRenderFrameStartedListeners.clear()

    onSourceAddedListeners.clear()
    onSourceDataLoadedListeners.clear()
    onSourceRemovedListeners.clear()

    onStyleLoadedListeners.clear()
    onStyleDataLoadedListeners.clear()
    onStyleImageMissingListeners.clear()
    onStyleImageUnusedListeners.clear()
  }
}
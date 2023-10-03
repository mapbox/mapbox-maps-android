package com.mapbox.maps

import androidx.annotation.RestrictTo
import androidx.annotation.UiThread
import androidx.annotation.VisibleForTesting
import com.mapbox.common.Cancelable
import com.mapbox.maps.extension.observable.*
import com.mapbox.maps.plugin.delegates.listeners.*
import java.util.Objects
import java.util.concurrent.CopyOnWriteArraySet

@Suppress("DeprecatedCallableAddReplaceWith", "DEPRECATION")
@UiThread
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class NativeObserver(
  private val observable: NativeMapImpl
) {
  /**
   * A [Cancelable] that will add itself to the given [cancelableSet] when created and remove
   * itself when [cancel]ed.
   *
   * @param originalCancelable its [Cancelable.cancel] will be called when [cancel] is executed.
   * @param onCancel function to be called after [cancel] is executed.
   * @param cancelableSet a [MutableSet] that will hold this cancelable while it is active.
   * @param listener a listener called [cancel] is executed.
   */
  private open inner class ExtendedCancelable(
    open val originalCancelable: Cancelable,
    val onCancel: (() -> Unit)? = null,
    private val cancelableSet: MutableSet<ExtendedCancelable> = this@NativeObserver.cancelableSet,
    val listener: Any? = null,
  ) : Cancelable {
    init {
      @Suppress("LeakingThis")
      cancelableSet.add(this)
    }

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
      if (listener != other.listener) return false

      return true
    }

    override fun hashCode(): Int =
      Objects.hash(originalCancelable, cancelableSet, onCancel, listener)
  }

  /**
   * An [ExtendedCancelable] that can resubscribe with [resubscriber].
   *
   * @param resubscriber lambda to produce new [Cancelable] when [resubscribe] is called.
   * @param originalCancelable its [Cancelable.cancel] will be called when [cancel] is executed. It will be updated when [resubscribe] is executed.
   * @param cancelableSet a [MutableSet] that will hold this cancelable while it is active.
   * @param onCancel function to be called after [cancel] is executed.
   * @param listener a listener called [cancel] is executed.
   */
  @Suppress("UNCHECKED_CAST")
  private inner class ResubscribeExtendedCancelable(
    private val resubscriber: () -> Cancelable,
    override var originalCancelable: Cancelable,
    cancelableSet: MutableSet<ResubscribeExtendedCancelable>,
    onCancel: (() -> Unit)? = null,
    listener: Any? = null,
  ) : ExtendedCancelable(
    originalCancelable = originalCancelable,
    cancelableSet = cancelableSet as MutableSet<ExtendedCancelable>,
    onCancel = onCancel,
    listener = listener,
  ) {
    fun resubscribe() {
      originalCancelable.cancel()
      originalCancelable = resubscriber()
    }

    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (javaClass != other?.javaClass) return false
      if (!super.equals(other)) return false

      other as ResubscribeExtendedCancelable

      return resubscriber == other.resubscriber
    }

    override fun hashCode(): Int = Objects.hash(super.hashCode(), resubscriber)
  }

  private val cancelableSet = CopyOnWriteArraySet<ExtendedCancelable>()
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  // ExtendedCancelable is private so we expose the set as generic Cancelable set
  internal val _cancelableSet: MutableSet<out Cancelable> = cancelableSet

  private val resubscribableSet = CopyOnWriteArraySet<ResubscribeExtendedCancelable>()
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  // ResubscribeExtendedCancelable is private so we expose the set as generic Cancelable set
  internal val _resubscribableSet: MutableSet<out Cancelable> = resubscribableSet

  private fun unsubscribeListener(listener: Any, cancelableSet: Set<ExtendedCancelable>) {
    cancelableSet
      .filter { it.listener == listener }
      .forEach { it.cancel() }
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnCameraChangeListener(onCameraChangeListener: OnCameraChangeListener) {
    subscribeCameraChanged(
      cameraChangedCallback = {
        onCameraChangeListener.onCameraChanged(it.toCameraChangedEventData())
      },
      onCameraChangeListener = onCameraChangeListener,
    )
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnCameraChangeListener(onCameraChangeListener: OnCameraChangeListener) {
    unsubscribeListener(onCameraChangeListener, cancelableSet)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnMapLoadedListener(onMapLoadedListener: OnMapLoadedListener) {
    subscribeMapLoaded(
      mapLoadedCallback = {
        onMapLoadedListener.onMapLoaded(it.toMapLoadedEventData())
      },
      mapLoadedListener = onMapLoadedListener,
    )
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnMapLoadedListener(onMapLoadedListener: OnMapLoadedListener) {
    unsubscribeListener(onMapLoadedListener, cancelableSet)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnMapIdleListener(onMapIdleListener: OnMapIdleListener) {
    subscribeMapIdle(
      mapIdleCallback = {
        onMapIdleListener.onMapIdle(it.toMapIdleEventData())
      },
      mapIdleListener = onMapIdleListener,
    )
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnMapIdleListener(onMapIdleListener: OnMapIdleListener) {
    unsubscribeListener(onMapIdleListener, cancelableSet)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnMapLoadErrorListener(onMapLoadErrorListener: OnMapLoadErrorListener) {
    subscribeMapLoadingError(
      mapLoadingErrorCallback = {
        onMapLoadErrorListener.onMapLoadError(it.toMapLoadingErrorEventData())
      },
      mapLoadingErrorListener = onMapLoadErrorListener,
    )
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnMapLoadErrorListener(onMapLoadErrorListener: OnMapLoadErrorListener) {
    unsubscribeListener(onMapLoadErrorListener, cancelableSet)
  }

  // Style events
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnStyleLoadedListener(onStyleLoadedListener: OnStyleLoadedListener) {
    subscribeStyleLoaded(
      styleLoadedCallback = {
        onStyleLoadedListener.onStyleLoaded(it.toStyleLoadedEventData())
      },
      styleLoadedListener = onStyleLoadedListener,
    )
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnStyleLoadedListener(onStyleLoadedListener: OnStyleLoadedListener) {
    unsubscribeListener(onStyleLoadedListener, resubscribableSet)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnStyleDataLoadedListener(onStyleDataLoadedListener: OnStyleDataLoadedListener) {
    subscribeStyleDataLoaded(
      styleDataLoadedCallback = {
        onStyleDataLoadedListener.onStyleDataLoaded(it.toStyleDataLoadedEventData())
      },
      styleDataLoadedListener = onStyleDataLoadedListener,
    )
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnStyleDataLoadedListener(onStyleDataLoadedListener: OnStyleDataLoadedListener) {
    unsubscribeListener(onStyleDataLoadedListener, resubscribableSet)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnSourceDataLoadedListener(onSourceDataLoadedListener: OnSourceDataLoadedListener) {
    subscribeSourceDataLoaded(
      sourceDataLoadedCallback = {
        onSourceDataLoadedListener.onSourceDataLoaded(it.toSourceDataLoadedEventData())
      },
      sourceDataLoadedListener = onSourceDataLoadedListener,
    )
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnSourceDataLoadedListener(onSourceDataLoadedListener: OnSourceDataLoadedListener) {
    unsubscribeListener(onSourceDataLoadedListener, cancelableSet)
  }

  // Source events
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnSourceAddedListener(onSourceAddedListener: OnSourceAddedListener) {
    subscribeSourceAdded(
      sourceAddedCallback = {
        onSourceAddedListener.onSourceAdded(it.toSourceAddedEventData())
      },
      sourceAddedListener = onSourceAddedListener,
    )
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnSourceAddedListener(onSourceAddedListener: OnSourceAddedListener) {
    unsubscribeListener(onSourceAddedListener, cancelableSet)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnSourceRemovedListener(onSourceRemovedListener: OnSourceRemovedListener) {
    subscribeSourceRemoved(
      sourceRemovedCallback = {
        onSourceRemovedListener.onSourceRemoved(it.toSourceRemovedEventData())
      },
      sourceRemovedListener = onSourceRemovedListener,
    )
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnSourceRemovedListener(onSourceRemovedListener: OnSourceRemovedListener) {
    unsubscribeListener(onSourceRemovedListener, cancelableSet)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnStyleImageMissingListener(onStyleImageMissingListener: OnStyleImageMissingListener) {
    subscribeStyleImageMissing(
      styleImageMissingCallback = {
        onStyleImageMissingListener.onStyleImageMissing(it.toStyleImageMissingEventData())
      },
      styleImageMissingListener = onStyleImageMissingListener,
    )
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnStyleImageMissingListener(onStyleImageMissingListener: OnStyleImageMissingListener) {
    unsubscribeListener(onStyleImageMissingListener, cancelableSet)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnStyleImageUnusedListener(onStyleImageUnusedListener: OnStyleImageUnusedListener) {
    subscribeStyleImageRemoveUnused(
      styleImageRemoveUnusedCallback = {
        onStyleImageUnusedListener.onStyleImageUnused(it.toStyleImageUnusedEventData())
      },
      styleImageUnusedListener = onStyleImageUnusedListener,
    )
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnStyleImageUnusedListener(onStyleImageUnusedListener: OnStyleImageUnusedListener) {
    unsubscribeListener(onStyleImageUnusedListener, cancelableSet)
  }

  // Render frame events
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnRenderFrameStartedListener(onRenderFrameStartedListener: OnRenderFrameStartedListener) {
    subscribeRenderFrameStarted(
      renderFrameStartedCallback = {
        onRenderFrameStartedListener.onRenderFrameStarted(it.toRenderFrameStartedEventData())
      },
      renderFrameStarted = onRenderFrameStartedListener,
    )
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnRenderFrameStartedListener(onRenderFrameStartedListener: OnRenderFrameStartedListener) {
    unsubscribeListener(onRenderFrameStartedListener, cancelableSet)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnRenderFrameFinishedListener(onRenderFrameFinishedListener: OnRenderFrameFinishedListener) {
    subscribeRenderFrameFinished(
      renderFrameFinishedCallback = {
        onRenderFrameFinishedListener.onRenderFrameFinished(it.toRenderFrameFinishedEventData())
      },
      renderFrameFinishedListener = onRenderFrameFinishedListener,
    )
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnRenderFrameFinishedListener(onRenderFrameFinishedListener: OnRenderFrameFinishedListener) {
    unsubscribeListener(onRenderFrameFinishedListener, cancelableSet)
  }

  // new subscribe* methods
  fun subscribeCameraChanged(
    cameraChangedCallback: CameraChangedCallback,
    onCancel: (() -> Unit)? = null,
    onCameraChangeListener: OnCameraChangeListener? = null,
  ): Cancelable = ExtendedCancelable(
    observable.subscribe(cameraChangedCallback),
    onCancel = onCancel,
    listener = onCameraChangeListener,
  )

  fun subscribeMapLoaded(
    mapLoadedCallback: MapLoadedCallback,
    onCancel: (() -> Unit)? = null,
    mapLoadedListener: OnMapLoadedListener? = null,
  ): Cancelable = ExtendedCancelable(
    observable.subscribe(mapLoadedCallback),
    onCancel = onCancel,
    listener = mapLoadedListener,
  )

  fun subscribeMapIdle(
    mapIdleCallback: MapIdleCallback,
    onCancel: (() -> Unit)? = null,
    mapIdleListener: OnMapIdleListener? = null,
  ): Cancelable = ExtendedCancelable(
    observable.subscribe(mapIdleCallback),
    onCancel = onCancel,
    listener = mapIdleListener,
  )

  fun subscribeMapLoadingError(
    mapLoadingErrorCallback: MapLoadingErrorCallback,
    onCancel: (() -> Unit)? = null,
    mapLoadingErrorListener: OnMapLoadErrorListener? = null,
  ): Cancelable = ExtendedCancelable(
    observable.subscribe(mapLoadingErrorCallback),
    onCancel = onCancel,
    listener = mapLoadingErrorListener,
  )

  fun subscribeStyleLoaded(
    styleLoadedCallback: StyleLoadedCallback,
    onCancel: (() -> Unit)? = null,
    styleLoadedListener: OnStyleLoadedListener? = null,
  ): Cancelable {
    val resubscriber = {
      observable.subscribe(styleLoadedCallback)
    }

    return ResubscribeExtendedCancelable(
      resubscriber = resubscriber,
      originalCancelable = resubscriber(),
      cancelableSet = resubscribableSet,
      onCancel = onCancel,
      listener = styleLoadedListener,
    )
  }

  fun subscribeStyleDataLoaded(
    styleDataLoadedCallback: StyleDataLoadedCallback,
    onCancel: (() -> Unit)? = null,
    styleDataLoadedListener: OnStyleDataLoadedListener? = null,
  ): Cancelable {
    val resubscriber = {
      observable.subscribe(styleDataLoadedCallback)
    }

    return ResubscribeExtendedCancelable(
      resubscriber = resubscriber,
      originalCancelable = resubscriber(),
      cancelableSet = resubscribableSet,
      onCancel = onCancel,
      listener = styleDataLoadedListener,
    )
  }

  fun subscribeSourceDataLoaded(
    sourceDataLoadedCallback: SourceDataLoadedCallback,
    onCancel: (() -> Unit)? = null,
    sourceDataLoadedListener: OnSourceDataLoadedListener? = null,
  ): Cancelable = ExtendedCancelable(
    observable.subscribe(sourceDataLoadedCallback),
    onCancel = onCancel,
    listener = sourceDataLoadedListener,
  )

  fun subscribeSourceAdded(
    sourceAddedCallback: SourceAddedCallback,
    onCancel: (() -> Unit)? = null,
    sourceAddedListener: OnSourceAddedListener? = null,
  ): Cancelable = ExtendedCancelable(
    observable.subscribe(sourceAddedCallback),
    onCancel = onCancel,
    listener = sourceAddedListener,
  )

  fun subscribeSourceRemoved(
    sourceRemovedCallback: SourceRemovedCallback,
    onCancel: (() -> Unit)? = null,
    sourceRemovedListener: OnSourceRemovedListener? = null,
  ): Cancelable = ExtendedCancelable(
    observable.subscribe(sourceRemovedCallback),
    onCancel = onCancel,
    listener = sourceRemovedListener,
  )

  fun subscribeStyleImageMissing(
    styleImageMissingCallback: StyleImageMissingCallback,
    onCancel: (() -> Unit)? = null,
    styleImageMissingListener: OnStyleImageMissingListener? = null,
  ): Cancelable = ExtendedCancelable(
    observable.subscribe(styleImageMissingCallback),
    onCancel = onCancel,
    listener = styleImageMissingListener,
  )

  fun subscribeStyleImageRemoveUnused(
    styleImageRemoveUnusedCallback: StyleImageRemoveUnusedCallback,
    onCancel: (() -> Unit)? = null,
    styleImageUnusedListener: OnStyleImageUnusedListener? = null,
  ): Cancelable = ExtendedCancelable(
    observable.subscribe(styleImageRemoveUnusedCallback),
    onCancel = onCancel,
    listener = styleImageUnusedListener,
  )

  fun subscribeRenderFrameStarted(
    renderFrameStartedCallback: RenderFrameStartedCallback,
    onCancel: (() -> Unit)? = null,
    renderFrameStarted: OnRenderFrameStartedListener? = null,
  ): Cancelable = ExtendedCancelable(
    observable.subscribe(renderFrameStartedCallback),
    onCancel = onCancel,
    listener = renderFrameStarted,
  )

  fun subscribeRenderFrameFinished(
    renderFrameFinishedCallback: RenderFrameFinishedCallback,
    onCancel: (() -> Unit)? = null,
    renderFrameFinishedListener: OnRenderFrameFinishedListener? = null,
  ): Cancelable = ExtendedCancelable(
    observable.subscribe(renderFrameFinishedCallback),
    onCancel = onCancel,
    listener = renderFrameFinishedListener,
  )

  fun subscribeResourceRequest(
    resourceRequestCallback: ResourceRequestCallback,
    onCancel: (() -> Unit)? = null,
  ): Cancelable = ExtendedCancelable(
    observable.subscribe(resourceRequestCallback),
    onCancel = onCancel,
  )

  @MapboxExperimental
  fun subscribeGenericEvent(
    eventName: String,
    genericEventCallback: GenericEventCallback,
    onCancel: (() -> Unit)? = null,
  ): Cancelable = ExtendedCancelable(
    observable.subscribe(eventName, genericEventCallback),
    onCancel = onCancel,
  )

  // This method is needed to prevent receiving styleLoaded events with the old style
  // when new style is loaded. For example loading empty style and another style immediately after
  // that will notify STYLE_LOADED event for the first style after second style has already started
  // loading
  fun resubscribeStyleLoadListeners() {
    resubscribableSet.forEach(ResubscribeExtendedCancelable::resubscribe)
  }

  fun onDestroy() {
    cancelableSet.forEach(Cancelable::cancel)
    resubscribableSet.forEach(Cancelable::cancel)
  }
}
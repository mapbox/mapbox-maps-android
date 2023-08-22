package com.mapbox.maps

import androidx.annotation.UiThread
import androidx.annotation.VisibleForTesting
import com.mapbox.common.Cancelable
import com.mapbox.maps.extension.observable.*
import com.mapbox.maps.plugin.delegates.listeners.*
import java.util.concurrent.CopyOnWriteArraySet

internal enum class MapEvent {
  CAMERA_CHANGED,

  // Map events
  MAP_IDLE,
  MAP_LOADING_ERROR,
  MAP_LOADED,

  // Style events
  STYLE_DATA_LOADED,
  STYLE_LOADED,
  STYLE_IMAGE_MISSING,
  STYLE_IMAGE_REMOVE_UNUSED,

  // Render frame events
  RENDER_FRAME_STARTED,
  RENDER_FRAME_FINISHED,

  // Source events
  SOURCE_ADDED,
  SOURCE_DATA_LOADED,
  SOURCE_REMOVED,

  // Resource request event
  RESOURCE_REQUEST,
  GENERIC_EVENT,
}

@UiThread
internal class NativeObserver(
  private val observable: NativeMapImpl
) {
  private inner class ExtendedStyleLoadedCancelable(
    val styleLoadedCallback: StyleLoadedCallback,
    var originalCancelable: Cancelable
  ) : Cancelable {
    override fun cancel() {
      styleLoadedCallbackSet.remove(this)
      originalCancelable.cancel()
    }
  }

  private inner class ExtendedDataStyleLoadedCancelable(
    val styleDataLoadedCallback: StyleDataLoadedCallback,
    var originalCancelable: Cancelable
  ) : Cancelable {
    override fun cancel() {
      styleDataLoadedCallbackSet.remove(this)
      originalCancelable.cancel()
    }
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

  /**
   * We have to control those types of callbacks as they participate in [resubscribeStyleLoadListeners].
   */
  private val styleLoadedCallbackSet = CopyOnWriteArraySet<ExtendedStyleLoadedCancelable>()
  private val styleDataLoadedCallbackSet = CopyOnWriteArraySet<ExtendedDataStyleLoadedCancelable>()

  internal var cancelableEventsMap = LinkedHashMap<MapEvent, MutableList<Cancelable>>()

  //
  // Subscribe to Add / Remove deprecated methods.
  //
  private fun <T> subscribeToMapEvent(event: MapEvent, callback: T, eventName: String = "") {
    when (event) {
      MapEvent.CAMERA_CHANGED -> {
        val cameraChangedCallback = CameraChangedCallback {
          (callback as OnCameraChangeListener).onCameraChanged(it.toCameraChangedEventData())
        }
        cancelableEventsMap.getOrPut(MapEvent.CAMERA_CHANGED) {
          mutableListOf()
        }.add(observable.subscribe(cameraChangedCallback))
      }
      MapEvent.MAP_IDLE -> {
        val mapIdleCallback = MapIdleCallback {
          (callback as OnMapIdleListener).onMapIdle(it.toMapIdleEventData())
        }
        cancelableEventsMap.getOrPut(MapEvent.MAP_IDLE) {
          mutableListOf()
        }.add(observable.subscribe(mapIdleCallback))
      }
      MapEvent.MAP_LOADING_ERROR -> {
        val mapLoadingErrorCallback = MapLoadingErrorCallback {
          (callback as OnMapLoadErrorListener).onMapLoadError(it.toMapLoadingErrorEventData())
        }
        cancelableEventsMap.getOrPut(MapEvent.MAP_LOADING_ERROR) {
          mutableListOf()
        }.add(observable.subscribe(mapLoadingErrorCallback))
      }
      MapEvent.MAP_LOADED -> {
        val mapLoadedCallback = MapLoadedCallback {
          (callback as OnMapLoadedListener).onMapLoaded(it.toMapLoadedEventData())
        }
        cancelableEventsMap.getOrPut(MapEvent.MAP_LOADED) {
          mutableListOf()
        }.add(observable.subscribe(mapLoadedCallback))
      }
      MapEvent.STYLE_DATA_LOADED -> {
        val styleDataLoadedCallback = StyleDataLoadedCallback {
          (callback as OnStyleDataLoadedListener).onStyleDataLoaded(it.toStyleDataLoadedEventData())
        }
        cancelableEventsMap.getOrPut(MapEvent.STYLE_DATA_LOADED) {
          mutableListOf()
        }.add(observable.subscribe(styleDataLoadedCallback))
      }
      MapEvent.STYLE_LOADED -> {
        val styleLoadedCallback = StyleLoadedCallback {
          (callback as OnStyleLoadedListener).onStyleLoaded(it.toStyleLoadedEventData())
        }
        cancelableEventsMap.getOrPut(MapEvent.STYLE_LOADED) {
          mutableListOf()
        }.add(observable.subscribe(styleLoadedCallback))
      }
      MapEvent.STYLE_IMAGE_MISSING -> {
        val styleImageMissingCallback = StyleImageMissingCallback {
          (callback as OnStyleImageMissingListener).onStyleImageMissing(it.getStyleImageMissingEventData())
        }
        cancelableEventsMap.getOrPut(MapEvent.STYLE_IMAGE_MISSING) {
          mutableListOf()
        }.add(observable.subscribe(styleImageMissingCallback))
      }
      MapEvent.STYLE_IMAGE_REMOVE_UNUSED -> {
        val styleImageRemoveUnusedCallback = StyleImageRemoveUnusedCallback {
          (callback as OnStyleImageUnusedListener).onStyleImageUnused(it.toStyleImageUnusedEventData())
        }
        cancelableEventsMap.getOrPut(MapEvent.STYLE_IMAGE_REMOVE_UNUSED) {
          mutableListOf()
        }.add(observable.subscribe(styleImageRemoveUnusedCallback))
      }
      MapEvent.RENDER_FRAME_FINISHED -> {
        val renderFrameFinishedCallback = RenderFrameFinishedCallback {
          (callback as OnRenderFrameFinishedListener).onRenderFrameFinished(it.toRenderFrameFinishedEventData())
        }
        cancelableEventsMap.getOrPut(MapEvent.RENDER_FRAME_FINISHED) {
          mutableListOf()
        }.add(observable.subscribe(renderFrameFinishedCallback))
      }
      MapEvent.RENDER_FRAME_STARTED -> {
        val renderFrameStartedCallback = RenderFrameStartedCallback {
          (callback as OnRenderFrameStartedListener).onRenderFrameStarted(it.toRenderFrameStartedEventData())
        }
        cancelableEventsMap.getOrPut(MapEvent.RENDER_FRAME_STARTED) {
          mutableListOf()
        }.add(observable.subscribe(renderFrameStartedCallback))
      }
      MapEvent.SOURCE_ADDED -> {
        val sourceAddedCallback = SourceAddedCallback {
          (callback as OnSourceAddedListener).onSourceAdded(it.toSourceAddedEventData())
        }
        cancelableEventsMap.getOrPut(MapEvent.SOURCE_ADDED) {
          mutableListOf()
        }.add(observable.subscribe(sourceAddedCallback))
      }
      MapEvent.SOURCE_DATA_LOADED -> {
        val sourceDataLoadedCallback = SourceDataLoadedCallback {
          (callback as OnSourceDataLoadedListener).onSourceDataLoaded(it.toSourceDataLoadedEventData())
        }
        cancelableEventsMap.getOrPut(MapEvent.SOURCE_DATA_LOADED) {
          mutableListOf()
        }.add(observable.subscribe(sourceDataLoadedCallback))
      }
      MapEvent.SOURCE_REMOVED -> {
        val sourceRemovedCallback = SourceRemovedCallback {
          (callback as OnSourceRemovedListener).onSourceRemoved(it.toSourceRemovedEventData())
        }
        cancelableEventsMap.getOrPut(MapEvent.SOURCE_REMOVED) {
          mutableListOf()
        }.add(observable.subscribe(sourceRemovedCallback))
      }
      MapEvent.RESOURCE_REQUEST -> {
        cancelableEventsMap.getOrPut(MapEvent.RESOURCE_REQUEST) {
          mutableListOf()
        }.add(observable.subscribe(callback as ResourceRequestCallback))
      }
      MapEvent.GENERIC_EVENT -> {
        // not applicable for add/remove v10 methods.
      }
    }
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal fun cancelAllAndRemove(event: MapEvent) {
    cancelableEventsMap[event]?.forEach { it.cancel() }
    cancelableEventsMap.remove(event)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnCameraChangeListener(onCameraChangeListener: OnCameraChangeListener) {
    subscribeToMapEvent(MapEvent.CAMERA_CHANGED, onCameraChangeListener)
    onCameraChangeListeners.add(onCameraChangeListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnCameraChangeListener(onCameraChangeListener: OnCameraChangeListener) {
    onCameraChangeListeners.remove(onCameraChangeListener)
    if (onCameraChangeListeners.isEmpty()) {
      cancelAllAndRemove(MapEvent.CAMERA_CHANGED)
    }
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnMapIdleListener(onMapIdleListener: OnMapIdleListener) {
    subscribeToMapEvent(MapEvent.MAP_IDLE, onMapIdleListener)
    onMapIdleListeners.add(onMapIdleListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnMapIdleListener(onMapIdleListener: OnMapIdleListener) {
    onMapIdleListeners.remove(onMapIdleListener)
    if (onMapIdleListeners.isEmpty()) {
      cancelAllAndRemove(MapEvent.MAP_IDLE)
    }
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnMapLoadErrorListener(onMapLoadErrorListener: OnMapLoadErrorListener) {
    subscribeToMapEvent(MapEvent.MAP_LOADING_ERROR, onMapLoadErrorListener)
    onMapLoadErrorListeners.add(onMapLoadErrorListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnMapLoadErrorListener(onMapLoadErrorListener: OnMapLoadErrorListener) {
    onMapLoadErrorListeners.remove(onMapLoadErrorListener)
    if (onMapLoadErrorListeners.isEmpty()) {
      cancelAllAndRemove(MapEvent.MAP_LOADING_ERROR)
    }
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnMapLoadedListener(onMapLoadedListener: OnMapLoadedListener) {
    subscribeToMapEvent(MapEvent.MAP_LOADED, onMapLoadedListener)
    onMapLoadedListeners.add(onMapLoadedListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnMapLoadedListener(onMapLoadedListener: OnMapLoadedListener) {
    onMapLoadedListeners.remove(onMapLoadedListener)
    if (onMapLoadedListeners.isEmpty()) {
      cancelAllAndRemove(MapEvent.MAP_LOADED)
    }
  }

  // Render frame events
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnRenderFrameFinishedListener(onRenderFrameFinishedListener: OnRenderFrameFinishedListener) {
    subscribeToMapEvent(MapEvent.RENDER_FRAME_FINISHED, onRenderFrameFinishedListener)
    onRenderFrameFinishedListeners.add(onRenderFrameFinishedListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnRenderFrameFinishedListener(onRenderFrameFinishedListener: OnRenderFrameFinishedListener) {
    onRenderFrameFinishedListeners.remove(onRenderFrameFinishedListener)
    if (onRenderFrameFinishedListeners.isEmpty()) {
      cancelAllAndRemove(MapEvent.RENDER_FRAME_FINISHED)
    }
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnRenderFrameStartedListener(onRenderFrameStartedListener: OnRenderFrameStartedListener) {
    subscribeToMapEvent(MapEvent.RENDER_FRAME_STARTED, onRenderFrameStartedListener)
    onRenderFrameStartedListeners.add(onRenderFrameStartedListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnRenderFrameStartedListener(onRenderFrameStartedListener: OnRenderFrameStartedListener) {
    onRenderFrameStartedListeners.remove(onRenderFrameStartedListener)
    if (onRenderFrameStartedListeners.isEmpty()) {
      cancelAllAndRemove(MapEvent.RENDER_FRAME_STARTED)
    }
  }

  // Source events
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnSourceAddedListener(onSourceAddedListener: OnSourceAddedListener) {
    subscribeToMapEvent(MapEvent.SOURCE_ADDED, onSourceAddedListener)
    onSourceAddedListeners.add(onSourceAddedListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnSourceAddedListener(onSourceAddedListener: OnSourceAddedListener) {
    onSourceAddedListeners.remove(onSourceAddedListener)
    if (onSourceAddedListeners.isEmpty()) {
      cancelAllAndRemove(MapEvent.SOURCE_ADDED)
    }
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnSourceDataLoadedListener(onSourceDataLoadedListener: OnSourceDataLoadedListener) {
    subscribeToMapEvent(MapEvent.SOURCE_DATA_LOADED, onSourceDataLoadedListener)
    onSourceDataLoadedListeners.add(onSourceDataLoadedListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnSourceDataLoadedListener(onSourceDataLoadedListener: OnSourceDataLoadedListener) {
    onSourceDataLoadedListeners.remove(onSourceDataLoadedListener)
    if (onSourceDataLoadedListeners.isEmpty()) {
      cancelAllAndRemove(MapEvent.SOURCE_DATA_LOADED)
    }
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnSourceRemovedListener(onSourceRemovedListener: OnSourceRemovedListener) {
    subscribeToMapEvent(MapEvent.SOURCE_REMOVED, onSourceRemovedListener)
    onSourceRemovedListeners.add(onSourceRemovedListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnSourceRemovedListener(onSourceRemovedListener: OnSourceRemovedListener) {
    onSourceRemovedListeners.remove(onSourceRemovedListener)
    if (onSourceRemovedListeners.isEmpty()) {
      cancelAllAndRemove(MapEvent.SOURCE_REMOVED)
    }
  }

  // Style events
  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnStyleLoadedListener(onStyleLoadedListener: OnStyleLoadedListener) {
    subscribeToMapEvent(MapEvent.STYLE_LOADED, onStyleLoadedListener)
    onStyleLoadedListeners.add(onStyleLoadedListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnStyleLoadedListener(onStyleLoadedListener: OnStyleLoadedListener) {
    onStyleLoadedListeners.remove(onStyleLoadedListener)
    if (onStyleLoadedListeners.isEmpty()) {
      cancelAllAndRemove(MapEvent.STYLE_LOADED)
    }
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnStyleDataLoadedListener(onStyleDataLoadedListener: OnStyleDataLoadedListener) {
    subscribeToMapEvent(MapEvent.STYLE_DATA_LOADED, onStyleDataLoadedListener)
    onStyleDataLoadedListeners.add(onStyleDataLoadedListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnStyleDataLoadedListener(onStyleDataLoadedListener: OnStyleDataLoadedListener) {
    onStyleDataLoadedListeners.remove(onStyleDataLoadedListener)
    if (onStyleDataLoadedListeners.isEmpty()) {
      cancelAllAndRemove(MapEvent.STYLE_DATA_LOADED)
    }
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnStyleImageMissingListener(onStyleImageMissingListener: OnStyleImageMissingListener) {
    subscribeToMapEvent(MapEvent.STYLE_IMAGE_MISSING, onStyleImageMissingListener)
    onStyleImageMissingListeners.add(onStyleImageMissingListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnStyleImageMissingListener(onStyleImageMissingListener: OnStyleImageMissingListener) {
    onStyleImageMissingListeners.remove(onStyleImageMissingListener)
    if (onStyleImageMissingListeners.isEmpty()) {
      cancelAllAndRemove(MapEvent.STYLE_IMAGE_MISSING)
    }
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun addOnStyleImageUnusedListener(onStyleImageUnusedListener: OnStyleImageUnusedListener) {
    subscribeToMapEvent(MapEvent.STYLE_IMAGE_REMOVE_UNUSED, onStyleImageUnusedListener)
    onStyleImageUnusedListeners.add(onStyleImageUnusedListener)
  }

  @Deprecated(
    message = "This method is deprecated, and will be removed in next major release.",
    level = DeprecationLevel.WARNING
  )
  fun removeOnStyleImageUnusedListener(onStyleImageUnusedListener: OnStyleImageUnusedListener) {
    onStyleImageUnusedListeners.remove(onStyleImageUnusedListener)
    if (onStyleImageUnusedListeners.isEmpty()) {
      cancelAllAndRemove(MapEvent.STYLE_IMAGE_REMOVE_UNUSED)
    }
  }

  /**
   * New subscribe methods supported by native.
   */
  fun subscribeCameraChanged(cameraChangedCallback: CameraChangedCallback): Cancelable {
    return observable.subscribe(cameraChangedCallback).also {
      cancelableEventsMap.getOrPut(MapEvent.CAMERA_CHANGED) {
        mutableListOf()
      }.add(it)
    }
  }

  fun subscribeMapLoaded(mapLoadedCallback: MapLoadedCallback): Cancelable {
    return observable.subscribe(mapLoadedCallback).also {
      cancelableEventsMap.getOrPut(MapEvent.MAP_LOADED) {
        mutableListOf()
      }.add(it)
    }
  }

  fun subscribeMapIdle(mapIdleCallback: MapIdleCallback): Cancelable {
    return observable.subscribe(mapIdleCallback).also {
      cancelableEventsMap.getOrPut(MapEvent.MAP_IDLE) {
        mutableListOf()
      }.add(it)
    }
  }

  fun subscribeMapLoadingError(mapLoadingErrorCallback: MapLoadingErrorCallback): Cancelable {
    return observable.subscribe(mapLoadingErrorCallback).also {
      cancelableEventsMap.getOrPut(MapEvent.MAP_LOADING_ERROR) {
        mutableListOf()
      }.add(it)
    }
  }

  fun subscribeStyleLoaded(
    styleLoadedCallback: StyleLoadedCallback
  ): Cancelable {
    val originalCancelable = observable.subscribe(styleLoadedCallback)
    // extendedCancelable is needed to track when user calls cancel so we return it to the user
    val extendedCancelable = ExtendedStyleLoadedCancelable(styleLoadedCallback, originalCancelable)
    // we store originalCancelable in the map so that `styleLoadedCallback` is not removed
    // when calling `resubscribeStyleLoadListeners`
    cancelableEventsMap.getOrPut(MapEvent.STYLE_LOADED) {
      mutableListOf()
    }.add(originalCancelable)
    styleLoadedCallbackSet.add(extendedCancelable)
    return extendedCancelable
  }

  fun subscribeStyleDataLoaded(styleDataLoadedCallback: StyleDataLoadedCallback): Cancelable {
    val originalCancelable = observable.subscribe(styleDataLoadedCallback)
    // extendedCancelable is needed to track when user calls cancel so we return it to the user
    val extendedCancelable = ExtendedDataStyleLoadedCancelable(styleDataLoadedCallback, originalCancelable)
    // we store originalCancelable in the map so that `styleDataLoadedCallbackSet` is not removed
    // when calling `resubscribeStyleLoadListeners`
    cancelableEventsMap.getOrPut(MapEvent.STYLE_DATA_LOADED) {
      mutableListOf()
    }.add(originalCancelable)
    styleDataLoadedCallbackSet.add(extendedCancelable)
    return extendedCancelable
  }

  fun subscribeSourceDataLoaded(sourceDataLoadedCallback: SourceDataLoadedCallback): Cancelable {
    return observable.subscribe(sourceDataLoadedCallback).also {
      cancelableEventsMap.getOrPut(MapEvent.SOURCE_DATA_LOADED) {
        mutableListOf()
      }.add(it)
    }
  }

  fun subscribeSourceAdded(sourceAddedCallback: SourceAddedCallback): Cancelable {
    return observable.subscribe(sourceAddedCallback).also {
      cancelableEventsMap.getOrPut(MapEvent.SOURCE_ADDED) {
        mutableListOf()
      }.add(it)
    }
  }

  fun subscribeSourceRemoved(sourceRemovedCallback: SourceRemovedCallback): Cancelable {
    return observable.subscribe(sourceRemovedCallback).also {
      cancelableEventsMap.getOrPut(MapEvent.SOURCE_REMOVED) {
        mutableListOf()
      }.add(it)
    }
  }

  fun subscribeStyleImageMissing(styleImageMissingCallback: StyleImageMissingCallback): Cancelable {
    return observable.subscribe(styleImageMissingCallback).also {
      cancelableEventsMap.getOrPut(MapEvent.STYLE_IMAGE_MISSING) {
        mutableListOf()
      }.add(it)
    }
  }

  fun subscribeStyleImageRemoveUnused(styleImageRemoveUnusedCallback: StyleImageRemoveUnusedCallback): Cancelable {
    return observable.subscribe(styleImageRemoveUnusedCallback).also {
      cancelableEventsMap.getOrPut(MapEvent.STYLE_IMAGE_REMOVE_UNUSED) {
        mutableListOf()
      }.add(it)
    }
  }

  fun subscribeRenderFrameStarted(renderFrameStartedCallback: RenderFrameStartedCallback): Cancelable {
    return observable.subscribe(renderFrameStartedCallback).also {
      cancelableEventsMap.getOrPut(MapEvent.RENDER_FRAME_STARTED) {
        mutableListOf()
      }.add(it)
    }
  }

  fun subscribeRenderFrameFinished(renderFrameFinishedCallback: RenderFrameFinishedCallback): Cancelable {
    return observable.subscribe(renderFrameFinishedCallback).also {
      cancelableEventsMap.getOrPut(MapEvent.RENDER_FRAME_FINISHED) {
        mutableListOf()
      }.add(it)
    }
  }

  fun subscribeResourceRequest(resourceRequestCallback: ResourceRequestCallback): Cancelable {
    return observable.subscribe(resourceRequestCallback).also {
      cancelableEventsMap.getOrPut(MapEvent.RESOURCE_REQUEST) {
        mutableListOf()
      }.add(it)
    }
  }

  @MapboxExperimental
  fun subscribeGenericEvent(
    eventName: String,
    genericEventCallback: GenericEventCallback
  ): Cancelable {
    return observable.subscribe(eventName, genericEventCallback).also {
      cancelableEventsMap.getOrPut(MapEvent.GENERIC_EVENT) {
        mutableListOf()
      }.add(it)
    }
  }

  // This method is needed to prevent receiving styleLoaded events with the old style
  // when new style is loaded. For example loading empty style and another style immediately after
  // that will notify STYLE_LOADED event for the first style after second style has already started
  // loading
  fun resubscribeStyleLoadListeners() {
    cancelAllAndRemove(MapEvent.STYLE_LOADED)
    cancelAllAndRemove(MapEvent.STYLE_DATA_LOADED)
    // we subscribe again but re-use extended cancelable/s that could be cached on user side
    // to make sure that calling extendedCancelable.cancel will actually hit the correct new originalCancelable.cancel
    styleLoadedCallbackSet.forEach {
      val originalCancelable = observable.subscribe(it.styleLoadedCallback)
      it.originalCancelable = originalCancelable
      // we store originalCancelable in the map so that `styleLoadedCallback` is not removed
      // when calling `resubscribeStyleLoadListeners`
      cancelableEventsMap.getOrPut(MapEvent.STYLE_LOADED) {
        mutableListOf()
      }.add(originalCancelable)
    }
    styleDataLoadedCallbackSet.forEach {
      val originalCancelable = observable.subscribe(it.styleDataLoadedCallback)
      it.originalCancelable = originalCancelable
      // we store originalCancelable in the map so that `styleLoadedCallback` is not removed
      // when calling `resubscribeStyleLoadListeners`
      cancelableEventsMap.getOrPut(MapEvent.STYLE_DATA_LOADED) {
        mutableListOf()
      }.add(originalCancelable)
    }
  }

  fun onDestroy() {
    // cancel all cancelable
    cancelableEventsMap.values.flatten().forEach { it.cancel() }

    cancelableEventsMap.clear()
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

    styleLoadedCallbackSet.clear()
    styleDataLoadedCallbackSet.clear()
  }
}
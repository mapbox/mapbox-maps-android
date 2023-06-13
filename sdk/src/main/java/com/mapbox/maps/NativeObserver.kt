package com.mapbox.maps

import androidx.annotation.UiThread
import androidx.annotation.VisibleForTesting
import com.mapbox.common.Cancelable
import java.util.concurrent.CopyOnWriteArraySet

@VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
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
  val onCameraChangeListeners = CopyOnWriteArraySet<CameraChangedCallback>()

  val onMapIdleListeners = CopyOnWriteArraySet<MapIdleCallback>()
  val onMapLoadErrorListeners = CopyOnWriteArraySet<MapLoadingErrorCallback>()
  val onMapLoadedListeners = CopyOnWriteArraySet<MapLoadedCallback>()

  val onStyleDataLoadedListeners = CopyOnWriteArraySet<StyleDataLoadedCallback>()
  val onStyleLoadedListeners = CopyOnWriteArraySet<StyleLoadedCallback>()
  val onStyleImageMissingListeners = CopyOnWriteArraySet<StyleImageMissingCallback>()
  val onStyleImageUnusedListeners = CopyOnWriteArraySet<StyleImageRemoveUnusedCallback>()

  val onRenderFrameFinishedListeners = CopyOnWriteArraySet<RenderFrameFinishedCallback>()
  val onRenderFrameStartedListeners = CopyOnWriteArraySet<RenderFrameStartedCallback>()

  val onSourceAddedListeners = CopyOnWriteArraySet<SourceAddedCallback>()
  val onSourceRemovedListeners = CopyOnWriteArraySet<SourceRemovedCallback>()
  val onSourceDataLoadedListeners = CopyOnWriteArraySet<SourceDataLoadedCallback>()

  val onResourceRequestListeners = CopyOnWriteArraySet<ResourceRequestCallback>()
  val onGenericEventListeners = CopyOnWriteArraySet<GenericEventCallback>()

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var cancelableEventsMap = HashMap<MapEvent, MutableList<Cancelable>>()

  //
  // Add / Remove
  //
  private fun <T> subscribeToMapEvent(event: MapEvent, callback: T, eventName: String = "") {
    when (event) {
      MapEvent.CAMERA_CHANGED -> {
        cancelableEventsMap.getOrPut(MapEvent.CAMERA_CHANGED) {
          mutableListOf()
        }.add(observable.subscribe(callback as CameraChangedCallback))
      }
      MapEvent.MAP_IDLE -> {
        cancelableEventsMap.getOrPut(MapEvent.MAP_IDLE) {
          mutableListOf()
        }.add(observable.subscribe(callback as MapIdleCallback))
      }
      MapEvent.MAP_LOADING_ERROR -> {
        cancelableEventsMap.getOrPut(MapEvent.MAP_LOADING_ERROR) {
          mutableListOf()
        }.add(observable.subscribe(callback as MapLoadingErrorCallback))
      }
      MapEvent.MAP_LOADED -> {
        cancelableEventsMap.getOrPut(MapEvent.MAP_LOADED) {
          mutableListOf()
        }.add(observable.subscribe(callback as MapLoadedCallback))
      }
      MapEvent.STYLE_DATA_LOADED -> {
        cancelableEventsMap.getOrPut(MapEvent.STYLE_DATA_LOADED) {
          mutableListOf()
        }.add(observable.subscribe(callback as StyleDataLoadedCallback))
      }
      MapEvent.STYLE_LOADED -> {
        cancelableEventsMap.getOrPut(MapEvent.STYLE_LOADED) {
          mutableListOf()
        }.add(observable.subscribe(callback as StyleLoadedCallback))
      }
      MapEvent.STYLE_IMAGE_MISSING -> {
        cancelableEventsMap.getOrPut(MapEvent.STYLE_IMAGE_MISSING) {
          mutableListOf()
        }.add(observable.subscribe(callback as StyleImageMissingCallback))
      }
      MapEvent.STYLE_IMAGE_REMOVE_UNUSED -> {
        cancelableEventsMap.getOrPut(MapEvent.STYLE_IMAGE_REMOVE_UNUSED) {
          mutableListOf()
        }.add(observable.subscribe(callback as StyleImageRemoveUnusedCallback))
      }

      MapEvent.RENDER_FRAME_FINISHED -> {
        cancelableEventsMap.getOrPut(MapEvent.RENDER_FRAME_FINISHED) {
          mutableListOf()
        }.add(observable.subscribe(callback as RenderFrameFinishedCallback))
      }
      MapEvent.RENDER_FRAME_STARTED -> {
        cancelableEventsMap.getOrPut(MapEvent.RENDER_FRAME_STARTED) {
          mutableListOf()
        }.add(observable.subscribe(callback as RenderFrameStartedCallback))
      }

      MapEvent.SOURCE_ADDED -> {
        cancelableEventsMap.getOrPut(MapEvent.SOURCE_ADDED) {
          mutableListOf()
        }.add(observable.subscribe(callback as SourceAddedCallback))
      }
      MapEvent.SOURCE_DATA_LOADED -> {
        cancelableEventsMap.getOrPut(MapEvent.SOURCE_DATA_LOADED) {
          mutableListOf()
        }.add(observable.subscribe(callback as SourceDataLoadedCallback))
      }
      MapEvent.SOURCE_REMOVED -> {
        cancelableEventsMap.getOrPut(MapEvent.SOURCE_REMOVED) {
          mutableListOf()
        }.add(observable.subscribe(callback as SourceRemovedCallback))
      }

      MapEvent.RESOURCE_REQUEST -> {
        cancelableEventsMap.getOrPut(MapEvent.RESOURCE_REQUEST) {
          mutableListOf()
        }.add(observable.subscribe(callback as ResourceRequestCallback))
      }
      MapEvent.GENERIC_EVENT -> {
        cancelableEventsMap.getOrPut(MapEvent.GENERIC_EVENT) {
          mutableListOf()
        }.add(observable.subscribe(eventName, callback as GenericEventCallback))
      }
    }
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal fun unsubscribeUnusedEvent(event: MapEvent) {
    cancelableEventsMap[event]?.forEach { it.cancel() }
    cancelableEventsMap.remove(event)
  }

  fun addOnCameraChangeListener(cameraChangedCallback: CameraChangedCallback) {
    subscribeToMapEvent(MapEvent.CAMERA_CHANGED, cameraChangedCallback)
    onCameraChangeListeners.add(cameraChangedCallback)
  }

  fun removeOnCameraChangeListener(cameraChangedCallback: CameraChangedCallback) {
    onCameraChangeListeners.remove(cameraChangedCallback)
    if (onCameraChangeListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvent.CAMERA_CHANGED)
    }
  }

  // Map events
  fun addOnMapIdleListener(mapIdleCallback: MapIdleCallback) {
    subscribeToMapEvent(MapEvent.MAP_IDLE, mapIdleCallback)
    onMapIdleListeners.add(mapIdleCallback)
  }

  fun removeOnMapIdleListener(mapIdleCallback: MapIdleCallback) {
    onMapIdleListeners.remove(mapIdleCallback)
    if (onMapIdleListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvent.MAP_IDLE)
    }
  }

  fun addOnMapLoadErrorListener(mapLoadingErrorCallback: MapLoadingErrorCallback) {
    subscribeToMapEvent(MapEvent.MAP_LOADING_ERROR, mapLoadingErrorCallback)
    onMapLoadErrorListeners.add(mapLoadingErrorCallback)
  }

  fun removeOnMapLoadErrorListener(mapLoadingErrorCallback: MapLoadingErrorCallback) {
    onMapLoadErrorListeners.remove(mapLoadingErrorCallback)
    if (onMapLoadErrorListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvent.MAP_LOADING_ERROR)
    }
  }

  fun addOnMapLoadedListener(mapLoadedCallback: MapLoadedCallback) {
    subscribeToMapEvent(MapEvent.MAP_LOADED, mapLoadedCallback)
    onMapLoadedListeners.add(mapLoadedCallback)
  }

  fun removeOnMapLoadedListener(mapLoadedCallback: MapLoadedCallback) {
    onMapLoadedListeners.remove(mapLoadedCallback)
    if (onMapLoadedListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvent.MAP_LOADED)
    }
  }

  // Render frame events
  fun addOnRenderFrameFinishedListener(renderFrameFinishedCallback: RenderFrameFinishedCallback) {
    subscribeToMapEvent(MapEvent.RENDER_FRAME_FINISHED, renderFrameFinishedCallback)
    onRenderFrameFinishedListeners.add(renderFrameFinishedCallback)
  }

  fun removeOnRenderFrameFinishedListener(renderFrameFinishedCallback: RenderFrameFinishedCallback) {
    onRenderFrameFinishedListeners.remove(renderFrameFinishedCallback)
    if (onRenderFrameFinishedListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvent.RENDER_FRAME_FINISHED)
    }
  }

  fun addOnRenderFrameStartedListener(renderFrameStartedCallback: RenderFrameStartedCallback) {
    subscribeToMapEvent(MapEvent.RENDER_FRAME_STARTED, renderFrameStartedCallback)
    onRenderFrameStartedListeners.add(renderFrameStartedCallback)
  }

  fun removeOnRenderFrameStartedListener(renderFrameStartedCallback: RenderFrameStartedCallback) {
    onRenderFrameStartedListeners.remove(renderFrameStartedCallback)
    if (onRenderFrameStartedListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvent.RENDER_FRAME_STARTED)
    }
  }

  // Source events
  fun addOnSourceAddedListener(sourceAddedCallback: SourceAddedCallback) {
    subscribeToMapEvent(MapEvent.SOURCE_ADDED, sourceAddedCallback)
    onSourceAddedListeners.add(sourceAddedCallback)
  }

  fun removeOnSourceAddedListener(sourceAddedCallback: SourceAddedCallback) {
    onSourceAddedListeners.remove(sourceAddedCallback)
    if (onSourceAddedListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvent.SOURCE_ADDED)
    }
  }

  fun addOnSourceDataLoadedListener(sourceDataLoadedCallback: SourceDataLoadedCallback) {
    subscribeToMapEvent(MapEvent.SOURCE_DATA_LOADED, sourceDataLoadedCallback)
    onSourceDataLoadedListeners.add(sourceDataLoadedCallback)
  }

  fun removeOnSourceDataLoadedListener(sourceDataLoadedCallback: SourceDataLoadedCallback) {
    onSourceDataLoadedListeners.remove(sourceDataLoadedCallback)
    if (onSourceDataLoadedListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvent.SOURCE_DATA_LOADED)
    }
  }

  fun addOnSourceRemovedListener(sourceRemovedCallback: SourceRemovedCallback) {
    subscribeToMapEvent(MapEvent.SOURCE_REMOVED, sourceRemovedCallback)
    onSourceRemovedListeners.add(sourceRemovedCallback)
  }

  fun removeOnSourceRemovedListener(sourceRemovedCallback: SourceRemovedCallback) {
    onSourceRemovedListeners.remove(sourceRemovedCallback)
    if (onSourceRemovedListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvent.SOURCE_REMOVED)
    }
  }

  // Style events
  fun addOnStyleLoadedListener(styleLoadedCallback: StyleLoadedCallback) {
    subscribeToMapEvent(MapEvent.STYLE_LOADED, styleLoadedCallback)
    onStyleLoadedListeners.add(styleLoadedCallback)
  }

  fun removeOnStyleLoadedListener(styleLoadedCallback: StyleLoadedCallback) {
    onStyleLoadedListeners.remove(styleLoadedCallback)
    if (onStyleLoadedListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvent.STYLE_LOADED)
    }
  }

  // This method is needed to prevent receiving styleLoaded events with the old style
  // when new style is loaded. For example loading empty style and another style immediately after
  // that will notify STYLE_LOADED event for the first style after second style has already started
  // loading
  fun resubscribeStyleLoadListeners(
    styleLoadedCallback: StyleLoadedCallback,
    styleDataLoadedCallback: StyleDataLoadedCallback
  ) {
    unsubscribeUnusedEvent(MapEvent.STYLE_LOADED)
    unsubscribeUnusedEvent(MapEvent.STYLE_DATA_LOADED)
    subscribeToMapEvent(MapEvent.STYLE_LOADED, styleLoadedCallback)
    subscribeToMapEvent(MapEvent.STYLE_DATA_LOADED, styleDataLoadedCallback)
  }

  fun addOnStyleDataLoadedListener(styleDataLoadedCallback: StyleDataLoadedCallback) {
    subscribeToMapEvent(MapEvent.STYLE_DATA_LOADED, styleDataLoadedCallback)
    onStyleDataLoadedListeners.add(styleDataLoadedCallback)
  }

  fun removeOnStyleDataLoadedListener(styleDataLoadedCallback: StyleDataLoadedCallback) {
    onStyleDataLoadedListeners.remove(styleDataLoadedCallback)
    if (onStyleDataLoadedListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvent.STYLE_DATA_LOADED)
    }
  }

  fun addOnStyleImageMissingListener(styleImageMissingCallback: StyleImageMissingCallback) {
    subscribeToMapEvent(MapEvent.STYLE_IMAGE_MISSING, styleImageMissingCallback)
    onStyleImageMissingListeners.add(styleImageMissingCallback)
  }

  fun removeOnStyleImageMissingListener(styleImageMissingCallback: StyleImageMissingCallback) {
    onStyleImageMissingListeners.remove(styleImageMissingCallback)
    if (onStyleImageMissingListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvent.STYLE_IMAGE_MISSING)
    }
  }

  fun addOnStyleImageUnusedListener(styleImageRemoveUnusedCallback: StyleImageRemoveUnusedCallback) {
    subscribeToMapEvent(MapEvent.STYLE_IMAGE_REMOVE_UNUSED, styleImageRemoveUnusedCallback)
    onStyleImageUnusedListeners.add(styleImageRemoveUnusedCallback)
  }

  fun removeOnStyleImageUnusedListener(styleImageRemoveUnusedCallback: StyleImageRemoveUnusedCallback) {
    onStyleImageUnusedListeners.remove(styleImageRemoveUnusedCallback)
    if (onStyleImageUnusedListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvent.STYLE_IMAGE_REMOVE_UNUSED)
    }
  }

  fun addOnResourceRequestListener(resourceRequestCallback: ResourceRequestCallback) {
    subscribeToMapEvent(MapEvent.RESOURCE_REQUEST, resourceRequestCallback)
    onResourceRequestListeners.add(resourceRequestCallback)
  }

  fun removeOnResourceRequestListener(resourceRequestCallback: ResourceRequestCallback) {
    onResourceRequestListeners.remove(resourceRequestCallback)
    if (onStyleImageUnusedListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvent.RESOURCE_REQUEST)
    }
  }

  fun addOnGenericEventListener(eventName: String, onUntypedEventCallback: GenericEventCallback) {
    subscribeToMapEvent(MapEvent.GENERIC_EVENT, onUntypedEventCallback, eventName)
    onGenericEventListeners.add(onUntypedEventCallback)
  }

  fun removeOnGenericEventListener(genericEventCallback: GenericEventCallback) {
    onGenericEventListeners.remove(genericEventCallback)
    if (onGenericEventListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvent.GENERIC_EVENT)
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

    onResourceRequestListeners.clear()
    onGenericEventListeners.clear()
  }

  companion object {
    private const val TAG = "Mapbox-NativeObserver"
  }
}
package com.mapbox.maps

import androidx.annotation.UiThread
import com.mapbox.maps.extension.observable.*
import com.mapbox.maps.plugin.delegates.listeners.*
import java.lang.ref.WeakReference
import java.util.concurrent.CopyOnWriteArrayList

@UiThread
internal class NativeObserver(
  private val observable: WeakReference<ObservableInterface>
) : Observer {
  val onCameraChangeListeners = CopyOnWriteArrayList<OnCameraChangeListener>()

  val onMapIdleListeners = CopyOnWriteArrayList<OnMapIdleListener>()
  val onMapLoadErrorListeners = CopyOnWriteArrayList<OnMapLoadErrorListener>()
  val onMapLoadedListeners = CopyOnWriteArrayList<OnMapLoadedListener>()

  val onRenderFrameFinishedListeners = CopyOnWriteArrayList<OnRenderFrameFinishedListener>()
  val onRenderFrameStartedListeners = CopyOnWriteArrayList<OnRenderFrameStartedListener>()

  val onSourceAddedListeners = CopyOnWriteArrayList<OnSourceAddedListener>()
  val onSourceRemovedListeners = CopyOnWriteArrayList<OnSourceRemovedListener>()

  val onSourceDataLoadedListeners = CopyOnWriteArrayList<OnSourceDataLoadedListener>()

  val onStyleDataLoadedListeners = CopyOnWriteArrayList<OnStyleDataLoadedListener>()
  val onStyleLoadedListeners = CopyOnWriteArrayList<OnStyleLoadedListener>()
  val onStyleImageMissingListeners = CopyOnWriteArrayList<OnStyleImageMissingListener>()
  val onStyleImageUnusedListeners = CopyOnWriteArrayList<OnStyleImageUnusedListener>()

  var observedEvents = CopyOnWriteArrayList<String>()
  //
  // Internal callbacks
  //

  override fun notify(event: Event) {
    when (event.type) {
      // Camera events
      MapEvents.CAMERA_CHANGED -> onCameraChangeListeners.forEach { it.onCameraChanged() }
      // Map events
      MapEvents.MAP_IDLE -> onMapIdleListeners.forEach { it.onMapIdle() }
      MapEvents.MAP_LOADING_ERROR -> if (onMapLoadErrorListeners.isNotEmpty()) {
        val loadingError = event.getMapLoadingErrorEventData()
        onMapLoadErrorListeners.forEach {
          it.onMapLoadError(loadingError.type, loadingError.message)
        }
      }
      MapEvents.MAP_LOADED -> onMapLoadedListeners.forEach { it.onMapLoaded() }
      // Style events
      MapEvents.STYLE_DATA_LOADED -> if (onStyleDataLoadedListeners.isNotEmpty()) {
        val eventData = event.getStyleDataLoadedEventData()
        onStyleDataLoadedListeners.forEach {
          it.onStyleDataLoaded(eventData.styleDataType)
        }
      }
      MapEvents.STYLE_LOADED -> onStyleLoadedListeners.forEach { it.onStyleLoaded() }
      MapEvents.STYLE_IMAGE_MISSING -> if (onStyleImageMissingListeners.isNotEmpty()) {
        val eventData = event.getStyleImageMissingEventData()
        onStyleImageMissingListeners.forEach {
          it.onStyleImageMissing(eventData.id)
        }
      }
      MapEvents.STYLE_IMAGE_REMOVE_UNUSED -> if (onStyleImageUnusedListeners.isNotEmpty()) {
        val eventData = event.getStyleImageUnusedEventData()
        onStyleImageUnusedListeners.forEach {
          it.onStyleImageUnused(eventData.id)
        }
      }
      // Render frame events
      MapEvents.RENDER_FRAME_STARTED -> onRenderFrameStartedListeners.forEach { it.onRenderFrameStarted() }
      MapEvents.RENDER_FRAME_FINISHED -> if (onRenderFrameFinishedListeners.isNotEmpty()) {
        val eventData = event.getRenderFrameFinishedEventData()
        onRenderFrameFinishedListeners.forEach {
          it.onRenderFrameFinished(
            eventData.renderMode,
            eventData.needsRepaint,
            eventData.placementChanged
          )
        }
      }
      // Source events
      MapEvents.SOURCE_ADDED -> if (onSourceAddedListeners.isNotEmpty()) {
        val eventData = event.getSourceAddedEventData()
        onSourceAddedListeners.forEach {
          it.onSourceAdded(eventData.id)
        }
      }
      MapEvents.SOURCE_DATA_LOADED -> if (onSourceDataLoadedListeners.isNotEmpty()) {
        val eventData = event.getSourceDataLoadedEventData()
        onSourceDataLoadedListeners.forEach {
          it.onSourceDataLoaded(eventData.id, eventData.type, eventData.loaded, eventData.tileID)
        }
      }
      MapEvents.SOURCE_REMOVED -> if (onSourceRemovedListeners.isNotEmpty()) {
        val eventData = event.getSourceRemovedEventData()
        onSourceRemovedListeners.forEach {
          it.onSourceRemoved(eventData.id)
        }
      }
    }
  }

  //
  // Add / Remove
  //

  private fun subscribeNewEvent(eventType: String) {
    observable.get()?.subscribe(this, listOf(eventType))
    observedEvents.add(eventType)
  }

  private fun unsubscribeUnusedEvent(eventType: String) {
    observable.get()?.unsubscribe(this, listOf(eventType))
    observedEvents.remove(eventType)
  }

  fun addOnCameraChangeListener(onCameraChangeListener: OnCameraChangeListener) {
    if (onCameraChangeListeners.isEmpty()) {
      subscribeNewEvent(MapEvents.CAMERA_CHANGED)
    }
    onCameraChangeListeners.add(onCameraChangeListener)
  }

  fun removeOnCameraChangeListener(onCameraChangeListener: OnCameraChangeListener) {
    onCameraChangeListeners.remove(onCameraChangeListener)
    if (onCameraChangeListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvents.CAMERA_CHANGED)
    }
  }

  // Map events
  fun addOnMapIdleListener(onMapIdleListener: OnMapIdleListener) {
    if (onMapIdleListeners.isEmpty()) {
      subscribeNewEvent(MapEvents.MAP_IDLE)
    }
    onMapIdleListeners.add(onMapIdleListener)
  }

  fun removeOnMapIdleListener(onMapIdleListener: OnMapIdleListener) {
    onMapIdleListeners.remove(onMapIdleListener)
    if (onMapIdleListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvents.MAP_IDLE)
    }
  }

  fun addOnMapLoadErrorListener(onMapLoadErrorListener: OnMapLoadErrorListener) {
    if (onMapLoadErrorListeners.isEmpty()) {
      subscribeNewEvent(MapEvents.MAP_LOADING_ERROR)
    }
    onMapLoadErrorListeners.add(onMapLoadErrorListener)
  }

  fun removeOnMapLoadErrorListener(onMapLoadErrorListener: OnMapLoadErrorListener) {
    onMapLoadErrorListeners.remove(onMapLoadErrorListener)
    if (onMapLoadErrorListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvents.MAP_LOADING_ERROR)
    }
  }

  fun addOnMapLoadedListener(onMapLoadedListener: OnMapLoadedListener) {
    if (onMapLoadedListeners.isEmpty()) {
      subscribeNewEvent(MapEvents.MAP_LOADED)
    }
    onMapLoadedListeners.add(onMapLoadedListener)
  }

  fun removeOnMapLoadedListener(onMapLoadedListener: OnMapLoadedListener) {
    onMapLoadedListeners.remove(onMapLoadedListener)
    if (onMapLoadedListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvents.MAP_LOADED)
    }
  }

  // Render frame events
  fun addOnRenderFrameFinishedListener(onRenderFrameFinishedListener: OnRenderFrameFinishedListener) {
    if (onRenderFrameFinishedListeners.isEmpty()) {
      subscribeNewEvent(MapEvents.RENDER_FRAME_FINISHED)
    }
    onRenderFrameFinishedListeners.add(onRenderFrameFinishedListener)
  }

  fun removeOnRenderFrameFinishedListener(onRenderFrameFinishedListener: OnRenderFrameFinishedListener) {
    onRenderFrameFinishedListeners.remove(onRenderFrameFinishedListener)
    if (onRenderFrameFinishedListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvents.RENDER_FRAME_FINISHED)
    }
  }

  fun addOnRenderFrameStartedListener(onRenderFrameStartedListener: OnRenderFrameStartedListener) {
    if (onRenderFrameStartedListeners.isEmpty()) {
      subscribeNewEvent(MapEvents.RENDER_FRAME_STARTED)
    }
    onRenderFrameStartedListeners.add(onRenderFrameStartedListener)
  }

  fun removeOnRenderFrameStartedListener(onRenderFrameStartedListener: OnRenderFrameStartedListener) {
    onRenderFrameStartedListeners.remove(onRenderFrameStartedListener)
    if (onRenderFrameStartedListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvents.RENDER_FRAME_STARTED)
    }
  }

  // Source events
  fun addOnSourceAddedListener(onSourceAddedListener: OnSourceAddedListener) {
    if (onSourceAddedListeners.isEmpty()) {
      subscribeNewEvent(MapEvents.SOURCE_ADDED)
    }
    onSourceAddedListeners.add(onSourceAddedListener)
  }

  fun removeOnSourceAddedListener(onSourceAddedListener: OnSourceAddedListener) {
    onSourceAddedListeners.remove(onSourceAddedListener)
    if (onSourceAddedListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvents.SOURCE_ADDED)
    }
  }

  fun addOnSourceDataLoadedListener(onSourceDataLoadedListener: OnSourceDataLoadedListener) {
    if (onSourceDataLoadedListeners.isEmpty()) {
      subscribeNewEvent(MapEvents.SOURCE_DATA_LOADED)
    }
    onSourceDataLoadedListeners.add(onSourceDataLoadedListener)
  }

  fun removeOnSourceDataLoadedListener(onSourceDataLoadedListener: OnSourceDataLoadedListener) {
    onSourceDataLoadedListeners.remove(onSourceDataLoadedListener)
    if (onSourceDataLoadedListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvents.SOURCE_DATA_LOADED)
    }
  }

  fun addOnSourceRemovedListener(onSourceRemovedListener: OnSourceRemovedListener) {
    if (onSourceRemovedListeners.isEmpty()) {
      subscribeNewEvent(MapEvents.SOURCE_REMOVED)
    }
    onSourceRemovedListeners.add(onSourceRemovedListener)
  }

  fun removeOnSourceRemovedListener(onSourceRemovedListener: OnSourceRemovedListener) {
    onSourceRemovedListeners.remove(onSourceRemovedListener)
    if (onSourceRemovedListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvents.SOURCE_REMOVED)
    }
  }

  // Style events
  fun addOnStyleLoadedListener(onStyleLoadedListener: OnStyleLoadedListener) {
    if (onStyleLoadedListeners.isEmpty()) {
      subscribeNewEvent(MapEvents.STYLE_LOADED)
    }
    onStyleLoadedListeners.add(onStyleLoadedListener)
  }

  fun removeOnStyleLoadedListener(onStyleLoadedListener: OnStyleLoadedListener) {
    onStyleLoadedListeners.remove(onStyleLoadedListener)
    if (onStyleLoadedListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvents.STYLE_LOADED)
    }
  }

  fun addOnStyleDataLoadedListener(onStyleDataLoadedListener: OnStyleDataLoadedListener) {
    if (onStyleDataLoadedListeners.isEmpty()) {
      subscribeNewEvent(MapEvents.STYLE_DATA_LOADED)
    }
    onStyleDataLoadedListeners.add(onStyleDataLoadedListener)
  }

  fun removeOnStyleDataLoadedListener(onStyleDataLoadedListener: OnStyleDataLoadedListener) {
    onStyleDataLoadedListeners.remove(onStyleDataLoadedListener)
    if (onStyleDataLoadedListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvents.STYLE_DATA_LOADED)
    }
  }

  fun addOnStyleImageMissingListener(onStyleImageMissingListener: OnStyleImageMissingListener) {
    if (onStyleImageMissingListeners.isEmpty()) {
      subscribeNewEvent(MapEvents.STYLE_IMAGE_MISSING)
    }
    onStyleImageMissingListeners.add(onStyleImageMissingListener)
  }

  fun removeOnStyleImageMissingListener(onStyleImageMissingListener: OnStyleImageMissingListener) {
    onStyleImageMissingListeners.remove(onStyleImageMissingListener)
    if (onStyleImageMissingListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvents.STYLE_IMAGE_MISSING)
    }
  }

  fun addOnStyleImageUnusedListener(onStyleImageUnusedListener: OnStyleImageUnusedListener) {
    if (onStyleImageUnusedListeners.isEmpty()) {
      subscribeNewEvent(MapEvents.STYLE_IMAGE_REMOVE_UNUSED)
    }
    onStyleImageUnusedListeners.add(onStyleImageUnusedListener)
  }

  fun removeOnStyleImageUnusedListener(onStyleImageUnusedListener: OnStyleImageUnusedListener) {
    onStyleImageUnusedListeners.remove(onStyleImageUnusedListener)
    if (onStyleImageUnusedListeners.isEmpty()) {
      unsubscribeUnusedEvent(MapEvents.STYLE_IMAGE_REMOVE_UNUSED)
    }
  }

  fun onDestroy() {
    observable.get()?.unsubscribe(this)

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

  companion object {
    private const val TAG = "Mapbox-NativeObserver"
    val SUPPORTED_EVENTS = listOf(
      // Camera events
      MapEvents.CAMERA_CHANGED,
      // Map events
      MapEvents.MAP_IDLE,
      MapEvents.MAP_LOADING_ERROR,
      MapEvents.MAP_LOADED,
      // Style events
      MapEvents.STYLE_DATA_LOADED,
      MapEvents.STYLE_LOADED,
      MapEvents.STYLE_IMAGE_MISSING,
      MapEvents.STYLE_IMAGE_REMOVE_UNUSED,
      // Render frame events
      MapEvents.RENDER_FRAME_STARTED,
      MapEvents.RENDER_FRAME_FINISHED,
      // Source events
      MapEvents.SOURCE_ADDED,
      MapEvents.SOURCE_DATA_LOADED,
      MapEvents.SOURCE_REMOVED
    )
  }
}
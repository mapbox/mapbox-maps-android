package com.mapbox.maps

import android.os.Handler
import android.os.Looper
import androidx.annotation.UiThread
import com.mapbox.maps.extension.observable.*
import com.mapbox.maps.extension.observable.eventdata.*
import com.mapbox.maps.plugin.delegates.listeners.*
import java.util.concurrent.CopyOnWriteArraySet

@UiThread
internal class NativeObserver(
  private val observable: ObservableInterface
) : Observer {
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

  var observedEvents = CopyOnWriteArraySet<String>()
  //
  // Internal callbacks
  //

  override fun notify(event: Event) {
    try {
      when (event.type) {
        // Camera events
        MapEvents.CAMERA_CHANGED -> onCameraChangeListeners.forEach {
          it.onCameraChanged(
            event.getCameraChangedEventData()
          )
        }
        // Map events
        MapEvents.MAP_IDLE -> onMapIdleListeners.forEach { it.onMapIdle(event.getMapIdleEventData()) }
        MapEvents.MAP_LOADING_ERROR -> if (onMapLoadErrorListeners.isNotEmpty()) {
          onMapLoadErrorListeners.forEach {
            it.onMapLoadError(event.getMapLoadingErrorEventData())
          }
        }
        MapEvents.MAP_LOADED -> onMapLoadedListeners.forEach { it.onMapLoaded(event.getMapLoadedEventData()) }
        // Style events
        MapEvents.STYLE_DATA_LOADED -> if (onStyleDataLoadedListeners.isNotEmpty()) {
          onStyleDataLoadedListeners.forEach {
            it.onStyleDataLoaded(event.getStyleDataLoadedEventData())
          }
        }
        MapEvents.STYLE_LOADED -> onStyleLoadedListeners.forEach {
          it.onStyleLoaded(event.getStyleLoadedEventData())
        }
        MapEvents.STYLE_IMAGE_MISSING -> if (onStyleImageMissingListeners.isNotEmpty()) {
          onStyleImageMissingListeners.forEach {
            it.onStyleImageMissing(event.getStyleImageMissingEventData())
          }
        }
        MapEvents.STYLE_IMAGE_REMOVE_UNUSED -> if (onStyleImageUnusedListeners.isNotEmpty()) {
          onStyleImageUnusedListeners.forEach {
            it.onStyleImageUnused(event.getStyleImageUnusedEventData())
          }
        }
        // Render frame events
        MapEvents.RENDER_FRAME_STARTED -> onRenderFrameStartedListeners.forEach {
          it.onRenderFrameStarted(event.getRenderFrameStartedEventData())
        }
        MapEvents.RENDER_FRAME_FINISHED -> if (onRenderFrameFinishedListeners.isNotEmpty()) {
          onRenderFrameFinishedListeners.forEach {
            it.onRenderFrameFinished(event.getRenderFrameFinishedEventData())
          }
        }
        // Source events
        MapEvents.SOURCE_ADDED -> if (onSourceAddedListeners.isNotEmpty()) {
          onSourceAddedListeners.forEach {
            it.onSourceAdded(event.getSourceAddedEventData())
          }
        }
        MapEvents.SOURCE_DATA_LOADED -> if (onSourceDataLoadedListeners.isNotEmpty()) {
          onSourceDataLoadedListeners.forEach {
            it.onSourceDataLoaded(event.getSourceDataLoadedEventData())
          }
        }
        MapEvents.SOURCE_REMOVED -> if (onSourceRemovedListeners.isNotEmpty()) {
          onSourceRemovedListeners.forEach {
            it.onSourceRemoved(event.getSourceRemovedEventData())
          }
        }
      }
    } catch (exception: Exception) {
      // Catching exception and rethrowing on main thread
      // This avoids a native crash with a pending java exception
      Handler(Looper.getMainLooper()).postAtFrontOfQueue {
        throw exception
      }
    }
  }

  //
  // Add / Remove
  //

  private fun subscribeNewEvent(eventType: String) {
    observable.subscribe(this, listOf(eventType))
    observedEvents.add(eventType)
  }

  private fun unsubscribeUnusedEvent(eventType: String) {
    observable.unsubscribe(this, listOf(eventType))
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

  // This method is needed to prevent receiving styleLoaded events with the old style
  // when new style is loaded. For example loading empty style and another style immediately after
  // that will notify STYLE_LOADED event for the first style after second style has already started
  // loading
  fun resubscribeStyleLoadListeners() {
    unsubscribeUnusedEvent(MapEvents.STYLE_LOADED)
    subscribeNewEvent(MapEvents.STYLE_LOADED)
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
    observable.unsubscribe(this)

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
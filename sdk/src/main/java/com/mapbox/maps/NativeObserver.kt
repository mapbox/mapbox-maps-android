package com.mapbox.maps

import com.mapbox.maps.extension.observable.*
import com.mapbox.maps.plugin.delegates.listeners.*
import java.util.concurrent.CopyOnWriteArrayList

internal class NativeObserver : Observer() {
  val onCameraChangeListeners = CopyOnWriteArrayList<OnCameraChangeListener>()

  val onMapIdleListeners = CopyOnWriteArrayList<OnMapIdleListener>()
  val onMapLoadErrorListeners = CopyOnWriteArrayList<OnMapLoadErrorListener>()
  val onMapLoadingFinishedListeners = CopyOnWriteArrayList<OnMapLoadingFinishedListener>()

  val onRenderFrameFinishedListeners = CopyOnWriteArrayList<OnRenderFrameFinishedListener>()
  val onRenderFrameStartedListeners = CopyOnWriteArrayList<OnRenderFrameStartedListener>()

  val onSourceAddedListeners = CopyOnWriteArrayList<OnSourceAddedListener>()
  val onSourceChangeListeners = CopyOnWriteArrayList<OnSourceChangeListener>()
  val onSourceRemovedListeners = CopyOnWriteArrayList<OnSourceRemovedListener>()

  val onStyleFullyLoadedListeners = CopyOnWriteArrayList<OnStyleFullyLoadedListener>()
  val onStyleImageMissingListeners = CopyOnWriteArrayList<OnStyleImageMissingListener>()
  val onStyleImageUnusedListeners = CopyOnWriteArrayList<OnStyleImageUnusedListener>()
  val onStyleLoadingFinishedListeners = CopyOnWriteArrayList<OnStyleLoadingFinishedListener>()

  val awaitingStyleGetters = mutableListOf<Style.OnStyleLoaded>()

  //
  // Internal callbacks
  //

  override fun notify(event: Event) {
    when (event.type) {
      // Camera events
      MapEvents.CAMERA_CHANGED -> if (onCameraChangeListeners.isNotEmpty()) {
        onCameraChangeListeners.forEach { it.onCameraChanged() }
      }
      // Map events
      MapEvents.MAP_IDLE -> if (onMapIdleListeners.isNotEmpty()) {
        onMapIdleListeners.forEach { it.onMapIdle() }
      }
      MapEvents.MAP_LOADING_ERROR -> if (onMapLoadErrorListeners.isNotEmpty()) {
        val loadingError = event.getMapLoadingErrorEventData()
        onMapLoadErrorListeners.forEach {
          it.onMapLoadError(loadingError.error, loadingError.description)
        }
      }
      MapEvents.MAP_LOADING_FINISHED -> if (onMapLoadingFinishedListeners.isNotEmpty()) {
        onMapLoadingFinishedListeners.forEach {
          it.onMapLoadingFinished()
        }
      }
      // Style events
      MapEvents.STYLE_LOADING_FINISHED -> if (onStyleLoadingFinishedListeners.isNotEmpty()) {
        onStyleLoadingFinishedListeners.forEach {
          it.onStyleLoadingFinished()
        }
      }
      MapEvents.STYLE_FULLY_LOADED -> if (onStyleFullyLoadedListeners.isNotEmpty()) {
        onStyleFullyLoadedListeners.forEach {
          it.onStyleFullyLoaded()
        }
      }
      MapEvents.STYLE_IMAGE_MISSING -> if (onStyleImageMissingListeners.isNotEmpty()) {
        val eventData = event.getStyleImageMissingEventData()
        onStyleImageMissingListeners.forEach {
          it.onStyleImageMissing(eventData.id)
        }
      }
      MapEvents.STYLE_IMAGE_REMOVE_UNUSED -> if (onStyleImageUnusedListeners.isNotEmpty()) {
        val eventData = event.getStyleImageRemoveUnusedEventData()
        onStyleImageUnusedListeners.forEach {
          it.onStyleImageUnused(eventData.id)
        }
      }
      // Render frame events
      MapEvents.RENDER_FRAME_STARTED -> if (onRenderFrameStartedListeners.isNotEmpty()) {
        onRenderFrameStartedListeners.forEach {
          it.onRenderFrameStarted()
        }
      }
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
      MapEvents.SOURCE_CHANGED -> if (onSourceChangeListeners.isNotEmpty()) {
        val eventData = event.getSourceChangedEventData()
        onSourceChangeListeners.forEach {
          it.onSourceChanged(eventData.id)
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

  fun addOnCameraChangeListener(onCameraChangeListener: OnCameraChangeListener) {
    onCameraChangeListeners.add(onCameraChangeListener)
  }

  fun removeOnCameraChangeListener(onCameraChangeListener: OnCameraChangeListener) {
    onCameraChangeListeners.remove(onCameraChangeListener)
  }

  // Map events
  fun addOnMapIdleListener(onMapIdleListener: OnMapIdleListener) {
    onMapIdleListeners.add(onMapIdleListener)
  }

  fun removeOnMapIdleListener(onMapIdleListener: OnMapIdleListener) {
    onMapIdleListeners.remove(onMapIdleListener)
  }

  fun addOnMapLoadErrorListener(onMapLoadErrorListener: OnMapLoadErrorListener) {
    onMapLoadErrorListeners.add(onMapLoadErrorListener)
  }

  fun removeOnMapLoadErrorListener(onMapLoadErrorListener: OnMapLoadErrorListener) {
    onMapLoadErrorListeners.remove(onMapLoadErrorListener)
  }

  fun addOnMapLoadingFinishedListener(onMapLoadingFinishedListener: OnMapLoadingFinishedListener) {
    onMapLoadingFinishedListeners.add(onMapLoadingFinishedListener)
  }

  fun removeOnMapLoadingFinishedListener(onMapLoadingFinishedListener: OnMapLoadingFinishedListener) {
    onMapLoadingFinishedListeners.remove(onMapLoadingFinishedListener)
  }

  // Render frame events
  fun addOnRenderFrameFinishedListener(onRenderFrameFinishedListener: OnRenderFrameFinishedListener) {
    onRenderFrameFinishedListeners.add(onRenderFrameFinishedListener)
  }

  fun removeOnRenderFrameFinishedListener(onRenderFrameFinishedListener: OnRenderFrameFinishedListener) {
    onRenderFrameFinishedListeners.remove(onRenderFrameFinishedListener)
  }

  fun addOnRenderFrameStartedListener(onRenderFrameStartedListener: OnRenderFrameStartedListener) {
    onRenderFrameStartedListeners.add(onRenderFrameStartedListener)
  }

  fun removeOnRenderFrameStartedListener(onRenderFrameStartedListener: OnRenderFrameStartedListener) {
    onRenderFrameStartedListeners.remove(onRenderFrameStartedListener)
  }

  // Source events
  fun addOnSourceAddedListener(onSourceAddedListener: OnSourceAddedListener) {
    onSourceAddedListeners.add(onSourceAddedListener)
  }

  fun removeOnSourceAddedListener(onSourceAddedListener: OnSourceAddedListener) {
    onSourceAddedListeners.remove(onSourceAddedListener)
  }

  fun addOnSourceChangeListener(onSourceChangeListener: OnSourceChangeListener) {
    onSourceChangeListeners.add(onSourceChangeListener)
  }
  fun removeOnSourceChangeListener(onSourceChangeListener: OnSourceChangeListener) {
    onSourceChangeListeners.remove(onSourceChangeListener)
  }

  fun addOnSourceRemovedListener(onSourceRemovedListener: OnSourceRemovedListener) {
    onSourceRemovedListeners.add(onSourceRemovedListener)
  }

  fun removeOnSourceRemovedListener(onSourceRemovedListener: OnSourceRemovedListener) {
    onSourceRemovedListeners.remove(onSourceRemovedListener)
  }

  // Style events
  fun addOnStyleFullyLoadedListener(onStyleFullyLoadedListener: OnStyleFullyLoadedListener) {
    onStyleFullyLoadedListeners.add(onStyleFullyLoadedListener)
  }
  fun removeOnStyleFullyLoadedListener(onStyleFullyLoadedListener: OnStyleFullyLoadedListener) {
    onStyleFullyLoadedListeners.remove(onStyleFullyLoadedListener)
  }

  fun addOnStyleImageMissingListener(onStyleImageMissingListener: OnStyleImageMissingListener) {
    onStyleImageMissingListeners.add(onStyleImageMissingListener)
  }

  fun removeOnStyleImageMissingListener(onStyleImageMissingListener: OnStyleImageMissingListener) {
    onStyleImageMissingListeners.remove(onStyleImageMissingListener)
  }

  fun addOnStyleImageUnusedListener(onStyleImageUnusedListener: OnStyleImageUnusedListener) {
    onStyleImageUnusedListeners.add(onStyleImageUnusedListener)
  }

  fun removeOnStyleImageUnusedListener(onStyleImageUnusedListener: OnStyleImageUnusedListener) {
    onStyleImageUnusedListeners.remove(onStyleImageUnusedListener)
  }

  fun addOnStyleLoadingFinishedListener(onStyleLoadingFinishedListener: OnStyleLoadingFinishedListener) {
    onStyleLoadingFinishedListeners.add(onStyleLoadingFinishedListener)
  }

  fun removeOnStyleLoadingFinishedListener(onStyleLoadingFinishedListener: OnStyleLoadingFinishedListener) {
    onStyleLoadingFinishedListeners.remove(onStyleLoadingFinishedListener)
  }

  fun clearListeners() {
    onCameraChangeListeners.clear()

    onMapIdleListeners.clear()
    onMapLoadErrorListeners.clear()
    onMapLoadingFinishedListeners.clear()

    onRenderFrameFinishedListeners.clear()
    onRenderFrameStartedListeners.clear()

    onSourceAddedListeners.clear()
    onSourceChangeListeners.clear()
    onSourceRemovedListeners.clear()

    onStyleFullyLoadedListeners.clear()
    onStyleImageMissingListeners.clear()
    onStyleImageUnusedListeners.clear()
    onStyleLoadingFinishedListeners.clear()

    awaitingStyleGetters.clear()
  }

  companion object {
    private const val TAG = "Mapbox-NativeObserver"
    val OBSERVED_EVENTS = listOf(
      // Camera events
      MapEvents.CAMERA_CHANGED,
      // Map events
      MapEvents.MAP_IDLE,
      MapEvents.MAP_LOADING_ERROR,
      MapEvents.MAP_LOADING_FINISHED,
      // Style events
      MapEvents.STYLE_LOADING_FINISHED,
      MapEvents.STYLE_FULLY_LOADED,
      MapEvents.STYLE_IMAGE_MISSING,
      MapEvents.STYLE_IMAGE_REMOVE_UNUSED,
      // Render frame events
      MapEvents.RENDER_FRAME_STARTED,
      MapEvents.RENDER_FRAME_FINISHED,
      MapEvents.SOURCE_ADDED,
      MapEvents.SOURCE_CHANGED,
      MapEvents.SOURCE_REMOVED
    )
  }
}
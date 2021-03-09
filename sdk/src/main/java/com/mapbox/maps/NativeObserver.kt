package com.mapbox.maps

import android.os.Handler
import com.mapbox.maps.extension.observable.*
import com.mapbox.maps.plugin.delegates.listeners.*
import java.lang.ref.WeakReference
import java.util.concurrent.CopyOnWriteArrayList

internal class NativeObserver(
  private val observable: WeakReference<ObservableInterface>,
  private val handler: Handler
) : Observer() {
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

  var observedEvents = CopyOnWriteArrayList<String>()

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

  internal fun onStop() {
    observable.get()?.unsubscribe(this)
  }

  internal fun onStart() {
    observable.get()?.subscribe(this, observedEvents)
  }

  //
  // Add / Remove
  //

  fun addOnCameraChangeListener(onCameraChangeListener: OnCameraChangeListener) {
    if (onCameraChangeListeners.isEmpty()) {
      handler.post {
        observable.get()?.subscribe(this, listOf(MapEvents.CAMERA_CHANGED))
      }
      observedEvents.add(MapEvents.CAMERA_CHANGED)
    }
    onCameraChangeListeners.add(onCameraChangeListener)
  }

  fun removeOnCameraChangeListener(onCameraChangeListener: OnCameraChangeListener) {
    onCameraChangeListeners.remove(onCameraChangeListener)
    if (onCameraChangeListeners.isEmpty()) {
      handler.post {
        observable.get()?.unsubscribe(this, listOf(MapEvents.CAMERA_CHANGED))
      }
      observedEvents.remove(MapEvents.CAMERA_CHANGED)
    }
  }

  // Map events
  fun addOnMapIdleListener(onMapIdleListener: OnMapIdleListener) {
    if (onMapIdleListeners.isEmpty()) {
      handler.post {
        observable.get()?.subscribe(this, listOf(MapEvents.MAP_IDLE))
      }
      observedEvents.add(MapEvents.MAP_IDLE)
    }
    onMapIdleListeners.add(onMapIdleListener)
  }

  fun removeOnMapIdleListener(onMapIdleListener: OnMapIdleListener) {
    onMapIdleListeners.remove(onMapIdleListener)
    if (onMapIdleListeners.isEmpty()) {
      handler.post {
        observable.get()?.unsubscribe(this, listOf(MapEvents.MAP_IDLE))
      }
      observedEvents.remove(MapEvents.MAP_IDLE)
    }
  }

  fun addOnMapLoadErrorListener(onMapLoadErrorListener: OnMapLoadErrorListener) {
    if (onMapLoadErrorListeners.isEmpty()) {
      handler.post {
        observable.get()?.subscribe(this, listOf(MapEvents.MAP_LOADING_ERROR))
      }
      observedEvents.add(MapEvents.MAP_LOADING_ERROR)
    }
    onMapLoadErrorListeners.add(onMapLoadErrorListener)
  }

  fun removeOnMapLoadErrorListener(onMapLoadErrorListener: OnMapLoadErrorListener) {
    onMapLoadErrorListeners.remove(onMapLoadErrorListener)
    if (onMapLoadErrorListeners.isEmpty()) {
      handler.post {
        observable.get()?.unsubscribe(this, listOf(MapEvents.MAP_LOADING_ERROR))
      }
      observedEvents.remove(MapEvents.MAP_LOADING_ERROR)
    }
  }

  fun addOnMapLoadingFinishedListener(onMapLoadingFinishedListener: OnMapLoadingFinishedListener) {
    if (onMapLoadingFinishedListeners.isEmpty()) {
      handler.post {
        observable.get()?.subscribe(this, listOf(MapEvents.MAP_LOADING_FINISHED))
      }
      observedEvents.add(MapEvents.MAP_LOADING_FINISHED)
    }
    onMapLoadingFinishedListeners.add(onMapLoadingFinishedListener)
  }

  fun removeOnMapLoadingFinishedListener(onMapLoadingFinishedListener: OnMapLoadingFinishedListener) {
    onMapLoadingFinishedListeners.remove(onMapLoadingFinishedListener)
    if (onMapLoadingFinishedListeners.isEmpty()) {
      handler.post {
        observable.get()?.unsubscribe(this, listOf(MapEvents.MAP_LOADING_FINISHED))
      }
      observedEvents.remove(MapEvents.MAP_LOADING_FINISHED)
    }
  }

  // Render frame events
  fun addOnRenderFrameFinishedListener(onRenderFrameFinishedListener: OnRenderFrameFinishedListener) {
    if (onRenderFrameFinishedListeners.isEmpty()) {
      handler.post {
        observable.get()?.subscribe(this, listOf(MapEvents.RENDER_FRAME_FINISHED))
      }
      observedEvents.add(MapEvents.RENDER_FRAME_FINISHED)
    }
    onRenderFrameFinishedListeners.add(onRenderFrameFinishedListener)
  }

  fun removeOnRenderFrameFinishedListener(onRenderFrameFinishedListener: OnRenderFrameFinishedListener) {
    onRenderFrameFinishedListeners.remove(onRenderFrameFinishedListener)
    if (onRenderFrameFinishedListeners.isEmpty()) {
      handler.post {
        observable.get()?.unsubscribe(this, listOf(MapEvents.RENDER_FRAME_FINISHED))
      }
      observedEvents.remove(MapEvents.RENDER_FRAME_FINISHED)
    }
  }

  fun addOnRenderFrameStartedListener(onRenderFrameStartedListener: OnRenderFrameStartedListener) {
    if (onRenderFrameStartedListeners.isEmpty()) {
      handler.post {
        observable.get()?.subscribe(this, listOf(MapEvents.RENDER_FRAME_STARTED))
      }
      observedEvents.add(MapEvents.RENDER_FRAME_STARTED)
    }
    onRenderFrameStartedListeners.add(onRenderFrameStartedListener)
  }

  fun removeOnRenderFrameStartedListener(onRenderFrameStartedListener: OnRenderFrameStartedListener) {
    onRenderFrameStartedListeners.remove(onRenderFrameStartedListener)
    if (onRenderFrameStartedListeners.isEmpty()) {
      handler.post {
        observable.get()?.unsubscribe(this, listOf(MapEvents.RENDER_FRAME_STARTED))
      }
      observedEvents.remove(MapEvents.RENDER_FRAME_STARTED)
    }
  }

  // Source events
  fun addOnSourceAddedListener(onSourceAddedListener: OnSourceAddedListener) {
    if (onSourceAddedListeners.isEmpty()) {
      handler.post {
        observable.get()?.subscribe(this, listOf(MapEvents.SOURCE_ADDED))
      }
      observedEvents.add(MapEvents.SOURCE_ADDED)
    }
    onSourceAddedListeners.add(onSourceAddedListener)
  }

  fun removeOnSourceAddedListener(onSourceAddedListener: OnSourceAddedListener) {
    onSourceAddedListeners.remove(onSourceAddedListener)
    if (onSourceAddedListeners.isEmpty()) {
      handler.post {
        observable.get()?.unsubscribe(this, listOf(MapEvents.SOURCE_ADDED))
      }
      observedEvents.remove(MapEvents.SOURCE_ADDED)
    }
  }

  fun addOnSourceChangeListener(onSourceChangeListener: OnSourceChangeListener) {
    if (onSourceChangeListeners.isEmpty()) {
      handler.post {
        observable.get()?.subscribe(this, listOf(MapEvents.SOURCE_CHANGED))
      }
      observedEvents.add(MapEvents.SOURCE_CHANGED)
    }
    onSourceChangeListeners.add(onSourceChangeListener)
  }

  fun removeOnSourceChangeListener(onSourceChangeListener: OnSourceChangeListener) {
    onSourceChangeListeners.remove(onSourceChangeListener)
    if (onSourceChangeListeners.isEmpty()) {
      handler.post {
        observable.get()?.unsubscribe(this, listOf(MapEvents.SOURCE_CHANGED))
      }
      observedEvents.remove(MapEvents.SOURCE_CHANGED)
    }
  }

  fun addOnSourceRemovedListener(onSourceRemovedListener: OnSourceRemovedListener) {
    if (onSourceRemovedListeners.isEmpty()) {
      handler.post {
        observable.get()?.subscribe(this, listOf(MapEvents.SOURCE_REMOVED))
      }
      observedEvents.add(MapEvents.SOURCE_REMOVED)
    }
    onSourceRemovedListeners.add(onSourceRemovedListener)
  }

  fun removeOnSourceRemovedListener(onSourceRemovedListener: OnSourceRemovedListener) {
    onSourceRemovedListeners.remove(onSourceRemovedListener)
    if (onSourceRemovedListeners.isEmpty()) {
      handler.post {
        observable.get()?.unsubscribe(this, listOf(MapEvents.SOURCE_REMOVED))
      }
      observedEvents.remove(MapEvents.SOURCE_REMOVED)
    }
  }

  // Style events
  fun addOnStyleFullyLoadedListener(onStyleFullyLoadedListener: OnStyleFullyLoadedListener) {
    if (onStyleFullyLoadedListeners.isEmpty()) {
      handler.post {
        observable.get()?.subscribe(this, listOf(MapEvents.STYLE_FULLY_LOADED))
      }
      observedEvents.add(MapEvents.STYLE_FULLY_LOADED)
    }
    onStyleFullyLoadedListeners.add(onStyleFullyLoadedListener)
  }

  fun removeOnStyleFullyLoadedListener(onStyleFullyLoadedListener: OnStyleFullyLoadedListener) {
    onStyleFullyLoadedListeners.remove(onStyleFullyLoadedListener)
    if (onStyleFullyLoadedListeners.isEmpty()) {
      handler.post {
        observable.get()?.unsubscribe(this, listOf(MapEvents.STYLE_FULLY_LOADED))
      }
      observedEvents.remove(MapEvents.STYLE_FULLY_LOADED)
    }
  }

  fun addOnStyleImageMissingListener(onStyleImageMissingListener: OnStyleImageMissingListener) {
    if (onStyleImageMissingListeners.isEmpty()) {
      handler.post {
        observable.get()?.subscribe(this, listOf(MapEvents.STYLE_IMAGE_MISSING))
      }
      observedEvents.add(MapEvents.STYLE_IMAGE_MISSING)
    }
    onStyleImageMissingListeners.add(onStyleImageMissingListener)
  }

  fun removeOnStyleImageMissingListener(onStyleImageMissingListener: OnStyleImageMissingListener) {
    onStyleImageMissingListeners.remove(onStyleImageMissingListener)
    if (onStyleImageMissingListeners.isEmpty()) {
      handler.post {
        observable.get()?.unsubscribe(this, listOf(MapEvents.STYLE_IMAGE_MISSING))
      }
      observedEvents.remove(MapEvents.STYLE_IMAGE_MISSING)
    }
  }

  fun addOnStyleImageUnusedListener(onStyleImageUnusedListener: OnStyleImageUnusedListener) {
    if (onStyleImageUnusedListeners.isEmpty()) {
      handler.post {
        observable.get()?.subscribe(this, listOf(MapEvents.STYLE_IMAGE_REMOVE_UNUSED))
      }
      observedEvents.add(MapEvents.STYLE_IMAGE_REMOVE_UNUSED)
    }
    onStyleImageUnusedListeners.add(onStyleImageUnusedListener)
  }

  fun removeOnStyleImageUnusedListener(onStyleImageUnusedListener: OnStyleImageUnusedListener) {
    onStyleImageUnusedListeners.remove(onStyleImageUnusedListener)
    if (onStyleImageUnusedListeners.isEmpty()) {
      handler.post {
        observable.get()?.unsubscribe(this, listOf(MapEvents.STYLE_IMAGE_REMOVE_UNUSED))
      }
      observedEvents.remove(MapEvents.STYLE_IMAGE_REMOVE_UNUSED)
    }
  }

  fun addOnStyleLoadingFinishedListener(onStyleLoadingFinishedListener: OnStyleLoadingFinishedListener) {
    if (onStyleLoadingFinishedListeners.isEmpty()) {
      handler.post {
        observable.get()?.subscribe(this, listOf(MapEvents.STYLE_LOADING_FINISHED))
      }
      observedEvents.add(MapEvents.STYLE_LOADING_FINISHED)
    }
    onStyleLoadingFinishedListeners.add(onStyleLoadingFinishedListener)
  }

  fun removeOnStyleLoadingFinishedListener(onStyleLoadingFinishedListener: OnStyleLoadingFinishedListener) {
    onStyleLoadingFinishedListeners.remove(onStyleLoadingFinishedListener)
    if (onStyleLoadingFinishedListeners.isEmpty()) {
      handler.post {
        observable.get()?.unsubscribe(this, listOf(MapEvents.STYLE_LOADING_FINISHED))
      }
      observedEvents.remove(MapEvents.STYLE_LOADING_FINISHED)
    }
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
    val SUPPORTED_EVENTS = listOf(
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
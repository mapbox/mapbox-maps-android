package com.mapbox.maps.extension.compose.internal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.mapbox.maps.CameraChangedCallback
import com.mapbox.maps.MapIdleCallback
import com.mapbox.maps.MapLoadedCallback
import com.mapbox.maps.MapLoadingErrorCallback
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.RenderFrameFinishedCallback
import com.mapbox.maps.RenderFrameStartedCallback
import com.mapbox.maps.ResourceRequestCallback
import com.mapbox.maps.SourceAddedCallback
import com.mapbox.maps.SourceDataLoadedCallback
import com.mapbox.maps.SourceRemovedCallback
import com.mapbox.maps.StyleDataLoadedCallback
import com.mapbox.maps.StyleImageMissingCallback
import com.mapbox.maps.StyleImageRemoveUnusedCallback
import com.mapbox.maps.StyleLoadedCallback
import com.mapbox.maps.extension.compose.MapEvents
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import com.mapbox.maps.plugin.gestures.gestures

/**
 * MapboxMapNode to observe/update the input arguments of MapboxMap.
 */
@OptIn(MapboxExperimental::class)
private class MapboxMapNode(
  val controller: MapView,
  initialClickListener: OnMapClickListener?,
  initialLongClickListener: OnMapLongClickListener?,
  initialMapViewportState: MapViewportState,
  mapEvents: MapEvents?,
) : MapNode() {
  private var mapEventCancelableHolder: MapEventCancelableHolder = MapEventCancelableHolder(controller.mapboxMap)

  var mapViewportState = initialMapViewportState
    set(value) {
      if (value == field) return
      field.setMap(null)
      field = value
      value.setMap(controller)
    }

  var clickListener: OnMapClickListener? = initialClickListener
    set(value) {
      controller.gestures.apply {
        field?.let {
          removeOnMapClickListener(it)
        }
        value?.let {
          addOnMapClickListener(it)
        }
      }
      field = value
    }
  var longClickListener: OnMapLongClickListener? = initialLongClickListener
    set(value) {
      controller.gestures.apply {
        field?.let {
          removeOnMapLongClickListener(it)
        }
        value?.let {
          addOnMapLongClickListener(it)
        }
      }
      field = value
    }

  var mapLoadedCallback: MapLoadedCallback? = mapEvents?.onMapLoaded
    set(value) {
      if (value == field) return
      mapEventCancelableHolder.cancelAndSubscribeToMapLoaded(value)
      field = value
    }

  var mapIdleCallback: MapIdleCallback? = mapEvents?.onMapIdle
    set(value) {
      if (value == field) return
      mapEventCancelableHolder.cancelAndSubscribeToMapIdle(value)
      field = value
    }

  var mapLoadingErrorCallback: MapLoadingErrorCallback? = mapEvents?.onMapLoadingError
    set(value) {
      if (value == field) return
      mapEventCancelableHolder.cancelAndSubscribeToMapLoadingError(value)
      field = value
    }

  var styleLoadedCallback: StyleLoadedCallback? = mapEvents?.onStyleLoaded
    set(value) {
      if (value == field) return
      mapEventCancelableHolder.cancelAndSubscribeToStyleLoaded(value)
      field = value
    }

  var styleDataLoadedCallback: StyleDataLoadedCallback? = mapEvents?.onStyleDataLoaded
    set(value) {
      if (value == field) return
      mapEventCancelableHolder.cancelAndSubscribeToStyleDataLoaded(value)
      field = value
    }

  var sourceDataLoadedCallback: SourceDataLoadedCallback? = mapEvents?.onSourceDataLoaded
    set(value) {
      if (value == field) return
      mapEventCancelableHolder.cancelAndSubscribeToSourceDataLoaded(value)
      field = value
    }

  var sourceAddedCallback: SourceAddedCallback? = mapEvents?.onSourceAdded
    set(value) {
      if (value == field) return
      mapEventCancelableHolder.cancelAndSubscribeToSourceAdded(value)
      field = value
    }

  var sourceRemovedCallback: SourceRemovedCallback? = mapEvents?.onSourceRemoved
    set(value) {
      if (value == field) return
      mapEventCancelableHolder.cancelAndSubscribeToSourceRemoved(value)
      field = value
    }

  var styleImageMissingCallback: StyleImageMissingCallback? = mapEvents?.onStyleImageMissing
    set(value) {
      if (value == field) return
      mapEventCancelableHolder.cancelAndSubscribeToStyleImageMissing(value)
      field = value
    }

  var styleImageRemoveUnusedCallback: StyleImageRemoveUnusedCallback? =
    mapEvents?.onStyleImageRemoveUnused
    set(value) {
      if (value == field) return
      mapEventCancelableHolder.cancelAndSubscribeToStyleImageRemoveUnused(value)
      field = value
    }

  var onCameraChangedCallback: CameraChangedCallback? = mapEvents?.onCameraChanged
    set(value) {
      if (value == field) return
      mapEventCancelableHolder.cancelAndSubscribeToCameraChanged(value)
      field = value
    }

  var renderFrameStartedCallback: RenderFrameStartedCallback? = mapEvents?.onRenderFrameStarted
    set(value) {
      if (value == field) return
      mapEventCancelableHolder.cancelAndSubscribeToRenderFrameStarted(value)
      field = value
    }

  var renderFrameFinishedCallback: RenderFrameFinishedCallback? = mapEvents?.onRenderFrameFinished
    set(value) {
      if (value == field) return
      mapEventCancelableHolder.cancelAndSubscribeToRenderFrameFinished(value)
      field = value
    }

  var resourceRequestCallback: ResourceRequestCallback? = mapEvents?.onResourceRequest
    set(value) {
      if (value == field) return
      mapEventCancelableHolder.cancelAndSubscribeToResourceRequest(value)
      field = value
    }

  override fun onAttached(parent: MapNode) {
    controller.gestures.apply {
      clickListener?.let {
        addOnMapClickListener(it)
      }
      longClickListener?.let {
        addOnMapLongClickListener(it)
      }
    }
    mapViewportState.setMap(controller)
    mapEventCancelableHolder.apply {
      cancelAndSubscribeToMapLoaded(mapLoadedCallback)
      cancelAndSubscribeToMapIdle(mapIdleCallback)
      cancelAndSubscribeToMapLoadingError(mapLoadingErrorCallback)
      cancelAndSubscribeToStyleLoaded(styleLoadedCallback)
      cancelAndSubscribeToStyleDataLoaded(styleDataLoadedCallback)
      cancelAndSubscribeToSourceDataLoaded(sourceDataLoadedCallback)
      cancelAndSubscribeToSourceAdded(sourceAddedCallback)
      cancelAndSubscribeToSourceRemoved(sourceRemovedCallback)
      cancelAndSubscribeToStyleImageMissing(styleImageMissingCallback)
      cancelAndSubscribeToStyleImageRemoveUnused(styleImageRemoveUnusedCallback)
      cancelAndSubscribeToCameraChanged(onCameraChangedCallback)
      cancelAndSubscribeToRenderFrameStarted(renderFrameStartedCallback)
      cancelAndSubscribeToRenderFrameFinished(renderFrameFinishedCallback)
      cancelAndSubscribeToResourceRequest(resourceRequestCallback)
    }
  }

  override fun onRemoved(parent: MapNode) {
    cleanUp()
  }

  override fun onClear() {
    cleanUp()
  }

  private fun cleanUp() {
    controller.gestures.apply {
      clickListener?.let {
        removeOnMapClickListener(it)
      }
      longClickListener?.let {
        removeOnMapLongClickListener(it)
      }
    }
    mapViewportState.setMap(null)
    mapEventCancelableHolder.cancelAll()
  }

  override fun toString(): String {
    return "MapboxMapNode()"
  }
}

@OptIn(MapboxExperimental::class)
@JvmSynthetic
@Composable
internal fun MapboxMapComposeNode(
  gesturesSettings: GesturesSettings,
  mapViewportState: MapViewportState,
  onMapClickListener: OnMapClickListener?,
  onMapLongClickListener: OnMapLongClickListener?,
  mapEvents: MapEvents?,
) {
  val mapApplier = currentComposer.applier as MapApplier
  ComposeNode<MapboxMapNode, MapApplier>(
    factory = {
      MapboxMapNode(
        mapApplier.mapView,
        onMapClickListener,
        onMapLongClickListener,
        mapViewportState,
        mapEvents,
      )
    },
    update = {
      // input arguments updater
      set(gesturesSettings) {
        this.controller.gestures.applySettings(it)
      }
      update(mapViewportState) {
        this.mapViewportState = mapViewportState
      }
      update(onMapClickListener) { listener ->
        this.clickListener = listener
      }
      update(onMapLongClickListener) { listener ->
        this.longClickListener = listener
      }
      update(mapEvents) {
        this.mapLoadedCallback = mapEvents?.onMapLoaded
        this.mapIdleCallback = mapEvents?.onMapIdle
        this.mapLoadingErrorCallback = mapEvents?.onMapLoadingError
        this.styleLoadedCallback = mapEvents?.onStyleLoaded
        this.styleDataLoadedCallback = mapEvents?.onStyleDataLoaded
        this.sourceDataLoadedCallback = mapEvents?.onSourceDataLoaded
        this.sourceAddedCallback = mapEvents?.onSourceAdded
        this.sourceRemovedCallback = mapEvents?.onSourceRemoved
        this.styleImageMissingCallback = mapEvents?.onStyleImageMissing
        this.styleImageRemoveUnusedCallback = mapEvents?.onStyleImageRemoveUnused
        this.onCameraChangedCallback = mapEvents?.onCameraChanged
        this.renderFrameStartedCallback = mapEvents?.onRenderFrameStarted
        this.renderFrameFinishedCallback = mapEvents?.onRenderFrameFinished
        this.resourceRequestCallback = mapEvents?.onResourceRequest
      }
    }
  )
}
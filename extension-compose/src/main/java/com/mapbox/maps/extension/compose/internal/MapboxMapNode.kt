package com.mapbox.maps.extension.compose.internal

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.mapbox.common.Cancelable
import com.mapbox.maps.CameraChangedCallback
import com.mapbox.maps.MapIdleCallback
import com.mapbox.maps.MapInitOptions
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
import com.mapbox.maps.extension.compose.DefaultSettingsProvider
import com.mapbox.maps.extension.compose.MapEvents
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.attribution.generated.AttributionSettings
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.compass.generated.CompassSettings
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.logo.generated.LogoSettings
import com.mapbox.maps.plugin.logo.logo
import com.mapbox.maps.plugin.scalebar.generated.ScaleBarSettings
import com.mapbox.maps.plugin.scalebar.scalebar
import com.mapbox.maps.plugin.viewport.ViewportStatusObserver
import com.mapbox.maps.plugin.viewport.viewport

/**
 * MapboxMapNode to observe/update the input arguments of MapboxMap.
 */
@OptIn(MapboxExperimental::class)
private class MapboxMapNode(
  val controller: MapView,
  initialClickListener: OnMapClickListener,
  initialLongClickListener: OnMapLongClickListener,
  mapViewportState: MapViewportState,
  mapEvents: MapEvents?,
) : MapNode() {
  private var cancelable: Cancelable? = null
  private var mapEventCancelableHolder: MapEventCancelableHolder
  private val cameraChangedCallback = CameraChangedCallback {
    val cameraState = controller.getMapboxMap().cameraState
    mapViewportState.cameraState = cameraState
  }
  private val viewportStatusObserver = ViewportStatusObserver { from, to, reason ->
    mapViewportState.mapViewportStatus = to
    mapViewportState.mapViewportStatusChangedReason = reason
  }

  init {
    mapViewportState.setMap(controller)
    mapEventCancelableHolder = MapEventCancelableHolder(controller.getMapboxMap())
  }

  var mapViewportState = mapViewportState
    set(value) {
      if (value == field) return
      field.setMap(null)
      field = value
      value.setMap(controller)
    }

  var clickListener: OnMapClickListener = initialClickListener
    set(value) {
      controller.gestures.apply {
        removeNonDefaultOnClickListener(field)
        addNonDefaultOnClickListener(value)
      }
      field = value
    }
  var longClickListener: OnMapLongClickListener = initialLongClickListener
    set(value) {
      controller.gestures.apply {
        removeNonDefaultOnLongClickListener(field)
        addNonDefaultOnLongClickListener(value)
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

  override fun onAttached() {
    controller.gestures.apply {
      addNonDefaultOnClickListener(clickListener)
      addNonDefaultOnLongClickListener(longClickListener)
    }
    mapViewportState.cameraState = controller.getMapboxMap().cameraState
    controller.viewport.addStatusObserver(viewportStatusObserver)
    cancelable = controller.getMapboxMap().subscribeCameraChanged(cameraChangedCallback)
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

  override fun onRemoved() {
    cleanUp()
  }

  override fun onClear() {
    cleanUp()
  }

  private fun cleanUp() {
    controller.gestures.apply {
      removeNonDefaultOnClickListener(clickListener)
      removeNonDefaultOnLongClickListener(longClickListener)
    }
    cancelable?.cancel()
    mapEventCancelableHolder.cancelAll()
    controller.viewport.removeStatusObserver(viewportStatusObserver)
  }
}

@JvmSynthetic
@Composable
internal fun MapboxMapComposeNode(
  mapInitOptionsFactory: (Context) -> MapInitOptions,
  attributionSettings: AttributionSettings,
  compassSettings: CompassSettings,
  gesturesSettings: GesturesSettings,
  locationComponentSettings: LocationComponentSettings,
  logoSettings: LogoSettings,
  scaleBarSettings: ScaleBarSettings,
  mapViewportState: MapViewportState,
  onMapClickListener: OnMapClickListener,
  onMapLongClickListener: OnMapLongClickListener,
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
      update(mapInitOptionsFactory) {
        throw IllegalStateException(
          """
          Mutating MapInitOptions during composition is not allowed.
          """.trimIndent()
        )
      }
      set(attributionSettings) {
        this.controller.attribution.applySettings(it)
      }
      set(compassSettings) {
        this.controller.compass.applySettings(it)
      }
      set(gesturesSettings) {
        this.controller.gestures.applySettings(it)
      }
      set(locationComponentSettings) {
        this.controller.location.applySettings(it)
      }
      set(logoSettings) {
        this.controller.logo.applySettings(it)
      }
      set(scaleBarSettings) {
        this.controller.scalebar.applySettings(it)
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

private fun GesturesPlugin.addNonDefaultOnClickListener(onMapClickListener: OnMapClickListener) {
  // Avoid addOnMapClickListener when the default instance is used.
  if (onMapClickListener !== DefaultSettingsProvider.defaultOnClickListener) {
    addOnMapClickListener(onMapClickListener)
  }
}

private fun GesturesPlugin.removeNonDefaultOnClickListener(onMapClickListener: OnMapClickListener) {
  // Avoid removeOnMapClickListener when the default instance is used.
  if (onMapClickListener !== DefaultSettingsProvider.defaultOnClickListener) {
    removeOnMapClickListener(onMapClickListener)
  }
}

private fun GesturesPlugin.addNonDefaultOnLongClickListener(onMapLongClickListener: OnMapLongClickListener) {
  // Avoid addOnMapLongClickListener when the default instance is used.
  if (onMapLongClickListener !== DefaultSettingsProvider.defaultOnLongClickListener) {
    addOnMapLongClickListener(onMapLongClickListener)
  }
}

private fun GesturesPlugin.removeNonDefaultOnLongClickListener(onMapLongClickListener: OnMapLongClickListener) {
  // Avoid removeOnMapLongClickListener when the default instance is used.
  if (onMapLongClickListener !== DefaultSettingsProvider.defaultOnLongClickListener) {
    removeOnMapLongClickListener(onMapLongClickListener)
  }
}
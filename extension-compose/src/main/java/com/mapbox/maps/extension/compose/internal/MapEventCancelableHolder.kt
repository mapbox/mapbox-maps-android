package com.mapbox.maps.extension.compose.internal

import com.mapbox.common.Cancelable
import com.mapbox.maps.CameraChangedCallback
import com.mapbox.maps.MapIdleCallback
import com.mapbox.maps.MapLoadedCallback
import com.mapbox.maps.MapLoadingErrorCallback
import com.mapbox.maps.MapboxMap
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

internal class MapEventCancelableHolder constructor(private val mapboxMap: MapboxMap) {

  private var mapLoadedCancelable: Cancelable? = null
  private var mapIdleCancelable: Cancelable? = null
  private var mapLoadingErrorCancelable: Cancelable? = null
  private var styleLoadedCancelable: Cancelable? = null
  private var styleDataLoadedCancelable: Cancelable? = null
  private var sourceDataLoadedCancelable: Cancelable? = null
  private var sourceAddedCancelable: Cancelable? = null
  private var sourceRemovedCancelable: Cancelable? = null
  private var styleImageMissingCancelable: Cancelable? = null
  private var styleImageRemoveUnusedCancelable: Cancelable? = null
  private var cameraChangedCancelable: Cancelable? = null
  private var renderFrameStartedCancelable: Cancelable? = null
  private var renderFrameFinishedCancelable: Cancelable? = null
  private var resourceRequestCancelable: Cancelable? = null

  fun cancelAndSubscribeToMapLoaded(callback: MapLoadedCallback?) {
    mapLoadedCancelable?.cancel()
    mapLoadedCancelable = callback?.let(mapboxMap::subscribeMapLoaded)
  }

  fun cancelAndSubscribeToMapIdle(callback: MapIdleCallback?) {
    mapIdleCancelable?.cancel()
    mapIdleCancelable = callback?.let(mapboxMap::subscribeMapIdle)
  }

  fun cancelAndSubscribeToMapLoadingError(callback: MapLoadingErrorCallback?) {
    mapLoadingErrorCancelable?.cancel()
    mapLoadingErrorCancelable = callback?.let(mapboxMap::subscribeMapLoadingError)
  }

  fun cancelAndSubscribeToStyleLoaded(callback: StyleLoadedCallback?) {
    styleLoadedCancelable?.cancel()
    styleLoadedCancelable = callback?.let(mapboxMap::subscribeStyleLoaded)
  }

  fun cancelAndSubscribeToStyleDataLoaded(callback: StyleDataLoadedCallback?) {
    styleDataLoadedCancelable?.cancel()
    styleDataLoadedCancelable = callback?.let(mapboxMap::subscribeStyleDataLoaded)
  }

  fun cancelAndSubscribeToSourceDataLoaded(callback: SourceDataLoadedCallback?) {
    sourceDataLoadedCancelable?.cancel()
    sourceDataLoadedCancelable = callback?.let(mapboxMap::subscribeSourceDataLoaded)
  }

  fun cancelAndSubscribeToSourceAdded(callback: SourceAddedCallback?) {
    sourceAddedCancelable?.cancel()
    sourceAddedCancelable = callback?.let(mapboxMap::subscribeSourceAdded)
  }

  fun cancelAndSubscribeToSourceRemoved(callback: SourceRemovedCallback?) {
    sourceRemovedCancelable?.cancel()
    sourceRemovedCancelable = callback?.let(mapboxMap::subscribeSourceRemoved)
  }

  fun cancelAndSubscribeToStyleImageMissing(callback: StyleImageMissingCallback?) {
    styleImageMissingCancelable?.cancel()
    styleImageMissingCancelable = callback?.let(mapboxMap::subscribeStyleImageMissing)
  }

  fun cancelAndSubscribeToStyleImageRemoveUnused(callback: StyleImageRemoveUnusedCallback?) {
    styleImageRemoveUnusedCancelable?.cancel()
    styleImageRemoveUnusedCancelable = callback?.let(mapboxMap::subscribeStyleImageRemoveUnused)
  }

  fun cancelAndSubscribeToCameraChanged(callback: CameraChangedCallback?) {
    cameraChangedCancelable?.cancel()
    cameraChangedCancelable = callback?.let(mapboxMap::subscribeCameraChanged)
  }

  fun cancelAndSubscribeToRenderFrameStarted(callback: RenderFrameStartedCallback?) {
    renderFrameStartedCancelable?.cancel()
    renderFrameStartedCancelable = callback?.let(mapboxMap::subscribeRenderFrameStarted)
  }

  fun cancelAndSubscribeToRenderFrameFinished(callback: RenderFrameFinishedCallback?) {
    renderFrameFinishedCancelable?.cancel()
    renderFrameFinishedCancelable = callback?.let(mapboxMap::subscribeRenderFrameFinished)
  }

  fun cancelAndSubscribeToResourceRequest(callback: ResourceRequestCallback?) {
    resourceRequestCancelable?.cancel()
    resourceRequestCancelable = callback?.let(mapboxMap::subscribeResourceRequest)
  }

  fun cancelAll() {
    mapLoadedCancelable?.cancel()
    mapIdleCancelable?.cancel()
    mapLoadingErrorCancelable?.cancel()
    styleLoadedCancelable?.cancel()
    styleDataLoadedCancelable?.cancel()
    sourceDataLoadedCancelable?.cancel()
    sourceAddedCancelable?.cancel()
    sourceRemovedCancelable?.cancel()
    styleImageMissingCancelable?.cancel()
    styleImageRemoveUnusedCancelable?.cancel()
    cameraChangedCancelable?.cancel()
    renderFrameStartedCancelable?.cancel()
    renderFrameFinishedCancelable?.cancel()
    resourceRequestCancelable?.cancel()
  }
}
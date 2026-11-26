package com.mapbox.maps.debugoptions

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.mapbox.annotation.MapboxExperimental
import com.mapbox.common.Cancelable
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

internal class DebugOptionsController {
  private val mapView: MapView
  private val mapboxMap: MapboxMap
  private val cameraDebugViewProvider: (Context) -> CameraDebugView
  private val paddingDebugViewProvider: (Context) -> PaddingDebugView

  private var cameraDebugView: CameraDebugView? = null
  private var paddingDebugView: PaddingDebugView? = null
  private var cameraSubscriptionCancelable: Cancelable? = null
  var started by Delegates.observable(false) {
    _, wasStarted, started ->
    if (wasStarted != started) {
      updateCameraSubscriptionIfNeeded()
    }
  }

  var options: Set<MapViewDebugOptions> = setOf()
    set(newValue) {
      field = newValue
      mapboxMap.debugOptions = newValue.nativeDebugOptions
      toggleMapViewDebugOptionsIfNeeded()
      updateCameraSubscriptionIfNeeded()
    }

  constructor(
    mapView: MapView,
  ) : this(mapView, { CameraDebugView(it) }, { PaddingDebugView(it) })

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  constructor(
    mapView: MapView,
    cameraDebugViewProvider: (Context) -> CameraDebugView,
    paddingDebugViewProvider: (Context) -> PaddingDebugView,
  ) {
    this.mapView = mapView
    this.mapboxMap = mapView.mapboxMap
    this.cameraDebugViewProvider = cameraDebugViewProvider
    this.paddingDebugViewProvider = paddingDebugViewProvider
  }

  @OptIn(MapboxExperimental::class)
  private fun updateCameraSubscriptionIfNeeded() {
    val needsCameraUpdates =
      options.contains(MapViewDebugOptions.CAMERA) || options.contains(MapViewDebugOptions.PADDING)
    if (started && needsCameraUpdates && cameraSubscriptionCancelable == null) {
      cameraSubscriptionCancelable = mapboxMap.subscribeCameraChangedCoalesced {
        mapView.mapScope.launch(Dispatchers.Main.immediate) {
          cameraDebugView?.update(it.cameraState)
          paddingDebugView?.update(it.cameraState.padding)
        }
      }
    } else {
      cameraSubscriptionCancelable?.cancel()
      cameraSubscriptionCancelable = null
    }
  }

  private fun toggleMapViewDebugOptionsIfNeeded() {
    val showCamera = options.contains(MapViewDebugOptions.CAMERA)
    val showPadding = options.contains(MapViewDebugOptions.PADDING)
    // Read on the calling (Mapbox) thread: the debug views are only touched on main below.
    val cameraState = if (showCamera || showPadding) mapboxMap.cameraState else null
    // Non-null whenever either option is shown, so the checks below match showCamera and
    // showPadding; they let the compiler smart-cast cameraState.
    if (showCamera && cameraState != null) {
      mapView.mapScope.launch(Dispatchers.Main.immediate) {
        if (cameraDebugView == null) {
          cameraDebugView = cameraDebugViewProvider(mapView.context).also {
            mapView.addView(it)
            it.update(cameraState)
          }
        }
      }
    } else {
      mapView.mapScope.launch(Dispatchers.Main.immediate) {
        cameraDebugView?.let {
          mapView.removeView(it)
          cameraDebugView = null
        }
      }
    }

    if (showPadding && cameraState != null) {
      mapView.mapScope.launch(Dispatchers.Main.immediate) {
        if (paddingDebugView == null) {
          paddingDebugView = paddingDebugViewProvider(mapView.context).also {
            mapView.addView(it)
            it.update(cameraState.padding)
          }
        }
      }
    } else {
      mapView.mapScope.launch(Dispatchers.Main.immediate) {
        paddingDebugView?.let {
          mapView.removeView(it)
          paddingDebugView = null
        }
      }
    }
  }
}
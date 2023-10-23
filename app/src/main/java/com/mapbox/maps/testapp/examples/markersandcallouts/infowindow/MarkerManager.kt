package com.mapbox.maps.testapp.examples.markersandcallouts.infowindow

import android.view.View
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.plugin.gestures.removeOnMapClickListener
import com.mapbox.maps.viewannotation.OnViewAnnotationUpdatedListener
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.math.abs

/**
 * Manager class helping to control the [Marker]s.
 */
class MarkerManager(
  private val mapView: MapView
) : OnPointAnnotationClickListener, OnMapClickListener {

  private val pointAnnotationManager: PointAnnotationManager =
    mapView.annotations.createPointAnnotationManager(
      AnnotationConfig(
        layerId = LAYER_ID
      )
    )

  // using copy on write just in case as potentially remove may be called while we're iterating in on click listener
  private val markerList = CopyOnWriteArrayList<Marker>()

  init {
    pointAnnotationManager.addClickListener(this)
    // by adding regular map click listener we implement deselecting all info windows on map click
    // in legacy code it was controlled by flag in API
    mapView.mapboxMap.addOnMapClickListener(this)
  }

  override fun onAnnotationClick(annotation: PointAnnotation): Boolean {
    markerList.forEach { marker ->
      if (marker.pointAnnotation == annotation) {
        selectMarker(marker, deselectIfSelected = true)
      }
    }
    return true
  }

  override fun onMapClick(point: Point): Boolean {
    markerList.forEach(::deselectMarker)
    return true
  }

  fun addMarker(marker: Marker): Marker {
    marker.prepareAnnotationMarker(pointAnnotationManager, LAYER_ID)
    marker.prepareViewAnnotation(mapView)
    markerList.add(marker)
    // do not show info window by default
    deselectMarker(marker)
    return marker
  }

  fun removeMarker(marker: Marker) {
    if (!marker.prepared) {
      return
    }
    markerList.remove(marker)
    mapView.viewAnnotationManager.removeViewAnnotation(marker.viewAnnotation)
    pointAnnotationManager.delete(marker.pointAnnotation)
  }

  fun selectMarker(marker: Marker, deselectIfSelected: Boolean = false) {
    if (marker.isSelected()) {
      if (deselectIfSelected) {
        deselectMarker(marker)
      }
      return
    }
    // Need to deselect any currently selected annotation first
    markerList.forEach(::deselectMarker)
    adjustViewAnnotationXOffset(marker)
    mapView.viewAnnotationManager.updateViewAnnotation(
      marker.viewAnnotation,
      viewAnnotationOptions {
        selected(true)
      }
    )
    marker.viewAnnotation.visibility = View.VISIBLE
  }

  private fun deselectMarker(marker: Marker) {
    mapView.viewAnnotationManager.updateViewAnnotation(
      marker.viewAnnotation,
      viewAnnotationOptions {
        selected(false)
        marker.anchor?.let {
          variableAnchors(
            listOf(it.toBuilder().offsetX(0.0).build())
          )
        }
      }
    )
    marker.viewAnnotation.visibility = View.INVISIBLE
  }

  fun destroy() {
    markerList.forEach(::removeMarker)
    pointAnnotationManager.removeClickListener(this)
    mapView.mapboxMap.removeOnMapClickListener(this)
  }

  // adjust offsetX to fit on the screen if the info window is shown near screen edge
  private fun adjustViewAnnotationXOffset(marker: Marker) {
    mapView.viewAnnotationManager.addOnViewAnnotationUpdatedListener(
      object : OnViewAnnotationUpdatedListener {
        override fun onViewAnnotationPositionUpdated(
          view: View,
          leftTopCoordinate: ScreenCoordinate,
          width: Double,
          height: Double,
        ) {
          if (view == marker.viewAnnotation) {
            updateOffsetX(marker, leftTopCoordinate, width)
            mapView.viewAnnotationManager.removeOnViewAnnotationUpdatedListener(this)
          }
        }
      })
  }

  private fun updateOffsetX(marker: Marker, leftTop: ScreenCoordinate, width: Double) {
    val resultOffsetX = if (leftTop.x < 0) {
      abs(leftTop.x) + ADDITIONAL_EDGE_PADDING_PX
    } else if (leftTop.x + width > mapView.mapboxMap.getSize().width) {
      mapView.mapboxMap.getSize().width - leftTop.x - width - ADDITIONAL_EDGE_PADDING_PX
    } else {
      0.0
    }
    val anchor = marker.anchor?.toBuilder()
      ?.offsetX(resultOffsetX)
      ?.build()

    mapView.viewAnnotationManager.updateViewAnnotation(
      marker.viewAnnotation,
      viewAnnotationOptions {
        if (anchor != null) {
          variableAnchors(listOf(anchor))
        }
      }
    )
  }

  private fun Marker.isSelected() =
    mapView.viewAnnotationManager.getViewAnnotationOptions(viewAnnotation)?.selected == true

  private companion object {
    // additional padding when offsetting view near the screen edge
    const val ADDITIONAL_EDGE_PADDING_PX = 20.0
    const val LAYER_ID = "annotation-layer"
  }
}
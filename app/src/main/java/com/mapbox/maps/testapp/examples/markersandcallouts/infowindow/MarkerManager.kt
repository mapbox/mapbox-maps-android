package com.mapbox.maps.testapp.examples.markersandcallouts.infowindow

import android.view.View
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.ScreenCoordinate
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

  private val pointAnnotationManager: PointAnnotationManager = mapView.annotations.createPointAnnotationManager()
  // using copy on write just in case as potentially remove may be called while we're iterating in on click listener
  private val markerList = CopyOnWriteArrayList<Marker>()

  init {
    pointAnnotationManager.addClickListener(this)
    // by adding regular map click listener we implement deselecting all info windows on map click
    // in legacy code it was controlled by flag in API
    mapView.getMapboxMap().addOnMapClickListener(this)
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
    deselectMarkers()
    return true
  }

  fun addMarker(marker: Marker): Marker {
    marker.prepareAnnotationMarker(pointAnnotationManager)
    marker.prepareViewAnnotation(mapView.viewAnnotationManager)
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
    deselectMarkers()
    mapView.viewAnnotationManager.updateViewAnnotation(
      marker.viewAnnotation,
      viewAnnotationOptions {
        selected(true)
      }
    )
    adjustViewAnnotationOffsets(marker.viewAnnotation)
    marker.viewAnnotation.visibility = View.VISIBLE
  }

  fun deselectMarkers() {
    markerList.forEach { marker ->
      deselectViewAnnotation(marker.viewAnnotation)
    }
  }

  fun deselectMarker(marker: Marker) {
    deselectViewAnnotation(marker.viewAnnotation)
  }

  fun destroy() {
    markerList.forEach { removeMarker(it) }
    pointAnnotationManager.removeClickListener(this)
    mapView.getMapboxMap().removeOnMapClickListener(this)
  }

  private fun deselectViewAnnotation(view: View) {
    mapView.viewAnnotationManager.updateViewAnnotation(
      view,
      viewAnnotationOptions {
        selected(false)
        // reset offset to default value
        offsetX(0)
      }
    )
    view.visibility = View.INVISIBLE
  }

  // if info window view is shown near screen edge - we adjust offsetX so that it fully appears on the screen
  private fun adjustViewAnnotationOffsets(viewToAdjust: View) {
    mapView.viewAnnotationManager.addOnViewAnnotationUpdatedListener(object : OnViewAnnotationUpdatedListener {
      override fun onViewAnnotationPositionUpdated(
        view: View,
        leftTopCoordinate: ScreenCoordinate,
        width: Int,
        height: Int
      ) {
        if (view == viewToAdjust) {
          updateOffsetX(viewToAdjust, leftTopCoordinate, width)
          mapView.viewAnnotationManager.removeOnViewAnnotationUpdatedListener(this)
        }
      }

      override fun onViewAnnotationVisibilityUpdated(view: View, visible: Boolean) {
        // not needed
      }
    })
  }

  private fun updateOffsetX(view: View, leftTop: ScreenCoordinate, width: Int) {
    var resultOffsetX = 0
    if (leftTop.x < 0) {
      resultOffsetX = abs(leftTop.x.toInt()) + ADDITIONAL_EDGE_PADDING_PX
    } else if (leftTop.x + width > mapView.getMapboxMap().getSize().width) {
      resultOffsetX = (mapView.getMapboxMap().getSize().width - leftTop.x - width - ADDITIONAL_EDGE_PADDING_PX).toInt()
    }
    mapView.viewAnnotationManager.updateViewAnnotation(
      view,
      viewAnnotationOptions {
        offsetX(resultOffsetX)
      }
    )
  }

  private fun Marker.isSelected() =
    mapView.viewAnnotationManager.getViewAnnotationOptionsByView(viewAnnotation)?.selected == true

  private companion object {
    // additional padding when offsetting view near the screen edge
    const val ADDITIONAL_EDGE_PADDING_PX = 20
  }
}
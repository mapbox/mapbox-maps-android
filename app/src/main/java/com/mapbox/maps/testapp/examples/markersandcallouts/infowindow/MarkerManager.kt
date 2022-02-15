package com.mapbox.maps.testapp.examples.markersandcallouts.infowindow

import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewTreeObserver
import com.mapbox.common.Logger
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.plugin.gestures.removeOnMapClickListener
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.math.abs

/**
 * Manager class helping to control the [Marker]s.
 */
class MarkerManager(
  private val mapView: MapView
): OnPointAnnotationClickListener, OnMapClickListener {

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
    if (mapView.viewAnnotationManager.getViewAnnotationOptionsByView(marker.viewAnnotation)?.selected == true) {
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
    markerList.clear()
    pointAnnotationManager.removeClickListener(this)
    mapView.getMapboxMap().removeOnMapClickListener(this)
  }

  private fun deselectViewAnnotation(view: View) {
    mapView.viewAnnotationManager.updateViewAnnotation(
      view,
      viewAnnotationOptions {
        selected(false)
      }
    )
    view.visibility = View.INVISIBLE
  }

  // if info window view is shown near screen edge - we adjust offsetX so that it fully appears on the screen
  private fun adjustViewAnnotationOffsets(view: View) {
    Logger.e("KIRYLDD", "outer W = ${view.measuredWidth}, H = ${view.measuredHeight}")
    // fixed dimensions are used - we could calculate right now
    if (view.measuredWidth > 0 && view.measuredHeight > 0) {
      updateOffsetX(view)
    } else {
      // TODO this could be simplified with some additional API exposed
      // wrap content dimensions are used - we need to calculate offset when dimensions are measured
      var onAttachStateChangeListener: View.OnAttachStateChangeListener? = null
      val globalLayoutListener = object: ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
          Logger.e("KIRYLDD", "inner W = ${view.measuredWidth}, H = ${view.measuredHeight}")
          if (view.measuredWidth > 0 && view.measuredHeight > 0) {
            updateOffsetX(view)
          }
          view.viewTreeObserver.removeOnGlobalLayoutListener(this)
          view.removeOnAttachStateChangeListener(onAttachStateChangeListener)
        }
      }
      onAttachStateChangeListener = object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(v: View?) {
          view.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
        }

        override fun onViewDetachedFromWindow(v: View?) {
          view.viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
        }
      }
      view.addOnAttachStateChangeListener(onAttachStateChangeListener)
    }
  }

  private fun updateOffsetX(view: View) {
    val currentOffsetX = mapView.viewAnnotationManager.getViewAnnotationOptionsByView(view)?.offsetX ?: 0
    var resultOffsetX = currentOffsetX
    val res = IntArray(2)
    view.getLocationOnScreen(res)
    Logger.e("KIRYLDD", "updateOffsetX: view.translationX=${res[0]}, map width=${mapView.getMapboxMap().getSize().width}")
    if (view.translationX < 0) {
      resultOffsetX += abs(view.translationX.toInt())
    } else if (view.translationX + view.measuredWidth > mapView.getMapboxMap().getSize().width) {
      resultOffsetX += (view.translationX + view.measuredWidth - mapView.getMapboxMap().getSize().width).toInt()
    }
    if (resultOffsetX != currentOffsetX) {
      mapView.viewAnnotationManager.updateViewAnnotation(
        view,
        viewAnnotationOptions {
          offsetX(currentOffsetX )
        }
      )
    }
  }
}
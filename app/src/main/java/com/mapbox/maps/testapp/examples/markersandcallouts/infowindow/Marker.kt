package com.mapbox.maps.testapp.examples.markersandcallouts.infowindow

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.ViewAnnotationAnchorConfig
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.testapp.R
import com.mapbox.maps.viewannotation.annotatedLayerFeature
import com.mapbox.maps.viewannotation.annotationAnchor
import com.mapbox.maps.viewannotation.viewAnnotationOptions

/**
 * Class representing a marker that contains marker icon and view annotation on top of it.
 */
data class Marker(
  val position: Point,
  val icon: Bitmap,
  val title: String? = null,
  val snippet: String? = null,
) {

  init {
    if (title == null && snippet == null) {
      throw RuntimeException("Marker should have either title or snippet!")
    }
  }

  var anchor: ViewAnnotationAnchorConfig? = null

  private var layerId: String = ""
  internal var prepared = false
    private set

  internal lateinit var pointAnnotation: PointAnnotation
    private set
  internal lateinit var viewAnnotation: View
    private set

  internal fun prepareAnnotationMarker(pointAnnotationManager: PointAnnotationManager, layerId: String) {
    val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
      .withPoint(position)
      .withIconImage(icon)
      .withIconAnchor(IconAnchor.BOTTOM)
    pointAnnotation = pointAnnotationManager.create(pointAnnotationOptions)
    this.layerId = layerId
  }

  internal fun prepareViewAnnotation(mapView: MapView) {
    viewAnnotation = LayoutInflater.from(mapView.context)
      .inflate(R.layout.item_legacy_callout_view, mapView.rootView as ViewGroup, false)
    viewAnnotation.findViewById<TextView>(R.id.infowindow_title).text = title
    viewAnnotation.findViewById<TextView>(R.id.infowindow_description).text = snippet

    mapView.viewAnnotationManager.addViewAnnotation(
      view = viewAnnotation,
      options = viewAnnotationOptions {
        // attach view annotation to the feature/layer ids of the annotation
        annotatedLayerFeature(layerId) {
          featureId(pointAnnotation.id)
        }
        annotationAnchor {
          // same anchor with the annotation
          anchor(ViewAnnotationAnchor.BOTTOM)
          // needed to display info window above the marker
          offsetY((pointAnnotation.iconImageBitmap?.height!! + MARKER_PADDING_PX).toDouble())
        }
      }.also {
        anchor = it.variableAnchors!!.first()
      }
    )
    prepared = true
  }

  private companion object {
    // padding between marker and info window
    const val MARKER_PADDING_PX = 10
  }
}
package com.mapbox.maps.testapp.examples.markersandcallouts.infowindow

import android.graphics.Bitmap
import android.view.View
import android.widget.TextView
import com.mapbox.geojson.Point
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.testapp.R
import com.mapbox.maps.viewannotation.ViewAnnotationManager
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

  internal var prepared = false
    private set

  internal lateinit var pointAnnotation: PointAnnotation
    private set
  internal lateinit var viewAnnotation: View
    private set

  internal fun prepareAnnotationMarker(pointAnnotationManager: PointAnnotationManager) {
    val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
      .withPoint(position)
      .withIconImage(icon)
      .withIconAnchor(IconAnchor.BOTTOM)
    pointAnnotation = pointAnnotationManager.create(pointAnnotationOptions)
  }

  internal fun prepareViewAnnotation(viewAnnotationManager: ViewAnnotationManager) {
    viewAnnotation = viewAnnotationManager.addViewAnnotation(
      resId = R.layout.item_legacy_callout_view,
      options = viewAnnotationOptions {
        geometry(position)
        // make info window automatically disappear when marker will disappear (e.g. due to a collision)
        associatedFeatureId(pointAnnotation.featureIdentifier)
        // align anchor to be the same as for the marker
        anchor(ViewAnnotationAnchor.BOTTOM)
        // needed to display info window above the marker
        offsetY(pointAnnotation.iconImageBitmap?.height!! + MARKER_PADDING_PX)
      }
    )
    viewAnnotation.findViewById<TextView>(R.id.infowindow_title).text = title
    viewAnnotation.findViewById<TextView>(R.id.infowindow_description).text = snippet
    prepared = true
  }

  private companion object {
    // padding between marker and info window
    const val MARKER_PADDING_PX = 10
  }
}
package com.mapbox.maps.testapp.examples.markersandcallouts

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.postDelayed
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.image.image
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.sources.getSourceAs
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.plugin.gestures.addOnMapLongClickListener
import com.mapbox.maps.plugin.viewannotation.ViewAnnotationPlugin
import com.mapbox.maps.plugin.viewannotation.viewAnnotation
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.utils.BitmapUtils

/**
 * Example how to add view annotations to the map.
 *
 * Specifically view annotations will be associated with marker icons
 * showcasing how to implement something similar to MarkerView from Maps v9.
 */
class ViewAnnotationShowcase : AppCompatActivity(), OnMapClickListener, OnMapLongClickListener {

  private lateinit var viewAnnotationPlugin: ViewAnnotationPlugin
  private lateinit var pointAnnotationManager: PointAnnotationManager

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(
      this,
      MapInitOptions(
        context = this,
        textureView = false
      )
    )
    setContentView(mapView)

    viewAnnotationPlugin = mapView.viewAnnotation
    pointAnnotationManager = mapView.annotations.createPointAnnotationManager(mapView)
    mapView.getMapboxMap().loadStyle(
      styleExtension = style(Style.MAPBOX_STREETS) {
        +image(BLUE_ICON_ID) {
          bitmap(BitmapFactory.decodeResource(
            resources,
            R.drawable.blue_marker_view)
          )
        }
      }
    ) {
      mapView.getMapboxMap().addOnMapClickListener(this)
      mapView.getMapboxMap().addOnMapLongClickListener(this)
    }
  }

  override fun onMapLongClick(point: Point): Boolean {
    addMarker(point)
    addViewAnnotation(point)
    return true
  }

  override fun onMapClick(point: Point): Boolean {

  }

  private fun addMarker(point: Point) {
    val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(point.longitude(), point.latitude()))
      .withIconImage(BLUE_ICON_ID)
    val s = pointAnnotationManager.create(pointAnnotationOptions)
    s.id
  }

  private fun addViewAnnotation(point: Point) {
    viewAnnotationPlugin.addViewAnnotation(
      R.layout.item_callout_view,
      ViewAnnotationOptions.Builder()
        .geometry(point)
        .allowViewAnnotationsCollision(false)
        .anchor(ViewAnnotationAnchor.TOP_RIGHT)
        .build()
    ) {
      it.visibility = View.GONE
      it.findViewById<TextView>(R.id.textNativeView).text =
        point.coordinates().joinToString(", ")
      it.findViewById<ImageView>(R.id.closeNativeView).setOnClickListener { view ->
        viewAnnotationPlugin.removeViewAnnotation(view)
      }
    }
  }

  companion object {
    private const val BLUE_ICON_ID = "blue"
    private const val SOURCE_ID = "source_id"
    private const val LAYER_ID = "layer_id"
  }
}
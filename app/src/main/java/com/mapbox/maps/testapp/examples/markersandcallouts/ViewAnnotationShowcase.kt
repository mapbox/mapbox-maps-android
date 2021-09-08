package com.mapbox.maps.testapp.examples.markersandcallouts

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.image.image
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.sources.getSourceAs
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.plugin.gestures.addOnMapLongClickListener
import com.mapbox.maps.plugin.viewannotation.ViewAnnotationPlugin
import com.mapbox.maps.plugin.viewannotation.viewAnnotation
import com.mapbox.maps.testapp.R

/**
 * Example how to add view annotations to the map.
 *
 * Specifically view annotations will be associated with marker icons
 * showcasing how to implement something similar to MarkerView from Maps v9.
 */
class ViewAnnotationShowcase : AppCompatActivity(), OnMapClickListener, OnMapLongClickListener {

  private lateinit var mapboxMap: MapboxMap
  private lateinit var viewAnnotationPlugin: ViewAnnotationPlugin
  private lateinit var style: Style
  private val pointList = mutableListOf<Feature>()
  private var id = "0"

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

    mapboxMap = mapView.getMapboxMap()
    mapboxMap.loadStyle(
      styleExtension = style(Style.MAPBOX_STREETS) {
        +image(BLUE_ICON_ID) {
          bitmap(BitmapFactory.decodeResource(
            resources,
            R.drawable.blue_marker_view)
          )
        }
        +geoJsonSource(SOURCE_ID)
        +symbolLayer(LAYER_ID, SOURCE_ID) {
          iconImage(BLUE_ICON_ID)
          iconAnchor(IconAnchor.BOTTOM)
          iconAllowOverlap(true)
        }
      }
    ) {
      style = it
      mapView.getMapboxMap().addOnMapClickListener(this)
      mapView.getMapboxMap().addOnMapLongClickListener(this)
    }
  }

  override fun onMapLongClick(point: Point): Boolean {
    val markerId = addMarker(point)
    addViewAnnotation(point, markerId)
    return true
  }

  override fun onMapClick(point: Point): Boolean {
    mapboxMap.queryRenderedFeatures(
      mapboxMap.pixelForCoordinate(point),
      RenderedQueryOptions(listOf(LAYER_ID), null)
    ) {
      if (it.isValue && it.value?.size!! > 0) {
        it.value?.get(0)?.feature?.let { feature ->
          if (feature.id() != null) {
            val annotationView = viewAnnotationPlugin.findViewAnnotation(feature.id()!!)
            annotationView?.visibility = View.VISIBLE
          }
        }
      }
    }
    return true
  }

  private fun addMarker(point: Point): String {
    val intId = id.toInt()
    id = (intId + 1).toString()
    pointList.add(Feature.fromGeometry(point, null, id))
    val featureCollection = FeatureCollection.fromFeatures(pointList)
    style.getSourceAs<GeoJsonSource>(SOURCE_ID)?.featureCollection(featureCollection)
    return id
  }

  private fun addViewAnnotation(point: Point, markerId: String) {
    viewAnnotationPlugin.addViewAnnotation(
      R.layout.item_callout_view,
      ViewAnnotationOptions.Builder()
        .geometry(point)
        .iconIdentifier(markerId)
        .allowViewAnnotationsCollision(true)
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
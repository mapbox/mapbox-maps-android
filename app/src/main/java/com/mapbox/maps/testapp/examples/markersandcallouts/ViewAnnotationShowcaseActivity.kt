package com.mapbox.maps.testapp.examples.markersandcallouts

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Button
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
import com.mapbox.maps.testapp.R
import com.mapbox.maps.viewannotation.ViewAnnotationManager

/**
 * Example how to add view annotations to the map.
 *
 * Specifically view annotations will be associated with marker icons
 * showcasing how to implement functionality similar to MarkerView from Maps v9.
 */
class ViewAnnotationShowcaseActivity : AppCompatActivity(), OnMapClickListener, OnMapLongClickListener {

  private lateinit var mapboxMap: MapboxMap
  private lateinit var viewAnnotationManager: ViewAnnotationManager
  private lateinit var style: Style
  private val pointList = mutableListOf<Feature>()
  private var markerId = "0"

  private var markerWidth = 0
  private var markerHeight = 0

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

    viewAnnotationManager = mapView.viewAnnotationManager

    mapboxMap = mapView.getMapboxMap()
    mapboxMap.loadStyle(
      styleExtension = style(Style.MAPBOX_STREETS) {
        +image(BLUE_ICON_ID) {
          val bitmap = BitmapFactory.decodeResource(resources, R.drawable.blue_marker_view)
          markerWidth = bitmap.width
          markerHeight = bitmap.height
          bitmap(bitmap)
        }
        +geoJsonSource(SOURCE_ID)
        +symbolLayer(LAYER_ID, SOURCE_ID) {
          iconImage(BLUE_ICON_ID)
          iconAnchor(IconAnchor.BOTTOM)
          iconAllowOverlap(false)
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
            viewAnnotationManager.getViewAnnotationByFeatureId(feature.id()!!)?.let { viewAnnotation ->
              if (viewAnnotation.visibility == View.VISIBLE) {
                viewAnnotation.visibility = View.GONE
                viewAnnotationManager.updateViewAnnotation(
                  viewAnnotation,
                  ViewAnnotationOptions.Builder()
                    .visible(false)
                    .build()
                )
              } else {
                viewAnnotation.visibility = View.VISIBLE
                viewAnnotationManager.updateViewAnnotation(
                  viewAnnotation,
                  ViewAnnotationOptions.Builder()
                    .visible(true)
                    .build()
                )
              }
            }
          }
        }
      }
    }
    return true
  }

  private fun addMarker(point: Point): String {
    val intId = markerId.toInt()
    markerId = (intId + 1).toString()
    pointList.add(Feature.fromGeometry(point, null, markerId))
    val featureCollection = FeatureCollection.fromFeatures(pointList)
    style.getSourceAs<GeoJsonSource>(SOURCE_ID)?.featureCollection(featureCollection)
    return markerId
  }

  @SuppressLint("SetTextI18n")
  private fun addViewAnnotation(point: Point, markerId: String) {
    val viewAnnotation = viewAnnotationManager.addViewAnnotation(
      R.layout.item_callout_view,
      ViewAnnotationOptions.Builder()
        .geometry(point)
        .associatedFeatureId(markerId)
        .anchor(ViewAnnotationAnchor.BOTTOM)
        .allowOverlap(false)
        .build()
    )
    viewAnnotation.visibility = View.GONE
    // calculate offsetY manually taking into account icon height only because of bottom anchoring
    viewAnnotationManager.updateViewAnnotation(
      viewAnnotation,
      ViewAnnotationOptions.Builder()
        .offsetY(markerHeight)
        .visible(false)
        .build()
    )
    viewAnnotation.findViewById<TextView>(R.id.textNativeView).text =
      "lat=%.2f\nlon=%.2f".format(point.latitude(), point.longitude())
    viewAnnotation.findViewById<ImageView>(R.id.closeNativeView).setOnClickListener { _ ->
      viewAnnotationManager.removeViewAnnotation(viewAnnotation)
    }
    viewAnnotation.findViewById<Button>(R.id.selectButton).setOnClickListener { b ->
      val button = b as Button
      if (button.text.contentEquals("SELECT", true)) {
        button.text = "DESELECT"
        viewAnnotationManager.updateViewAnnotation(
          viewAnnotation,
          ViewAnnotationOptions.Builder()
            .width(viewAnnotationManager.getViewAnnotationOptionsByView(viewAnnotation)?.width!! + SELECTED_ADD_COEF_PX)
            .height(viewAnnotationManager.getViewAnnotationOptionsByView(viewAnnotation)?.height!! + SELECTED_ADD_COEF_PX)
            .selected(true)
            .build()
        )
      } else {
        button.text = "SELECT"
        viewAnnotationManager.updateViewAnnotation(
          viewAnnotation,
          ViewAnnotationOptions.Builder()
            .width(viewAnnotationManager.getViewAnnotationOptionsByView(viewAnnotation)?.width!! - SELECTED_ADD_COEF_PX)
            .height(viewAnnotationManager.getViewAnnotationOptionsByView(viewAnnotation)?.height!! - SELECTED_ADD_COEF_PX)
            .selected(false)
            .build()
        )
      }
    }
  }

  companion object {
    private const val BLUE_ICON_ID = "blue"
    private const val SOURCE_ID = "source_id"
    private const val LAYER_ID = "layer_id"
    private const val SELECTED_ADD_COEF_PX = 50
  }
}
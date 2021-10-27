package com.mapbox.maps.testapp.examples.markersandcallouts

import android.annotation.SuppressLint
import android.graphics.Bitmap
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
import com.mapbox.maps.extension.style.sources.generated.rasterDemSource
import com.mapbox.maps.extension.style.sources.getSourceAs
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.extension.style.terrain.generated.terrain
import com.mapbox.maps.plugin.gestures.*
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityViewAnnotationShowcaseBinding
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import com.mapbox.maps.viewannotation.viewAnnotationOptions

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
  private var markerId = 0

  private var markerWidth = 0
  private var markerHeight = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityViewAnnotationShowcaseBinding.inflate(layoutInflater)
    setContentView(binding.root)

    viewAnnotationManager = binding.mapView.viewAnnotationManager

    val bitmap = BitmapFactory.decodeResource(resources, R.drawable.blue_marker_view)
    markerWidth = bitmap.width
    markerHeight = bitmap.height

    mapboxMap = binding.mapView.getMapboxMap().apply {
      loadStyle(
        styleExtension = prepareStyle(Style.MAPBOX_STREETS, bitmap)
      ) {
        style = it
        addOnMapClickListener(this@ViewAnnotationShowcaseActivity)
        addOnMapLongClickListener(this@ViewAnnotationShowcaseActivity)
        binding.fabStyleToggle.setOnClickListener {
          if (getStyle()?.styleURI == Style.MAPBOX_STREETS) {
            loadStyle(prepareStyle(Style.SATELLITE_STREETS, bitmap))
          } else if (getStyle()?.styleURI == Style.SATELLITE_STREETS) {
            loadStyle(prepareStyle(Style.MAPBOX_STREETS, bitmap))
          }
        }
      }
    }

    // code to verify performance, leaving for now, will remove later

//    binding.mapView.postDelayed({
//      for (i in 0 until 100) {
//        addViewAnnotation(Point.fromLngLat(27.56667 + i * 0.01, 53.9 + i * 0.01), "1")
//      }
//    }, 5_000L)
  }

  private fun prepareStyle(styleUri: String, bitmap: Bitmap) = style(styleUri) {
    +image(BLUE_ICON_ID) {
      bitmap(bitmap)
    }
    +geoJsonSource(SOURCE_ID) {
      featureCollection(FeatureCollection.fromFeatures(pointList))
    }
    if (styleUri == Style.SATELLITE_STREETS) {
      +rasterDemSource(TERRAIN_SOURCE) {
        url(TERRAIN_URL_TILE_RESOURCE)
      }
      +terrain(TERRAIN_SOURCE)
    }
    +symbolLayer(LAYER_ID, SOURCE_ID) {
      iconImage(BLUE_ICON_ID)
      iconAnchor(IconAnchor.BOTTOM)
      iconAllowOverlap(false)
    }
  }

  override fun onMapLongClick(point: Point): Boolean {
    val markerId = addMarkerAndReturnId(point)
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
              } else {
                viewAnnotation.visibility = View.VISIBLE
              }
            }
          }
        }
      }
    }
    return true
  }

  private fun addMarkerAndReturnId(point: Point): String {
    val currentId = "${MARKER_ID_PREFIX}${(markerId++)}"
    pointList.add(Feature.fromGeometry(point, null, currentId))
    val featureCollection = FeatureCollection.fromFeatures(pointList)
    style.getSourceAs<GeoJsonSource>(SOURCE_ID)?.featureCollection(featureCollection)
    return currentId
  }

  @SuppressLint("SetTextI18n")
  private fun addViewAnnotation(point: Point, markerId: String) {
    viewAnnotationManager.addViewAnnotation(
      R.layout.item_callout_view,
      viewAnnotationOptions {
        geometry(point)
        associatedFeatureId(markerId)
        anchor(ViewAnnotationAnchor.BOTTOM)
        allowOverlap(false)
      }
    ) { viewAnnotation ->
      viewAnnotation.visibility = View.GONE
      // calculate offsetY manually taking into account icon height only because of bottom anchoring
      viewAnnotationManager.updateViewAnnotation(
        viewAnnotation,
        viewAnnotationOptions {
          offsetY(markerHeight)
        }
      )
      viewAnnotation.findViewById<TextView>(R.id.textNativeView).text =
        "lat=%.2f\nlon=%.2f".format(point.latitude(), point.longitude())
      viewAnnotation.findViewById<ImageView>(R.id.closeNativeView).setOnClickListener { _ ->
        viewAnnotationManager.removeViewAnnotation(viewAnnotation)
      }
      viewAnnotation.findViewById<Button>(R.id.selectButton).setOnClickListener { b ->
        val button = b as Button
        val isSelected = button.text.contentEquals("SELECT", true)
        val pxDelta = if (isSelected) SELECTED_ADD_COEF_PX else -SELECTED_ADD_COEF_PX
        button.text = if (isSelected) "DESELECT" else "SELECT"
        viewAnnotationManager.updateViewAnnotation(
          viewAnnotation,
          viewAnnotationOptions {
            width(viewAnnotationManager.getViewAnnotationOptionsByView(viewAnnotation)?.width!! + pxDelta)
            height(viewAnnotationManager.getViewAnnotationOptionsByView(viewAnnotation)?.height!! + pxDelta)
            selected(isSelected)
          }
        )
      }
    }
  }

  private companion object {
    const val BLUE_ICON_ID = "blue"
    const val SOURCE_ID = "source_id"
    const val LAYER_ID = "layer_id"
    const val TERRAIN_SOURCE = "TERRAIN_SOURCE"
    const val TERRAIN_URL_TILE_RESOURCE = "mapbox://mapbox.mapbox-terrain-dem-v1"
    const val MARKER_ID_PREFIX = "view_annotation_"
    const val SELECTED_ADD_COEF_PX = 50
  }
}
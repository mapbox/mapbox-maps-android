package com.mapbox.maps.testapp.examples.terrain3D

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.eq
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.get
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.literal
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.FillExtrusionLayer
import com.mapbox.maps.extension.style.layers.generated.LocationIndicatorLayer
import com.mapbox.maps.extension.style.light.generated.getLight
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.testapp.databinding.ActivityFillExtrusionBinding

/**
 * Extrude the building layer in the Mapbox Light style using FillExtrusionLayer
 * and set up the light position.
 */
class FillExtrusionActivity : AppCompatActivity() {

  private var isRedColor: Boolean = false
  private var isInitPosition: Boolean = false
  private lateinit var binding: ActivityFillExtrusionBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityFillExtrusionBinding.inflate(layoutInflater)
    setContentView(binding.root)
    val mapboxMap = binding.mapView.getMapboxMap()

    mapboxMap.setCamera(
      CameraOptions.Builder()
        .center(Point.fromLngLat(139.772637, 35.675500))
        .pitch(45.0)
        .zoom(15.5)
        .bearing(-17.6)
        .build()
    )

    mapboxMap.loadStyleUri(
      Style.LIGHT
    ) { style ->
      setupLayers(style)
      style.setStyleLayerProperty(
        "3d-buildings",
        "fill-extrusion-indicator-hole-points",
        Value(listOf(35.675500, 139.772637).map(::Value))
      )
      style.setStyleLayerProperty(
        "3d-buildings",
        "fill-extrusion-indicator-hole-opacity",
        Value(0.4)
      )
      style.setStyleLayerProperty(
        "3d-buildings",
        "fill-extrusion-indicator-hole-radius",
        Value(300)
      )
    }
  }

  private fun setupLayers(style: Style) {
    val locationIndocatorLayer = LocationIndicatorLayer("indicator-layer")
    locationIndocatorLayer.accuracyRadius(10.0)
    locationIndocatorLayer.accuracyRadiusColor(Color.GREEN)
    locationIndocatorLayer.location(listOf(35.675500, 139.772637, 0.0))
    style.addLayer(locationIndocatorLayer)

    val fillExtrusionLayer = FillExtrusionLayer("3d-buildings", "composite")
    fillExtrusionLayer.sourceLayer("building")
    fillExtrusionLayer.filter(eq(get("extrude"), literal("true")))
    fillExtrusionLayer.minZoom(15.0)
    fillExtrusionLayer.fillExtrusionColor(Color.parseColor("#cccccc"))
    fillExtrusionLayer.fillExtrusionHeight(get("height"))
    fillExtrusionLayer.fillExtrusionBase(get("min_height"))
    fillExtrusionLayer.fillExtrusionOpacity(1.0)
    fillExtrusionLayer.fillExtrusionAmbientOcclusionIntensity(0.3)
    fillExtrusionLayer.fillExtrusionAmbientOcclusionRadius(3.0)
    style.addLayer(fillExtrusionLayer)
  }

}
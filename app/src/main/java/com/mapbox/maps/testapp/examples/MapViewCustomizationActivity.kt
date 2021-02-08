package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_map_view_customization.*

/**
 * Example of customizing your [MapView].
 * This example will show how to create [MapView] both programmatically and from xml
 * and apply various options.
 */
class MapViewCustomizationActivity : AppCompatActivity() {

  private lateinit var customMapView: MapView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_map_view_customization)
    configureMapViewFromXml()
    configureMapViewFromCode()
  }

  private fun configureMapViewFromCode() {
    val mapboxMapOptions = MapboxMapOptions(this).apply {
      // set initial camera position
      cameraOptions = CameraOptions.Builder()
        .center(Point.fromLngLat(-122.4194, 37.7749))
        .zoom(9.0)
        .build()
      // use texture view renderer
      textureView = true
      // set other map options
      mapOptions = MapOptions.Builder()
        .constrainMode(ConstrainMode.HEIGHT_ONLY)
        .glyphsRasterizationOptions(
          GlyphsRasterizationOptions.Builder()
            .rasterizationMode(GlyphsRasterizationMode.IDEOGRAPHS_RASTERIZED_LOCALLY)
            .build()
        )
        .build()
      // set token and cache size for this particular map view
      resourceOptions = ResourceOptions.Builder()
        .accessToken(getString(R.string.mapbox_access_token))
        .cacheSize(75_000L)
        .build()
    }
    // create view programmatically and add to root layout
    customMapView = MapView(this, mapboxMapOptions)
    val params = LinearLayout.LayoutParams(
      LinearLayout.LayoutParams.MATCH_PARENT,
      0,
      1.0f
    )
    root.addView(customMapView, params)
    // load style to map view
    customMapView.getMapboxMap().loadStyleUri(Style.SATELLITE)
  }

  private fun configureMapViewFromXml() {
    // let's set `custom` token to MapView from code (however it will be same token from resources so that map will work)
    MapboxOptions.setDefaultResourceOptions(this, getString(R.string.mapbox_access_token))
    // all options provided in xml file - so we just load style
    mapView.getMapboxMap().loadStyleUri(Style.DARK)
  }

  override fun onStart() {
    super.onStart()
    mapView.onStart()
    customMapView.onStart()
  }

  override fun onStop() {
    super.onStop()
    mapView.onStop()
    customMapView.onStop()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
    customMapView.onLowMemory()
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
    customMapView.onDestroy()
  }
}
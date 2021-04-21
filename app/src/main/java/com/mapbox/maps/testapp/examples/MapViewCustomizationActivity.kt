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
    // Create  a custom CredentialsManager with a token and set it to CredentialsManager.shared, so that all MapViews created with default config will apply this token.
    CredentialsManager.default.setAccessToken(getString(R.string.mapbox_access_token))
    setContentView(R.layout.activity_map_view_customization)
    // all options provided in xml file - so we just load style
    mapView.getMapboxMap().loadStyleUri(Style.DARK)
    configureMapViewFromCode()
  }

  private fun configureMapViewFromCode() {
    // set map options
    val mapOptions = MapOptions.Builder()
      .constrainMode(ConstrainMode.HEIGHT_ONLY)
      .glyphsRasterizationOptions(
        GlyphsRasterizationOptions.Builder()
          .rasterizationMode(GlyphsRasterizationMode.IDEOGRAPHS_RASTERIZED_LOCALLY)
          // Font family is required when the GlyphsRasterizationMode is set to IDEOGRAPHS_RASTERIZED_LOCALLY or ALL_GLYPHS_RASTERIZED_LOCALLY
          .fontFamily("sans-serif")
          .build()
      )
      .build()

    // set token and cache size for this particular map view
    val resourceOptions = ResourceOptions.Builder()
      .accessToken(getString(R.string.mapbox_access_token))
      .cacheSize(75_000L)
      .build()

    // set initial camera position
    val initialCameraOptions = CameraOptions.Builder()
      .center(Point.fromLngLat(-122.4194, 37.7749))
      .zoom(9.0)
      .build()

    val mapboxMapOptions =
      MapInitOptions(this, resourceOptions, mapOptions, initialCameraOptions, true)
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
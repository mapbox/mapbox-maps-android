package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.common.MapboxOptions
import com.mapbox.common.TileStore
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.plugin.*
import com.mapbox.maps.plugin.Plugin.Companion.MAPBOX_ATTRIBUTION_PLUGIN_ID
import com.mapbox.maps.plugin.Plugin.Companion.MAPBOX_LOGO_PLUGIN_ID
import com.mapbox.maps.testapp.databinding.ActivityMapViewCustomizationBinding

/**
 * Example of customizing your [MapView].
 * This example will show how to create [MapView] both programmatically and from xml
 * and apply various options.
 */
class MapViewCustomizationActivity : AppCompatActivity() {

  private lateinit var customMapView: MapView
  // Users should keep a reference to the customised tileStore instance (if there's any)
  private val tileStore by lazy { TileStore.create() }
  private lateinit var binding: ActivityMapViewCustomizationBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Set tile store and tile store usage mode so that all MapViews created from now on will apply
    // these settings.
    MapboxOptions.mapsOptions.tileStore = tileStore
    MapboxOptions.mapsOptions.tileStoreUsageMode = TileStoreUsageMode.READ_ONLY

    binding = ActivityMapViewCustomizationBinding.inflate(layoutInflater)

    setContentView(binding.root)

    // all options provided in xml file - so we just load style
    binding.mapView.mapboxMap.loadStyle(Style.DARK)
    configureMapViewFromCode()
  }

  private fun configureMapViewFromCode() {
    // set map options
    val mapOptions = MapOptions.Builder().applyDefaultParams(this)
      .constrainMode(ConstrainMode.HEIGHT_ONLY)
      .glyphsRasterizationOptions(
        GlyphsRasterizationOptions.Builder()
          .rasterizationMode(GlyphsRasterizationMode.IDEOGRAPHS_RASTERIZED_LOCALLY)
          // Font family is required when the GlyphsRasterizationMode is set to IDEOGRAPHS_RASTERIZED_LOCALLY or ALL_GLYPHS_RASTERIZED_LOCALLY
          .fontFamily("sans-serif")
          .build()
      )
      .build()

    // plugins configuration
    val plugins = listOf(
      Plugin.Mapbox(MAPBOX_LOGO_PLUGIN_ID),
      Plugin.Mapbox(MAPBOX_ATTRIBUTION_PLUGIN_ID)
    )

    // Set tile store and tile store usage mode so that all MapViews created from now on will apply
    // these settings.
    MapboxOptions.mapsOptions.tileStore = tileStore
    MapboxOptions.mapsOptions.tileStoreUsageMode = TileStoreUsageMode.DISABLED

    // set initial camera position
    val initialCameraOptions = CameraOptions.Builder()
      .center(Point.fromLngLat(-122.4194, 37.7749))
      .zoom(9.0)
      .bearing(120.0)
      .build()

    val mapInitOptions =
      MapInitOptions(this, mapOptions, plugins, initialCameraOptions, true)

    // create view programmatically and add to root layout
    customMapView = MapView(this, mapInitOptions)
    val params = LinearLayout.LayoutParams(
      LinearLayout.LayoutParams.MATCH_PARENT,
      0,
      1.0f
    )
    binding.rootLayout.addView(customMapView, params)
    // load style to map view
    customMapView.mapboxMap.loadStyle(Style.SATELLITE)
  }

  override fun onStart() {
    super.onStart()
    customMapView.onStart()
  }

  override fun onStop() {
    super.onStop()
    customMapView.onStop()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    customMapView.onLowMemory()
  }

  override fun onDestroy() {
    super.onDestroy()
    customMapView.onDestroy()
  }
}
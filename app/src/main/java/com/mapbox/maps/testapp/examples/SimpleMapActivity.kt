package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.layers.properties.generated.Anchor
import com.mapbox.maps.extension.style.layers.properties.generated.ProjectionName
import com.mapbox.maps.extension.style.light.generated.light
import com.mapbox.maps.extension.style.projection.generated.projection
import com.mapbox.maps.extension.style.sources.generated.rasterDemSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.extension.style.terrain.generated.terrain

/**
 * Example of displaying a map.
 */
class SimpleMapActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap
  private var currentStyleUri = DAY_STYLE

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)
    mapboxMap = mapView.getMapboxMap()
    mapboxMap.setCamera(
      CameraOptions.Builder()
        .center(Point.fromLngLat(LONGITUDE, LATITUDE))
        .zoom(9.0)
        .pitch(45.0)
        .build()
    )
    loadStyle(currentStyleUri, TERRAIN_SOURCE_1, -1)

    for (i in 0..100) {
      Handler(Looper.getMainLooper()).postDelayed(1000L + i.toLong()) {
        when (currentStyleUri) {
          // terrain source 1 and 2 are equal but crash also happens if they are different
          DAY_STYLE -> loadStyle(NIGHT_STYLE, TERRAIN_SOURCE_1, i)
          NIGHT_STYLE -> loadStyle(DAY_STYLE, TERRAIN_SOURCE_2, i)
        }
      }
    }
  }

  private fun loadStyle(styleUri: String, terrainSource: String, iteration: Int) {
    currentStyleUri = styleUri
    logE("KIRYLDD", "Loading style $currentStyleUri, iteration $iteration")
    mapboxMap.loadStyle(
      styleExtension = style(styleUri) {
        logE("KIRYLDD", "Loaded style $styleUri, iteration $iteration")
        +projection(ProjectionName.GLOBE)
        +light {
          intensity(0.6)
          anchor(Anchor.MAP)
          position(1.5, 210.0, 45.0)
        }
        // we need terrain for the drape-mipmap parameter to be effective to reduce flickering
        +rasterDemSource(terrainSource) {
          url(TERRAIN_URL_TILE_RESOURCE)
          // 514 specifies padded DEM tile and provides better performance than 512 tiles.
          tileSize(514)
          maxzoom(3)
        }
        +terrain(terrainSource) {
          exaggeration(literal(0))
        }
      }
    )
  }

  companion object {
    private const val LATITUDE = 0.0
    private const val LONGITUDE = 0.0

    private const val DAY_STYLE = Style.LIGHT
    private const val NIGHT_STYLE = Style.DARK

    private const val TERRAIN_SOURCE_1 = "TERRAIN_SOURCE"
    private const val TERRAIN_SOURCE_2 = "TERRAIN_SOURCE"
    private const val TERRAIN_URL_TILE_RESOURCE = "mapbox://mapbox.mapbox-terrain-dem-v1"
  }
}
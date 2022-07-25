package com.mapbox.maps.testapp.examples.terrain3D

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.atmosphere.generated.atmosphere
import com.mapbox.maps.extension.style.layers.generated.skyLayer
import com.mapbox.maps.extension.style.layers.properties.generated.ProjectionName
import com.mapbox.maps.extension.style.layers.properties.generated.SkyType
import com.mapbox.maps.extension.style.projection.generated.projection
import com.mapbox.maps.extension.style.sources.generated.*
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.extension.style.terrain.generated.terrain
import com.mapbox.maps.testapp.databinding.ActivityTerrainShowcaseBinding
import com.mapbox.maps.testapp.examples.*
import com.mapbox.maps.testapp.examples.style.TileJsonActivity
import kotlin.random.Random

/**
 * Example that demonstrates realistic map with 3D terrain and atmosphere sky layer.
 */
class Terrain3DShowcaseActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityTerrainShowcaseBinding.inflate(layoutInflater)
    setContentView(binding.root)
    mapboxMap = binding.mapView.getMapboxMap()
    loadStyle()
    val runnable = object : Runnable {
      override fun run() {
        println("Load style again!")
        loadStyle()
        binding.mapView.postDelayed(this, 1)
      }
    }
    binding.mapView.post(runnable)
  }

  private fun loadStyle() {
    mapboxMap.loadStyle(
      styleExtension = style(Style.SATELLITE_STREETS) {
        // crashes
        +geoJsonSource(SOURCE_ID) {
          geometry(Point.fromLngLat(24.9384, 60.1699))
        }
        // crashes
//        +rasterDemSource(SOURCE) {
//          url(TERRAIN_URL_TILE_RESOURCE)
//        }

        // does not crash
//        +vectorSource("IconPropertyActivity.SOURCE_ID") {
//          url("mapbox://examples.ciuz0vpc")
//        }
        // does not crash
//        +rasterSource(WmsSourceActivity.WMS_SOURCE_ID) {
//          tileSize(256)
//          tileSet(WmsSourceActivity.TILESET_JSON, listOf(WmsSourceActivity.WMS_SOURCE_URL)) {}
//        }
        // does not crash
//        +imageSource("image-source") {
//          url("https://upload.wikimedia.org/wikipedia/commons/thumb/1/1f/Mapbox_logo_2019.svg/2560px-Mapbox_logo_2019.svg.png")
//          coordinates(
//            listOf(
//              listOf(-80.11725, 25.7836),
//              listOf(-80.1397431334, 25.783548),
//              listOf(-80.13964, 25.7680),
//              listOf(-80.11725, 25.76795)
//            )
//          )
//        }
      }
    )
  }

  companion object {
    const val CAMERA_ZOOM = 14.0
    const val CAMERA_PITCH = 45.0
    const val SOURCE_ID = "source-id"
    const val MODEL_LAYER_ID = "model-layer-id"
    const val MODEL_ID_KEY = "model-id-key"
    const val MODEL_ID_1 = "model-id-1"
    const val MODEL_ID_2 = "model-id-2"
    const val SAMPLE_MODEL_URI_1 =
      "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Duck/glTF-Embedded/Duck.gltf"
    const val SAMPLE_MODEL_URI_2 = "asset://sportcar.glb"
    val HELSINKI = Point.fromLngLat(24.9384, 60.1699)
    val MAPBOX_HELSINKI = Point.fromLngLat(24.945389069265598, 60.17195694011002)
    private const val SOURCE = "TERRAIN_SOURCE"
    private const val SKY_LAYER = "sky"
    private const val TERRAIN_URL_TILE_RESOURCE = "mapbox://mapbox.mapbox-terrain-dem-v1"
  }
}
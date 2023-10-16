package com.mapbox.maps.testapp.examples.terrain3D

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.atmosphere.generated.atmosphere
import com.mapbox.maps.extension.style.layers.generated.skyLayer
import com.mapbox.maps.extension.style.layers.properties.generated.ProjectionName
import com.mapbox.maps.extension.style.layers.properties.generated.SkyType
import com.mapbox.maps.extension.style.projection.generated.projection
import com.mapbox.maps.extension.style.sources.generated.rasterDemSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.extension.style.terrain.generated.terrain
import com.mapbox.maps.testapp.databinding.ActivityTerrainShowcaseBinding

/**
 * Example that demonstrates realistic map with 3D terrain and atmosphere sky layer.
 */
class Terrain3DShowcaseActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityTerrainShowcaseBinding.inflate(layoutInflater)
    setContentView(binding.root)
    mapboxMap = binding.mapView.mapboxMap
    mapboxMap.loadStyle(
      styleExtension = style(Style.SATELLITE_STREETS) {
        +rasterDemSource(SOURCE) {
          url(TERRAIN_URL_TILE_RESOURCE)
          // 514 specifies padded DEM tile and provides better performance than 512 tiles.
          tileSize(514)
        }
        +terrain(SOURCE)
        +skyLayer(SKY_LAYER) {
          skyType(SkyType.ATMOSPHERE)
          skyAtmosphereSun(listOf(-50.0, 90.2))
        }
        +atmosphere { }
        +projection(ProjectionName.GLOBE)
      }
    )
  }

  companion object {
    private const val SOURCE = "TERRAIN_SOURCE"
    private const val SKY_LAYER = "sky"
    private const val TERRAIN_URL_TILE_RESOURCE = "mapbox://mapbox.mapbox-terrain-dem-v1"
  }
}
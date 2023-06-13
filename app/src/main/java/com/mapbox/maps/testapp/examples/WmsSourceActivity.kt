package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.addLayerBelow
import com.mapbox.maps.extension.style.layers.generated.RasterLayer
import com.mapbox.maps.extension.style.layers.getLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.rasterSource
import com.mapbox.maps.testapp.databinding.ActivityWmsSourceBinding

/**
 * Adding an external Web Map Service layer to the map.
 */
class WmsSourceActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityWmsSourceBinding.inflate(layoutInflater)
    setContentView(binding.root)
    binding.mapView.getMapboxMap().loadStyleUri(
      Style.LIGHT
    ) {
      it.addSource(
        rasterSource(WMS_SOURCE_ID) {
          tileSize(256)
          tileSet(TILESET_JSON, listOf(WMS_SOURCE_URL)) {}
        }
      )

      if (it.getLayer(BELOW_LAYER_ID) != null) {
        it.addLayerBelow(
          RasterLayer(RASTER_LAYER_ID, WMS_SOURCE_ID),
          BELOW_LAYER_ID
        )
      } else {
        it.addLayer(RasterLayer(RASTER_LAYER_ID, WMS_SOURCE_ID))
      }
    }
  }

  companion object {
    const val WMS_SOURCE_URL = "https://img.nj.gov/imagerywms/Natural2015?bbox={bbox-epsg-3857}" +
      "&format=image/png&service=WMS&version=1.1.1&request=GetMap&srs=EPSG:3857" +
      "&transparent=true&width=256&height=256&layers=Natural2015"
    const val WMS_SOURCE_ID = "web-map-source"
    const val RASTER_LAYER_ID = "web-map-layer"
    const val BELOW_LAYER_ID = "tunnel-simple"
    const val TILESET_JSON = "tileset"
  }
}
package com.mapbox.maps.testapp.examples.style

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.addLayerBelow
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.vectorSource

/**
 * Activity renders vector tiles using a third party vector tile source.
 *
 * In this case, Mapillary provides the vector tiles, which are added to the map using addSource.
 */
class ThirdPartyVectorSourceActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)
    mapboxMap = mapView.getMapboxMap()

    mapboxMap.setCamera(
      CameraOptions.Builder()
        .zoom(12.0)
        .center(Point.fromLngLat(-87.622088, 41.878781))
        .build()
    )

    mapboxMap.loadStyleUri(Style.LIGHT) {
      it.addSource(
        vectorSource(SOURCE_ID) {
          tiles(listOf("https://tiles.mapillary.com/maps/vtp/mly1_public/2/{z}/{x}/{y}?access_token=MLY|4142433049200173|72206abe5035850d6743b23a49c41333"))
          minzoom(6)
          maxzoom(14)
        }
      )
      it.addLayerBelow(
        lineLayer(LAYER_ID, SOURCE_ID) {
          sourceLayer(SOURCE_LAYER_ID)
          lineCap(LineCap.ROUND)
          lineJoin(LineJoin.ROUND)
          lineOpacity(0.6)
          lineColor(Expression.rgb(53.0, 175.0, 109.0))
          lineWidth(2.0)
        },
        BELOW_LAYER_ID
      )
    }
  }

  private companion object {
    const val SOURCE_ID = "mapillary"
    const val LAYER_ID = SOURCE_ID
    const val TAG = SOURCE_ID
    const val SOURCE_LAYER_ID = "sequence"
    const val BELOW_LAYER_ID = "road-label"
  }
}
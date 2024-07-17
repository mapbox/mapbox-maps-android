package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.clipLayer
import com.mapbox.maps.extension.style.layers.generated.fillLayer
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.R

/**
 * Example showcasing the usage of [com.mapbox.maps.extension.style.layers.generated.ClipLayer].
 */
class ClipLayerActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)
    mapboxMap = mapView.mapboxMap.apply {
      setCamera(START_CAMERA_POSITION)
    }
    mapboxMap.loadStyle(
      style(style = Style.STANDARD) {
        +geoJsonSource(SOURCE_ID) {
          geometry(Polygon.fromLngLats(POLYGON_POINTS))
        }
        +fillLayer(FILL_LAYER_ID, SOURCE_ID) {
          fillOpacity(0.8)
          fillColor(ContextCompat.getColor(this@ClipLayerActivity, R.color.blue))
          slot("bottom")
        }
      }
    )
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_clip_layer, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.menu_action_clip_reset -> {
        mapboxMap.getStyle { style ->
          if (style.styleLayerExists(CLIP_LAYER_ID)) {
            style.removeStyleLayer(CLIP_LAYER_ID)
          }
        }
        true
      }

      R.id.menu_action_clip_model -> {
        updateClipLayerTypes("model")
        true
      }

      R.id.menu_action_clip_symbol -> {
        updateClipLayerTypes("symbol")
        true
      }

      else -> {
        super.onOptionsItemSelected(item)
      }
    }
  }

  @OptIn(MapboxExperimental::class)
  private fun updateClipLayerTypes(clipLayerTypes: String) {
    mapboxMap.getStyle { style ->
      if (style.styleLayerExists(CLIP_LAYER_ID)) {
        style.removeStyleLayer(CLIP_LAYER_ID)
      }
      style.addLayer(
        clipLayer(CLIP_LAYER_ID, SOURCE_ID) {
          clipLayerTypes(listOf(clipLayerTypes))
        }
      )
    }
  }

  companion object {
    private const val CLIP_LAYER_ID = "clip-layer-id"
    private const val FILL_LAYER_ID = "fill-layer-id"
    private const val SOURCE_ID = "source-id"
    private val CENTER = Point.fromLngLat(-74.0027, 40.7130)
    private val START_CAMERA_POSITION = cameraOptions {
      center(CENTER)
      zoom(16.5)
      bearing(60.0)
      pitch(30.0)
    }

    private val POLYGON_POINTS = listOf(
      listOf(
        Point.fromLngLat(
          -74.00438542864366,
          40.71275107696869
        ),
        Point.fromLngLat(
          -74.00465916994656,
          40.712458268827675
        ),
        Point.fromLngLat(
          -74.00417333128154,
          40.71212099900339
        ),
        Point.fromLngLat(
          -74.00314623457163,
          40.71238635014873
        ),
        Point.fromLngLat(
          -74.00088173461268,
          40.71296692136764
        ),
        Point.fromLngLat(
          -74.00081475001514,
          40.713220461793924
        ),
        Point.fromLngLat(
          -74.0024425998592,
          40.71419501190087
        ),
        Point.fromLngLat(
          -74.00341033210208,
          40.71374214594772
        ),
        Point.fromLngLat(
          -74.00438542864366,
          40.71275107696869
        ),
      )
    )
  }
}
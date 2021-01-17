package com.mapbox.maps.testapp.examples

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.match
import com.mapbox.maps.extension.style.image.image
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_add_marker_symbol.*

/**
 * Example showing how to add 2 different markers based on their type
 */
class AddMarkersSymbolActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_marker_symbol)

    mapView.getMapboxMap().loadStyle(
      styleExtension = style(Style.LIGHT) {
        // prepare red marker from resources
        +image(RED_ICON_ID) {
          bitmap(BitmapFactory.decodeResource(resources, R.drawable.red_marker))
        }
        // prepare blue marker from resources
        +image(BLUE_ICON_ID) {
          bitmap(BitmapFactory.decodeResource(resources, R.drawable.blue_marker_view))
        }
        // prepare source that will hold icons and add extra string property to each of it
        // to identify what marker icon should be used
        +geoJsonSource(SOURCE_ID) {
          featureCollection(
            FeatureCollection.fromFeatures(
              arrayOf(
                Feature.fromGeometry(
                  Point.fromLngLat(
                    72.88055419921875,
                    19.05822387777432
                  )
                ).apply {
                  addStringProperty(ICON_KEY, ICON_RED_PROPERTY)
                },
                Feature.fromGeometry(
                  Point.fromLngLat(
                    77.22015380859375,
                    28.549544699103865
                  )
                ).apply {
                  addStringProperty(ICON_KEY, ICON_BLUE_PROPERTY)
                }
              )
            )
          )
        }
        // finally prepare symbol layer with
        // if get(ICON_KEY) == ICON_RED_PROPERTY
        //  then
        //    RED_MARKER
        //  else if get(ICON_KEY) == ICON_BLUE_PROPERTY
        //    BLUE_MARKER
        //  else
        //    RED_MARKER
        +symbolLayer(LAYER_ID, SOURCE_ID) {
          iconImage(
            match {
              get {
                literal(ICON_KEY)
              }
              stop {
                literal(ICON_RED_PROPERTY)
                literal(RED_ICON_ID)
              }
              stop {
                literal(ICON_BLUE_PROPERTY)
                literal(BLUE_ICON_ID)
              }
              literal(RED_ICON_ID)
            }
          )
          iconAllowOverlap(true)
          iconAnchor(IconAnchor.BOTTOM)
        }
      }
    )
  }

  override fun onStart() {
    super.onStart()
    mapView.onStart()
  }

  override fun onStop() {
    super.onStop()
    mapView.onStop()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

  companion object {
    private const val RED_ICON_ID = "red"
    private const val BLUE_ICON_ID = "blue"
    private const val SOURCE_ID = "source_id"
    private const val LAYER_ID = "layer_id"
    private const val ICON_KEY = "icon_key"
    private const val ICON_RED_PROPERTY = "icon_red_property"
    private const val ICON_BLUE_PROPERTY = "icon_blue_property"
  }
}
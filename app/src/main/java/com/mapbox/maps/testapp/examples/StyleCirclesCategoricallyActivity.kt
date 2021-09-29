package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.expressions.dsl.generated.match
import com.mapbox.maps.extension.style.layers.generated.circleLayer
import com.mapbox.maps.extension.style.sources.generated.vectorSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.R

/**
 * Add point data to a style from a vector tileset and use the match and
 * get expressions to assign the color of each point in a CircleLayer
 * based on a data property.
 */
class StyleCirclesCategoricallyActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)
    mapboxMap = mapView.getMapboxMap()
    mapboxMap.loadStyle(
      style(Style.LIGHT) {

        +vectorSource("ethnicity-source") {
          url("http://api.mapbox.com/v4/examples.8fgz4egr.json?access_token=" + getString(R.string.mapbox_access_token))
        }

        +circleLayer("population", "ethnicity-source") {
          sourceLayer("sf2010")
          circleRadius(
            interpolate {
              exponential {
                literal(1.75)
              }
              zoom()
              stop {
                literal(12)
                literal(2)
              }
              stop {
                literal(22)
                literal(180)
              }
            }
          )

          circleColor(
            match {
              get {
                literal("ethnicity")
              }
              stop {
                literal("white")
                rgb {
                  literal(251)
                  literal(176)
                  literal(59)
                }
              }
              stop {
                literal("Black")
                rgb {
                  literal(34)
                  literal(59)
                  literal(83)
                }
              }
              stop {
                literal("Hispanic")
                rgb {
                  literal(229)
                  literal(94)
                  literal(94)
                }
              }
              stop {
                literal("Asian")
                rgb {
                  literal(59)
                  literal(178)
                  literal(208)
                }
              }
              stop {
                literal("Other")
                rgb {
                  literal(204)
                  literal(204)
                  literal(204)
                }
              }
              rgb {
                literal(0)
                literal(0)
                literal(0)
              }
            }
          )
        }
      }
    )

    mapboxMap.setCamera(
      CameraOptions.Builder()
        .center(Point.fromLngLat(-122.447303, 37.753574))
        .zoom(12.0)
        .build()
    )
  }
}
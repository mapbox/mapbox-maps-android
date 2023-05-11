package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.exponentialInterpolator
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.literal
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.match
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.rgb
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.zoom
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
            exponentialInterpolator(
              base = 1.75,
              input = zoom(),
              stops = arrayOf(
                literal(12) to literal(2),
                literal(22) to literal(180)
              )
            )
          )
          circleColor(
            match(
              input = Expression.get("ethnicity"),
              stops = arrayOf(
                literal("white") to rgb(251.0, 176.0, 59.0),
                literal("Black") to rgb(34.0, 59.0, 83.0),
                literal("Hispanic") to rgb(229.0, 94.0, 94.0),
                literal("Asian") to rgb(59.0, 178.0, 208.0),
                literal("Other") to rgb(204.0, 204.0, 204.0),
              ),
              fallback = rgb(0.0, 0.0, 0.0)
            )
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
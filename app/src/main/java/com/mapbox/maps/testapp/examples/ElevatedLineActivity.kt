package com.mapbox.maps.testapp.examples

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.mapbox.geojson.Feature
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.style.expressions.dsl.generated.atInterpolated
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.properties.generated.LineElevationReference
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.layers.properties.generated.ProjectionName
import com.mapbox.maps.extension.style.projection.generated.projection
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style

/**
 * Example of adding an elevated line to a map.
 * This example is based on mapbox-gl-js example: https://docs.mapbox.com/mapbox-gl-js/example/elevated-line/
 */
class ElevatedLineActivity : AppCompatActivity() {

  @OptIn(MapboxExperimental::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapInitOptions = MapInitOptions(
      this,
      cameraOptions = CAMERA_OPTIONS,
    )
    val mapView = MapView(this, mapInitOptions)
    setContentView(mapView)
    mapView.mapboxMap.loadStyle(
      style(Style.STANDARD) {
        +geoJsonSource(LINE_SOURCE_ID) {
          lineMetrics(true)
          feature(createLineStringFeature(COORDINATES, ELEVATIONS))
        }
        // add the elevated line layer
        +lineLayer(LINE_LAYER_ID, LINE_SOURCE_ID) {
          lineJoin(LineJoin.ROUND)
          lineWidth(8.0)
          lineColor(Color.BLUE)
          lineEmissiveStrength(1.0)
          lineElevationReference(LineElevationReference.SEA)
          lineZOffset(
            atInterpolated {
              product {
                lineProgress()
                subtract {
                  length { get(ELEVATION_PROPERTY_KEY) }
                  literal(1)
                }
              }
              get(ELEVATION_PROPERTY_KEY)
            }
          )
        }
      }
    )
  }

  private fun createLineStringFeature(coordinates: List<Point>, elevations: List<Double>): Feature {
    val properties = JsonObject()
    // add an array of elevation values.
    // the number of values doesn't need to match the number of coordinates.
    JsonArray().apply {
      elevations.forEach(::add)
    }.let { elevationsArray ->
      properties.add(ELEVATION_PROPERTY_KEY, elevationsArray)
    }
    return Feature.fromGeometry(LineString.fromLngLats(coordinates), properties)
  }

  companion object {
    private const val ELEVATION_PROPERTY_KEY = "elevation"
    private const val LINE_SOURCE_ID = "geojson"
    private const val LINE_LAYER_ID = "elevated-line"

    private val CAMERA_OPTIONS = cameraOptions {
      center(Point.fromLngLat(6.7782, 45.8418))
      zoom(11.0)
      bearing(-150.0)
      pitch(62.0)
      projection(ProjectionName.MERCATOR)
    }

    private val ELEVATIONS = arrayOf(
      4600, 4600, 4600, 4599, 4598, 4596, 4593, 4590, 4584, 4578, 4569,
      4559, 4547, 4533, 4516, 4497, 4475, 4450, 4422, 4390, 4355, 4316,
      4275, 4227, 4177, 4124, 4068, 4009, 3946, 3880, 3776, 3693, 3599,
      3502, 3398, 3290, 3171, 3052, 2922, 2786, 2642, 2490, 2332, 2170,
      1994, 1810, 1612, 1432, 1216, 1000
    ).map { it.toDouble() }

    private val COORDINATES = listOf(
      Point.fromLngLat(6.862885, 45.833563),
      Point.fromLngLat(6.863605, 45.846851),
      Point.fromLngLat(6.859783, 45.862445),
      Point.fromLngLat(6.848727, 45.876361),
      Point.fromLngLat(6.827155, 45.892361),
      Point.fromLngLat(6.802194, 45.905032),
      Point.fromLngLat(6.780023, 45.909602),
      Point.fromLngLat(6.753605, 45.906074),
      Point.fromLngLat(6.728807, 45.899120),
      Point.fromLngLat(6.700449, 45.883872),
      Point.fromLngLat(6.683772, 45.863866),
      Point.fromLngLat(6.684058, 45.841619),
      Point.fromLngLat(6.691115, 45.825417),
      Point.fromLngLat(6.704446, 45.813349),
      Point.fromLngLat(6.720959, 45.807886),
      Point.fromLngLat(6.748477, 45.809517),
      Point.fromLngLat(6.775554, 45.817254),
      Point.fromLngLat(6.791236, 45.828871),
      Point.fromLngLat(6.801289, 45.838797),
      Point.fromLngLat(6.806307, 45.849788),
      Point.fromLngLat(6.803161, 45.866159),
      Point.fromLngLat(6.794599, 45.880461),
      Point.fromLngLat(6.769846, 45.890231),
      Point.fromLngLat(6.744712, 45.889576),
      Point.fromLngLat(6.722788, 45.881677),
      Point.fromLngLat(6.708097, 45.868556),
      Point.fromLngLat(6.699435, 45.851973),
      Point.fromLngLat(6.707324, 45.832980),
      Point.fromLngLat(6.723743, 45.822384),
      Point.fromLngLat(6.739347, 45.818626),
      Point.fromLngLat(6.756019, 45.822069),
      Point.fromLngLat(6.773963, 45.832436),
      Point.fromLngLat(6.785920, 45.848229),
      Point.fromLngLat(6.786155, 45.860521),
      Point.fromLngLat(6.774430, 45.870586),
      Point.fromLngLat(6.749012, 45.875670),
      Point.fromLngLat(6.731251, 45.868501),
      Point.fromLngLat(6.716033, 45.853689),
      Point.fromLngLat(6.714748, 45.846970),
      Point.fromLngLat(6.714635, 45.838934),
      Point.fromLngLat(6.717850, 45.832829),
      Point.fromLngLat(6.724010, 45.828151),
      Point.fromLngLat(6.730551, 45.827333),
      Point.fromLngLat(6.733951, 45.829900),
      Point.fromLngLat(6.735957, 45.834154),
      Point.fromLngLat(6.735286, 45.839871),
      Point.fromLngLat(6.734471, 45.843933),
      Point.fromLngLat(6.730893, 45.847233),
      Point.fromLngLat(6.728550, 45.847899),
      Point.fromLngLat(6.726590, 45.847822),
      Point.fromLngLat(6.724876, 45.846455),
      Point.fromLngLat(6.725096, 45.843900),
      Point.fromLngLat(6.726635, 45.841201),
      Point.fromLngLat(6.728074, 45.837041),
      Point.fromLngLat(6.727822, 45.834292),
    )
  }
}
package com.mapbox.maps.testapp.examples

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.*
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.division
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.interpolate
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.step
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.circleLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.extension.style.utils.transition
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.utils.BitmapUtils.bitmapFromDrawableRes

/**
 * Example of using GeoJSON and circle layers to visualize point data in clusters.
 */
class CircleLayerClusteringActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)
    val mapboxMap = mapView.getMapboxMap()

    mapboxMap.loadStyle(
      styleExtension = style(Style.LIGHT) {
        +transition {
            duration(0)
            delay(0)
            enablePlacementTransitions(false)
        }
      },
      onStyleLoaded = {
        mapboxMap.flyTo(
          CameraOptions.Builder()
            .center(Point.fromLngLat(-79.045, 12.099))
            .zoom(3.0)
            .build()
        )

        addClusteredGeoJsonSource(it)

        bitmapFromDrawableRes(this, R.drawable.ic_cross)?.let { bitmap ->
          it.addImage(CROSS_ICON_ID, bitmap, true)
        }

        Toast.makeText(
          this@CircleLayerClusteringActivity,
          R.string.zoom_map_in_and_out_instruction,
          Toast.LENGTH_SHORT
        ).show()
      }
    )
  }

  private fun addClusteredGeoJsonSource(style: Style) {

    // Add a new source from the GeoJSON data and set the 'cluster' option to true.
    style.addSource(
      // Point to GeoJSON data. This example visualizes all M1.0+ earthquakes from 12/22/15 to 1/21/16 as logged by USGS' Earthquake hazards program.
      geoJsonSource(GEOJSON_SOURCE_ID) {
        url("https://www.mapbox.com/mapbox-gl-js/assets/earthquakes.geojson")
        cluster(true)
        maxzoom(14)
        clusterRadius(50)
      }
    )

    // Creating a marker layer for single data points
    style.addLayer(
      symbolLayer("unclustered-points", GEOJSON_SOURCE_ID) {
        iconImage(CROSS_ICON_ID)
        iconSize(
          division {
            get {
              literal("mag")
            }
            literal(4.0)
          }
        )
        iconColor(
          interpolate {
            exponential {
              literal(1)
            }
            get {
              literal("mag")
            }
            stop {
              literal(2.0)
              rgb {
                literal(0)
                literal(255)
                literal(0)
              }
            }
            stop {
              literal(4.5)
              rgb {
                literal(0)
                literal(0)
                literal(255)
              }
            }
            stop {
              literal(7.0)
              rgb {
                literal(255)
                literal(0)
                literal(0)
              }
            }
          }
        )
        filter(
          has {
            literal("mag")
          }
        )
      }
    )

    // Use the earthquakes GeoJSON source to create three layers: One layer for each cluster category.
    // Each point range gets a different fill color.
    val layers = arrayOf(
      intArrayOf(150, ContextCompat.getColor(this, R.color.red)),
      intArrayOf(20, ContextCompat.getColor(this, R.color.green)),
      intArrayOf(0, ContextCompat.getColor(this, R.color.blue))
    )

    // Add clusters' circles
    style.addLayer(
      circleLayer("clusters", GEOJSON_SOURCE_ID) {
        circleColor(
          step(
            input = get("point_count"),
            output = literal(ColorUtils.colorToRgbaString(layers[2][1])),
            stops = arrayOf(
              literal(layers[1][0].toDouble()) to literal(ColorUtils.colorToRgbaString(layers[1][1])),
              literal(layers[0][0].toDouble()) to literal(ColorUtils.colorToRgbaString(layers[0][1]))
            )
          )
        )
        circleRadius(18.0)
        filter(
          has("point_count")
        )
      }
    )

    style.addLayer(
      symbolLayer("count", GEOJSON_SOURCE_ID) {
        textField(
          format {
            formatSection(
              toString {
                get {
                  literal("point_count")
                }
              }
            )
          }
        )
        textSize(12.0)
        textColor(Color.WHITE)
        textIgnorePlacement(true)
        textAllowOverlap(true)
      }
    )
  }

  companion object {
    private const val GEOJSON_SOURCE_ID = "earthquakes"
    private const val CROSS_ICON_ID = "cross-icon-id"
  }
}
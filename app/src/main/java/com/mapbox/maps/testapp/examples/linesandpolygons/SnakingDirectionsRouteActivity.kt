package com.mapbox.maps.testapp.examples.linesandpolygons

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.mapbox.api.directions.v5.DirectionsCriteria
import com.mapbox.api.directions.v5.DirectionsCriteria.GEOMETRY_POLYLINE
import com.mapbox.api.directions.v5.MapboxDirections
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.api.directions.v5.models.LegStep
import com.mapbox.common.MapboxOptions
import com.mapbox.core.constants.Constants.PRECISION_5
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.image.image
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.sources.getSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.logE
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityJavaservicesSnakingDirectionsRouteBinding
import com.mapbox.turf.TurfConstants
import com.mapbox.turf.TurfMisc
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Rather than showing the directions route all at once, have it "snake" from the origin to destination.
 */
class SnakingDirectionsRouteActivity : AppCompatActivity() {

  private var mapboxDirectionsClient: MapboxDirections? = null
  private lateinit var binding: ActivityJavaservicesSnakingDirectionsRouteBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityJavaservicesSnakingDirectionsRouteBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.mapView.mapboxMap.loadStyle(
      style(Style.LIGHT) {
        +image(
          ICON_ID,
          ContextCompat.getDrawable(
            this@SnakingDirectionsRouteActivity,
            R.drawable.ic_red_marker
          )!!.toBitmap()
        )
        +geoJsonSource(SOURCE_ID) {
          featureCollection(
            FeatureCollection.fromFeatures(
              listOf(
                Feature.fromGeometry(PARIS_ORIGIN_POINT),
                Feature.fromGeometry(TULLINS_DESTINATION_POINT)
              )
            )
          )
        }
        +symbolLayer(LAYER_ID, SOURCE_ID) {
          iconImage(ICON_ID)
          iconOffset(listOf(0.0, -8.0))
        }
        // Add a source and LineLayer for the snaking directions route line
        +geoJsonSource(DRIVING_ROUTE_POLYLINE_SOURCE_ID) {
          geometry(PARIS_ORIGIN_POINT)
        }
        +layerAtPosition(
          lineLayer(DRIVING_ROUTE_POLYLINE_LINE_LAYER_ID, DRIVING_ROUTE_POLYLINE_SOURCE_ID) {
            lineWidth(NAVIGATION_LINE_WIDTH)
            lineOpacity(NAVIGATION_LINE_OPACITY)
            lineCap(LineCap.ROUND)
            lineJoin(LineJoin.ROUND)
            lineColor("#d742f4")
          },
          below = LAYER_ID
        )
      }
    ) { requestDirectionRoute() }
  }

  /**
   * Build and execute the Mapbox Directions API request
   */
  private fun requestDirectionRoute() {
    mapboxDirectionsClient = MapboxDirections.builder()
      .origin(PARIS_ORIGIN_POINT)
      .destination(TULLINS_DESTINATION_POINT)
      .overview(DirectionsCriteria.OVERVIEW_FULL)
      .profile(DirectionsCriteria.PROFILE_DRIVING)
      .geometries(GEOMETRY_POLYLINE)
      .alternatives(true)
      .steps(true)
      .accessToken(MapboxOptions.accessToken)
      .build()

    mapboxDirectionsClient?.enqueueCall(object : Callback<DirectionsResponse> {
      override fun onResponse(
        call: Call<DirectionsResponse>,
        response: Response<DirectionsResponse>
      ) {
        response.body()?.let { body ->
          if (body.routes().isEmpty()) {
            logE(TAG, "No routes found")
            return
          }
          body.routes()[0]?.legs()?.get(0)?.steps()?.let { steps ->
            drawRoute(steps)
          }
        } ?: run {
          logE(TAG, "No routes found, make sure you set the right user and access token.")
          return
        }
      }

      override fun onFailure(call: Call<DirectionsResponse>, t: Throwable) {
        Toast.makeText(
          this@SnakingDirectionsRouteActivity,
          R.string.snaking_directions_activity_error, Toast.LENGTH_SHORT
        ).show()
      }
    })
  }

  private fun drawRoute(steps: List<LegStep>) {
    val totalDistance = steps.sumOf(LegStep::distance)
    val singleAnimationDistance = totalDistance / ANIMATION_STEPS

    val line = steps
      .mapNotNull { it.geometry() }
      .map { LineString.fromPolyline(it, PRECISION_5) }
      .flatMap { it.coordinates() }
      .let(LineString::fromLngLats)

    val features = List(ANIMATION_STEPS) { index ->
      Feature.fromGeometry(
        TurfMisc.lineSliceAlong(
          line,
          singleAnimationDistance * index,
          singleAnimationDistance * (index + 1),
          TurfConstants.UNIT_METERS
        )
      )
    }

    val map = binding.mapView.mapboxMap
    (0..ANIMATION_STEPS).forEach { index ->
      binding.mapView.postDelayed(
        {
          if (map.isValid()) {
            map.getStyle {
              (it.getSource(DRIVING_ROUTE_POLYLINE_SOURCE_ID) as? GeoJsonSource)
                ?.featureCollection(FeatureCollection.fromFeatures(features.take(index)))
            }
          }
        },
        DRAW_SPEED_MILLISECONDS * index
      )
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    mapboxDirectionsClient?.cancelCall()
  }

  companion object {
    private const val TAG = "SnakingDirectionsRouteActivity"
    private const val ICON_ID = "icon-id"
    private const val SOURCE_ID = "source-id"
    private const val LAYER_ID = "layer-id"
    private const val NAVIGATION_LINE_WIDTH = 6.0
    private const val NAVIGATION_LINE_OPACITY = 0.8
    private const val DRIVING_ROUTE_POLYLINE_LINE_LAYER_ID = "DRIVING_ROUTE_POLYLINE_LINE_LAYER_ID"
    private const val DRIVING_ROUTE_POLYLINE_SOURCE_ID = "DRIVING_ROUTE_POLYLINE_SOURCE_ID"
    private const val DRAW_SPEED_MILLISECONDS = 50L
    private const val ANIMATION_STEPS = 200
    private val PARIS_ORIGIN_POINT = Point.fromLngLat(2.35222, 48.856614)
    private val TULLINS_DESTINATION_POINT = Point.fromLngLat(5.486011, 45.299410)
  }
}
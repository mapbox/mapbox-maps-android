package com.mapbox.maps.testapp.examples

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.api.directions.v5.DirectionsCriteria
import com.mapbox.api.directions.v5.DirectionsCriteria.GEOMETRY_POLYLINE
import com.mapbox.api.directions.v5.MapboxDirections
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.api.directions.v5.models.LegStep
import com.mapbox.common.Logger
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
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_dds_draw_polygon.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Rather than showing the directions route all at once, have it "snake" from the origin to destination by showing the
 * route one [LegStep] section at a time.
 */
class SnakingDirectionsRouteActivity : AppCompatActivity() {

  private lateinit var mapboxDirectionsClient: MapboxDirections
  private lateinit var drawRouteRunnable: Runnable
  private val handler = Handler()
  private val drivingRoutePolyLineFeatureList = mutableListOf<Feature>()
  private var counterIndex = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_javaservices_snaking_directions_route)

    mapView.getMapboxMap().loadStyle(
      style(Style.LIGHT) {
        +image(ICON_ID) {
          bitmap(BitmapFactory.decodeResource(resources, R.drawable.red_marker))
        }
        +geoJsonSource(SOURCE_ID) {
          featureCollection(
            FeatureCollection.fromFeatures(
              listOf(
                Feature.fromGeometry(PARIS_ORIGIN_POINT),
                Feature.fromGeometry(LYON_DESTINATION_POINT)
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
      .destination(LYON_DESTINATION_POINT)
      .overview(DirectionsCriteria.OVERVIEW_FULL)
      .profile(DirectionsCriteria.PROFILE_DRIVING)
      .geometries(GEOMETRY_POLYLINE)
      .alternatives(true)
      .steps(true)
      .accessToken(getString(R.string.mapbox_access_token))
      .build()

    mapboxDirectionsClient.enqueueCall(object : Callback<DirectionsResponse> {
      override fun onResponse(
        call: Call<DirectionsResponse>,
        response: Response<DirectionsResponse>
      ) {
        response.body()?.let { body ->
          if (body.routes().isEmpty()) {
            Logger.e(TAG, "No routes found")
            return
          }
          body.routes()[0]?.legs()?.get(0)?.steps()?.let { steps ->
            drawRoute(steps)
          }
        } ?: run {
          Logger.e(TAG, "No routes found, make sure you set the right user and access token.")
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
    /**
     * Runnable class which goes through the route and draws each [LegStep] of the Directions API route
     */
    drawRouteRunnable = Runnable {
      if (counterIndex < steps.size) {
        val singleStep = steps[counterIndex]
        singleStep.geometry()?.let {
          val lineStringRepresentingSingleStep = LineString.fromPolyline(
            it, PRECISION_5
          )
          drivingRoutePolyLineFeatureList.add(Feature.fromGeometry(lineStringRepresentingSingleStep))
        }
        mapView.getMapboxMap().getStyle {
          val source = it.getSource(DRIVING_ROUTE_POLYLINE_SOURCE_ID) as? GeoJsonSource
          source?.featureCollection(FeatureCollection.fromFeatures(drivingRoutePolyLineFeatureList))
        }
        counterIndex++
      }
      handler.postDelayed(drawRouteRunnable, DRAW_SPEED_MILLISECONDS)
    }
    handler.postDelayed(drawRouteRunnable, DRAW_SPEED_MILLISECONDS)
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
    if (this::mapboxDirectionsClient.isInitialized) {
      mapboxDirectionsClient.cancelCall()
    }
    if (this::drawRouteRunnable.isInitialized) {
      handler.removeCallbacks(drawRouteRunnable)
    }
    mapView.onDestroy()
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
    private const val DRAW_SPEED_MILLISECONDS = 500L
    private val PARIS_ORIGIN_POINT = Point.fromLngLat(2.35222, 48.856614)
    private val LYON_DESTINATION_POINT = Point.fromLngLat(4.83565, 45.76404)
  }
}
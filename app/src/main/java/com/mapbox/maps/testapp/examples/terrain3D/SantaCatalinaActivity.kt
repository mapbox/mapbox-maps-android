package com.mapbox.maps.testapp.examples.terrain3D

import android.animation.*
import android.graphics.Color.rgb
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.api.directions.v5.DirectionsCriteria
import com.mapbox.api.directions.v5.MapboxDirections
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.core.constants.Constants.PRECISION_6
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.image.image
import com.mapbox.maps.extension.style.layers.generated.*
import com.mapbox.maps.extension.style.layers.getLayerAs
import com.mapbox.maps.extension.style.layers.properties.generated.Visibility
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.sources.generated.rasterDemSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.extension.style.terrain.generated.terrain
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.scalebar.scalebar
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.utils.BitmapUtils.bitmapFromDrawableRes
import com.mapbox.turf.TurfConstants
import com.mapbox.turf.TurfMeasurement
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Example showcasing terrain with animating the free camera with a line string.
 */
class SantaCatalinaActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap
  private var timeAnimator: TimeAnimator? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    val mapView = MapView(this)
    setContentView(mapView)
    mapView.disablePlugins()

    // get map and setup initial camera
    mapboxMap = mapView.getMapboxMap()
    mapboxMap.setCamera(
      CameraOptions.Builder()
        .center(POINT_START)
        .zoom(14.0)
        .bearing(215.0)
        .build()
    )

    // load satellite style and add terrain with a line layer to visualize the route
    mapboxMap.loadStyle(
      style(styleUri = Style.SATELLITE_STREETS) {
        +rasterDemSource(SOURCE) {
          url(TERRAIN_URL_TILE_RESOURCE)
          // 514 specifies padded DEM tile and provides better performance than 512 tiles.
          tileSize(514)
        }
        +terrain(SOURCE) {
          exaggeration(TERRAIN_EXEGERATION)
        }
        +layerAtPosition(
          lineLayer(LINE_LAYER_ID, GEOJSON_SOURCE_ID) {
            lineColor(rgb(255, 79, 60))
            lineWidth(5.0)
          },
          above = LAYER_ABOVE_ID
        )
        +locationIndicatorLayer(LOCATION_LAYER_ID) {
          topImage(FOREGROUND_ICON)
          bearingImage(BACKGROUND_ICON)
          imagePitchDisplacement(5.0)
          topImageSize(1.5)
          bearingImageSize(1.5)
        }
        +image(FOREGROUND_ICON) {
          bitmap(
            bitmapFromDrawableRes(
              this@SantaCatalinaActivity,
              R.drawable.mapbox_mylocation_icon_default
            )!!
          )
        }
        +image(BACKGROUND_ICON) {
          bitmap(
            bitmapFromDrawableRes(
              this@SantaCatalinaActivity,
              R.drawable.mapbox_mylocation_bg_shape
            )!!
          )
        }
      }
    ) { style ->
      // hide road labels
      style.getLayerAs<SymbolLayer>(LAYER_ROAD_ID).visibility(Visibility.NONE)

      // execute direction request
      executeDirectionsRequestForRoute {
        // add a source to visualize the route
        addSourceForRoute(style, it)
        // animate the route
        animateRoute(it)
      }
    }
  }

  /**
   * Executes a directions request.
   * propagates the linestring geometry as high order function.
   */
  private fun executeDirectionsRequestForRoute(animateRoute: (LineString) -> Unit) {
    val client = MapboxDirections.builder()
      .origin(POINT_START)
      .destination(POINT_END)
      .overview(DirectionsCriteria.OVERVIEW_SIMPLIFIED)
      .profile(DirectionsCriteria.PROFILE_WALKING)
      .steps(true)
      .accessToken(getString(R.string.mapbox_access_token))
      .build()
    client.enqueueCall(object : Callback<DirectionsResponse> {
      override fun onResponse(
        call: Call<DirectionsResponse>,
        response: Response<DirectionsResponse>
      ) {
        response.body()?.let { body ->
          if (body.routes().isNotEmpty()) {
            body.routes()[0].geometry()?.let {
              animateRoute(
                LineString.fromPolyline(it, PRECISION_6)
              )
              return
            }
          }
        } ?: throw RuntimeException("Not able to retrieve a directions route")
      }

      override fun onFailure(call: Call<DirectionsResponse>, t: Throwable) {}
    })
  }

  /**
   * Adds a GeoJsonSource to a style to visualize a LineString
   */
  private fun addSourceForRoute(style: Style, lineString: LineString) {
    style.addSource(
      geoJsonSource(GEOJSON_SOURCE_ID) {
        geometry(lineString)
      }
    )
  }

  /**
   * Animate the route using FreeCamera API
   */
  private fun animateRoute(lineString: LineString) {
    // get the overall distance of each route so we can interpolate along them
    val routeDistance = TurfMeasurement.length(
      lineString, TurfConstants.UNIT_KILOMETERS
    )

    mapboxMap.getStyle {
      // get reference to the location layer
      val locationLayer = it.getLayerAs<LocationIndicatorLayer>(LOCATION_LAYER_ID)

      // cache the camera
      val camera = mapboxMap.getFreeCameraOptions()

      // use time animator to animate the lineString and location layer
      timeAnimator = TimeAnimator().apply {
        setTimeListener { animator, totalTime, _ ->

          // phase determines how far through the animation we are
          val phase: Double = totalTime / ANIMATION_DURATION

          // phase is normalized between 0 and 1
          // when the animation is finished, cancel the animation
          if (phase > 1) {
            animator.cancel()
          }

          // use phase to get a point that is the appropriate distance along the route
          val cameraLookingAt = TurfMeasurement.along(
            lineString, routeDistance * phase,
            TurfConstants.UNIT_KILOMETERS
          )

          // at start, we hover the current position first before trailing it
          var cameraPhase = phase - PHASE_DROP_OFF
          if (cameraPhase < 0) {
            cameraPhase = 0.0
          }

          // use phase that trails behind to be able to create a tilted camera
          val cameraLocation = TurfMeasurement.along(
            lineString, routeDistance * cameraPhase,
            TurfConstants.UNIT_KILOMETERS
          )

          // calculate the elevation of the user location
          val elevation = (ELEVATION_MAX * phase) + ELEVATION_MIN

          // Update location indicator
          locationLayer.location(
            listOf(
              cameraLookingAt.latitude(),
              cameraLookingAt.longitude(),
              elevation
            )
          )

          // place the camera above the elevation of the user position
          val elevationDifference = BASE_CAMERA_ELEVATION + elevation

          // set the position and altitude of the camera
          camera.setLocation(cameraLocation, elevationDifference)

          // set the position to look with a decreased elevation for creating tilted camera
          camera.lookAtPoint(cameraLookingAt, elevation)

          // set the updated camera position
          mapboxMap.setCamera(camera)
        }
        duration = ANIMATION_DURATION.toLong()
        start()
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    timeAnimator?.cancel()
  }

  companion object {
    // Style constants
    private const val GEOJSON_SOURCE_ID = "geojson"
    private const val LINE_LAYER_ID = "line"
    private const val LOCATION_LAYER_ID = "location"
    private const val SOURCE = "TERRAIN_SOURCE"
    private const val TERRAIN_URL_TILE_RESOURCE = "mapbox://mapbox.mapbox-terrain-dem-v1"
    private const val TERRAIN_EXEGERATION = 1.7
    private const val FOREGROUND_ICON = "mapbox-location-icon"
    private const val BACKGROUND_ICON = "mapbox-location-stroke-icon"
    private const val LAYER_ABOVE_ID = "bridge-oneway-arrow-white"
    private const val LAYER_ROAD_ID = "road-label"

    // Camera animation constants
    private const val ANIMATION_DURATION = 75000.0
    private const val BASE_CAMERA_ELEVATION = 45.0
    private const val PHASE_DROP_OFF = 0.15
    private const val ELEVATION_MAX = 225.0
    private const val ELEVATION_MIN = 65.0
    private val POINT_START = Point.fromLngLat(-118.33283, 33.33470)
    private val POINT_END = Point.fromLngLat(-118.34183, 33.32387)
  }
}

fun MapView.disablePlugins() {
  scalebar.enabled = false
  compass.enabled = false

  gestures.pitchEnabled = false
  gestures.rotateEnabled = false
  gestures.pinchToZoomEnabled = false
  gestures.doubleTouchToZoomOutEnabled = false
  gestures.doubleTapToZoomInEnabled = false
  gestures.scrollEnabled = false
}
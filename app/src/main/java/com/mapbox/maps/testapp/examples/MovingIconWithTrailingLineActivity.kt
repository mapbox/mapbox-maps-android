package com.mapbox.maps.testapp.examples

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.api.directions.v5.DirectionsCriteria
import com.mapbox.api.directions.v5.MapboxDirections
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.common.Logger
import com.mapbox.core.constants.Constants.PRECISION_6
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.CoordinateBounds
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.addLayerBelow
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.animation.easeTo
import com.mapbox.maps.testapp.R
import com.mapbox.turf.TurfMeasurement
import kotlinx.android.synthetic.main.activity_dds_moving_icon_with_trailing_line.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Make a directions request with the Mapbox Directions API and then draw a line behind a moving
 * SymbolLayer icon which moves along the Directions response route.
 */
class MovingIconWithTrailingLineActivity : AppCompatActivity() {

  private lateinit var pointSource: GeoJsonSource
  private lateinit var lineSource: GeoJsonSource
  private lateinit var routeCoordinateList: MutableList<Point>
  private var markerLinePointList = ArrayList<Point>()

  private var routeIndex: Int = 0
  private lateinit var currentAnimator: Animator

  private var count = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_dds_moving_icon_with_trailing_line)
    mapView.getMapboxMap().loadStyleUri(
      Style.LIGHT
    ) { // Use the Mapbox Directions API to get a directions route
      getRoute()
    }
  }

  /**
   * Add data to the map once the GeoJSON has been loaded
   *
   * @param featureCollection returned GeoJSON FeatureCollection from the Directions API route request
   */
  private fun initData(style: Style, featureCollection: FeatureCollection) {
    featureCollection.features()?.let {
      (it[0].geometry() as? LineString)?.let { lineString ->
        routeCoordinateList = lineString.coordinates()
        initSources(style, featureCollection)
        initSymbolLayer(style)
        initDotLinePath(style)
        animate()
      }
    }
  }

  /**
   * Set up the repeat logic for moving the icon along the route.
   */
  private fun animate() {
    if (routeCoordinateList.size - 1 > routeIndex) {
      val indexPoint = routeCoordinateList[routeIndex]
      val newPoint = Point.fromLngLat(indexPoint.longitude(), indexPoint.latitude())
      currentAnimator = createPointAnimator(indexPoint, newPoint)
      currentAnimator.start()
      routeIndex++
    }
  }

  private fun createPointAnimator(curretPosition: Point, targetPosition: Point): Animator {
    val pointEvaluator = TypeEvaluator<Point> { fraction, startValue, endValue ->
      Point.fromLngLat(
        startValue.longitude() + ((endValue.longitude() - startValue.longitude() * fraction)),
        startValue.latitude() + ((endValue.latitude() - startValue.latitude()) * fraction)
      )
    }
    return ValueAnimator.ofObject(pointEvaluator, curretPosition, targetPosition).apply {
      duration = TurfMeasurement.distance(curretPosition, targetPosition, "meters").toLong()
      interpolator = LinearInterpolator()

      addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
          super.onAnimationEnd(animation)
          animate()
        }
      })

      addUpdateListener { animation ->
        (animation.animatedValue as? Point)?.let {
          markerLinePointList.add(it)
          // Throttle the updates to the GeojsonSource, otherwise the dot won't animate.
          // https://github.com/mapbox/mapbox-maps-android/issues/554
          if (++count % 4 == 0) {
            pointSource.geometry(it)
            lineSource.geometry(LineString.fromLngLats(markerLinePointList))
          }
        }
      }
    }
  }

  private fun getRoute() {
    val client = MapboxDirections.builder()
      .origin(originPoint)
      .destination(destinationPoint)
      .overview(DirectionsCriteria.OVERVIEW_FULL)
      .profile(DirectionsCriteria.PROFILE_WALKING)
      .accessToken(getString(R.string.mapbox_access_token))
      .build()

    client.enqueueCall(object : Callback<DirectionsResponse> {
      override fun onResponse(
        call: Call<DirectionsResponse>,
        response: Response<DirectionsResponse>
      ) {
        response.body()?.let { body ->
          if (body.routes().size < 1) {
            Logger.e(TAG, "No routes found")
            return
          }

          val currentRoute = body.routes()[0]
          mapView.getMapboxMap().getStyle { style ->
            mapView.getMapboxMap().let { mapboxMap ->
              mapboxMap.easeTo(
                mapboxMap.cameraForCoordinateBounds(
                  CoordinateBounds(originPoint, destinationPoint),
                  EdgeInsets(50.0, 50.0, 50.0, 50.0),
                  null,
                  null
                ),
                mapAnimationOptions {
                  duration(5000L)
                }
              )
            }
            currentRoute.geometry()?.let {
              initData(
                style,
                FeatureCollection.fromFeature(
                  Feature.fromGeometry(
                    LineString.fromPolyline(
                      it,
                      PRECISION_6
                    )
                  )
                )
              )
            }
          }
        } ?: run {
          Logger.e(TAG, "No routes found, make sure you set the right user and access token.")
          return
        }
      }

      override fun onFailure(call: Call<DirectionsResponse>, t: Throwable) {
        Logger.e(TAG, "Error: ${t.message}")
        Toast.makeText(
          this@MovingIconWithTrailingLineActivity,
          "Error: ${t.message}",
          Toast.LENGTH_SHORT
        ).show()
      }
    })
  }

  /**
   * Add various sources to the map.
   */
  private fun initSources(style: Style, featureCollection: FeatureCollection) {
    pointSource = geoJsonSource(DOT_SOURCE_ID) {
      featureCollection(featureCollection)
    }
    lineSource = geoJsonSource(LINE_SOURCE_ID) {
      featureCollection(featureCollection)
    }
    style.addSource(pointSource)
    style.addSource(lineSource)
  }

  /**
   * Add the marker icon SymbolLayer.
   */
  private fun initSymbolLayer(style: Style) {
    style.addImage(MARKER_ID, BitmapFactory.decodeResource(resources, R.drawable.pink_dot))
    style.addLayer(
      symbolLayer(SYMBOL_LAYER_ID, DOT_SOURCE_ID) {
        iconImage(MARKER_ID)
        iconSize(1.0)
        iconOffset(listOf(5.0, 5.0))
        iconIgnorePlacement(true)
        iconAllowOverlap(true)
      }
    )
  }

  /**
   * Add the LineLayer for the marker icon's travel route. Adding it under the "road-label" layer, so that the
   * this LineLayer doesn't block the street name.
   */
  private fun initDotLinePath(style: Style) {
    style.addLayerBelow(
      lineLayer(LINE_Layer_ID, LINE_SOURCE_ID) {
        lineColor("#F13C6E")
        lineCap(LineCap.ROUND)
        lineJoin(LineJoin.ROUND)
        lineWidth(4.0)
      },
      below = "road-label"
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
    private const val TAG = "MovingIconWithTrailingLineActivity"
    private const val DOT_SOURCE_ID = "dot-source-id"
    private const val LINE_SOURCE_ID = "line-source-id"
    private const val LINE_Layer_ID = "line-layer-id"
    private const val MARKER_ID = "moving-red-marker"
    private const val SYMBOL_LAYER_ID = "symbol-layer-id"
    private val originPoint = Point.fromLngLat(38.7508, 9.0309)
    private val destinationPoint = Point.fromLngLat(38.795902, 8.984467)
  }
}
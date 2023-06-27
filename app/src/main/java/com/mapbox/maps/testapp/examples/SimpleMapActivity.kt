package com.mapbox.maps.testapp.examples

import android.animation.Animator
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.easeTo
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.testapp.R

/**
 * Example of displaying a map.
 */
class SimpleMapActivity : AppCompatActivity() {

  private val iconImage by lazy {
    BitmapFactory.decodeResource(resources, R.drawable.red_marker)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)
    val mapboxMap = mapView.getMapboxMap()

    mapboxMap.setCamera(
      CameraOptions.Builder()
        .center(Point.fromLngLat(LONGITUDE, LATITUDE))
        .zoom(ZOOM)
        .build()
    )

    val annotationManger = mapView.annotations.createPointAnnotationManager()

    val points = FeatureCollection.fromJson("""
      {
        "type": "FeatureCollection",
        "features": [
          {
            "type": "Feature",
            "properties": {},
            "geometry": {
              "coordinates": [
                -121.98968395774108,
                37.348239492183026
              ],
              "type": "Point"
            }
          },
          {
            "type": "Feature",
            "properties": {},
            "geometry": {
              "coordinates": [
                -121.92776934202291,
                37.3826180722824
              ],
              "type": "Point"
            }
          },
          {
            "type": "Feature",
            "properties": {},
            "geometry": {
              "coordinates": [
                -122.1092724620735,
                37.61812419162797
              ],
              "type": "Point"
            }
          },
          {
            "type": "Feature",
            "properties": {},
            "geometry": {
              "coordinates": [
                -122.18475740452456,
                37.7570593963339
              ],
              "type": "Point"
            }
          },
          {
            "type": "Feature",
            "properties": {},
            "geometry": {
              "coordinates": [
                -122.43750473992192,
                37.75303595237459
              ],
              "type": "Point"
            }
          }
        ]
      }
    """.trimIndent()).features()?.map { it.geometry() as Point }!!

    val annotations = points.map {
      annotationManger.create(
        PointAnnotationOptions()
          .withPoint(it)
          .withIconImage(iconImage)
      )
    }

    Log.d(TAG, "onCreate: $annotations")

    mapboxMap.addOnMapClickListener {
      Log.d(TAG, "zoom before cameraForCoordinates easeTo ${mapboxMap.cameraState.zoom}")

      val cameraOptions = mapboxMap.cameraForCoordinates(points)

      mapboxMap.easeTo(cameraOptions, MapAnimationOptions.mapAnimationOptions {
        animatorListener(object : SimpleAnimatorListener() {
          override fun onAnimationEnd(animator: Animator?) {
            Log.d(TAG, "onAnimationEnd: after ${mapboxMap.cameraState.zoom}")
          }
        })
      })

      false
    }
  }

  companion object {
    private const val TAG = "sd-troubleshooting"
    private const val LATITUDE = 37.72238350042194
    private const val LONGITUDE = -122.2961442458147
    private const val ZOOM = 8.0
  }

  private open class SimpleAnimatorListener : Animator.AnimatorListener {
    override fun onAnimationStart(animator: Animator?) {}
    override fun onAnimationCancel(animator: Animator?) {}
    override fun onAnimationRepeat(animator: Animator?) {}
    override fun onAnimationEnd(animator: Animator?) {}
  }
}
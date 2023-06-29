package com.mapbox.maps.testapp.annotation

import android.graphics.Color
import android.os.Handler
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.google.gson.JsonPrimitive
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.maps.testapp.BaseMapTest
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Instrumented test for verifying annotation updating while changing style
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class UpdateAnnotationWithMultiManagersTest : BaseMapTest() {
  private val latch = CountDownLatch(4)
  private lateinit var pointAnnotationManager: PointAnnotationManager
  private lateinit var circleAnnotationManager: CircleAnnotationManager
  private lateinit var polygonAnnotationManager: PolygonAnnotationManager
  private lateinit var polylineAnnotationManager: PolylineAnnotationManager
  private lateinit var pointAnnotation: PointAnnotation
  private lateinit var circleAnnotation: CircleAnnotation
  private lateinit var polylineAnnotation: PolylineAnnotation
  private lateinit var polygonAnnotation: PolygonAnnotation
  private lateinit var handler: Handler
  private val updateTimes = 100
  private val updateSteps = 0.1
  private val delta = 0.0001
  private val points = mutableListOf(
    mutableListOf(
      Point.fromLngLat(-3.363937, -10.733102),
      Point.fromLngLat(1.754703, -19.716317),
      Point.fromLngLat(-15.747196, -21.085074),
      Point.fromLngLat(-3.363937, -10.733102)
    )
  )
  private val polylinePoints = mutableListOf(
    Point.fromLngLat(-4.375974, -2.178992),
    Point.fromLngLat(-7.639772, -4.107888),
    Point.fromLngLat(-11.439207, 2.798737),
  )

  @Test
  fun testUpdateAnnotation() {
    rule.scenario.onActivity {
      handler = Handler(it.mainLooper)
      it.runOnUiThread {
        mapboxMap.loadStyle(Style.MAPBOX_STREETS) {
          pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
          pointAnnotationManager.textFont = listOf("Open Sans Regular")
          pointAnnotation = pointAnnotationManager.create(
            PointAnnotationOptions()
              .withIconColor(ColorUtils.colorToRgbaString(Color.RED))
              .withIconImage("car-15")
              .withPoint(Point.fromLngLat(0.0, 0.0))
          )
          circleAnnotationManager = mapView.annotations.createCircleAnnotationManager()
          circleAnnotation = circleAnnotationManager.create(
            CircleAnnotationOptions()
              .withCircleColor(Color.RED)
              .withCircleRadius(5.0)
              .withPoint(Point.fromLngLat(0.0, 0.0))
          )

          polygonAnnotationManager = mapView.annotations.createPolygonAnnotationManager()
          polygonAnnotation = polygonAnnotationManager.create(
            PolygonAnnotationOptions()
              .withPoints(points)
              .withData(JsonPrimitive("Foobar"))
              .withFillColor(Color.RED)
          )

          polylineAnnotationManager = mapView.annotations.createPolylineAnnotationManager()
          polylineAnnotation = polylineAnnotationManager.create(
            PolylineAnnotationOptions()
              .withPoints(polylinePoints)
              .withLineColor(Color.RED)
              .withLineWidth(5.0)
          )

          Thread {
            for (i in 1..updateTimes) {
              handler.post {
                pointAnnotation.point = Point.fromLngLat(
                  pointAnnotation.point.longitude() + updateSteps,
                  pointAnnotation.point.latitude() + updateSteps
                )
                pointAnnotationManager.update(pointAnnotation)
              }
              Thread.sleep(10)
            }
            latch.countDown()
          }.start()

          Thread {
            for (i in 1..updateTimes) {
              handler.post {
                circleAnnotation.point = Point.fromLngLat(
                  circleAnnotation.point.longitude() + updateSteps,
                  circleAnnotation.point.latitude() + updateSteps
                )
                circleAnnotationManager.update(circleAnnotation)
              }
              Thread.sleep(10)
            }
            latch.countDown()
          }.start()

          Thread {
            for (i in 1..updateTimes) {
              handler.post {
                points[0][0] = Point.fromLngLat(
                  points[0][0].longitude() + updateSteps,
                  points[0][0].latitude() + updateSteps
                )
                polygonAnnotation.points = points
                polygonAnnotationManager.update(polygonAnnotation)
              }
              Thread.sleep(10)
            }
            latch.countDown()
          }.start()
          Thread {
            for (i in 1..updateTimes) {
              handler.post {
                polylinePoints[0] = Point.fromLngLat(
                  polylinePoints[0].longitude() + updateSteps,
                  polylinePoints[0].latitude() + updateSteps
                )
                polylineAnnotation.points = polylinePoints
                polylineAnnotationManager.update(polylineAnnotation)
              }
              Thread.sleep(10)
            }
            latch.countDown()
          }.start()
        }
      }
    }
    if (!latch.await(3000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    assertEquals(pointAnnotation.point.latitude(), updateSteps * updateTimes, delta)
    assertEquals(pointAnnotation.point.longitude(), updateSteps * updateTimes, delta)
    assertEquals(circleAnnotation.point.longitude(), updateSteps * updateTimes, delta)
    assertEquals(circleAnnotation.point.latitude(), updateSteps * updateTimes, delta)
    assertEquals(polylineAnnotation.points[0].longitude(), polylinePoints[0].longitude(), delta)
    assertEquals(polylineAnnotation.points[0].latitude(), polylinePoints[0].latitude(), delta)
    assertEquals(polygonAnnotation.points[0][0].longitude(), points[0][0].longitude(), delta)
    assertEquals(polygonAnnotation.points[0][0].latitude(), points[0][0].latitude(), delta)
  }
}
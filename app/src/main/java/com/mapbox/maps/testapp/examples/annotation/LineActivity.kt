package com.mapbox.maps.testapp.examples.annotation

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.maps.plugin.annotation.getAnnotationPlugin
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.utils.Assets
import kotlinx.android.synthetic.main.activity_add_marker_symbol.*
import java.io.IOException
import java.util.*

/**
 * Example showing how to add Line annotations
 */
class LineActivity : AppCompatActivity() {
  private val random = Random()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_annotation)
    mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS) {
      val annotationPlugin = mapView.getAnnotationPlugin()
      val lineManager = annotationPlugin.getLineManager()
      lineManager.addClickListener(
        OnLineClickListener {
          Toast.makeText(
            this@LineActivity,
            "click",
            Toast.LENGTH_LONG
          ).show()
          false
        }
      )

      val points = listOf(
        Point.fromLngLat(-4.375974, -2.178992),
        Point.fromLngLat(-7.639772, -4.107888),
        Point.fromLngLat(-11.439207, 2.798737),
      )

      val lineOptions: LineOptions = LineOptions()
        .withPoints(points)
        .withLineColor(ColorUtils.colorToRgbaString(Color.RED))
        .withLineWidth(5.0)
      lineManager.create(lineOptions)

      // random add lines across the globe
      val lists: MutableList<List<Point>> = ArrayList<List<Point>>()
      for (i in 0..99) {
        lists.add(createRandomPoints())
      }
      val lineOptionsList = lists.map {
        val color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
        LineOptions()
          .withPoints(it)
          .withLineColor(ColorUtils.colorToRgbaString(color))
      }

      lineManager.create(lineOptionsList)

      try {
        lineManager.create(
          FeatureCollection.fromJson(
            Assets.loadStringFromAssets(
              this,
              "annotations.json"
            )
          )
        )
      } catch (e: IOException) {
        throw RuntimeException("Unable to parse annotations.json")
      }
    }
  }

  private fun createRandomPoints(): List<Point> {
    val points: MutableList<Point> = ArrayList<Point>()
    for (i in 0 until random.nextInt(10)) {
      points.add(
        Point.fromLngLat(
          random.nextDouble() * -360.0 + 180.0,
          random.nextDouble() * -180.0 + 90.0
        )
      )
    }
    return points
  }
}
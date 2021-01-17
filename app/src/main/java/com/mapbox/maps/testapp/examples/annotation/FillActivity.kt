package com.mapbox.maps.testapp.examples.annotation

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonPrimitive
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
 * Example showing how to add Fill annotations
 */
class FillActivity : AppCompatActivity() {
  private val random = Random()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_annotation)
    mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS) {
      val annotationPlugin = mapView.getAnnotationPlugin()
      val fillManager = annotationPlugin.getFillManager()
      fillManager.addClickListener(
        OnFillClickListener {
          Toast.makeText(this@FillActivity, "click", Toast.LENGTH_LONG).show()
          false
        }
      )
      val points = listOf(
        listOf(
          Point.fromLngLat(-3.363937, -10.733102),
          Point.fromLngLat(1.754703, -19.716317),
          Point.fromLngLat(-15.747196, -21.085074),
          Point.fromLngLat(-3.363937, -10.733102)
        )
      )

      val fillOptions: FillOptions = FillOptions()
        .withPoints(points)
        .withData(JsonPrimitive("Foobar"))
        .withFillColor(ColorUtils.colorToRgbaString(Color.RED))
      fillManager.create(fillOptions)

      // random add fills across the globe
      val fillOptionsList: MutableList<FillOptions> = ArrayList()
      for (i in 0..2) {
        val color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
        fillOptionsList.add(
          FillOptions()
            .withPoints(createRandomPoints())
            .withFillColor(ColorUtils.colorToRgbaString(color))
        )
      }
      fillManager.create(fillOptionsList)

      try {
        fillManager.create(
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

  private fun createRandomPoints(): List<List<Point>> {
    val points = mutableListOf<Point>()
    val firstLast = Point.fromLngLat(
      random.nextDouble() * -360.0 + 180.0,
      random.nextDouble() * -180.0 + 90.0
    )
    points.add(firstLast)
    for (i in 0 until random.nextInt(10)) {
      points.add(
        Point.fromLngLat(
          random.nextDouble() * -360.0 + 180.0,
          random.nextDouble() * -180.0 + 90.0
        )
      )
    }
    points.add(firstLast)
    return listOf(points)
  }
}
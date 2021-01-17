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
 * Example showing how to add Circle annotations
 */
class CircleActivity : AppCompatActivity() {
  private val random = Random()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_annotation)
    mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS) {
      val annotationPlugin = mapView.getAnnotationPlugin()
      val circleManager = annotationPlugin.getCircleManager()
      circleManager.addClickListener(
        OnCircleClickListener {
          Toast.makeText(this@CircleActivity, "click", Toast.LENGTH_LONG).show()
          false
        }
      )

      val circleOptions: CircleOptions = CircleOptions()
        .withPoint(Point.fromLngLat(0.381457, 6.687337))
        .withCircleColor(ColorUtils.colorToRgbaString(Color.YELLOW))
        .withCircleRadius(12.0)
        .withDraggable(true)
      circleManager.create(circleOptions)

      // random add circles across the globe
      val circleOptionsList: MutableList<CircleOptions> = ArrayList()
      for (i in 0..20) {
        val color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
        circleOptionsList.add(
          CircleOptions()
            .withPoint(createRandomPoints())
            .withCircleColor(ColorUtils.colorToRgbaString(color))
            .withCircleRadius(8.0)
            .withDraggable(true)
        )
      }
      circleManager.create(circleOptionsList)

      try {
        circleManager.create(
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

  private fun createRandomPoints(): Point {
    return Point.fromLngLat(
      random.nextDouble() * -360.0 + 180.0,
      random.nextDouble() * -180.0 + 90.0
    )
  }
}
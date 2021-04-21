package com.mapbox.maps.testapp.examples.annotation

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_add_marker_symbol.*
import kotlinx.android.synthetic.main.activity_add_marker_symbol.mapView
import kotlinx.android.synthetic.main.activity_annotation.*
import java.util.*

/**
 * Example showing how to add Circle annotations
 */
class CircleAnnotationActivity : AppCompatActivity() {
  private val random = Random()
  private var circleManager: CircleAnnotationManager? = null
  private var index: Int = 0
  private val nextStyle: String
    get() {
      return AnnotationUtils.STYLES[index++ % AnnotationUtils.STYLES.size]
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_annotation)
    mapView.getMapboxMap().loadStyleUri(nextStyle) {
      val annotationPlugin = mapView.annotations
      circleManager = annotationPlugin.createCircleAnnotationManager(mapView).apply {
        addClickListener(
          OnCircleAnnotationClickListener {
            Toast.makeText(this@CircleAnnotationActivity, "click", Toast.LENGTH_LONG).show()
            false
          }
        )

        val circleAnnotationOptions: CircleAnnotationOptions = CircleAnnotationOptions()
          .withPoint(Point.fromLngLat(CIRCLE_LONGITUDE, CIRCLE_LATITUDE))
          .withCircleColor(Color.YELLOW)
          .withCircleRadius(12.0)
          .withDraggable(true)
        create(circleAnnotationOptions)

        // random add circles across the globe
        val circleAnnotationOptionsList: MutableList<CircleAnnotationOptions> = ArrayList()
        for (i in 0..20) {
          val color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
          circleAnnotationOptionsList.add(
            CircleAnnotationOptions()
              .withPoint(AnnotationUtils.createRandomPoint())
              .withCircleColor(color)
              .withCircleRadius(8.0)
              .withDraggable(true)
          )
        }
        create(circleAnnotationOptionsList)

        AnnotationUtils.loadStringFromAssets(
          this@CircleAnnotationActivity, "annotations.json"
        )?.let {
          create(FeatureCollection.fromJson(it))
        }
      }
    }

    deleteAll.setOnClickListener { circleManager?.deleteAll() }
    changeStyle.setOnClickListener {
      mapView.getMapboxMap().loadStyleUri(nextStyle)
    }
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
    private const val CIRCLE_LONGITUDE = 0.381457
    private const val CIRCLE_LATITUDE = 6.687337
  }
}
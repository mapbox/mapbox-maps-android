package com.mapbox.maps.testapp.examples.markersandcallouts

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonPrimitive
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.maps.testapp.databinding.ActivityAnnotationBinding
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils
import java.util.*

/**
 * Example showing how to add Polygone annotations
 */
class PolygoneAnnotationActivity : AppCompatActivity() {
  private val random = Random()
  private var polygonAnnotationManager: PolygonAnnotationManager? = null
  private var index: Int = 0
  private val nextStyle: String
    get() {
      return AnnotationUtils.STYLES[index++ % AnnotationUtils.STYLES.size]
    }
  private lateinit var annotationPlugin: AnnotationPlugin

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityAnnotationBinding.inflate(layoutInflater)
    setContentView(binding.root)
    binding.mapView.getMapboxMap().flyTo(CameraOptions.Builder().zoom(14.0).center(Point.fromLngLat(35.53110449138368,24.02974060478422)).build())
    binding.mapView.getMapboxMap().loadStyleUri(nextStyle) {

      annotationPlugin = binding.mapView.annotations
      polygonAnnotationManager = annotationPlugin.createPolygonAnnotationManager(binding.mapView).apply {
        addClickListener(
          OnPolygonAnnotationClickListener {
            Toast.makeText(this@PolygoneAnnotationActivity, "click ${it.id}", Toast.LENGTH_SHORT)
              .show()
            false
          }
        )

        addInteractionListener(object : OnPolygonAnnotationInteractionListener {
          override fun onSelectAnnotation(annotation: PolygonAnnotation) {
            Toast.makeText(
              this@PolygoneAnnotationActivity,
              "onSelectAnnotation ${annotation.id}",
              Toast.LENGTH_SHORT
            ).show()
          }

          override fun onDeselectAnnotation(annotation: PolygonAnnotation) {
            Toast.makeText(
              this@PolygoneAnnotationActivity,
              "onDeselectAnnotation ${annotation.id}",
              Toast.LENGTH_SHORT
            ).show()
          }
        })

        val points = listOf(
          listOf(
            Point.fromLngLat(35.52482636074315,24.014679947948405),
            Point.fromLngLat(35.51114716956354,24.01391178034676),
            Point.fromLngLat(35.50374631403231,24.02820018964633),
            Point.fromLngLat(35.51002341064811,24.043258382501087),
            Point.fromLngLat(35.52370239640629,24.044030629840638),
            Point.fromLngLat(35.53110449138368,24.02974060478422)
          )
        )
        val polygonAnnotationOptions: PolygonAnnotationOptions = PolygonAnnotationOptions()
          .withPoints(points)
          .withData(JsonPrimitive("Foobar"))
          .withFillColor(Color.RED)
        create(polygonAnnotationOptions)

//        // random add fills across the globe
//        val polygonAnnotationOptionsList: MutableList<PolygonAnnotationOptions> = ArrayList()
//        for (i in 0..2) {
//          val color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
//          polygonAnnotationOptionsList.add(
//            PolygonAnnotationOptions()
//              .withPoints(AnnotationUtils.createRandomPointsList())
//              .withFillColor(color)
//          )
//        }
//        create(polygonAnnotationOptionsList)
//
//        AnnotationUtils.loadStringFromAssets(
//          this@PolygoneAnnotationActivity,
//          "annotations.json"
//        )?.let {
//          create(FeatureCollection.fromJson(it))
//        }
      }
    }

    binding.deleteAll.setOnClickListener {
      polygonAnnotationManager?.let {
        annotationPlugin.removeAnnotationManager(it)
      }
    }
    binding.changeStyle.setOnClickListener { binding.mapView.getMapboxMap().loadStyleUri(nextStyle) }
  }
}
package com.mapbox.maps.testapp.examples.markersandcallouts

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonPrimitive
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.maps.testapp.databinding.ActivityAnnotationBinding
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils

/**
 * Example showing how to add Polygone annotations
 */
class PolygoneAnnotationActivity : AppCompatActivity() {
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
    binding.mapView.getMapboxMap().setCamera(INITIAL_CAMERA_POS)
    binding.mapView.getMapboxMap().loadStyleUri(nextStyle) {
      annotationPlugin = binding.mapView.annotations
      polygonAnnotationManager = annotationPlugin.createPolygonAnnotationManager().apply {
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
            Point.fromLngLat(-89.857177734375, 24.51713945052515),
            Point.fromLngLat(-87.967529296875, 24.51713945052515),
            Point.fromLngLat(-87.967529296875, 26.244156283890756),
            Point.fromLngLat(-89.857177734375, 26.244156283890756),
            Point.fromLngLat(-89.857177734375, 24.51713945052515)
          )
        )

        val polygonAnnotationOptions: PolygonAnnotationOptions = PolygonAnnotationOptions()
          .withPoints(points)
          .withData(JsonPrimitive("Foobar"))
          .withFillColor(Color.RED)
          .withDraggable(true)
        create(polygonAnnotationOptions)

        AnnotationUtils.loadStringFromAssets(
          this@PolygoneAnnotationActivity,
          "annotations.json"
        )?.let {
          create(FeatureCollection.fromJson(it))
        }
      }
    }

    binding.deleteAll.setOnClickListener {
      polygonAnnotationManager?.let {
        annotationPlugin.removeAnnotationManager(it)
      }
    }
    binding.changeStyle.setOnClickListener { binding.mapView.getMapboxMap().loadStyleUri(nextStyle) }
  }

  private companion object {
    private val INITIAL_CAMERA_POS = cameraOptions {
      center(Point.fromLngLat(-88.90136, 25.04579))
      zoom(5.0)
    }
  }
}
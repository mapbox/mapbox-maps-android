package com.mapbox.maps.testapp.examples.markersandcallouts

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.extension.style.layers.getLayer
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.maps.testapp.databinding.ActivityAnnotationBinding
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils
import java.util.*

/**
 * Example showing how to add Line annotations
 */
class PolylineAnnotationActivity : AppCompatActivity() {
  private val random = Random()
  private var polylineAnnotationManager: PolylineAnnotationManager? = null
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
    binding.mapView.getMapboxMap().setCamera(
      CameraOptions.Builder()
        .center(Point.fromLngLat(24.945749, 60.171924))
        .pitch(45.0)
        .zoom(9.5)
        .bearing(-17.6)
        .build()
    )
    binding.mapView.getMapboxMap().loadStyle(nextStyle) {
      annotationPlugin = binding.mapView.annotations
      polylineAnnotationManager = annotationPlugin.createPolylineAnnotationManager(
        annotationConfig = AnnotationConfig(PITCH_OUTLINE, LAYER_ID, SOURCE_ID)
      ).apply {
        it.getLayer(LAYER_ID)?.let { layer ->
          Toast.makeText(this@PolylineAnnotationActivity, layer.layerId, Toast.LENGTH_LONG).show()
        }
        addClickListener(
          OnPolylineAnnotationClickListener {
            Toast.makeText(this@PolylineAnnotationActivity, "click ${it.id}", Toast.LENGTH_SHORT)
              .show()
            false
          }
        )

        addInteractionListener(object : OnPolylineAnnotationInteractionListener {
          override fun onSelectAnnotation(annotation: PolylineAnnotation) {
            Toast.makeText(
              this@PolylineAnnotationActivity,
              "onSelectAnnotation ${annotation.id}",
              Toast.LENGTH_SHORT
            ).show()
          }

          override fun onDeselectAnnotation(annotation: PolylineAnnotation) {
            Toast.makeText(
              this@PolylineAnnotationActivity,
              "onDeselectAnnotation ${annotation.id}",
              Toast.LENGTH_SHORT
            ).show()
          }
        })

        val points = listOf(
          Point.fromLngLat(-4.375974, -2.178992),
          Point.fromLngLat(-7.639772, -4.107888),
          Point.fromLngLat(-11.439207, 2.798737),
        )

        val polylineAnnotationOptions: PolylineAnnotationOptions = PolylineAnnotationOptions()
          .withPoints(points)
          .withLineColor(Color.RED)
          .withLineWidth(5.0)
        create(polylineAnnotationOptions)

        // random add lines across the globe
        val lists: MutableList<List<Point>> = ArrayList<List<Point>>()
        for (i in 0..99) {
          lists.add(AnnotationUtils.createRandomPoints())
        }
        val lineOptionsList = lists.map {
          val color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
          PolylineAnnotationOptions()
            .withPoints(it)
            .withLineColor(color)
        }

        create(lineOptionsList)

        AnnotationUtils.loadStringFromAssets(
          this@PolylineAnnotationActivity,
          "annotations.json"
        )?.let {
          create(FeatureCollection.fromJson(it))
        }
      }
    }

    binding.deleteAll.setOnClickListener {
      polylineAnnotationManager?.let {
        annotationPlugin.removeAnnotationManager(it)
      }
    }
    binding.changeStyle.setOnClickListener {
      binding.mapView.getMapboxMap().loadStyle(nextStyle)
    }
  }

  companion object {
    private const val LAYER_ID = "line_layer"
    private const val SOURCE_ID = "line_source"
    private const val PITCH_OUTLINE = "pitch-outline"
  }
}
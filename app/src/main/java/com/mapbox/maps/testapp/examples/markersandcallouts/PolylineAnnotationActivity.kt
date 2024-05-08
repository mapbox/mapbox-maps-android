package com.mapbox.maps.testapp.examples.markersandcallouts

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.extension.style.layers.getLayer
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.OnPolylineAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.OnPolylineAnnotationInteractionListener
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotation
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPolylineAnnotationManager
import com.mapbox.maps.testapp.databinding.ActivityAnnotationBinding
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils.showShortToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Random

/**
 * Example showing how to add Line annotations
 */
class PolylineAnnotationActivity : AppCompatActivity() {
  private val random = Random()
  private var polylineAnnotationManager: PolylineAnnotationManager? = null
  private var styleIndex: Int = 0
  private var slotIndex: Int = 0
  private val nextStyle: String
    get() {
      return AnnotationUtils.STYLES[styleIndex++ % AnnotationUtils.STYLES.size]
    }
  private val nextSlot: String
    get() {
      return AnnotationUtils.SLOTS[slotIndex++ % AnnotationUtils.SLOTS.size]
    }

  private lateinit var annotationPlugin: AnnotationPlugin

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityAnnotationBinding.inflate(layoutInflater)
    setContentView(binding.root)
    binding.mapView.mapboxMap.setCamera(
      CameraOptions.Builder()
        .center(Point.fromLngLat(24.945749, 60.171924))
        .pitch(0.0)
        .zoom(4.0)
        .bearing(0.0)
        .build()
    )
    binding.mapView.mapboxMap.loadStyle(nextStyle) {
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
        val lineOptionsList = List(100) {
          val color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
          PolylineAnnotationOptions()
            .withPoints(AnnotationUtils.createRandomPoints())
            .withLineColor(color)
        }

        create(lineOptionsList)

        lifecycleScope.launch {
          val featureCollection = withContext(Dispatchers.Default) {
            FeatureCollection.fromJson(
              AnnotationUtils.loadStringFromAssets(
                this@PolylineAnnotationActivity,
                "annotations.json"
              )
            )
          }
          create(featureCollection)
        }
      }
    }

    binding.deleteAll.setOnClickListener {
      polylineAnnotationManager?.let {
        annotationPlugin.removeAnnotationManager(it)
      }
    }
    binding.changeStyle.setOnClickListener {
      binding.mapView.mapboxMap.loadStyle(nextStyle)
    }
    binding.changeSlot.setOnClickListener {
      val slot = nextSlot
      showShortToast("Switching to $slot slot")
      polylineAnnotationManager?.slot = slot
    }
  }

  companion object {
    private const val LAYER_ID = "line_layer"
    private const val SOURCE_ID = "line_source"
    private const val PITCH_OUTLINE = "pitch-outline"
  }
}
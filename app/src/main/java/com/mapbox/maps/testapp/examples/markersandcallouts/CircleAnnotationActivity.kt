package com.mapbox.maps.testapp.examples.markersandcallouts

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotation
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.OnCircleAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.OnCircleAnnotationInteractionListener
import com.mapbox.maps.plugin.annotation.generated.OnCircleAnnotationLongClickListener
import com.mapbox.maps.plugin.annotation.generated.createCircleAnnotationManager
import com.mapbox.maps.testapp.databinding.ActivityAnnotationBinding
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils.showShortToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Random

/**
 * Example showing how to add Circle annotations
 */
class CircleAnnotationActivity : AppCompatActivity() {
  private val random = Random()
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
  private lateinit var binding: ActivityAnnotationBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityAnnotationBinding.inflate(layoutInflater)
    setContentView(binding.root)
    // Load initial style
    switchToNextStyle()
    Toast.makeText(this, "Long click a circle to enable dragging.", Toast.LENGTH_LONG).show()
    annotationPlugin = binding.mapView.annotations
    val circleAnnotationManager = annotationPlugin.createCircleAnnotationManager().apply {
      // Setup the default properties for all annotations added to this manager
      circleColorInt = Color.YELLOW
      circleRadius = 8.0

      addClickListener(
        OnCircleAnnotationClickListener {
          Toast.makeText(this@CircleAnnotationActivity, "click: ${it.id}", Toast.LENGTH_SHORT)
            .show()
          false
        }
      )
      addLongClickListener(
        OnCircleAnnotationLongClickListener {
          it.isDraggable = true
          Toast.makeText(this@CircleAnnotationActivity, "long click: ${it.id}", Toast.LENGTH_SHORT)
            .show()
          false
        }
      )
      binding.mapView.mapboxMap.setCamera(
        CameraOptions.Builder()
          .center(Point.fromLngLat(CIRCLE_LONGITUDE, CIRCLE_LATITUDE))
          .pitch(0.0)
          .zoom(3.0)
          .bearing(0.0)
          .build()
      )
      addInteractionListener(
        object : OnCircleAnnotationInteractionListener {
          override fun onSelectAnnotation(annotation: CircleAnnotation) {
            Toast.makeText(
              this@CircleAnnotationActivity,
              "onSelectAnnotation: ${annotation.id}",
              Toast.LENGTH_SHORT
            ).show()
          }

          override fun onDeselectAnnotation(annotation: CircleAnnotation) {
            Toast.makeText(
              this@CircleAnnotationActivity,
              "onDeselectAnnotation: ${annotation.id}",
              Toast.LENGTH_SHORT
            ).show()
          }
        }
      )

      val circleAnnotationOptions: CircleAnnotationOptions = CircleAnnotationOptions()
        .withPoint(Point.fromLngLat(CIRCLE_LONGITUDE, CIRCLE_LATITUDE))
        // overwrite circleAnnotationManager base circle annotation's radius to the specific value of 12.0
        .withCircleRadius(12.0)
        .withDraggable(false)
      create(circleAnnotationOptions)

      lifecycleScope.launch {
        // random add circles across the globe
        val circleAnnotationOptionsList = withContext(Dispatchers.Default) {
          List(2_000) {
            val color =
              Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
            CircleAnnotationOptions()
              .withPoint(AnnotationUtils.createRandomPoint())
              // overwrite circleAnnotationManager base circle color for this specific annotation to random color.
              .withCircleColor(color)
              .withDraggable(false)
          }
        }
        create(circleAnnotationOptionsList)
        val annotationsJsonContents = withContext(Dispatchers.Default) {
          FeatureCollection.fromJson(
            AnnotationUtils.loadStringFromAssets(
              this@CircleAnnotationActivity,
              "annotations.json"
            )
          )
        }
        create(annotationsJsonContents)
      }
    }

    binding.deleteAll.setOnClickListener {
      annotationPlugin.removeAnnotationManager(circleAnnotationManager)
    }
    binding.changeStyle.setOnClickListener {
      switchToNextStyle()
    }
    binding.changeSlot.setOnClickListener {
      val slot = nextSlot
      showShortToast("Switching to $slot slot")
      circleAnnotationManager.slot = slot
    }
  }

  private fun switchToNextStyle() {
    val style = nextStyle
    binding.mapView.mapboxMap.loadStyle(style)
    // only standard based styles support slots
    binding.changeSlot.isEnabled = (style == Style.STANDARD || style == Style.STANDARD_SATELLITE)
  }

  companion object {
    private const val CIRCLE_LONGITUDE = 0.381457
    private const val CIRCLE_LATITUDE = 6.687337
  }
}
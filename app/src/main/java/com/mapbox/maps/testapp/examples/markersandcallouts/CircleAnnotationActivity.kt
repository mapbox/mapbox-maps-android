package com.mapbox.maps.testapp.examples.markersandcallouts

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.maps.testapp.databinding.ActivityAnnotationBinding
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils.showShortToast
import java.util.*

/**
 * Example showing how to add Circle annotations
 */
class CircleAnnotationActivity : AppCompatActivity() {
  private val random = Random()
  private var circleAnnotationManager: CircleAnnotationManager? = null
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
    Toast.makeText(this, "Long click a circle to start dragging.", Toast.LENGTH_LONG).show()
    annotationPlugin = binding.mapView.annotations
    circleAnnotationManager = annotationPlugin.createCircleAnnotationManager().apply {
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
          .zoom(5.0)
          .bearing(0.0)
          .build()
      )
      binding.mapView.mapboxMap.loadStyle(nextStyle) {
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
          .withCircleColor(Color.YELLOW)
          .withCircleRadius(12.0)
          .withDraggable(false)
        create(circleAnnotationOptions)

        // random add circles across the globe
        val circleAnnotationOptionsList: MutableList<CircleAnnotationOptions> = ArrayList()
        for (i in 0..2000) {
          val color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
          circleAnnotationOptionsList.add(
            CircleAnnotationOptions()
              .withPoint(AnnotationUtils.createRandomPoint())
              .withCircleColor(color)
              .withCircleRadius(8.0)
              .withDraggable(false)
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

    binding.deleteAll.setOnClickListener {
      circleAnnotationManager?.let {
        annotationPlugin.removeAnnotationManager(it)
      }
    }
    binding.changeStyle.setOnClickListener {
      binding.mapView.mapboxMap.loadStyle(nextStyle)
    }
    binding.changeSlot.setOnClickListener {
      val slot = nextSlot
      showShortToast("Switching to $slot slot")
      circleAnnotationManager?.slot = slot
    }
  }

  companion object {
    private const val CIRCLE_LONGITUDE = 0.381457
    private const val CIRCLE_LATITUDE = 6.687337
  }
}
package com.mapbox.maps.testapp.examples

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.style.layers.addLayerBelow
import com.mapbox.maps.extension.style.layers.generated.fillLayer
import com.mapbox.maps.extension.style.layers.generated.slotLayer
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPolygonAnnotationManager
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivitySlotLayerBinding
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils.showShortToast
import com.mapbox.turf.TurfConstants
import com.mapbox.turf.TurfMeasurement

/**
 * Example showcasing the usage of [com.mapbox.maps.extension.style.layers.generated.SlotLayer].
 */
class SlotLayerActivity : AppCompatActivity() {

  private lateinit var binding: ActivitySlotLayerBinding
  private lateinit var mapboxMap: MapboxMap
  private lateinit var annotationManager: PolygonAnnotationManager

  @OptIn(MapboxExperimental::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySlotLayerBinding.inflate(layoutInflater)
    setContentView(binding.root)
    mapboxMap = binding.mapView.mapboxMap
    mapboxMap.loadStyle(createStyle()) {
      mapboxMap.setCamera(START_CAMERA_POSITION)
      annotationManager = binding.mapView.annotations.createPolygonAnnotationManager()
      // We set annotations in a custom slot (below "middle") associated with slotLayer
      annotationManager.slot = SLOT_LAYER_ID
      annotationManager.create(createTriangleAnnotationOption())
      binding.addOrRemoveSlotLayer.setOnClickListener {
        swapLayer()
        showShortToast("Current slots: ${mapboxMap.style?.styleSlots}")
      }
    }
  }

  private fun swapLayer() {
    mapboxMap.getStyle { style ->
      if (style.styleLayerExists(SLOT_LAYER_ID)) {
        style.removeStyleLayer(SLOT_LAYER_ID)
        // move annotation to a "middle" slot - annotation will move above the rectangle
        annotationManager.slot = MIDDLE_SLOT
        binding.addOrRemoveSlotLayer.setImageResource(R.drawable.ic_layers)
      } else {
        // Add SlotLayer below FillLayer to keep the original order as shown in createStyle()
        style.addLayerBelow(
          slotLayer(SLOT_LAYER_ID) {
            slot(MIDDLE_SLOT)
          },
          below = LAYER_ID
        )
        // move annotation to a custom slot below "middle" - annotation will move below the rectangle
        annotationManager.slot = SLOT_LAYER_ID
        binding.addOrRemoveSlotLayer.setImageResource(R.drawable.ic_layers_clear)
      }
    }
  }

  private fun createStyle() = style(style = Style.STANDARD) {
    +geoJsonSource(SOURCE_ID) {
      geometry(Polygon.fromLngLats(RECTANGLE_POINTS))
    }

    // Placeholder for annotations.
    // Added new layer will be placed before the items in the "middle" slot.
    +slotLayer(SLOT_LAYER_ID) {
      slot(MIDDLE_SLOT)
    }

    // Rendered rectangle will be placed above the annotation placeholder in middle slot.
    +fillLayer(LAYER_ID, SOURCE_ID) {
      fillOpacity(0.8)
      fillColor(ContextCompat.getColor(this@SlotLayerActivity, R.color.pink))
      slot(MIDDLE_SLOT)
    }
  }

  private fun createTriangleAnnotationOption(): PolygonAnnotationOptions {
    val vertex1 = TurfMeasurement.destination(
      CENTER, 500.0, 45.0, TurfConstants.UNIT_METERS
    )

    val vertex2 = TurfMeasurement.destination(
      CENTER, 500.0, -45.0, TurfConstants.UNIT_METERS
    )

    val trianglePoints = listOf(
      listOf(
        CENTER,
        vertex1,
        vertex2,
        CENTER,
      )
    )

    return PolygonAnnotationOptions()
      .withPoints(trianglePoints)
      .withFillColor(Color.YELLOW)
  }

  companion object {
    private const val LAYER_ID = "layer-id"
    private const val SOURCE_ID = "source-id"
    private const val SLOT_LAYER_ID = "annotation-placeholder"
    private const val MIDDLE_SLOT = "middle"
    private val CENTER = Point.fromLngLat(24.9389, 60.1699)
    private val START_CAMERA_POSITION = cameraOptions {
      center(CENTER)
      zoom(14.0)
      bearing(0.0)
      pitch(0.0)
    }

    private val RECTANGLE_POINTS = listOf(
      listOf(
        Point.fromLngLat(24.9349, 60.1725),
        Point.fromLngLat(24.9429, 60.1725),
        Point.fromLngLat(24.9429, 60.1673),
        Point.fromLngLat(24.9349, 60.1673),
        Point.fromLngLat(24.9349, 60.1725),
      )
    )
  }
}
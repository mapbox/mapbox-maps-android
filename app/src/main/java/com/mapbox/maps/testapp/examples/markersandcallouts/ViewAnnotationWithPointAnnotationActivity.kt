package com.mapbox.maps.testapp.examples.markersandcallouts

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.plugin.annotation.Annotation
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityViewAnnotationShowcaseBinding
import com.mapbox.maps.testapp.databinding.ItemCalloutViewBinding
import com.mapbox.maps.testapp.utils.BitmapUtils
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import com.mapbox.maps.viewannotation.ViewAnnotationUpdateMode
import com.mapbox.maps.viewannotation.viewAnnotationOptions

/**
 * Example how to add view annotation to the point annotation.
 */
class ViewAnnotationWithPointAnnotationActivity : AppCompatActivity() {

  private lateinit var viewAnnotationManager: ViewAnnotationManager
  private lateinit var pointAnnotationManager: PointAnnotationManager
  private lateinit var pointAnnotation: PointAnnotation
  private lateinit var viewAnnotation: View

  @SuppressLint("SetTextI18n")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityViewAnnotationShowcaseBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val iconBitmap = BitmapUtils.bitmapFromDrawableRes(
      this@ViewAnnotationWithPointAnnotationActivity,
      R.drawable.blue_marker_view
    )!!

    viewAnnotationManager = binding.mapView.viewAnnotationManager

    binding.mapView.getMapboxMap().loadStyle(Style.STANDARD) {
      prepareAnnotationMarker(binding.mapView, iconBitmap)
      prepareViewAnnotation()
      // show / hide view annotation based on a marker click
      pointAnnotationManager.addClickListener { clickedAnnotation ->
        if (pointAnnotation == clickedAnnotation) {
          viewAnnotation.toggleViewVisibility()
        }
        true
      }
      // show / hide view annotation based on marker visibility
      binding.fabStyleToggle.setOnClickListener {
        pointAnnotation.iconImageBitmap = if (pointAnnotation.iconImage == null) iconBitmap else null
        pointAnnotationManager.update(pointAnnotation)
      }
      // update view annotation geometry if dragging the marker
      pointAnnotationManager.addDragListener(object : OnPointAnnotationDragListener {
        override fun onAnnotationDragStarted(annotation: Annotation<*>) {
        }

        override fun onAnnotationDrag(annotation: Annotation<*>) {
          if (annotation == pointAnnotation) {
            binding.mapView.viewAnnotationManager.updateViewAnnotation(
              viewAnnotation,
              viewAnnotationOptions {
                geometry(pointAnnotation.geometry)
              }
            )
            ItemCalloutViewBinding.bind(viewAnnotation).apply {
              textNativeView.text = "lat=%.2f\nlon=%.2f".format(
                pointAnnotation.geometry.latitude(),
                pointAnnotation.geometry.longitude()
              )
            }
          }
        }

        override fun onAnnotationDragFinished(annotation: Annotation<*>) {
        }
      })
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_view_annotation, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_view_annotation_fixed_delay -> {
        viewAnnotationManager.setViewAnnotationUpdateMode(ViewAnnotationUpdateMode.MAP_FIXED_DELAY)
        true
      }
      R.id.action_view_annotation_map_synchronized -> {
        viewAnnotationManager.setViewAnnotationUpdateMode(ViewAnnotationUpdateMode.MAP_SYNCHRONIZED)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  private fun View.toggleViewVisibility() {
    visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
  }

  @SuppressLint("SetTextI18n")
  private fun prepareViewAnnotation() {
    viewAnnotation = viewAnnotationManager.addViewAnnotation(
      resId = R.layout.item_callout_view,
      options = viewAnnotationOptions {
        geometry(POINT)
        associatedFeatureId(pointAnnotation.id)
        anchor(ViewAnnotationAnchor.BOTTOM)
        offsetY((pointAnnotation.iconImageBitmap?.height!!).toInt())
      }
    )
    ItemCalloutViewBinding.bind(viewAnnotation).apply {
      textNativeView.text = "lat=%.2f\nlon=%.2f".format(POINT.latitude(), POINT.longitude())
      closeNativeView.setOnClickListener {
        viewAnnotationManager.removeViewAnnotation(viewAnnotation)
      }
      selectButton.setOnClickListener { b ->
        val button = b as Button
        val isSelected = button.text.toString().equals("SELECT", true)
        val pxDelta = if (isSelected) SELECTED_ADD_COEF_PX else -SELECTED_ADD_COEF_PX
        button.text = if (isSelected) "DESELECT" else "SELECT"
        viewAnnotationManager.updateViewAnnotation(
          viewAnnotation,
          viewAnnotationOptions {
            selected(isSelected)
          }
        )
        (button.layoutParams as ViewGroup.MarginLayoutParams).apply {
          bottomMargin += pxDelta
          rightMargin += pxDelta
          leftMargin += pxDelta
        }
        button.requestLayout()
      }
    }
  }

  private fun prepareAnnotationMarker(mapView: MapView, iconBitmap: Bitmap) {
    val annotationPlugin = mapView.annotations
    val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
      .withPoint(POINT)
      .withIconImage(iconBitmap)
      .withIconAnchor(IconAnchor.BOTTOM)
      .withDraggable(true)
    pointAnnotationManager = annotationPlugin.createPointAnnotationManager()
    pointAnnotation = pointAnnotationManager.create(pointAnnotationOptions)
  }

  private companion object {
    const val SELECTED_ADD_COEF_PX = 25
    val POINT: Point = Point.fromLngLat(0.381457, 6.687337)
  }
}
package com.mapbox.maps.testapp.examples.markersandcallouts.viewannotation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.gestures.*
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityViewAnnotationShowcaseBinding
import com.mapbox.maps.testapp.databinding.ItemCalloutViewBinding
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import com.mapbox.maps.viewannotation.ViewAnnotationUpdateMode
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions

/**
 * Example how to add view annotations by clicking on the map.
 */
class ViewAnnotationBasicAddActivity : AppCompatActivity(), OnMapClickListener {

  private lateinit var mapboxMap: MapboxMap
  private lateinit var viewAnnotationManager: ViewAnnotationManager
  private val viewAnnotationViews = mutableListOf<View>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityViewAnnotationShowcaseBinding.inflate(layoutInflater)
    setContentView(binding.root)

    viewAnnotationManager = binding.mapView.viewAnnotationManager

    mapboxMap = binding.mapView.mapboxMap.apply {
      loadStyle(Style.STANDARD) {
        addOnMapClickListener(this@ViewAnnotationBasicAddActivity)
        binding.fabStyleToggle.setOnClickListener {
          when (style?.styleURI) {
            Style.STANDARD -> loadStyle(Style.SATELLITE_STREETS)
            Style.SATELLITE_STREETS -> loadStyle(Style.STANDARD_SATELLITE)
            Style.STANDARD_SATELLITE -> loadStyle(Style.STANDARD)
          }
        }
        Toast.makeText(this@ViewAnnotationBasicAddActivity, STARTUP_TEXT, Toast.LENGTH_LONG).show()
      }
    }

    binding.fabReframe.setOnClickListener {
      if (viewAnnotationViews.isNotEmpty()) {
        viewAnnotationManager.cameraForAnnotations(viewAnnotationViews) {
          mapboxMap.flyTo(it)
        }
      } else {
        Toast.makeText(this@ViewAnnotationBasicAddActivity, ADD_VIEW_ANNOTATION_TEXT, Toast.LENGTH_LONG).show()
      }
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

  override fun onMapClick(point: Point): Boolean {
    addViewAnnotation(point)
    return true
  }

  @SuppressLint("SetTextI18n")
  private fun addViewAnnotation(point: Point) {
    val viewAnnotation = viewAnnotationManager.addViewAnnotation(
      resId = R.layout.item_callout_view,
      options = viewAnnotationOptions {
        geometry(point)
        allowOverlap(true)
        ignoreCameraPadding(true)
        allowOverlapWithPuck(true)
      }
    )
    viewAnnotationViews.add(viewAnnotation)
    ItemCalloutViewBinding.bind(viewAnnotation).apply {
      textNativeView.text = "lat=%.2f\nlon=%.2f".format(point.latitude(), point.longitude())
      closeNativeView.setOnClickListener {
        viewAnnotationManager.removeViewAnnotation(viewAnnotation)
        viewAnnotationViews.remove(viewAnnotation)
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

  private companion object {
    const val SELECTED_ADD_COEF_PX = 25
    const val STARTUP_TEXT = "Click on a map to add a view annotation."
    const val ADD_VIEW_ANNOTATION_TEXT = "Add view annotations to re-frame map camera"
  }
}
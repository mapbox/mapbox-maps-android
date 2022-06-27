package com.mapbox.maps.testapp.examples.markersandcallouts

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.plugin.gestures.*
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityViewAnnotationShowcaseBinding
import com.mapbox.maps.testapp.databinding.ItemCalloutViewBinding
import com.mapbox.maps.viewannotation.viewAnnotationOptions

/**
 * Example how to add view annotations by clicking on the map.
 */
class ViewAnnotationBasicAddActivity : BaseViewAnnotationActivity(), OnMapClickListener {

  private lateinit var mapView: MapView
  private lateinit var mapboxMap: MapboxMap

  override fun getViewAnnotationManager() = mapView.viewAnnotationManager

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityViewAnnotationShowcaseBinding.inflate(layoutInflater)
    setContentView(binding.root)

    mapboxMap = mapView.getMapboxMap().apply {
      loadStyleUri(Style.MAPBOX_STREETS) {
        addOnMapClickListener(this@ViewAnnotationBasicAddActivity)
        binding.fabStyleToggle.setOnClickListener {
          when (getStyle()?.styleURI) {
            Style.MAPBOX_STREETS -> mapboxMap.loadStyleUri(Style.SATELLITE_STREETS)
            Style.SATELLITE_STREETS -> mapboxMap.loadStyleUri(Style.MAPBOX_STREETS)
          }
        }
        Toast.makeText(this@ViewAnnotationBasicAddActivity, STARTUP_TEXT, Toast.LENGTH_LONG).show()
      }
    }
  }

  override fun onMapClick(point: Point): Boolean {
    addViewAnnotation(point)
    return true
  }

  @SuppressLint("SetTextI18n")
  private fun addViewAnnotation(point: Point) {
    val viewAnnotation = getViewAnnotationManager().addViewAnnotation(
      resId = R.layout.item_callout_view,
      options = viewAnnotationOptions {
        geometry(point)
        allowOverlap(true)
      }
    )
    ItemCalloutViewBinding.bind(viewAnnotation).apply {
      textNativeView.text = "lat=%.2f\nlon=%.2f".format(point.latitude(), point.longitude())
      closeNativeView.setOnClickListener {
        getViewAnnotationManager().removeViewAnnotation(viewAnnotation)
      }
      selectButton.setOnClickListener { b ->
        val button = b as Button
        val isSelected = button.text.toString().equals("SELECT", true)
        val pxDelta = if (isSelected) SELECTED_ADD_COEF_PX else -SELECTED_ADD_COEF_PX
        button.text = if (isSelected) "DESELECT" else "SELECT"
        getViewAnnotationManager().updateViewAnnotation(
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
  }
}
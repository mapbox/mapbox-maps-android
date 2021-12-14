package com.mapbox.maps.testapp.examples.markersandcallouts

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityViewAnnotationShowcaseBinding
import com.mapbox.maps.testapp.databinding.ItemCalloutViewBinding
import com.mapbox.maps.testapp.utils.BitmapUtils
import com.mapbox.maps.viewannotation.viewAnnotationOptions

/**
 * Example how to add view annotation to the point annotation.
 */
class AnnotationWithCalloutActivity : AppCompatActivity() {

  @SuppressLint("SetTextI18n")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityViewAnnotationShowcaseBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS) {
      val viewAnnotationManager = binding.mapView.viewAnnotationManager
      val point = Point.fromLngLat(
        AIRPORT_LONGITUDE,
        AIRPORT_LATITUDE
      )
      val iconBitmap = BitmapUtils.bitmapFromDrawableRes(
        this@AnnotationWithCalloutActivity,
        R.drawable.ic_airplanemode_active_black_24dp
      )!!

      val annotationPlugin = binding.mapView.annotations
      val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
        .withPoint(point)
        .withIconImage(iconBitmap)
        .withIconAnchor(IconAnchor.BOTTOM)

      val annotationManager = annotationPlugin.createPointAnnotationManager()
      val annotation = annotationManager.create(pointAnnotationOptions)

      val viewAnnotation = viewAnnotationManager.addViewAnnotation(
        resId = R.layout.item_callout_view,
        options = viewAnnotationOptions {
          geometry(point)
          associatedFeatureId(annotation.featureIdentifier)
          anchor(ViewAnnotationAnchor.BOTTOM)
          offsetY((annotation.iconImageBitmap?.height!!).toInt())
        }
      )
      ItemCalloutViewBinding.bind(viewAnnotation).apply {
        textNativeView.text = "lat=%.2f\nlon=%.2f".format(point.latitude(), point.longitude())
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
              width(viewAnnotationManager.getViewAnnotationOptionsByView(viewAnnotation)?.width!! + pxDelta)
              height(viewAnnotationManager.getViewAnnotationOptionsByView(viewAnnotation)?.height!! + pxDelta)
              selected(isSelected)
            }
          )
        }
      }

      binding.fabStyleToggle.setOnClickListener {
        annotation.iconImageBitmap = if (annotation.iconImage == null) iconBitmap else null
        annotationManager.update(annotation)
      }
    }
  }

  private companion object {
    const val SELECTED_ADD_COEF_PX = 50
    private const val AIRPORT_LONGITUDE = 0.381457
    private const val AIRPORT_LATITUDE = 6.687337
  }
}
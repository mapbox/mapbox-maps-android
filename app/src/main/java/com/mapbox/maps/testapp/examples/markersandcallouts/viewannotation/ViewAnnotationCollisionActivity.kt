package com.mapbox.maps.testapp.examples.markersandcallouts.viewannotation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.ClickInteraction
import com.mapbox.maps.MapboxDelicateApi
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.debugoptions.MapViewDebugOptions
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.interactions.standard.generated.StandardPoiState
import com.mapbox.maps.interactions.standard.generated.StandardPoiStateKey
import com.mapbox.maps.interactions.standard.generated.standardPoi
import com.mapbox.maps.testapp.databinding.ActivityViewAnnotationCollisionBinding
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import com.mapbox.maps.viewannotation.annotationAnchors
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions

/**
 * Example showing a single view annotation rendered as a [PinView] (pin shape + label),
 * mirroring the SwiftUI PinView used in maps-ios examples. The annotation participates
 * in symbol-layer collision so the pin and its label can be hidden by overlapping map symbols.
 */
@OptIn(MapboxExperimental::class)
class ViewAnnotationCollisionActivity : AppCompatActivity() {

  private var symbolCollisionEnabled = true
  private var allowOverlapEnabled = true
  private val allPins = mutableListOf<View>()
  private lateinit var vaManager: ViewAnnotationManager

  @OptIn(MapboxDelicateApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityViewAnnotationCollisionBinding.inflate(layoutInflater)
    setContentView(binding.root)
    vaManager = binding.mapView.viewAnnotationManager

    binding.debugToggle.setOnCheckedChangeListener { _, isChecked ->
      binding.mapView.debugOptions = if (isChecked) setOf(MapViewDebugOptions.COLLISION) else emptySet()
    }

    binding.symbolCollisionToggle.setOnCheckedChangeListener { _, isChecked ->
      symbolCollisionEnabled = isChecked
      updateAllPinOptions()
    }

    binding.vaAllowOverlap.setOnCheckedChangeListener { _, isChecked ->
      allowOverlapEnabled = isChecked
      updateAllPinOptions()
    }

    val point = Point.fromLngLat(-122.4194, 37.7749)
    val pin = PinView(this, text = "San Francisco")

    binding.mapView.mapboxMap.apply {
      setCamera(cameraOptions { center(point); zoom(14.0) })
      vaManager.addViewAnnotation(
        view = pin,
        options = pinOptions(point, pin.pinTipY)
      )
      allPins += pin

      addInteraction(
        ClickInteraction.standardPoi { poi, _ ->
          setFeatureState(poi, StandardPoiState { hide(true) }) {
            val poiPin = PinView(binding.mapView.context, text = poi.name ?: "")
            poiPin.setOnClickListener {
              removeFeatureState(poi, StandardPoiStateKey.HIDE)
              vaManager.removeViewAnnotation(poiPin)
              allPins -= poiPin
            }
            vaManager.addViewAnnotation(
              view = poiPin,
              options = pinOptions(poi.geometry, poiPin.pinTipY)
            )
            allPins += poiPin
          }
          return@standardPoi true
        }
      )
    }
  }

  private fun updateAllPinOptions() {
    allPins.forEach { view ->
      vaManager.updateViewAnnotation(
        view,
        viewAnnotationOptions {
        enableSymbolLayerCollision(symbolCollisionEnabled)
        allowOverlap(allowOverlapEnabled)
      }
      )
    }
  }

  private fun pinOptions(point: Point, tipY: Int) = viewAnnotationOptions {
    geometry(point)
    allowOverlap(allowOverlapEnabled)
    allowOverlapWithPuck(true)
    ignoreCameraPadding(true)
    enableSymbolLayerCollision(symbolCollisionEnabled)
    annotationAnchors(
      {
        anchor(ViewAnnotationAnchor.TOP)
        offsetY(tipY.toDouble())
      }
    )
  }
}
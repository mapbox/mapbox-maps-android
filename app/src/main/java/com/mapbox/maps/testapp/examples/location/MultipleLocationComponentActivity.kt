package com.mapbox.maps.testapp.examples.location

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.LocationPuck3D
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.CustomJourneyLocationProvider
import com.mapbox.maps.plugin.locationcomponent.Journey
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin2
import com.mapbox.maps.plugin.locationcomponent.location2
import com.mapbox.maps.testapp.utils.LocationComponentUtils
import com.mapbox.maps.testapp.utils.createLocationComponent
import java.util.concurrent.CopyOnWriteArraySet

/**
 * Example of using multiple location component.
 */
class MultipleLocationComponentActivity : AppCompatActivity(), OnMapClickListener {

  private val journey = Journey()
  private val customJourneyLocationProvider = CustomJourneyLocationProvider().apply { loadJourney(journey) }
  private lateinit var mapView: MapView
  private val locationComponents = CopyOnWriteArraySet<LocationComponentPlugin2>()

  @SuppressLint("SetTextI18n")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mapView = MapView(this)
    setContentView(mapView)
    mapView.addView(
      Button(this).apply {
        layoutParams = ViewGroup.LayoutParams(
          ViewGroup.LayoutParams.WRAP_CONTENT,
          ViewGroup.LayoutParams.WRAP_CONTENT
        )
        text = "Cancel"
        setOnClickListener {
          journey.pause()
        }
      }
    )
    mapView.getMapboxMap()
      .apply {
        setCamera(
          CameraOptions.Builder()
            .center(HELSINKI)
            .pitch(40.0)
            .zoom(14.0)
            .build()
        )
        loadStyleUri(Style.MAPBOX_STREETS) {
          initLocationComponents()
          initClickListeners()
          journey.start()
        }
      }
  }

  private fun initClickListeners() {
    mapView.gestures.addOnMapClickListener(this)
  }

  private fun initLocationComponents() {
    locationComponents.add(
      mapView.createLocationComponent(LocationComponentUtils.getNextLocationComponentOptions()).apply {
        setLocationProvider(customJourneyLocationProvider)
        enabled = true
        locationPuck = LocationPuck2D(
          topImage = null,
          bearingImage = null,
        )
        puckBearingEnabled = true
        pulsingEnabled = true
      }
    )
    locationComponents.add(
      mapView.createLocationComponent(LocationComponentUtils.getNextLocationComponentOptions()).apply {
        setLocationProvider(customJourneyLocationProvider)
        enabled = true
        locationPuck = LocationPuck3D(
          modelUri = "asset://sportcar.glb",
          modelScale = listOf(0.2f, 0.2f, 0.2f),
          modelTranslation = listOf(0.1f, 0.1f, 0.1f),
          modelRotation = listOf(0.0f, 0.0f, 180.0f)
        )
        puckBearingEnabled = true
      }
    )
    locationComponents.add(
      mapView.createLocationComponent(LocationComponentUtils.getNextLocationComponentOptions()).apply {
        setLocationProvider(customJourneyLocationProvider)
        enabled = true
        locationPuck = LocationPuck3D(
          modelUri = "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Duck/glTF-Embedded/Duck.gltf",
          modelScale = listOf(0.2f, 0.2f, 0.2f),
          modelRotation = listOf(0f, 0f, -90f),
          modelTranslation = listOf(0f, 0.0f, 0.0f)
        )
        puckBearingEnabled = true
      }
    )

  }

  override fun onMapClick(point: Point): Boolean {
    journey.queueLocationUpdate(point)
    return true
  }

  companion object {
    private val HELSINKI = Point.fromLngLat(24.9384, 60.1699)
  }
}
package com.mapbox.maps.testapp.examples.location

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.LocationPuck3D
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.CustomJourneyLocationProvider
import com.mapbox.maps.plugin.locationcomponent.Journey
import com.mapbox.maps.plugin.locationcomponent.LocationComponentInitOptions
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin2
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateBearing
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.MultiPuckViewportStateBearing
import com.mapbox.maps.plugin.viewport.data.MultiPuckViewportStateOptions
import com.mapbox.maps.plugin.viewport.state.MultiPuckViewportState
import com.mapbox.maps.plugin.viewport.viewport
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityMultiDisplayBinding
import com.mapbox.maps.testapp.databinding.ActivityMultiLocationcomponentBinding
import com.mapbox.maps.testapp.utils.BitmapUtils
import com.mapbox.maps.testapp.utils.createLocationComponent
import java.util.*

/**
 * Example of using multiple location component.
 */
@OptIn(MapboxExperimental::class)
class MultipleLocationComponentActivity : AppCompatActivity(), OnMapClickListener {
  private lateinit var mapView: MapView
  private val journeys = mutableListOf(Journey(speed = 150.0, angularSpeed = 150.0), Journey(speed = 100.0, angularSpeed = 150.0))
  private val customJourneyLocationProviders = mutableListOf(
    CustomJourneyLocationProvider().apply { loadJourney(journeys.first()) },
    CustomJourneyLocationProvider().apply { loadJourney(journeys.last()) }
  )
  private val annotationLists =
    mutableListOf(LinkedList<PointAnnotation>(), LinkedList<PointAnnotation>())
  private val locationComponents = mutableListOf<LocationComponentPlugin2>()
  private lateinit var pointAnnotationManager: PointAnnotationManager
  private lateinit var multiPuckViewportState: MultiPuckViewportState

  var selectedPuck = 0

  @SuppressLint("SetTextI18n")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityMultiLocationcomponentBinding.inflate(layoutInflater)
    setContentView(binding.root)
    mapView = binding.mapView
    binding.toggleControlBtn.apply {
      text = "Controlling ${if (selectedPuck == 0) "Car" else "Duck"}"
      setOnClickListener {
        toggleControl()
        text = "Controlling ${if (selectedPuck == 0) "Car" else "Duck"}"
      }
    }
    binding.followPucksButton.setOnClickListener {
      mapView.viewport.transitionTo(multiPuckViewportState)
    }
    pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
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
          journeys.forEach { it.start() }
        }
      }

    journeys.forEachIndexed { index, journey ->
      journey.observeJourneyUpdates { _, _, _, _ ->
        annotationLists[index].poll()?.let {
          pointAnnotationManager.delete(it)
        }
        true
      }
    }
  }

  private fun toggleControl() {
    selectedPuck = (selectedPuck + 1) % 2
  }

  private fun initClickListeners() {
    mapView.gestures.addOnMapClickListener(this)
  }

  private fun initLocationComponents() {
    // Puck with pulsing car
    locationComponents.add(
      mapView.createLocationComponent(LocationComponentInitOptions.getNextUniqueLocationComponentOptions())
        .apply {
          setLocationProvider(customJourneyLocationProviders.first())
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
      mapView.createLocationComponent(LocationComponentInitOptions.getNextUniqueLocationComponentOptions())
        .apply {
          setLocationProvider(customJourneyLocationProviders.first())
          enabled = true
          locationPuck = LocationPuck3D(
            modelUri = "asset://sportcar.glb",
            modelScale = listOf(0.3f, 0.3f, 0.3f),
            modelTranslation = listOf(0.1f, 0.1f, 0.1f),
            modelRotation = listOf(0.0f, 0.0f, 180.0f)
          )
          puckBearingEnabled = true
        }
    )
    // Puck with pulsing duck
    locationComponents.add(
      mapView.createLocationComponent(LocationComponentInitOptions.getNextUniqueLocationComponentOptions())
        .apply {
          setLocationProvider(customJourneyLocationProviders.last())
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
      mapView.createLocationComponent(LocationComponentInitOptions.getNextUniqueLocationComponentOptions())
        .apply {
          setLocationProvider(customJourneyLocationProviders.last())
          enabled = true
          locationPuck = LocationPuck3D(
            modelUri = "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Duck/glTF-Embedded/Duck.gltf",
            modelScale = listOf(0.5f, 0.5f, 0.5f),
            modelRotation = listOf(0f, 0f, -90f),
            modelTranslation = listOf(0f, 0.0f, 0.0f)
          )
          puckBearingEnabled = true
        }
    )
    multiPuckViewportState = mapView.viewport.makeMultiPuckViewportState(
      MultiPuckViewportStateOptions
        .Builder()
        .bearing(MultiPuckViewportStateBearing.SyncWithLocationPuck(locationComponents.first()))
        .padding(EdgeInsets(100.0, 100.0, 100.0, 100.0))
        .build(),
      locationComponents = locationComponents
    )
  }

  override fun onMapClick(point: Point): Boolean {
    journeys[selectedPuck].queueLocationUpdate(point)
    BitmapUtils.bitmapFromDrawableRes(
      this,
      if (selectedPuck == 0) R.drawable.red_marker else R.drawable.blue_marker_view
    )?.let {
      pointAnnotationManager.create(
        PointAnnotationOptions()
          .withPoint(point)
          .withIconImage(it)
          .withDraggable(true)
      ).also { annotation -> annotationLists[selectedPuck].add(annotation) }
    }
    return true
  }

  companion object {
    private val HELSINKI = Point.fromLngLat(24.9384, 60.1699)
  }
}
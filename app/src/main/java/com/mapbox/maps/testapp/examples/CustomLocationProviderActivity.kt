package com.mapbox.maps.testapp.examples

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.LocationPuck3D
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.Journey
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.locationcomponent.location2
import java.util.*

/**
 * Example of using custom location provider.
 */
class CustomLocationProviderActivity : AppCompatActivity(), OnMapClickListener {

  private val journey = Journey()
  private lateinit var mapView: MapView

  @SuppressLint("SetTextI18n")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mapView = MapView(this)
    setContentView(mapView)
    journey.stopOnLastLeg = false
    mapView.location.addOnIndicatorPositionChangedListener(journey)

    mapView.addView(
      Button(this).apply {
        layoutParams = ViewGroup.LayoutParams(
          ViewGroup.LayoutParams.WRAP_CONTENT,
          ViewGroup.LayoutParams.WRAP_CONTENT
        )
        text = "Cancel"
        setOnClickListener {
          journey.stop()
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
          initLocationComponent()
          initClickListeners()
        }
      }
  }

  private fun initClickListeners() {
    mapView.gestures.addOnMapClickListener(this)
  }

  private fun initLocationComponent() {
    val locationComponentPlugin2 = mapView.location2
    locationComponentPlugin2.setLocationProvider(journey)
    locationComponentPlugin2.let {
      it.locationPuck = LocationPuck3D(
        modelUri = "asset://sportcar.glb",
        modelScale = listOf(0.1f, 0.1f, 0.1f),
        modelTranslation = listOf(0.1f, 0.1f, 0.1f),
        modelRotation = listOf(0.0f, 0.0f, 180.0f)
      )
    }
    locationComponentPlugin2.enabled = true
    locationComponentPlugin2.puckBearingEnabled = true
  }

  override fun onMapClick(point: Point): Boolean {
    val leg = Journey.Leg()
    leg.points.add(point)
    var newLegs = LinkedList(journey.legs)
    newLegs.add(leg)

    // apparently lists don't feature value semaintics in Kotlin, this should simulate it
    journey.legs = newLegs

    if (!journey.isOngoing) {
        journey.start()
    }
    return true
  }

  companion object {
    private val HELSINKI = Point.fromLngLat(24.9384, 60.1699)
  }
}
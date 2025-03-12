package com.mapbox.maps.testapp.examples.geofence

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.JsonObject
import com.mapbox.annotation.MapboxExperimental
import com.mapbox.bindgen.Expected
import com.mapbox.common.geofencing.GeofencingError
import com.mapbox.common.geofencing.GeofencingEvent
import com.mapbox.common.geofencing.GeofencingFactory
import com.mapbox.common.geofencing.GeofencingObserver
import com.mapbox.common.geofencing.GeofencingOptions
import com.mapbox.common.geofencing.GeofencingPropertiesKeys
import com.mapbox.geojson.Feature
import com.mapbox.geojson.GeometryCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.generated.FillLayer
import com.mapbox.maps.extension.style.layers.generated.fillLayer
import com.mapbox.maps.extension.style.layers.getLayerAs
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.logD
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
import com.mapbox.maps.plugin.viewport.viewport
import com.mapbox.maps.testapp.databinding.ActivitySimpleGeofencingBinding
import com.mapbox.maps.testapp.utils.LocationPermissionHelper
import com.mapbox.turf.TurfConstants
import com.mapbox.turf.TurfTransformation
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

/**
 * This example shows how to get updates from the geofence engine.
 * After the current location is received around it's created geofence zone and rendered in Yellow color.
 * Geofence callbacks are called when device location enters, dwells, or leaves loaded geofence zone.
 * Each aforementioned event is accompanied by rendering received feature (Blue, Green, Red colors ).
 */
@MapboxExperimental
class SimpleGeofencingActivity : AppCompatActivity() {

  private lateinit var locationPermissionHelper: LocationPermissionHelper
  private lateinit var binding: ActivitySimpleGeofencingBinding

  private lateinit var mapboxMap: MapboxMap
  private val customZonesFeatures = mutableListOf<Feature>()

  private var geofencingStarted: Boolean = false

  // NOTE: You need to grant location permissions before initialising GeofencingService with getOrCreate()
  private val geofencing by lazy {
    GeofencingFactory.getOrCreate()
  }

  private val observer: GeofencingObserver = object : GeofencingObserver {
    override fun onEntry(event: GeofencingEvent) {
      logD(TAG, "onEntry() called with: feature id = ${event.feature.id()} at ${event.timestamp}")
      updateGeofenceZoneLayerColor(DARK_BLUE)
    }

    override fun onDwell(event: GeofencingEvent) {
      logD(TAG, "onDwell() called with:  feature id = ${event.feature.id()} at ${event.timestamp}")
      updateGeofenceZoneLayerColor(GREEN)
    }

    override fun onExit(event: GeofencingEvent) {
      logD(TAG, "onExit() called with:  feature id = ${event.feature.id()} at ${event.timestamp}")
      updateGeofenceZoneLayerColor(RED)
    }

    override fun onError(error: GeofencingError) {
      logD(TAG, "onError() called with: error = $error")
    }

    override fun onUserConsentChanged(isConsentGiven: Boolean) {
      logD(TAG, "onUserConsentChanged() called with: isConsentGiven = $isConsentGiven")
      userRevokedConsentUi(isConsentGiven)
    }

    private fun updateGeofenceZoneLayerColor(fillColor: String) {
      lifecycleScope.launch {
        mapboxMap.getStyle { style ->
          val layer = style.getLayerAs<FillLayer>(CUSTOM_GEOZONE_LAYER_ID)
          layer?.fillColor(fillColor)
          moveCameraToActiveFeatures()
        }
      }
    }
  }

  private fun moveCameraToActiveFeatures() {
    val geometries = buildList {
      addAll(customZonesFeatures.map { it.geometry() })
    }.filterNotNull()
    binding.mapView.viewport.transitionTo(
      binding.mapView.viewport.makeOverviewViewportState(
        OverviewViewportStateOptions.Builder()
          .geometry(GeometryCollection.fromGeometries(geometries))
          .padding(
            EdgeInsets(
              /* top = */ 100.0,
              /* left = */ 100.0,
              /* bottom = */ 100.0,
              /* right = */ 100.0
            )
          )
          .build()
      )
    )
  }

  @SuppressLint("SetTextI18n")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivitySimpleGeofencingBinding.inflate(layoutInflater)
    setContentView(binding.root)
    mapboxMap = binding.mapView.mapboxMap

    locationPermissionHelper = LocationPermissionHelper(WeakReference(this))
    locationPermissionHelper.checkPermissions {
      // Postpone access to Geofence engine until we get location permissions
      geofencing.configure(
        GeofencingOptions.Builder().apply {
          defaultRadius = CUSTOM_GEOFENCE_RADIUS
          maximumMonitoredFeatures = 300_000
        }.build(),
        logGeofencingError("configure")
      )
      startGeofencing()

      // Once we've location permission enable location puck
      with(binding.mapView) {
        location.locationPuck = createDefault2DPuck()
        location.enabled = true
        location.puckBearing = PuckBearing.COURSE
        val onIndicatorPositionChangedListener = object : OnIndicatorPositionChangedListener {
          override fun onIndicatorPositionChanged(point: Point) {
            mapboxMap.setCamera(CameraOptions.Builder().center(point).zoom(ZOOM).pitch(0.0).build())
            // We use only the first location to create the geofence so remove the listener immediate
            location.removeOnIndicatorPositionChangedListener(this)
            loadGeofenceAroundLocation(point)
          }
        }
        location.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
      }

      mapboxMap.loadStyle(
        styleExtension = style(Style.STANDARD) {
          +geoJsonSource(CUSTOM_GEOZONE_SOURCE_ID)

          +fillLayer(layerId = CUSTOM_GEOZONE_LAYER_ID, sourceId = CUSTOM_GEOZONE_SOURCE_ID) {
            fillColor(YELLOW)
            fillOpacity(LAYER_OPACITY)
            fillOutlineColor(LAYER_OUTLINE_COLOR)
          }
        }
      )
    }
  }

  /**
   * Adds point Feature to GeofencingService to track it.
   * Adds Polygon Feature, based on point, to the map to render the same geofence zone
   */
  private fun loadGeofenceAroundLocation(point: Point) {
    val featureId = System.currentTimeMillis().toString()
    val circleFeature = circleFeature(point, featureId)

    val properties = JsonObject()
    properties.addProperty(GeofencingPropertiesKeys.DWELL_TIME_KEY, DWELL_TIME)
    // for geofences represented by Point the defaultRadius from [GeofencingOptions] is used.
    val pointFeature = Feature.fromGeometry(
      point,
      properties,
      featureId
    )

    geofencing.addFeature(pointFeature, logGeofencingError<String>("addCustomFeature"))
    mapboxMap.getStyle { style ->
      style.addGeoJSONSourceFeatures(
        CUSTOM_GEOZONE_SOURCE_ID,
        CUSTOM_GEOZONE_DATA_ID,
        features = listOf(circleFeature)
      )

      customZonesFeatures.add(circleFeature)
    }
  }

  private fun circleFeature(
    point: Point,
    featureId: String
  ): Feature = Feature.fromGeometry(
    TurfTransformation.circle(
      point, CUSTOM_GEOFENCE_RADIUS.toDouble(),
      TurfConstants.UNIT_METERS
    ),
    JsonObject(),
    featureId
  )

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<String>,
    grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
  }

  private fun startGeofencing() {
    logD(TAG, "startGeofencing")
    geofencing.addObserver(observer, logGeofencingError("addObserver"))
    geofencingStarted = true
  }

  private fun stopGeofencing() {
    logD(TAG, "stopGeofencingListeningToRenderOnMap")
    geofencing.removeObserver(observer, logGeofencingError("removeObserver"))
  }

  private fun clearGeofences() =
    geofencing.clearFeatures(logGeofencingError("clearFeatures"))

  override fun onDestroy() {
    if (geofencingStarted) {
      stopGeofencing()
      clearGeofences()
    }

    super.onDestroy()
  }

  private fun userRevokedConsentUi(isConsentGiven: Boolean) = lifecycleScope.launch {
    with(binding) {
      if (isConsentGiven) {
        textGeofencesDisabled.visibility = View.GONE
      } else {
        textGeofencesDisabled.visibility = View.VISIBLE
      }
    }
  }

  private fun <T> logGeofencingError(functionName: String): (result: Expected<GeofencingError, T>) -> Unit =
    {
      it.error?.let { geofenceError ->
        logD(TAG, "geofence.$functionName() error $geofenceError")
      }
    }

  companion object {
    private const val TAG = "GeofencingActivity"

    private const val CUSTOM_GEOZONE_SOURCE_ID = "custom_geozone_source_id"
    private const val CUSTOM_GEOZONE_LAYER_ID = "custom_geozone_layer_id"
    private const val CUSTOM_GEOZONE_DATA_ID = "custom_geozone_data_id"
    private const val CUSTOM_GEOFENCE_RADIUS = 500
    private const val DWELL_TIME = 0.5
    private const val LAYER_OPACITY = 0.6
    private const val LAYER_OUTLINE_COLOR = "#000"
    private const val DARK_BLUE = "#4264fb"
    private const val RED = "#f74e4e"
    private const val GREEN = "#33c377"
    private const val YELLOW = "#ffff00"
    private const val ZOOM = 14.0
  }
}
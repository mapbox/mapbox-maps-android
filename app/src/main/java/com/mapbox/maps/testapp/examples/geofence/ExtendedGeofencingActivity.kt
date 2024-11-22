package com.mapbox.maps.testapp.examples.geofence

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.mapbox.api.isochrone.IsochroneCriteria
import com.mapbox.api.isochrone.MapboxIsochrone
import com.mapbox.bindgen.Expected
import com.mapbox.common.geofencing.GeofencingError
import com.mapbox.common.geofencing.GeofencingEvent
import com.mapbox.common.geofencing.GeofencingFactory
import com.mapbox.common.geofencing.GeofencingObserver
import com.mapbox.common.geofencing.GeofencingOptions
import com.mapbox.common.geofencing.GeofencingPropertiesKeys
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.GeometryCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.ClickInteraction
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.InteractionContext
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.generated.fillLayer
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.logD
import com.mapbox.maps.logW
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
import com.mapbox.maps.plugin.viewport.viewport
import com.mapbox.maps.testapp.MapboxApplication
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityExtendedGeofencingBinding
import com.mapbox.maps.testapp.utils.LocationPermissionHelper
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference
import java.util.Date

/**
 * This example shows how to get updates from the geofence engine.
 * Every tap onto the map calls [MapboxIsochrone] service to create a [Feature] that connects points of equal travel time around a given location.
 * Every returned [Feature] is persisted in the geofence engine so that geofencing works even without network.
 * Geofence callbacks are called when device location enters, dwells, or leaves any loaded geofence zone.
 * Each aforementioned event is accompanied by rendering received feature (Blue, Green, Red colors ) onto the map and showing a notification.
 * Subscription to notifications happens in [MapboxApplication] class to have the ability to receive geofence notifications in the background,
 * even when GeofenceActivity or the whole app is closed.
 * [MapboxApplication.ENABLE_BACKGROUND_GEOFENCING] flag turns ON/OFF showcase of background behavior of the geofence engine.
 */

@com.mapbox.annotation.MapboxExperimental
class ExtendedGeofencingActivity : AppCompatActivity() {

  private var requestNotificationPermissionLauncher: ActivityResultLauncher<String> =
    registerForActivityResult(
      ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
      if (isGranted) {
        createNotificationChannel()
      } else {
        Toast.makeText(
          this,
          "Notification permission is denied and no toasts from the geofence would be shown",
          Toast.LENGTH_LONG
        ).show()
        logD(TAG, "Notification permission denied")
      }
    }
  private lateinit var locationPermissionHelper: LocationPermissionHelper
  private lateinit var binding: ActivityExtendedGeofencingBinding

  private lateinit var mapboxMap: MapboxMap

  private var entryFeatures = mutableListOf<Feature>()
  private var dwellFeatures = mutableListOf<Feature>()
  private var exitFeature: Feature? = null
  private var justAddedFeature: Feature? = null

  private var geofencingStarted: Boolean = false

  // NOTE: You need to grant location permissions before initialising GeofencingService with getOrCreate()
  private val geofencing by lazy {
    GeofencingFactory.getOrCreate()
  }

  private val observer: GeofencingObserver = object : GeofencingObserver {
    override fun onEntry(event: GeofencingEvent) {
      logD(TAG, "onEntry() called with: feature id = ${event.feature.id()} at ${event.timestamp}")

      removeExitFeatureIfNeeded(event)
      lifecycleScope.launch {
        mapboxMap.getStyle { style ->
          val isNewFeature = justAddedFeature?.id() == event.feature.id()
          if (isNewFeature) {
            justAddedFeature.removeFeature(style, JUST_ADDED_ZONE_SOURCE_ID, CUSTOM_ZONE_DATA_ID)
            justAddedFeature = null
          }
          event.feature.addFeature(style, ENTRY_SOURCE_ID, ENTRY_DATA_ID)
          entryFeatures.add(event.feature)
          moveCameraToActiveFeatures()
        }
      }
    }

    override fun onDwell(event: GeofencingEvent) {
      logD(TAG, "onDwell() called with:  feature id = ${event.feature.id()} at ${event.timestamp}")

      removeExitFeatureIfNeeded(event)
      lifecycleScope.launch {
        mapboxMap.getStyle { style ->
          entryFeatures.remove(event.feature)
          event.feature.removeFeature(style, ENTRY_SOURCE_ID, ENTRY_DATA_ID)
          event.feature.addFeature(style, DWELL_SOURCE_ID, DWELL_DATA_ID)
          dwellFeatures.add(event.feature)
          moveCameraToActiveFeatures()
        }
      }
    }

    override fun onExit(event: GeofencingEvent) {
      logD(TAG, "onExit() called with:  feature id = ${event.feature.id()} at ${event.timestamp}")

      fun updateExitFeature(style: Style, newFeature: Feature) {
        newFeature.addFeature(style, EXIT_SOURCE_ID, EXIT_DATA_ID)
        exitFeature.removeFeature(style, EXIT_SOURCE_ID, EXIT_DATA_ID)
        exitFeature = newFeature
      }

      lifecycleScope.launch {
        mapboxMap.getStyle { style ->
          if (entryFeatures.contains(event.feature)) {
            entryFeatures.remove(event.feature)
            event.feature.removeFeature(style, ENTRY_SOURCE_ID, ENTRY_DATA_ID)
          }
          if (dwellFeatures.contains(event.feature)) {
            dwellFeatures.remove(event.feature)
            event.feature.removeFeature(style, DWELL_SOURCE_ID, DWELL_DATA_ID)
          }
          updateExitFeature(style, event.feature)
          moveCameraToActiveFeatures()
        }
      }
    }

    private fun removeExitFeatureIfNeeded(event: GeofencingEvent) {
      if (exitFeature?.id() == event.feature.id()) {
        lifecycleScope.launch {
          mapboxMap.getStyle { style ->
            exitFeature.removeFeature(style, EXIT_SOURCE_ID, EXIT_DATA_ID)
            exitFeature = null
          }
        }
      }
    }

    override fun onError(error: GeofencingError) {
      logD(TAG, "onError() called with: error = $error")
    }

    override fun onUserConsentChanged(isConsentGiven: Boolean) {
      logD(TAG, "onUserConsentChanged() called with: isConsentGiven = $isConsentGiven")
      userRevokedConsentUi(isConsentGiven)
    }
  }

  private fun moveCameraToActiveFeatures() {
    val geometries = buildList {
      addAll(entryFeatures.map { it.geometry() })
      add(exitFeature?.geometry())
      addAll(dwellFeatures.map { it.geometry() })
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
  @OptIn(MapboxExperimental::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityExtendedGeofencingBinding.inflate(layoutInflater)
    setContentView(binding.root)
    mapboxMap = binding.mapView.mapboxMap

    mapboxMap.setCamera(
      CameraOptions.Builder().center(
        Point.fromLngLat(
          LATITUDE, LONGITUDE
        )
      ).zoom(ZOOM).build()
    )

    locationPermissionHelper = LocationPermissionHelper(WeakReference(this))
    locationPermissionHelper.checkBackgroundPermission {
      // Postpone access to Geofence engine until we get location permissions
      geofencing.configure(
        GeofencingOptions.Builder().apply {
          defaultRadius = CUSTOM_GEOFENCE_RADIUS
          maximumMonitoredFeatures = 300_000
        }.build(),
        logGeofencingError("configure")
      )
      startGeofencing()
      (this.applicationContext as? MapboxApplication)?.registerGeofencingObserver()

      requestNotificationPermission()

      // Once we've location permission enable location puck
      with(binding.mapView) {
        location.locationPuck = createDefault2DPuck()
        location.enabled = true
        location.puckBearing = PuckBearing.COURSE
        val onIndicatorPositionChangedListener = object : OnIndicatorPositionChangedListener {
          override fun onIndicatorPositionChanged(point: Point) {
            mapboxMap.setCamera(CameraOptions.Builder().center(point).zoom(ZOOM).pitch(0.0).build())
            // We use only the first location to move camera so remove the listener immediate
            location.removeOnIndicatorPositionChangedListener(this)
          }
        }
        location.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
      }

      mapboxMap.loadStyle(
        styleExtension = style(Style.STANDARD) {
          +geoJsonSource(DWELL_SOURCE_ID)
          +geoJsonSource(ENTRY_SOURCE_ID)
          +geoJsonSource(EXIT_SOURCE_ID)
          +geoJsonSource(JUST_ADDED_ZONE_SOURCE_ID)

          +fillLayer(layerId = ENTRY_LAYER_ID, sourceId = ENTRY_SOURCE_ID) {
            fillColor(DARK_BLUE)
            fillOpacity(LAYER_OPACITY)
            fillOutlineColor(LAYER_OUTLINE_COLOR)
          }
          +fillLayer(layerId = EXIT_LAYER_ID, sourceId = EXIT_SOURCE_ID) {
            fillColor(RED)
            fillOpacity(LAYER_OPACITY)
            fillOutlineColor(LAYER_OUTLINE_COLOR)
          }
          +fillLayer(layerId = DWELL_LAYER_ID, sourceId = DWELL_SOURCE_ID) {
            fillColor(GREEN)
            fillOpacity(LAYER_OPACITY)
            fillOutlineColor(LAYER_OUTLINE_COLOR)
          }
          +fillLayer(layerId = JUST_ADDED_ZONE_LAYER_ID, sourceId = JUST_ADDED_ZONE_SOURCE_ID) {
            fillColor(YELLOW)
            fillOpacity(LAYER_OPACITY)
            fillOutlineColor(LAYER_OUTLINE_COLOR)
          }
        }
      )
    }

    binding.mapView.mapboxMap.addInteraction(
      ClickInteraction { interactionContext ->
        loadIsochroneGeofence(interactionContext)
        return@ClickInteraction true
      }
    )
    handleGeofenceIntent(intent)
  }

  private fun loadIsochroneGeofence(interactionContext: InteractionContext) {
    showProgress(true)

    val mapboxIsochroneRequest = MapboxIsochrone.builder()
      .accessToken(resources.getString(R.string.mapbox_access_token))
      .profile(IsochroneCriteria.PROFILE_DRIVING)
      .addContoursMinutes(ISOCHRONE_CONTOURS_MINUTES)
      .polygons(true)
      .denoise(ISOCHRONE_DENOISE)
      .coordinates(interactionContext.coordinateInfo.coordinate)
      .build()

    mapboxIsochroneRequest.enqueueCall(object : Callback<FeatureCollection> {
      override fun onResponse(
        call: Call<FeatureCollection>,
        response: Response<FeatureCollection>
      ) {
        val responseFeature = response.body()?.features()?.first()
        if (responseFeature == null) {
          Toast.makeText(
            this@ExtendedGeofencingActivity,
            "Isochrone request returned empty feature",
            Toast.LENGTH_LONG
          ).show()
          logD(TAG, "Isochrone request returned empty feature")
        } else {
          val properties = responseFeature.properties()!!
          properties.addProperty(GeofencingPropertiesKeys.DWELL_TIME_KEY, DWELL_TIME)
          val featureId = System.currentTimeMillis().toString()
          val isoFeature = Feature.fromGeometry(
            responseFeature.geometry(),
            properties,
            featureId
          )

          geofencing.addFeature(isoFeature, logGeofencingError<String>("addIsochroneFeature"))
          mapboxMap.getStyle { style ->
            isoFeature.addFeature(style, JUST_ADDED_ZONE_SOURCE_ID, CUSTOM_ZONE_DATA_ID)
            justAddedFeature.removeFeature(style, JUST_ADDED_ZONE_SOURCE_ID, CUSTOM_ZONE_DATA_ID)
            justAddedFeature = isoFeature
          }
          showProgress(false)
        }
      }

      override fun onFailure(call: Call<FeatureCollection>, t: Throwable) {
        showProgress(false)
        Toast.makeText(
          this@ExtendedGeofencingActivity,
          "Isochrone request failed",
          Toast.LENGTH_LONG
        ).show()
        logD(TAG, "Isochrone request failed")
      }
    })
  }

  private fun requestNotificationPermission() {
    if (ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.POST_NOTIFICATIONS
      ) == PackageManager.PERMISSION_GRANTED
    ) {
      createNotificationChannel()
    } else {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        requestNotificationPermissionLauncher.launch(
          Manifest.permission.POST_NOTIFICATIONS
        )
      }
    }
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<String>,
    grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
  }

  private fun showProgress(inProgress: Boolean) {
    with(binding) {
      progress.isVisible = inProgress
    }
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

      if (!MapboxApplication.ENABLE_BACKGROUND_GEOFENCING) {
        clearGeofences()
      }
    }

    super.onDestroy()
  }

  @SuppressLint("MissingSuperCall")
  override fun onNewIntent(intent: Intent?) {
    super.onNewIntent(intent)

    handleGeofenceIntent(intent)
  }

  private fun handleGeofenceIntent(intent: Intent?) {
    val featureId = intent?.getStringExtra(NOTIFICATION_FEATURE_ID)
    val featureType = intent?.getStringExtra(NOTIFICATION_FEATURE_TYPE)

    featureId?.let {
      geofencing.getFeature(featureId) {
        it.value?.let { geofenceState ->

          val geofencingEvent = GeofencingEvent.Builder().apply {
            feature = geofenceState.feature
            timestamp = geofenceState.timestamp ?: Date()
          }.build()
          when (featureType) {
            NOTIFICATION_FEATURE_EXIT -> observer.onExit(geofencingEvent)
            NOTIFICATION_FEATURE_ENTRY -> observer.onEntry(geofencingEvent)
            NOTIFICATION_FEATURE_DWELL -> observer.onDwell(geofencingEvent)
            else -> {
              logW(TAG, "Unknown feature type $featureType")
            }
          }
        }
      }
    }
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

  private fun createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val importance = NotificationManager.IMPORTANCE_HIGH
      val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
      val notificationManager: NotificationManager =
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
      notificationManager.createNotificationChannel(channel)
    }
  }

  private fun Feature?.removeFeature(style: Style, sourceId: String, dataId: String) {
    this?.id()?.let { featureId ->
      style.removeGeoJSONSourceFeatures(
        sourceId, dataId, featureIds = listOf(featureId)
      )
    }
  }

  private fun Feature.addFeature(style: Style, sourceId: String, dataId: String) {
    style.addGeoJSONSourceFeatures(
      sourceId, dataId, features = listOf(this)
    )
  }

  private fun <T> logGeofencingError(functionName: String): (result: Expected<GeofencingError, T>) -> Unit =
    {
      it.error?.let { geofenceError ->
        logD(TAG, "geofence.$functionName() error $geofenceError")
      }
    }

  companion object {
    private const val TAG = "GeofencingActivity"

    private const val ENTRY_SOURCE_ID = "entry_source_id"
    private const val ENTRY_LAYER_ID = "entry_layer_id"
    private const val ENTRY_DATA_ID = "entry_data_id"
    private const val DWELL_SOURCE_ID = "dwell_source_id"
    private const val DWELL_LAYER_ID = "dwell_layer_id"
    private const val DWELL_DATA_ID = "dwell_data_id"
    private const val EXIT_SOURCE_ID = "exit_source_id"
    private const val EXIT_LAYER_ID = "exit_layer_id"
    private const val EXIT_DATA_ID = "exit_data_id"
    private const val JUST_ADDED_ZONE_SOURCE_ID = "custom_zone_source_id"
    private const val JUST_ADDED_ZONE_LAYER_ID = "custom_zone_layer_id"
    private const val CUSTOM_ZONE_DATA_ID = "custom_zone_data_id"
    private const val CUSTOM_GEOFENCE_RADIUS = 500
    private const val ISOCHRONE_CONTOURS_MINUTES = 5
    private const val ISOCHRONE_DENOISE = .4f
    private const val DWELL_TIME = 0.5
    private const val LAYER_OPACITY = 0.6
    private const val LAYER_OUTLINE_COLOR = "#000"
    private const val DARK_BLUE = "#4264fb"
    private const val RED = "#f74e4e"
    private const val GREEN = "#33c377"
    private const val YELLOW = "#ffff00"
    private const val LATITUDE = 24.932492
    private const val LONGITUDE = 60.167948
    private const val ZOOM = 14.0
    private const val CHANNEL_ID = "GEOFENCE_NOTIFICATIONS_ID"
    private const val CHANNEL_NAME = "GEOFENCE_NOTIFICATIONS"
    private const val ALERT_TITLE = "Geofence alert"
    private const val NOTIFICATION_FEATURE_ID = "notification_feature_id"
    private const val NOTIFICATION_FEATURE_TYPE = "notification_feature_type"
    const val NOTIFICATION_FEATURE_DWELL = "notification_feature_dwell"
    const val NOTIFICATION_FEATURE_ENTRY = "notification_feature_entry"
    const val NOTIFICATION_FEATURE_EXIT = "notification_feature_exit"
    private var notificationCounter = 0

    fun showNotification(
      appContext: Context,
      text: String,
      featureId: String?,
      featureType: String
    ) {

      val intent = Intent(appContext, ExtendedGeofencingActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        putExtra(NOTIFICATION_FEATURE_ID, featureId)
        putExtra(NOTIFICATION_FEATURE_TYPE, featureType)
      }
      val pendingIntent: PendingIntent =
        PendingIntent.getActivity(
          appContext,
          notificationCounter,
          intent,
          PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

      val builder = NotificationCompat.Builder(appContext, CHANNEL_ID)
        .setSmallIcon(R.drawable.mapbox_logo_helmet)
        .setContentTitle(ALERT_TITLE)
        .setContentText(text)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

      with(NotificationManagerCompat.from(appContext)) {
        if (ActivityCompat.checkSelfPermission(
            appContext,
            Manifest.permission.POST_NOTIFICATIONS
          ) == PackageManager.PERMISSION_GRANTED
        ) {
          try {
            notify(++notificationCounter, builder.build())
          } catch (e: SecurityException) {
            logD(TAG, "postGeofenceNotification: failed to post notification $e")
          }
        }
      }
    }
  }
}
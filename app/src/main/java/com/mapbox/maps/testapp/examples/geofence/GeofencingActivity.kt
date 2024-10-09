package com.mapbox.maps.testapp.examples.geofence

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.gson.JsonObject
import com.mapbox.bindgen.Expected
import com.mapbox.common.experimental.geofencing.GeofencingError
import com.mapbox.common.experimental.geofencing.GeofencingEvent
import com.mapbox.common.experimental.geofencing.GeofencingFactory
import com.mapbox.common.experimental.geofencing.GeofencingObserver
import com.mapbox.common.experimental.geofencing.GeofencingOptions
import com.mapbox.common.experimental.geofencing.GeofencingPropertiesKeys
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
import com.mapbox.maps.plugin.attribution.Attribution
import com.mapbox.maps.plugin.attribution.AttributionDialogManager
import com.mapbox.maps.plugin.attribution.AttributionParserConfig
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.delegates.MapAttributionDelegate
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
import com.mapbox.maps.plugin.viewport.viewport
import com.mapbox.maps.testapp.MapboxApplication
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityGeofencingBinding
import com.mapbox.maps.testapp.utils.LocationPermissionHelper
import com.mapbox.turf.TurfConstants
import com.mapbox.turf.TurfTransformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.ref.WeakReference
import java.util.Date

/**
 * This example shows how to get updates from the geofence engine.
 * Initially dataset is downloaded to the device from the network and is persisted in the geofence engine
 * so that geofencing works even without network.
 * Geofence callbacks are called when device location enters, dwells, or leaves any loaded geofence zone.
 * Each aforementioned event is accompanied by rendering received feature (Blue, Green, Red colors )
 * onto the map and showing a notification.
 * Subscription to notifications happens in [MapboxApplication] class to have the ability to receive geofence notifications in the background,
 * even when GeofenceActivity is closed.
 * [MapboxApplication.ENABLE_BACKGROUND_GEOFENCING] flag turns ON/OFF showcase of background behavior of the geofence engine.
 */
class GeofencingActivity : AppCompatActivity() {

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
  private lateinit var binding: ActivityGeofencingBinding

  private lateinit var mapboxMap: MapboxMap
  private var importGeofencesJob: Job? = null
  private var customZonesFeatures = mutableListOf<Feature>()

  private var entryFeatures = mutableListOf<Feature>()
  private var dwellFeatures = mutableListOf<Feature>()
  private var exitFeature: Feature? = null
  private val datastoreBaseUrl =
    "https://opendata.arcgis.com/datasets/89b6b5142a9b4bb9a5c5f4404ff28963_0.geojson"
  // NOTE: You need to grant location permissions before initialising GeofencingService with getOrCreate()
  private val geofencing by lazy {
    GeofencingFactory.getOrCreate()
  }

  private val observer: GeofencingObserver = object : GeofencingObserver {
    override fun onEntry(event: GeofencingEvent) {
      logD(TAG, "onEntry() called with: feature id = ${event.feature.id()} at ${event.timestamp}")

      if (exitFeature?.id() == event.feature.id()) {
        lifecycleScope.launch {
          mapboxMap.getStyle { style ->
            exitFeature.removeFeature(style, EXIT_SOURCE_ID, EXIT_DATA_ID)
            exitFeature = null
          }
        }
      }

      if (event.feature.geometry() is Point) {
        var customZone = customZonesFeatures.find { it.id() == event.feature.id() }
        if (customZone == null) {
          customZone = circleFeature(event.feature.geometry() as Point, event.feature.id() ?: "")
        }
        customZone.let {
          customZonesFeatures.remove(customZone)
          lifecycleScope.launch {
            mapboxMap.getStyle { style ->
              customZone.removeFeature(style, CUSTOM_ZONE_SOURCE_ID, CUSTOM_ZONE_DATA_ID)
              customZone?.addFeature(style, ENTRY_SOURCE_ID, ENTRY_DATA_ID)
              entryFeatures.add(it)
              moveCameraToActiveFeatures()
            }
          }
        }
      } else {
        lifecycleScope.launch {
          mapboxMap.getStyle { style ->
            event.feature.addFeature(style, ENTRY_SOURCE_ID, ENTRY_DATA_ID)
            entryFeatures.add(event.feature)
            moveCameraToActiveFeatures()
          }
        }
      }
    }

    override fun onDwell(event: GeofencingEvent) {
      logD(TAG, "onDwell() called with:  feature id = ${event.feature.id()} at ${event.timestamp}")
      val feature = if (event.feature.geometry() is Point) {
        entryFeatures.find { it.id() == event.feature.id() }
      } else {
        event.feature
      }
      feature?.let {
        lifecycleScope.launch {
          mapboxMap.getStyle { style ->
            entryFeatures.remove(feature)
            feature.removeFeature(style, ENTRY_SOURCE_ID, ENTRY_DATA_ID)
            feature.addFeature(style, DWELL_SOURCE_ID, DWELL_DATA_ID)
            dwellFeatures.add(feature)
            moveCameraToActiveFeatures()
          }
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

      if (event.feature.geometry() is Point) {
        var customZone = entryFeatures.find { it.id() == event.feature.id() }
        if (customZone != null) {
          entryFeatures.remove(customZone)
          lifecycleScope.launch {
            mapboxMap.getStyle { style ->
              customZone?.removeFeature(style, ENTRY_SOURCE_ID, ENTRY_DATA_ID)
              customZone?.let {
                updateExitFeature(style, it)
              }
            }
            moveCameraToActiveFeatures()
          }
        } else {
          customZone = dwellFeatures.find { it.id() == event.feature.id() }
          dwellFeatures.remove(customZone)
          lifecycleScope.launch {
            mapboxMap.getStyle { style ->
              customZone?.removeFeature(style, DWELL_SOURCE_ID, DWELL_DATA_ID)
              customZone?.let {
                updateExitFeature(style, it)
              }
            }
            moveCameraToActiveFeatures()
          }
        }
      } else {
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
    }

    override fun onError(error: GeofencingError) {
      logD(TAG, "onError() called with: error = $error")
    }
  }

  private var geofencingStarted: Boolean = false

  fun moveCameraToActiveFeatures() {
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

    binding = ActivityGeofencingBinding.inflate(layoutInflater)
    setContentView(binding.root)
    mapboxMap = binding.mapView.mapboxMap

    if (getPreferences(Context.MODE_PRIVATE).getBoolean("network_geofences_loaded", false)) {
      binding.buttonLoadGeofenceZones.text = "Reload GeoJson zones"
    }
    binding.buttonLoadGeofenceZones.setOnClickListener {
      loadGeofences()
    }

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
        GeofencingOptions(CUSTOM_GEOFENCE_RADIUS, 300_000),
        logGeofencingError("configure")
      )
      startGeofencing()
      (this.applicationContext as? MapboxApplication)?.registerGeofencingObserver()

      requestNotificationPermission()

      // Postpone access to applySettings() until we get location permissions
      // Otherwise puck doesn't move until the app is restarted
      with(binding.mapView) {
        location.locationPuck = createDefault2DPuck()
        location.enabled = true
        location.puckBearing = PuckBearing.COURSE
        val onIndicatorPositionChangedListener = object : OnIndicatorPositionChangedListener {
          override fun onIndicatorPositionChanged(point: Point) {
            mapboxMap.setCamera(CameraOptions.Builder().center(point).zoom(ZOOM).pitch(0.0).build())
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
          +geoJsonSource(CUSTOM_ZONE_SOURCE_ID)

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
          +fillLayer(layerId = CUSTOM_ZONE_LAYER_ID, sourceId = CUSTOM_ZONE_SOURCE_ID) {
            fillColor(YELLOW)
            fillOpacity(LAYER_OPACITY)
            fillOutlineColor(LAYER_OUTLINE_COLOR)
          }
        }
      )
    }

    binding.mapView.mapboxMap.addInteraction(
      ClickInteraction { interactionContext ->
        loadCustomGeofence(interactionContext)
        return@ClickInteraction true
      }
    )
    binding.mapView.attribution.setCustomAttributionDialogManager(CustomAttributionDialog(this))

    handleGeofenceIntent(intent)
  }

  private fun loadCustomGeofence(interactionContext: InteractionContext) {
    val featureId = System.currentTimeMillis().toString()
    val circleFeature = circleFeature(interactionContext.coordinateInfo.coordinate, featureId)

    val properties = JsonObject()
    properties.addProperty(GeofencingPropertiesKeys.DWELL_TIME_KEY, DWELL_TIME)
    // for geofences represented by Point the defaultRadius from [GeofencingOptions] is used.
    val pointFeature = Feature.fromGeometry(
      interactionContext.coordinateInfo.coordinate,
      properties,
      featureId
    )

    mapboxMap.getStyle { style ->
      geofencing.addFeature(pointFeature, logGeofencingError<String>("addCustomFeature"))
      circleFeature.addFeature(style, CUSTOM_ZONE_SOURCE_ID, CUSTOM_ZONE_DATA_ID)
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

  @SuppressLint("SetTextI18n")
  private fun loadGeofences() {
    showProgress(true)
    importGeofencesJob?.cancel()
    clearGeofences()
    mapboxMap.getStyle { style ->
      removeFeaturesFromMap(style)
    }
    importGeofencesJob = lifecycleScope.launch {
      try {
        withContext(Dispatchers.IO) {
          loadGeofencesFromNetwork()
        }
        getPreferences(Context.MODE_PRIVATE).edit {
          putBoolean("network_geofences_loaded", true)
        }
        binding.buttonLoadGeofenceZones.text = "Reload GeoJson zones"
      } catch (e: Exception) {
        logD(TAG, "exception handler: $e")
      } finally {
        showProgress(false)
      }
    }
  }

  private fun removeFeaturesFromMap(style: Style) {
    dwellFeatures.forEach {
      it.removeFeature(style, DWELL_SOURCE_ID, DWELL_DATA_ID)
    }
    entryFeatures.forEach {
      it.removeFeature(style, ENTRY_SOURCE_ID, ENTRY_DATA_ID)
    }
    exitFeature.removeFeature(style, EXIT_SOURCE_ID, EXIT_DATA_ID)
  }

  private fun CoroutineScope.loadGeofencesFromNetwork() {
    val dataSet = loadDataSetFromNetwork()
    ensureActive()
    val features = FeatureCollection.fromJson(dataSet)
    loadDataToGeofenceEngine(features.features())
  }

  private fun CoroutineScope.loadDataToGeofenceEngine(features: List<Feature>?) {
    val logGeofencingErrorFunction = logGeofencingError<String>("addFeature")
    var counter = 0
    features?.forEach { feature ->
      if (counter++ % 500 == 0) {
        ensureActive()
      }
      val properties = feature.properties()!!
      val id =
        properties.get("FID")!!.asString + "-" + properties.get("Fid_1")!!.asString + properties.get(
          "Id"
        )!!.asString
      properties.addProperty(GeofencingPropertiesKeys.DWELL_TIME_KEY, DWELL_TIME)
      val featureWithId = Feature.fromGeometry(
        feature.geometry(), properties, id
      )
      geofencing.addFeature(featureWithId, logGeofencingErrorFunction)
    }
  }

  private fun showProgress(inProgress: Boolean) {
    with(binding) {
      buttonLoadGeofenceZones.isEnabled = !inProgress
      progress.isVisible = inProgress
    }
  }

  private fun loadDataSetFromNetwork(): String {
    val client = OkHttpClient()
    val request = Request.Builder()
      .url(datastoreBaseUrl)
      .build()

    val response = client.newCall(request).execute()
    return response.body?.string() ?: ""
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
          val geofencingEvent = GeofencingEvent(
            geofenceState.feature,
            geofenceState.timestamp ?: Date(),
          )
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

  /***
   * Custom attribution to show the link to HSL public dataset
   */
  inner class CustomAttributionDialog(
    private val context: Context,
  ) : AttributionDialogManager, DialogInterface.OnClickListener {

    private lateinit var attributionList: MutableList<Attribution>
    private var dialog: AlertDialog? = null
    private var mapAttributionDelegate: MapAttributionDelegate? = null

    override fun showAttribution(mapAttributionDelegate: MapAttributionDelegate) {
      this.mapAttributionDelegate = mapAttributionDelegate
      attributionList =
        mapAttributionDelegate.parseAttributions(
          context,
          AttributionParserConfig()
        ).toMutableList()

      // HSL Attribution
      attributionList.add(
        0,
        Attribution(
          "Geofence boundaries courtesy Helsingin seuden liiken HSL (CC-BY)",
          "https://hri.fi/data/en_GB/dataset/hsl-n-taksavyohykkeet"
        )
      )
      var isActivityFinishing = false
      if (context is Activity) {
        isActivityFinishing = context.isFinishing
      }
      if (!isActivityFinishing) {
        val attributionTitles = attributionList.map { it.title }.toTypedArray()
        val builder = AlertDialog.Builder(context)
        builder.setTitle(com.mapbox.maps.plugin.attribution.R.string.mapbox_attributionsDialogTitle)
        builder.setAdapter(
          ArrayAdapter(
            context,
            com.mapbox.maps.plugin.attribution.R.layout.mapbox_attribution_list_item,
            attributionTitles
          ),
          this
        )
        dialog = builder.show()
      }
    }

    override fun onStop() {
      dialog?.takeIf { it.isShowing }?.dismiss()
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
      showWebPage(attributionList[which].url)
    }

    private fun showWebPage(url: String) {
      if (context is Activity) {
        try {
          val intent = Intent(Intent.ACTION_VIEW)
          intent.data = Uri.parse(url)
          context.startActivity(intent)
        } catch (exception: ActivityNotFoundException) {
          Toast.makeText(
            context,
            com.mapbox.maps.plugin.attribution.R.string.mapbox_attributionErrorNoBrowser,
            Toast.LENGTH_LONG
          ).show()
        }
      }
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
    private const val CUSTOM_ZONE_SOURCE_ID = "custom_zone_source_id"
    private const val CUSTOM_ZONE_LAYER_ID = "custom_zone_layer_id"
    private const val CUSTOM_ZONE_DATA_ID = "custom_zone_data_id"
    private const val CUSTOM_GEOFENCE_RADIUS = 500
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

      val intent = Intent(appContext, GeofencingActivity::class.java).apply {
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
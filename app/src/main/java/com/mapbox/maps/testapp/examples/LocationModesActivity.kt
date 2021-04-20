package com.mapbox.maps.testapp.examples

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.RectF
import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ListPopupWindow
import com.mapbox.android.core.location.LocationEngineRequest
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.location.*
import com.mapbox.maps.plugin.location.listeneres.CancelableCallback
import com.mapbox.maps.plugin.location.listeneres.OnCameraTrackingChangedListener
import com.mapbox.maps.plugin.location.listeneres.OnLocationCameraTransitionListener
import com.mapbox.maps.plugin.location.listeneres.OnLocationClickListener
import com.mapbox.maps.plugin.location.modes.CameraMode
import com.mapbox.maps.plugin.location.modes.RenderMode
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.utils.LocationPermissionHelper
import kotlinx.android.synthetic.main.activity_location_layer_mode.*
import java.util.*

class LocationModesActivity :
  AppCompatActivity(),
  OnLocationClickListener,
  OnCameraTrackingChangedListener {

  private lateinit var locationModeBtn: Button
  private lateinit var locationPermissionHelper: LocationPermissionHelper
  private lateinit var mapboxMap: MapboxMap
  private var locationPlugin: LocationPluginImpl? = null
  private lateinit var locationTrackingBtn: Button
  private lateinit var protectedGestureArea: View

  private var cameraMode = CameraMode.TRACKING
  private var renderMode = RenderMode.NORMAL

  private var defaultStyle = false

  private var lastLocation: Location? = null
  private var lastStyleUri = Style.DARK

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_location_layer_mode)

    protectedGestureArea = findViewById(R.id.view_protected_gesture_area)

    locationModeBtn = findViewById(R.id.button_location_mode)
    locationModeBtn.setOnClickListener {
      locationPlugin?.let { showModeListDialog() }
    }
    locationTrackingBtn = findViewById(R.id.button_location_tracking)
    locationTrackingBtn.setOnClickListener {
      if (locationPlugin == null) {
        return@setOnClickListener
      }
      locationPlugin?.let { showTrackingListDialog() }
    }

    if (savedInstanceState != null) {
      cameraMode = CameraMode.valueOf(savedInstanceState.getString(SAVED_STATE_CAMERA, "NONE"))
      renderMode = RenderMode.valueOf(savedInstanceState.getString(SAVED_STATE_RENDER, "NORMAL"))
      lastLocation = savedInstanceState.getParcelable(SAVED_STATE_LOCATION)
    }

    mapboxMap = mapView.getMapboxMap()

    locationPermissionHelper = LocationPermissionHelper(this)
    locationPermissionHelper.checkPermissions {
      onMapReady()
    }
  }

  private fun onMapReady() {
    mapboxMap.loadStyleUri(
      Style.MAPBOX_STREETS
    ) {
      initLocationComponent(it)
      lastStyleUri = Style.MAPBOX_STREETS
    }
  }

  private fun initLocationComponent(style: Style) {
    locationPlugin = mapView.locationLegacy()
    // Activate with a built LocationComponentActivationOptions object
    locationPlugin?.let {
      it.activateLocationComponent(
        LocationComponentActivationOptions
          .builder(this, style)
          .useDefaultLocationEngine(true)
          .locationEngineRequest(
            LocationEngineRequest.Builder(750)
              .setFastestInterval(750)
              .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
              .build()
          )
          // Enable 3d model layer
          .locationModelLayerOptions(LocationModelLayerOptions("asset://race_car_model.gltf"))
          .build()
      )
      toggleStyle()
      it.enabled = true
      it.addOnLocationClickListener(this)
      it.addOnCameraTrackingChangedListener(this)
      it.cameraMode = cameraMode
      setRendererMode(renderMode)
      it.forceLocationUpdate(lastLocation)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_location_mode, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (locationPlugin == null) {
      return super.onOptionsItemSelected(item)
    }
    val id = item.itemId
    if (id == R.id.action_style_change) {
      toggleStyle()
      return true
    } else if (id == R.id.action_map_style_change) {
      toggleMapStyle()
      return true
    } else if (id == R.id.action_component_disable) {
      locationPlugin?.enabled = false
      return true
    } else if (id == R.id.action_component_enabled) {
      locationPlugin?.enabled = true
      return true
    } else if (id == R.id.action_gestures_management_disabled) {
      disableGesturesManagement()
      return true
    } else if (id == R.id.action_gestures_management_enabled) {
      enableGesturesManagement()
      return true
    } else if (id == R.id.action_component_throttling_enabled) {
      locationPlugin?.setMaxAnimationFps(5)
    } else if (id == R.id.action_component_throttling_disabled) {
      locationPlugin?.setMaxAnimationFps(Int.MAX_VALUE)
    } else if (id == R.id.action_component_animate_while_tracking) {
      locationPlugin?.zoomWhileTracking(
        17.0, 750,
        object : CancelableCallback {
          override fun onCancel() { // No impl
          }

          override fun onFinish() {
            locationPlugin?.pitchWhileTracking(60.0)
          }
        }
      )
      if (locationPlugin?.cameraMode === CameraMode.NONE) {
        Toast.makeText(this, "Not possible to animate - not tracking", Toast.LENGTH_SHORT).show()
      }
    } else if (id == R.id.action_component_padding_animation_while_tracking) {
      val paddingRandom = Random()
      locationPlugin?.paddingWhileTracking(
        doubleArrayOf(
          paddingRandom.nextDouble() * 500 * if (paddingRandom.nextBoolean()) -1 else 1,
          paddingRandom.nextDouble() * 500 * if (paddingRandom.nextBoolean()) -1 else 1,
          paddingRandom.nextDouble() * 500 * if (paddingRandom.nextBoolean()) -1 else 1,
          paddingRandom.nextDouble() * 500 * if (paddingRandom.nextBoolean()) -1 else 1
        ),
        1000L,
        object : CancelableCallback {
          override fun onCancel() {
          }

          override fun onFinish() {
            locationPlugin?.zoomWhileTracking(16.0)
          }
        }
      )
      if (locationPlugin?.cameraMode == CameraMode.NONE) {
        Toast.makeText(this, "Not possible to animate - not tracking", Toast.LENGTH_SHORT).show()
      }
    }
    return super.onOptionsItemSelected(item)
  }

  @SuppressLint("SetTextI18n")
  private fun setRendererMode(mode: RenderMode) {
    renderMode = mode
    locationPlugin?.renderMode = mode
    when (mode) {
      RenderMode.NORMAL -> {
        locationModeBtn.text = "Normal"
      }
      RenderMode.COMPASS -> {
        locationModeBtn.text = "Compass"
      }
      RenderMode.GPS -> {
        locationModeBtn.text = "Gps"
      }
    }
  }

  private fun toggleStyle() {
    locationPlugin?.let {
      defaultStyle = !defaultStyle
      var options: LocationComponentOptions = LocationComponentOptions.createFromAttributes(
        this,
        if (defaultStyle) R.style.mapbox_LocationComponent else R.style.CustomLocationComponent
      )
      if (defaultStyle) {
        val padding: IntArray =
          if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            intArrayOf(750, 0, 0, 0)
          } else {
            intArrayOf(250, 0, 0, 0)
          }
        @Suppress("DEPRECATION")
        options = options.toBuilder()
          .padding(padding)
          .build()
      }
      it.applyStyle(options)
    }
  }

  private fun toggleMapStyle() {
    locationPlugin?.let {
      val styleUrl = if (lastStyleUri == Style.DARK) Style.LIGHT else Style.DARK
      mapboxMap.loadStyleUri(
        styleUrl
      ) { lastStyleUri = styleUrl }
    }
  }

  private fun disableGesturesManagement() {
    locationPlugin?.let {
      val params = protectedGestureArea.layoutParams
      params.height = 0
      params.width = 0
      protectedGestureArea.layoutParams = params

      val options: LocationComponentOptions = it
        .locationComponentOptions
        .toBuilder()
        .trackingGesturesManagement(false)
        .build()
      it.applyStyle(options)
    }
  }

  private fun enableGesturesManagement() {
    locationPlugin?.let {
      val rectF = RectF(0f, 0f, mapView.width / 2f, mapView.height / 2f)
      val params = protectedGestureArea.layoutParams
      params.height = rectF.bottom.toInt()
      params.width = rectF.right.toInt()
      protectedGestureArea.layoutParams = params

      val options: LocationComponentOptions = it
        .locationComponentOptions
        .toBuilder()
        .trackingGesturesManagement(true)
        .trackingMultiFingerProtectedMoveArea(rectF)
        .trackingMultiFingerMoveThreshold(500f)
        .build()
      it.applyStyle(options)
    }
  }

  private fun showModeListDialog() {
    val modes: MutableList<String> = ArrayList()
    modes.add("Normal")
    modes.add("Compass")
    modes.add("GPS")
    val profileAdapter = ArrayAdapter(
      this,
      android.R.layout.simple_list_item_1, modes
    )
    val listPopup =
      ListPopupWindow(this)
    listPopup.setAdapter(profileAdapter)
    listPopup.anchorView = locationModeBtn
    listPopup.setOnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->
      val selectedMode = modes[position]
      locationModeBtn.text = selectedMode
      when {
        selectedMode.contentEquals("Normal") -> {
          setRendererMode(RenderMode.NORMAL)
        }
        selectedMode.contentEquals("Compass") -> {
          setRendererMode(RenderMode.COMPASS)
        }
        selectedMode.contentEquals("GPS") -> {
          setRendererMode(RenderMode.GPS)
        }
      }
      listPopup.dismiss()
    }
    listPopup.show()
  }

  private fun showTrackingListDialog() {
    val trackingTypes: MutableList<String> =
      ArrayList()
    trackingTypes.add("None")
    trackingTypes.add("None Compass")
    trackingTypes.add("None GPS")
    trackingTypes.add("Tracking")
    trackingTypes.add("Tracking Compass")
    trackingTypes.add("Tracking GPS")
    trackingTypes.add("Tracking GPS North")
    val profileAdapter = ArrayAdapter(
      this,
      android.R.layout.simple_list_item_1, trackingTypes
    )
    val listPopup =
      ListPopupWindow(this)
    listPopup.setAdapter(profileAdapter)
    listPopup.anchorView = locationTrackingBtn
    listPopup.setOnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->
      val selectedTrackingType = trackingTypes[position]
      locationTrackingBtn.text = selectedTrackingType
      when {
        selectedTrackingType.contentEquals("None") -> {
          setCameraTrackingMode(CameraMode.NONE)
        }
        selectedTrackingType.contentEquals("None Compass") -> {
          setCameraTrackingMode(CameraMode.NONE_COMPASS)
        }
        selectedTrackingType.contentEquals("None GPS") -> {
          setCameraTrackingMode(CameraMode.NONE_GPS)
        }
        selectedTrackingType.contentEquals("Tracking") -> {
          setCameraTrackingMode(CameraMode.TRACKING)
        }
        selectedTrackingType.contentEquals("Tracking Compass") -> {
          setCameraTrackingMode(CameraMode.TRACKING_COMPASS)
        }
        selectedTrackingType.contentEquals("Tracking GPS") -> {
          setCameraTrackingMode(CameraMode.TRACKING_GPS)
        }
        selectedTrackingType.contentEquals("Tracking GPS North") -> {
          setCameraTrackingMode(CameraMode.TRACKING_GPS_NORTH)
        }
      }
      listPopup.dismiss()
    }
    listPopup.show()
  }

  private fun setCameraTrackingMode(mode: CameraMode) {
    locationPlugin!!.setCameraMode(
      mode, 1200, 16.0, null, 45.0,
      object : OnLocationCameraTransitionListener {
        override fun onLocationCameraTransitionFinished(cameraMode: CameraMode) {
          Toast.makeText(this@LocationModesActivity, "Transition finished", Toast.LENGTH_SHORT)
            .show()
        }

        override fun onLocationCameraTransitionCanceled(cameraMode: CameraMode) {
          Toast.makeText(this@LocationModesActivity, "Transition canceled", Toast.LENGTH_SHORT)
            .show()
        }
      }
    )
  }

  override fun onStart() {
    super.onStart()
    mapView.onStart()
  }

  override fun onStop() {
    super.onStop()
    mapView.onStop()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putString(SAVED_STATE_CAMERA, cameraMode.toString())
    outState.putString(SAVED_STATE_RENDER, renderMode.toString())
    locationPlugin?.let {
      outState.putParcelable(
        SAVED_STATE_LOCATION,
        it.lastKnownLocation
      )
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

  companion object {
    private const val SAVED_STATE_CAMERA = "saved_state_camera"
    private const val SAVED_STATE_RENDER = "saved_state_render"
    private const val SAVED_STATE_LOCATION = "saved_state_location"
  }

  override fun onLocationComponentClick() {
    Toast.makeText(this, "OnLocationComponentClick", Toast.LENGTH_LONG).show()
  }

  @SuppressLint("SetTextI18n")
  override fun onCameraTrackingChanged(currentMode: CameraMode?) {
    cameraMode = currentMode!!
    when (currentMode) {
      CameraMode.NONE -> {
        locationTrackingBtn.text = "None"
      }
      CameraMode.NONE_COMPASS -> {
        locationTrackingBtn.text = "None Compass"
      }
      CameraMode.NONE_GPS -> {
        locationTrackingBtn.text = "None GPS"
      }
      CameraMode.TRACKING -> {
        locationTrackingBtn.text = "Tracking"
      }
      CameraMode.TRACKING_COMPASS -> {
        locationTrackingBtn.text = "Tracking Compass"
      }
      CameraMode.TRACKING_GPS -> {
        locationTrackingBtn.text = "Tracking GPS"
      }
      CameraMode.TRACKING_GPS_NORTH -> {
        locationTrackingBtn.text = "Tracking GPS North"
      }
    }
  }

  @SuppressLint("SetTextI18n")
  override fun onCameraTrackingDismissed() {
    locationTrackingBtn.text = "None"
  }
}
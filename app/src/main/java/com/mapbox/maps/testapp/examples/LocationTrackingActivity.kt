package com.mapbox.maps.testapp.examples

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.layers.properties.generated.IconRotationAlignment
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.generated.SymbolManager
import com.mapbox.maps.plugin.annotation.generated.SymbolOptions
import com.mapbox.maps.plugin.annotation.generated.createSymbolManager
import com.mapbox.maps.plugin.annotation.getAnnotationPlugin
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.getGesturesPlugin
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.getLocationComponentPlugin
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.utils.LocationPermissionHelper
import com.mapbox.maps.toJson
import kotlinx.android.synthetic.main.activity_location_layer_mode.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Tracks the user location on screen, simulates a navigation session.
 */
class LocationTrackingActivity : AppCompatActivity() {

  companion object {
    private val ANNOTATION_TEXT_FONT_ARRAY_BOLD =
      listOf(
        "Caveat Bold",
        "Arial Unicode MS Bold"
      )

    private val ANNOTATION_TEXT_FONT_ARRAY_REGULAR = //listOf("Open Sans Regular", "Arial Unicode MS Regular")
      listOf(
        "Caveat Regular",
        "Arial Unicode MS Regular"
      )

  }

  private lateinit var locationPermissionHelper: LocationPermissionHelper

  private lateinit var symbolManager: SymbolManager
  private var symbolLocation: Point? = null

  private var lastStyleUri = Style.DARK

  private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
    mapView.getMapboxMap().jumpTo(CameraOptions.Builder().bearing(it).build())
  }

  private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
    mapView.getMapboxMap().jumpTo(CameraOptions.Builder().center(it).build())
    if (symbolLocation == null) {
      symbolLocation = it
    }
    mapView.getGesturesPlugin().focalPoint = mapView.getMapboxMap().pixelForCoordinate(it)
  }

  private val onMoveListener = object : OnMoveListener {
    override fun onMoveBegin(detector: MoveGestureDetector) {
      onCameraTrackingDismissed()
    }

    override fun onMove(detector: MoveGestureDetector): Boolean {
      return false
    }

    override fun onMoveEnd(detector: MoveGestureDetector) {}
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_simple_map)
    locationPermissionHelper = LocationPermissionHelper(this)
    locationPermissionHelper.checkPermissions {
      onMapReady()
    }
  }

  private fun onMapReady() {
    mapView.getMapboxMap().jumpTo(
      CameraOptions.Builder()
        .zoom(14.0)
        .build()
    )
    mapView.getMapboxMap().loadStyleUri(
      lastStyleUri
    ) {
      initLocationComponent()
      setupGesturesListener()
      initializeSymbolManager(null)
    }
  }

  private fun setupGesturesListener() {
    mapView.getGesturesPlugin().addOnMoveListener(onMoveListener)
  }

  private fun initLocationComponent() {
    val locationComponentPlugin = mapView.getLocationComponentPlugin()
    locationComponentPlugin.updateSettings {
      this.enabled = true
      this.locationPuck = LocationPuck2D(
        bearingImage = AppCompatResources.getDrawable(
          this@LocationTrackingActivity,
          R.drawable.mapbox_user_puck_icon,
        ),
        shadowImage = AppCompatResources.getDrawable(
          this@LocationTrackingActivity,
          R.drawable.mapbox_user_icon_shadow,
        ),
        scaleExpression = interpolate {
          linear()
          zoom()
          stop {
            literal(0.0)
            literal(0.6)
          }
          stop {
            literal(20.0)
            literal(1.0)
          }
        }.toJson()
      )
    }
    locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
    locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
  }


  fun printLayer(v: View?) {
    mapView.getMapboxMap().getStyle()?.let {
      for (layer in it.styleLayers) {
        Log.d("-->", "layer: ${layer.id}")
      }
    }
    Log.d("-->", "symbolLocation: $symbolLocation")
  }

  fun deleteSymbol(v: View?) {
    symbolManager.deleteAll()
  }

  fun addSymbol(v: View?) {

    symbolManager.deleteAll()

    symbolLocation?.let {
      // put a text symbol with background, and customized text
      val symbolOption: SymbolOptions = SymbolOptions()
        .withPoint(it)
        .withTextColor("4384BD")
        .withTextFont(ANNOTATION_TEXT_FONT_ARRAY_REGULAR)
        .withTextField("Test")
        .withTextSize(26.0)
        .withIconSize(1.0)
        .withIconImage("ID_SYMBOL_ONE")

      symbolManager.create(symbolOption)

      Toast.makeText(this, "Adding Symbol", Toast.LENGTH_SHORT).show()

    } ?: run {
      Toast.makeText(this,
        "Do not have location to place symbol, please try again", Toast.LENGTH_SHORT
      ).show()
    }
  }

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  fun toggleMapStyle(v: View?) {
    val styleUrl = if (lastStyleUri == Style.DARK) Style.LIGHT else Style.DARK
    mapView.getMapboxMap().loadStyleUri(styleUrl) {
      lastStyleUri = styleUrl
      val selectedRouteLeftBitmap = resources
        .getDrawable(R.drawable.ic_faster_route_current, null).toBitmap()
      it.addImage("ID_SYMBOL_ONE", selectedRouteLeftBitmap)
    }
  }

  private fun initializeSymbolManager(belowLayer: String?) {
    symbolManager =
      mapView.getAnnotationPlugin().createSymbolManager(
        mapView,
        AnnotationConfig(belowLayer, "test-layer")
      )
    symbolManager.iconAllowOverlap = true
    symbolManager.textAllowOverlap = true
    symbolManager.iconIgnorePlacement = true
    symbolManager.textIgnorePlacement = true

    symbolManager.iconRotationAlignment = IconRotationAlignment.VIEWPORT

    mapView.getMapboxMap().getStyle()?.let { style ->

      val selectedRouteLeftBitmap = resources
        .getDrawable(R.drawable.ic_faster_route_current, null).toBitmap()

      style.addImage("ID_SYMBOL_ONE", selectedRouteLeftBitmap)
    }
  }


  private fun onCameraTrackingDismissed() {
    Toast.makeText(this, "onCameraTrackingDismissed", Toast.LENGTH_SHORT).show()
    mapView.getLocationComponentPlugin()
      .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
    mapView.getLocationComponentPlugin()
      .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
    mapView.getGesturesPlugin().removeOnMoveListener(onMoveListener)
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
    mapView.getLocationComponentPlugin()
      .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
    mapView.getLocationComponentPlugin()
      .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
    mapView.getGesturesPlugin().removeOnMoveListener(onMoveListener)
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<String>,
    grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
  }
}
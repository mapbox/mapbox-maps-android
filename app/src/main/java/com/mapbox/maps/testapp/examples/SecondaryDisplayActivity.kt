package com.mapbox.maps.testapp.examples

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.mapbox.common.Logger
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.image.image
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.locationcomponent.getLocationComponentPlugin
import com.mapbox.maps.testapp.R
import com.mapbox.maps.toJson
import kotlinx.android.synthetic.main.activity_multi_display.*
import kotlinx.android.synthetic.main.activity_simple_map.mapView

/**
 * Example of displaying a map on a secondary display.
 */
class SecondaryDisplayActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_secondary_display)
    val rootView = SymbolBitmapGenerator.inflateStreetNameRootView(
      this,
      "Street Name",
    )

    val newBitMap = SymbolBitmapGenerator.generate(rootView)
    Logger.e(
      "testtest",
      "SecondaryDisplayActivity, first bitmap:  ${SymbolBitmapGenerator.bitmap!!.width}, ${SymbolBitmapGenerator.bitmap!!.height}"
    )
    Logger.e(
      "testtest",
      "SecondaryDisplayActivity, second bitmap: ${newBitMap.width}, ${newBitMap.height}"
    )

    mapView.getLocationComponentPlugin().updateSettings {
      enabled = true
      locationPuck = LocationPuck2D(
        bearingImage = AppCompatResources.getDrawable(
          this@SecondaryDisplayActivity,
          R.drawable.mapbox_user_puck_icon,
        ),
        shadowImage = AppCompatResources.getDrawable(
          this@SecondaryDisplayActivity,
          R.drawable.mapbox_user_icon_shadow,
        ),
        scaleExpression = literal(3.5/this@SecondaryDisplayActivity.resources.displayMetrics.density.toDouble()).toJson()
      )
    }

    mapView.getMapboxMap().loadStyle(
      style(Style.DARK) {
        +image(IMAGE_ID) {
          bitmap(newBitMap)
          // Note: The default scale doesn't work with secondary displays.
          // we need to manually set the scale to the pixel for the current context
          scale(this@SecondaryDisplayActivity.resources.displayMetrics.density)
          Logger.e(
            "testtest",
            "SecondaryDisplayActivity, scale: ${this@SecondaryDisplayActivity.resources.displayMetrics.density}"
          )
        }
        +geoJsonSource(SOURCE_ID) {
          geometry(HELSINKI)
        }
        +symbolLayer(LAYER_ID, SOURCE_ID) {
          iconImage(IMAGE_ID)
          iconAnchor(IconAnchor.BOTTOM)
          textField("Helsinki")
          iconAllowOverlap(true)
        }
      }
    )
    mapView.getLocationComponentPlugin().addOnIndicatorPositionChangedListener {
      mapView.getMapboxMap().jumpTo(CameraOptions.Builder().center(it).build())
    }
    moveCameraButton.setOnClickListener {
      moveCamera()
    }
  }

  private fun moveCamera() {
    val cameraOption = CameraOptions.Builder()
      .center(HELSINKI)
      .zoom(ZOOM)
      .pitch(60.0)
      .build()

    mapView.getMapboxMap().flyTo(
      cameraOption,
      MapAnimationOptions.mapAnimationOptions {
        duration(DURATION)
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

  companion object {
    private val HELSINKI = Point.fromLngLat(24.9384, 60.1699)
    private const val ZOOM = 14.0
    private const val DURATION = 3000L
    private const val IMAGE_ID = "test-image"
    private const val LAYER_ID = "symbol-layer-id"
    private const val SOURCE_ID = "source-id"
  }
}
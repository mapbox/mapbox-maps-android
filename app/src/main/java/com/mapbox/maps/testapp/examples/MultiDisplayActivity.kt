package com.mapbox.maps.testapp.examples

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.hardware.display.DisplayManager
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.mapbox.common.Logger
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.image.image
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.locationcomponent.getLocationComponentPlugin
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.examples.SymbolBitmapGenerator.inflateStreetNameRootView
import kotlinx.android.synthetic.main.activity_multi_display.*
import kotlinx.android.synthetic.main.activity_simple_map.mapView

/**
 * Example of displaying a map on the second screen.
 *
 * To use this example, you need to attach a second display to your device, or
 * have the simulated secondary display enabled in the developer settings.
 */
class MultiDisplayActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_multi_display)
    val rootView = inflateStreetNameRootView(
      this,
      "Street Name",
    )

    SymbolBitmapGenerator.bitmap = SymbolBitmapGenerator.generate(rootView)

    mapView.getLocationComponentPlugin().updateSettings {
      enabled = true
      locationPuck = LocationPuck2D(
        bearingImage = AppCompatResources.getDrawable(
          this@MultiDisplayActivity,
          R.drawable.mapbox_user_puck_icon,
        ),
        shadowImage = AppCompatResources.getDrawable(
          this@MultiDisplayActivity,
          R.drawable.mapbox_user_icon_shadow,
        )
      )
    }

    mapView.getMapboxMap().loadStyle(
      style(Style.DARK) {
        +image(IMAGE_ID) {
          bitmap(SymbolBitmapGenerator.bitmap!!)
          scale(this@MultiDisplayActivity.resources.displayMetrics.density)
          Logger.e("testtest", "MultiDisplayActivity, scale: ${this@MultiDisplayActivity.resources.displayMetrics.density}")
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

    displayOnSecondDisplayButton.setOnClickListener {
      displayMapInSecondaryScreen()
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
      mapAnimationOptions {
        duration(DURATION)
      }
    )
  }

  private fun displayMapInSecondaryScreen() {
    val displayManager = getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
    val displays = displayManager.displays
    displays.forEach {
      var metrics = DisplayMetrics()
      it.getMetrics(metrics)
      Logger.e("testtest", metrics.toString())
    }
    if (displays.size > 1) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Use activity options to select the display screen.
        val options = ActivityOptions.makeBasic()
        options.launchDisplayId = displays[1].displayId
        // To display on the second screen, the intent must be launched using singleTask launchMode.
        startActivity(
          Intent(this, SecondaryDisplayActivity::class.java),
          options.toBundle()
        )
      }
    } else {
      Toast.makeText(this, "Second screen not found.", Toast.LENGTH_SHORT).show()
    }
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

object SymbolBitmapGenerator {
  fun generate(view: View): Bitmap {
    val measureSpec: Int = View.MeasureSpec.makeMeasureSpec(
      0,
      View.MeasureSpec.UNSPECIFIED
    )
    view.measure(measureSpec, measureSpec)
    val measuredWidth: Int = view.measuredWidth
    val measuredHeight: Int = view.measuredHeight
    view.layout(0, 0, measuredWidth, measuredHeight)

    val bitmap = Bitmap.createBitmap(
      measuredWidth,
      measuredHeight,
      Bitmap.Config.ARGB_8888
    )
    bitmap.eraseColor(Color.TRANSPARENT)

    val canvas = Canvas(bitmap)
    view.draw(canvas)

    return bitmap
  }

  fun inflateStreetNameRootView(
    context: Context,
    streetName: String?
  ): View {

    val rootView =
      LayoutInflater.from(context).inflate(
        R.layout.label_street_name_ic_view, null, false
      )

    val instructionStreetNameView =
      rootView.findViewById<TextView>(R.id.label_street_name_view)
    instructionStreetNameView.text = streetName

    return rootView
  }

  var bitmap: Bitmap? = null
}
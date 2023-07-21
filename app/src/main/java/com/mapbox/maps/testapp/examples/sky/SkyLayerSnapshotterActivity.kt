package com.mapbox.maps.testapp.examples.sky

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.skyLayer
import com.mapbox.maps.extension.style.layers.properties.generated.SkyType
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.scalebar.scalebar
import com.mapbox.maps.testapp.databinding.ActivitySkySnapshotterBinding

/**
 * Prototype for Junction view showing upcoming maneuver.
 */
class SkyLayerSnapshotterActivity : AppCompatActivity() {

  private var snapshotter: Snapshotter? = null
  private lateinit var binding: ActivitySkySnapshotterBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySkySnapshotterBinding.inflate(layoutInflater)
    setContentView(binding.root)
    binding.mapView.scalebar.enabled = false
    binding.mapView.compass.enabled = false
    binding.mapView.getMapboxMap().setCamera(
      CameraOptions.Builder()
        .center(Point.fromLngLat(24.827187523937795, 60.55932732152849))
        .zoom(16.0)
        .pitch(85.0)
        .bearing(330.1)
        .build()
    )
    binding.mapView.getMapboxMap().loadStyle(
      styleExtension = style(Style.OUTDOORS) {
        +skyLayer("sky") {
          skyType(SkyType.GRADIENT)
          skyGradient(
            interpolate {
              linear()
              skyRadialProgress()
              literal(0.0)
              literal("yellow")
              literal(1.0)
              literal("pink")
            }
          )
          skyGradientCenter(listOf(-34.0, 90.0))
          skyGradientRadius(8.0)
        }
      }
    ) { prepareSnapshotter() }
  }

  private fun prepareSnapshotter() {
    val snapshotMapOptions = MapSnapshotOptions.Builder()
      .size(Size(512.0f, 512.0f))
      .build()

    snapshotter = Snapshotter(this, snapshotMapOptions).apply {
      setStyleListener(object : SnapshotStyleListener {
        override fun onDidFinishLoadingStyle(style: Style) {
          style.addLayer(
            skyLayer("sky_snapshotter") {
              skyType(SkyType.ATMOSPHERE)
              skyAtmosphereSun(listOf(0.0, 90.0))
            }
          )
        }
      })
      setCamera(
        CameraOptions.Builder()
          .center(Point.fromLngLat(24.81958807097479, 60.56524768721757))
          .zoom(16.0)
          .pitch(85.0)
          .bearing(330.1)
          .build()
      )
      setStyleUri(Style.STANDARD)
      start { bitmap, error ->
        if (bitmap != null) {
          binding.maneuverView.setImageBitmap(bitmap)
          binding.maneuverCaption.visibility = View.VISIBLE
        } else {
          Toast.makeText(
            this@SkyLayerSnapshotterActivity,
            "Snapshotter error: $error",
            Toast.LENGTH_SHORT
          ).show()
        }
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    snapshotter?.cancel()
  }
}
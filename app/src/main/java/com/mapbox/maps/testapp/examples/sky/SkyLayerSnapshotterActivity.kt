package com.mapbox.maps.testapp.examples.sky

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.bindgen.Expected
import com.mapbox.common.Logger
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.SkyLayer
import com.mapbox.maps.extension.style.layers.generated.skyLayer
import com.mapbox.maps.extension.style.layers.properties.generated.SkyType
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.compass.getCompassPlugin
import com.mapbox.maps.plugin.scalebar.getScaleBarPlugin
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_sky_snapshotter.*
import java.nio.ByteBuffer

/**
 * Prototype for Junction view showing upcoming maneuver.
 */
class SkyLayerSnapshotterActivity : AppCompatActivity() {

  private var snapshotter: Snapshotter? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_sky_snapshotter)
    mapView.getScaleBarPlugin().enabled = false
    mapView.getCompassPlugin().enabled = false
    mapView.getMapboxMap().jumpTo(
      CameraOptions.Builder()
        .center(Point.fromLngLat(24.827187523937795, 60.55932732152849))
        .zoom(16.0)
        .pitch(85.0)
        .bearing(330.1)
        .build()
    )
    mapView.getMapboxMap().loadStyle(
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
      .resourceOptions(MapboxOptions.getDefaultResourceOptions(this))
      .build()

    snapshotter = Snapshotter(this, snapshotMapOptions).apply {
      setCameraOptions(
        CameraOptions.Builder()
          .center(Point.fromLngLat(24.81958807097479, 60.56524768721757))
          .zoom(16.0)
          .pitch(85.0)
          .bearing(330.1)
          .build()
      )
      setUri(Style.MAPBOX_STREETS)
      start(object : Snapshotter.SnapshotReadyCallback {
        override fun onStyleLoaded(style: Style) {
          val skyLayer = SkyLayer("sky_snapshotter")
          skyLayer.skyType(SkyType.ATMOSPHERE)
          skyLayer.skyAtmosphereSun(listOf(0.0, 90.0))
          style.addLayer(skyLayer)
        }

        override fun onSnapshotCreated(snapshot: Expected<MapSnapshotInterface?, String?>) {
          if (snapshot.isError) {
            Logger.e(TAG, "${snapshot.error}")
          } else {
            val image = snapshot.value!!.image()
            val configBmp: Bitmap.Config = Bitmap.Config.ARGB_8888
            val bitmap: Bitmap = Bitmap.createBitmap(image.width, image.height, configBmp)
            val buffer: ByteBuffer = ByteBuffer.wrap(image.data)
            bitmap.copyPixelsFromBuffer(buffer)
            maneuverView.setImageBitmap(bitmap)
            maneuverCaption.visibility = View.VISIBLE
          }
        }
      })
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
    snapshotter?.cancel()
    mapView.onDestroy()
  }

  companion object {
    private const val TAG = "SkySnapshootter"
  }
}
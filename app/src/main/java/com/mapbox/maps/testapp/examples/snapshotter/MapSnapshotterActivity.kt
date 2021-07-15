package com.mapbox.maps.testapp.examples.snapshotter

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.common.Logger
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.Snapshotter
import com.mapbox.maps.bitmap

/**
 * Example demonstrating taking simple snapshot using [Snapshotter].
 */
class MapSnapshotterActivity : AppCompatActivity(), SnapshotStyleListener {

  private lateinit var mapSnapshotter: Snapshotter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val snapshotterOptions = MapSnapshotOptions.Builder()
      .resourceOptions(
        ResourceOptionsManager.getDefault(this).resourceOptions
      )
      .size(Size(512.0f, 512.0f))
      .pixelRatio(1.0f)
      .build()

    mapSnapshotter = Snapshotter(
      this, snapshotterOptions,
      SnapshotOverlayOptions(showLogo = false, showAttributes = false)
    ).apply {
      setStyleListener(this@MapSnapshotterActivity)
      setStyleUri(Style.MAPBOX_STREETS)
      setCamera(
        CameraOptions.Builder().zoom(14.0).center(
          Point.fromLngLat(
            4.895033, 52.374724
          )
        ).build()
      )
      start {
        it?.let { mapSnapshot ->
          val imageView = ImageView(this@MapSnapshotterActivity)
          imageView.setImageBitmap(mapSnapshot.bitmap())
          setContentView(imageView)
        }
      }
    }
  }

  override fun onDidFinishLoadingStyle(style: Style) {
    Logger.i(TAG, "OnStyleLoaded: ${style.styleURI}")
  }

  override fun onDestroy() {
    super.onDestroy()
    mapSnapshotter.cancel()
  }

  companion object {
    const val TAG: String = "MapSnapshotterActivity"
  }
}
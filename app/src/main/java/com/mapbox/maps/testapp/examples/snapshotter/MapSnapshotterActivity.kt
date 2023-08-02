package com.mapbox.maps.testapp.examples.snapshotter

import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.Snapshotter

/**
 * Example demonstrating taking simple snapshot using [Snapshotter].
 */
class MapSnapshotterActivity : AppCompatActivity(), SnapshotStyleListener {

  private lateinit var mapSnapshotter: Snapshotter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val snapshotterOptions = MapSnapshotOptions.Builder()
      .size(Size(512.0f, 512.0f))
      .pixelRatio(1.0f)
      .build()

    mapSnapshotter = Snapshotter(this, snapshotterOptions,).apply {
      setStyleListener(this@MapSnapshotterActivity)
      setStyleUri(Style.STANDARD)
      setCamera(
        CameraOptions.Builder().zoom(14.0).center(
          Point.fromLngLat(
            4.895033, 52.374724
          )
        ).build()
      )
      start(
        overlayCallback = { overlay ->
          overlay.canvas.drawOval(
            RectF(0f, 0f, 100f, 100f),
            Paint().apply { alpha = 128 }
          )
        }
      ) { bitmap, errorMessage ->
        if (errorMessage != null) {
          Toast.makeText(
            this@MapSnapshotterActivity,
            errorMessage,
            Toast.LENGTH_SHORT
          ).show()
        }
        val imageView = ImageView(this@MapSnapshotterActivity)
        imageView.setImageBitmap(bitmap)
        setContentView(imageView)
      }
    }
  }

  override fun onDidFinishLoadingStyle(style: Style) {
    logI(TAG, "OnStyleLoaded: ${style.styleURI}")
  }

  override fun onDestroy() {
    super.onDestroy()
    mapSnapshotter.destroy()
  }

  companion object {
    const val TAG: String = "MapSnapshotterActivity"
  }
}
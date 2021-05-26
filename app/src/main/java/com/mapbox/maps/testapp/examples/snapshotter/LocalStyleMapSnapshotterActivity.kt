package com.mapbox.maps.testapp.examples.snapshotter

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.testapp.R

/**
 * Activity to validate creating a snapshot from a configuration not using style URI or JSON
 */
class LocalStyleMapSnapshotterActivity : AppCompatActivity() {
  private lateinit var mapSnapshotter: Snapshotter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val snapshotterOptions = MapSnapshotOptions.Builder()
      .resourceOptions(
        ResourceOptions.Builder()
          .accessToken(getString(R.string.mapbox_access_token))
          .cachePath(filesDir.absolutePath + "/$DATABASE_NAME")
          .cacheSize(50000L)
          .build()
      )
      .size(Size(512.0f, 512.0f))
      .pixelRatio(1.0f)
      .build()

    mapSnapshotter = Snapshotter(this, snapshotterOptions)
    mapSnapshotter.setCamera(
      CameraOptions.Builder().zoom(14.0).center(
        Point.fromLngLat(
          4.895033, 52.374724
        )
      ).build()
    )
    mapSnapshotter.setStyleJson(
      """
        {
          "version": 8,
          "metadata": {
            "test": {
              "width": 64,
              "height": 64
            }
          },
          "sources": {},
          "layers": [
            {
              "id": "background",
              "type": "background",
              "paint": {
                "background-color": "red"
              }
            }
          ]
        }
      """.trimIndent()
    )
    mapSnapshotter.start {
      it?.let { mapSnapshot ->
        val imageView = ImageView(this)
        imageView.setImageBitmap(mapSnapshot.bitmap())
        setContentView(imageView)
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    mapSnapshotter.cancel()
  }

  companion object {
    const val TAG: String = "LocalStyleMapSnapshotterActivity"
  }
}
package com.mapbox.maps.testapp.examples.snapshotter

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.bindgen.Expected
import com.mapbox.common.Logger
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.Snapshotter
import com.mapbox.maps.testapp.R
import java.nio.ByteBuffer

class MapSnapshotterActivity : AppCompatActivity(), Snapshotter.SnapshotReadyCallback {
  private lateinit var mapSnapshotter: Snapshotter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val snapshotterOptions = MapSnapshotOptions.Builder()
      .resourceOptions(
        ResourceOptions.Builder()
          .accessToken(getString(R.string.mapbox_access_token))
          .cachePath(filesDir.absolutePath + "/$DATABASE_NAME")
          .assetPath(filesDir.absolutePath)
          .cacheSize(50000L)
          .build()
      )
      .size(Size(512.0f, 512.0f))
      .pixelRatio(1.0f)
      .build()

    mapSnapshotter = Snapshotter(this, snapshotterOptions)
    mapSnapshotter.setUri(Style.MAPBOX_STREETS)
    mapSnapshotter.setCameraOptions(
      CameraOptions.Builder().zoom(14.0).center(
        Point.fromLngLat(
          4.895033, 52.374724
        )
      ).build()
    )
    mapSnapshotter.start(this)
  }

  override fun onStyleLoaded(style: Style) {
    Logger.e(TAG, "OnStyleLoaded: ${style.styleURI}")
  }

  override fun onSnapshotCreated(snapshot: Expected<MapSnapshotInterface?, String?>) {
    if (snapshot.isValue) {
      val mapSnapshot = snapshot.value as MapSnapshotInterface
      Logger.e(TAG, "Result: ${mapSnapshot.image().data.size}")

      val image = mapSnapshot.image()

      val configBmp: Bitmap.Config = Bitmap.Config.ARGB_8888
      val bitmap: Bitmap = Bitmap.createBitmap(image.width, image.height, configBmp)
      val buffer: ByteBuffer = ByteBuffer.wrap(image.data)
      bitmap.copyPixelsFromBuffer(buffer)

      val imageView = ImageView(this)
      imageView.setImageBitmap(bitmap)
      setContentView(imageView)
    } else {
      Logger.e(TAG, "Error: ${snapshot.error}")
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    mapSnapshotter.cancel()
  }

  companion object {
    const val TAG: String = "MapSnapshotterActivity"
  }
}
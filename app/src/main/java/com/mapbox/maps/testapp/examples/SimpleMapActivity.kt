package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.postDelayed
import com.mapbox.common.Logger
import com.mapbox.geojson.Point
import com.mapbox.maps.*

/**
 * Example of displaying a map.
 */
class SimpleMapActivity : AppCompatActivity() {

  private lateinit var mapView: MapView
  private var snapshotter: Snapshotter? = null
  private var snapshotCount = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mapView = MapView(this)
    setContentView(mapView)

    val map = mapView.getMapboxMap()
    map.loadStyleUri(Style.MAPBOX_STREETS) {
      map.setCamera(
        CameraOptions.Builder()
          .center(MARKER_POINT)
          .zoom(12.0)
          .build()
      )
    }
  }

  override fun onResume() {
    super.onResume()
    processSnapshot()
  }

  private fun destroySnapshotter() {
    snapshotter?.destroy()
    snapshotter = null
  }

  private fun processSnapshot() {
    destroySnapshotter()

    snapshotter = createSnapshotter().apply {
      setStyleUri(Style.MAPBOX_STREETS)
      start { callback ->
        snapshotCount += 1
        Logger.d("KIRYLDD", "processSnapshot: callback is ${if (callback != null) "non-null" else "null"}")
        mapView.postDelayed(100) { processSnapshot() }
      }
    }
  }


  private fun createSnapshotOptions() =
    MapSnapshotOptions.Builder()
      .size(Size(mapView.width.toFloat(), mapView.height.toFloat()))
      .resourceOptions(MapInitOptions.getDefaultResourceOptions(this))
      .build()

  private fun createSnapshotter() =
    Snapshotter(
      this,
      createSnapshotOptions(),
      SnapshotOverlayOptions(
        showLogo = false,
        showAttributes = false
      )
    ).apply {
      setCamera(
        CameraOptions.Builder()
          .center(Point.fromLngLat(23.760833, 61.498056))
          .zoom(14.0)
          .build()
      )
    }

  companion object {
    val MARKER_POINT: Point = Point.fromLngLat(25.0, 60.25)
  }
}
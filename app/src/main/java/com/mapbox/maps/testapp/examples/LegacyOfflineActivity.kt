package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.common.Logger
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_offline.*

/**
 * Example app that downloads an offline region and when succeeded
 * shows a button to load a map at the offline region definition.
 */
class LegacyOfflineActivity : AppCompatActivity() {

  private lateinit var offlineManager: OfflineRegionManager
  private lateinit var offlineRegion: OfflineRegion
  private var mapView: MapView? = null

  private val regionObserver: OfflineRegionObserver = object : OfflineRegionObserver() {
    override fun mapboxTileCountLimitExceeded(limit: Long) {
      Logger.e(TAG, "Mapbox tile count max (= $limit) has exceeded!")
    }

    override fun statusChanged(status: OfflineRegionStatus) {
      Logger.d(
        TAG,
        "${status.completedResourceCount}/${status.requiredResourceCount} resources; ${status.completedResourceSize} bytes downloaded."
      )
      if (status.downloadState == OfflineRegionDownloadState.INACTIVE) {
        downloadComplete()
        return
      }
    }

    override fun responseError(error: ResponseError) {
      Logger.e(TAG, "onError: ${error.reason}, ${error.message}")
      offlineRegion.setOfflineRegionDownloadState(OfflineRegionDownloadState.INACTIVE)
    }
  }

  private val callback: OfflineRegionCreateCallback = OfflineRegionCreateCallback { expected ->
    if (expected.isValue) {
      expected.value?.let {
        offlineRegion = it
        it.setOfflineRegionObserver(regionObserver)
        it.setOfflineRegionDownloadState(OfflineRegionDownloadState.ACTIVE)
      }
    } else {
      Logger.e(TAG, expected.error!!)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_offline)
    offlineManager = OfflineRegionManager(MapboxOptions.getDefaultResourceOptions((this)))
    offlineManager.createOfflineRegion(
      OfflineRegionGeometryDefinition.Builder()
        .geometry(point)
        .pixelRatio(2f)
        .minZoom(zoom - 2)
        .maxZoom(zoom + 2)
        .styleURL(styleUrl)
        .glyphsRasterizationMode(GlyphsRasterizationMode.NO_GLYPHS_RASTERIZED_LOCALLY)
        .build(),
      callback
    )
  }

  private fun downloadComplete() {
    download_progress.visibility = View.GONE
    show_map_button.visibility = View.VISIBLE
    show_map_button.setOnClickListener {
      it.visibility = View.GONE
      // create mapView
      mapView = MapView(this@LegacyOfflineActivity).also { mapview ->
        val mapboxMap = mapview.getMapboxMap()
        mapboxMap.jumpTo(CameraOptions.Builder().zoom(zoom).center(point).build())
        mapboxMap.loadStyleUri(styleUrl)
      }
      setContentView(mapView)

      mapView?.onStart()
    }
  }

  override fun onStart() {
    super.onStart()
    mapView?.onStart()
  }

  override fun onStop() {
    super.onStop()
    mapView?.onStop()
  }

  override fun onDestroy() {
    super.onDestroy()
    offlineRegion.invalidate { }
    mapView?.onDestroy()
  }

  companion object {
    private const val TAG = "Offline"
    private const val zoom = 16.0
    private val point: Point = Point.fromLngLat(57.818901, 20.071357)
    private const val styleUrl = Style.SATELLITE
  }
}
package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.testapp.databinding.ActivityLegacyOfflineBinding

/**
 * Example app that downloads an offline region and when succeeded
 * shows a button to load a map at the offline region definition.
 */
@Suppress("DEPRECATION")
class LegacyOfflineActivity : AppCompatActivity() {

  private lateinit var offlineManager: OfflineRegionManager
  private lateinit var offlineRegion: OfflineRegion
  private var mapView: MapView? = null
  private lateinit var binding: ActivityLegacyOfflineBinding

  private val regionObserver: OfflineRegionObserver = object : OfflineRegionObserver {
    override fun errorOccurred(error: OfflineRegionError) {
      if (error.isFatal) {
        logE(TAG, "Fatal error: ${error.type}, ${error.message}")
      } else {
        logW(TAG, "Error downloading some resources:  ${error.type}, ${error.message}")
      }
      offlineRegion.setOfflineRegionDownloadState(OfflineRegionDownloadState.INACTIVE)
    }

    override fun statusChanged(status: OfflineRegionStatus) {
      logD(
        TAG,
        "${status.completedResourceCount}/${status.requiredResourceCount} resources; ${status.completedResourceSize} bytes downloaded."
      )
      if (status.downloadState == OfflineRegionDownloadState.INACTIVE) {
        downloadComplete()
        return
      }
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
      logE(TAG, expected.error!!)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLegacyOfflineBinding.inflate(layoutInflater)
    setContentView(binding.root)
    offlineManager = OfflineRegionManager()
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
    binding.downloadProgress.visibility = View.GONE
    binding.showMapButton.visibility = View.VISIBLE
    binding.showMapButton.setOnClickListener {
      it.visibility = View.GONE
      // create mapView
      mapView = MapView(this@LegacyOfflineActivity).also { mapview ->
        val mapboxMap = mapview.getMapboxMap()
        mapboxMap.setCamera(CameraOptions.Builder().zoom(zoom).center(point).build())
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
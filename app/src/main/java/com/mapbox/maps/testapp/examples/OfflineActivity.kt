package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.common.*
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.Event
import com.mapbox.maps.extension.observable.subscribeResourceRequest
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_offline.*

/**
 * Example app that downloads an offline region and when succeeded
 * shows a button to load a map at the offline region definition.
 */
class OfflineActivity : AppCompatActivity() {

  private lateinit var offlineManager: OfflineManager
  private var mapView: MapView? = null
  private lateinit var handler: Handler
  private lateinit var tileStorePath: String
  private val tileStore = TileStore.getInstance()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_offline)
    handler = Handler()
    tileStorePath = this.filesDir.absolutePath
    offlineManager = OfflineManager(MapboxOptions.getDefaultResourceOptions((this)))

    // 1. Create style package with loadStylePack() call.
    offlineManager.loadStylePack(
      Style.MAPBOX_STREETS,
      StylePackLoadOptions.Builder().build()
//      { progress -> Logger.d(TAG, "StylePackLoadProgress: $progress") }
    ) { expected ->
      if (expected.isValue) {
        expected.value?.let { stylePack ->
          val loadingCompleted = stylePack.completedResourceCount == stylePack.requiredResourceCount
          Logger.d(TAG, "StylePack loadingCompleted: $loadingCompleted")
        }
      } else {
        Logger.e(TAG, expected.error!!.toString())
      }
    }

    // 2. Create an offline region with tiles for Streets and Satellite styles.
    val streetsTilesetOptions = TilesetDescriptorOptions.Builder()
      .styleURL(Style.MAPBOX_STREETS)
      .minZoom(0)
      .maxZoom(20)
      .stylePackOptions(
        StylePackLoadOptions.Builder()
          .glyphsRasterizationMode(GlyphsRasterizationMode.ALL_GLYPHS_RASTERIZED_LOCALLY).build()
      )
      .build()
    // Resolving of this tileset descriptor WILL implicitly create a style package
    // (and it won't contain any fonts).
    val streetsDescriptor = offlineManager.createTilesetDescriptor(streetsTilesetOptions)

    val satelliteTilesetOptions = TilesetDescriptorOptions.Builder()
      .styleURL(Style.SATELLITE)
      .minZoom(0)
      .maxZoom(5)
      .build()

    // Resolving of this tileset descriptor WILL NOT create a style package.
    val satelliteDescriptor = offlineManager.createTilesetDescriptor(satelliteTilesetOptions)

    val offlineRegionLoadOptions = TileRegionLoadOptions.Builder()
      .geometry(point)
      .descriptors(listOf(streetsDescriptor))
      .tileLoadOptions(
        TileLoadOptions.Builder()
          .criticalPriority(true)
          .acceptExpired(true)
          .networkRestriction(NetworkRestriction.NONE)
          .build()
      )
      .build()

    tileStore.loadTileRegion(
      OFFLINE_REGION_ID,
      offlineRegionLoadOptions,
      { progress -> Logger.e(TAG, "OfflineRegionLoadProgressCallback: $progress") }) { expected ->
      if (expected.isValue) {
        expected.value?.let { region ->
          Logger.e(TAG, "OfflineRegionCallback: $region")
          downloadComplete()
        }
      }
      expected.error?.let {
        Logger.e(TAG, "OfflineRegionCallback error: $it")
      }
    }
  }

  private fun downloadComplete() {
    handler.post {
      download_progress.visibility = View.GONE
      show_map_button.visibility = View.VISIBLE
      show_map_button.setOnClickListener {
        it.visibility = View.GONE
        // create mapView
        mapView = MapView(this@OfflineActivity).also { mapview ->
          val mapboxMap = mapview.getMapboxMap()
          mapboxMap.subscribeResourceRequest(object : Observer() {
            override fun notify(event: Event) {
              Logger.e(TAG, "event: $event")
            }
          })
          mapboxMap.setCamera(CameraOptions.Builder().zoom(zoom).center(point).build())
          mapboxMap.loadStyleUri(styleUrl)
        }
        setContentView(mapView)

        mapView?.onStart()
      }
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
    tileStore.removeTileRegion(OFFLINE_REGION_ID)
    mapView?.onDestroy()
  }

  companion object {
    private const val TAG = "Offline"
    private const val zoom = 12.0
    private val point: Point = Point.fromLngLat(139.769305, 35.682027)
    private const val styleUrl = Style.MAPBOX_STREETS
    private const val OFFLINE_REGION_ID = "myOfflineRegion"
  }
}
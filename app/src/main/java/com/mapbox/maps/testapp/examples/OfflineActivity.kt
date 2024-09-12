package com.mapbox.maps.testapp.examples

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mapbox.bindgen.Value
import com.mapbox.common.Cancelable
import com.mapbox.common.MapboxOptions
import com.mapbox.common.NetworkRestriction
import com.mapbox.common.OfflineSwitch
import com.mapbox.common.TileRegionLoadOptions
import com.mapbox.common.TileStore
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.GlyphsRasterizationMode
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.OfflineManager
import com.mapbox.maps.Style
import com.mapbox.maps.StylePackLoadOptions
import com.mapbox.maps.TilesetDescriptorOptions
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.mapsOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createCircleAnnotationManager
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityOfflineBinding
import kotlinx.coroutines.launch

/**
 * Example app that shows how to use OfflineManager and TileStore to
 * download regions for offline use.
 *
 * Please refer to our [offline guide](https://docs.mapbox.com/android/maps/guides/offline/#limits) for the limitations of the offline usage.
 */
class OfflineActivity : AppCompatActivity() {
  // We use the default tile store
  private val tileStore: TileStore = MapboxOptions.mapsOptions.tileStore!!
  private val offlineManager: OfflineManager = OfflineManager()
  private val offlineLogsAdapter: OfflineLogsAdapter = OfflineLogsAdapter()
  private lateinit var binding: ActivityOfflineBinding
  private val cancelables = mutableListOf<Cancelable>()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityOfflineBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // Initialize a logger that writes into the recycler view
    binding.recycler.layoutManager = LinearLayoutManager(this)
    binding.recycler.adapter = offlineLogsAdapter

    prepareDownloadButton()
  }

  private fun prepareDownloadButton() {
    updateButton("DOWNLOAD") {
      downloadOfflineRegion()
    }
  }

  private fun prepareCancelButton() {
    updateButton("CANCEL DOWNLOAD") {
      cancelables.forEach { it.cancel() }
      cancelables.clear()
      prepareDownloadButton()
    }
  }

  private fun prepareViewMapButton() {
    // Disable network stack, so that the map can only load from downloaded region.
    OfflineSwitch.getInstance().isMapboxStackConnected = false
    logInfoMessage("Mapbox network stack disabled.")
    lifecycleScope.launch {
      updateButton("VIEW SATELLITE STREET MAP") {
        val context = this@OfflineActivity
        // create a Mapbox MapView
        // Note that the MapView will use the current tile store set in MapboxOptions.mapsOptions.tileStore
        // It must be the same TileStore that is used to create the tile regions. (i.e. the
        // tileStorePath must be consistent).
        val mapView = MapView(context, MapInitOptions(context, styleUri = Style.SATELLITE_STREETS))
        binding.container.addView(mapView)

        mapView.mapboxMap.setCamera(CameraOptions.Builder().zoom(ZOOM).center(TOKYO).build())
        // Add a circle annotation to the offline geometry.
        mapView.annotations.createCircleAnnotationManager().create(
          CircleAnnotationOptions()
            .withPoint(TOKYO)
            .withCircleColor(Color.RED)
        )
        prepareViewStandardMapButton(mapView)
      }
    }
  }

  private fun prepareViewStandardMapButton(mapView: MapView) {
    lifecycleScope.launch {
      updateButton("VIEW STANDARD MAP") {
        // Load standard style and animate camera to show 3D buildings.
        mapView.mapboxMap.loadStyle(Style.STANDARD)
        mapView.mapboxMap.flyTo(
          cameraOptions {
            center(
              Point.fromLngLat(
                139.76567069012344,
                35.68134814430844
              )
            )
            zoom(15.0)
            bearing(356.1)
            pitch(59.8)
          },
          mapAnimationOptions { duration(1000L) }
        )
        prepareShowDownloadedRegionButton()
      }
    }
  }

  private fun prepareShowDownloadedRegionButton() {
    updateButton("SHOW DOWNLOADED REGIONS") {
      showDownloadedRegions()
      prepareRemoveOfflineRegionButton()
    }
  }

  private fun prepareRemoveOfflineRegionButton() {
    updateButton("REMOVE DOWNLOADED REGIONS") {
      removeOfflineRegions()
      showDownloadedRegions()
      binding.container.removeAllViews()

      // Re-enable the Mapbox network stack, so that the new offline region download can succeed.
      OfflineSwitch.getInstance().isMapboxStackConnected = true
      logInfoMessage("Mapbox network stack enabled.")

      prepareDownloadButton()
    }
  }

  private fun updateButton(text: String, listener: View.OnClickListener) {
    binding.button.text = text
    binding.button.setOnClickListener(listener)
  }

  private fun downloadOfflineRegion() {
    // 1. Create style package with loadStylePack() call.

    // A style pack (a Style offline package) contains the loaded style and its resources: loaded
    // sources, fonts, sprites. Style packs are identified with their style URI.

    // Style packs are stored in the disk cache database, but their resources are not subject to
    // the data eviction algorithm and are not considered when calculating the disk cache size.
    cancelables.add(
      offlineManager.loadStylePack(
        Style.SATELLITE_STREETS,
        // Build Style pack load options
        StylePackLoadOptions.Builder()
          .glyphsRasterizationMode(GlyphsRasterizationMode.IDEOGRAPHS_RASTERIZED_LOCALLY)
          .metadata(Value(STYLE_PACK_SATELLITE_STREET_METADATA))
          .build(),
        { progress ->
          // Update the download progress to UI
          updateSatelliteStreetStylePackDownloadProgress(
            progress.completedResourceCount,
            progress.requiredResourceCount,
            "StylePackLoadProgress: $progress"
          )
        },
        { expected ->
          expected.value?.let { stylePack ->
            // Style pack download finishes successfully
            logSuccessMessage("StylePack downloaded: $stylePack")
            if (allResourcesDownloadLoaded()) {
              prepareViewMapButton()
            } else {
              logInfoMessage("Waiting for tile region download to be finished.")
            }
          }
          expected.error?.let {
            // Handle error occurred during the style pack download.
            logErrorMessage("StylePackError: $it")
          }
        }
      )
    )

    // Download standard style pack
    cancelables.add(
      offlineManager.loadStylePack(
        Style.STANDARD,
        // Build Style pack load options
        StylePackLoadOptions.Builder()
          .glyphsRasterizationMode(GlyphsRasterizationMode.IDEOGRAPHS_RASTERIZED_LOCALLY)
          .metadata(Value(STYLE_PACK_STANDARD_METADATA))
          .build(),
        { progress ->
          // Update the download progress to UI
          updateStandardStylePackDownloadProgress(
            progress.completedResourceCount,
            progress.requiredResourceCount,
            "StylePackStandardLoadProgress: $progress"
          )
        },
        { expected ->
          expected.value?.let { stylePack ->
            // Style pack download finishes successfully
            logSuccessMessage("StylePack downloaded: $stylePack")
            if (allResourcesDownloadLoaded()) {
              prepareViewMapButton()
            } else {
              logInfoMessage("Waiting for tile region download to be finished.")
            }
          }
          expected.error?.let {
            // Handle error occurred during the style pack download.
            logErrorMessage("StylePackError: $it")
          }
        }
      )
    )

    // 2. Create a tile region with tiles for the satellite street style

    // A Tile Region represents an identifiable geographic tile region with metadata, consisting of
    // a set of tiles packs that cover a given area (a polygon). Tile Regions allow caching tiles
    // packs in an explicit way: By creating a Tile Region, developers can ensure that all tiles in
    // that region will be downloaded and remain cached until explicitly deleted.

    // Creating a Tile Region requires supplying a description of the area geometry, the tilesets
    // and zoom ranges of the tiles within the region.

    // The tileset descriptor encapsulates the tile-specific data, such as which tilesets, zoom ranges,
    // pixel ratio etc. the cached tile packs should have. It is passed to the Tile Store along with
    // the region area geometry to load a new Tile Region.

    // The OfflineManager is responsible for creating tileset descriptors for the given style and zoom range.
    val tilesetDescriptors = listOf(
      offlineManager.createTilesetDescriptor(
        TilesetDescriptorOptions.Builder()
          .styleURI(Style.SATELLITE_STREETS)
          .pixelRatio(resources.displayMetrics.density)
          .minZoom(0)
          .maxZoom(16)
          .build()
      ),
      offlineManager.createTilesetDescriptor(
        TilesetDescriptorOptions.Builder()
          .styleURI(Style.STANDARD)
          .pixelRatio(resources.displayMetrics.density)
          .minZoom(0)
          .maxZoom(16)
          .build()
      )
    )

    // Use the the default TileStore to load this region. You can create custom TileStores that are
    // unique for a particular file path, i.e. there is only ever one TileStore per unique path.

    // Note that the TileStore path must be the same with the TileStore used when initialise the MapView.
    cancelables.add(
      tileStore.loadTileRegion(
        TILE_REGION_ID,
        TileRegionLoadOptions.Builder()
          .geometry(TOKYO)
          .descriptors(tilesetDescriptors)
          .metadata(Value(TILE_REGION_METADATA))
          .acceptExpired(true)
          .networkRestriction(NetworkRestriction.NONE)
          .build(),
        { progress ->
          updateTileRegionDownloadProgress(
            progress.completedResourceCount,
            progress.requiredResourceCount,
            "TileRegionLoadProgress: $progress"
          )
        }
      ) { expected ->
        // Tile pack download finishes successfully
        expected.value?.let { region ->
          logSuccessMessage("TileRegion downloaded: $region")
          if (allResourcesDownloadLoaded()) {
            prepareViewMapButton()
          } else {
            logInfoMessage("Waiting for style pack download to be finished.")
          }
        }
        expected.error?.let {
          // Handle error occurred during the tile region download.
          logErrorMessage("TileRegionError: $it")
        }
      }
    )
    prepareCancelButton()
  }

  private fun allResourcesDownloadLoaded(): Boolean = binding.satelliteStreetsStylePackDownloadProgress.max > 0 &&
    binding.standardStylePackDownloadProgress.max > 0 &&
    binding.tilePackDownloadProgress.max > 0 &&
    binding.satelliteStreetsStylePackDownloadProgress.progress == binding.satelliteStreetsStylePackDownloadProgress.max &&
    binding.standardStylePackDownloadProgress.progress == binding.standardStylePackDownloadProgress.max &&
    binding.tilePackDownloadProgress.progress == binding.tilePackDownloadProgress.max

  private fun showDownloadedRegions() {
    // Get a list of tile regions that are currently available.
    tileStore.getAllTileRegions { expected ->
      expected.value?.let { tileRegionList ->
        logInfoMessage("Existing tile regions: $tileRegionList")
      }
      expected.error?.let { tileRegionError ->
        logErrorMessage("TileRegionError: $tileRegionError")
      }
    }
    // Get a list of style packs that are currently available.
    offlineManager.getAllStylePacks { expected ->
      expected.value?.let { stylePackList ->
        logInfoMessage("Existing style packs: $stylePackList")
      }
      expected.error?.let { stylePackError ->
        logErrorMessage("StylePackError: $stylePackError")
      }
    }
  }

  private fun removeOfflineRegions() {
    // Remove the tile region with the tile region ID.
    // Note this will not remove the downloaded tile packs, instead, it will just mark the tileset
    // not a part of a tile region. The tiles still exists as a predictive cache in TileStore.
    tileStore.removeTileRegion(TILE_REGION_ID)

    // Remove the style pack with the style url.
    // Note this will not remove the downloaded style pack, instead, it will just mark the resources
    // not a part of the existing style pack. The resources still exists as disk cache.
    offlineManager.removeStylePack(Style.SATELLITE_STREETS)
    offlineManager.removeStylePack(Style.STANDARD)

    MapboxMap.clearData {
      it.error?.let { error ->
        logErrorMessage(error)
      }
    }

    // Explicitly clear ambient cache data (so that if we try to download tile store regions again - it would actually truly download it from network).
    // Ambient cache data is anything not associated with an offline region or a style pack, including predictively cached data.
    // Note that it is advisable to rely on internal TileStore implementation to clear cache when needed.
    tileStore.clearAmbientCache {
      it.error?.let { error ->
        logErrorMessage(error.message)
      }
    }

    // Reset progressbar.
    updateSatelliteStreetStylePackDownloadProgress(0, 0)
    updateStandardStylePackDownloadProgress(0, 0)
    updateTileRegionDownloadProgress(0, 0)
  }

  private fun updateSatelliteStreetStylePackDownloadProgress(progress: Long, max: Long, message: String? = null) {
    binding.satelliteStreetsStylePackDownloadProgress.max = max.toInt()
    binding.satelliteStreetsStylePackDownloadProgress.progress = progress.toInt()
    message?.let {
      offlineLogsAdapter.addLog(OfflineLog.StylePackProgress(it))
    }
  }

  private fun updateStandardStylePackDownloadProgress(progress: Long, max: Long, message: String? = null) {
    binding.standardStylePackDownloadProgress.max = max.toInt()
    binding.standardStylePackDownloadProgress.progress = progress.toInt()
    message?.let {
      offlineLogsAdapter.addLog(OfflineLog.StylePackProgress(it))
    }
  }

  private fun updateTileRegionDownloadProgress(progress: Long, max: Long, message: String? = null) {
    binding.tilePackDownloadProgress.max = max.toInt()
    binding.tilePackDownloadProgress.progress = progress.toInt()
    message?.let {
      offlineLogsAdapter.addLog(OfflineLog.TilePackProgress(it))
    }
  }

  private fun logInfoMessage(message: String) {
    offlineLogsAdapter.addLog(OfflineLog.Info(message))
  }

  private fun logErrorMessage(message: String) {
    offlineLogsAdapter.addLog(OfflineLog.Error(message))
  }

  private fun logSuccessMessage(message: String) {
    offlineLogsAdapter.addLog(OfflineLog.Success(message))
  }

  override fun onDestroy() {
    super.onDestroy()
    // Cancel the current downloading jobs
    cancelables.forEach { it.cancel() }
    cancelables.clear()
    // Remove downloaded style packs and tile regions.
    removeOfflineRegions()
    // Bring back the network connectivity when exiting the OfflineActivity.
    OfflineSwitch.getInstance().isMapboxStackConnected = true
  }

  private class OfflineLogsAdapter : RecyclerView.Adapter<OfflineLogsAdapter.ViewHolder>() {
    private var isUpdating: Boolean = false
    private val updateHandler = Handler(Looper.getMainLooper())
    private val logs = ArrayList<OfflineLog>()

    @SuppressLint("NotifyDataSetChanged")
    private val updateRunnable = Runnable {
      notifyDataSetChanged()
      isUpdating = false
    }

    class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
      internal var alertMessageTv: TextView = view.findViewById(R.id.alert_message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view =
        LayoutInflater.from(parent.context).inflate(R.layout.item_gesture_alert, parent, false)
      return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val alert = logs[position]
      holder.alertMessageTv.text = alert.message
      holder.alertMessageTv.setTextColor(
        ContextCompat.getColor(holder.alertMessageTv.context, alert.color)
      )
    }

    override fun getItemCount(): Int {
      return logs.size
    }

    fun addLog(alert: OfflineLog) {
      when (alert) {
        is OfflineLog.Error -> Log.e(TAG, alert.message)
        else -> Log.d(TAG, alert.message)
      }
      logs.add(0, alert)
      if (!isUpdating) {
        isUpdating = true
        updateHandler.postDelayed(updateRunnable, 250)
      }
    }
  }

  private sealed class OfflineLog(val message: String, val color: Int) {
    class Info(message: String) : OfflineLog(message, android.R.color.black)
    class Error(message: String) : OfflineLog(message, android.R.color.holo_red_dark)
    class Success(message: String) : OfflineLog(message, android.R.color.holo_green_dark)
    class TilePackProgress(message: String) : OfflineLog(message, android.R.color.holo_purple)
    class StylePackProgress(message: String) : OfflineLog(message, android.R.color.holo_orange_dark)
  }

  companion object {
    private const val TAG = "OfflineActivity"
    private const val ZOOM = 12.0
    private val TOKYO = Point.fromLngLat(139.769305, 35.682027)
    private const val TILE_REGION_ID = "myTileRegion"
    private const val STYLE_PACK_SATELLITE_STREET_METADATA = "my-satellite-street-style-pack"
    private const val STYLE_PACK_STANDARD_METADATA = "my-standard-style-pack"
    private const val TILE_REGION_METADATA = "my-offline-region"
  }
}
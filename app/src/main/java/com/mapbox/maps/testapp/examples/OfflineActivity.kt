package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mapbox.bindgen.Value
import com.mapbox.common.*
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_gestures.*
import kotlinx.android.synthetic.main.activity_offline.*
import kotlinx.android.synthetic.main.activity_offline.recycler
import java.util.ArrayList

/**
 * Example app that downloads an offline region and when succeeded
 * shows a button to load a map at the offline region definition.
 */
class OfflineActivity : AppCompatActivity() {

  private lateinit var offlineManager: OfflineManager
  private var mapView: MapView? = null
  private lateinit var handler: Handler
  private lateinit var offlineLogsAdapter: OfflineLogsAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_offline)
    handler = Handler()

    recycler.layoutManager = LinearLayoutManager(this)
    offlineLogsAdapter = OfflineLogsAdapter()
    recycler.adapter = offlineLogsAdapter

    prepareDownloadButton()
  }

  private fun prepareDownloadButton() {
    updateButton("DOWNLOAD") {
      downloadOfflineRegion()
    }
  }

  private fun prepareCancelButton(cancelables: List<Cancelable>) {
    updateButton("CANCEL DOWNLOAD") {
      cancelables.forEach {
        it.cancel()
      }
      prepareDownloadButton()
    }
  }

  private fun prepareViewMapButton() {
    // Disable network stack, so that the map can only load from downloaded region.
    NetworkConnectivity.getInstance().setMapboxStackConnected(false)
    logInfoMessage("Mapbox network stack disabled.")
    handler.post {
      updateButton("VIEW MAP") {
        // create mapView
        mapView = MapView(this@OfflineActivity).also { mapview ->
          val mapboxMap = mapview.getMapboxMap()
          mapboxMap.setCamera(CameraOptions.Builder().zoom(ZOOM).center(TOKYO).build())
          mapboxMap.loadStyleUri(Style.OUTDOORS)
        }
        container.addView(mapView)
        mapView?.onStart()
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
      container.removeAllViews()

      logInfoMessage("Mapbox network stack enabled.")
      // Re-enable the Mapbox network stack, so that the new offline region download can succeed.
      NetworkConnectivity.getInstance().setMapboxStackConnected(true)

      prepareDownloadButton()
    }
  }

  private fun updateButton(text: String, listener: View.OnClickListener) {
    button.text = text
    button.setOnClickListener(listener)
  }

  private fun downloadOfflineRegion() {
    offlineManager = OfflineManager(MapInitOptions.getDefaultResourceOptions(this))

    // 1. Create style package with loadStylePack() call.
    val stylePackCancelable = offlineManager.loadStylePack(
      Style.OUTDOORS,
      StylePackLoadOptions.Builder()
        .glyphsRasterizationMode(GlyphsRasterizationMode.IDEOGRAPHS_RASTERIZED_LOCALLY)
        .metadata(Value(STYLE_PACK_METADATA))
        .build(),
      { progress ->
        updateStylePackDownloadProgress(
          (progress.completedResourceCount * 100 / progress.requiredResourceCount).toInt(),
          "StylePackLoadProgress: $progress"
        )
      },
      { expected ->
        if (expected.isValue) {
          expected.value?.let { stylePack ->
            logSuccessMessage("StylePack downloaded: $stylePack")
            if (tile_pack_download_progress.progress == tile_pack_download_progress.max) {
              prepareViewMapButton()
            } else {
              logInfoMessage("Waiting for tile region download to be finished.")
            }
          }
        }
        expected.error?.let {
          logErrorMessage("StylePackError: $it")
        }
      }
    )

    // 2. Create an offline region with tiles for the outdoors style
    val outdoorTilesetOptions = TilesetDescriptorOptions.Builder()
      .styleURI(Style.OUTDOORS)
      .minZoom(0)
      .maxZoom(16)
      .build()
    // Resolving of this tileset descriptor WILL create a style package..
    val outdoorDescriptor = offlineManager.createTilesetDescriptor(outdoorTilesetOptions)

    val tileRegionLoadOptions = TileRegionLoadOptions.Builder()
      .geometry(TOKYO)
      .descriptors(listOf(outdoorDescriptor))
      .metadata(Value(TILE_REGION_METADATA))
      .tileLoadOptions(
        TileLoadOptions.Builder()
          .criticalPriority(false)
          .acceptExpired(true)
          .networkRestriction(NetworkRestriction.NONE)
          .build()
      )
      .build()

    val tilePackCancelable = TileStore.getInstance().loadTileRegion(
      TILE_REGION_ID,
      tileRegionLoadOptions,
      { progress ->
        updateTileRegionDownloadProgress(
          (progress.completedResourceCount * 100 / progress.requiredResourceCount).toInt(),
          "TileRegionLoadProgress: $progress"
        )
      }
    ) { expected ->
      if (expected.isValue) {
        expected.value?.let { region ->
          logSuccessMessage("TileRegion downloaded: $region")
          if (style_pack_download_progress.progress == style_pack_download_progress.max) {
            prepareViewMapButton()
          } else {
            logInfoMessage("Waiting for style pack download to be finished.")
          }
        }
      }
      expected.error?.let {
        logErrorMessage("TileRegionError: $it")
      }
    }
    prepareCancelButton(listOf(stylePackCancelable, tilePackCancelable))
  }

  private fun showDownloadedRegions() {
    TileStore.getInstance().getAllTileRegions { expected ->
      if (expected.isValue) {
        expected.value?.let { tileRegionList ->
          logInfoMessage("Existing tile regions: $tileRegionList")
        }
      }
      expected.error?.let { tileRegionError ->
        logErrorMessage("TileRegionError: $tileRegionError")
      }
    }
    offlineManager.getAllStylePacks { expected ->
      if (expected.isValue) {
        expected.value?.let { stylePackList ->
          logInfoMessage("Existing style packs: $stylePackList")
        }
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
    TileStore.getInstance().removeTileRegion(TILE_REGION_ID)

    // Set the disk quota to zero, so that tile regions are fully evicted
    // when removed. The TileStore is also used when `ResourceOptions.isLoadTilePacksFromNetwork`
    // is `true`, and also by the Navigation SDK.
    // This removes the tiles from the predictive cache.
    TileStore.getInstance().setOption(TileStoreOptions.DISK_QUOTA, Value(0))

    // Remove the style pack with the style url.
    // Note this will not remove the downloaded style pack, instead, it will just mark the resources
    // not a part of the existing style pack. The resources still exists as ambient cache.
    if (this::offlineManager.isInitialized) {
      offlineManager.removeStylePack(Style.OUTDOORS)
    }
    // Remove the existing style resources from ambient cache using cache manager.
    val cacheManager = CacheManager(
      MapInitOptions.getDefaultResourceOptions(this)
    )
    cacheManager.clearAmbientCache {}

    // Reset progressbar.
    updateStylePackDownloadProgress(0)
    updateTileRegionDownloadProgress(0)
  }

  private fun updateStylePackDownloadProgress(progress: Int, message: String? = null) {
    style_pack_download_progress.progress = progress
    message?.let {
      offlineLogsAdapter.addLog(OfflineLog.StylePackProgress(it))
    }
  }

  private fun updateTileRegionDownloadProgress(progress: Int, message: String? = null) {
    tile_pack_download_progress.progress = progress
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
    removeOfflineRegions()
    // Bring back the network connectivity when exiting the OfflineActivity.
    NetworkConnectivity.getInstance().setMapboxStackConnected(true)
    mapView?.onDestroy()
  }

  private class OfflineLogsAdapter : RecyclerView.Adapter<OfflineLogsAdapter.ViewHolder>() {
    private var isUpdating: Boolean = false
    private val updateHandler = Handler()
    private val logs = ArrayList<OfflineLog>()

    private val updateRunnable = Runnable {
      notifyDataSetChanged()
      isUpdating = false
    }

    class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
      internal var alertMessageTv: TextView = view.findViewById(R.id.alert_message)
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
      val view =
        LayoutInflater.from(parent.context).inflate(R.layout.item_gesture_alert, parent, false)
      return ViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
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
    private const val ZOOM = 12.0
    private val TOKYO: Point = Point.fromLngLat(139.769305, 35.682027)
    private const val TILE_REGION_ID = "myTileRegion"
    private const val STYLE_PACK_METADATA = "my-outdoor-style-pack"
    private const val TILE_REGION_METADATA = "my-outdoors-tile-region"
  }
}
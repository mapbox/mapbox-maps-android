package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.common.Logger
import com.mapbox.maps.*
import com.mapbox.maps.extension.observable.getResourceEventData
import com.mapbox.maps.extension.observable.subscribeResourceRequest
import com.mapbox.maps.extension.observable.unsubscribeResourceRequest
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.delegates.listeners.OnMapLoadErrorListener
import com.mapbox.maps.plugin.delegates.listeners.eventdata.MapLoadErrorType
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityDebugBinding

/**
 * Example of enabling and visualizing some debug information for a map.
 */
class DebugModeActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap
  private val debugOptions: MutableList<MapDebugOptions> = mutableListOf(
    MapDebugOptions.TILE_BORDERS,
    MapDebugOptions.PARSE_STATUS,
    MapDebugOptions.TIMESTAMPS,
    MapDebugOptions.COLLISION,
    MapDebugOptions.STENCIL_CLIP,
    MapDebugOptions.DEPTH_BUFFER
  )
  private val extensionObservable = object : Observer() {
    override fun notify(event: Event) {
      val data = event.getResourceEventData()
      Logger.i(
        TAG,
        "extensionObservable DataSource: ${data.dataSource}\nRequest: ${data.request}\nResponse: ${data.response}\nCancelled: ${data.cancelled}"
      )
    }
  }

  private val observable = object : Observer() {
    override fun notify(event: Event) {
      Logger.i(
        TAG,
        "Type: ${event.type}\nValue: ${event.data.contents}"
      )
    }
  }

  private lateinit var binding: ActivityDebugBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityDebugBinding.inflate(layoutInflater)
    setContentView(binding.root)

    mapboxMap = binding.mapView.getMapboxMap()
    mapboxMap.subscribe(observable, listOf("resource-request"))
    // Using the extension method
    mapboxMap.subscribeResourceRequest(extensionObservable)
    mapboxMap.loadStyleUri(Style.MAPBOX_STREETS)
    binding.mapView.compass.opacity = 0.5f
    mapboxMap.setDebug(debugOptions, true)
    registerListeners(mapboxMap)
  }

  private fun registerListeners(mapboxMap: MapboxMap) {
    mapboxMap.addOnStyleLoadedListener {
      Logger.i(TAG, "OnStyleLoadedListener")
    }
    mapboxMap.addOnStyleDataLoadedListener {
      Logger.i(TAG, "OnStyleDataLoadedListener: $it")
    }
    mapboxMap.addOnStyleImageMissingListener {
      Logger.i(TAG, "OnStyleImageMissingListener: $it")
    }
    mapboxMap.addOnStyleImageUnusedListener {
      Logger.i(TAG, "OnStyleImageUnusedListener: $it")
    }
    mapboxMap.addOnMapIdleListener {
      Logger.i(TAG, "OnMapIdleListener")
    }
    mapboxMap.addOnMapLoadErrorListener(object : OnMapLoadErrorListener {
      override fun onMapLoadError(mapLoadErrorType: MapLoadErrorType, message: String) {
        Logger.i(TAG, "OnMapLoadErrorListener: $mapLoadErrorType, $message")
      }
    })
    mapboxMap.addOnMapLoadedListener {
      Logger.i(TAG, "OnMapLoadedListener")
    }
    mapboxMap.addOnCameraChangeListener {
      Logger.i(TAG, "OnCameraChangeListener")
    }
    mapboxMap.addOnRenderFrameStartedListener {
      Logger.i(TAG, "OnRenderFrameStartedListener")
    }
    mapboxMap.addOnRenderFrameFinishedListener { renderMode, needsRepaint, placementChanged ->
      Logger.i(
        TAG,
        "OnRenderFrameFinishedListener: $renderMode, $needsRepaint, $placementChanged"
      )
    }
    mapboxMap.addOnSourceAddedListener {
      Logger.i(
        TAG,
        "OnSourceAddedListener: $it"
      )
    }
    mapboxMap.addOnSourceDataLoadedListener { id, type, loaded, tileID ->
      Logger.i(
        TAG,
        "OnSourceDataLoadedListener: $id, $type, $loaded, $tileID"
      )
    }
    mapboxMap.addOnSourceRemovedListener {
      Logger.i(
        TAG,
        "OnSourceRemovedListener: $it"
      )
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_debug_mode, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.menu_debug_mode_tile_borders -> {
        item.isChecked = toggleDebugOptions(MapDebugOptions.TILE_BORDERS)
      }
      R.id.menu_debug_mode_parse_status -> {
        item.isChecked = toggleDebugOptions(MapDebugOptions.PARSE_STATUS)
      }
      R.id.menu_debug_mode_timestamps -> {
        item.isChecked = toggleDebugOptions(MapDebugOptions.TIMESTAMPS)
      }
      R.id.menu_debug_mode_collision -> {
        item.isChecked = toggleDebugOptions(MapDebugOptions.COLLISION)
      }
      R.id.menu_debug_mode_overdraw -> {
        item.isChecked = toggleDebugOptions(MapDebugOptions.OVERDRAW)
      }
      R.id.menu_debug_mode_stencil_clip -> {
        item.isChecked = toggleDebugOptions(MapDebugOptions.STENCIL_CLIP)
      }
      R.id.menu_debug_mode_depth_buffer -> {
        item.isChecked = toggleDebugOptions(MapDebugOptions.DEPTH_BUFFER)
      }
    }
    return true
  }

  private fun toggleDebugOptions(option: MapDebugOptions): Boolean {
    return if (debugOptions.contains(option)) {
      mapboxMap.setDebug(debugOptions, false)
      debugOptions.remove(option)
      mapboxMap.setDebug(debugOptions, true)
      false
    } else {
      mapboxMap.setDebug(debugOptions, false)
      debugOptions.add(option)
      mapboxMap.setDebug(debugOptions, true)
      true
    }
  }

  override fun onStart() {
    super.onStart()
    binding.mapView.setOnFpsChangedListener {
      runOnUiThread {
        binding.fpsView.text = getString(R.string.fps, it.toInt().toString())
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    mapboxMap.unsubscribe(observable)
    mapboxMap.unsubscribeResourceRequest(extensionObservable)
  }

  companion object {
    const val TAG = "DebugModeActivity"
  }
}
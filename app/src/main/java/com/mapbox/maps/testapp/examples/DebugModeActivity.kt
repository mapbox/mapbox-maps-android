package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.*
import com.mapbox.maps.extension.observable.eventdata.MapLoadingErrorEventData
import com.mapbox.maps.extension.observable.getResourceEventData
import com.mapbox.maps.extension.observable.subscribeResourceRequest
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.delegates.listeners.OnMapLoadErrorListener
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
  private val extensionObservable = Observer { event ->
    val data = event.getResourceEventData()
    logI(
      TAG,
      "extensionObservable DataSource: ${data.dataSource}\nRequest: ${data.request}\nResponse: ${data.response}\nCancelled: ${data.cancelled}"
    )
  }

  private val observable = Observer { event ->
    logI(
      TAG,
      "Type: ${event.type}\nValue: ${event.data.contents}"
    )
  }

  private lateinit var binding: ActivityDebugBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityDebugBinding.inflate(layoutInflater)
    setContentView(binding.root)

    mapboxMap = binding.mapView.getMapboxMap()
    mapboxMap.subscribe(observable, listOf(MapEvents.RESOURCE_REQUEST))
    // Using the extension method
    mapboxMap.subscribeResourceRequest(extensionObservable)
    mapboxMap.loadStyleUri(Style.MAPBOX_STREETS)
    binding.mapView.compass.opacity = 0.5f
    mapboxMap.setDebug(debugOptions, true)
    registerListeners(mapboxMap)
  }

  private fun registerListeners(mapboxMap: MapboxMap) {
    mapboxMap.addOnStyleLoadedListener {
      logI(TAG, "OnStyleLoadedListener: $it")
    }
    mapboxMap.addOnStyleDataLoadedListener {
      logI(TAG, "OnStyleDataLoadedListener: $it")
    }
    mapboxMap.addOnStyleImageMissingListener {
      logI(TAG, "OnStyleImageMissingListener: $it")
    }
    mapboxMap.addOnStyleImageUnusedListener {
      logI(TAG, "OnStyleImageUnusedListener: $it")
    }
    mapboxMap.addOnMapIdleListener {
      logI(TAG, "OnMapIdleListener: $it")
    }
    mapboxMap.addOnMapLoadErrorListener(object : OnMapLoadErrorListener {
      override fun onMapLoadError(eventData: MapLoadingErrorEventData) {
        logI(TAG, "OnMapLoadErrorListener: $eventData")
      }
    })
    mapboxMap.addOnMapLoadedListener {
      logI(TAG, "OnMapLoadedListener: $it")
    }
    mapboxMap.addOnCameraChangeListener {
      logI(TAG, "OnCameraChangeListener: $it")
    }
    mapboxMap.addOnRenderFrameStartedListener {
      logI(TAG, "OnRenderFrameStartedListener: $it")
    }
    mapboxMap.addOnRenderFrameFinishedListener {
      logI(
        TAG,
        "OnRenderFrameFinishedListener: $it"
      )
    }
    mapboxMap.addOnSourceAddedListener {
      logI(
        TAG,
        "OnSourceAddedListener: $it"
      )
    }
    mapboxMap.addOnSourceDataLoadedListener {
      logI(
        TAG,
        "OnSourceDataLoadedListener: $it"
      )
    }
    mapboxMap.addOnSourceRemovedListener {
      logI(
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

  companion object {
    const val TAG = "DebugModeActivity"
  }
}
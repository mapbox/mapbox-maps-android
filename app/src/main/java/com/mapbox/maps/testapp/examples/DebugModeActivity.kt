package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.common.Cancelable
import com.mapbox.maps.*
import com.mapbox.maps.plugin.compass.compass
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
  private var resourceRequestCancelable: Cancelable? = null
  private var untypedEventCancelable: Cancelable? = null

  private lateinit var binding: ActivityDebugBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityDebugBinding.inflate(layoutInflater)
    setContentView(binding.root)

    mapboxMap = binding.mapView.getMapboxMap()

    mapboxMap.subscribeResourceRequest {
      logI(
        TAG,
        "extensionObservable DataSource: ${it.source}\nRequest: ${it.request}\nResponse: ${it.response}\nCancelled: ${it.cancelled}"
      )
    }

    mapboxMap.loadStyleUri(Style.MAPBOX_STREETS)
    binding.mapView.compass.opacity = 0.5f
    mapboxMap.setDebug(debugOptions, true)
    registerListeners(mapboxMap)
  }

  private fun registerListeners(mapboxMap: MapboxMap) {
    mapboxMap.addOnStyleLoadedListener {
      logI(TAG, "StyleLoadedCallback: $it")
    }
    mapboxMap.addOnStyleDataLoadedListener {
      logI(TAG, "StyleDataLoadedCallback: $it")
    }
    mapboxMap.addOnStyleImageMissingListener {
      logI(TAG, "StyleImageMissingCallback: $it")
    }
    mapboxMap.addOnStyleImageUnusedListener {
      logI(TAG, "StyleImageRemoveUnusedCallback: $it")
    }
    mapboxMap.addOnMapIdleListener {
      logI(TAG, "MapIdleCallback: $it")
    }
    mapboxMap.addOnMapLoadErrorListener { eventData ->
      logI(
        TAG,
        "MapLoadingErrorCallback: $eventData"
      )
    }
    mapboxMap.addOnMapLoadedListener {
      logI(TAG, "MapLoadedCallback: $it")
    }
    mapboxMap.addOnCameraChangeListener {
      logI(TAG, "CameraChangedCallback: $it")
    }
    mapboxMap.addOnRenderFrameStartedListener {
      logI(TAG, "RenderFrameStartedCallback: $it")
    }
    mapboxMap.addOnRenderFrameFinishedListener {
      logI(
        TAG,
        "RenderFrameFinishedCallback: $it"
      )
    }
    mapboxMap.addOnSourceAddedListener {
      logI(
        TAG,
        "SourceAddedCallback: $it"
      )
    }
    mapboxMap.addOnSourceDataLoadedListener {
      logI(
        TAG,
        "SourceDataLoadedCallback: $it"
      )
    }
    mapboxMap.addOnSourceRemovedListener {
      logI(
        TAG,
        "SourceRemovedCallback: $it"
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
      else -> {
        return super.onOptionsItemSelected(item)
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
        binding.fpsView.text = getString(R.string.fps, String.format("%.2f", it))
      }
    }
  }

  override fun onStop() {
    super.onStop()
    resourceRequestCancelable?.cancel()
    untypedEventCancelable?.cancel()
  }

  companion object {
    const val TAG = "DebugModeActivity"
  }
}
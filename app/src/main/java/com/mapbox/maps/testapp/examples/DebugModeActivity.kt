package com.mapbox.maps.testapp.examples

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.mapbox.common.Cancelable
import com.mapbox.maps.*
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityDebugBinding

/**
 * Example of enabling and visualizing some debug information for a map.
 */
@OptIn(MapboxExperimental::class)
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

    mapboxMap = binding.mapView.mapboxMap

    mapboxMap.subscribeResourceRequest {
      logI(
        TAG,
        "extensionObservable DataSource: ${it.source}\nRequest: ${it.request}\nResponse: ${it.response}\nCancelled: ${it.cancelled}"
      )
    }

    mapboxMap.loadStyle(Style.STANDARD)
    setupPerformanceStatisticsCollection()
    binding.mapView.compass.opacity = 0.5f
    mapboxMap.setDebug(debugOptions, true)
    registerListeners(mapboxMap)
  }

  private fun setupPerformanceStatisticsCollection() {
    binding.perfStatButton.setOnClickListener {
      when (binding.perfStatButton.text) {
        PERF_STAT_START_COLLECT_BUTTON -> {
          mapboxMap.startPerformanceStatisticsCollection(
            PerformanceStatisticsOptions.Builder()
              .samplerOptions(
                listOf(
                  PerformanceSamplerOptions.PER_FRAME_RENDERING_STATS,
                  PerformanceSamplerOptions.CUMULATIVE_RENDERING_STATS
                )
              )
              // we should be collecting the results every 5 seconds
              .samplingDurationMillis(5_000.0)
              .build()
          ) { performanceStatistics ->
            logI(TAG_PERFORMANCE_STATISTICS, "Collection duration:\n ${performanceStatistics.collectionDurationMillis}")
            logI(TAG_PERFORMANCE_STATISTICS, "Per frame performance statistics:\n ${performanceStatistics.perFrameStatistics}")
            logI(TAG_PERFORMANCE_STATISTICS, "Cumulative performance statistics:\n ${performanceStatistics.cumulativeStatistics}")
            logI(TAG_PERFORMANCE_STATISTICS, "Render duration statistics:\n ${performanceStatistics.mapRenderDurationStatistics}")
            val mostExpensiveLayerData = performanceStatistics.perFrameStatistics?.topRenderLayers?.firstOrNull()
            val snackBar = Snackbar.make(
              binding.mapView,
              "The most expensive layer to render is ${mostExpensiveLayerData?.name} " +
                "(time ${mostExpensiveLayerData?.durationMillis} ms)",
              Snackbar.LENGTH_LONG
            )
            snackBar.animationMode = Snackbar.ANIMATION_MODE_SLIDE
            snackBar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines = 5
            snackBar.show()
          }
          @SuppressLint("SetTextI18n")
          binding.perfStatButton.text = PERF_STAT_STOP_COLLECT_BUTTON
        }
        PERF_STAT_STOP_COLLECT_BUTTON -> {
          mapboxMap.stopPerformanceStatisticsCollection()
          @SuppressLint("SetTextI18n")
          binding.perfStatButton.text = PERF_STAT_START_COLLECT_BUTTON
        }
      }
    }
  }

  private fun registerListeners(mapboxMap: MapboxMap) {
    mapboxMap.subscribeStyleLoaded {
      logI(TAG, "StyleLoadedCallback: $it")
    }
    mapboxMap.subscribeStyleDataLoaded {
      logI(TAG, "StyleDataLoadedCallback: $it")
    }
    mapboxMap.subscribeStyleImageMissing {
      logI(TAG, "StyleImageMissingCallback: $it")
    }
    mapboxMap.subscribeStyleImageRemoveUnused {
      logI(TAG, "StyleImageRemoveUnusedCallback: $it")
    }
    mapboxMap.subscribeMapIdle {
      logI(TAG, "MapIdleCallback: $it")
    }
    mapboxMap.subscribeMapLoadingError {
      logE(TAG, "MapLoadingErrorCallback: $it")
    }
    mapboxMap.subscribeMapLoaded {
      logI(TAG, "MapLoadedCallback: $it")
    }
    mapboxMap.subscribeCameraChanged {
      logI(TAG, "CameraChangedCallback: $it")
    }
    mapboxMap.subscribeRenderFrameStarted {
      logI(TAG, "RenderFrameStartedCallback: $it")
    }
    mapboxMap.subscribeRenderFrameFinished {
      logI(TAG, "RenderFrameFinishedCallback: $it")
    }
    mapboxMap.subscribeSourceAdded {
      logI(
        TAG,
        "SourceAddedCallback: $it"
      )
    }
    mapboxMap.subscribeSourceDataLoaded {
      logI(
        TAG,
        "SourceDataLoadedCallback: $it"
      )
    }
    mapboxMap.subscribeSourceRemoved {
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

  private companion object {
    private const val TAG = "DebugModeActivity"
    private const val TAG_PERFORMANCE_STATISTICS = "PerformanceStatistics"
    private const val PERF_STAT_STOP_COLLECT_BUTTON = "Stop collecting"
    private const val PERF_STAT_START_COLLECT_BUTTON = "Collect Perf Stats"
  }
}
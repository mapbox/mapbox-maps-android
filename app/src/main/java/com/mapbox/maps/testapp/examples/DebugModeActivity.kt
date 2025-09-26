package com.mapbox.maps.testapp.examples

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.mapbox.common.Cancelable
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.PerformanceSamplerOptions
import com.mapbox.maps.PerformanceStatisticsOptions
import com.mapbox.maps.Style
import com.mapbox.maps.Vec2
import com.mapbox.maps.debugoptions.MapViewDebugOptions
import com.mapbox.maps.logE
import com.mapbox.maps.logI
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityDebugBinding

/**
 * Example of enabling and visualizing some debug information for a map.
 */
@OptIn(MapboxExperimental::class)
class DebugModeActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap
  private val debugOptions: MutableSet<MapViewDebugOptions> = mutableSetOf(
    MapViewDebugOptions.TILE_BORDERS,
    MapViewDebugOptions.PARSE_STATUS,
    MapViewDebugOptions.TIMESTAMPS,
    MapViewDebugOptions.COLLISION,
    MapViewDebugOptions.STENCIL_CLIP,
    MapViewDebugOptions.DEPTH_BUFFER,
    MapViewDebugOptions.CAMERA,
    MapViewDebugOptions.PADDING,
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
    setupScreenShapeButton()
    binding.mapView.compass.opacity = 0.5f
    binding.mapView.debugOptions = debugOptions
    registerListeners(mapboxMap)
  }
  private var overlayView: HexSpotlightView? = null

  private fun setupScreenShapeButton() {
    binding.screenShapeButton.setOnClickListener {
      when (binding.screenShapeButton.text) {
        SCREEN_SHAPE_RECT -> {
          binding.screenShapeButton.text = SCREEN_SHAPE_CUSTOM

          mapboxMap.setScreenCullingShape(emptyList())
          (overlayView?.parent as? ViewGroup)?.removeView(overlayView)
        }
        SCREEN_SHAPE_CUSTOM -> {
          val shape = listOf(
            Vec2(0.35, 0.34),
            Vec2(0.65, 0.34),
            Vec2(0.85, 0.50),
            Vec2(0.65, 0.66),
            Vec2(0.35, 0.66),
            Vec2(0.15, 0.50)
          )

          overlayView = HexSpotlightView(this, shape)
          addContentView(overlayView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
          mapboxMap.setScreenCullingShape(shape)
          binding.screenShapeButton.text = SCREEN_SHAPE_RECT
        }
      }
    }
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

  @OptIn(com.mapbox.annotation.MapboxExperimental::class)
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
    mapboxMap.subscribeCameraChangedCoalesced {
      logI(TAG, "CameraChangedCoalescedCallback: $it")
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
        item.isChecked = toggleDebugOptions(MapViewDebugOptions.TILE_BORDERS)
      }
      R.id.menu_debug_mode_parse_status -> {
        item.isChecked = toggleDebugOptions(MapViewDebugOptions.PARSE_STATUS)
      }
      R.id.menu_debug_mode_timestamps -> {
        item.isChecked = toggleDebugOptions(MapViewDebugOptions.TIMESTAMPS)
      }
      R.id.menu_debug_mode_collision -> {
        item.isChecked = toggleDebugOptions(MapViewDebugOptions.COLLISION)
      }
      R.id.menu_debug_mode_overdraw -> {
        item.isChecked = toggleDebugOptions(MapViewDebugOptions.OVERDRAW)
      }
      R.id.menu_debug_mode_stencil_clip -> {
        item.isChecked = toggleDebugOptions(MapViewDebugOptions.STENCIL_CLIP)
      }
      R.id.menu_debug_mode_depth_buffer -> {
        item.isChecked = toggleDebugOptions(MapViewDebugOptions.DEPTH_BUFFER)
      }
      R.id.menu_debug_mode_camera_overlay -> {
        item.isChecked = toggleDebugOptions(MapViewDebugOptions.CAMERA)
      }
      R.id.menu_debug_mode_padding_overlay -> {
        item.isChecked = toggleDebugOptions(MapViewDebugOptions.PADDING)
      }
      R.id.menu_debug_mode_info -> {
        showMapInfo()
      }
      else -> {
        return super.onOptionsItemSelected(item)
      }
    }
    return true
  }

  private fun showMapInfo() {
    // Get style information
    val styleInfo = extractStyleInfo()
    val styleMessage = """
Style URL: ${styleInfo.styleURL}
Modified: ${styleInfo.modifiedDate}
SDK Compatibility:
${styleInfo.sdkCompatibility}
    """.trimIndent()

    AlertDialog.Builder(this)
      .setTitle("Style Info")
      .setMessage(styleMessage)
      .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
      .show()
  }

  private fun extractStyleInfo(): StyleInfo {
    val style = mapboxMap.style
    val styleJSON = style?.styleJSON ?: return StyleInfo("Unknown", "Unknown", "Unknown")

    return runCatching {
      val parsedStyle = Gson().fromJson(styleJSON, StyleJson::class.java)

      val modifiedDate = parsedStyle.modified ?: "Unknown"

      val sdkCompatibility = parsedStyle.metadata?.compatibility?.let { compatibility ->
        val compatibilityParts = mutableListOf<String>()
        compatibility.ios?.let { compatibilityParts.add("iOS: $it") }
        compatibility.android?.let { compatibilityParts.add("Android: $it") }
        compatibility.js?.let { compatibilityParts.add("JS: $it") }

        if (compatibilityParts.isEmpty()) "Unknown" else compatibilityParts.joinToString("\n")
      } ?: "Unknown"

      val styleURL = parsedStyle.metadata?.origin?.takeIf { it.isNotBlank() }
        ?: style.styleURI ?: "Unknown"

      StyleInfo(modifiedDate, sdkCompatibility, styleURL)
    }.getOrElse { StyleInfo("Unknown", "Unknown", "Unknown") }
  }

  private fun toggleDebugOptions(option: MapViewDebugOptions): Boolean {
    if (debugOptions.add(option)) {
      binding.mapView.debugOptions = debugOptions
      return true
    }

    debugOptions.remove(option)
    binding.mapView.debugOptions = debugOptions
    return false
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
    private const val SCREEN_SHAPE_CUSTOM = "Hex screen"
    private const val SCREEN_SHAPE_RECT = "Rect screen"
  }
}

@SuppressLint("ViewConstructor")
class HexSpotlightView(context: Context, private val shape: List<Vec2>) : View(context) {

  private val overlayPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    color = Color.parseColor("#80000000") // dimmed background
  }

  private val clearPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
  }

  private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    color = Color.WHITE
    style = Paint.Style.STROKE
    strokeWidth = 4F * context.resources.displayMetrics.density
    strokeJoin = Paint.Join.ROUND
    pathEffect = CornerPathEffect(12F * context.resources.displayMetrics.density)
  }
  private var hexPath: Path = createPath(width.toFloat(), height.toFloat())

  private val glowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    color = Color.WHITE
    style = Paint.Style.STROKE
    strokeWidth = 4F * context.resources.displayMetrics.density
    maskFilter = BlurMaskFilter(32F, BlurMaskFilter.Blur.OUTER)
    strokeJoin = Paint.Join.ROUND
    pathEffect = CornerPathEffect(12F * context.resources.displayMetrics.density)
  }

  override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
    super.onLayout(changed, left, top, right, bottom)

    hexPath = createPath(width.toFloat(), height.toFloat())
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)

    val layer = canvas.saveLayer(null, null)

    // Draw full dim overlay
    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), overlayPaint)

    // Draw hexagon cutout
    canvas.drawPath(hexPath, clearPaint)
    canvas.drawPath(hexPath, glowPaint)
    canvas.drawPath(hexPath, glowPaint)
    canvas.drawPath(hexPath, strokePaint)

    canvas.restoreToCount(layer)
  }

  private fun createPath(w: Float, h: Float): Path {
    val path = Path()
    shape.forEachIndexed { index, pt ->
      val x = pt.x * w
      val y = pt.y * h
      if (index == 0) path.moveTo(x.toFloat(), y.toFloat()) else path.lineTo(x.toFloat(), y.toFloat())
    }
    path.close()
    return path
  }
}

data class StyleInfo(
  val modifiedDate: String,
  val sdkCompatibility: String,
  val styleURL: String
)

data class StyleJson(
  @SerializedName("modified")
  val modified: String?,

  @SerializedName("metadata")
  val metadata: Metadata?
)

data class Metadata(
  @SerializedName("mapbox:origin")
  val origin: String?,

  @SerializedName("mapbox:compatibility")
  val compatibility: Compatibility?
)

data class Compatibility(
  @SerializedName("ios")
  val ios: String?,

  @SerializedName("android")
  val android: String?,

  @SerializedName("js")
  val js: String?
)
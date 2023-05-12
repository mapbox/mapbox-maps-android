package com.mapbox.maps

import android.content.Context
import android.graphics.*
import android.text.Html
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.VisibleForTesting
import androidx.core.content.res.ResourcesCompat
import com.mapbox.annotation.module.MapboxModuleType
import com.mapbox.common.module.provider.MapboxModuleProvider
import com.mapbox.common.module.provider.ModuleProviderArgument
import com.mapbox.geojson.Point
import com.mapbox.maps.attribution.AttributionLayout
import com.mapbox.maps.attribution.AttributionMeasure
import com.mapbox.maps.attribution.AttributionParser
import com.mapbox.maps.extension.observable.getMapLoadingErrorEventData
import com.mapbox.maps.extension.observable.getStyleDataLoadedEventData
import com.mapbox.maps.extension.observable.getStyleImageMissingEventData
import com.mapbox.maps.extension.observable.model.StyleDataType
import com.mapbox.maps.module.MapTelemetry
import java.lang.ref.WeakReference
import kotlin.math.min

/**
 * [Snapshotter] is high-level component responsible for taking map snapshot with given [MapSnapshotOptions].
 */
open class Snapshotter {

  private val context: WeakReference<Context>
  private val coreSnapshotter: MapSnapshotter
  private val pixelRatio: Float
  private val mapSnapshotOptions: MapSnapshotOptions

  internal var snapshotCreatedCallback: SnapshotCreatedListener? = null
  internal var snapshotStyleCallback: SnapshotStyleListener? = null

  private val observer: Observer
  private val snapshotOverlayOptions: SnapshotOverlayOptions

  @JvmOverloads
  constructor(
    context: Context,
    options: MapSnapshotOptions,
    overlayOptions: SnapshotOverlayOptions = SnapshotOverlayOptions()
  ) {
    this.context = WeakReference(context)
    mapSnapshotOptions = options
    snapshotOverlayOptions = overlayOptions
    pixelRatio = context.resources.displayMetrics.density
    coreSnapshotter = MapSnapshotter(options)
    dispatchTelemetryTurnstileEvent(context, options)
    val weakSelf = WeakReference(this)
    observer = Observer { event ->
      weakSelf.get()?.apply {
        when (event.type) {
          MapEvents.MAP_LOADING_ERROR -> {
            snapshotStyleCallback?.onDidFailLoadingStyle(event.getMapLoadingErrorEventData().message)
            coreSnapshotter.unsubscribe(observer)
          }
          MapEvents.STYLE_DATA_LOADED -> if (event.getStyleDataLoadedEventData().type == StyleDataType.STYLE) {
            snapshotStyleCallback?.onDidFinishLoadingStyle(
              Style(
                coreSnapshotter,
                pixelRatio
              )
            )
          }
          MapEvents.STYLE_LOADED -> {
            snapshotStyleCallback?.onDidFullyLoadStyle(
              Style(
                coreSnapshotter,
                pixelRatio
              )
            )
            coreSnapshotter.unsubscribe(observer)
          }
          MapEvents.STYLE_IMAGE_MISSING -> {
            snapshotStyleCallback?.onStyleImageMissing(event.getStyleImageMissingEventData().id)
          }
          else -> Unit
        }
      }
    }
    subscribe(observer, STYLE_LOAD_EVENTS_LIST)
  }

  private fun dispatchTelemetryTurnstileEvent(
    context: Context,
    options: MapSnapshotOptions
  ) {
    MapboxModuleProvider.createModule<MapTelemetry>(MapboxModuleType.MapTelemetry) {
      arrayOf(
        ModuleProviderArgument(
          Context::class.java,
          context.applicationContext
        ),
        ModuleProviderArgument(String::class.java, options.resourceOptions.accessToken)
      )
    }.onAppUserTurnstileEvent()
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal constructor(
    context: WeakReference<Context>,
    options: MapSnapshotOptions,
    overlayOptions: SnapshotOverlayOptions,
    coreSnapshotter: MapSnapshotter,
    observer: Observer
  ) {
    this.context = context
    mapSnapshotOptions = options
    snapshotOverlayOptions = overlayOptions
    this.coreSnapshotter = coreSnapshotter
    this.observer = observer
    pixelRatio = 1f
  }

  /**
   * Set [SnapshotStyleListener] to listen to all events style related.
   */
  fun setStyleListener(listener: SnapshotStyleListener) {
    snapshotStyleCallback = listener
  }

  /**
   * Start taking a snapshot.
   *
   * @param callback instance of [SnapshotCreatedListener] that will be triggered
   *  when snapshot will be ready or will error out.
   */
  fun start(callback: SnapshotCreatedListener) {
    snapshotCreatedCallback = callback
    if (getStyleJson().isEmpty() && getStyleUri().isEmpty()) {
      throw IllegalStateException("It's required to call setUri or setJson to provide a style definition before calling start.")
    }

    coreSnapshotter.start { result ->
      if (result.isValue) {
        result.value?.let { coreMapSnapshot ->
          snapshotCreatedCallback?.onSnapshotResult(
            addOverlay(
              object : MapSnapshotResult() {

                private var cachedBitmap: Bitmap? = null

                override fun screenCoordinate(coordinate: Point): ScreenCoordinate {
                  return coreMapSnapshot.screenCoordinate(coordinate)
                }

                override fun coordinate(screenCoordinate: ScreenCoordinate): Point {
                  return coreMapSnapshot.coordinate(screenCoordinate)
                }

                override fun attributions(): List<String> {
                  return coreMapSnapshot.attributions()
                }

                override fun bitmap(): Bitmap {
                  cachedBitmap?.let {
                    return it
                  }
                  coreMapSnapshot.moveImage()!!.let { coreImage ->
                    return Bitmap.createBitmap(
                      coreImage.width,
                      coreImage.height,
                      Bitmap.Config.ARGB_8888
                    ).apply {
                      copyPixelsFromBuffer(coreImage.data.buffer)
                      cachedBitmap = this
                    }
                  }
                }
              }
            )
          )
        } ?: run {
          logE(TAG, result.error ?: "Snapshot is empty.")
          snapshotCreatedCallback?.onSnapshotResult(null)
        }
      } else {
        logE(TAG, result.error ?: "Undefined error happened.")
        snapshotCreatedCallback?.onSnapshotResult(null)
      }
    }
  }

  /**
   * Cancel taking snapshot if it was running.
   */
  fun cancel() {
    coreSnapshotter.cancel()
    snapshotCreatedCallback = null
  }

  /**
   * Destroy snapshotter.
   */
  fun destroy() {
    cancel()
    coreSnapshotter.unsubscribe(observer)
    snapshotStyleCallback = null
  }

  /**
   * Set the camera options of the snapshot.
   *
   * @param cameraOptions the camera options of the snapshot.
   */
  fun setCamera(cameraOptions: CameraOptions) {
    coreSnapshotter.setCamera(cameraOptions)
  }

  /**
   * Set the URI of the current Mapbox Style in use.
   *
   * @param styleUri string containing a Mapbox style URI.
   */
  fun setStyleUri(styleUri: String) {
    coreSnapshotter.styleURI = styleUri
  }

  /**
   * Returns the coordinate bounds for a given camera.
   *
   * Note that if the given camera shows the antimeridian, the returned wrapped bounds
   * might not represent the minimum bounding box.
   *
   * @return CoordinateBounds
   */
  fun coordinateBoundsForCamera(options: CameraOptions): CoordinateBounds {
    return coreSnapshotter.coordinateBoundsForCamera(options)
  }

  /**
   * Convenience method that returns the camera options object for given arguments
   *
   * @param coordinates The coordinates representing the bounds of the map
   * @param padding The edge padding of the map
   * @param bearing The bearing of the map
   * @param pitch The pitch of the map
   *
   * @return Returns the camera options object representing the provided params
   */
  fun cameraForCoordinates(
    coordinates: List<Point>,
    padding: EdgeInsets,
    bearing: Double,
    pitch: Double
  ): CameraOptions {
    return coreSnapshotter.cameraForCoordinates(coordinates, padding, bearing, pitch)
  }

  /**
   * Get the current camera state.
   *
   * @return [CameraState] object.
   */
  fun getCameraState(): CameraState {
    return coreSnapshotter.cameraState
  }

  /**
   * Sets the size of the snapshot
   *
   * @param size The new size of the snapshot in \link MapOptions#size platform pixels \endlink
   */
  fun setSize(size: Size) {
    coreSnapshotter.size = size
  }

  /**
   * Gets the size of the snapshot
   *
   * @return Snapshot size in \link MapOptions#size platform pixels \endlink
   */
  fun getSize(): Size {
    return coreSnapshotter.size
  }

  /**
   * Load the style from a provided JSON string.
   *
   * This method should be called on the same thread where @see Map object is initialized.
   *
   * @param styleJson A JSON string containing a serialized Mapbox Style.
   */
  fun setStyleJson(styleJson: String) {
    coreSnapshotter.styleJSON = styleJson
  }

  /**
   * Get the JSON serialization string of the current Mapbox Style in use.
   *
   * @return A JSON string containing a serialized Mapbox Style.
   */
  fun getStyleJson(): String {
    return coreSnapshotter.styleJSON
  }

  /**
   * Get the URI of the current Mapbox Style in use.
   *
   * @return A string containing a Mapbox style URI.
   */
  fun getStyleUri(): String {
    return coreSnapshotter.styleURI
  }

  /**
   * Subscribes an Observer to a provided list of event types.
   * Observable will hold a strong reference to an Observer instance, therefore,
   * in order to stop receiving notifications, caller must call unsubscribe with an
   * Observer instance used for an initial subscription.
   *
   * @param observer an Observer
   * @param events an array of event types to be subscribed to.
   */
  fun subscribe(observer: Observer, events: List<String>) {
    coreSnapshotter.subscribe(observer, events)
  }

  /**
   * Unsubscribes an Observer from a provided list of event types.
   *
   * @param observer an Observer
   * @param events an array of event types to be unsubscribed from.
   */
  fun unsubscribe(observer: Observer, events: List<String>) {
    coreSnapshotter.unsubscribe(observer, events)
  }

  /**
   * Unsubscribes an Observer from all events.
   *
   * @param observer an Observer
   */
  fun unsubscribe(observer: Observer) {
    coreSnapshotter.unsubscribe(observer)
  }

  /**
   * Clears temporary map data.
   *
   * Clears temporary map data from the data path defined in the given resource options.
   * Useful to reduce the disk usage or in case the disk cache contains invalid data.
   *
   * Note that calling this API will affect all maps that use the same data path and does not
   * affect persistent map data like offline style packages.
   *
   * @param callback Called once the request is complete or an error occurred.
   */
  fun clearData(callback: AsyncOperationResultCallback) {
    return Map.clearData(mapSnapshotOptions.resourceOptions, callback)
  }

  private fun drawAttribution(
    mapSnapshot: MapSnapshotResult,
    canvas: Canvas,
    measure: AttributionMeasure,
    layout: AttributionLayout
  ) {
    // draw attribution
    val anchorPoint = layout.anchorPoint
    if (anchorPoint != null) {
      drawAttribution(canvas, measure, anchorPoint)
    } else {
      val snapshot: Bitmap = mapSnapshot.bitmap()
      logE(
        TAG,
        "Could not generate attribution for snapshot size: ${snapshot.width}x${snapshot.height}." +
          " You are required to provide your own attribution for the used sources: ${mapSnapshot.attributions()}"
      )
    }
  }

  private fun drawAttribution(canvas: Canvas, measure: AttributionMeasure, anchorPoint: PointF) {
    canvas.save()
    canvas.translate(anchorPoint.x, anchorPoint.y)
    measure.getTextView().draw(canvas)
    canvas.restore()
  }

  private fun addOverlay(mapSnapshot: MapSnapshotResult): MapSnapshotResult {
    context.get()?.apply {
      val canvas = Canvas(mapSnapshot.bitmap())
      val margin: Int = this.resources.displayMetrics.density.toInt() * 4
      drawOverlay(mapSnapshot, mapSnapshot.bitmap(), canvas, margin)
    }
    return mapSnapshot
  }

  private fun drawOverlay(mapSnapshot: MapSnapshotResult, snapshot: Bitmap, canvas: Canvas, margin: Int) {
    context.get()?.let {
      val measure: AttributionMeasure = getAttributionMeasure(it, mapSnapshot, snapshot, margin)
      val layout = measure.measure()
      layout?.let {
        if (snapshotOverlayOptions.showLogo) {
          drawLogo(mapSnapshot, canvas, margin, layout)
        }
        if (snapshotOverlayOptions.showAttributes) {
          drawAttribution(mapSnapshot, canvas, measure, layout)
        }
      }
    }
  }

  private fun getAttributionMeasure(
    context: Context,
    mapSnapshot: MapSnapshotResult,
    snapshot: Bitmap,
    margin: Int
  ): AttributionMeasure {
    val logo: Logo = createScaledLogo(context, snapshot)
    return AttributionMeasure(
      snapshot = snapshot,
      logo = logo.large,
      logoSmall = logo.small,
      textView = createTextView(context, mapSnapshot, false, logo.scale),
      textViewShort = createTextView(context, mapSnapshot, true, logo.scale),
      margin = margin.toFloat()
    )
  }

  private fun createTextView(
    context: Context,
    mapSnapshot: MapSnapshotResult,
    shortText: Boolean,
    scale: Float
  ): TextView {
    val textColor: Int = ResourcesCompat.getColor(
      context.resources,
      R.color.mapbox_gray_dark,
      context.theme
    )
    val widthMeasureSpec: Int = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    val heightMeasureSpec: Int = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    val textView = TextView(context)
    textView.layoutParams = ViewGroup.LayoutParams(
      ViewGroup.LayoutParams.WRAP_CONTENT,
      ViewGroup.LayoutParams.WRAP_CONTENT
    )
    val attributionString = createAttributionString(mapSnapshot, shortText)
    if (attributionString.isNotEmpty()) {
      textView.isSingleLine = true
      textView.textSize = 10 * scale
      textView.setTextColor(textColor)
      textView.setBackgroundResource(R.drawable.mapbox_rounded_corner)
      @Suppress("DEPRECATION")
      textView.text = Html.fromHtml(attributionString)
      textView.measure(widthMeasureSpec, heightMeasureSpec)
      textView.layout(0, 0, textView.measuredWidth, textView.measuredHeight)
    }
    return textView
  }

  private fun createAttributionString(
    mapSnapshot: MapSnapshotResult,
    shortText: Boolean
  ): String {
    context.get()?.apply {
      val attributionParser: AttributionParser = AttributionParser.Options(this)
        .withAttributionData(*mapSnapshot.attributions().toTypedArray())
        .withCopyrightSign(false)
        .withImproveMap(false)
        .build()
      return attributionParser.createAttributionString(shortText)
    }
    return ""
  }

  private fun drawLogo(
    mapSnapshot: MapSnapshotResult,
    canvas: Canvas,
    margin: Int,
    layout: AttributionLayout
  ) {
    drawLogo(mapSnapshot.bitmap(), canvas, margin, layout)
  }

  private fun drawLogo(
    snapshot: Bitmap,
    canvas: Canvas,
    margin: Int,
    placement: AttributionLayout
  ) {
    placement.logo?.let {
      canvas.drawBitmap(
        it,
        margin.toFloat(),
        (snapshot.height - it.height - margin).toFloat(),
        null
      )
    }
  }

  private fun createScaledLogo(context: Context, snapshot: Bitmap): Logo {
    val logo = BitmapFactory.decodeResource(context.resources, R.drawable.mapbox_logo_icon, null)
    val scale = calculateLogoScale(context, snapshot, logo)
    val matrix = Matrix()
    matrix.postScale(scale, scale)
    val helmet =
      BitmapFactory.decodeResource(context.resources, R.drawable.mapbox_logo_helmet, null)
    val large = Bitmap.createBitmap(logo, 0, 0, logo.width, logo.height, matrix, true)
    val small = Bitmap.createBitmap(helmet, 0, 0, helmet.width, helmet.height, matrix, true)
    return Logo(large, small, scale)
  }

  private fun calculateLogoScale(context: Context, snapshot: Bitmap, logo: Bitmap): Float {
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    val widthRatio = displayMetrics.widthPixels / snapshot.width.toFloat()
    val heightRatio = displayMetrics.heightPixels / snapshot.height.toFloat()
    val prefWidth = logo.width / widthRatio
    val prefHeight = logo.height / heightRatio
    var calculatedScale = min(prefWidth / logo.width, prefHeight / logo.height) * 2
    if (calculatedScale > 1) {
      // don't allow over-scaling
      calculatedScale = 1.0f
    } else if (calculatedScale < 0.60f) {
      // don't scale to low either
      calculatedScale = 0.60f
    }
    return calculatedScale
  }

  private data class Logo constructor(
    val large: Bitmap,
    val small: Bitmap,
    val scale: Float
  )

  /**
   * Static variables and methods.
   */
  companion object {
    private const val TAG = "Snapshotter"

    private val STYLE_LOAD_EVENTS_LIST = listOf(
      MapEvents.MAP_LOADING_ERROR,
      MapEvents.STYLE_DATA_LOADED,
      MapEvents.STYLE_LOADED,
      MapEvents.STYLE_IMAGE_MISSING
    )
  }
}
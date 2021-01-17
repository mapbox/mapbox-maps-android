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
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.common.Logger
import com.mapbox.geojson.Point
import com.mapbox.maps.attribution.AttributionLayout
import com.mapbox.maps.attribution.AttributionMeasure
import com.mapbox.maps.attribution.AttributionParser
import java.lang.ref.WeakReference

/**
 * [Snapshotter] is high-level component responsible for taking map snapshot with given [MapSnapshotOptions].
 */
class Snapshotter : MapSnapshotterObserver {

  private val context: WeakReference<Context>
  private val coreSnapshotter: MapSnapshotterInterface
  private var snapshotReadyCallback: SnapshotReadyCallback? = null
  private val pixelRatio: Float

  constructor(context: Context, options: MapSnapshotOptions) {
    this.context = WeakReference(context)
    coreSnapshotter = MapSnapshotter(options, this)
    pixelRatio = context.resources.displayMetrics.density
  }

  @VisibleForTesting
  internal constructor(context: Context, snapshotter: MapSnapshotterInterface) {
    this.context = WeakReference(context)
    coreSnapshotter = snapshotter
    pixelRatio = context.resources.displayMetrics.density
  }

  /**
   * Notifies the client about style load errors
   *
   * @param message Error message associated with the error.
   */
  override fun onDidFailLoadingStyle(message: String) {
    Logger.e(TAG, message)
  }

  /**
   * Notifies the client when style loading finished.
   */
  override fun onDidFinishLoadingStyle() {
    snapshotReadyCallback?.onStyleLoaded(Style(coreSnapshotter, pixelRatio))
  }

  /**
   * Notifies the client about a missing style image.
   *
   * @param imageId The id of the image that is missing.
   */
  override fun onStyleImageMissing(imageId: String) {
    Logger.e(TAG, "Style image missing: $imageId")
  }

  /**
   * Start taking a snapshot.
   *
   * @param callback that will be triggered when snapshot will be ready.
   */
  fun start(callback: SnapshotReadyCallback) {
    snapshotReadyCallback = callback
    if (getJson().isEmpty() && getUri().isEmpty()) {
      throw IllegalStateException("It's required to call setUri or setJson to provide a style definition before calling start.")
    }

    coreSnapshotter.start { result ->
      lateinit var interceptedResult: Expected<MapSnapshotInterface?, String?>
      if (result.isValue) {
        result.value?.let {
          interceptedResult =
            ExpectedFactory.createValue(addOverlay(Snapshot(it)) as MapSnapshotInterface)
        } ?: run {
          interceptedResult = ExpectedFactory.createError("snapshot is empty")
        }
      } else {
        interceptedResult = ExpectedFactory.createError(result.error ?: "")
      }
      snapshotReadyCallback?.onSnapshotCreated(interceptedResult)
    }
  }

  /**
   * Cancel taking snapshot if it was running.
   */
  fun cancel() {
    coreSnapshotter.cancel()
    snapshotReadyCallback = null
  }

  /**
   * Set the camera options of the snapshot.
   *
   * @param cameraOptions the camera options of the snapshot.
   */
  fun setCameraOptions(cameraOptions: CameraOptions) {
    coreSnapshotter.cameraOptions = cameraOptions
  }

  /**
   * Set the URI of the current Mapbox Style in use.
   *
   * @param uri string containing a Mapbox style URI.
   */
  fun setUri(uri: String) {
    coreSnapshotter.styleURI = uri
  }

  /**
   * Sets the snapshotter to the tile mode.
   *
   * In the tile mode, the snapshotter fetches the still image of a single tile.
   *
   * @param set Bool representing if the snapshotter is in the tile mode.
   */
  fun setTileMode(set: Boolean) {
    coreSnapshotter.setTileMode(set)
  }

  /**
   * Get the previously set rectangular bounds.
   *
   * @return CoordinateBounds
   */
  fun getRegion(): CoordinateBounds {
    return coreSnapshotter.region
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
  fun cameraForCoordinates(coordinates: List<Point>, padding: EdgeInsets, bearing: Double, pitch: Double): CameraOptions {
    return coreSnapshotter.cameraForCoordinates(coordinates, padding, bearing, pitch)
  }

  /**
   *  Returns TRUE if the snapshotter is in the tile mode.
   */
  fun isInTileMode(): Boolean {
    return coreSnapshotter.isInTileMode
  }

  /**
   * Get the current camera options
   *
   * @return CameraOptions.
   */
  fun getCameraOptions(): CameraOptions {
    return coreSnapshotter.cameraOptions
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
   * @param json A JSON string containing a serialized Mapbox Style.
   */
  fun setJson(styleJson: String) {
    coreSnapshotter.styleJSON = styleJson
  }

  /**
   * Get the JSON serialization string of the current Mapbox Style in use.
   *
   * @return A JSON string containing a serialized Mapbox Style.
   */
  fun getJson(): String {
    return coreSnapshotter.styleJSON
  }

  /**
   * Get the URI of the current Mapbox Style in use.
   *
   * @return A string containing a Mapbox style URI.
   */
  fun getUri(): String {
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
  fun subscribe(observer: Observer, list: MutableList<String>) {
    coreSnapshotter.subscribe(observer, list)
  }

  /**
   * Unsubscribes an Observer from a provided list of event types.
   *
   * @param observer an Observer
   * @param events an array of event types to be unsubscribed from.
   */
  fun unsubscribe(observer: Observer, list: MutableList<String>) {
    coreSnapshotter.unsubscribe(observer, list)
  }

  /**
   * Unsubscribes an Observer from all events.
   *
   * @param observer an Observer
   */
  fun unsubscribe(observer: Observer) {
    coreSnapshotter.unsubscribe(observer)
  }

  private fun drawAttribution(
    mapSnapshot: Snapshot,
    canvas: Canvas,
    measure: AttributionMeasure,
    layout: AttributionLayout
  ) {
    // draw attribution
    val anchorPoint = layout.anchorPoint
    if (anchorPoint != null) {
      drawAttribution(canvas, measure, anchorPoint)
    } else {
      val snapshot: Bitmap = mapSnapshot.bitmap
      Logger.e(
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

  private fun addOverlay(mapSnapshot: Snapshot): Snapshot {
    context.get()?.apply {
      val canvas = Canvas(mapSnapshot.bitmap)
      val margin: Int = this.resources.displayMetrics.density.toInt() * 4
      drawOverlay(mapSnapshot, mapSnapshot.bitmap, canvas, margin)
    }
    return mapSnapshot
  }

  private fun drawOverlay(mapSnapshot: Snapshot, snapshot: Bitmap, canvas: Canvas, margin: Int) {
    context.get()?.let {
      val measure: AttributionMeasure = getAttributionMeasure(it, mapSnapshot, snapshot, margin)
      val layout = measure.measure()
      layout?.let {
        drawLogo(mapSnapshot, canvas, margin, layout)
        drawAttribution(mapSnapshot, canvas, measure, layout)
      }
    }
  }

  private fun getAttributionMeasure(
    context: Context,
    mapSnapshot: Snapshot,
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
    mapSnapshot: Snapshot,
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
      textView.setSingleLine(true)
      textView.textSize = 10 * scale
      textView.setTextColor(textColor)
      textView.setBackgroundResource(R.drawable.mapbox_rounded_corner)
      textView.text = Html.fromHtml(attributionString)
      textView.measure(widthMeasureSpec, heightMeasureSpec)
      textView.layout(0, 0, textView.measuredWidth, textView.measuredHeight)
    }
    return textView
  }

  private fun createAttributionString(
    mapSnapshot: Snapshot,
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
    mapSnapshot: Snapshot,
    canvas: Canvas,
    margin: Int,
    layout: AttributionLayout
  ) {
    drawLogo(mapSnapshot.bitmap, canvas, margin, layout)
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
    var calculatedScale = Math.min(prefWidth / logo.width, prefHeight / logo.height) * 2
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
   * Callback for getting snapshot when it's finished.
   */
  interface SnapshotReadyCallback {
    /**
     * @param mapStyleDelegate The style delegate
     */
    fun onStyleLoaded(style: Style)

    /**
     * @param snapshot An image snapshot of a map rendered by MapSnapshotter.
     */
    fun onSnapshotCreated(snapshot: Expected<MapSnapshotInterface?, String?>)
  }

  /**
   * Static variables and methods.
   */
  companion object {
    private const val TAG = "Snapshotter"
  }
}
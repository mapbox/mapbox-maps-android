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
import com.mapbox.common.Cancelable
import com.mapbox.common.MapboxOptions
import com.mapbox.common.module.provider.MapboxModuleProvider
import com.mapbox.common.module.provider.ModuleProviderArgument
import com.mapbox.geojson.Point
import com.mapbox.maps.attribution.AttributionLayout
import com.mapbox.maps.attribution.AttributionMeasure
import com.mapbox.maps.attribution.AttributionParser
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
  @get:JvmSynthetic @set:JvmSynthetic
  internal var snapshotStyleCallback: SnapshotStyleListener? = null
  private val snapshotOverlayOptions: SnapshotOverlayOptions
  private var cancelableEvents = mutableListOf<Cancelable>()
  private var loadingErrorCancelable: Cancelable? = null
  private var styleLoadedCancelable: Cancelable? = null

  @JvmOverloads
  constructor(
    context: Context,
    options: MapSnapshotOptions,
    overlayOptions: SnapshotOverlayOptions = SnapshotOverlayOptions()
  ) {
    if (MapboxOptions.accessToken.isBlank()) {
      throw MapboxConfigurationException()
    }

    this.context = WeakReference(context)
    mapSnapshotOptions = options
    snapshotOverlayOptions = overlayOptions
    pixelRatio = context.resources.displayMetrics.density
    coreSnapshotter = MapSnapshotter(options)
    dispatchTelemetryTurnstileEvent(context)
    val weakSelf = WeakReference(this)
    loadingErrorCancelable = subscribeMapLoadingError {
      weakSelf.get()?.apply {
        loadingErrorCancelable?.cancel()
        snapshotStyleCallback?.onDidFailLoadingStyle(it.message)
      }
    }
    cancelableEvents.add(
      subscribeStyleDataLoaded {
      if (it.type == StyleDataLoadedType.STYLE) {
        weakSelf.get()?.apply {
          snapshotStyleCallback?.onDidFinishLoadingStyle(
            Style(
              coreSnapshotter,
              pixelRatio
            )
          )
        }
      }
    }
    )
    styleLoadedCancelable = subscribeStyleLoaded {
      weakSelf.get()?.apply {
        snapshotStyleCallback?.onDidFullyLoadStyle(
          Style(
            coreSnapshotter,
            pixelRatio
          )
        )
        styleLoadedCancelable?.cancel()
      }
    }
    cancelableEvents.add(
      subscribeStyleImageMissing {
      weakSelf.get()?.apply {
        snapshotStyleCallback?.onStyleImageMissing(it.imageId)
      }
    }
    )
  }

  private fun dispatchTelemetryTurnstileEvent(
    context: Context
  ) {
    MapboxModuleProvider.createModule<MapTelemetry>(MapboxModuleType.MapTelemetry) {
      arrayOf(
        ModuleProviderArgument(
          Context::class.java,
          context.applicationContext
        ),
      )
    }.onAppUserTurnstileEvent()
  }

  private constructor(
    context: WeakReference<Context>,
    options: MapSnapshotOptions,
    overlayOptions: SnapshotOverlayOptions,
    coreSnapshotter: MapSnapshotter,
  ) {
    this.context = context
    mapSnapshotOptions = options
    snapshotOverlayOptions = overlayOptions
    this.coreSnapshotter = coreSnapshotter
    pixelRatio = 1f
  }

  /**
   * Set [SnapshotStyleListener] to listen to all events style related.
   */
  fun setStyleListener(listener: SnapshotStyleListener) {
    snapshotStyleCallback = listener
  }

  /**
   * Request a new snapshot.
   * If there is a running snapshot request, it is cancelled and [SnapshotResultCallback.onSnapshotResult] for it will be called with snapshot=NULL.
   *
   * @param overlayCallback optional callback that could be used to draw custom content directly over the snapshot image.
   * @param resultCallback callback triggered when snapshot was obtained or if an error occurred.
   */
  fun start(
    overlayCallback: SnapshotOverlayCallback? = null,
    resultCallback: SnapshotResultCallback,
  ) {
    if (getStyleJson().isEmpty() && getStyleUri().isEmpty()) {
      throw IllegalStateException("It's required to call setUri or setJson to provide a style definition before calling start.")
    }
    coreSnapshotter.start { result ->
      if (result.isValue) {
        result.value?.let { coreMapSnapshot ->
          val snapshotBitmap = coreMapSnapshot.moveImage()?.let { coreImage ->
            Bitmap.createBitmap(
              coreImage.width, coreImage.height, Bitmap.Config.ARGB_8888
            ).apply {
              copyPixelsFromBuffer(coreImage.data.buffer)
            }
          } ?: run {
            resultCallback.onSnapshotResult(
              snapshot = null,
              errorMessage = "Moving image data failed!"
            )
            return@let
          }
          // create canvas just once and use it for all the drawings
          val canvas = Canvas(snapshotBitmap)
          val snapshotOverlay = object : SnapshotOverlay() {

            override val canvas = canvas

            override val attributions = coreMapSnapshot.attributions()

            override fun screenCoordinate(coordinate: Point): ScreenCoordinate {
              return coreMapSnapshot.screenCoordinate(coordinate)
            }

            override fun coordinate(screenCoordinate: ScreenCoordinate): Point {
              return coreMapSnapshot.coordinate(screenCoordinate)
            }
          }
          // do the user drawing if needed
          overlayCallback?.onSnapshotOverlay(snapshotOverlay)
          // draw Mapbox attribution on top of user drawing
          addOverlay(snapshotBitmap, snapshotOverlay)
          resultCallback.onSnapshotResult(
            snapshot = snapshotBitmap,
            errorMessage = null,
          )
        } ?: run {
          resultCallback.onSnapshotResult(
            snapshot = null,
            errorMessage = "Snapshot was empty."
          )
        }
      } else {
        resultCallback.onSnapshotResult(
          snapshot = null,
          errorMessage = result.error ?: "Undefined error happened."
        )
      }
    }
  }

  /**
   * Cancel taking snapshot if it was running.
   */
  fun cancel() {
    coreSnapshotter.cancel()
  }

  /**
   * Destroy snapshotter.
   */
  fun destroy() {
    cancel()
    cancelableEvents.forEach(Cancelable::cancel)
    cancelableEvents.clear()
    loadingErrorCancelable?.cancel()
    styleLoadedCancelable?.cancel()
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

  private fun subscribeMapLoadingError(mapLoadingErrorCallback: MapLoadingErrorCallback): Cancelable {
    return coreSnapshotter.subscribe(mapLoadingErrorCallback)
  }

  private fun subscribeStyleLoaded(styleLoadedCallback: StyleLoadedCallback): Cancelable {
    return coreSnapshotter.subscribe(styleLoadedCallback)
  }

  private fun subscribeStyleDataLoaded(styleDataLoadedCallback: StyleDataLoadedCallback): Cancelable {
    return coreSnapshotter.subscribe(styleDataLoadedCallback)
  }

  private fun subscribeStyleImageMissing(styleImageMissingCallback: StyleImageMissingCallback): Cancelable {
    return coreSnapshotter.subscribe(styleImageMissingCallback)
  }

  private fun drawAttribution(
    overlay: SnapshotOverlay,
    measure: AttributionMeasure,
    layout: AttributionLayout
  ) {
    // draw attribution
    val anchorPoint = layout.anchorPoint
    if (anchorPoint != null) {
      drawAttribution(overlay.canvas, measure, anchorPoint)
    } else {
      logE(
        TAG,
        "Could not generate attribution for snapshot size: ${overlay.canvas.width}x${overlay.canvas.height}." + " You are required to provide your own attribution for the used sources: ${overlay.attributions}"
      )
    }
  }

  private fun drawAttribution(canvas: Canvas, measure: AttributionMeasure, anchorPoint: PointF) {
    canvas.save()
    canvas.translate(anchorPoint.x, anchorPoint.y)
    measure.getTextView().draw(canvas)
    canvas.restore()
  }

  private fun addOverlay(
    snapshotBitmap: Bitmap,
    overlay: SnapshotOverlay
  ) {
    context.get()?.apply {
      val margin: Int = this.resources.displayMetrics.density.toInt() * 4
      drawOverlay(snapshotBitmap, overlay, margin)
    }
  }

  private fun drawOverlay(
    snapshotBitmap: Bitmap,
    overlay: SnapshotOverlay,
    margin: Int
  ) {
    context.get()?.let {
      val measure: AttributionMeasure = getAttributionMeasure(it, overlay, snapshotBitmap, margin)
      val layout = measure.measure()
      layout?.let {
        if (snapshotOverlayOptions.showLogo) {
          drawLogo(snapshotBitmap, overlay.canvas, margin, layout)
        }
        if (snapshotOverlayOptions.showAttributes) {
          drawAttribution(overlay, measure, layout)
        }
      }
    }
  }

  private fun getAttributionMeasure(
    context: Context,
    overlay: SnapshotOverlay,
    snapshotBitmap: Bitmap,
    margin: Int
  ): AttributionMeasure {
    val logo: Logo = createScaledLogo(context, snapshotBitmap)
    return AttributionMeasure(
      snapshot = snapshotBitmap,
      logo = logo.large,
      logoSmall = logo.small,
      textView = createTextView(context, overlay, false, logo.scale),
      textViewShort = createTextView(context, overlay, true, logo.scale),
      margin = margin.toFloat()
    )
  }

  private fun createTextView(
    context: Context,
    overlay: SnapshotOverlay,
    shortText: Boolean,
    scale: Float
  ): TextView {
    val textColor: Int = ResourcesCompat.getColor(
      context.resources, R.color.mapbox_gray_dark, context.theme
    )
    val widthMeasureSpec: Int = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    val heightMeasureSpec: Int = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    val textView = TextView(context)
    textView.layoutParams = ViewGroup.LayoutParams(
      ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
    )
    val attributionString = createAttributionString(overlay, shortText)
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
    overlay: SnapshotOverlay,
    shortText: Boolean
  ): String {
    context.get()?.apply {
      val attributionParser: AttributionParser = AttributionParser.Options(this)
        .withAttributionData(*overlay.attributions.toTypedArray())
        .withCopyrightSign(false)
        .withImproveMap(false)
        .withMapboxPrivacyPolicy(false)
        .withTelemetryAttribution(false)
        .build()
      return attributionParser.createAttributionString(shortText)
    }
    return ""
  }

  private fun drawLogo(
    snapshot: Bitmap,
    canvas: Canvas,
    margin: Int,
    placement: AttributionLayout
  ) {
    placement.logo?.let {
      canvas.drawBitmap(
        it, margin.toFloat(), (snapshot.height - it.height - margin).toFloat(), null
      )
    }
  }

  private fun createScaledLogo(context: Context, snapshotBitmap: Bitmap): Logo {
    val logo = BitmapFactory.decodeResource(context.resources, R.drawable.mapbox_logo_icon, null)
    val scale = calculateLogoScale(context, snapshotBitmap, logo)
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

  /**
   * Returns tileIDs that cover current map camera
   *
   * Note! This is an experimental API and behavior might change in future.
   *
   * @param tileCoverOptions Options for the tile cover method
   * @param cameraOptions This is an extra parameter for future use. Has no effect for now.
   */
  @MapboxExperimental
  fun tileCover(
    tileCoverOptions: TileCoverOptions,
    cameraOptions: CameraOptions?
  ): MutableList<CanonicalTileID> {
    return coreSnapshotter.tileCover(tileCoverOptions, cameraOptions)
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
    /**
     * Clears temporary map data.
     *
     * Clears temporary map data from the data path defined in the current options.
     * Useful to reduce the disk usage or in case the disk cache contains invalid data.
     *
     * Note that calling this API will affect all maps that use the same data path and does not
     * affect persistent map data like offline style packages.
     *
     * @param callback Called once the request is complete or an error occurred.
     */
    @JvmStatic
    fun clearData(callback: AsyncOperationResultCallback) {
      MapsResourceOptions.clearData(callback)
    }

    private const val TAG = "Snapshotter"

    @JvmSynthetic
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal operator fun invoke(
      context: WeakReference<Context>,
      options: MapSnapshotOptions,
      overlayOptions: SnapshotOverlayOptions,
      coreSnapshotter: MapSnapshotter,
    ) = Snapshotter(context, options, overlayOptions, coreSnapshotter)
  }
}
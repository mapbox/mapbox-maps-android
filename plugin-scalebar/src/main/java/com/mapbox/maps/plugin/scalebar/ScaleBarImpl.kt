package com.mapbox.maps.plugin.scalebar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.util.Pair
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.VisibleForTesting
import androidx.core.util.component1
import androidx.core.util.component2
import com.mapbox.maps.plugin.scalebar.generated.ScaleBarSettings
import java.lang.ref.WeakReference
import java.text.DecimalFormat

/**
 * Concrete implementation of ScaleBar.
 */
class ScaleBarImpl : ScaleBar, View {
  /**
   * The scaleTable used for calculating scale bar segments
   */
  internal var scaleTable: List<Pair<Int, Int>> = metricTable

  /**
   * The paint draw distance num and unit
   */
  internal val textPaint = Paint()

  /**
   * The paint draw scale bar
   */
  internal val barPaint = Paint()

  /**
   * The paint draw text border
   */
  internal val strokePaint = Paint()

  /**
   * The unit for distance
   */
  internal var unit: String = METER_UNIT

  /**
   * Defines the width of mapView
   */
  override var mapViewWidth = DEFAULT_MAPVIEW_WIDTH
    set(value) {
      field = value
      post(::requestLayout)
    }

  /**
   * Defines the pixel ratio in the current display.
   */
  override var pixelRatio = DEFAULT_PIXEL_RATIO

  /**
   * If set to True scale bar will be triggering onDraw depending on [ScaleBarSettings.refreshInterval]
   * even if actual data did not change. If set to False scale bar will redraw only on demand.
   *
   * Defaults to False and should not be changed explicitly in most cases.
   * Could be set to True to produce correct GPU frame metrics when running gfxinfo command.
   */
  override var useContinuousRendering = false
    set(value) {
      if (value) {
        if (!isScaleBarVisible) {
          visibility = VISIBLE
        }
        refreshHandler.removeMessages(MSG_RENDER_ON_DEMAND)
        refreshHandler.sendEmptyMessage(MSG_RENDER_CONTINUOUS)
      } else {
        if (!isScaleBarVisible) {
          visibility = GONE
        }
        refreshHandler.removeMessages(MSG_RENDER_CONTINUOUS)
        reusableCanvas = null
      }
      field = value
    }

  private val refreshHandler: RefreshHandler
  private val decimalFormat = DecimalFormat("0.#")
  private var isScaleBarVisible = false
  private var reusableCanvas: Canvas? = null

  /**
   * Current settings will be used to draw ScaleBar
   */
  override var settings: ScaleBarSettings = ScaleBarSettings { }
    set(value) {
      textPaint.color = value.textColor
      textPaint.textSize = value.textSize
      strokePaint.textSize = value.textSize
      scaleTable = if (value.isMetricUnits) metricTable else imperialTable
      unit = if (value.isMetricUnits) METER_UNIT else FEET_UNIT
      strokePaint.strokeWidth = if (value.showTextBorder) value.textBorderWidth else 0F
      enable = value.enabled
      if (useContinuousRendering) {
        reusableCanvas = null
      } else {
        if (!refreshHandler.hasMessages(MSG_RENDER_ON_DEMAND)) {
          refreshHandler.sendEmptyMessageDelayed(MSG_RENDER_ON_DEMAND, value.refreshInterval)
        }
      }

      field = value
      (layoutParams as FrameLayout.LayoutParams).apply {
        gravity = value.position
        setMargins(
          value.marginLeft.toInt(),
          value.marginTop.toInt(),
          value.marginRight.toInt(),
          value.marginBottom.toInt()
        )
      }
      // Refresh mapViewWidth
      mapViewWidth = mapViewWidth
    }

  private var currentSegmentsConfiguration: SegmentsConfiguration? = null

  /**
   * distancePerPixel in current location latitude
   */
  override var distancePerPixel = 0F
    set(value) {
      // We want to store imperial value but we're getting meters so transform it before checking
      // if it's different
      val valueCandidate = if (settings.isMetricUnits) value else value * FEET_PER_METER
      if (field != valueCandidate) {
        field = valueCandidate
        if (useContinuousRendering) {
          reusableCanvas = null
        } else {
          val maxDistance = mapViewWidth * distancePerPixel * settings.ratio
          val newSegmentsConfiguration = calculateSegmentsConfiguration(
            maxDistance = maxDistance,
            distancePerPixel = field,
            scaleTable = scaleTable,
            textPaint = textPaint,
            strokeWidth = strokePaint.strokeWidth,
            unit = unit,
            rightMarginPx = width
          )
          // Only force a re-draw if the bar UI has changed enough by checking current
          // `segmentsConfiguration` against new one and in case there isn't the same message
          // already queued
          if (newSegmentsConfiguration != currentSegmentsConfiguration &&
            !refreshHandler.hasMessages(MSG_RENDER_ON_DEMAND)
          ) {
            refreshHandler.sendEmptyMessageDelayed(MSG_RENDER_ON_DEMAND, settings.refreshInterval)
          }
        }
      }
    }

  /**
   * Whether ScaleBar is enabled or not
   */
  override var enable: Boolean
    get() = isScaleBarVisible
    set(value) {
      isScaleBarVisible = value
      if (!useContinuousRendering) {
        visibility = if (value) VISIBLE else GONE
      }
    }

  /**
   * Constructor with context.
   *
   * @param context
   */
  constructor(context: Context) : super(context)

  /**
   * Constructor with context and attribute set.
   *
   * @param context
   * @param attrs
   */
  constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

  /**
   * Constructor with context, attribute set and defStyleAttr.
   *
   * @param context
   * @param attrs The attribution set.
   * @param defStyleAttr An attribute in the current theme that contains a reference to a style
   *  resource that supplies defaults values for the StyledAttributes. Can be 0 to not look for defaults.
   */
  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
    context, attrs, defStyleAttr
  )

  init {
    textPaint.isAntiAlias = true
    textPaint.textAlign = Paint.Align.CENTER

    strokePaint.isAntiAlias = true
    strokePaint.textAlign = Paint.Align.CENTER
    strokePaint.style = Paint.Style.STROKE
    strokePaint.color = Color.WHITE

    barPaint.isAntiAlias = true
    refreshHandler = RefreshHandler(this).apply {
      if (useContinuousRendering) {
        sendEmptyMessage(MSG_RENDER_CONTINUOUS)
      }
    }
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val (viewWidth, viewHeight) = calculateWidthAndHeight()
    setMeasuredDimension(viewWidth.toInt(), viewHeight.toInt())
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal fun calculateWidthAndHeight(): Pair<Float, Float> {
    // Add padding the width to avoid texts being cut off and the end of scalebar.
    val width = mapViewWidth * settings.ratio + INTERNAL_PADDING_DP * pixelRatio
    val height = settings.run { textBarMargin + textSize + height + (borderWidth * 2) }
    return Pair(width, height)
  }

  /**
   * Draw ScaleBar with current settings
   */
  override fun onDraw(canvas: Canvas) {
    if (useContinuousRendering) {
      if (!isScaleBarVisible) {
        canvas.drawARGB(0, 0, 0, 0)
        return
      }
      if (reusableCanvas != null) {
        return
      }
    }
    if (distancePerPixel <= 0 || mapViewWidth <= 0 || width <= 0) {
      return
    }
    settings.run {
      val maxDistance = mapViewWidth * distancePerPixel * ratio
      if (maxDistance <= 0.1F) {
        // There's no space to render the smallest distance. Don't render anything.
        canvas.drawARGB(0, 0, 0, 0)
        return
      }
      val scaleBarUiConfiguration = calculateSegmentsConfiguration(
        maxDistance = maxDistance,
        distancePerPixel = distancePerPixel,
        scaleTable = scaleTable,
        textPaint = textPaint,
        strokeWidth = strokePaint.strokeWidth,
        unit = unit,
        rightMarginPx = width
      )
      currentSegmentsConfiguration = scaleBarUiConfiguration

      // Drawing the surrounding borders
      barPaint.style = Paint.Style.FILL_AND_STROKE
      barPaint.color = secondaryColor
      val barWidth = scaleBarUiConfiguration.unitBarWidth * scaleBarUiConfiguration.rectCount
      canvas.drawRect(
        0f,
        textBarMargin + textSize - (borderWidth * 2),
        barWidth + (borderWidth * 2),
        textBarMargin + textSize + height + (borderWidth * 2),
        barPaint
      )
      barPaint.color = primaryColor
      canvas.drawRect(
        borderWidth,
        textBarMargin + textSize - borderWidth,
        barWidth + borderWidth,
        textBarMargin + textSize + height + borderWidth,
        barPaint
      )

      // Drawing the fill
      barPaint.style = Paint.Style.FILL

      for (rectIndex in 0..scaleBarUiConfiguration.rectCount) {
        barPaint.color = if (rectIndex % 2 == 0) primaryColor else secondaryColor
        val distanceText =
          scaleBarUiConfiguration.labelTexts[rectIndex]
        when (rectIndex) {
          0 -> {
            textPaint.textAlign = Paint.Align.LEFT
            strokePaint.textAlign = Paint.Align.LEFT
          }
          else -> {
            textPaint.textAlign = Paint.Align.CENTER
            strokePaint.textAlign = Paint.Align.CENTER
          }
        }

        drawText(
          canvas = canvas,
          text = distanceText,
          x = scaleBarUiConfiguration.labelMarginsAndAnchor[rectIndex].third,
          y = textSize,
        )

        if (rectIndex != scaleBarUiConfiguration.rectCount) {
          canvas.drawRect(
            (borderWidth * 2) + (scaleBarUiConfiguration.unitBarWidth * rectIndex),
            textBarMargin + textSize,
            scaleBarUiConfiguration.unitBarWidth * (1 + rectIndex),
            textBarMargin + textSize + height,
            barPaint
          )
        }
      }
    }
    if (useContinuousRendering) {
      reusableCanvas = canvas
    }
  }

  private fun drawText(
    canvas: Canvas,
    text: String,
    x: Float,
    y: Float,
  ) {
    if (settings.showTextBorder) {
      canvas.drawText(text, x, y, strokePaint)
    }
    canvas.drawText(text, x, y, textPaint)
  }

  /**
   * @return a new configuration holding the values to be able to render the scale bar
   */
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal fun calculateSegmentsConfiguration(
    maxDistance: Float,
    distancePerPixel: Float,
    scaleTable: List<Pair<Int, Int>>,
    textPaint: Paint,
    strokeWidth: Float,
    unit: String,
    rightMarginPx: Int
  ): SegmentsConfiguration {
    // Find the entry where the distance just fits in the given `maxDistance`
    val pair =
      scaleTable.lastOrNull { (distance: Int, _: Int) -> distance <= maxDistance } ?: scaleTable[0]

    var distance: Float = pair.first.toFloat()
    var rectCount: Int = pair.second
    var unitDistance: Float = distance / rectCount

    // When maxDistance is small (i.e. high zoom levels near the poles) then
    // the `distance` might be bigger than maxDistance. This loop will keep removing
    // bar divisions (rectCount) until it fits
    while (unitDistance * rectCount > maxDistance && rectCount > 0) {
      rectCount--
      distance = unitDistance * rectCount
    }
    // In case the unitDistance doesn't fit at all we fallback to maxDistance (rounded to 1
    // decimal) with 1 division
    if (rectCount == 0) {
      unitDistance = maxDistance.toOneDecimal()
      rectCount = 1
      distance = unitDistance
    }

    var overlapDetected = true
    val labelTexts: MutableList<String> = mutableListOf()
    val labelMarginsAndAnchor: MutableList<Triple<Float, Float, Float>> = mutableListOf()
    var unitBarWidth = 0F
    while (overlapDetected) {
      unitDistance = distance / rectCount
      unitBarWidth = (unitDistance / distancePerPixel).toOneDecimal()
      labelTexts.clear()
      labelMarginsAndAnchor.clear()

      // Let's create the text label and calculate the position per each one
      for (idx in 0..rectCount) {
        labelTexts.add(idx, getDistanceText(unitDistance * idx, unit))
        labelMarginsAndAnchor.add(
          idx,
          calculateTextPositions(
            labelTexts[idx],
            unitBarWidth * idx,
            if (idx == 0) Paint.Align.LEFT else Paint.Align.CENTER,
            textPaint,
            strokeWidth,
            rightMarginPx
          )
        )
      }

      // Figure out if there's an overlap, by comparing each label right position with the next
      // one left position
      overlapDetected = false
      for (idx in 0 until labelMarginsAndAnchor.size - 1) {
        // `second` is the label right margin, `first` is the left margin
        if (labelMarginsAndAnchor[idx].second >= labelMarginsAndAnchor[idx + 1].first) {
          if (rectCount == 1) {
            // There is a collision and we can't reduce the count, let's hide the `0` label
            labelTexts[0] = ""
            break
          }
          // There's is a collision so reduce the `rectCount` and start all over again
          rectCount--
          overlapDetected = true
          break
        }
      }
    }
    return SegmentsConfiguration(
      unitDistance = unitDistance,
      unitBarWidth = unitBarWidth,
      rectCount = rectCount,
      labelTexts = labelTexts.toList(),
      labelMarginsAndAnchor = labelMarginsAndAnchor.toList()
    )
  }

  private fun calculateTextPositions(
    text: String,
    anchorXPx: Float,
    alignment: Paint.Align,
    textPaint: Paint,
    strokeWidth: Float,
    rightMarginPx: Int,
  ): Triple<Float, Float, Float> {
    var safeAnchorX = anchorXPx

    val textWidthPx = textPaint.measureText(text)
    var textRightPx = anchorXPx + strokeWidth / 2F + when (alignment) {
      Paint.Align.LEFT -> textWidthPx
      Paint.Align.CENTER -> textWidthPx / 2
      Paint.Align.RIGHT -> 0F
    }

    // Check if it goes beyond the right margin of the view
    if (textRightPx > rightMarginPx) {
      // Move it away from right margin enough to fit
      safeAnchorX -= (textRightPx - rightMarginPx)
    }
    var textLeftPx = safeAnchorX - strokeWidth / 2F - when (alignment) {
      Paint.Align.LEFT -> 0F
      Paint.Align.CENTER -> textWidthPx / 2
      Paint.Align.RIGHT -> textWidthPx
    }

    // Check if if the text would fall beyond the left margin
    if (textLeftPx < 0) {
      // Shift everything to the right so it fits
      textLeftPx += strokeWidth / 2
      safeAnchorX += strokeWidth / 2
      textRightPx += strokeWidth / 2
      // At this point it can happen that textRightPx is beyond the rightMarginPx
      // that means that the text doesn't fit! We can't solve it so we let the text be cut on
      // the right
    }
    return Triple(textLeftPx.toOneDecimal(), textRightPx.toOneDecimal(), safeAnchorX.toOneDecimal())
  }

  /**
   * Get the formatted distance text according unit and distance
   *
   * @param distance original distance
   * @param unit the unit to use, either [METER_UNIT] or [FEET_UNIT]
   * @return Formatted distance text
   */
  internal fun getDistanceText(distance: Float, unit: String): String = when {
    distance == 0F -> "0"
    METER_UNIT == unit -> {
      if (distance < KILOMETER) decimalFormat.format(distance) + unit
      else decimalFormat.format(distance * 1.0 / KILOMETER) + KILOMETER_UNIT
    }
    else -> {
      if (distance < FEET_PER_MILE) decimalFormat.format(distance) + unit
      else decimalFormat.format(distance * 1.0 / FEET_PER_MILE) + MILE_UNIT
    }
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal fun refreshHandlerHasMessages(what: Int): Boolean = refreshHandler.hasMessages(what)

  /**
   * Handler class to limit the refresh frequent.
   */
  private class RefreshHandler(scaleBarImpl: ScaleBarImpl) : Handler(Looper.getMainLooper()) {
    private var scaleBarWidgetWeakReference: WeakReference<ScaleBarImpl> =
      WeakReference(scaleBarImpl)

    override fun handleMessage(msg: Message) {
      scaleBarWidgetWeakReference.get()?.let {
        when (msg.what) {
          MSG_RENDER_CONTINUOUS -> {
            if (it.reusableCanvas == null) {
              it.invalidate()
            } else {
              it.draw(it.reusableCanvas)
            }
            sendEmptyMessageDelayed(MSG_RENDER_CONTINUOUS, it.settings.refreshInterval)
          }
          MSG_RENDER_ON_DEMAND -> it.invalidate()
          else -> return
        }
      }
    }
  }

  /**
   * Static variables and methods.
   */
  companion object {
    internal const val MSG_RENDER_ON_DEMAND = 0
    internal const val MSG_RENDER_CONTINUOUS = 1
    internal const val DEFAULT_MAPVIEW_WIDTH = 0F
    internal const val DEFAULT_PIXEL_RATIO = 1.0F
    internal const val INTERNAL_PADDING_DP = 10
  }
  private fun Float.toOneDecimal(): Float = (this * 10).toInt() / 10.0F
}

internal data class SegmentsConfiguration(
  var unitDistance: Float,
  var unitBarWidth: Float,
  var rectCount: Int,
  var labelTexts: List<String>,
  /**
   * A [Triple] that contains per each label:
   * 1. left margin in pixels
   * 2. right margin in pixels
   * 3. anchor X in pixels
   */
  var labelMarginsAndAnchor: List<Triple<Float, Float, Float>>,
)
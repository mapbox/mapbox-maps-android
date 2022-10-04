package com.mapbox.maps.plugin.scalebar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Message
import android.os.Trace
import android.util.AttributeSet
import android.util.Log
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
  internal var unit: String? = null

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

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal val refreshHandler: RefreshHandler
  private val decimalFormat = DecimalFormat("0.#")
  private var isScaleBarVisible = false
  private var reusableCanvas: Canvas? = null

  /**
   * Current settings will be used to draw ScaleBar
   */
  override var settings: ScaleBarSettings = ScaleBarSettings()
    set(value) {
      Trace.beginSection("ScaleBarImpl.settings.set")

      try {
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
      } finally {
        Trace.endSection() // "ScaleBarImpl.settings.set"
      }
    }

  /**
   * distancePerPixel in current location latitude
   */
  override var distancePerPixel = 0F
    set(value) {
      Log.d("ScaleBarImpl", "distancePerPixel: field: $field, value: $value")
      if (field != value) {
        Trace.beginSection("ScaleBarImpl.distancePerPixel.set")
        if (useContinuousRendering) {
          reusableCanvas = null
        } else {
          if (!refreshHandler.hasMessages(MSG_RENDER_ON_DEMAND)) {
            Log.d("ScaleBarImpl", "sendEmptyMessageDelayed")
            refreshHandler.sendEmptyMessageDelayed(MSG_RENDER_ON_DEMAND, settings.refreshInterval)
          }
        }
        // TODO: Check the value is always different than field for Imperial units due to this conversion
        field = if (settings.isMetricUnits) value else value * FEET_PER_METER
        Trace.endSection() // "ScaleBarImpl.distancePerPixel.set")
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
    context,
    attrs,
    defStyleAttr
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

  override fun invalidate() {
    Trace.beginSection("ScaleBarImpl.invalidate")
    super.invalidate()
    Trace.endSection() // "ScaleBarImpl.invalidate"
  }

  /**
   * Draw ScaleBar with current settings
   */
  @SuppressLint("DrawAllocation")
  override fun onDraw(canvas: Canvas) {
    Trace.beginSection("ScaleBarImpl.onDraw")

    try {
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
        canvas.drawARGB(255, 0, 0, 0)
        val maxDistance = mapViewWidth * distancePerPixel * ratio
        if (maxDistance <= 0.1F) {
          // There's no space to render the smallest distance. Don't render anything.
          canvas.drawARGB(0, 0, 0, 0)
          return
        }
        val (unitDistance, rectCount) = findSuitableScaleBarSegments(maxDistance)

        val unitBarWidth = (unitDistance / distancePerPixel)

        val distanceTexts: Array<String?> = Array(rectCount + 1) {
          getDistanceText(unitDistance * it)
        }
        for (rectIndex in 0..rectCount) {

        }


        // Drawing the surrounding borders
        Trace.beginSection("ScaleBarImpl.onDraw.Borders")
        barPaint.style = Paint.Style.FILL_AND_STROKE
        barPaint.color = secondaryColor
        canvas.drawRect(
          0f,
          textBarMargin + textSize - (borderWidth * 2),
          (unitBarWidth * rectCount) + (borderWidth * 2),
          textBarMargin + textSize + height + (borderWidth * 2),
          barPaint
        )
        barPaint.color = primaryColor
        canvas.drawRect(
          borderWidth,
          textBarMargin + textSize - borderWidth,
          (unitBarWidth * rectCount) + borderWidth,
          textBarMargin + textSize + height + borderWidth,
          barPaint
        )
        Trace.endSection() // "ScaleBarImpl.onDraw.Borders"

        // Drawing the fill
        barPaint.style = Paint.Style.FILL

        // Drawing texts
        Trace.beginSection("ScaleBarImpl.onDraw.BarsAndText")




        for (rectIndex in 0..rectCount) {
          barPaint.color = if (rectIndex % 2 == 0) primaryColor else secondaryColor
          val distanceText = getDistanceText(unitDistance * rectIndex)
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
            x = (unitBarWidth * rectIndex),
            y = textSize,
            lastUnitBarText = (rectIndex == rectCount),
          )

          if (rectIndex != rectCount) {
            canvas.drawRect(
              (borderWidth * 2) + (unitBarWidth * rectIndex),
              textBarMargin + textSize,
              unitBarWidth * (1 + rectIndex),
              textBarMargin + textSize + height,
              barPaint
            )
          }
        }
        Trace.endSection() // "ScaleBarImpl.onDraw.BarsAndText"
      }
      if (useContinuousRendering) {
        reusableCanvas = canvas
      }
    } finally {
      Trace.endSection() // "ScaleBarImpl.onDraw"
    }
  }

  private fun drawText(
    canvas: Canvas,
    text: String,
    x: Float,
    y: Float,
    lastUnitBarText: Boolean,
  ) {
    var safeX = x

    // As an optimization only check if it goes beyond right view for the last unit bar text
    if (lastUnitBarText) {
      // Check if it goes beyond the right margin of the view
      val textWidthPx = textPaint.measureText(text)
      val textMaxRightPx = x + strokePaint.strokeWidth + when (textPaint.textAlign) {
        Paint.Align.LEFT -> textWidthPx
        Paint.Align.CENTER -> textWidthPx / 2
        Paint.Align.RIGHT, null -> 0F
      }
      if (textMaxRightPx > width) {
        // Move it away from right margin enough to fit
        safeX -= (textMaxRightPx - width)
      }
    }

    // Check if it goes beyond the left margin of the view
    if (safeX - (strokePaint.strokeWidth / 2) < 0) {
      safeX += (strokePaint.strokeWidth / 2)
    }

    if (settings.showTextBorder) {
      canvas.drawText(text, safeX, y, strokePaint)
    }
    canvas.drawText(text, safeX, y, textPaint)
  }

  /**
   * @return A tuple with the scale bar segment distance and the amount of bar segments
   */
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal fun findSuitableScaleBarSegments(maxDistance: Float): Pair<Float, Int> {
    Trace.beginSection("ScaleBarImpl.findSuitableScaleBarSegments")

    try {
      var pair: Pair<Int, Int> = scaleTable[0]
      for (i in 1 until scaleTable.size) {
        pair = scaleTable[i]
        val distance = pair.first
        if (distance > maxDistance) {
          // use the previous scale here, otherwise the scale will be too large
          pair = scaleTable[i - 1]
          break
        }
      }
      val distance = pair.first.toFloat()
      var rectCount = pair.second
      var unitDistance = distance / rectCount
      // When maxDistance is small (i.e. high zoom levels near the poles) then
      // the `distance` might be bigger than maxDistance. This loop will keep removing
      // bar divisions (rectCount) until it fits
      while (unitDistance * rectCount > maxDistance && rectCount > 0) {
        rectCount--
      }
      // In case the unitDistance doesn't fit at all we fallback to maxDistance (rounded to 1
      // decimal) with 1 division
      if (rectCount == 0) {
        unitDistance = (maxDistance * 10).toInt() / 10.0F
        rectCount = 1
      }
      with(ScaleBarUiConfiguration) {
        var overlapDetected = true
        this.rectCount = rectCount
        while (overlapDetected) {
          this.unitDistance = distance / this.rectCount
          labelTexts = arrayOfNulls(this.rectCount + 1)
          labelTextPositions = FloatArray(this.rectCount + 1)
          labelTextMargins = arrayOfNulls(this.rectCount + 1)
          unitBarWidth = this.unitDistance / distancePerPixel
          for (idx in 0..this.rectCount) {
            labelTexts[idx] = getDistanceText(this.unitDistance * idx)
            labelTextPositions[idx] = unitBarWidth * idx
            labelTextMargins[idx] =
              calculateTextPositions(labelTexts[idx]!!, labelTextPositions[idx])
          }
          overlapDetected = false
          for (idx in 0 until labelTextMargins.size - 1) {
            if (labelTextMargins[idx]!!.second >= labelTextMargins[idx + 1]!!.first) {
              this.rectCount--
              overlapDetected = true
              break
            }
          }
        }
      }
      return Pair(ScaleBarUiConfiguration.unitDistance, ScaleBarUiConfiguration.rectCount)
    } finally {
      Trace.endSection()
    }
  }

  private fun calculateTextPositions(
    text: String,
    x: Float,
  ): Pair<Float, Float> {
    var safeX = x

    // As an optimization only check if it goes beyond right view for the last unit bar text
    // if (lastUnitBarText) {
    // Check if it goes beyond the right margin of the view
    val textWidthPx = textPaint.measureText(text)
    val textRightPosPx = x + strokePaint.strokeWidth / 2F + when (textPaint.textAlign) {
      Paint.Align.LEFT -> textWidthPx
      Paint.Align.CENTER -> textWidthPx / 2
      Paint.Align.RIGHT, null -> 0F
    }

    if (textRightPosPx > width) {
      // Move it away from right margin enough to fit
      safeX -= (textRightPosPx - width)
    }
    var textLeftPosPx = safeX - strokePaint.strokeWidth / 2F - when (textPaint.textAlign) {
      Paint.Align.LEFT, null -> 0F
      Paint.Align.CENTER -> textWidthPx / 2
      Paint.Align.RIGHT -> textWidthPx
    }
    //}

    // Check if if the text would fall beyond the left margin
    if (textLeftPosPx < 0) {
      textLeftPosPx += strokePaint.strokeWidth / 2
    }
    return Pair(textLeftPosPx, textRightPosPx)
  }

  /**
   * Get the formatted distance text according unit and distance
   *
   * @param distance original distance
   * @return Formatted distance text
   */
  internal fun getDistanceText(distance: Float): String {
    return if (distance == 0F) {
      "0"
    } else if (METER_UNIT == unit) {
      if (distance < KILOMETER) decimalFormat.format(distance) + unit
      else decimalFormat.format(distance * 1.0 / KILOMETER) + KILOMETER_UNIT
    } else {
      if (distance < FEET_PER_MILE) decimalFormat.format(distance) + unit
      else decimalFormat.format(distance * 1.0 / FEET_PER_MILE) + MILE_UNIT
    }
  }

  /**
   * Handler class to limit the refresh frequent.
   */
  internal class RefreshHandler(scaleBarImpl: ScaleBarImpl) : Handler() {
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

          MSG_RENDER_ON_DEMAND -> {
            Trace.beginAsyncSection("MSG_RENDER_ON_DEMAND.invalidate", 0)
            it.invalidate()
            Trace.endAsyncSection("MSG_RENDER_ON_DEMAND.invalidate", 0)
          }

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
}

private object ScaleBarUiConfiguration {
  var unitDistance: Float = 0F
  var unitBarWidth: Float = 0F
  var rectCount: Int = 0
  var labelTexts: Array<String?> = emptyArray()
  var labelTextPositions: FloatArray = floatArrayOf()
  var labelTextMargins: Array<Pair<Float, Float>?> = emptyArray()
}
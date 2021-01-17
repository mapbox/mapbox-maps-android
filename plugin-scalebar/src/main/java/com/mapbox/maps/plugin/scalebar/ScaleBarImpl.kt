package com.mapbox.maps.plugin.scalebar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.util.Pair
import android.view.View
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
   * The text path
   */
  internal val path = Path()

  /**
   * The unit for distance
   */
  internal var unit: String? = null

  /**
   * The max width of scale bar
   */
  internal var maxBarWidth = DEFAULT_BAR_WIDTH

  /**
   * Defines the width of mapView
   */
  override var mapViewWidth = DEFAULT_MAPVIEW_WIDTH
    set(value) {
      field = value
      if (value > 0) {
        maxBarWidth = value * settings.ratio - settings.marginLeft
      }
    }

  private val refreshHandler: RefreshHandler
  private val decimalFormat = DecimalFormat("0.#")

  /**
   * Current settings will be used to draw ScaleBar
   */
  override var settings: ScaleBarSettings = ScaleBarSettings()
    set(value) {
      textPaint.color = value.textColor
      textPaint.textSize = value.textSize
      strokePaint.textSize = value.textSize
      scaleTable = if (value.isMetricUnits) metricTable else imperialTable
      unit = if (value.isMetricUnits) METER_UNIT else FEET_UNIT
      strokePaint.strokeWidth = value.textBorderWidth
      enable = value.enabled
      if (!refreshHandler.hasMessages(MSG_WHAT)) {
        refreshHandler.sendEmptyMessageDelayed(MSG_WHAT, value.refreshInterval)
      }
      // Refresh mapViewWidth
      mapViewWidth = mapViewWidth
      field = value
    }

  /**
   * distancePerPixel in current location latitude
   */
  override var distancePerPixel = 0F
    set(value) {
      if (!refreshHandler.hasMessages(MSG_WHAT)) {
        refreshHandler.sendEmptyMessageDelayed(MSG_WHAT, settings.refreshInterval)
      }
      field = if (settings.isMetricUnits) value else value * FEET_PER_METER
    }

  /**
   * Whether ScaleBar is enabled or not
   */
  override var enable: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
      visibility = if (value) View.VISIBLE else View.GONE
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
    refreshHandler = RefreshHandler(this)
  }

  /**
   * Draw ScaleBar with current settings
   */
  override fun onDraw(canvas: Canvas) {
    if (distancePerPixel <= 0 || mapViewWidth <= 0 || maxBarWidth <= 0) {
      return
    }
    val maxDistance = mapViewWidth * distancePerPixel * settings.ratio
    var pair = scaleTable[0]
    for (i in 1 until scaleTable.size) {
      pair = scaleTable[i]
      if (pair.first > maxDistance) {
        // use the last scale here, otherwise the scale will be too large
        pair = scaleTable[i - 1]
        break
      }
    }
    var unitDistance = pair.first / pair.second
    var unitBarWidth = maxBarWidth / pair.second
    if (unitDistance == 0) {
      unitDistance = 1
    } else {
      unitBarWidth = (unitDistance / distancePerPixel)
    }
    // Drawing the surrounding borders
    barPaint.style = Paint.Style.FILL_AND_STROKE
    barPaint.color = settings.secondaryColor
    canvas.drawRect(
      settings.marginLeft - settings.borderWidth * 2,
      settings.textBarMargin + settings.textSize + settings.marginTop - settings.borderWidth * 2,
      settings.marginLeft + unitBarWidth * pair.second + settings.borderWidth * 2,
      settings.textBarMargin + settings.textSize + settings.marginTop + settings.height + settings.borderWidth * 2,
      barPaint
    )
    barPaint.color = settings.primaryColor
    canvas.drawRect(
      settings.marginLeft - settings.borderWidth,
      settings.textBarMargin + settings.textSize + settings.marginTop - settings.borderWidth,
      settings.marginLeft + unitBarWidth * pair.second + settings.borderWidth,
      settings.textBarMargin + settings.textSize + settings.marginTop + settings.height + settings.borderWidth,
      barPaint
    )

    // Drawing the fill
    barPaint.style = Paint.Style.FILL
    for (i in 0 until pair.second) {
      barPaint.color = if (i % 2 == 0) settings.primaryColor else settings.secondaryColor
      val distanceText = getDistanceText(unitDistance * i)
      textPaint.getTextPath(
        distanceText, 0, distanceText.length, settings.marginLeft + unitBarWidth * i,
        settings.textSize + settings.marginTop, path
      )
      if (settings.showTextBorder) {
        canvas.drawPath(path, strokePaint)
      }
      canvas.drawPath(path, textPaint)
      canvas.drawRect(
        settings.marginLeft + unitBarWidth * i,
        settings.textBarMargin + settings.textSize + settings.marginTop,
        settings.marginLeft + unitBarWidth * (1 + i),
        settings.textBarMargin + settings.textSize + settings.marginTop + settings.height,
        barPaint
      )
    }
    val distanceText = getDistanceText(unitDistance * pair.second)
    textPaint.getTextPath(
      distanceText, 0, distanceText.length, settings.marginLeft + unitBarWidth * pair.second,
      settings.textSize + settings.marginTop, path
    )
    if (settings.showTextBorder) {
      canvas.drawPath(path, strokePaint)
    }
    canvas.drawPath(path, textPaint)
  }

  /**
   * Get the formatted distance text according unit and distance
   *
   * @param distance original distance
   * @return Formatted distance text
   */
  internal fun getDistanceText(distance: Int): String {
    return if (distance == 0) {
      "0"
    } else if (METER_UNIT == unit) {
      if (distance < KILOMETER) distance.toString() + unit
      else decimalFormat.format(distance * 1.0 / KILOMETER) + KILOMETER_UNIT
    } else {
      if (distance < FEET_PER_MILE) distance.toString() + unit
      else decimalFormat.format(distance * 1.0 / FEET_PER_MILE) + MILE_UNIT
    }
  }

  /**
   * Handler class to limit the refresh frequent.
   */
  private class RefreshHandler(scaleBarImpl: ScaleBarImpl) : Handler() {
    private var scaleBarWidgetWeakReference: WeakReference<ScaleBarImpl> =
      WeakReference(scaleBarImpl)

    override fun handleMessage(msg: Message) {
      scaleBarWidgetWeakReference.get()?.let {
        if (msg.what == MSG_WHAT) {
          it.invalidate()
        }
      }
    }
  }

  /**
   * Static variables and methods.
   */
  companion object {
    internal const val MSG_WHAT = 0
    internal const val DEFAULT_MAPVIEW_WIDTH = 0F
    internal const val DEFAULT_BAR_WIDTH = 0F
  }
}
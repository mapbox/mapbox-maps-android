package com.mapbox.maps.plugin.consolidatedattribution

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt

/**
 * Disclosure chevron shown at the trailing edge of the expanded pill.
 *
 * The chevron is stroked twice: once in [outlineColor] as a halo that keeps it legible where
 * the pill is translucent and the map shows through, then in [shapeColor] on top.
 */
internal class ChevronView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

  @ColorInt
  private var shapeColor: Int = 0xB3000000.toInt()

  @ColorInt
  private var outlineColor: Int = 0xFFFFFFFF.toInt()

  private val path = Path().apply {
    moveTo(2.5f, 2.5f)
    lineTo(6.5f, 7f)
    lineTo(2.5f, 11.5f)
  }

  private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    style = Paint.Style.STROKE
    strokeCap = Paint.Cap.ROUND
    strokeJoin = Paint.Join.ROUND
  }

  init {
    isClickable = false
    isFocusable = false
    importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_NO
  }

  fun applyColors(@ColorInt contentColor: Int, @ColorInt detailColor: Int) {
    shapeColor = contentColor
    outlineColor = detailColor
    invalidate()
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    if (width == 0 || height == 0) {
      return
    }
    val save = canvas.save()
    canvas.scale(width / REFERENCE_WIDTH, height / REFERENCE_HEIGHT)

    strokePaint.color = outlineColor
    strokePaint.strokeWidth = OUTLINE_WIDTH
    canvas.drawPath(path, strokePaint)

    strokePaint.color = shapeColor
    strokePaint.strokeWidth = STROKE_WIDTH
    canvas.drawPath(path, strokePaint)

    canvas.restoreToCount(save)
  }

  companion object {
    private const val REFERENCE_WIDTH = 9f
    private const val REFERENCE_HEIGHT = 14f
    private const val OUTLINE_WIDTH = 3.2f
    private const val STROKE_WIDTH = 1.7f

    const val INTRINSIC_WIDTH_DP = 9f
    const val INTRINSIC_HEIGHT_DP = 14f
  }
}
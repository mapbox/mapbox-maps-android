package com.mapbox.maps.plugin.consolidatedattribution

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * Draws the Mapbox icon (pin + optional background disc) directly via [Canvas] path drawing.
 *
 * Path geometry is ported from the iOS LogoView bezier paths. Do not modify coordinates.
 * Reference coordinate space: 21 × 21.
 *
 * Color mapping from iOS [LogoView.drawIconInCurrentContext]:
 * - iOS `primaryColor` → [detailColor]  (disc fill, star highlight)
 * - iOS `accentColor`  → [contentColor] (outer ring, pin body)
 *
 * @param showBackground When `false`, the background disc (bezier12/13) is omitted.
 *   Set to `false` when this view sits inside the consolidated attribution pill — the pill
 *   surface serves as the circular background.
 */
internal class LogoIconView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
  var showBackground: Boolean = true,
) : View(context, attrs, defStyleAttr) {

  private val discPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
  private val ringPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
  private val bodyPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
  private val starPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }

  // Bezier #12 — background disc (iOS primaryColor → detailColor)
  private val discPath = Path().apply {
    moveTo(10.5f, 1.24f)
    cubicTo(5.39f, 1.24f, 1.25f, 5.39f, 1.25f, 10.49f)
    cubicTo(1.25f, 15.59f, 5.4f, 19.74f, 10.5f, 19.74f)
    cubicTo(15.6f, 19.74f, 19.75f, 15.59f, 19.75f, 10.49f)
    cubicTo(19.75f, 5.38f, 15.61f, 1.24f, 10.5f, 1.24f)
    close()
    moveTo(14.89f, 12.77f)
    cubicTo(12.96f, 14.7f, 10.11f, 15.08f, 8.19f, 15.08f)
    cubicTo(7.49f, 15.08f, 6.78f, 15.03f, 6.09f, 14.92f)
    cubicTo(6.09f, 14.92f, 5.07f, 9.28f, 8.23f, 6.11f)
    cubicTo(9.06f, 5.28f, 10.18f, 4.83f, 11.36f, 4.83f)
    cubicTo(12.63f, 4.83f, 13.85f, 5.34f, 14.75f, 6.25f)
    cubicTo(16.59f, 8.09f, 16.64f, 11f, 14.89f, 12.77f)
    close()
  }

  // Bezier #13 — outer ring (iOS accentColor → contentColor)
  private val ringPath = Path().apply {
    moveTo(10.5f, -0.01f)
    cubicTo(4.7f, -0.01f, 0f, 4.7f, 0f, 10.49f)
    cubicTo(0f, 16.28f, 4.7f, 20.99f, 10.5f, 20.99f)
    cubicTo(16.3f, 20.99f, 21f, 16.29f, 21f, 10.49f)
    cubicTo(20.99f, 4.7f, 16.3f, -0.01f, 10.5f, -0.01f)
    close()
    moveTo(10.5f, 19.74f)
    cubicTo(5.39f, 19.74f, 1.25f, 15.59f, 1.25f, 10.49f)
    cubicTo(1.25f, 5.39f, 5.39f, 1.23f, 10.5f, 1.23f)
    cubicTo(15.61f, 1.23f, 19.75f, 5.38f, 19.75f, 10.48f)
    cubicTo(19.75f, 15.61f, 15.61f, 19.74f, 10.5f, 19.74f)
    close()
  }

  // Bezier #14 — pin body (iOS accentColor → contentColor)
  private val bodyPath = Path().apply {
    moveTo(14.74f, 6.25f)
    cubicTo(12.9f, 4.41f, 9.98f, 4.35f, 8.23f, 6.1f)
    cubicTo(5.07f, 9.27f, 6.09f, 14.91f, 6.09f, 14.91f)
    cubicTo(6.09f, 14.91f, 11.73f, 15.93f, 14.9f, 12.77f)
    cubicTo(16.64f, 11f, 16.59f, 8.09f, 14.74f, 6.25f)
    close()
    moveTo(12.47f, 10.34f)
    lineTo(11.56f, 12.21f)
    lineTo(10.66f, 10.34f)
    lineTo(8.8f, 9.43f)
    lineTo(10.66f, 8.53f)
    lineTo(11.56f, 6.66f)
    lineTo(12.47f, 8.53f)
    lineTo(14.33f, 9.43f)
    lineTo(12.47f, 10.34f)
    close()
  }

  // Bezier #15 — star highlight (iOS primaryColor → detailColor)
  // Coordinates are relative to local origin; canvas is translated to (11.55, 9.45)
  // and rotated -0.05° before drawing.
  private val starPath = Path().apply {
    moveTo(0f, -2.75f)
    lineTo(0.92f, -0.92f)
    lineTo(2.75f, 0f)
    lineTo(0.92f, 0.92f)
    lineTo(0f, 2.75f)
    lineTo(-0.92f, 0.92f)
    lineTo(-2.75f, 0f)
    lineTo(-0.92f, -0.92f)
    close()
  }

  init {
    setWillNotDraw(false)
  }

  /**
   * Apply the active color scheme.
   *
   * @param contentColor ARGB color for the outer ring and pin body (iOS accentColor role).
   * @param detailColor  ARGB color for the disc fill and star highlight (iOS primaryColor role).
   */
  fun applyColors(contentColor: Int, detailColor: Int) {
    discPaint.color = detailColor
    starPaint.color = detailColor
    ringPaint.color = contentColor
    bodyPaint.color = contentColor
    invalidate()
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val sizePx = (INTRINSIC_SIZE_DP * resources.displayMetrics.density).toInt()
    setMeasuredDimension(sizePx, sizePx)
  }

  override fun onDraw(canvas: Canvas) {
    canvas.save()
    // Scale 21×21 reference space to actual pixel dimensions.
    val scale = width.toFloat() / REFERENCE_SIZE
    canvas.scale(scale, scale)

    if (showBackground) {
      canvas.drawPath(discPath, discPaint)
      canvas.drawPath(ringPath, ringPaint)
    }

    canvas.drawPath(bodyPath, bodyPaint)

    // Star is defined relative to (0,0); translate to pin center and apply rotation.
    canvas.save()
    canvas.translate(11.55f, 9.45f)
    canvas.rotate(-0.05f)
    canvas.drawPath(starPath, starPaint)
    canvas.restore()

    canvas.restore()
  }

  private companion object {
    const val INTRINSIC_SIZE_DP = 21f
    const val REFERENCE_SIZE = 21f
  }
}
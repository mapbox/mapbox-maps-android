package com.mapbox.maps.plugin.consolidatedattribution

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * Draws the Mapbox "mapbox" wordmark directly via [Canvas] path drawing.
 *
 * Path geometry is ported from the iOS LogoView bezier paths. Do not modify coordinates.
 * Reference coordinate space: 85 × 21 (full logo including icon at x = 0..21).
 * The canvas is translated by −21 so that the letters (x ≈ 22..85) render in a local
 * 64 × 21 coordinate space matching the view's intrinsic size.
 *
 * Color mapping from iOS [LogoView.drawWordmarkInCurrentContext]:
 * - iOS `primaryColor` → [contentColor] (letter fills: bezier1, 2, 3, 6, 8, 10)
 * - iOS `accentColor`  → [detailColor]  (inner counters + outline halo: bezier4, 5, 7, 9, 11)
 */
internal class LogoWordmarkView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

  private val contentPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
  private val detailPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }

  // -------------------------------------------------------------------------
  // Bezier #1 — "x" letter (iOS primaryColor → contentColor)
  // -------------------------------------------------------------------------
  private val bezier1 = Path().apply {
    moveTo(83.25f, 14.26f)
    cubicTo(83.25f, 14.38f, 83.16f, 14.47f, 83.04f, 14.47f)
    lineTo(81.43f, 14.47f)
    cubicTo(81.3f, 14.47f, 81.19f, 14.41f, 81.13f, 14.3f)
    lineTo(79.69f, 11.91f)
    lineTo(78.25f, 14.3f)
    cubicTo(78.19f, 14.41f, 78.07f, 14.47f, 77.95f, 14.47f)
    lineTo(76.34f, 14.47f)
    cubicTo(76.3f, 14.47f, 76.26f, 14.46f, 76.22f, 14.44f)
    cubicTo(76.13f, 14.38f, 76.09f, 14.25f, 76.16f, 14.16f)
    lineTo(76.16f, 14.16f)
    lineTo(78.59f, 10.48f)
    lineTo(76.2f, 6.84f)
    cubicTo(76.18f, 6.81f, 76.17f, 6.77f, 76.17f, 6.72f)
    cubicTo(76.17f, 6.6f, 76.26f, 6.51f, 76.38f, 6.51f)
    lineTo(77.99f, 6.51f)
    cubicTo(78.12f, 6.51f, 78.23f, 6.57f, 78.29f, 6.68f)
    lineTo(79.7f, 9.04f)
    lineTo(81.1f, 6.69f)
    cubicTo(81.16f, 6.58f, 81.28f, 6.52f, 81.4f, 6.52f)
    lineTo(83f, 6.52f)
    cubicTo(83.04f, 6.52f, 83.08f, 6.53f, 83.12f, 6.55f)
    cubicTo(83.21f, 6.61f, 83.25f, 6.74f, 83.18f, 6.83f)
    lineTo(83.18f, 6.83f)
    lineTo(80.81f, 10.46f)
    lineTo(83.24f, 14.13f)
    cubicTo(83.24f, 14.18f, 83.25f, 14.22f, 83.25f, 14.26f)
    close()
  }

  // -------------------------------------------------------------------------
  // Bezier #2 — "b" outer (iOS primaryColor → contentColor)
  // -------------------------------------------------------------------------
  private val bezier2 = Path().apply {
    moveTo(66.24f, 9.59f)
    cubicTo(65.85f, 7.71f, 64.28f, 6.31f, 62.4f, 6.31f)
    cubicTo(61.37f, 6.31f, 60.37f, 6.73f, 59.67f, 7.49f)
    lineTo(59.67f, 3.51f)
    cubicTo(59.67f, 3.38f, 59.57f, 3.28f, 59.44f, 3.28f)
    lineTo(58.04f, 3.28f)
    cubicTo(57.91f, 3.28f, 57.81f, 3.39f, 57.81f, 3.51f)
    lineTo(57.81f, 14.23f)
    cubicTo(57.81f, 14.36f, 57.91f, 14.46f, 58.04f, 14.46f)
    lineTo(59.44f, 14.46f)
    cubicTo(59.57f, 14.46f, 59.67f, 14.35f, 59.67f, 14.23f)
    lineTo(59.67f, 13.5f)
    cubicTo(60.38f, 14.25f, 61.37f, 14.68f, 62.4f, 14.68f)
    cubicTo(64.28f, 14.68f, 65.85f, 13.27f, 66.24f, 11.39f)
    cubicTo(66.37f, 10.79f, 66.37f, 10.18f, 66.24f, 9.59f)
    lineTo(66.24f, 9.59f)
    close()
    moveTo(62.08f, 13f)
    cubicTo(60.76f, 13f, 59.69f, 11.89f, 59.67f, 10.52f)
    lineTo(59.67f, 10.46f)
    cubicTo(59.69f, 9.08f, 60.76f, 7.98f, 62.08f, 7.98f)
    cubicTo(63.4f, 7.98f, 64.5f, 9.1f, 64.5f, 10.49f)
    cubicTo(64.5f, 11.88f, 63.41f, 13f, 62.08f, 13f)
    close()
  }

  // -------------------------------------------------------------------------
  // Bezier #3 — "o" outer (iOS primaryColor → contentColor)
  // -------------------------------------------------------------------------
  private val bezier3 = Path().apply {
    moveTo(71.67f, 6.32f)
    cubicTo(69.69f, 6.31f, 67.95f, 7.67f, 67.51f, 9.61f)
    cubicTo(67.38f, 10.2f, 67.38f, 10.8f, 67.51f, 11.38f)
    cubicTo(67.95f, 13.32f, 69.68f, 14.7f, 71.68f, 14.68f)
    cubicTo(73.68f, 14.68f, 75.94f, 12.81f, 75.94f, 10.49f)
    cubicTo(75.94f, 8.17f, 74.04f, 6.32f, 71.67f, 6.32f)
    close()
    moveTo(71.65f, 13.01f)
    cubicTo(70.32f, 13.01f, 69.23f, 11.89f, 69.23f, 10.5f)
    cubicTo(69.23f, 9.11f, 70.31f, 7.98f, 71.65f, 7.98f)
    cubicTo(72.98f, 7.98f, 74.07f, 9.1f, 74.07f, 10.49f)
    cubicTo(74.07f, 11.88f, 72.99f, 13f, 71.65f, 13.01f)
    lineTo(71.65f, 13.01f)
    close()
  }

  // -------------------------------------------------------------------------
  // Bezier #4 — "b" inner counter (iOS accentColor → detailColor)
  // -------------------------------------------------------------------------
  private val bezier4 = Path().apply {
    moveTo(62.08f, 7.98f)
    cubicTo(60.76f, 7.98f, 59.69f, 9.09f, 59.67f, 10.46f)
    lineTo(59.67f, 10.52f)
    cubicTo(59.68f, 11.9f, 60.75f, 13f, 62.08f, 13f)
    cubicTo(63.41f, 13f, 64.5f, 11.88f, 64.5f, 10.49f)
    cubicTo(64.5f, 9.1f, 63.41f, 7.98f, 62.08f, 7.98f)
    close()
    moveTo(62.08f, 11.76f)
    cubicTo(61.45f, 11.76f, 60.94f, 11.2f, 60.91f, 10.51f)
    lineTo(60.91f, 10.47f)
    cubicTo(60.92f, 9.78f, 61.45f, 9.22f, 62.08f, 9.22f)
    cubicTo(62.71f, 9.22f, 63.25f, 9.79f, 63.25f, 10.49f)
    cubicTo(63.24f, 11.2f, 62.73f, 11.76f, 62.08f, 11.76f)
    close()
  }

  // -------------------------------------------------------------------------
  // Bezier #5 — "o" inner counter (iOS accentColor → detailColor)
  // -------------------------------------------------------------------------
  private val bezier5 = Path().apply {
    moveTo(71.65f, 7.98f)
    cubicTo(70.32f, 7.98f, 69.23f, 9.1f, 69.23f, 10.49f)
    cubicTo(69.23f, 11.88f, 70.32f, 13f, 71.65f, 13f)
    cubicTo(72.98f, 13f, 74.07f, 11.88f, 74.07f, 10.49f)
    cubicTo(74.07f, 9.1f, 72.99f, 7.98f, 71.65f, 7.98f)
    close()
    moveTo(71.65f, 11.76f)
    cubicTo(71.01f, 11.76f, 70.48f, 11.19f, 70.48f, 10.49f)
    cubicTo(70.48f, 9.79f, 71.01f, 9.23f, 71.65f, 9.23f)
    cubicTo(72.29f, 9.23f, 72.82f, 9.8f, 72.82f, 10.5f)
    cubicTo(72.82f, 11.21f, 72.29f, 11.76f, 71.65f, 11.76f)
    close()
  }

  // -------------------------------------------------------------------------
  // Bezier #6 — "a" outer (iOS primaryColor → contentColor)
  // -------------------------------------------------------------------------
  private val bezier6 = Path().apply {
    moveTo(45.74f, 6.53f)
    lineTo(44.34f, 6.53f)
    cubicTo(44.21f, 6.53f, 44.11f, 6.64f, 44.11f, 6.76f)
    lineTo(44.11f, 7.49f)
    cubicTo(43.4f, 6.74f, 42.41f, 6.31f, 41.38f, 6.31f)
    cubicTo(39.21f, 6.31f, 37.44f, 8.18f, 37.44f, 10.5f)
    cubicTo(37.44f, 12.82f, 39.21f, 14.69f, 41.38f, 14.69f)
    cubicTo(42.42f, 14.69f, 43.41f, 14.26f, 44.11f, 13.5f)
    lineTo(44.11f, 14.23f)
    cubicTo(44.11f, 14.36f, 44.21f, 14.46f, 44.34f, 14.46f)
    lineTo(45.74f, 14.46f)
    cubicTo(45.87f, 14.46f, 45.97f, 14.35f, 45.97f, 14.23f)
    lineTo(45.97f, 6.74f)
    cubicTo(45.97f, 6.62f, 45.88f, 6.52f, 45.75f, 6.52f)
    cubicTo(45.75f, 6.53f, 45.75f, 6.53f, 45.74f, 6.53f)
    close()
    moveTo(44.12f, 10.53f)
    cubicTo(44.11f, 11.9f, 43.03f, 13f, 41.71f, 13f)
    cubicTo(40.39f, 13f, 39.29f, 11.88f, 39.29f, 10.49f)
    cubicTo(39.29f, 9.1f, 40.37f, 7.97f, 41.69f, 7.97f)
    cubicTo(43.02f, 7.97f, 44.08f, 9.08f, 44.1f, 10.45f)
    lineTo(44.12f, 10.53f)
    close()
  }

  // -------------------------------------------------------------------------
  // Bezier #7 — "a" inner counter (iOS accentColor → detailColor)
  // -------------------------------------------------------------------------
  private val bezier7 = Path().apply {
    moveTo(41.71f, 7.98f)
    cubicTo(40.38f, 7.98f, 39.29f, 9.1f, 39.29f, 10.49f)
    cubicTo(39.29f, 11.88f, 40.37f, 13f, 41.71f, 13f)
    cubicTo(43.05f, 13f, 44.1f, 11.89f, 44.12f, 10.52f)
    lineTo(44.12f, 10.46f)
    cubicTo(44.1f, 9.09f, 43.03f, 7.98f, 41.71f, 7.98f)
    close()
    moveTo(40.55f, 10.49f)
    cubicTo(40.55f, 9.79f, 41.07f, 9.22f, 41.72f, 9.22f)
    cubicTo(42.36f, 9.22f, 42.86f, 9.78f, 42.89f, 10.47f)
    lineTo(42.89f, 10.51f)
    cubicTo(42.88f, 11.19f, 42.36f, 11.75f, 41.72f, 11.75f)
    cubicTo(41.08f, 11.75f, 40.55f, 11.19f, 40.55f, 10.49f)
    close()
  }

  // -------------------------------------------------------------------------
  // Bezier #8 — "p" outer (iOS primaryColor → contentColor)
  // -------------------------------------------------------------------------
  private val bezier8 = Path().apply {
    moveTo(52.41f, 6.32f)
    cubicTo(51.38f, 6.32f, 50.38f, 6.74f, 49.68f, 7.5f)
    lineTo(49.68f, 6.75f)
    cubicTo(49.68f, 6.62f, 49.58f, 6.52f, 49.45f, 6.52f)
    lineTo(48.05f, 6.52f)
    cubicTo(47.92f, 6.52f, 47.82f, 6.63f, 47.82f, 6.75f)
    lineTo(47.82f, 17.47f)
    cubicTo(47.82f, 17.6f, 47.92f, 17.7f, 48.05f, 17.7f)
    lineTo(49.45f, 17.7f)
    cubicTo(49.58f, 17.7f, 49.68f, 17.6f, 49.68f, 17.47f)
    lineTo(49.68f, 13.5f)
    cubicTo(50.39f, 14.25f, 51.38f, 14.68f, 52.42f, 14.68f)
    cubicTo(54.59f, 14.68f, 56.36f, 12.81f, 56.36f, 10.49f)
    cubicTo(56.36f, 8.17f, 54.58f, 6.32f, 52.41f, 6.32f)
    close()
    moveTo(52.08f, 13.01f)
    cubicTo(50.76f, 13.01f, 49.69f, 11.9f, 49.66f, 10.53f)
    lineTo(49.66f, 10.46f)
    cubicTo(49.68f, 9.08f, 50.75f, 7.97f, 52.06f, 7.97f)
    cubicTo(53.38f, 7.97f, 54.47f, 9.09f, 54.47f, 10.48f)
    cubicTo(54.47f, 11.87f, 53.4f, 13f, 52.08f, 13.01f)
    lineTo(52.08f, 13.01f)
    close()
  }

  // -------------------------------------------------------------------------
  // Bezier #9 — "p" inner counter (iOS accentColor → detailColor)
  // -------------------------------------------------------------------------
  private val bezier9 = Path().apply {
    moveTo(52.08f, 7.98f)
    cubicTo(50.76f, 7.98f, 49.69f, 9.09f, 49.66f, 10.46f)
    lineTo(49.66f, 10.52f)
    cubicTo(49.69f, 11.9f, 50.76f, 13f, 52.08f, 13f)
    cubicTo(53.4f, 13f, 54.49f, 11.88f, 54.49f, 10.49f)
    cubicTo(54.49f, 9.1f, 53.4f, 7.98f, 52.08f, 7.98f)
    close()
    moveTo(52.08f, 11.76f)
    cubicTo(51.45f, 11.76f, 50.94f, 11.2f, 50.91f, 10.51f)
    lineTo(50.91f, 10.47f)
    cubicTo(50.92f, 9.78f, 51.45f, 9.22f, 52.08f, 9.22f)
    cubicTo(52.71f, 9.22f, 53.25f, 9.8f, 53.25f, 10.49f)
    cubicTo(53.25f, 11.18f, 52.72f, 11.76f, 52.08f, 11.76f)
    close()
  }

  // -------------------------------------------------------------------------
  // Bezier #10 — "m" letter (iOS primaryColor → contentColor)
  // -------------------------------------------------------------------------
  private val bezier10 = Path().apply {
    moveTo(36.08f, 14.24f)
    cubicTo(36.08f, 14.37f, 35.98f, 14.47f, 35.85f, 14.47f)
    lineTo(34.44f, 14.47f)
    cubicTo(34.31f, 14.47f, 34.21f, 14.36f, 34.21f, 14.24f)
    lineTo(34.21f, 9.68f)
    cubicTo(34.21f, 8.7f, 33.47f, 7.97f, 32.59f, 7.97f)
    cubicTo(31.79f, 7.97f, 31.13f, 8.67f, 31f, 9.59f)
    lineTo(31.01f, 14.25f)
    cubicTo(31.01f, 14.38f, 30.9f, 14.48f, 30.78f, 14.48f)
    lineTo(29.37f, 14.48f)
    cubicTo(29.24f, 14.48f, 29.14f, 14.37f, 29.14f, 14.25f)
    lineTo(29.14f, 9.68f)
    cubicTo(29.14f, 8.7f, 28.4f, 7.97f, 27.52f, 7.97f)
    cubicTo(26.67f, 7.97f, 25.98f, 8.76f, 25.92f, 9.77f)
    lineTo(25.92f, 14.25f)
    cubicTo(25.92f, 14.38f, 25.82f, 14.48f, 25.69f, 14.48f)
    lineTo(24.29f, 14.48f)
    cubicTo(24.16f, 14.48f, 24.06f, 14.37f, 24.06f, 14.25f)
    lineTo(24.06f, 6.74f)
    cubicTo(24.07f, 6.61f, 24.16f, 6.52f, 24.29f, 6.52f)
    lineTo(25.69f, 6.52f)
    cubicTo(25.82f, 6.52f, 25.91f, 6.63f, 25.92f, 6.74f)
    lineTo(25.92f, 7.4f)
    cubicTo(26.42f, 6.72f, 27.22f, 6.31f, 28.08f, 6.3f)
    lineTo(28.11f, 6.3f)
    cubicTo(29.2f, 6.3f, 30.2f, 6.9f, 30.71f, 7.85f)
    cubicTo(31.16f, 6.9f, 32.11f, 6.3f, 33.15f, 6.29f)
    cubicTo(34.77f, 6.29f, 36.08f, 7.54f, 36.08f, 9.07f)
    lineTo(36.08f, 14.24f)
    close()
  }

  // -------------------------------------------------------------------------
  // Bezier #11 — compound outline / halo (iOS accentColor → detailColor)
  // This large compound path encompasses all letterforms and is drawn last.
  // -------------------------------------------------------------------------
  private val bezier11 = Path().apply {
    fillType = Path.FillType.EVEN_ODD
    moveTo(84.34f, 13.59f)
    lineTo(84.27f, 13.46f)
    lineTo(82.31f, 10.47f)
    lineTo(84.25f, 7.52f)
    cubicTo(84.69f, 6.85f, 84.51f, 5.96f, 83.84f, 5.5f)
    cubicTo(83.82f, 5.5f, 83.81f, 5.5f, 83.8f, 5.49f)
    cubicTo(83.57f, 5.34f, 83.3f, 5.27f, 83.02f, 5.27f)
    lineTo(81.41f, 5.27f)
    cubicTo(80.85f, 5.27f, 80.33f, 5.56f, 80.04f, 6.05f)
    lineTo(79.72f, 6.6f)
    lineTo(79.38f, 6.04f)
    cubicTo(79.09f, 5.56f, 78.57f, 5.27f, 78f, 5.27f)
    lineTo(76.4f, 5.27f)
    cubicTo(75.8f, 5.27f, 75.27f, 5.64f, 75.05f, 6.19f)
    cubicTo(72.86f, 4.53f, 69.77f, 4.72f, 67.79f, 6.64f)
    cubicTo(67.44f, 6.98f, 67.14f, 7.36f, 66.9f, 7.78f)
    cubicTo(66f, 6.16f, 64.32f, 5.06f, 62.4f, 5.06f)
    cubicTo(61.9f, 5.06f, 61.39f, 5.13f, 60.92f, 5.29f)
    lineTo(60.92f, 3.51f)
    cubicTo(60.92f, 2.69f, 60.26f, 2.03f, 59.45f, 2.03f)
    lineTo(58.05f, 2.03f)
    cubicTo(57.24f, 2.03f, 56.58f, 2.69f, 56.58f, 3.5f)
    lineTo(56.58f, 7.25f)
    cubicTo(55.63f, 5.89f, 54.08f, 5.07f, 52.41f, 5.06f)
    cubicTo(51.67f, 5.06f, 50.95f, 5.22f, 50.29f, 5.53f)
    cubicTo(50.05f, 5.36f, 49.75f, 5.27f, 49.45f, 5.27f)
    lineTo(48.05f, 5.27f)
    cubicTo(47.6f, 5.27f, 47.18f, 5.48f, 46.9f, 5.83f)
    cubicTo(46.88f, 5.8f, 46.86f, 5.78f, 46.83f, 5.75f)
    cubicTo(46.55f, 5.45f, 46.15f, 5.28f, 45.74f, 5.28f)
    lineTo(44.35f, 5.28f)
    cubicTo(44.05f, 5.28f, 43.75f, 5.37f, 43.51f, 5.54f)
    cubicTo(42.84f, 5.24f, 42.12f, 5.08f, 41.39f, 5.08f)
    cubicTo(39.56f, 5.08f, 37.96f, 6.08f, 37.02f, 7.58f)
    cubicTo(36.82f, 7.12f, 36.54f, 6.69f, 36.19f, 6.33f)
    cubicTo(35.39f, 5.52f, 34.3f, 5.08f, 33.17f, 5.08f)
    lineTo(33.16f, 5.08f)
    cubicTo(32.27f, 5.09f, 31.41f, 5.41f, 30.7f, 5.96f)
    cubicTo(29.96f, 5.39f, 29.06f, 5.08f, 28.13f, 5.08f)
    lineTo(28.1f, 5.08f)
    cubicTo(27.81f, 5.08f, 27.52f, 5.11f, 27.24f, 5.19f)
    cubicTo(26.96f, 5.25f, 26.68f, 5.35f, 26.42f, 5.47f)
    cubicTo(26.21f, 5.35f, 25.97f, 5.29f, 25.72f, 5.29f)
    lineTo(24.32f, 5.29f)
    cubicTo(23.5f, 5.29f, 22.85f, 5.95f, 22.85f, 6.76f)
    lineTo(22.85f, 14.26f)
    cubicTo(22.85f, 15.08f, 23.51f, 15.73f, 24.32f, 15.73f)
    lineTo(25.72f, 15.73f)
    cubicTo(26.54f, 15.73f, 27.2f, 15.07f, 27.2f, 14.25f)
    lineTo(27.2f, 14.25f)
    lineTo(27.2f, 9.79f)
    cubicTo(27.23f, 9.43f, 27.43f, 9.2f, 27.56f, 9.2f)
    cubicTo(27.74f, 9.2f, 27.94f, 9.38f, 27.94f, 9.67f)
    lineTo(27.94f, 14.24f)
    cubicTo(27.94f, 15.06f, 28.6f, 15.71f, 29.41f, 15.71f)
    lineTo(30.82f, 15.71f)
    cubicTo(31.64f, 15.71f, 32.29f, 15.05f, 32.29f, 14.24f)
    lineTo(32.28f, 9.67f)
    cubicTo(32.34f, 9.35f, 32.53f, 9.2f, 32.63f, 9.2f)
    cubicTo(32.81f, 9.2f, 33.01f, 9.38f, 33.01f, 9.67f)
    lineTo(33.01f, 14.24f)
    cubicTo(33.01f, 15.06f, 33.67f, 15.71f, 34.48f, 15.71f)
    lineTo(35.89f, 15.71f)
    cubicTo(36.71f, 15.71f, 37.36f, 15.05f, 37.36f, 14.24f)
    lineTo(37.36f, 13.86f)
    cubicTo(38.32f, 15.15f, 39.82f, 15.92f, 41.42f, 15.92f)
    cubicTo(42.16f, 15.92f, 42.88f, 15.76f, 43.54f, 15.45f)
    cubicTo(43.78f, 15.62f, 44.08f, 15.71f, 44.38f, 15.71f)
    lineTo(45.77f, 15.71f)
    cubicTo(46.07f, 15.71f, 46.37f, 15.62f, 46.61f, 15.45f)
    lineTo(46.61f, 17.46f)
    cubicTo(46.61f, 18.28f, 47.27f, 18.93f, 48.08f, 18.93f)
    lineTo(49.48f, 18.93f)
    cubicTo(50.3f, 18.93f, 50.95f, 18.27f, 50.95f, 17.46f)
    lineTo(50.95f, 15.69f)
    cubicTo(51.43f, 15.84f, 51.94f, 15.92f, 52.44f, 15.91f)
    cubicTo(54.14f, 15.91f, 55.66f, 15.04f, 56.61f, 13.71f)
    lineTo(56.61f, 14.23f)
    cubicTo(56.61f, 15.05f, 57.27f, 15.7f, 58.08f, 15.7f)
    lineTo(59.48f, 15.7f)
    cubicTo(59.78f, 15.7f, 60.08f, 15.61f, 60.32f, 15.44f)
    cubicTo(60.98f, 15.75f, 61.71f, 15.91f, 62.44f, 15.91f)
    cubicTo(64.36f, 15.91f, 66.04f, 14.81f, 66.93f, 13.18f)
    cubicTo(68.47f, 15.83f, 71.88f, 16.71f, 74.51f, 15.16f)
    cubicTo(74.69f, 15.05f, 74.87f, 14.94f, 75.04f, 14.8f)
    cubicTo(75.26f, 15.35f, 75.8f, 15.71f, 76.39f, 15.7f)
    lineTo(78f, 15.7f)
    cubicTo(78.56f, 15.7f, 79.08f, 15.41f, 79.37f, 14.92f)
    lineTo(79.74f, 14.31f)
    lineTo(80.11f, 14.92f)
    cubicTo(80.4f, 15.4f, 80.92f, 15.7f, 81.49f, 15.7f)
    lineTo(83.09f, 15.7f)
    cubicTo(83.9f, 15.7f, 84.55f, 15.04f, 84.54f, 14.24f)
    cubicTo(84.49f, 14.02f, 84.44f, 13.8f, 84.34f, 13.59f)
    lineTo(84.34f, 13.59f)
    close()
    // --- sub-paths duplicating each letterform outline (accentColor overlay) ---
    moveTo(35.86f, 14.47f)
    lineTo(34.45f, 14.47f)
    cubicTo(34.32f, 14.47f, 34.22f, 14.36f, 34.22f, 14.24f)
    lineTo(34.22f, 9.68f)
    cubicTo(34.22f, 8.7f, 33.48f, 7.97f, 32.6f, 7.97f)
    cubicTo(31.8f, 7.97f, 31.14f, 8.67f, 31.01f, 9.59f)
    lineTo(31.02f, 14.25f)
    cubicTo(31.02f, 14.38f, 30.92f, 14.48f, 30.79f, 14.48f)
    lineTo(29.38f, 14.48f)
    cubicTo(29.25f, 14.48f, 29.15f, 14.37f, 29.15f, 14.25f)
    lineTo(29.15f, 9.68f)
    cubicTo(29.15f, 8.7f, 28.41f, 7.97f, 27.53f, 7.97f)
    cubicTo(26.68f, 7.97f, 25.99f, 8.76f, 25.93f, 9.77f)
    lineTo(25.93f, 14.25f)
    cubicTo(25.93f, 14.38f, 25.83f, 14.48f, 25.7f, 14.48f)
    lineTo(24.3f, 14.48f)
    cubicTo(24.17f, 14.48f, 24.07f, 14.37f, 24.07f, 14.25f)
    lineTo(24.07f, 6.74f)
    cubicTo(24.08f, 6.61f, 24.18f, 6.52f, 24.3f, 6.52f)
    lineTo(25.7f, 6.52f)
    cubicTo(25.83f, 6.52f, 25.92f, 6.63f, 25.93f, 6.74f)
    lineTo(25.93f, 7.4f)
    cubicTo(26.43f, 6.72f, 27.23f, 6.31f, 28.09f, 6.3f)
    lineTo(28.12f, 6.3f)
    cubicTo(29.21f, 6.3f, 30.21f, 6.9f, 30.72f, 7.85f)
    cubicTo(31.17f, 6.9f, 32.12f, 6.3f, 33.16f, 6.29f)
    cubicTo(34.78f, 6.29f, 36.09f, 7.54f, 36.06f, 9.07f)
    lineTo(36.07f, 14.23f)
    cubicTo(36.09f, 14.36f, 35.98f, 14.46f, 35.86f, 14.47f)
    lineTo(35.86f, 14.47f)
    close()
    moveTo(45.97f, 14.24f)
    cubicTo(45.97f, 14.37f, 45.87f, 14.47f, 45.74f, 14.47f)
    lineTo(44.34f, 14.47f)
    cubicTo(44.21f, 14.47f, 44.11f, 14.36f, 44.11f, 14.24f)
    lineTo(44.11f, 13.5f)
    cubicTo(43.41f, 14.26f, 42.42f, 14.68f, 41.39f, 14.68f)
    cubicTo(39.22f, 14.68f, 37.45f, 12.81f, 37.45f, 10.49f)
    cubicTo(37.45f, 8.17f, 39.22f, 6.3f, 41.39f, 6.3f)
    cubicTo(42.42f, 6.3f, 43.41f, 6.73f, 44.12f, 7.48f)
    lineTo(44.12f, 6.74f)
    cubicTo(44.12f, 6.61f, 44.22f, 6.51f, 44.35f, 6.51f)
    lineTo(45.75f, 6.51f)
    cubicTo(45.87f, 6.5f, 45.97f, 6.59f, 45.98f, 6.72f)
    cubicTo(45.98f, 6.73f, 45.98f, 6.73f, 45.98f, 6.74f)
    lineTo(45.98f, 14.25f)
    lineTo(45.97f, 14.25f)
    lineTo(45.97f, 14.24f)
    close()
    moveTo(52.41f, 14.67f)
    cubicTo(51.38f, 14.67f, 50.39f, 14.24f, 49.68f, 13.49f)
    lineTo(49.68f, 17.46f)
    cubicTo(49.68f, 17.59f, 49.58f, 17.69f, 49.45f, 17.69f)
    lineTo(48.05f, 17.69f)
    cubicTo(47.92f, 17.69f, 47.82f, 17.59f, 47.82f, 17.46f)
    lineTo(47.82f, 6.75f)
    cubicTo(47.82f, 6.62f, 47.92f, 6.53f, 48.05f, 6.53f)
    lineTo(49.45f, 6.53f)
    cubicTo(49.58f, 6.53f, 49.68f, 6.64f, 49.68f, 6.76f)
    lineTo(49.68f, 7.49f)
    cubicTo(50.39f, 6.73f, 51.38f, 6.31f, 52.41f, 6.31f)
    cubicTo(54.58f, 6.31f, 56.35f, 8.17f, 56.35f, 10.49f)
    cubicTo(56.35f, 12.81f, 54.58f, 14.67f, 52.41f, 14.67f)
    close()
    moveTo(66.24f, 11.39f)
    cubicTo(65.85f, 13.26f, 64.28f, 14.68f, 62.4f, 14.68f)
    cubicTo(61.37f, 14.68f, 60.38f, 14.25f, 59.67f, 13.5f)
    lineTo(59.67f, 14.23f)
    cubicTo(59.67f, 14.36f, 59.57f, 14.46f, 59.44f, 14.46f)
    lineTo(58.04f, 14.46f)
    cubicTo(57.91f, 14.46f, 57.81f, 14.35f, 57.81f, 14.23f)
    lineTo(57.81f, 3.51f)
    cubicTo(57.81f, 3.38f, 57.91f, 3.28f, 58.04f, 3.28f)
    lineTo(59.44f, 3.28f)
    cubicTo(59.57f, 3.28f, 59.67f, 3.39f, 59.67f, 3.51f)
    lineTo(59.67f, 7.48f)
    cubicTo(60.38f, 6.73f, 61.37f, 6.3f, 62.4f, 6.31f)
    cubicTo(64.28f, 6.31f, 65.85f, 7.71f, 66.24f, 9.59f)
    cubicTo(66.37f, 10.19f, 66.37f, 10.8f, 66.24f, 11.39f)
    lineTo(66.24f, 11.39f)
    lineTo(66.24f, 11.39f)
    close()
    moveTo(71.67f, 14.68f)
    cubicTo(69.67f, 14.69f, 67.94f, 13.33f, 67.5f, 11.38f)
    cubicTo(67.37f, 10.79f, 67.37f, 10.19f, 67.5f, 9.61f)
    cubicTo(67.94f, 7.67f, 69.67f, 6.3f, 71.67f, 6.31f)
    cubicTo(73.67f, 6.31f, 75.93f, 8.18f, 75.93f, 10.5f)
    cubicTo(75.93f, 12.82f, 74.03f, 14.68f, 71.67f, 14.68f)
    lineTo(71.67f, 14.68f)
    close()
    moveTo(83.04f, 14.47f)
    lineTo(81.43f, 14.47f)
    cubicTo(81.3f, 14.47f, 81.19f, 14.41f, 81.13f, 14.3f)
    lineTo(79.69f, 11.91f)
    lineTo(78.25f, 14.3f)
    cubicTo(78.19f, 14.41f, 78.07f, 14.47f, 77.95f, 14.47f)
    lineTo(76.34f, 14.47f)
    cubicTo(76.3f, 14.47f, 76.26f, 14.46f, 76.22f, 14.44f)
    cubicTo(76.13f, 14.38f, 76.09f, 14.25f, 76.16f, 14.16f)
    lineTo(76.16f, 14.16f)
    lineTo(78.59f, 10.48f)
    lineTo(76.2f, 6.84f)
    cubicTo(76.18f, 6.81f, 76.17f, 6.77f, 76.17f, 6.72f)
    cubicTo(76.17f, 6.6f, 76.26f, 6.51f, 76.38f, 6.51f)
    lineTo(77.99f, 6.51f)
    cubicTo(78.12f, 6.51f, 78.23f, 6.57f, 78.29f, 6.68f)
    lineTo(79.7f, 9.04f)
    lineTo(81.11f, 6.68f)
    cubicTo(81.17f, 6.57f, 81.29f, 6.51f, 81.41f, 6.51f)
    lineTo(83.02f, 6.51f)
    cubicTo(83.06f, 6.51f, 83.1f, 6.52f, 83.14f, 6.54f)
    cubicTo(83.23f, 6.6f, 83.27f, 6.73f, 83.2f, 6.82f)
    lineTo(83.2f, 6.82f)
    lineTo(80.82f, 10.46f)
    lineTo(83.25f, 14.13f)
    cubicTo(83.27f, 14.16f, 83.28f, 14.2f, 83.28f, 14.25f)
    cubicTo(83.25f, 14.38f, 83.16f, 14.47f, 83.04f, 14.47f)
    lineTo(83.04f, 14.47f)
    lineTo(83.04f, 14.47f)
    close()
  }

  init {
    setWillNotDraw(false)
  }

  /**
   * Apply the active color scheme.
   *
   * @param contentColor ARGB color for letter fills (iOS primaryColor role).
   * @param detailColor  ARGB color for inner counters and outline halo (iOS accentColor role).
   */
  fun applyColors(contentColor: Int, detailColor: Int) {
    contentPaint.color = contentColor
    detailPaint.color = detailColor
    invalidate()
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val density = resources.displayMetrics.density
    val w = (INTRINSIC_WIDTH_DP * density).toInt()
    val h = (INTRINSIC_HEIGHT_DP * density).toInt()
    setMeasuredDimension(w, h)
  }

  override fun onDraw(canvas: Canvas) {
    val saveCount = canvas.save()

    // Scale from 64×21 logical space to actual pixel dimensions, then shift
    // the 85-unit reference frame left by 21 so the letters start at x = 0.
    val scaleX = width.toFloat() / INTRINSIC_WIDTH_DP
    val scaleY = height.toFloat() / INTRINSIC_HEIGHT_DP
    canvas.scale(scaleX, scaleY)
    canvas.translate(-ICON_WIDTH_REF, 0f)

    // Drawing order mirrors the iOS source exactly.
    canvas.drawPath(bezier1, contentPaint) // x
    canvas.drawPath(bezier2, contentPaint) // b outer
    canvas.drawPath(bezier3, contentPaint) // o outer
    canvas.drawPath(bezier4, detailPaint) // b inner counter
    canvas.drawPath(bezier5, detailPaint) // o inner counter
    canvas.drawPath(bezier6, contentPaint) // a outer
    canvas.drawPath(bezier7, detailPaint) // a inner counter
    canvas.drawPath(bezier8, contentPaint) // p outer
    canvas.drawPath(bezier9, detailPaint) // p inner counter
    canvas.drawPath(bezier10, contentPaint) // m
    // bezier11 uses EVEN_ODD fill: covers the halo ring but not the letter bodies.
    canvas.drawPath(bezier11, detailPaint) // compound outline halo

    canvas.restoreToCount(saveCount)
  }

  internal companion object {
    const val INTRINSIC_WIDTH_DP = 64f
    const val INTRINSIC_HEIGHT_DP = 21f
    /** Width of the icon in the 85×21 reference frame; wordmark begins at this x offset. */
    const val ICON_WIDTH_REF = 21f
  }
}
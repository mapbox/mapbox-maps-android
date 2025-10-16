package com.mapbox.maps.extension.compose.annotation

import android.graphics.Paint.Join
import android.graphics.Paint.Style
import android.graphics.Typeface
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.text.TextUtils
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.R
import com.mapbox.maps.viewannotation.annotationAnchor
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import java.lang.ref.WeakReference

/**
 * Composable function to add a simple [Marker] to the Map.
 *
 * `Marker` is a convenience which creates a simple `ViewAnnotation` with limited customization options.
 * Use `Marker` to quickly add a pin annotation at the specific coordinates.
 * If you need greater customization use `ViewAnnotation` directly.
 *
 * `Marker` is great for displaying unique interactive features. However, it may be suboptimal for
 * large amounts of data and doesn't support clustering. Each marker creates Compose views with
 * custom text rendering, so for scenarios with 100+ markers, consider using `PointAnnotation`.
 *
 * @param point The geographic location of the Marker
 * @param color The outer color of the [Marker]
 * @param innerColor The inner color of the [Marker]
 * @param stroke The color of optional strokes. Pass null to remove the strokes.
 * @param text A text to be displayed with the [Marker]
 * @param onClick Optional callback to be invoked when the marker is clicked
 */
@Composable
@MapboxMapComposable
@MapboxExperimental
public fun Marker(
  point: Point,
  color: Color = Color(0xffcfdaf7),
  innerColor: Color = Color(0xffffffff),
  stroke: Color? = Color(0xff3a59fa),
  text: String? = null,
  onClick: (() -> Unit)? = null
) {
  val stableOptions = remember(point) {
    viewAnnotationOptions {
      geometry(point)
      annotationAnchor {
        anchor(ViewAnnotationAnchor.TOP)
        offsetY(110.0)
        allowOverlap(true)
      }
    }
  }

  ViewAnnotation(
    options = stableOptions
  ) {
    Column(
      modifier = Modifier
        .size(width = 120.dp, height = 100.dp)
        .run {
          if (onClick != null) {
            clickable(
              indication = null,
              interactionSource = remember { MutableInteractionSource() }
            ) { onClick() }
          } else {
            this
          }
        },
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Top
    ) {
      MarkerImage(color, innerColor, stroke)
      text?.let {
        MarkerText(text)
      }
    }
  }
}

@Composable
private fun MarkerImage(
  color: Color,
  innerColor: Color,
  stroke: Color?
) {
  Box(
    modifier = Modifier.height(40.dp),
    contentAlignment = Alignment.TopCenter
  ) {
    Image(
      painter = painterResource(id = R.drawable.default_marker_outer),
      contentDescription = "Shadow",
      modifier = Modifier
        .offset(y = 2.dp)
        .alpha(0.17f)
        .blur(1.dp),
      colorFilter = ColorFilter.tint(Color.Black)
    )
    Image(
      painter = painterResource(R.drawable.default_marker_outer),
      contentDescription = "Marker outer",
      colorFilter = ColorFilter.tint(color)
    )
    Image(
      painter = painterResource(R.drawable.default_marker_inner),
      contentDescription = "Marker inner",
      colorFilter = ColorFilter.tint(innerColor)
    )
    if (stroke != null) {
      Image(
        painter = painterResource(R.drawable.default_marker_outer_stroke),
        contentDescription = "Marker outer stroke",
        colorFilter = ColorFilter.tint(stroke)
      )
      Image(
        painter = painterResource(R.drawable.default_marker_inner_stroke),
        contentDescription = "Marker inner stroke",
        colorFilter = ColorFilter.tint(stroke)
      )
    }
  }
}

@Composable
private fun MarkerText(
  text: String,
  fontColor: Color = Color.Black,
  fontStrokeColor: Color = Color.White,
  fontSize: TextUnit = 15.sp,
  strokeWidth: Dp = 3.dp,
  maxLines: Int = 3,
) {
  val density = LocalDensity.current
  val fontSizePx = with(density) { fontSize.toPx() }

  val textPaint = remember(fontSize, strokeWidth, fontColor) {
    TextPaint().apply {
      isAntiAlias = true
      style = Style.FILL
      textSize = fontSizePx
      color = fontColor.toArgb()
      typeface = Fonts.mediumFont
    }
  }

  val strokePaint = remember(fontSize, strokeWidth, fontStrokeColor) {
    TextPaint().apply {
      isAntiAlias = true
      style = Style.STROKE
      textSize = fontSizePx
      color = fontStrokeColor.toArgb()
      typeface = Fonts.mediumFont
      strokeMiter = 4f
      strokeJoin = Join.ROUND
      this.strokeWidth = with(density) { strokeWidth.toPx() }
    }
  }

  Canvas(Modifier.fillMaxSize()) {
    drawIntoCanvas { canvas ->
      val layoutWidth = size.width.toInt()
      val alignment = Layout.Alignment.ALIGN_CENTER

      fun makeLayoutSdk21(paint: TextPaint) = StaticLayout(
        text, paint, layoutWidth, alignment, 1f, 0f, false
      ).let {
        if (it.lineCount <= maxLines) return@let it
        val endIndex = it.getLineEnd(maxLines - 1)
        val truncated = text.take(endIndex).trimEnd() + "…"
        StaticLayout(
          truncated, paint, layoutWidth, alignment, 1f, 0f, false
        )
      }

      fun makeLayout(paint: TextPaint) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          StaticLayout.Builder
            .obtain(text, 0, text.length, paint, layoutWidth)
            .setAlignment(alignment)
            .setEllipsize(TextUtils.TruncateAt.END)
            .setMaxLines(maxLines)
            .build()
        } else {
          makeLayoutSdk21(paint)
        }

      canvas.nativeCanvas.apply {
        makeLayout(strokePaint).draw(this)
        makeLayout(textPaint).draw(this)
      }
    }
  }
}

private object Fonts {
  private var _mediumFont = WeakReference<Typeface>(null)
  val mediumFont: Typeface
    get() = _mediumFont.get()
      ?: Typeface.create("sans-serif-medium", Typeface.NORMAL).also {
        _mediumFont = WeakReference(it)
      }
}
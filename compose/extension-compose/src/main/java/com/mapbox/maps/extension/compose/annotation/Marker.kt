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
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
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
 * Markers support animations via two parameters:
 * - `appearAnimation`: Effects that play when the marker first appears
 * - `disappearAnimation`: Effects that play when the marker is removed
 *
 * Available effects: [MarkerAnimationEffect.wiggle], [MarkerAnimationEffect.scale], [MarkerAnimationEffect.fadeIn], [MarkerAnimationEffect.fadeOut]
 *
 * Example:
 * ```
 * Marker(
 *   point = point,
 *   appearAnimation = listOf(MarkerAnimationEffect.wiggle, MarkerAnimationEffect.scale),
 *   disappearAnimation = listOf(MarkerAnimationEffect.fadeOut),
 *   onClick = { println("Marker clicked!") }
 * )
 * ```
 *
 * @param point The geographic location of the Marker
 * @param color The outer color of the [Marker]
 * @param innerColor The inner color of the [Marker]
 * @param stroke The color of optional strokes. Pass null to remove the strokes.
 * @param text A text to be displayed with the [Marker]
 * @param appearAnimation Effects to play when marker appears
 * @param disappearAnimation Effects to play when marker disappears
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
  appearAnimation: List<MarkerAnimationEffect> = emptyList(),
  disappearAnimation: List<MarkerAnimationEffect> = emptyList(),
  onClick: (() -> Unit)? = null
) {
  val animationConfigs = remember(appearAnimation, disappearAnimation) {
    buildMap {
      if (appearAnimation.isNotEmpty()) {
        put(
          MarkerAnimationTrigger.Appear,
          MarkerAnimationConfig(
            appearAnimation.map { it.value }
          )
        )
      }
      if (disappearAnimation.isNotEmpty()) {
        put(
          MarkerAnimationTrigger.Disappear,
          MarkerAnimationConfig(
            disappearAnimation.map { it.value }
          )
        )
      }
    }
  }

  val animationState = remember(animationConfigs) {
    if (animationConfigs.isEmpty()) {
      null
    } else {
      MarkerAnimationState(
        appearConfig = animationConfigs[MarkerAnimationTrigger.Appear],
        disappearConfig = animationConfigs[MarkerAnimationTrigger.Disappear]
      )
    }
  }

  val density = LocalDensity.current
  val offsetAdjustment = remember(density) {
    with(density) { 50.dp.toPx() }
  }

  val stableOptions = remember(point, offsetAdjustment) {
    viewAnnotationOptions {
      geometry(point)
      annotationAnchor {
        anchor(ViewAnnotationAnchor.TOP)
        offsetY(120.0 + offsetAdjustment)
        allowOverlap(true)
      }
    }
  }

  ViewAnnotation(
    options = stableOptions
  ) {
    // Set disappear animation config as a view tag so ViewAnnotationNode.onRemoved() can read it
    val view = LocalView.current
    val disappearConfig = animationState?.disappearConfig

    // Cache the ComposeView reference to avoid walking the parent tree on every composition
    val composeView = remember(view) {
      var targetView: android.view.View? = view
      while (targetView != null && targetView !is ComposeView) {
        targetView = targetView.parent as? android.view.View
      }
      targetView
    }

    // Use SideEffect to set the tag after each composition
    // This ensures the tag is always up-to-date when the marker is removed
    SideEffect {
      composeView?.setTag(R.id.markerDisappearAnimation, disappearConfig)
    }

    Box(
      modifier = Modifier
        .size(200.dp)
        .graphicsLayer(clip = false),
      contentAlignment = Alignment.Center
    ) {
      MarkerContent(
        modifier = Modifier,
        color = color,
        innerColor = innerColor,
        stroke = stroke,
        text = text,
        animationState = animationState,
        onClick = onClick
      )
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
  maxWidth: Dp = 120.dp
) {
  val density = LocalDensity.current
  val fontSizePx = with(density) { fontSize.toPx() }
  val maxWidthPx = with(density) { maxWidth.toPx().toInt() }

  val textPaint = remember(fontSize, fontColor) {
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

  // Pre-calculate layout once to get actual dimensions
  // We'll draw with both paints using the same layout structure
  val textLayout = remember(text, textPaint, maxWidthPx, maxLines) {
    createTextLayout(text, textPaint, maxWidthPx, maxLines)
  }

  // Calculate actual dimensions from layout
  val actualWidth = with(density) { textLayout.width.toDp() }
  val actualHeight = with(density) { textLayout.height.toDp() }

  Canvas(
    Modifier
      .width(actualWidth)
      .height(actualHeight)
  ) {
    drawIntoCanvas { canvas ->
      canvas.nativeCanvas.apply {
        // Draw stroke first (using same layout with different paint)
        // We iterate through lines and draw each with the stroke paint
        for (i in 0 until textLayout.lineCount) {
          val lineStart = textLayout.getLineStart(i)
          val lineEnd = textLayout.getLineEnd(i)
          val lineText = text.substring(lineStart, lineEnd)
          val x = textLayout.getLineLeft(i)
          val y = textLayout.getLineBaseline(i).toFloat()
          drawText(lineText, x, y, strokePaint)
        }
        // Draw text on top with the original layout
        textLayout.draw(this)
      }
    }
  }
}

/**
 * Helper function to create a StaticLayout with proper truncation.
 * Extracted to avoid recreation on every composition.
 */
private fun createTextLayout(
  text: String,
  paint: TextPaint,
  width: Int,
  maxLines: Int
): StaticLayout {
  val alignment = Layout.Alignment.ALIGN_CENTER

  fun makeLayoutSdk21() = StaticLayout(
    text, paint, width, alignment, 1f, 0f, false
  ).let {
    if (it.lineCount <= maxLines) return@let it
    val endIndex = it.getLineEnd(maxLines - 1)
    val truncated = text.take(endIndex).trimEnd() + "…"
    StaticLayout(
      truncated, paint, width, alignment, 1f, 0f, false
    )
  }

  return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    StaticLayout.Builder
      .obtain(text, 0, text.length, paint, width)
      .setAlignment(alignment)
      .setEllipsize(TextUtils.TruncateAt.END)
      .setMaxLines(maxLines)
      .build()
  } else {
    makeLayoutSdk21()
  }
}

private object Fonts {
  val mediumFont: Typeface by lazy {
    Typeface.create("sans-serif-medium", Typeface.NORMAL)
  }
}

@Composable
private fun MarkerContent(
  modifier: Modifier,
  color: Color,
  innerColor: Color,
  stroke: Color?,
  text: String?,
  animationState: MarkerAnimationState?,
  onClick: (() -> Unit)?
) {
  CompositionLocalProvider(LocalIndication provides NoIndication) {
    Box(
      modifier = Modifier
        .size(width = 120.dp, height = 100.dp)
        .graphicsLayer(clip = false),
      contentAlignment = Alignment.TopCenter
    ) {
      val animScale = animationState?.animatedScale()?.value ?: 1f
      val animAlpha = animationState?.animatedAlpha()?.value ?: 1f
      val animRotation = animationState?.animatedRotation()?.value ?: 0f

      Column(
        modifier = modifier
          .wrapContentSize()
          .graphicsLayer {
            clip = false
          }
          .clickable(
            enabled = onClick != null,
            indication = null,
            interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
          ) {
            onClick?.invoke()
          },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
      ) {
        // Animated pin
        Box(
          modifier = Modifier.graphicsLayer {
            scaleX = animScale
            scaleY = animScale
            rotationZ = animRotation
            alpha = animAlpha
            transformOrigin = TransformOrigin(0.5f, 1f)
            clip = false
          }
        ) {
          MarkerImage(color, innerColor, stroke)

          if (animationState != null) {
            LaunchedEffect(animationState) {
              val hasWiggle = animationState.animateAppear()
              if (hasWiggle) {
                animationState.animateWiggle()
              }
            }
          }
        }

        text?.let {
          MarkerText(text)
        }
      }
    }
  }
}

/**
 * Empty indication that disables visual feedback (ripple effects) for markers.
 */
private object NoIndication : Indication {
  @Composable
  override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
    return object : IndicationInstance {
      override fun androidx.compose.ui.graphics.drawscope.ContentDrawScope.drawIndication() {
        drawContent()
      }
    }
  }
}
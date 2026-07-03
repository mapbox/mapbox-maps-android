package com.mapbox.maps.testapp.examples.markersandcallouts.viewannotation

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.LinearLayout
import androidx.core.graphics.withTranslation
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.viewannotation.mbxViewAnnotationCollisionBox

/**
 * PinView renders a pin shape (gradient-filled teardrop with hole) and a label below it.
 * Both the pin and the label are marked as [mbxViewAnnotationCollisionBox] so they can collide
 * independently with map symbols.
 */
@OptIn(MapboxExperimental::class)
class PinView(context: Context, text: String) : LinearLayout(context) {

  /** Y offset (px, from view top) of the pin's tip — i.e. the bottom of the pin shape. */
  val pinTipY: Int

  init {
    val d = resources.displayMetrics.density
    val pinSize = (35 * d).toInt()
    pinTipY = pinSize * 3 / 2
    layoutParams = ViewGroup.LayoutParams(
      ViewGroup.LayoutParams.WRAP_CONTENT,
      ViewGroup.LayoutParams.WRAP_CONTENT
    )
    orientation = VERTICAL
    gravity = Gravity.CENTER_HORIZONTAL
    clipChildren = false
    clipToPadding = false
    addView(
      PinShapeView(context),
      LayoutParams(pinSize, pinTipY).apply { bottomMargin = (3 * d).toInt() }
    )
    addView(
      StrokedTextView(context).apply {
        this.text = text
        setTextColor(Color.RED)
        textSize = 13.5f
        setTypeface(typeface, Typeface.BOLD)
        gravity = Gravity.CENTER
        maxWidth = (130 * d).toInt()
        mbxViewAnnotationCollisionBox = true
      },
      LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    )
  }

  private class StrokedTextView(context: Context) : androidx.appcompat.widget.AppCompatTextView(context) {
    private val strokeColor = Color.WHITE

    override fun onDraw(canvas: Canvas) {
      val textLayout = layout ?: return super.onDraw(canvas)
      val p = paint
      val originalStyle = p.style
      val originalStrokeWidth = p.strokeWidth
      canvas.withTranslation(totalPaddingLeft.toFloat(), totalPaddingTop.toFloat()) {
        // Stroke pass: white outline. We bypass super.onDraw + setTextColor to avoid the
        // invalidate() that setTextColor triggers, which would cause a 60fps redraw loop.
        p.style = Paint.Style.STROKE
        p.strokeWidth = resources.displayMetrics.density
        p.color = strokeColor
        textLayout.draw(this)
        // Fill pass: original text color on top of the outline.
        p.style = originalStyle
        p.strokeWidth = originalStrokeWidth
        p.color = currentTextColor
        textLayout.draw(this)
      }
    }
  }

  private class PinShapeView(context: Context) : View(context) {
    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
      style = Paint.Style.STROKE
      strokeWidth = resources.displayMetrics.density
      color = Color.argb((0.4f * 255).toInt(), 255, 255, 255)
    }
    private val path = Path().apply { fillType = Path.FillType.EVEN_ODD }

    init {
      mbxViewAnnotationCollisionBox = true
      fillPaint.setShadowLayer(10f, 0f, 0f, Color.argb(128, 0, 0, 0))
      scaleX = 0f
      scaleY = 0f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
      val width = w.toFloat()
      val height = h.toFloat()
      val r = width / 2
      val angle = Math.PI / 4
      val sx = (r + r * Math.cos(angle)).toFloat()
      val sy = (r + r * Math.sin(angle)).toFloat()
      path.reset()
      path.moveTo(sx, sy)
      path.arcTo(RectF(0f, 0f, width, width), 45f, -270f, true)
      path.lineTo(r, height)
      path.lineTo(sx, sy)
      path.addCircle(r, r, r / 3, Path.Direction.CCW)
      fillPaint.shader = LinearGradient(
        0f, 0f, 0f, height, Color.RED, Color.BLUE, Shader.TileMode.CLAMP
      )
      pivotX = r
      pivotY = height
      ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 400
        interpolator = OvershootInterpolator()
        addUpdateListener {
          val v = it.animatedValue as Float
          scaleX = v
          scaleY = v
        }
        start()
      }
    }

    override fun onDraw(canvas: Canvas) {
      canvas.drawPath(path, fillPaint)
      canvas.drawPath(path, strokePaint)
    }
  }
}
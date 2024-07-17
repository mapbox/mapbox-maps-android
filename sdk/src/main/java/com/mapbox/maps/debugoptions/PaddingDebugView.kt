package com.mapbox.maps.debugoptions

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.graphics.ColorUtils
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.toDP

internal class PaddingDebugView(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
  private val paddingFrame = FrameLayout(context)
  private val top = TextView(context)
  private val left = TextView(context)
  private val right = TextView(context)
  private val bottom = TextView(context)

  init {
    val paddingDrawable = GradientDrawable()
    paddingDrawable.setColor(Color.TRANSPARENT)
    paddingDrawable.setStroke(1f.toDP(context).toInt(), GREEN_DARK)
    paddingFrame.background = paddingDrawable
    paddingFrame.layoutParams = LayoutParams(
      LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT
    )

    val crossView = CrossView(context)
    crossView.layoutParams =
      LayoutParams(15f.toDP(context).toInt(), 15f.toDP(context).toInt()).also {
        it.gravity = Gravity.CENTER
      }

    for (textView in listOf(top, left, right, bottom)) {
      textView.typeface = Typeface.MONOSPACE
      textView.textSize = 10f
      textView.setTextColor(Color.WHITE)
      textView.setBackgroundColor(GREEN_DARK)
      textView.layoutParams =
        LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).also {
          when (textView) {
            top -> it.gravity = Gravity.CENTER_HORIZONTAL
            left -> it.gravity = Gravity.CENTER_VERTICAL
            right -> it.gravity = Gravity.CENTER_VERTICAL or Gravity.RIGHT
            bottom -> it.gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
          }
          it.leftMargin = 1f.toDP(context).toInt()
          it.rightMargin = 1f.toDP(context).toInt()
        }
      textView.setPadding(1f.toDP(context).toInt(), 0, 1f.toDP(context).toInt(), 0)
      paddingFrame.addView(textView)
    }

    paddingFrame.addView(crossView)
    addView(paddingFrame)
  }

  @SuppressLint("SetTextI18n")
  fun update(padding: EdgeInsets) {
    paddingFrame.layoutParams = (paddingFrame.layoutParams as LayoutParams).apply {
      this.setMargins(
        padding.left.toDP(context).toInt(),
        padding.top.toDP(context).toInt(),
        padding.right.toDP(context).toInt(),
        padding.bottom.toDP(context).toInt()
      )
    }
    top.text = "%.1f".format(padding.top)
    left.text = "%.1f".format(padding.left)
    right.text = "%.1f".format(padding.right)
    bottom.text = "%.1f".format(padding.bottom)
  }
}
internal class CrossView(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
  private val greenPaint = Paint(0).apply {
    color = GREEN_DARK
    strokeWidth = 1f.toDP(context)
  }

  override fun onDraw(canvas: Canvas?) {
    canvas?.apply {
      val midY = height / 2f
      val midX = width / 2f
      drawLine(0f, midY, width.toFloat(), midY, greenPaint)
      drawLine(midX, 0f, midX, height.toFloat(), greenPaint)
    }
  }
}

private val GREEN_DARK = ColorUtils.blendARGB(Color.GREEN, Color.BLACK, 0.25f)
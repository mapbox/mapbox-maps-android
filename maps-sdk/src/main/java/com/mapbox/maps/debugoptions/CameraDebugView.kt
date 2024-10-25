package com.mapbox.maps.debugoptions

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.bold
import com.mapbox.maps.CameraState
import com.mapbox.maps.toDP

internal class CameraDebugView(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

  init {
    setTypeface(Typeface.MONOSPACE)
    val padding = 4f.toDP(context).toInt()
    textSize = 13f
    setPadding(padding, padding, padding, padding)
    setTextColor(Color.BLACK)
    val leftMargin = 8f.toDP(context).toInt()
    val topMargin = 48f.toDP(context).toInt()
    layoutParams = FrameLayout.LayoutParams(
      FrameLayout.LayoutParams.WRAP_CONTENT,
      FrameLayout.LayoutParams.WRAP_CONTENT
    ).also { it.setMargins(leftMargin, topMargin, 0, 0) }

    val cornerRadius = 5f.toDP(context)
    val drawable = GradientDrawable()
    drawable.cornerRadius = cornerRadius
    drawable.setColor(Color.WHITE)
    background = drawable
  }

  fun update(cameraState: CameraState) {
    val string = SpannableStringBuilder()
      .bold { append("lat:") }.append(" %.4f".format(cameraState.center.latitude()))
      .append("\n")
      .bold { append("lon:") }.append(" %.4f".format(cameraState.center.longitude()))
      .append("\n")
      .bold { append("zoom:") }.append(" %.2f".format(cameraState.zoom))

    if (cameraState.bearing != 0.0) {
      string.append("\n")
      string.bold { append("bearing:") }.append(" %.2f".format(cameraState.bearing))
    }
    if (cameraState.pitch != 0.toDouble()) {
      string.append("\n")
      string.bold { append("pitch:") }.append(" %.2f".format(cameraState.pitch))
    }

    text = string
  }
}
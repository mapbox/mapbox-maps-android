package com.mapbox.maps.testapp.examples.markersandcallouts

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.MarkerViewBinding

class MarkerView
@JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private var rotation: Int = 0

  private val binding: MarkerViewBinding
  private val container: View

  init {
    val view = inflate(context, R.layout.marker_view, this)
    binding = MarkerViewBinding.bind(view)
    container = binding.root
  }

  fun setAnchor(anchor: IconAnchor) {
    binding.tvNumber.text = anchor.name
  }

  fun render(): Bitmap {
    val measureSpec: Int = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
    container.measure(measureSpec, measureSpec)
    var measuredWidth: Int = container.measuredWidth
    var measuredHeight: Int = container.measuredHeight
    container.layout(0, 0, measuredWidth, measuredHeight)
    if (rotation == 1 || rotation == 3) {
      measuredHeight = container.measuredWidth
      measuredWidth = container.measuredHeight
    }
    val bitmap: Bitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
    bitmap.eraseColor(Color.TRANSPARENT)
    val canvas = Canvas(bitmap)
    when (rotation) {
      0 -> {}
      1 -> {
        canvas.translate(measuredWidth.toFloat(), 0f)
        canvas.rotate(90f)
      }
      2 -> canvas.rotate(180f, measuredWidth.toFloat() / 2, measuredHeight.toFloat() / 2)
      3 -> {
        canvas.translate(0f, measuredHeight.toFloat())
        canvas.rotate(270f)
      }
    }
    container.draw(canvas)
    return bitmap
  }
}
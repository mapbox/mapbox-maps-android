package com.mapbox.maps.testapp.examples

import android.graphics.*
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.eq
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.expressions.dsl.generated.zoom
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.linearInterpolator
import com.mapbox.maps.extension.style.image.image
import com.mapbox.maps.extension.style.layers.generated.fillLayer
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityRtsFillPatternTintBinding

/**
 * Add an image to a style and use it to display a pattern in the landuse
 * FillLayer in the Mapbox Streets style.
 */
class TintFillPatternActivity : AppCompatActivity() {

  private lateinit var initialBitmap: Bitmap

  public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityRtsFillPatternTintBinding.inflate(layoutInflater)
    setContentView(binding.root)

    initialBitmap = BitmapFactory.decodeResource(resources, R.drawable.fill_pattern)

    binding.fabPaint.setOnClickListener {
      binding.mapView.getMapboxMap().getStyle()?.apply {
        addImage(FILL_PATTERN_ID, changeBitmapColor(initialBitmap, randomColor()))
      }
    }

    binding.mapView.getMapboxMap().loadStyle(
      style(styleUri = Style.MAPBOX_STREETS) {
        +image(FILL_PATTERN_ID, changeBitmapColor(initialBitmap, randomColor()))
        +fillLayer(FILL_LAYER_ID, STREETS_SOURCE_ID) {
          sourceLayer(SOURCE_LAYER)
          fillPattern(FILL_PATTERN_ID)
          fillOpacity(
            linearInterpolator(
              input = zoom(),
              stops = arrayOf(
                literal(13.0) to literal(0.0),
                literal(15.0) to literal(0.5),
                literal(17.0) to literal(0.75)
              )
            )
          )
          filter(
            eq {
              get { literal(FILTER_FIELD) }
              literal(FILTER_VALUE)
            }
          )
        }
      }
    )
  }

  private fun changeBitmapColor(sourceBitmap: Bitmap, @ColorInt color: Int): Bitmap {
    val resultBitmap: Bitmap = sourceBitmap.copy(sourceBitmap.config, true)
    val paint = Paint()
    paint.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
    val canvas = Canvas(resultBitmap)
    canvas.drawBitmap(resultBitmap, 0f, 0f, paint)
    return resultBitmap
  }

  private fun randomColor(): Int {
    return (Math.random() * 16777215).toInt() or (0xFF shl 24)
  }

  companion object {
    private const val FILL_LAYER_ID = "fill_layer_id"
    private const val FILL_PATTERN_ID = "fill_pattern_id"
    private const val STREETS_SOURCE_ID = "composite"
    private const val SOURCE_LAYER = "landuse"
    private const val FILTER_FIELD = "class"
    private const val FILTER_VALUE = "parking"
  }
}
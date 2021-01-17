package com.mapbox.maps.testapp.examples

import android.graphics.*
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.eq
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.image.image
import com.mapbox.maps.extension.style.layers.generated.fillLayer
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_rts_fill_pattern_tint.*
import kotlinx.android.synthetic.main.item_map.mapView

/**
 * Tint a fill pattern and change it's color on button click.
 */
class TintFillPatternActivity : AppCompatActivity() {

  private lateinit var initialBitmap: Bitmap

  public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_rts_fill_pattern_tint)

    initialBitmap = BitmapFactory.decodeResource(resources, R.drawable.fill_pattern)

    fabPaint.setOnClickListener {
      mapView.getMapboxMap().getStyle()?.apply {
        addImage(FILL_PATTERN_ID, changeBitmapColor(initialBitmap, randomColor()))
      }
    }

    mapView.getMapboxMap().loadStyle(
      style(styleUri = Style.MAPBOX_STREETS) {
        +image(FILL_PATTERN_ID) {
          bitmap(changeBitmapColor(initialBitmap, randomColor()))
        }
        +fillLayer(FILL_LAYER_ID, STREETS_SOURCE_ID) {
          sourceLayer(SOURCE_LAYER)
          fillPattern(FILL_PATTERN_ID)
          fillOpacity(
            interpolate {
              linear()
              zoom()
              stop {
                literal(13.0)
                literal(0.0)
              }
              stop {
                literal(15.0)
                literal(0.5)
              }
              stop {
                literal(17.0)
                literal(0.75)
              }
            }
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

  override fun onStart() {
    super.onStart()
    mapView.onStart()
  }

  override fun onStop() {
    super.onStop()
    mapView.onStop()
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
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
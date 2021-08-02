package com.mapbox.maps

import android.graphics.Typeface
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.lang.reflect.Field
import kotlin.collections.Map

/**
 * Utility class to select a font from a range of font names based on the availability of fonts on the device.
 */
internal object FontUtils {

  private const val DEFAULT_FONT = "sans-serif"
  private const val TAG = "Mbgl-FontUtils"
  private const val TYPEFACE_FONTMAP_FIELD_NAME = "sSystemFontMap"
  private val DEFAULT_FONT_STACKS: List<String> = listOf(
    "sans-serif", "serif", "monospace"
  )

  /**
   * Select a font from a range of font names to match the availability of fonts on the device.
   *
   * @param font font name to select
   * @return the selected font
   */
  fun extractValidFont(font: String?): String {
    var validFonts: List<String>? = null
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      validFonts = deviceFonts
    }
    if (validFonts == null || validFonts.isEmpty()) {
      validFonts = DEFAULT_FONT_STACKS
    }
    font?.let {
      if (validFonts.contains(it)) {
        return font
      }
    }
    Log.i(
      TAG, "Couldn't map font family for local ideograph, using ${DEFAULT_FONT} instead"
    )
    return DEFAULT_FONT
  }

  @get:RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  private val deviceFonts: List<String>
    get() {
      val fonts: MutableList<String> = ArrayList()
      try {
        val typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        val fontField: Field = Typeface::class.java.getDeclaredField(TYPEFACE_FONTMAP_FIELD_NAME)
        fontField.isAccessible = true
        val fontMap: Map<String, Typeface> =
          fontField.get(typeface) as Map<String, Typeface>
        fonts.addAll(fontMap.keys)
      } catch (exception: Exception) {
        Log.e(TAG, "Couldn't load fonts from Typeface: $exception")
      }
      return fonts
    }
}
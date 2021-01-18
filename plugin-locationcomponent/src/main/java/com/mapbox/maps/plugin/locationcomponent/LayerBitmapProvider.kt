package com.mapbox.maps.plugin.locationcomponent

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import com.mapbox.maps.plugin.locationcomponent.utils.BitmapUtils

internal class LayerBitmapProvider() {
  fun generateBitmap(drawable: Drawable, @ColorInt tintColor: Int?): Bitmap {
    val drawable =
      BitmapUtils.getDrawableWithTint(drawable, tintColor)
    return BitmapUtils.getBitmapFromDrawable(drawable)!!
  }
}
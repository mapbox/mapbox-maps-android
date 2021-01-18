package com.mapbox.maps.plugin.locationcomponent.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.VisibleForTesting
import androidx.appcompat.content.res.AppCompatResources
import java.nio.ByteBuffer
import java.util.*

/**
 * Utility class for creating bitmaps
 */
internal object BitmapUtils {
  /**
   * Extract an underlying bitmap from a drawable
   *
   * @param sourceDrawable The source drawable
   * @return The underlying bitmap
   */
  fun getBitmapFromDrawable(sourceDrawable: Drawable?): Bitmap? {
    if (sourceDrawable == null) {
      return null
    }
    return if (sourceDrawable is BitmapDrawable) {
      sourceDrawable.bitmap
    } else {
      // copying drawable object to not manipulate on the same reference
      val constantState = sourceDrawable.constantState ?: return null
      val drawable = constantState.newDrawable().mutate()
      val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth, drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
      )
      val canvas = Canvas(bitmap)
      drawable.setBounds(0, 0, canvas.width, canvas.height)
      drawable.draw(canvas)
      bitmap
    }
  }

  /**
   * Get a drawable from a resource.
   *
   * @param context     Context to obtain [android.content.res.Resources]
   * @param drawableRes Drawable resource
   * @return The drawable created from the resource
   */
  fun getDrawableFromRes(
    context: Context,
    @DrawableRes drawableRes: Int
  ): Drawable? {
    return getDrawableFromRes(
      context,
      drawableRes,
      null
    )
  }

  /**
   * Get a tinted drawable from a resource.
   *
   * @param context     Context to obtain [android.content.res.Resources]
   * @param drawableRes Drawable resource
   * @param tintColor   Tint color
   * @return The drawable created from the resource
   */
  fun getDrawableFromRes(
    context: Context,
    @DrawableRes drawableRes: Int,
    @ColorInt tintColor: Int?
  ): Drawable? {
    val drawable =
      AppCompatResources.getDrawable(context, drawableRes) ?: return null
    if (tintColor == null) {
      return drawable
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      drawable.setTint(tintColor)
    } else {
      drawable.mutate().setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
    }
    return drawable
  }

  /**
   * Get a tinted drawable from a drawable.
   *
   * @param drawable    Drawable resource
   * @param tintColor   Tint color
   * @return The drawable created from the resource
   */
  fun getDrawableWithTint(
    drawable: Drawable,
    @ColorInt tintColor: Int?
  ): Drawable? {
    if (tintColor == null) {
      return drawable
    }
    val tintedDrawable = drawable.mutate().constantState.newDrawable()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      tintedDrawable.setTint(tintColor)
    } else {
      tintedDrawable.mutate().setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
    }
    return tintedDrawable
  }

  /**
   * Validates if the bytes of a bitmap matches another
   *
   * @param bitmap the bitmap to be compared against
   * @param other  the bitmap to compare with
   * @return true if equal
   */
  @VisibleForTesting
  fun equals(bitmap: Bitmap, other: Bitmap): Boolean {
    val buffer =
      ByteBuffer.allocate(bitmap.height * bitmap.rowBytes)
    bitmap.copyPixelsToBuffer(buffer)
    val bufferOther =
      ByteBuffer.allocate(other.height * other.rowBytes)
    other.copyPixelsToBuffer(bufferOther)
    return Arrays.equals(buffer.array(), bufferOther.array())
  }
}
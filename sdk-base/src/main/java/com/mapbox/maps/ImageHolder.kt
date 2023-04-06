package com.mapbox.maps

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import java.util.Objects

/**
 * Class to hold image data either as drawable id or as the [Bitmap].
 */
@Parcelize
class ImageHolder private constructor(
  /**
   * Image represented as drawable resource id.
   */
  @DrawableRes val drawableId: Int?,
  /**
   * Image represented as the bitmap.
   */
  val bitmap: Bitmap?
) : Parcelable {

  /**
   * Overloaded equals function.
   */
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    other as ImageHolder
    return drawableId == other.drawableId && bitmap == other.bitmap
  }

  /**
   * Overloaded hashCode function based on all class properties.
   */
  override fun hashCode(): Int {
    return Objects.hash(drawableId, bitmap)
  }

  /**
   * Overloaded toString function.
   */
  override fun toString(): String {
    return "ImageHolder(bitmap=$bitmap, drawableId=$drawableId)"
  }

  /**
   * Static methods to create an instance of [ImageHolder].
   */
  companion object {
    /**
     * Create an instance of [ImageHolder] from given [DrawableRes] id.
     */
    @JvmStatic
    fun from(@DrawableRes drawableId: Int): ImageHolder {
      return ImageHolder(drawableId, null)
    }

    /**
     * Create an instance of [ImageHolder] from given [Bitmap].
     */
    @JvmStatic
    fun from(bitmap: Bitmap): ImageHolder {
      return ImageHolder(null, bitmap)
    }
  }
}
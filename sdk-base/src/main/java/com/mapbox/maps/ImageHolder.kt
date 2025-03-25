package com.mapbox.maps

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.RestrictTo
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
  val bitmap: Bitmap?,
  /**
   * Image represented as the internal Mapbox image.
   */
  @get:RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
  val image: Image?,
) : Parcelable {

  /**
   * Overloaded equals function.
   */
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    other as ImageHolder
    return drawableId == other.drawableId && bitmap == other.bitmap && image == other.image
  }

  /**
   * Overloaded hashCode function based on all class properties.
   */
  override fun hashCode(): Int {
    return Objects.hash(drawableId, bitmap, image)
  }

  /**
   * Overloaded toString function.
   */
  override fun toString(): String {
    return "ImageHolder(bitmap=$bitmap, drawableId=$drawableId, image=$image)"
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
      return ImageHolder(drawableId, null, null)
    }

    /**
     * Create an instance of [ImageHolder] from given [Bitmap].
     */
    @JvmStatic
    fun from(bitmap: Bitmap): ImageHolder {
      return ImageHolder(null, bitmap, null)
    }

    /**
     * Create an instance of [ImageHolder] from given [Image].
     */
    @JvmStatic
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
    fun from(image: Image): ImageHolder {
      return ImageHolder(null, null, image)
    }
  }
}
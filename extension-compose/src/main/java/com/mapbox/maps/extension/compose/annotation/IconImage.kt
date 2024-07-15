package com.mapbox.maps.extension.compose.annotation

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotationState
import com.mapbox.maps.extension.compose.style.drawToBitmap
import kotlinx.parcelize.Parcelize
import java.util.Objects

/**
 * Represents an [IconImage] for [PointAnnotationState], it can either be a [String] image id or a [Bitmap] to be added to the map.
 */
@Immutable
@Parcelize
public class IconImage private constructor(
  internal val imageId: String?,
  internal val bitmap: Bitmap?
) : Parcelable {
  /**
   * Construct a [IconImage] with [imageId] as a [String].
   */
  public constructor(imageId: String) : this(imageId = imageId, bitmap = null)

  /**
   * Construct a [IconImage] with [bitmap] as a [Bitmap].
   */
  public constructor(bitmap: Bitmap) : this(imageId = null, bitmap = bitmap)

  /**
   * Override the hashcode.
   */
  override fun hashCode(): Int {
    return Objects.hash(imageId, bitmap)
  }

  /**
   * Override the equals.
   */
  override fun equals(other: Any?): Boolean {
    return other is IconImage && other.imageId == imageId && other.bitmap == bitmap
  }

  /**
   * Override toString.
   */
  override fun toString(): String {
    return "IconImage(${imageId ?: bitmap.toString()})"
  }
}

/**
 * Create and remember a [Bitmap] with [Painter].
 *
 * @param painter the [Painter] to provide the bitmap
 *
 * @return a [Bitmap]
 */
@Composable
public fun rememberIconImage(key: Any?, painter: Painter): IconImage {
  return remember(key, painter) {
    IconImage(painter.drawToBitmap().asAndroidBitmap())
  }
}

/**
 * Create and remember a [Bitmap] with [resourceId].
 *
 * @param resourceId the resource id to be loaded to the image
 *
 * @return a [Bitmap]
 */
@Composable
public fun rememberIconImage(@DrawableRes resourceId: Int): IconImage {
  return rememberIconImage(key = resourceId, painter = painterResource(id = resourceId))
}
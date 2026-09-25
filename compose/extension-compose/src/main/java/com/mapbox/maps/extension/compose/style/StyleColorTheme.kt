package com.mapbox.maps.extension.compose.style

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.mapbox.maps.ColorTheme
import com.mapbox.maps.Image
import com.mapbox.maps.MapboxDelicateApi
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.style.color.colorTheme
import com.mapbox.maps.toMapboxImage

/**
 * Create and remember [ColorTheme]
 * @param base64 Base64 encoded representation of an LUT image. The height of the image cannot be more than 32 pixels.
 *
 * @return [ColorTheme]
 */
@Composable
@MapboxExperimental
public fun rememberColorTheme(base64: String): ColorTheme {
  return remember(base64) {
    colorTheme(base64 = base64)
  }
}

/**
 * Create and remember [ColorTheme]
 * @param image [Image] data of an LUT image
 *
 * @return [ColorTheme]
 */
@Composable
@MapboxExperimental
public fun rememberColorTheme(image: Image): ColorTheme {
  return remember(image) {
    colorTheme(image = image)
  }
}

/**
 * Create and remember [ColorTheme]
 * @param imageBitmap The image data of the bitmap as [ImageBitmap]
 *
 * @return [ColorTheme]
 */
@OptIn(MapboxDelicateApi::class)
@Composable
@MapboxExperimental
public fun rememberColorTheme(imageBitmap: ImageBitmap): ColorTheme {
  return rememberColorTheme(image = imageBitmap.asAndroidBitmap().toMapboxImage())
}

/**
 * Create and remember [ColorTheme]
 * @param painter the [Painter] to provide the image
 *
 * @return [ColorTheme]
 */
@Composable
@MapboxExperimental
public fun rememberColorTheme(painter: Painter): ColorTheme {
  return rememberColorTheme(imageBitmap = painter.drawToBitmap())
}

/**
 * Create and remember [ColorTheme]
 * @param resourceId the resource id to be loaded to the image
 *
 * @return [ColorTheme]
 */
@Composable
@MapboxExperimental
public fun rememberColorTheme(@DrawableRes resourceId: Int): ColorTheme {
  return rememberColorTheme(painter = painterResource(resourceId))
}

/**
 * Create and remember [StyleColorTheme]
 * @param colorTheme [ColorTheme] instance to apply to the [StyleState].
 *
 * @return [StyleColorTheme]
 */
@Composable
@MapboxExperimental
public fun rememberStyleColorTheme(colorTheme: ColorTheme): StyleColorTheme {
  return remember(colorTheme) {
    StyleColorTheme(colorTheme)
  }
}

/**
 * Color theme used when loading the style.
 *
 * @param colorTheme which will be loaded when loading the style.
 */
@Immutable
@MapboxExperimental
public data class StyleColorTheme internal constructor(
  val colorTheme: ColorTheme,
  internal val isStyleDefault: Boolean
) {

  /**
   * @param colorTheme which will be loaded when loading the style.
   */
  public constructor(colorTheme: ColorTheme) : this(colorTheme, false)

  /**
   * Public companion object
   */
  public companion object {
    /**
     * Using STYLE_DEFAULT will use the theme defined in style spec.
     */
    @JvmField
    public val STYLE_DEFAULT: StyleColorTheme = StyleColorTheme(colorTheme(), true)

    /**
     * Using NONE deletes the applied theme
     */
    @JvmField
    public val NONE: StyleColorTheme = StyleColorTheme(colorTheme(), false)
  }
}
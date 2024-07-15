// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions

/**
 * Set icon-color to initialise the pointAnnotation with.
 *
 * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
 *
 * @param iconColor the icon-color value
 * @return this
 */
public fun PointAnnotationOptions.withIconColor(iconColor: Color): PointAnnotationOptions = apply {
  withIconColor(iconColor.toArgb())
}
/**
 * Set icon-halo-color to initialise the pointAnnotation with.
 *
 * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
 *
 * @param iconHaloColor the icon-halo-color value
 * @return this
 */
public fun PointAnnotationOptions.withIconHaloColor(iconHaloColor: Color): PointAnnotationOptions = apply {
  withIconHaloColor(iconHaloColor.toArgb())
}
/**
 * Set text-color to initialise the pointAnnotation with.
 *
 * The color with which the text will be drawn.
 *
 * @param textColor the text-color value
 * @return this
 */
public fun PointAnnotationOptions.withTextColor(textColor: Color): PointAnnotationOptions = apply {
  withTextColor(textColor.toArgb())
}
/**
 * Set text-halo-color to initialise the pointAnnotation with.
 *
 * The color of the text's halo, which helps it stand out from backgrounds.
 *
 * @param textHaloColor the text-halo-color value
 * @return this
 */
public fun PointAnnotationOptions.withTextHaloColor(textHaloColor: Color): PointAnnotationOptions = apply {
  withTextHaloColor(textHaloColor.toArgb())
}

// End of generated file
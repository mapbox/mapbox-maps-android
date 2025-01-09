// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationOptions

/**
 * Set fill-color to initialise the polygonAnnotation with.
 *
 * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. Default value: "#000000".
 *
 * @param fillColor the fill-color value
 * @return this
 */
public fun PolygonAnnotationOptions.withFillColor(fillColor: Color): PolygonAnnotationOptions = apply {
  withFillColor(fillColor.toArgb())
}
/**
 * Set fill-outline-color to initialise the polygonAnnotation with.
 *
 * The outline color of the fill. Matches the value of `fill-color` if unspecified.
 *
 * @param fillOutlineColor the fill-outline-color value
 * @return this
 */
public fun PolygonAnnotationOptions.withFillOutlineColor(fillOutlineColor: Color): PolygonAnnotationOptions = apply {
  withFillOutlineColor(fillOutlineColor.toArgb())
}

// End of generated file
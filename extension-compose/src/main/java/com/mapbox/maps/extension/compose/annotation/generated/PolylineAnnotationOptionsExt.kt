// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationOptions

/**
 * Set line-border-color to initialise the polylineAnnotation with.
 *
 * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color. Default value: "rgba(0, 0, 0, 0)".
 *
 * @param lineBorderColor the line-border-color value
 * @return this
 */
public fun PolylineAnnotationOptions.withLineBorderColor(lineBorderColor: Color): PolylineAnnotationOptions = apply {
  withLineBorderColor(lineBorderColor.toArgb())
}
/**
 * Set line-color to initialise the polylineAnnotation with.
 *
 * The color with which the line will be drawn. Default value: "#000000".
 *
 * @param lineColor the line-color value
 * @return this
 */
public fun PolylineAnnotationOptions.withLineColor(lineColor: Color): PolylineAnnotationOptions = apply {
  withLineColor(lineColor.toArgb())
}

// End of generated file
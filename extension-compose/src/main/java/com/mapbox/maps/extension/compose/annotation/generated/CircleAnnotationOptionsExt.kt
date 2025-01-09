// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions

/**
 * Set circle-color to initialise the circleAnnotation with.
 *
 * The fill color of the circle. Default value: "#000000".
 *
 * @param circleColor the circle-color value
 * @return this
 */
public fun CircleAnnotationOptions.withCircleColor(circleColor: Color): CircleAnnotationOptions = apply {
  withCircleColor(circleColor.toArgb())
}
/**
 * Set circle-stroke-color to initialise the circleAnnotation with.
 *
 * The stroke color of the circle. Default value: "#000000".
 *
 * @param circleStrokeColor the circle-stroke-color value
 * @return this
 */
public fun CircleAnnotationOptions.withCircleStrokeColor(circleStrokeColor: Color): CircleAnnotationOptions = apply {
  withCircleStrokeColor(circleStrokeColor.toArgb())
}

// End of generated file
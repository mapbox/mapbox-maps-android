// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.style.layers.properties.generated.CirclePitchAlignment
import com.mapbox.maps.extension.style.layers.properties.generated.CirclePitchScale
import com.mapbox.maps.extension.style.layers.properties.generated.CircleTranslateAnchor
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager

/**
 * The state holder for [CircleAnnotationGroup] properties.
 */
@MapboxExperimental
@Stable
public class CircleAnnotationGroupState private constructor(
  initialCircleEmissiveStrength: Double?,
  initialCirclePitchAlignment: CirclePitchAlignment?,
  initialCirclePitchScale: CirclePitchScale?,
  initialCircleTranslate: List<Double>?,
  initialCircleTranslateAnchor: CircleTranslateAnchor?,
) {
  public constructor() : this(
    initialCircleEmissiveStrength = null,
    initialCirclePitchAlignment = null,
    initialCirclePitchScale = null,
    initialCircleTranslate = null,
    initialCircleTranslateAnchor = null,
  )
  /**
   * Controls the intensity of light emitted on the source features. The unit of circleEmissiveStrength is in intensity.
   */
  public var circleEmissiveStrength: Double? by mutableStateOf(initialCircleEmissiveStrength)
  /**
   * Orientation of circle when map is pitched.
   */
  public var circlePitchAlignment: CirclePitchAlignment? by mutableStateOf(initialCirclePitchAlignment)
  /**
   * Controls the scaling behavior of the circle when the map is pitched.
   */
  public var circlePitchScale: CirclePitchScale? by mutableStateOf(initialCirclePitchScale)
  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. The unit of circleTranslate is in density-independent pixels.
   */
  public var circleTranslate: List<Double>? by mutableStateOf(initialCircleTranslate)
  /**
   * Controls the frame of reference for {@link PropertyFactory#circleTranslate}.
   */
  public var circleTranslateAnchor: CircleTranslateAnchor? by mutableStateOf(initialCircleTranslateAnchor)

  @Composable
  private fun UpdateCircleEmissiveStrength(annotationManager: CircleAnnotationManager) {
    annotationManager.circleEmissiveStrength = circleEmissiveStrength
  }
  @Composable
  private fun UpdateCirclePitchAlignment(annotationManager: CircleAnnotationManager) {
    annotationManager.circlePitchAlignment = circlePitchAlignment
  }
  @Composable
  private fun UpdateCirclePitchScale(annotationManager: CircleAnnotationManager) {
    annotationManager.circlePitchScale = circlePitchScale
  }
  @Composable
  private fun UpdateCircleTranslate(annotationManager: CircleAnnotationManager) {
    annotationManager.circleTranslate = circleTranslate
  }
  @Composable
  private fun UpdateCircleTranslateAnchor(annotationManager: CircleAnnotationManager) {
    annotationManager.circleTranslateAnchor = circleTranslateAnchor
  }

  @Composable
  internal fun UpdateProperties(annotationManager: CircleAnnotationManager) {
    UpdateCircleEmissiveStrength(annotationManager)
    UpdateCirclePitchAlignment(annotationManager)
    UpdateCirclePitchScale(annotationManager)
    UpdateCircleTranslate(annotationManager)
    UpdateCircleTranslateAnchor(annotationManager)
  }
}

// End of generated file.
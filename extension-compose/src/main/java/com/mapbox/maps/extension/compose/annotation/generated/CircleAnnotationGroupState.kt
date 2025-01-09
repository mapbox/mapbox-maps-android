// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.mapbox.maps.extension.style.layers.properties.generated.CirclePitchAlignment
import com.mapbox.maps.extension.style.layers.properties.generated.CirclePitchScale
import com.mapbox.maps.extension.style.layers.properties.generated.CircleTranslateAnchor
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager

/**
 * The state holder for [CircleAnnotationGroup] properties.
 */
@Stable
public class CircleAnnotationGroupState private constructor(
  initialCircleSortKey: Double?,
  initialCircleBlur: Double?,
  initialCircleColor: Color?,
  initialCircleEmissiveStrength: Double?,
  initialCircleOpacity: Double?,
  initialCirclePitchAlignment: CirclePitchAlignment?,
  initialCirclePitchScale: CirclePitchScale?,
  initialCircleRadius: Double?,
  initialCircleStrokeColor: Color?,
  initialCircleStrokeOpacity: Double?,
  initialCircleStrokeWidth: Double?,
  initialCircleTranslate: List<Double>?,
  initialCircleTranslateAnchor: CircleTranslateAnchor?,
  initialCircleAnnotationGroupInteractionsState: CircleAnnotationGroupInteractionsState,
) {
  public constructor() : this(
    initialCircleSortKey = null,
    initialCircleBlur = null,
    initialCircleColor = null,
    initialCircleEmissiveStrength = null,
    initialCircleOpacity = null,
    initialCirclePitchAlignment = null,
    initialCirclePitchScale = null,
    initialCircleRadius = null,
    initialCircleStrokeColor = null,
    initialCircleStrokeOpacity = null,
    initialCircleStrokeWidth = null,
    initialCircleTranslate = null,
    initialCircleTranslateAnchor = null,
    initialCircleAnnotationGroupInteractionsState = CircleAnnotationGroupInteractionsState(),
  )

  /**
   * Holds all interactions with [PointAnnotationGroup]
   */
  public var interactionsState: CircleAnnotationGroupInteractionsState by mutableStateOf(initialCircleAnnotationGroupInteractionsState)
  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   */
  public var circleSortKey: Double? by mutableStateOf(initialCircleSortKey)
  /**
   * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity. Setting a negative value renders the blur as an inner glow effect. Default value: 0.
   */
  public var circleBlur: Double? by mutableStateOf(initialCircleBlur)
  /**
   * The fill color of the circle. Default value: "#000000".
   */
  public var circleColor: Color? by mutableStateOf(initialCircleColor)
  /**
   * Controls the intensity of light emitted on the source features. Default value: 0. Minimum value: 0. The unit of circleEmissiveStrength is in intensity.
   */
  public var circleEmissiveStrength: Double? by mutableStateOf(initialCircleEmissiveStrength)
  /**
   * The opacity at which the circle will be drawn. Default value: 1. Value range: [0, 1]
   */
  public var circleOpacity: Double? by mutableStateOf(initialCircleOpacity)
  /**
   * Orientation of circle when map is pitched. Default value: "viewport".
   */
  public var circlePitchAlignment: CirclePitchAlignment? by mutableStateOf(initialCirclePitchAlignment)
  /**
   * Controls the scaling behavior of the circle when the map is pitched. Default value: "map".
   */
  public var circlePitchScale: CirclePitchScale? by mutableStateOf(initialCirclePitchScale)
  /**
   * Circle radius. Default value: 5. Minimum value: 0. The unit of circleRadius is in pixels.
   */
  public var circleRadius: Double? by mutableStateOf(initialCircleRadius)
  /**
   * The stroke color of the circle. Default value: "#000000".
   */
  public var circleStrokeColor: Color? by mutableStateOf(initialCircleStrokeColor)
  /**
   * The opacity of the circle's stroke. Default value: 1. Value range: [0, 1]
   */
  public var circleStrokeOpacity: Double? by mutableStateOf(initialCircleStrokeOpacity)
  /**
   * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`. Default value: 0. Minimum value: 0. The unit of circleStrokeWidth is in pixels.
   */
  public var circleStrokeWidth: Double? by mutableStateOf(initialCircleStrokeWidth)
  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. Default value: [0,0]. The unit of circleTranslate is in pixels.
   */
  public var circleTranslate: List<Double>? by mutableStateOf(initialCircleTranslate)
  /**
   * Controls the frame of reference for `circle-translate`. Default value: "map".
   */
  public var circleTranslateAnchor: CircleTranslateAnchor? by mutableStateOf(initialCircleTranslateAnchor)

  @Composable
  private fun UpdateCircleSortKey(annotationManager: CircleAnnotationManager) {
    annotationManager.circleSortKey = circleSortKey
  }
  @Composable
  private fun UpdateCircleBlur(annotationManager: CircleAnnotationManager) {
    annotationManager.circleBlur = circleBlur
  }
  @Composable
  private fun UpdateCircleColor(annotationManager: CircleAnnotationManager) {
    annotationManager.circleColorString = circleColor?.toArgb()?.let { ColorUtils.colorToRgbaString(it) }
  }
  @Composable
  private fun UpdateCircleEmissiveStrength(annotationManager: CircleAnnotationManager) {
    annotationManager.circleEmissiveStrength = circleEmissiveStrength
  }
  @Composable
  private fun UpdateCircleOpacity(annotationManager: CircleAnnotationManager) {
    annotationManager.circleOpacity = circleOpacity
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
  private fun UpdateCircleRadius(annotationManager: CircleAnnotationManager) {
    annotationManager.circleRadius = circleRadius
  }
  @Composable
  private fun UpdateCircleStrokeColor(annotationManager: CircleAnnotationManager) {
    annotationManager.circleStrokeColorString = circleStrokeColor?.toArgb()?.let { ColorUtils.colorToRgbaString(it) }
  }
  @Composable
  private fun UpdateCircleStrokeOpacity(annotationManager: CircleAnnotationManager) {
    annotationManager.circleStrokeOpacity = circleStrokeOpacity
  }
  @Composable
  private fun UpdateCircleStrokeWidth(annotationManager: CircleAnnotationManager) {
    annotationManager.circleStrokeWidth = circleStrokeWidth
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
    UpdateCircleSortKey(annotationManager)
    UpdateCircleBlur(annotationManager)
    UpdateCircleColor(annotationManager)
    UpdateCircleEmissiveStrength(annotationManager)
    UpdateCircleOpacity(annotationManager)
    UpdateCirclePitchAlignment(annotationManager)
    UpdateCirclePitchScale(annotationManager)
    UpdateCircleRadius(annotationManager)
    UpdateCircleStrokeColor(annotationManager)
    UpdateCircleStrokeOpacity(annotationManager)
    UpdateCircleStrokeWidth(annotationManager)
    UpdateCircleTranslate(annotationManager)
    UpdateCircleTranslateAnchor(annotationManager)
  }
}

// End of generated file.
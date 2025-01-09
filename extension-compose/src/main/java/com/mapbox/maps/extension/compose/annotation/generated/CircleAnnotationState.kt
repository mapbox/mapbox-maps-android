// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.mapbox.maps.extension.compose.annotation.internal.generated.CircleAnnotationNode
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotation

/**
 * The state holder for [CircleAnnotation] properties.
 */
@Stable
public class CircleAnnotationState private constructor(
  initialCircleBlur: Double?,
  initialCircleColor: Color?,
  initialCircleOpacity: Double?,
  initialCircleRadius: Double?,
  initialCircleStrokeColor: Color?,
  initialCircleStrokeOpacity: Double?,
  initialCircleStrokeWidth: Double?,
  initialCircleAnnotationInteractionsState: CircleAnnotationInteractionsState,
) {

  public constructor() : this(
    initialCircleBlur = null,
    initialCircleColor = null,
    initialCircleOpacity = null,
    initialCircleRadius = null,
    initialCircleStrokeColor = null,
    initialCircleStrokeOpacity = null,
    initialCircleStrokeWidth = null,
    initialCircleAnnotationInteractionsState = CircleAnnotationInteractionsState(),
)

  /**
  * All interactions with [PointAnnotation]
  */
  public var interactionsState: CircleAnnotationInteractionsState by mutableStateOf(initialCircleAnnotationInteractionsState)
  /**
   * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity. Setting a negative value renders the blur as an inner glow effect. Default value: 0.
   */
  public var circleBlur: Double? by mutableStateOf(initialCircleBlur)
  /**
   * The fill color of the circle. Default value: "#000000".
   */
  public var circleColor: Color? by mutableStateOf(initialCircleColor)
  /**
   * The opacity at which the circle will be drawn. Default value: 1. Value range: [0, 1]
   */
  public var circleOpacity: Double? by mutableStateOf(initialCircleOpacity)
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

  @Composable
  private fun UpdateCircleBlur(
    annotationNode: CircleAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.circleBlur = circleBlur
      }
    )
  }
  @Composable
  private fun UpdateCircleColor(
    annotationNode: CircleAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.circleColorInt = circleColor?.toArgb()
      }
    )
  }
  @Composable
  private fun UpdateCircleOpacity(
    annotationNode: CircleAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.circleOpacity = circleOpacity
      }
    )
  }
  @Composable
  private fun UpdateCircleRadius(
    annotationNode: CircleAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.circleRadius = circleRadius
      }
    )
  }
  @Composable
  private fun UpdateCircleStrokeColor(
    annotationNode: CircleAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.circleStrokeColorInt = circleStrokeColor?.toArgb()
      }
    )
  }
  @Composable
  private fun UpdateCircleStrokeOpacity(
    annotationNode: CircleAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.circleStrokeOpacity = circleStrokeOpacity
      }
    )
  }
  @Composable
  private fun UpdateCircleStrokeWidth(
    annotationNode: CircleAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.circleStrokeWidth = circleStrokeWidth
      }
    )
  }

  @Composable
  internal fun UpdateProperties(
    annotationNode: CircleAnnotationNode,
  ) {
    UpdateCircleBlur(annotationNode)
    UpdateCircleColor(annotationNode)
    UpdateCircleOpacity(annotationNode)
    UpdateCircleRadius(annotationNode)
    UpdateCircleStrokeColor(annotationNode)
    UpdateCircleStrokeOpacity(annotationNode)
    UpdateCircleStrokeWidth(annotationNode)
  }
}

// End of generated file
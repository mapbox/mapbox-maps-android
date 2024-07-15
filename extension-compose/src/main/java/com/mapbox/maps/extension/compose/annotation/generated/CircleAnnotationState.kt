// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.annotation.internal.generated.CircleAnnotationNode
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotation

/**
 * The state holder for [CircleAnnotation] properties.
 */
@MapboxExperimental
@Stable
public class CircleAnnotationState private constructor(
  initialCircleBlur: Double?,
  initialCircleColor: Color?,
  initialCircleOpacity: Double?,
  initialCircleRadius: Double?,
  initialCircleStrokeColor: Color?,
  initialCircleStrokeOpacity: Double?,
  initialCircleStrokeWidth: Double?,
) {

  public constructor() : this(
    initialCircleBlur = null,
    initialCircleColor = null,
    initialCircleOpacity = null,
    initialCircleRadius = null,
    initialCircleStrokeColor = null,
    initialCircleStrokeOpacity = null,
    initialCircleStrokeWidth = null,
  )

  /**
   * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity.
   */
  public var circleBlur: Double? by mutableStateOf(initialCircleBlur)
  /**
   * The fill color of the circle.
   */
  public var circleColor: Color? by mutableStateOf(initialCircleColor)
  /**
   * The opacity at which the circle will be drawn.
   */
  public var circleOpacity: Double? by mutableStateOf(initialCircleOpacity)
  /**
   * Circle radius. The unit of circleRadius is in density-independent pixels.
   */
  public var circleRadius: Double? by mutableStateOf(initialCircleRadius)
  /**
   * The stroke color of the circle.
   */
  public var circleStrokeColor: Color? by mutableStateOf(initialCircleStrokeColor)
  /**
   * The opacity of the circle's stroke.
   */
  public var circleStrokeOpacity: Double? by mutableStateOf(initialCircleStrokeOpacity)
  /**
   * The width of the circle's stroke. Strokes are placed outside of the {@link PropertyFactory#circleRadius}. The unit of circleStrokeWidth is in density-independent pixels.
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
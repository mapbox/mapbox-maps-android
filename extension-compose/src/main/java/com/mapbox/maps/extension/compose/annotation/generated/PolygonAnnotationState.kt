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
import com.mapbox.maps.extension.compose.annotation.internal.generated.PolygonAnnotationNode
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotation

/**
 * The state holder for [PolygonAnnotation] properties.
 */
@Stable
public class PolygonAnnotationState private constructor(
  initialFillColor: Color?,
  initialFillOpacity: Double?,
  initialFillOutlineColor: Color?,
  initialFillPattern: String?,
  initialFillZOffset: Double?,
  initialPolygonAnnotationInteractionsState: PolygonAnnotationInteractionsState,
) {

  public constructor() : this(
    initialFillColor = null,
    initialFillOpacity = null,
    initialFillOutlineColor = null,
    initialFillPattern = null,
    initialFillZOffset = null,
    initialPolygonAnnotationInteractionsState = PolygonAnnotationInteractionsState(),
)

  /**
  * All interactions with [PointAnnotation]
  */
  public var interactionsState: PolygonAnnotationInteractionsState by mutableStateOf(initialPolygonAnnotationInteractionsState)
  /**
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. Default value: "#000000".
   */
  public var fillColor: Color? by mutableStateOf(initialFillColor)
  /**
   * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used. Default value: 1. Value range: [0, 1]
   */
  public var fillOpacity: Double? by mutableStateOf(initialFillOpacity)
  /**
   * The outline color of the fill. Matches the value of `fill-color` if unspecified.
   */
  public var fillOutlineColor: Color? by mutableStateOf(initialFillOutlineColor)
  /**
   * Name of image in sprite to use for drawing image fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  public var fillPattern: String? by mutableStateOf(initialFillPattern)
  /**
   * Specifies an uniform elevation in meters. Note: If the value is zero, the layer will be rendered on the ground. Non-zero values will elevate the layer from the sea level, which can cause it to be rendered below the terrain. Default value: 0. Minimum value: 0.
   */
  @MapboxExperimental
  public var fillZOffset: Double? by mutableStateOf(initialFillZOffset)

  @Composable
  private fun UpdateFillColor(
    annotationNode: PolygonAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.fillColorInt = fillColor?.toArgb()
      }
    )
  }
  @Composable
  private fun UpdateFillOpacity(
    annotationNode: PolygonAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.fillOpacity = fillOpacity
      }
    )
  }
  @Composable
  private fun UpdateFillOutlineColor(
    annotationNode: PolygonAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.fillOutlineColorInt = fillOutlineColor?.toArgb()
      }
    )
  }
  @Composable
  private fun UpdateFillPattern(
    annotationNode: PolygonAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.fillPattern = fillPattern
      }
    )
  }
  @Composable
  private fun UpdateFillZOffset(
    annotationNode: PolygonAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.fillZOffset = fillZOffset
      }
    )
  }

  @Composable
  internal fun UpdateProperties(
    annotationNode: PolygonAnnotationNode,
  ) {
    UpdateFillColor(annotationNode)
    UpdateFillOpacity(annotationNode)
    UpdateFillOutlineColor(annotationNode)
    UpdateFillPattern(annotationNode)
    UpdateFillZOffset(annotationNode)
  }
}

// End of generated file
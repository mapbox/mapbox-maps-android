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
import com.mapbox.maps.extension.compose.annotation.internal.generated.PolylineAnnotationNode
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotation

/**
 * The state holder for [PolylineAnnotation] properties.
 */
@Stable
public class PolylineAnnotationState private constructor(
  initialLineJoin: LineJoin?,
  initialLineZOffset: Double?,
  initialLineBlur: Double?,
  initialLineBorderColor: Color?,
  initialLineBorderWidth: Double?,
  initialLineColor: Color?,
  initialLineGapWidth: Double?,
  initialLineOffset: Double?,
  initialLineOpacity: Double?,
  initialLinePattern: String?,
  initialLineWidth: Double?,
  initialPolylineAnnotationInteractionsState: PolylineAnnotationInteractionsState,
) {

  public constructor() : this(
    initialLineJoin = null,
    initialLineZOffset = null,
    initialLineBlur = null,
    initialLineBorderColor = null,
    initialLineBorderWidth = null,
    initialLineColor = null,
    initialLineGapWidth = null,
    initialLineOffset = null,
    initialLineOpacity = null,
    initialLinePattern = null,
    initialLineWidth = null,
    initialPolylineAnnotationInteractionsState = PolylineAnnotationInteractionsState(),
)

  /**
  * All interactions with [PointAnnotation]
  */
  public var interactionsState: PolylineAnnotationInteractionsState by mutableStateOf(initialPolylineAnnotationInteractionsState)
  /**
   * The display of lines when joining.
   */
  public var lineJoin: LineJoin? by mutableStateOf(initialLineJoin)
  /**
   * Vertical offset from ground, in meters. Defaults to 0. Not supported for globe projection at the moment.
   */
  @MapboxExperimental
  public var lineZOffset: Double? by mutableStateOf(initialLineZOffset)
  /**
   * Blur applied to the line, in density-independent pixels. The unit of lineBlur is in pixels.
   */
  public var lineBlur: Double? by mutableStateOf(initialLineBlur)
  /**
   * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color.
   */
  public var lineBorderColor: Color? by mutableStateOf(initialLineBorderColor)
  /**
   * The width of the line border. A value of zero means no border.
   */
  public var lineBorderWidth: Double? by mutableStateOf(initialLineBorderWidth)
  /**
   * The color with which the line will be drawn.
   */
  public var lineColor: Color? by mutableStateOf(initialLineColor)
  /**
   * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap. The unit of lineGapWidth is in density-independent pixels.
   */
  public var lineGapWidth: Double? by mutableStateOf(initialLineGapWidth)
  /**
   * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset. The unit of lineOffset is in density-independent pixels.
   */
  public var lineOffset: Double? by mutableStateOf(initialLineOffset)
  /**
   * The opacity at which the line will be drawn.
   */
  public var lineOpacity: Double? by mutableStateOf(initialLineOpacity)
  /**
   * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  public var linePattern: String? by mutableStateOf(initialLinePattern)
  /**
   * Stroke thickness. The unit of lineWidth is in density-independent pixels.
   */
  public var lineWidth: Double? by mutableStateOf(initialLineWidth)

  @Composable
  private fun UpdateLineJoin(
    annotationNode: PolylineAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.lineJoin = lineJoin
      }
    )
  }
  @Composable
  private fun UpdateLineZOffset(
    annotationNode: PolylineAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.lineZOffset = lineZOffset
      }
    )
  }
  @Composable
  private fun UpdateLineBlur(
    annotationNode: PolylineAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.lineBlur = lineBlur
      }
    )
  }
  @Composable
  private fun UpdateLineBorderColor(
    annotationNode: PolylineAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.lineBorderColorInt = lineBorderColor?.toArgb()
      }
    )
  }
  @Composable
  private fun UpdateLineBorderWidth(
    annotationNode: PolylineAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.lineBorderWidth = lineBorderWidth
      }
    )
  }
  @Composable
  private fun UpdateLineColor(
    annotationNode: PolylineAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.lineColorInt = lineColor?.toArgb()
      }
    )
  }
  @Composable
  private fun UpdateLineGapWidth(
    annotationNode: PolylineAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.lineGapWidth = lineGapWidth
      }
    )
  }
  @Composable
  private fun UpdateLineOffset(
    annotationNode: PolylineAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.lineOffset = lineOffset
      }
    )
  }
  @Composable
  private fun UpdateLineOpacity(
    annotationNode: PolylineAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.lineOpacity = lineOpacity
      }
    )
  }
  @Composable
  private fun UpdateLinePattern(
    annotationNode: PolylineAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.linePattern = linePattern
      }
    )
  }
  @Composable
  private fun UpdateLineWidth(
    annotationNode: PolylineAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.lineWidth = lineWidth
      }
    )
  }

  @Composable
  internal fun UpdateProperties(
    annotationNode: PolylineAnnotationNode,
  ) {
    UpdateLineJoin(annotationNode)
    UpdateLineZOffset(annotationNode)
    UpdateLineBlur(annotationNode)
    UpdateLineBorderColor(annotationNode)
    UpdateLineBorderWidth(annotationNode)
    UpdateLineColor(annotationNode)
    UpdateLineGapWidth(annotationNode)
    UpdateLineOffset(annotationNode)
    UpdateLineOpacity(annotationNode)
    UpdateLinePattern(annotationNode)
    UpdateLineWidth(annotationNode)
  }
}

// End of generated file
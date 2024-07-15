// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineTranslateAnchor
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationManager

/**
 * The state holder for [PolylineAnnotationGroup] properties.
 */
@Stable
public class PolylineAnnotationGroupState private constructor(
  initialLineCap: LineCap?,
  initialLineMiterLimit: Double?,
  initialLineRoundLimit: Double?,
  initialLineDasharray: List<Double>?,
  initialLineDepthOcclusionFactor: Double?,
  initialLineEmissiveStrength: Double?,
  initialLineOcclusionOpacity: Double?,
  initialLineTranslate: List<Double>?,
  initialLineTranslateAnchor: LineTranslateAnchor?,
  initialLineTrimOffset: List<Double>?,
) {
  public constructor() : this(
    initialLineCap = null,
    initialLineMiterLimit = null,
    initialLineRoundLimit = null,
    initialLineDasharray = null,
    initialLineDepthOcclusionFactor = null,
    initialLineEmissiveStrength = null,
    initialLineOcclusionOpacity = null,
    initialLineTranslate = null,
    initialLineTranslateAnchor = null,
    initialLineTrimOffset = null,
  )
  /**
   * The display of line endings.
   */
  public var lineCap: LineCap? by mutableStateOf(initialLineCap)
  /**
   * Used to automatically convert miter joins to bevel joins for sharp angles.
   */
  public var lineMiterLimit: Double? by mutableStateOf(initialLineMiterLimit)
  /**
   * Used to automatically convert round joins to miter joins for shallow angles.
   */
  public var lineRoundLimit: Double? by mutableStateOf(initialLineRoundLimit)
  /**
   * Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to density-independent pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels. The unit of lineDasharray is in line widths.
   */
  public var lineDasharray: List<Double>? by mutableStateOf(initialLineDasharray)
  /**
   * Decrease line layer opacity based on occlusion from 3D objects. Value 0 disables occlusion, value 1 means fully occluded.
   */
  public var lineDepthOcclusionFactor: Double? by mutableStateOf(initialLineDepthOcclusionFactor)
  /**
   * Controls the intensity of light emitted on the source features. The unit of lineEmissiveStrength is in intensity.
   */
  public var lineEmissiveStrength: Double? by mutableStateOf(initialLineEmissiveStrength)
  /**
   * Opacity multiplier (multiplies line-opacity value) of the line part that is occluded by 3D objects. Value 0 hides occluded part, value 1 means the same opacity as non-occluded part. The property is not supported when {@link PropertyFactory#lineOpacity} has data-driven styling.
   */
  @MapboxExperimental
  public var lineOcclusionOpacity: Double? by mutableStateOf(initialLineOcclusionOpacity)
  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. The unit of lineTranslate is in density-independent pixels.
   */
  public var lineTranslate: List<Double>? by mutableStateOf(initialLineTranslate)
  /**
   * Controls the frame of reference for {@link PropertyFactory#lineTranslate}.
   */
  public var lineTranslateAnchor: LineTranslateAnchor? by mutableStateOf(initialLineTranslateAnchor)
  /**
   * The line part between [trim-start, trim-end] will be marked as transparent to make a route vanishing effect. The line trim-off offset is based on the whole line range [0.0, 1.0].
   */
  public var lineTrimOffset: List<Double>? by mutableStateOf(initialLineTrimOffset)

  @Composable
  private fun UpdateLineCap(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineCap = lineCap
  }
  @Composable
  private fun UpdateLineMiterLimit(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineMiterLimit = lineMiterLimit
  }
  @Composable
  private fun UpdateLineRoundLimit(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineRoundLimit = lineRoundLimit
  }
  @Composable
  private fun UpdateLineDasharray(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineDasharray = lineDasharray
  }
  @Composable
  private fun UpdateLineDepthOcclusionFactor(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineDepthOcclusionFactor = lineDepthOcclusionFactor
  }
  @Composable
  private fun UpdateLineEmissiveStrength(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineEmissiveStrength = lineEmissiveStrength
  }
  @Composable
  @OptIn(MapboxExperimental::class)
  private fun UpdateLineOcclusionOpacity(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineOcclusionOpacity = lineOcclusionOpacity
  }
  @Composable
  private fun UpdateLineTranslate(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineTranslate = lineTranslate
  }
  @Composable
  private fun UpdateLineTranslateAnchor(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineTranslateAnchor = lineTranslateAnchor
  }
  @Composable
  private fun UpdateLineTrimOffset(annotationManager: PolylineAnnotationManager) {
    annotationManager.lineTrimOffset = lineTrimOffset
  }

  @Composable
  internal fun UpdateProperties(annotationManager: PolylineAnnotationManager) {
    UpdateLineCap(annotationManager)
    UpdateLineMiterLimit(annotationManager)
    UpdateLineRoundLimit(annotationManager)
    UpdateLineDasharray(annotationManager)
    UpdateLineDepthOcclusionFactor(annotationManager)
    UpdateLineEmissiveStrength(annotationManager)
    UpdateLineOcclusionOpacity(annotationManager)
    UpdateLineTranslate(annotationManager)
    UpdateLineTranslateAnchor(annotationManager)
    UpdateLineTrimOffset(annotationManager)
  }
}

// End of generated file.
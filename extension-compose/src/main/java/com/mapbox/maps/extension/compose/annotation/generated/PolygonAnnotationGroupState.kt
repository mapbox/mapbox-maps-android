// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.style.layers.properties.generated.FillTranslateAnchor
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationManager

/**
 * The state holder for [PolygonAnnotationGroup] properties.
 */
@MapboxExperimental
@Stable
public class PolygonAnnotationGroupState private constructor(
  initialFillAntialias: Boolean?,
  initialFillEmissiveStrength: Double?,
  initialFillTranslate: List<Double>?,
  initialFillTranslateAnchor: FillTranslateAnchor?,
) {
  public constructor() : this(
    initialFillAntialias = null,
    initialFillEmissiveStrength = null,
    initialFillTranslate = null,
    initialFillTranslateAnchor = null,
  )
  /**
   * Whether or not the fill should be antialiased.
   */
  public var fillAntialias: Boolean? by mutableStateOf(initialFillAntialias)
  /**
   * Controls the intensity of light emitted on the source features. The unit of fillEmissiveStrength is in intensity.
   */
  public var fillEmissiveStrength: Double? by mutableStateOf(initialFillEmissiveStrength)
  /**
   * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. The unit of fillTranslate is in density-independent pixels.
   */
  public var fillTranslate: List<Double>? by mutableStateOf(initialFillTranslate)
  /**
   * Controls the frame of reference for {@link PropertyFactory#fillTranslate}.
   */
  public var fillTranslateAnchor: FillTranslateAnchor? by mutableStateOf(initialFillTranslateAnchor)

  @Composable
  private fun UpdateFillAntialias(annotationManager: PolygonAnnotationManager) {
    annotationManager.fillAntialias = fillAntialias
  }
  @Composable
  private fun UpdateFillEmissiveStrength(annotationManager: PolygonAnnotationManager) {
    annotationManager.fillEmissiveStrength = fillEmissiveStrength
  }
  @Composable
  private fun UpdateFillTranslate(annotationManager: PolygonAnnotationManager) {
    annotationManager.fillTranslate = fillTranslate
  }
  @Composable
  private fun UpdateFillTranslateAnchor(annotationManager: PolygonAnnotationManager) {
    annotationManager.fillTranslateAnchor = fillTranslateAnchor
  }

  @Composable
  internal fun UpdateProperties(annotationManager: PolygonAnnotationManager) {
    UpdateFillAntialias(annotationManager)
    UpdateFillEmissiveStrength(annotationManager)
    UpdateFillTranslate(annotationManager)
    UpdateFillTranslateAnchor(annotationManager)
  }
}

// End of generated file.
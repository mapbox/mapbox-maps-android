// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.annotation.internal.generated.PolygonAnnotationManagerNode
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotation
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPolygonAnnotationManager

/**
 * Composable function to add a [PolygonAnnotationGroup] to the Map.
 *
 * The [PolygonAnnotationGroup] is more performant than adding multiple [PolygonAnnotation] individually,
 * because the [PolygonAnnotationGroup] is added to the map as a single layer.
 *
 * [PointAnnotationGroup] and [CircleAnnotationGroup] can also be clustered based on the configuration, see [AnnotationConfig.annotationSourceOptions] and [ClusterOptions] for more details.
 *
 * @param annotations List of [PolygonAnnotationOptions] to be added to the group.
 * @param annotationConfig Configuration for [PolygonAnnotationCluster].
 * @param fillAntialias Whether or not the fill should be antialiased.
 * @param fillEmissiveStrength Controls the intensity of light emitted on the source features. The unit of fillEmissiveStrength is in intensity.
 * @param fillTranslate The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. The unit of fillTranslate is in density-independent pixels.
 * @param fillTranslateAnchor Controls the frame of reference for {@link PropertyFactory#fillTranslate}.
 * @param onClick Callback to be invoked when one of the [PolygonAnnotation] in the cluster is clicked. The clicked [PolygonAnnotation] will be passed as parameter.
 */
@Composable
@MapboxMapComposable
@MapboxExperimental
public fun PolygonAnnotationGroup(
  annotations: List<PolygonAnnotationOptions>,
  annotationConfig: AnnotationConfig? = null,
  fillAntialias: Boolean? = null,
  fillEmissiveStrength: Double? = null,
  fillTranslate: List<Double>? = null,
  fillTranslateAnchor: FillTranslateAnchor? = null,
  onClick: (PolygonAnnotation) -> Boolean = { false },
) {

  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of PolygonAnnotationCluster inside unsupported composable function")

  ComposeNode<PolygonAnnotationManagerNode, MapApplier>(
    factory = {
      val annotationManager =
        mapApplier.mapView.annotations.createPolygonAnnotationManager(annotationConfig)
      PolygonAnnotationManagerNode(annotationManager, onClick)
    },
    update = {
      set(annotations) {
        annotationClusterItems = it
      }
      set(fillAntialias) {
        annotationManager.fillAntialias = it
      }
      set(fillEmissiveStrength) {
        annotationManager.fillEmissiveStrength = it
      }
      set(fillTranslate) {
        annotationManager.fillTranslate = it
      }
      set(fillTranslateAnchor) {
        annotationManager.fillTranslateAnchor = it
      }
      update(onClick) {
        onClicked = it
      }
    }
  )
}
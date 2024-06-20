// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.annotation.internal.generated.CircleAnnotationManagerNode
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotation
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createCircleAnnotationManager

/**
 * Composable function to add a [CircleAnnotationGroup] to the Map.
 *
 * The [CircleAnnotationGroup] is more performant than adding multiple [CircleAnnotation] individually,
 * because the [CircleAnnotationGroup] is added to the map as a single layer.
 *
 * [PointAnnotationGroup] and [CircleAnnotationGroup] can also be clustered based on the configuration, see [AnnotationConfig.annotationSourceOptions] and [ClusterOptions] for more details.
 *
 * @param annotations List of [CircleAnnotationOptions] to be added to the group.
 * @param annotationConfig Configuration for [CircleAnnotationCluster].
 * @param circleEmissiveStrength Controls the intensity of light emitted on the source features. The unit of circleEmissiveStrength is in intensity.
 * @param circlePitchAlignment Orientation of circle when map is pitched.
 * @param circlePitchScale Controls the scaling behavior of the circle when the map is pitched.
 * @param circleTranslate The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. The unit of circleTranslate is in density-independent pixels.
 * @param circleTranslateAnchor Controls the frame of reference for {@link PropertyFactory#circleTranslate}.
 * @param onClick Callback to be invoked when one of the [CircleAnnotation] in the cluster is clicked. The clicked [CircleAnnotation] will be passed as parameter.
 */
@Composable
@MapboxMapComposable
@MapboxExperimental
public fun CircleAnnotationGroup(
  annotations: List<CircleAnnotationOptions>,
  annotationConfig: AnnotationConfig? = null,
  circleEmissiveStrength: Double? = null,
  circlePitchAlignment: CirclePitchAlignment? = null,
  circlePitchScale: CirclePitchScale? = null,
  circleTranslate: List<Double>? = null,
  circleTranslateAnchor: CircleTranslateAnchor? = null,
  onClick: (CircleAnnotation) -> Boolean = { false },
) {

  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of CircleAnnotationCluster inside unsupported composable function")

  ComposeNode<CircleAnnotationManagerNode, MapApplier>(
    factory = {
      val annotationManager =
        mapApplier.mapView.annotations.createCircleAnnotationManager(annotationConfig)
      CircleAnnotationManagerNode(annotationManager, onClick)
    },
    update = {
      set(annotations) {
        annotationClusterItems = it
      }
      set(circleEmissiveStrength) {
        annotationManager.circleEmissiveStrength = it
      }
      set(circlePitchAlignment) {
        annotationManager.circlePitchAlignment = it
      }
      set(circlePitchScale) {
        annotationManager.circlePitchScale = it
      }
      set(circleTranslate) {
        annotationManager.circleTranslate = it
      }
      set(circleTranslateAnchor) {
        annotationManager.circleTranslateAnchor = it
      }
      update(onClick) {
        onClicked = it
      }
    }
  )
}
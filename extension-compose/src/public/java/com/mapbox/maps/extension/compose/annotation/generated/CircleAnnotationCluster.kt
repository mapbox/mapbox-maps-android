// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
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
 * Composable function to add a [CircleAnnotation]Cluster to the Map.
 *
 * @param annotations List of [CircleAnnotationOptions] to be added to the cluster.
 * @param annotationConfig Configuration for [CircleAnnotationCluster].
 * @param circlePitchAlignment Orientation of circle when map is pitched.
 * @param circlePitchScale Controls the scaling behavior of the circle when the map is pitched.
 * @param circleTranslate The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. The unit of circleTranslate is in density-independent pixels.
 * @param circleTranslateAnchor Controls the frame of reference for {@link PropertyFactory#circleTranslate}.
 * @param onClick Callback to be invoked when one of the [CircleAnnotation] in the cluster is clicked. The clicked [CircleAnnotation] will be passed as parameter.
 */
@Composable
@MapboxMapComposable
public fun CircleAnnotationCluster(
  annotations: List<CircleAnnotationOptions>,
  annotationConfig: AnnotationConfig? = null,
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
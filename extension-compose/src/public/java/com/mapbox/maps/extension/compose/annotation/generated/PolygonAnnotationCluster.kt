// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
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
 * Composable function to add a [PolygonAnnotation]Cluster to the Map.
 *
 * @param annotations List of [PolygonAnnotationOptions] to be added to the cluster.
 * @param annotationConfig Configuration for [PolygonAnnotationCluster].
 * @param fillAntialias Whether or not the fill should be antialiased.
 * @param fillTranslate The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. The unit of fillTranslate is in density-independent pixels.
 * @param fillTranslateAnchor Controls the frame of reference for {@link PropertyFactory#fillTranslate}.
 * @param onClick Callback to be invoked when one of the [PolygonAnnotation] in the cluster is clicked. The clicked [PolygonAnnotation] will be passed as parameter.
 */
@Composable
@MapboxMapComposable
public fun PolygonAnnotationCluster(
  annotations: List<PolygonAnnotationOptions>,
  annotationConfig: AnnotationConfig? = null,
  fillAntialias: Boolean? = null,
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
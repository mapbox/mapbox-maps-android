// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.annotation.internal.generated.PointAnnotationManagerNode
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager

/**
 * Composable function to add a [PointAnnotationGroup] to the Map. For convenience, if there's
 * no need to hoist the [pointAnnotationGroupState], use `PointAnnotationGroup(annotations, annotationConfig, onClick, init)` with trailing lambda instead.
 *
 * The [PointAnnotationGroup] is more performant than adding multiple [PointAnnotation] individually,
 * because the [PointAnnotationGroup] is added to the map as a single layer.
 *
 * [PointAnnotationGroup] and [CircleAnnotationGroup] can also be clustered based on the configuration, see [AnnotationConfig.annotationSourceOptions] and [ClusterOptions] for more details.
 *
 * @param annotations List of [PointAnnotationOptions] to be added to the group.
 * @param annotationConfig Configuration for [PointAnnotationGroup].
 * @param onClick Callback to be invoked when one of the [PointAnnotation] in the cluster is clicked. The clicked [PointAnnotation] will be passed as parameter.
 * @param pointAnnotationGroupState The state holder for [PointAnnotation]Group properties.
 */
@Composable
@MapboxMapComposable
@MapboxExperimental
public fun PointAnnotationGroup(
  annotations: List<PointAnnotationOptions>,
  annotationConfig: AnnotationConfig? = null,
  onClick: (PointAnnotation) -> Boolean = { false },
  pointAnnotationGroupState: PointAnnotationGroupState = remember { PointAnnotationGroupState() }
) {

  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of PointAnnotationCluster inside unsupported composable function")

  var annotationManager by remember {
    mutableStateOf<PointAnnotationManager?>(null)
  }

  ComposeNode<PointAnnotationManagerNode, MapApplier>(
    factory = {
      PointAnnotationManagerNode(
        mapApplier.mapView.mapboxMap,
        mapApplier.mapView.annotations.createPointAnnotationManager(annotationConfig).also { annotationManager = it },
        onClick
      )
    },
    update = {
      set(annotations) {
        annotationClusterItems = it
      }
      update(onClick) {
        onClicked = it
      }
    }
  ) {
    key(pointAnnotationGroupState) {
      annotationManager?.let {
        pointAnnotationGroupState.UpdateProperties(it)
      }
    }
  }
}

/**
 * Composable function to add a [PointAnnotationGroup] to the Map.
 *
 * The [PointAnnotationGroup] is more performant than adding multiple [PointAnnotation] individually,
 * because the [PointAnnotationGroup] is added to the map as a single layer.
 *
 * [PointAnnotationGroup] and [CircleAnnotationGroup] can also be clustered based on the configuration, see [AnnotationConfig.annotationSourceOptions] and [ClusterOptions] for more details.
 *
 * @param annotations List of [PointAnnotationOptions] to be added to the group.
 * @param annotationConfig Configuration for [PointAnnotationGroup].
 * @param onClick Callback to be invoked when one of the [PointAnnotation] in the cluster is clicked. The clicked [PointAnnotation] will be passed as parameter.
 * @param init the lambda that will be applied to the remembered [PointAnnotationGroupState].
 */
@Composable
@MapboxMapComposable
@MapboxExperimental
public inline fun PointAnnotationGroup(
  annotations: List<PointAnnotationOptions>,
  annotationConfig: AnnotationConfig? = null,
  noinline onClick: (PointAnnotation) -> Boolean = { false },
  crossinline init: PointAnnotationGroupState.() -> Unit
) {
  PointAnnotationGroup(
    annotations = annotations,
    annotationConfig = annotationConfig,
    onClick = onClick,
    pointAnnotationGroupState = remember { PointAnnotationGroupState() }.apply(init)
  )
}
// End of generated file.
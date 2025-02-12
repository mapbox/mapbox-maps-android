// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
 * @param annotations List of [PointAnnotationOptions] to be added to the group.
 * @param annotationConfig Configuration for [PointAnnotationGroup].
 * @param onClick Callback to be invoked when one of the [PointAnnotation] in the cluster is clicked. The clicked [PointAnnotation] will be passed as parameter.
 * @param pointAnnotationGroupState The state holder for [PointAnnotation]Group properties.
 */
@Composable
@MapboxMapComposable
@Deprecated(
  message = "This method is deprecated, and will be removed in next major release.",
  replaceWith = ReplaceWith("PointAnnotationGroup(annotations, annotationConfig, pointAnnotationGroupState)"),
  level = DeprecationLevel.WARNING
)
public fun PointAnnotationGroup(
  annotations: List<PointAnnotationOptions>,
  annotationConfig: AnnotationConfig? = null,
  onClick: (PointAnnotation) -> Boolean = { false },
  pointAnnotationGroupState: PointAnnotationGroupState = remember { PointAnnotationGroupState() }
) {
  PointAnnotationGroup(
    annotations,
    annotationConfig,
     pointAnnotationGroupState.also {
      it.interactionsState.onClicked(onClick = onClick)
    }
  )
}

/**
 * Composable function to add a [PointAnnotationGroup] to the Map. For convenience, if there's
 * no need to hoist the [pointAnnotationGroupState], use `PointAnnotationGroup(annotations, annotationConfig, onClick, init)` with trailing lambda instead.
 *
 * The [PointAnnotationGroup] is more performant than adding multiple [PointAnnotation] individually,
 * because the [PointAnnotationGroup] is added to the map as a single layer.
 *
 * [PointAnnotationGroup] and [CircleAnnotationGroup] can also be clustered based on the configuration, see [AnnotationConfig.annotationSourceOptions] and [ClusterOptions] for more details.
 * @param annotations List of [PointAnnotationOptions] to be added to the group.
 * @param annotationConfig Configuration for [PointAnnotationGroup].
 * @param pointAnnotationGroupState The state holder for [PointAnnotation]Group properties.
 */
@Composable
@MapboxMapComposable
public fun PointAnnotationGroup(
  annotations: List<PointAnnotationOptions>,
  annotationConfig: AnnotationConfig? = null,
  pointAnnotationGroupState: PointAnnotationGroupState = remember { PointAnnotationGroupState() }
) {

  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of PointAnnotationCluster inside unsupported composable function")

  var annotationManager by remember {
    mutableStateOf<PointAnnotationManager?>(null)
  }

  var annotationManagerNode by remember {
    mutableStateOf<PointAnnotationManagerNode?>(null)
  }

  if (pointAnnotationGroupState.interactionsState.isDraggable) {
    annotations.forEach { it.withDraggable(true) }
  }

  val coroutineScope = rememberCoroutineScope()

  ComposeNode<PointAnnotationManagerNode, MapApplier>(
    factory = {
      PointAnnotationManagerNode(
        mapApplier.mapView.mapboxMap,
        mapApplier.mapView.annotations.createPointAnnotationManager(annotationConfig).also { annotationManager = it },
        coroutineScope,
      ).also { annotationManagerNode = it }
    },
    update = {
      set(annotations) {
        annotationClusterItems = it
      }
    }
  ) {
    key(pointAnnotationGroupState) {
      annotationManager?.let {
        pointAnnotationGroupState.UpdateProperties(it)
      }
    }
  }
  key(pointAnnotationGroupState.interactionsState, annotationManager, annotationManagerNode) {
    if (annotationManager != null && annotationManagerNode != null) {
      annotationManager?.let {
        pointAnnotationGroupState.interactionsState.BindTo(it)
      }
      annotationManagerNode?.let {
        it.currentAnnotations.forEach { annotation ->
          annotation.isDraggable = pointAnnotationGroupState.interactionsState.isDraggable
        }
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
 * @param annotations List of [PointAnnotationOptions] to be added to the group.
 * @param annotationConfig Configuration for [PointAnnotationGroup].
 * @param onClick Callback to be invoked when one of the [PointAnnotation] in the cluster is clicked. The clicked [PointAnnotation] will be passed as parameter.
 * @param init the lambda that will be applied to the remembered [PointAnnotationGroupState].
 */
@Composable
@MapboxMapComposable
@Deprecated(
  message = "This method is deprecated, and will be removed in next major release.",
  replaceWith = ReplaceWith("PointAnnotationGroup(annotations, annotationConfig, init)"),
  level = DeprecationLevel.WARNING
)
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

/**
 * Composable function to add a [PointAnnotationGroup] to the Map.
 *
 * The [PointAnnotationGroup] is more performant than adding multiple [PointAnnotation] individually,
 * because the [PointAnnotationGroup] is added to the map as a single layer.
 *
 * [PointAnnotationGroup] and [CircleAnnotationGroup] can also be clustered based on the configuration, see [AnnotationConfig.annotationSourceOptions] and [ClusterOptions] for more details.
 * @param annotations List of [PointAnnotationOptions] to be added to the group.
 * @param annotationConfig Configuration for [PointAnnotationGroup].
 * @param init the lambda that will be applied to the remembered [PointAnnotationGroupState].
 */
@Composable
@MapboxMapComposable
public inline fun PointAnnotationGroup(
  annotations: List<PointAnnotationOptions>,
  annotationConfig: AnnotationConfig? = null,
  crossinline init: PointAnnotationGroupState.() -> Unit
) {
  PointAnnotationGroup(
    annotations = annotations,
    annotationConfig = annotationConfig,
    pointAnnotationGroupState = remember { PointAnnotationGroupState() }.apply(init)
  )
}

// End of generated file.
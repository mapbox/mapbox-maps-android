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
import com.mapbox.maps.extension.compose.annotation.internal.generated.PolygonAnnotationManagerNode
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotation
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPolygonAnnotationManager

/**
 * Composable function to add a [PolygonAnnotationGroup] to the Map. For convenience, if there's
 * no need to hoist the [polygonAnnotationGroupState], use `PolygonAnnotationGroup(annotations, annotationConfig, onClick, init)` with trailing lambda instead.
 *
 * The [PolygonAnnotationGroup] is more performant than adding multiple [PolygonAnnotation] individually,
 * because the [PolygonAnnotationGroup] is added to the map as a single layer.
 *
 * [PointAnnotationGroup] and [CircleAnnotationGroup] can also be clustered based on the configuration, see [AnnotationConfig.annotationSourceOptions] and [ClusterOptions] for more details.
 * @param annotations List of [PolygonAnnotationOptions] to be added to the group.
 * @param annotationConfig Configuration for [PolygonAnnotationGroup].
 * @param onClick Callback to be invoked when one of the [PolygonAnnotation] in the cluster is clicked. The clicked [PolygonAnnotation] will be passed as parameter.
 * @param polygonAnnotationGroupState The state holder for [PolygonAnnotation]Group properties.
 */
@Composable
@MapboxMapComposable
@Deprecated(
  message = "This method is deprecated, and will be removed in next major release.",
  replaceWith = ReplaceWith("PolygonAnnotationGroup(annotations, annotationConfig, polygonAnnotationGroupState)"),
  level = DeprecationLevel.WARNING
)
public fun PolygonAnnotationGroup(
  annotations: List<PolygonAnnotationOptions>,
  annotationConfig: AnnotationConfig? = null,
  onClick: (PolygonAnnotation) -> Boolean = { false },
  polygonAnnotationGroupState: PolygonAnnotationGroupState = remember { PolygonAnnotationGroupState() }
) {
  PolygonAnnotationGroup(
    annotations,
    annotationConfig,
     polygonAnnotationGroupState.also {
      it.interactionsState.onClicked(onClick = onClick)
    }
  )
}

/**
 * Composable function to add a [PolygonAnnotationGroup] to the Map. For convenience, if there's
 * no need to hoist the [polygonAnnotationGroupState], use `PolygonAnnotationGroup(annotations, annotationConfig, onClick, init)` with trailing lambda instead.
 *
 * The [PolygonAnnotationGroup] is more performant than adding multiple [PolygonAnnotation] individually,
 * because the [PolygonAnnotationGroup] is added to the map as a single layer.
 *
 * [PointAnnotationGroup] and [CircleAnnotationGroup] can also be clustered based on the configuration, see [AnnotationConfig.annotationSourceOptions] and [ClusterOptions] for more details.
 * @param annotations List of [PolygonAnnotationOptions] to be added to the group.
 * @param annotationConfig Configuration for [PolygonAnnotationGroup].
 * @param polygonAnnotationGroupState The state holder for [PolygonAnnotation]Group properties.
 */
@Composable
@MapboxMapComposable
public fun PolygonAnnotationGroup(
  annotations: List<PolygonAnnotationOptions>,
  annotationConfig: AnnotationConfig? = null,
  polygonAnnotationGroupState: PolygonAnnotationGroupState = remember { PolygonAnnotationGroupState() }
) {

  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of PolygonAnnotationCluster inside unsupported composable function")

  var annotationManager by remember {
    mutableStateOf<PolygonAnnotationManager?>(null)
  }

  var annotationManagerNode by remember {
    mutableStateOf<PolygonAnnotationManagerNode?>(null)
  }

  if (polygonAnnotationGroupState.interactionsState.isDraggable) {
    annotations.forEach { it.withDraggable(true) }
  }

  val coroutineScope = rememberCoroutineScope()

  ComposeNode<PolygonAnnotationManagerNode, MapApplier>(
    factory = {
      PolygonAnnotationManagerNode(
        mapApplier.mapView.mapboxMap,
        mapApplier.mapView.annotations.createPolygonAnnotationManager(annotationConfig).also { annotationManager = it },
        coroutineScope,
      ).also { annotationManagerNode = it }
    },
    update = {
      set(annotations) {
        annotationClusterItems = it
      }
    }
  ) {
    key(polygonAnnotationGroupState) {
      annotationManager?.let {
        polygonAnnotationGroupState.UpdateProperties(it)
      }
    }
  }
  key(polygonAnnotationGroupState.interactionsState, annotationManager, annotationManagerNode) {
    if (annotationManager != null && annotationManagerNode != null) {
      annotationManager?.let {
        polygonAnnotationGroupState.interactionsState.BindTo(it)
      }
      annotationManagerNode?.let {
        it.currentAnnotations.forEach { annotation ->
          annotation.isDraggable = polygonAnnotationGroupState.interactionsState.isDraggable
        }
      }
    }
  }
}

/**
 * Composable function to add a [PolygonAnnotationGroup] to the Map.
 *
 * The [PolygonAnnotationGroup] is more performant than adding multiple [PolygonAnnotation] individually,
 * because the [PolygonAnnotationGroup] is added to the map as a single layer.
 *
 * [PointAnnotationGroup] and [CircleAnnotationGroup] can also be clustered based on the configuration, see [AnnotationConfig.annotationSourceOptions] and [ClusterOptions] for more details.
 * @param annotations List of [PolygonAnnotationOptions] to be added to the group.
 * @param annotationConfig Configuration for [PolygonAnnotationGroup].
 * @param onClick Callback to be invoked when one of the [PolygonAnnotation] in the cluster is clicked. The clicked [PolygonAnnotation] will be passed as parameter.
 * @param init the lambda that will be applied to the remembered [PolygonAnnotationGroupState].
 */
@Composable
@MapboxMapComposable
@Deprecated(
  message = "This method is deprecated, and will be removed in next major release.",
  replaceWith = ReplaceWith("PolygonAnnotationGroup(annotations, annotationConfig, init)"),
  level = DeprecationLevel.WARNING
)
public inline fun PolygonAnnotationGroup(
  annotations: List<PolygonAnnotationOptions>,
  annotationConfig: AnnotationConfig? = null,
  noinline onClick: (PolygonAnnotation) -> Boolean = { false },
  crossinline init: PolygonAnnotationGroupState.() -> Unit
) {
  PolygonAnnotationGroup(
    annotations = annotations,
    annotationConfig = annotationConfig,
    onClick = onClick,
    polygonAnnotationGroupState = remember { PolygonAnnotationGroupState() }.apply(init)
  )
}

/**
 * Composable function to add a [PolygonAnnotationGroup] to the Map.
 *
 * The [PolygonAnnotationGroup] is more performant than adding multiple [PolygonAnnotation] individually,
 * because the [PolygonAnnotationGroup] is added to the map as a single layer.
 *
 * [PointAnnotationGroup] and [CircleAnnotationGroup] can also be clustered based on the configuration, see [AnnotationConfig.annotationSourceOptions] and [ClusterOptions] for more details.
 * @param annotations List of [PolygonAnnotationOptions] to be added to the group.
 * @param annotationConfig Configuration for [PolygonAnnotationGroup].
 * @param init the lambda that will be applied to the remembered [PolygonAnnotationGroupState].
 */
@Composable
@MapboxMapComposable
public inline fun PolygonAnnotationGroup(
  annotations: List<PolygonAnnotationOptions>,
  annotationConfig: AnnotationConfig? = null,
  crossinline init: PolygonAnnotationGroupState.() -> Unit
) {
  PolygonAnnotationGroup(
    annotations = annotations,
    annotationConfig = annotationConfig,
    polygonAnnotationGroupState = remember { PolygonAnnotationGroupState() }.apply(init)
  )
}

// End of generated file.
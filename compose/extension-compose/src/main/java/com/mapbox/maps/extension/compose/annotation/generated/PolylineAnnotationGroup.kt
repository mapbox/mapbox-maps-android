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
import com.mapbox.maps.extension.compose.annotation.internal.generated.PolylineAnnotationManagerNode
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotation
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPolylineAnnotationManager

/**
 * Composable function to add a [PolylineAnnotationGroup] to the Map. For convenience, if there's
 * no need to hoist the [polylineAnnotationGroupState], use `PolylineAnnotationGroup(annotations, annotationConfig, onClick, init)` with trailing lambda instead.
 *
 * The [PolylineAnnotationGroup] is more performant than adding multiple [PolylineAnnotation] individually,
 * because the [PolylineAnnotationGroup] is added to the map as a single layer.
 *
 * [PointAnnotationGroup] and [CircleAnnotationGroup] can also be clustered based on the configuration, see [AnnotationConfig.annotationSourceOptions] and [ClusterOptions] for more details.
 * @param annotations List of [PolylineAnnotationOptions] to be added to the group.
 * @param annotationConfig Configuration for [PolylineAnnotationGroup].
 * @param onClick Callback to be invoked when one of the [PolylineAnnotation] in the cluster is clicked. The clicked [PolylineAnnotation] will be passed as parameter.
 * @param polylineAnnotationGroupState The state holder for [PolylineAnnotation]Group properties.
 */
@Composable
@MapboxMapComposable
@Deprecated(
  message = "This method is deprecated, and will be removed in next major release.",
  replaceWith = ReplaceWith("PolylineAnnotationGroup(annotations, annotationConfig, polylineAnnotationGroupState)"),
  level = DeprecationLevel.WARNING
)
public fun PolylineAnnotationGroup(
  annotations: List<PolylineAnnotationOptions>,
  annotationConfig: AnnotationConfig? = null,
  onClick: (PolylineAnnotation) -> Boolean = { false },
  polylineAnnotationGroupState: PolylineAnnotationGroupState = remember { PolylineAnnotationGroupState() }
) {
  PolylineAnnotationGroup(
    annotations,
    annotationConfig,
     polylineAnnotationGroupState.also {
      it.interactionsState.onClicked(onClick = onClick)
    }
  )
}

/**
 * Composable function to add a [PolylineAnnotationGroup] to the Map. For convenience, if there's
 * no need to hoist the [polylineAnnotationGroupState], use `PolylineAnnotationGroup(annotations, annotationConfig, onClick, init)` with trailing lambda instead.
 *
 * The [PolylineAnnotationGroup] is more performant than adding multiple [PolylineAnnotation] individually,
 * because the [PolylineAnnotationGroup] is added to the map as a single layer.
 *
 * [PointAnnotationGroup] and [CircleAnnotationGroup] can also be clustered based on the configuration, see [AnnotationConfig.annotationSourceOptions] and [ClusterOptions] for more details.
 * @param annotations List of [PolylineAnnotationOptions] to be added to the group.
 * @param annotationConfig Configuration for [PolylineAnnotationGroup].
 * @param polylineAnnotationGroupState The state holder for [PolylineAnnotation]Group properties.
 */
@Composable
@MapboxMapComposable
public fun PolylineAnnotationGroup(
  annotations: List<PolylineAnnotationOptions>,
  annotationConfig: AnnotationConfig? = null,
  polylineAnnotationGroupState: PolylineAnnotationGroupState = remember { PolylineAnnotationGroupState() }
) {

  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of PolylineAnnotationCluster inside unsupported composable function")

  var annotationManager by remember {
    mutableStateOf<PolylineAnnotationManager?>(null)
  }

  var annotationManagerNode by remember {
    mutableStateOf<PolylineAnnotationManagerNode?>(null)
  }

  if (polylineAnnotationGroupState.interactionsState.isDraggable) {
    annotations.forEach { it.withDraggable(true) }
  }

  val coroutineScope = rememberCoroutineScope()

  ComposeNode<PolylineAnnotationManagerNode, MapApplier>(
    factory = {
      PolylineAnnotationManagerNode(
        mapApplier.mapView.mapboxMap,
        mapApplier.mapView.annotations.createPolylineAnnotationManager(annotationConfig).also { annotationManager = it },
        coroutineScope,
      ).also { annotationManagerNode = it }
    },
    update = {
      set(annotations) {
        annotationClusterItems = it
      }
    }
  ) {
    key(polylineAnnotationGroupState) {
      annotationManager?.let {
        polylineAnnotationGroupState.UpdateProperties(it)
      }
    }
  }
  key(polylineAnnotationGroupState.interactionsState, annotationManager, annotationManagerNode) {
    if (annotationManager != null && annotationManagerNode != null) {
      annotationManager?.let {
        polylineAnnotationGroupState.interactionsState.BindTo(it)
      }
      annotationManagerNode?.let {
        it.currentAnnotations.forEach { annotation ->
          annotation.isDraggable = polylineAnnotationGroupState.interactionsState.isDraggable
        }
      }
    }
  }
}

/**
 * Composable function to add a [PolylineAnnotationGroup] to the Map.
 *
 * The [PolylineAnnotationGroup] is more performant than adding multiple [PolylineAnnotation] individually,
 * because the [PolylineAnnotationGroup] is added to the map as a single layer.
 *
 * [PointAnnotationGroup] and [CircleAnnotationGroup] can also be clustered based on the configuration, see [AnnotationConfig.annotationSourceOptions] and [ClusterOptions] for more details.
 * @param annotations List of [PolylineAnnotationOptions] to be added to the group.
 * @param annotationConfig Configuration for [PolylineAnnotationGroup].
 * @param onClick Callback to be invoked when one of the [PolylineAnnotation] in the cluster is clicked. The clicked [PolylineAnnotation] will be passed as parameter.
 * @param init the lambda that will be applied to the remembered [PolylineAnnotationGroupState].
 */
@Composable
@MapboxMapComposable
@Deprecated(
  message = "This method is deprecated, and will be removed in next major release.",
  replaceWith = ReplaceWith("PolylineAnnotationGroup(annotations, annotationConfig, init)"),
  level = DeprecationLevel.WARNING
)
public inline fun PolylineAnnotationGroup(
  annotations: List<PolylineAnnotationOptions>,
  annotationConfig: AnnotationConfig? = null,
  noinline onClick: (PolylineAnnotation) -> Boolean = { false },
  crossinline init: PolylineAnnotationGroupState.() -> Unit
) {
  PolylineAnnotationGroup(
    annotations = annotations,
    annotationConfig = annotationConfig,
    onClick = onClick,
    polylineAnnotationGroupState = remember { PolylineAnnotationGroupState() }.apply(init)
  )
}

/**
 * Composable function to add a [PolylineAnnotationGroup] to the Map.
 *
 * The [PolylineAnnotationGroup] is more performant than adding multiple [PolylineAnnotation] individually,
 * because the [PolylineAnnotationGroup] is added to the map as a single layer.
 *
 * [PointAnnotationGroup] and [CircleAnnotationGroup] can also be clustered based on the configuration, see [AnnotationConfig.annotationSourceOptions] and [ClusterOptions] for more details.
 * @param annotations List of [PolylineAnnotationOptions] to be added to the group.
 * @param annotationConfig Configuration for [PolylineAnnotationGroup].
 * @param init the lambda that will be applied to the remembered [PolylineAnnotationGroupState].
 */
@Composable
@MapboxMapComposable
public inline fun PolylineAnnotationGroup(
  annotations: List<PolylineAnnotationOptions>,
  annotationConfig: AnnotationConfig? = null,
  crossinline init: PolylineAnnotationGroupState.() -> Unit
) {
  PolylineAnnotationGroup(
    annotations = annotations,
    annotationConfig = annotationConfig,
    polylineAnnotationGroupState = remember { PolylineAnnotationGroupState() }.apply(init)
  )
}

// End of generated file.
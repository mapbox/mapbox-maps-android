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
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.annotation.internal.generated.PointAnnotationNode
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager

/**
 * Composable function to add a [PointAnnotation] to the Map. For convenience, if there's
 * no need to hoist the [pointAnnotationState], use `PointAnnotation(point, onClick, init)` with trailing lambda instead.
 *
 * @param point The Point of the pointAnnotation, which represents the location of the pointAnnotation on the map.
 * @param onClick Callback to be invoked when the [PointAnnotation] is clicked. The clicked [PointAnnotation] will be passed as parameter.
 * @param pointAnnotationState The state holder for [PointAnnotation] properties.
 */
@Composable
@MapboxMapComposable
@Deprecated(
  message = "This method is deprecated, and will be removed in next major release.",
  replaceWith = ReplaceWith("PointAnnotation(point, pointAnnotationState)"),
  level = DeprecationLevel.WARNING
)
public fun PointAnnotation(
  point: Point,
  onClick: (PointAnnotation) -> Boolean = { false },
  pointAnnotationState: PointAnnotationState = remember { PointAnnotationState() },
) {
  PointAnnotation(
    point = point,
    pointAnnotationState = pointAnnotationState.also {
      it.interactionsState.onClicked(onClick = onClick)
  }
 )
}

/**
 * Composable function to add a [PointAnnotation] to the Map. For convenience, if there's
 * no need to hoist the [pointAnnotationState], use `PointAnnotation(point, init)` with trailing lambda instead.
 *
 * @param point The Point of the pointAnnotation, which represents the location of the pointAnnotation on the map.
 * @param pointAnnotationState The state holder for [PointAnnotation] properties.
 */
@Composable
@MapboxMapComposable
public fun PointAnnotation(
  point: Point,
  pointAnnotationState: PointAnnotationState = remember { PointAnnotationState() },
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of PointAnnotation inside unsupported composable function")

  var annotationNode by remember {
    mutableStateOf<PointAnnotationNode?>(null)
  }

  var annotationManager by remember {
    mutableStateOf<PointAnnotationManager?>(null)
  }

  val coroutineScope = rememberCoroutineScope()

  ComposeNode<PointAnnotationNode, MapApplier>(
    factory = {
      val factoryAnnotationManager = mapApplier.mapView.annotations.createPointAnnotationManager().also { annotationManager = it }
      val annotationOptions: PointAnnotationOptions = PointAnnotationOptions()
        .withPoint(point)
        .withDraggable(pointAnnotationState.interactionsState.isDraggable)
      val annotation = factoryAnnotationManager.create(annotationOptions)
      PointAnnotationNode(mapApplier.mapView.mapboxMap, factoryAnnotationManager, annotation, coroutineScope).also { annotationNode = it }
    },
    update = {
      update(point) {
        this.annotation.point = it
        this.annotationManager.update(annotation)
      }
    }
  ) {
    key(pointAnnotationState) {
      annotationNode?.let {
        pointAnnotationState.UpdateProperties(it)
      }
    }
  }
  key(pointAnnotationState.interactionsState, annotationManager, annotationNode) {
    if (annotationManager != null && annotationNode != null) {
      annotationNode?.annotation?.isDraggable = pointAnnotationState.interactionsState.isDraggable
      annotationManager?.let {
        pointAnnotationState.interactionsState.BindTo(it)
      }
    }
  }
}

/**
 * Composable function to add a [PointAnnotation] to the Map.
 *
 * @param point The Point of the pointAnnotation, which represents the location of the pointAnnotation on the map.
 * @param onClick Callback to be invoked when the [PointAnnotation] is clicked. The clicked [PointAnnotation] will be passed as parameter.
 * @param init the lambda that will be applied to the remembered [PointAnnotationState].
 */
@Composable
@MapboxMapComposable
@Deprecated(
  message = "This method is deprecated, and will be removed in next major release.",
  replaceWith = ReplaceWith("PointAnnotation(point, init)"),
  level = DeprecationLevel.WARNING
)
public inline fun PointAnnotation(
  point: Point,
  noinline onClick: (PointAnnotation) -> Boolean = { false },
  crossinline init: PointAnnotationState.() -> Unit,
) {
  PointAnnotation(
    point = point,
    onClick = onClick,
    pointAnnotationState = remember {
      PointAnnotationState()
    }.apply(init),
  )
}

/**
 * Composable function to add a [PointAnnotation] to the Map.
 *
 * @param point The Point of the pointAnnotation, which represents the location of the pointAnnotation on the map.
 * @param init the lambda that will be applied to the remembered [PointAnnotationState].
 */
@Composable
@MapboxMapComposable
public inline fun PointAnnotation(
  point: Point,
  crossinline init: PointAnnotationState.() -> Unit,
) {
  PointAnnotation(
    point = point,
    pointAnnotationState = remember {
      PointAnnotationState()
    }.apply(init),
  )
}

// End of generated file
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
import com.mapbox.maps.extension.compose.annotation.internal.generated.CircleAnnotationNode
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotation
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createCircleAnnotationManager

/**
 * Composable function to add a [CircleAnnotation] to the Map. For convenience, if there's
 * no need to hoist the [circleAnnotationState], use `CircleAnnotation(point, onClick, init)` with trailing lambda instead.
 *
 * @param point The Point of the circleAnnotation, which represents the location of the circleAnnotation on the map.
 * @param onClick Callback to be invoked when the [CircleAnnotation] is clicked. The clicked [CircleAnnotation] will be passed as parameter.
 * @param circleAnnotationState The state holder for [CircleAnnotation] properties.
 */
@Composable
@MapboxMapComposable
@Deprecated(
  message = "This method is deprecated, and will be removed in next major release.",
  replaceWith = ReplaceWith("CircleAnnotation(point, circleAnnotationState)"),
  level = DeprecationLevel.WARNING
)
public fun CircleAnnotation(
  point: Point,
  onClick: (CircleAnnotation) -> Boolean = { false },
  circleAnnotationState: CircleAnnotationState = remember { CircleAnnotationState() },
) {
  CircleAnnotation(
    point = point,
    circleAnnotationState = circleAnnotationState.also {
      it.interactionsState.onClicked(onClick = onClick)
  }
 )
}

/**
 * Composable function to add a [CircleAnnotation] to the Map. For convenience, if there's
 * no need to hoist the [circleAnnotationState], use `CircleAnnotation(point, init)` with trailing lambda instead.
 *
 * @param point The Point of the circleAnnotation, which represents the location of the circleAnnotation on the map.
 * @param circleAnnotationState The state holder for [CircleAnnotation] properties.
 */
@Composable
@MapboxMapComposable
public fun CircleAnnotation(
  point: Point,
  circleAnnotationState: CircleAnnotationState = remember { CircleAnnotationState() },
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of CircleAnnotation inside unsupported composable function")

  var annotationNode by remember {
    mutableStateOf<CircleAnnotationNode?>(null)
  }

  var annotationManager by remember {
    mutableStateOf<CircleAnnotationManager?>(null)
  }

  val coroutineScope = rememberCoroutineScope()

  ComposeNode<CircleAnnotationNode, MapApplier>(
    factory = {
      val factoryAnnotationManager = mapApplier.mapView.annotations.createCircleAnnotationManager().also { annotationManager = it }
      val annotationOptions: CircleAnnotationOptions = CircleAnnotationOptions()
        .withPoint(point)
        .withDraggable(circleAnnotationState.interactionsState.isDraggable)
      val annotation = factoryAnnotationManager.create(annotationOptions)
      CircleAnnotationNode(mapApplier.mapView.mapboxMap, factoryAnnotationManager, annotation, coroutineScope).also { annotationNode = it }
    },
    update = {
      update(point) {
        this.annotation.point = it
        this.annotationManager.update(annotation)
      }
    }
  ) {
    key(circleAnnotationState) {
      annotationNode?.let {
        circleAnnotationState.UpdateProperties(it)
      }
    }
  }
  key(circleAnnotationState.interactionsState, annotationManager, annotationNode) {
    if (annotationManager != null && annotationNode != null) {
      annotationNode?.annotation?.isDraggable = circleAnnotationState.interactionsState.isDraggable
      annotationManager?.let {
        circleAnnotationState.interactionsState.BindTo(it)
      }
    }
  }
}

/**
 * Composable function to add a [CircleAnnotation] to the Map.
 *
 * @param point The Point of the circleAnnotation, which represents the location of the circleAnnotation on the map.
 * @param onClick Callback to be invoked when the [CircleAnnotation] is clicked. The clicked [CircleAnnotation] will be passed as parameter.
 * @param init the lambda that will be applied to the remembered [CircleAnnotationState].
 */
@Composable
@MapboxMapComposable
@Deprecated(
  message = "This method is deprecated, and will be removed in next major release.",
  replaceWith = ReplaceWith("CircleAnnotation(point, init)"),
  level = DeprecationLevel.WARNING
)
public inline fun CircleAnnotation(
  point: Point,
  noinline onClick: (CircleAnnotation) -> Boolean = { false },
  crossinline init: CircleAnnotationState.() -> Unit,
) {
  CircleAnnotation(
    point = point,
    onClick = onClick,
    circleAnnotationState = remember {
      CircleAnnotationState()
    }.apply(init),
  )
}

/**
 * Composable function to add a [CircleAnnotation] to the Map.
 *
 * @param point The Point of the circleAnnotation, which represents the location of the circleAnnotation on the map.
 * @param init the lambda that will be applied to the remembered [CircleAnnotationState].
 */
@Composable
@MapboxMapComposable
public inline fun CircleAnnotation(
  point: Point,
  crossinline init: CircleAnnotationState.() -> Unit,
) {
  CircleAnnotation(
    point = point,
    circleAnnotationState = remember {
      CircleAnnotationState()
    }.apply(init),
  )
}

// End of generated file
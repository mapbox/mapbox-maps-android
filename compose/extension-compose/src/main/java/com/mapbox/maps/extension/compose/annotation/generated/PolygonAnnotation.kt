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
import com.mapbox.maps.extension.compose.annotation.internal.generated.PolygonAnnotationNode
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotation
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPolygonAnnotationManager

/**
 * Composable function to add a [PolygonAnnotation] to the Map. For convenience, if there's
 * no need to hoist the [polygonAnnotationState], use `PolygonAnnotation(points, onClick, init)` with trailing lambda instead.
 *
 * @param points A list of lists of Point for the fill, which represents the locations of the fill on the map.
 * @param onClick Callback to be invoked when the [PolygonAnnotation] is clicked. The clicked [PolygonAnnotation] will be passed as parameter.
 * @param polygonAnnotationState The state holder for [PolygonAnnotation] properties.
 */
@Composable
@MapboxMapComposable
@Deprecated(
  message = "This method is deprecated, and will be removed in next major release.",
  replaceWith = ReplaceWith("PolygonAnnotation(point, polygonAnnotationState)"),
  level = DeprecationLevel.WARNING
)
public fun PolygonAnnotation(
  points: List<List<Point>>,
  onClick: (PolygonAnnotation) -> Boolean = { false },
  polygonAnnotationState: PolygonAnnotationState = remember { PolygonAnnotationState() },
) {
  PolygonAnnotation(
    points = points,
    polygonAnnotationState = polygonAnnotationState.also {
      it.interactionsState.onClicked(onClick = onClick)
  }
 )
}

/**
 * Composable function to add a [PolygonAnnotation] to the Map. For convenience, if there's
 * no need to hoist the [polygonAnnotationState], use `PolygonAnnotation(points, init)` with trailing lambda instead.
 *
 * @param points A list of lists of Point for the fill, which represents the locations of the fill on the map.
 * @param polygonAnnotationState The state holder for [PolygonAnnotation] properties.
 */
@Composable
@MapboxMapComposable
public fun PolygonAnnotation(
  points: List<List<Point>>,
  polygonAnnotationState: PolygonAnnotationState = remember { PolygonAnnotationState() },
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of PolygonAnnotation inside unsupported composable function")

  var annotationNode by remember {
    mutableStateOf<PolygonAnnotationNode?>(null)
  }

  var annotationManager by remember {
    mutableStateOf<PolygonAnnotationManager?>(null)
  }

  val coroutineScope = rememberCoroutineScope()

  ComposeNode<PolygonAnnotationNode, MapApplier>(
    factory = {
      val factoryAnnotationManager = mapApplier.mapView.annotations.createPolygonAnnotationManager().also { annotationManager = it }
      val annotationOptions: PolygonAnnotationOptions = PolygonAnnotationOptions()
        .withPoints(points)
        .withDraggable(polygonAnnotationState.interactionsState.isDraggable)
      val annotation = factoryAnnotationManager.create(annotationOptions)
      PolygonAnnotationNode(mapApplier.mapView.mapboxMap, factoryAnnotationManager, annotation, coroutineScope).also { annotationNode = it }
    },
    update = {
      update(points) {
        this.annotation.points = it
        this.annotationManager.update(annotation)
      }
    }
  ) {
    key(polygonAnnotationState) {
      annotationNode?.let {
        polygonAnnotationState.UpdateProperties(it)
      }
    }
  }
  key(polygonAnnotationState.interactionsState, annotationManager, annotationNode) {
    if (annotationManager != null && annotationNode != null) {
      annotationNode?.annotation?.isDraggable = polygonAnnotationState.interactionsState.isDraggable
      annotationManager?.let {
        polygonAnnotationState.interactionsState.BindTo(it)
      }
    }
  }
}

/**
 * Composable function to add a [PolygonAnnotation] to the Map.
 *
 * @param points A list of lists of Point for the fill, which represents the locations of the fill on the map.
 * @param onClick Callback to be invoked when the [PolygonAnnotation] is clicked. The clicked [PolygonAnnotation] will be passed as parameter.
 * @param init the lambda that will be applied to the remembered [PolygonAnnotationState].
 */
@Composable
@MapboxMapComposable
@Deprecated(
  message = "This method is deprecated, and will be removed in next major release.",
  replaceWith = ReplaceWith("PolygonAnnotation(point, init)"),
  level = DeprecationLevel.WARNING
)
public inline fun PolygonAnnotation(
  points: List<List<Point>>,
  noinline onClick: (PolygonAnnotation) -> Boolean = { false },
  crossinline init: PolygonAnnotationState.() -> Unit,
) {
  PolygonAnnotation(
    points = points,
    onClick = onClick,
    polygonAnnotationState = remember {
      PolygonAnnotationState()
    }.apply(init),
  )
}

/**
 * Composable function to add a [PolygonAnnotation] to the Map.
 *
 * @param points A list of lists of Point for the fill, which represents the locations of the fill on the map.
 * @param init the lambda that will be applied to the remembered [PolygonAnnotationState].
 */
@Composable
@MapboxMapComposable
public inline fun PolygonAnnotation(
  points: List<List<Point>>,
  crossinline init: PolygonAnnotationState.() -> Unit,
) {
  PolygonAnnotation(
    points = points,
    polygonAnnotationState = remember {
      PolygonAnnotationState()
    }.apply(init),
  )
}

// End of generated file
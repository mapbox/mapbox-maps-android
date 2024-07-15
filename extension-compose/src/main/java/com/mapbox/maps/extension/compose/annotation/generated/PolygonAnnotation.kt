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
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.annotation.internal.generated.PolygonAnnotationNode
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotation
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
public fun PolygonAnnotation(
  points: List<List<Point>>,
  onClick: (PolygonAnnotation) -> Boolean = { false },
  polygonAnnotationState: PolygonAnnotationState = remember { PolygonAnnotationState() },
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of PolygonAnnotation inside unsupported composable function")

  var annotationNode by remember {
    mutableStateOf<PolygonAnnotationNode?>(null)
  }

  ComposeNode<PolygonAnnotationNode, MapApplier>(
    factory = {
      val annotationManager = mapApplier.mapView.annotations.createPolygonAnnotationManager()
      val annotationOptions: PolygonAnnotationOptions = PolygonAnnotationOptions()
        .withPoints(points)
      val annotation = annotationManager.create(annotationOptions)
      PolygonAnnotationNode(mapApplier.mapView.mapboxMap, annotationManager, annotation, onClick).also { annotationNode = it }
    },
    update = {
      update(onClick) {
        onClicked = it
      }
      update(points) {
        annotation.points = it
        annotationManager.update(annotation)
      }
    }
  ) {
    key(polygonAnnotationState) {
      annotationNode?.let {
        polygonAnnotationState.UpdateProperties(it)
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

// End of generated file
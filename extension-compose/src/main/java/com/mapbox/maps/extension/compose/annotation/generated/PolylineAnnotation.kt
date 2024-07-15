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
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.annotation.internal.generated.PolylineAnnotationNode
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotation
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPolylineAnnotationManager

/**
 * Composable function to add a [PolylineAnnotation] to the Map. For convenience, if there's
 * no need to hoist the [polylineAnnotationState], use `PolylineAnnotation(points, onClick, init)` with trailing lambda instead.
 *
 * @param points A list of Point for the line, which represents the locations of the line on the map.
 * @param onClick Callback to be invoked when the [PolylineAnnotation] is clicked. The clicked [PolylineAnnotation] will be passed as parameter.
 * @param polylineAnnotationState The state holder for [PolylineAnnotation] properties.
 */
@Composable
@MapboxMapComposable
@MapboxExperimental
public fun PolylineAnnotation(
  points: List<Point>,
  onClick: (PolylineAnnotation) -> Boolean = { false },
  polylineAnnotationState: PolylineAnnotationState = remember { PolylineAnnotationState() },
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of PolylineAnnotation inside unsupported composable function")

  var annotationNode by remember {
    mutableStateOf<PolylineAnnotationNode?>(null)
  }

  ComposeNode<PolylineAnnotationNode, MapApplier>(
    factory = {
      val annotationManager = mapApplier.mapView.annotations.createPolylineAnnotationManager()
      val annotationOptions: PolylineAnnotationOptions = PolylineAnnotationOptions()
        .withPoints(points)
      val annotation = annotationManager.create(annotationOptions)
      PolylineAnnotationNode(mapApplier.mapView.mapboxMap, annotationManager, annotation, onClick).also { annotationNode = it }
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
    key(polylineAnnotationState) {
      annotationNode?.let {
        polylineAnnotationState.UpdateProperties(it)
      }
    }
  }
}

/**
 * Composable function to add a [PolylineAnnotation] to the Map.
 *
 * @param points A list of Point for the line, which represents the locations of the line on the map.
 * @param onClick Callback to be invoked when the [PolylineAnnotation] is clicked. The clicked [PolylineAnnotation] will be passed as parameter.
 * @param init the lambda that will be applied to the remembered [PolylineAnnotationState].
 */
@Composable
@MapboxMapComposable
@MapboxExperimental
public inline fun PolylineAnnotation(
  points: List<Point>,
  noinline onClick: (PolylineAnnotation) -> Boolean = { false },
  crossinline init: PolylineAnnotationState.() -> Unit,
) {
  PolylineAnnotation(
    points = points,
    onClick = onClick,
    polylineAnnotationState = remember {
      PolylineAnnotationState()
    }.apply(init),
  )
}

// End of generated file
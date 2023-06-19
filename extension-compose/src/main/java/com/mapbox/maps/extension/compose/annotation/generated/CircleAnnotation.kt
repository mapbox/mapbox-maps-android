// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.annotation.internal.generated.CircleAnnotationManagerNode
import com.mapbox.maps.extension.compose.annotation.internal.generated.CircleAnnotationNode
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.compose.internal.RootMapNode
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotation
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createCircleAnnotationManager

/**
 * Composable function to add a [CircleAnnotation] to the Map.
 *
 * @param point The Point of the circleAnnotation, which represents the location of the circleAnnotation on the map.
 * @param circleBlur Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity.
 * @param circleColorInt The fill color of the circle. The property is set as Color Int.
 * @param circleColorString The fill color of the circle. The property is set as Color String.
 * @param circleOpacity The opacity at which the circle will be drawn.
 * @param circleRadius Circle radius. The unit of circleRadius is in density-independent pixels.
 * @param circleStrokeColorInt The stroke color of the circle. The property is set as Color Int.
 * @param circleStrokeColorString The stroke color of the circle. The property is set as Color String.
 * @param circleStrokeOpacity The opacity of the circle's stroke.
 * @param circleStrokeWidth The width of the circle's stroke. Strokes are placed outside of the {@link PropertyFactory#circleRadius}. The unit of circleStrokeWidth is in density-independent pixels.
 * @param onClick Callback to be invoked when the [CircleAnnotation] is clicked. The clicked [CircleAnnotation] will be passed as parameter.
 */
@Composable
@MapboxMapComposable
public fun CircleAnnotation(
  point: Point,
  circleBlur: Double? = null,
  circleColorInt: Int? = null,
  circleColorString: String? = null,
  circleOpacity: Double? = null,
  circleRadius: Double? = null,
  circleStrokeColorInt: Int? = null,
  circleStrokeColorString: String? = null,
  circleStrokeOpacity: Double? = null,
  circleStrokeWidth: Double? = null,
  onClick: (CircleAnnotation) -> Boolean = { false },
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of CircleAnnotation inside unsupported composable function")
  ComposeNode<CircleAnnotationNode, MapApplier>(
    factory = {
      val annotationManager = when (val currentNode = mapApplier.current) {
        // not reachable now as we don't allow inserting annotation node under annotation cluster node.
        is CircleAnnotationManagerNode -> currentNode.annotationManager
        is RootMapNode -> mapApplier.mapView.annotations.createCircleAnnotationManager()
        else -> throw IllegalArgumentException("Illegal use of CircleAnnotation inside an incompatible node: $currentNode.")
      }
      val annotationOptions: CircleAnnotationOptions = CircleAnnotationOptions()
        .withPoint(point)
      circleBlur?.let {
        annotationOptions.withCircleBlur(it)
      }
      circleColorInt?.let {
        annotationOptions.withCircleColor(it)
      }
      circleColorString?.let {
        annotationOptions.withCircleColor(it)
      }
      circleOpacity?.let {
        annotationOptions.withCircleOpacity(it)
      }
      circleRadius?.let {
        annotationOptions.withCircleRadius(it)
      }
      circleStrokeColorInt?.let {
        annotationOptions.withCircleStrokeColor(it)
      }
      circleStrokeColorString?.let {
        annotationOptions.withCircleStrokeColor(it)
      }
      circleStrokeOpacity?.let {
        annotationOptions.withCircleStrokeOpacity(it)
      }
      circleStrokeWidth?.let {
        annotationOptions.withCircleStrokeWidth(it)
      }

      val annotation = annotationManager.create(annotationOptions)
      CircleAnnotationNode(annotationManager, annotation, onClick)
    },
    update = {
      update(onClick) {
        onClicked = it
      }
      update(point) {
        annotation.point = it
        annotationManager.update(annotation)
      }
      update(circleBlur) {
        annotation.circleBlur = it
        annotationManager.update(annotation)
      }
      update(circleColorInt) {
        annotation.circleColorInt = it
        annotationManager.update(annotation)
      }
      update(circleColorString) {
        annotation.circleColorString = it
        annotationManager.update(annotation)
      }
      update(circleOpacity) {
        annotation.circleOpacity = it
        annotationManager.update(annotation)
      }
      update(circleRadius) {
        annotation.circleRadius = it
        annotationManager.update(annotation)
      }
      update(circleStrokeColorInt) {
        annotation.circleStrokeColorInt = it
        annotationManager.update(annotation)
      }
      update(circleStrokeColorString) {
        annotation.circleStrokeColorString = it
        annotationManager.update(annotation)
      }
      update(circleStrokeOpacity) {
        annotation.circleStrokeOpacity = it
        annotationManager.update(annotation)
      }
      update(circleStrokeWidth) {
        annotation.circleStrokeWidth = it
        annotationManager.update(annotation)
      }
    }
  )
}
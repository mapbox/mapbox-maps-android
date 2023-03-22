// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.annotation.internal.generated.PolylineAnnotationManagerNode
import com.mapbox.maps.extension.compose.annotation.internal.generated.PolylineAnnotationNode
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.compose.internal.RootMapNode
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotation
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPolylineAnnotationManager

/**
 * Composable function to add a [PolylineAnnotation] to the Map.
 *
 * @param points A list of Point for the line, which represents the locations of the line on the map.
 * @param lineJoin The display of lines when joining.
 * @param lineBlur Blur applied to the line, in density-independent pixels. The unit of lineBlur is in pixels.
 * @param lineColorInt The color with which the line will be drawn. The property is set as Color Int.
 * @param lineColorString The color with which the line will be drawn. The property is set as Color String.
 * @param lineGapWidth Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap. The unit of lineGapWidth is in density-independent pixels.
 * @param lineOffset The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset. The unit of lineOffset is in density-independent pixels.
 * @param lineOpacity The opacity at which the line will be drawn.
 * @param linePattern Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
 * @param lineWidth Stroke thickness. The unit of lineWidth is in density-independent pixels.
 * @param onClick Callback to be invoked when the [PolylineAnnotation] is clicked. The clicked [PolylineAnnotation] will be passed as parameter.
 */
@Composable
@MapboxMapComposable
public fun PolylineAnnotation(
  points: List<Point>,
  lineJoin: LineJoin? = null,
  lineBlur: Double? = null,
  lineColorInt: Int? = null,
  lineColorString: String? = null,
  lineGapWidth: Double? = null,
  lineOffset: Double? = null,
  lineOpacity: Double? = null,
  linePattern: String? = null,
  lineWidth: Double? = null,
  onClick: (PolylineAnnotation) -> Boolean = { false },
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of PolylineAnnotation inside unsupported composable function")
  ComposeNode<PolylineAnnotationNode, MapApplier>(
    factory = {
      val annotationManager = when (val currentNode = mapApplier.current) {
        // not reachable now as we don't allow inserting annotation node under annotation cluster node.
        is PolylineAnnotationManagerNode -> currentNode.annotationManager
        is RootMapNode -> mapApplier.mapView.annotations.createPolylineAnnotationManager()
        else -> throw IllegalArgumentException("Illegal use of PolylineAnnotation inside an incompatible node: $currentNode.")
      }
      val annotationOptions: PolylineAnnotationOptions = PolylineAnnotationOptions()
        .withPoints(points)
      lineJoin?.let {
        annotationOptions.withLineJoin(it)
      }
      lineBlur?.let {
        annotationOptions.withLineBlur(it)
      }
      lineColorInt?.let {
        annotationOptions.withLineColor(it)
      }
      lineColorString?.let {
        annotationOptions.withLineColor(it)
      }
      lineGapWidth?.let {
        annotationOptions.withLineGapWidth(it)
      }
      lineOffset?.let {
        annotationOptions.withLineOffset(it)
      }
      lineOpacity?.let {
        annotationOptions.withLineOpacity(it)
      }
      linePattern?.let {
        annotationOptions.withLinePattern(it)
      }
      lineWidth?.let {
        annotationOptions.withLineWidth(it)
      }

      val annotation = annotationManager.create(annotationOptions)
      PolylineAnnotationNode(annotationManager, annotation, onClick)
    },
    update = {
      update(onClick) {
        onClicked = it
      }
      update(points) {
        annotation.points = it
        annotationManager.update(annotation)
      }
      update(lineJoin) {
        annotation.lineJoin = it
        annotationManager.update(annotation)
      }
      update(lineBlur) {
        annotation.lineBlur = it
        annotationManager.update(annotation)
      }
      update(lineColorInt) {
        annotation.lineColorInt = it
        annotationManager.update(annotation)
      }
      update(lineColorString) {
        annotation.lineColorString = it
        annotationManager.update(annotation)
      }
      update(lineGapWidth) {
        annotation.lineGapWidth = it
        annotationManager.update(annotation)
      }
      update(lineOffset) {
        annotation.lineOffset = it
        annotationManager.update(annotation)
      }
      update(lineOpacity) {
        annotation.lineOpacity = it
        annotationManager.update(annotation)
      }
      update(linePattern) {
        annotation.linePattern = it
        annotationManager.update(annotation)
      }
      update(lineWidth) {
        annotation.lineWidth = it
        annotationManager.update(annotation)
      }
    }
  )
}
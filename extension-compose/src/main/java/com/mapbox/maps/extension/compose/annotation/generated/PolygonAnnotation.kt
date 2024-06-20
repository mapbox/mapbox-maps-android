// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.annotation.internal.generated.PolygonAnnotationManagerNode
import com.mapbox.maps.extension.compose.annotation.internal.generated.PolygonAnnotationNode
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.compose.internal.RootMapNode
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotation
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPolygonAnnotationManager

/**
 * Composable function to add a [PolygonAnnotation] to the Map.
 *
 * @param points A list of lists of Point for the fill, which represents the locations of the fill on the map.
 * @param fillColorInt The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. The property is set as Color Int.
 * @param fillColorString The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used. The property is set as Color String.
 * @param fillOpacity The opacity of the entire fill layer. In contrast to the {@link PropertyFactory#fillColor}, this value will also affect the 1px stroke around the fill, if the stroke is used.
 * @param fillOutlineColorInt The outline color of the fill. Matches the value of {@link PropertyFactory#fillColor} if unspecified. The property is set as Color Int.
 * @param fillOutlineColorString The outline color of the fill. Matches the value of {@link PropertyFactory#fillColor} if unspecified. The property is set as Color String.
 * @param fillPattern Name of image in sprite to use for drawing image fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
 * @param onClick Callback to be invoked when the [PolygonAnnotation] is clicked. The clicked [PolygonAnnotation] will be passed as parameter.
 */
@Composable
@MapboxMapComposable
@MapboxExperimental
public fun PolygonAnnotation(
  points: List<List<Point>>,
  fillColorInt: Int? = null,
  fillColorString: String? = null,
  fillOpacity: Double? = null,
  fillOutlineColorInt: Int? = null,
  fillOutlineColorString: String? = null,
  fillPattern: String? = null,
  onClick: (PolygonAnnotation) -> Boolean = { false },
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of PolygonAnnotation inside unsupported composable function")
  ComposeNode<PolygonAnnotationNode, MapApplier>(
    factory = {
      val annotationManager = when (val currentNode = mapApplier.current) {
        // not reachable now as we don't allow inserting annotation node under annotation cluster node.
        is PolygonAnnotationManagerNode -> currentNode.annotationManager
        is RootMapNode -> mapApplier.mapView.annotations.createPolygonAnnotationManager()
        else -> throw IllegalArgumentException("Illegal use of PolygonAnnotation inside an incompatible node: $currentNode.")
      }
      val annotationOptions: PolygonAnnotationOptions = PolygonAnnotationOptions()
        .withPoints(points)
      fillColorInt?.let {
        annotationOptions.withFillColor(it)
      }
      fillColorString?.let {
        annotationOptions.withFillColor(it)
      }
      fillOpacity?.let {
        annotationOptions.withFillOpacity(it)
      }
      fillOutlineColorInt?.let {
        annotationOptions.withFillOutlineColor(it)
      }
      fillOutlineColorString?.let {
        annotationOptions.withFillOutlineColor(it)
      }
      fillPattern?.let {
        annotationOptions.withFillPattern(it)
      }

      val annotation = annotationManager.create(annotationOptions)
      PolygonAnnotationNode(annotationManager, annotation, onClick)
    },
    update = {
      update(onClick) {
        onClicked = it
      }
      update(points) {
        annotation.points = it
        annotationManager.update(annotation)
      }
      update(fillColorInt) {
        annotation.fillColorInt = it
        annotationManager.update(annotation)
      }
      update(fillColorString) {
        annotation.fillColorString = it
        annotationManager.update(annotation)
      }
      update(fillOpacity) {
        annotation.fillOpacity = it
        annotationManager.update(annotation)
      }
      update(fillOutlineColorInt) {
        annotation.fillOutlineColorInt = it
        annotationManager.update(annotation)
      }
      update(fillOutlineColorString) {
        annotation.fillOutlineColorString = it
        annotationManager.update(annotation)
      }
      update(fillPattern) {
        annotation.fillPattern = it
        annotationManager.update(annotation)
      }
    }
  )
}
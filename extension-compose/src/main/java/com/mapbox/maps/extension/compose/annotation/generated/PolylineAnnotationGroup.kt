// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.annotation.internal.generated.PolylineAnnotationManagerNode
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotation
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPolylineAnnotationManager

/**
 * Composable function to add a [PolylineAnnotationGroup] to the Map.
 *
 * The [PolylineAnnotationGroup] is more performant than adding multiple [PolylineAnnotation] individually,
 * because the [PolylineAnnotationGroup] is added to the map as a single layer.
 *
 * [PointAnnotationGroup] and [CircleAnnotationGroup] can also be clustered based on the configuration, see [AnnotationConfig.annotationSourceOptions] and [ClusterOptions] for more details.
 *
 * @param annotations List of [PolylineAnnotationOptions] to be added to the group.
 * @param annotationConfig Configuration for [PolylineAnnotationCluster].
 * @param lineCap The display of line endings.
 * @param lineMiterLimit Used to automatically convert miter joins to bevel joins for sharp angles.
 * @param lineRoundLimit Used to automatically convert round joins to miter joins for shallow angles.
 * @param lineDasharray Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to density-independent pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels. The unit of lineDasharray is in line widths.
 * @param lineDepthOcclusionFactor Decrease line layer opacity based on occlusion from 3D objects. Value 0 disables occlusion, value 1 means fully occluded.
 * @param lineEmissiveStrength Emission strength The unit of lineEmissiveStrength is in intensity.
 * @param lineTranslate The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively. The unit of lineTranslate is in density-independent pixels.
 * @param lineTranslateAnchor Controls the frame of reference for {@link PropertyFactory#lineTranslate}.
 * @param lineTrimOffset The line part between [trim-start, trim-end] will be marked as transparent to make a route vanishing effect. The line trim-off offset is based on the whole line range [0.0, 1.0].
 * @param onClick Callback to be invoked when one of the [PolylineAnnotation] in the cluster is clicked. The clicked [PolylineAnnotation] will be passed as parameter.
 */
@Composable
@MapboxMapComposable
@MapboxExperimental
public fun PolylineAnnotationGroup(
  annotations: List<PolylineAnnotationOptions>,
  annotationConfig: AnnotationConfig? = null,
  lineCap: LineCap? = null,
  lineMiterLimit: Double? = null,
  lineRoundLimit: Double? = null,
  lineDasharray: List<Double>? = null,
  lineDepthOcclusionFactor: Double? = null,
  lineEmissiveStrength: Double? = null,
  lineTranslate: List<Double>? = null,
  lineTranslateAnchor: LineTranslateAnchor? = null,
  lineTrimOffset: List<Double>? = null,
  onClick: (PolylineAnnotation) -> Boolean = { false },
) {

  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of PolylineAnnotationCluster inside unsupported composable function")

  ComposeNode<PolylineAnnotationManagerNode, MapApplier>(
    factory = {
      val annotationManager =
        mapApplier.mapView.annotations.createPolylineAnnotationManager(annotationConfig)
      PolylineAnnotationManagerNode(annotationManager, onClick)
    },
    update = {
      set(annotations) {
        annotationClusterItems = it
      }
      set(lineCap) {
        annotationManager.lineCap = it
      }
      set(lineMiterLimit) {
        annotationManager.lineMiterLimit = it
      }
      set(lineRoundLimit) {
        annotationManager.lineRoundLimit = it
      }
      set(lineDasharray) {
        annotationManager.lineDasharray = it
      }
      set(lineDepthOcclusionFactor) {
        annotationManager.lineDepthOcclusionFactor = it
      }
      set(lineEmissiveStrength) {
        annotationManager.lineEmissiveStrength = it
      }
      set(lineTranslate) {
        annotationManager.lineTranslate = it
      }
      set(lineTranslateAnchor) {
        annotationManager.lineTranslateAnchor = it
      }
      set(lineTrimOffset) {
        annotationManager.lineTrimOffset = it
      }
      update(onClick) {
        onClicked = it
      }
    }
  )
}
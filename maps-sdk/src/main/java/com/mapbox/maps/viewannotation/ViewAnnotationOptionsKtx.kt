package com.mapbox.maps.viewannotation

import com.mapbox.geojson.Geometry
import com.mapbox.maps.AnnotatedFeature
import com.mapbox.maps.AnnotatedLayerFeature
import com.mapbox.maps.ViewAnnotationAnchorConfig
import com.mapbox.maps.ViewAnnotationOptions

/**
 * DSL builder functions to create [ViewAnnotationOptions] object.
 */
inline fun viewAnnotationOptions(block: ViewAnnotationOptions.Builder.() -> Unit): ViewAnnotationOptions =
  ViewAnnotationOptions.Builder().apply(block).build()

/**
 * DSL function to set multiple [ViewAnnotationAnchorConfig]'s to [ViewAnnotationOptions.Builder.variableAnchors].
 * Anchors will be applied in the order they are specified.
 */
fun ViewAnnotationOptions.Builder.annotationAnchors(
  vararg blocks: ViewAnnotationAnchorConfig.Builder.() -> Unit
): ViewAnnotationOptions.Builder =
  variableAnchors(
    listOf(*blocks.map { ViewAnnotationAnchorConfig.Builder().apply(it).build() }.toTypedArray())
  )

/**
 * DSL function to set single [ViewAnnotationAnchorConfig]  to [ViewAnnotationOptions.Builder.variableAnchors].
 */
inline fun ViewAnnotationOptions.Builder.annotationAnchor(block: ViewAnnotationAnchorConfig.Builder.() -> Unit): ViewAnnotationOptions.Builder =
  variableAnchors(listOf(ViewAnnotationAnchorConfig.Builder().apply(block).build()))

/**
 * DSL function to set [AnnotatedLayerFeature] to [ViewAnnotationOptions.Builder.annotatedFeature].
 */
inline fun ViewAnnotationOptions.Builder.annotatedLayerFeature(
  layerId: String,
  block: AnnotatedLayerFeature.Builder.() -> Unit = {}
): ViewAnnotationOptions.Builder =
  annotatedFeature(
    AnnotatedFeature.valueOf(
      AnnotatedLayerFeature.Builder().layerId(layerId).apply(block).build()
    )
  )

/**
 * DSL function to set [Geometry] to [ViewAnnotationOptions.Builder.annotatedFeature].
 */
fun ViewAnnotationOptions.Builder.geometry(geometry: Geometry): ViewAnnotationOptions.Builder =
  annotatedFeature(AnnotatedFeature.valueOf(geometry))
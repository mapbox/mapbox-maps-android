package com.mapbox.maps.plugin.location

/**
 * Option class that configs and defines the default values for ModelLayerRender
 */
@Deprecated("Location Plugin is deprecated, use Location Component Plugin instead.")
data class LocationModelLayerOptions(
  /**
   * The url of this model, in gltf format.
   */
  val modelUrl: String,
  /**
   * The position of this model
   */
  val position: List<Double> = listOf(0.0, 0.0),
  /**
   * The scale of this model
   */
  val modelScale: List<Double> = listOf(1.0, 1.0, 1.0),
  /**
   * The rotation of this model
   */
  val modelRotation: List<Double> = listOf(0.0, 0.0, 90.0),
  /**
   * The opacity of this model
   */
  val modelOpacity: Double = 1.0
)
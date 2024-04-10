package com.mapbox.maps.extension.compose.style

import com.mapbox.maps.MapboxExperimental
import java.util.UUID

/**
 * Utility to generate a random ID.
 */
@MapboxExperimental
public object IdGenerator {
  private const val SOURCE_ID_PREFIX = "COMPOSE_SOURCE_ID_"
  private const val LAYER_ID_PREFIX = "COMPOSE_LAYER_ID_"

  private fun generateRandomId(prefix: String): String =
    prefix + "_" + UUID.randomUUID().toString()

  /**
   * Generate a random source id based on UUID.
   */
  @MapboxExperimental
  public fun generateRandomSourceId(sourceType: String): String {
    return generateRandomId(SOURCE_ID_PREFIX + sourceType)
  }

  /**
   * Generate a random layer id based on UUID.
   */
  @MapboxExperimental
  public fun generateRandomLayerId(layerType: String): String {
    return generateRandomId(LAYER_ID_PREFIX + layerType)
  }
}
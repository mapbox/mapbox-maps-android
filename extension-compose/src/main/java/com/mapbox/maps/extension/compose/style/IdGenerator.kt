package com.mapbox.maps.extension.compose.style

import java.util.UUID

/**
 * Utility to generate a random ID.
 */
public object IdGenerator {
  private const val SOURCE_ID_PREFIX = "COMPOSE_SOURCE_ID_"
  private const val LAYER_ID_PREFIX = "COMPOSE_LAYER_ID_"
  private const val LIGHT_ID_PREFIX = "COMPOSE_LIGHT_ID_"

  private fun generateRandomId(prefix: String): String =
    prefix + "_" + UUID.randomUUID().toString()

  /**
   * Generate a random source id based on UUID.
   */
  public fun generateRandomSourceId(sourceType: String): String {
    return generateRandomId(SOURCE_ID_PREFIX + sourceType)
  }

  /**
   * Generate a random layer id based on UUID.
   */
  public fun generateRandomLayerId(layerType: String): String {
    return generateRandomId(LAYER_ID_PREFIX + layerType)
  }

  /**
   * Generate a random light id based on UUID.
   */
  public fun generateRandomLightId(lightType: String): String {
    return generateRandomId(LIGHT_ID_PREFIX + lightType)
  }
}
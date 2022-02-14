// This file is generated.

package com.mapbox.maps.plugin

import android.graphics.drawable.Drawable

/**
 * Configures the directions in which the map is allowed to move during a scroll gesture.
 *
 * @param value String value of this property
 */
enum class ScrollMode(val value: String) {
  /**
   * The map may only move horizontally.
   */
  HORIZONTAL("horizontal"),
  /**
   * The map may only move vertically.
   */
  VERTICAL("vertical"),
  /**
   * The map may move both horizontally and vertically.
   */
  HORIZONTAL_AND_VERTICAL("horizontal-and-vertical"),
}

/**
 * Sealed class representing a location-puck.
 */
sealed class LocationPuck

/**
 * Definition of a location_puck_2_d.
 */
data class LocationPuck2D @JvmOverloads constructor(
  /**
   * Name of image in sprite to use as the top of the location indicator.
   */
  var topImage: Drawable? = null,
  /**
   * Name of image in sprite to use as the middle of the location indicator.
   */
  var bearingImage: Drawable? = null,
  /**
   * Name of image in sprite to use as the background of the location indicator.
   */
  var shadowImage: Drawable? = null,
  /**
   * The scale expression of the images. If defined, it will be applied to all the three images.
   */
  var scaleExpression: String? = null,
) : LocationPuck()

/**
 * Definition of a location_puck_3_d.
 */
data class LocationPuck3D @JvmOverloads constructor(
  /**
   * An URL for the model file in gltf format.
   */
  var modelUri: String,
  /**
   * The position of the model.
   */
  var position: List<Float> = listOf(0f, 0f),
  /**
   * The opacity of the model.
   */
  var modelOpacity: Float = 1f,
  /**
   * The scale of the model.
   */
  var modelScale: List<Float> = listOf(1f, 1f, 1f),
  /**
   * The scale expression of the model, which will overwrite the default scale expression that keeps the model size constant during zoom.
   */
  var modelScaleExpression: String? = null,
  /**
   * The translation of the model [lon, lat, z]
   */
  var modelTranslation: List<Float> = listOf(0f, 0f, 0f),
  /**
   * The rotation of the model.
   */
  var modelRotation: List<Float> = listOf(0f, 0f, 90f),
) : LocationPuck()

// End of generated file.
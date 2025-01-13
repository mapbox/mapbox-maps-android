// This file is generated.

package com.mapbox.maps.plugin

import android.graphics.Color
import android.os.Parcelable
import com.mapbox.maps.ImageHolder
import com.mapbox.maps.MapboxExperimental
import kotlinx.parcelize.Parcelize

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
 * The enum controls how the puck is oriented
 *
 * @param value String value of this property
 */
enum class PuckBearing(val value: String) {
  /**
   * Orients the puck to match the direction in which the device is facing.
   */
  HEADING("heading"),
  /**
   * Orients the puck to match the direction in which the device is moving.
   */
  COURSE("course"),
}

/**
 * Sealed class representing a location-puck.
 */
@Parcelize
sealed class LocationPuck : Parcelable

/**
 * Definition of a location_puck_2_d.
 */
data class LocationPuck2D @JvmOverloads constructor(
  /**
   * Name of image in sprite to use as the top of the location indicator.
   */
  var topImage: ImageHolder? = null,
  /**
   * Name of image in sprite to use as the middle of the location indicator.
   */
  var bearingImage: ImageHolder? = null,
  /**
   * Name of image in sprite to use as the background of the location indicator.
   */
  var shadowImage: ImageHolder? = null,
  /**
   * The scale expression of the images. If defined, it will be applied to all the three images.
   */
  var scaleExpression: String? = null,
  /**
   * The opacity of the entire location puck Default value: 1. Value range: [0, 1]
   */
  var opacity: Float = 1f,
) : LocationPuck()

/**
 * Definition of a location_puck_3_d.
 */
@OptIn(MapboxExperimental::class)
data class LocationPuck3D @JvmOverloads constructor(
  /**
   * An URL for the model file in gltf format. Default value: null.
   */
  var modelUri: String,
  /**
   * The position of the model. Default value: [0,0].
   */
  var position: List<Float> = listOf(0f, 0f),
  /**
   * The opacity of the model. Default value: 1. Value range: [0, 1]
   */
  var modelOpacity: Float = 1f,
  /**
   * The scale of the model. Default value: [1,1,1].
   */
  var modelScale: List<Float> = listOf(1f, 1f, 1f),
  /**
   * The scale expression of the model, which will overwrite the default scale expression that keeps the model size constant during zoom.
   */
  var modelScaleExpression: String? = null,
  /**
   * The translation of the model [lon, lat, z] Default value: [0,0,0].
   */
  var modelTranslation: List<Float> = listOf(0f, 0f, 0f),
  /**
   * The rotation of the model. Default value: [0,0,90].
   */
  var modelRotation: List<Float> = listOf(0f, 0f, 90f),
  /**
   * Enable/Disable shadow casting for the 3D location puck. Default value: true.
   */
  @MapboxExperimental
  var modelCastShadows: Boolean = true,
  /**
   * Enable/Disable shadow receiving for the 3D location puck. Default value: true.
   */
  @MapboxExperimental
  var modelReceiveShadows: Boolean = true,
  /**
   * Defines scaling mode. Only applies to location-indicator type layers. Default value: "map".
   */
  @MapboxExperimental
  var modelScaleMode: ModelScaleMode = ModelScaleMode.VIEWPORT,
  /**
   * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are only supported as a global layer value (and not for each feature) when using GeoJSON or vector tile as the model layer source. Default value: 1. Value range: [0, 5]
   */
  var modelEmissiveStrength: Float = 1f,
  /**
   * The emissive strength expression of the model, which will overwrite the default model emissive strength.
   */
  var modelEmissiveStrengthExpression: String? = null,
  /**
   * The opacity expression of the model, which will overwrite the default model opacity.
   */
  var modelOpacityExpression: String? = null,
  /**
   * The rotation expression of the model, which will overwrite the default model rotation.
   */
  var modelRotationExpression: String? = null,
  /**
   * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: "#ffffff".
   */
  var modelColor: Int = Color.parseColor("#ffffff"),
  /**
   * The color expression of the model, which will overwrite the default model color.
   */
  var modelColorExpression: String? = null,
  /**
   * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix. Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source. Default value: 0. Value range: [0, 1]
   */
  var modelColorMixIntensity: Float = 0f,
  /**
   * The color mix expression of the model, which will overwrite the default model color mix.
   */
  var modelColorMixIntensityExpression: String? = null,
  /**
   * The material overrides for the model. Default value: [].
   */
  @MapboxExperimental
  var materialOverrides: List<String> = emptyList(),
  /**
   * The node overrides for the model. Default value: [].
   */
  @MapboxExperimental
  var nodeOverrides: List<String> = emptyList(),
  /**
   * Selects the base of the model. Some modes might require precomputed elevation data in the tileset. Default value: "ground".
   */
  @MapboxExperimental
  var modelElevationReference: ModelElevationReference = ModelElevationReference.GROUND,
) : LocationPuck()

/**
 * Defines scaling mode. Only applies to location-indicator type layers.
 *
 * @param value String value of this property
 */
@MapboxExperimental
enum class ModelScaleMode(val value: String) {
  /**
   * Model is scaled so that it's always the same size relative to other map features. The property model-scale specifies how many meters each unit in the model file should cover.
   */
  MAP("map"),
  /**
   * Model is scaled so that it's always the same size on the screen. The property model-scale specifies how many pixels each unit in model file should cover.
   */
  VIEWPORT("viewport"),
}

/**
 * Selects the base of the model. Some modes might require precomputed elevation data in the tileset.
 *
 * @param value String value of this property
 */
@MapboxExperimental
enum class ModelElevationReference(val value: String) {
  /**
   * Elevated rendering is enabled. Use this mode to elevate lines relative to the sea level.
   */
  SEA("sea"),
  /**
   * Elevated rendering is enabled. Use this mode to elevate lines relative to the ground's height below them.
   */
  GROUND("ground"),
}

// End of generated file.
package com.mapbox.maps.plugin.locationcomponent.model

import android.annotation.SuppressLint
import androidx.annotation.ColorInt
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.logE
import com.mapbox.maps.plugin.LocationPuck3D
import com.mapbox.maps.plugin.delegates.MapFeatureStateDelegate
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.MODEL_SOURCE
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPluginImpl
import com.mapbox.maps.plugin.locationcomponent.ModelSourceWrapper.Companion.DEFAULT_MODEL_NAME
import com.mapbox.maps.plugin.locationcomponent.model.AnimatableModel.Companion.DEFAULT_COLOR
import com.mapbox.maps.plugin.locationcomponent.model.AnimatableModel.Companion.DEFAULT_COLOR_MIX_INTENSITY
import com.mapbox.maps.plugin.locationcomponent.model.AnimatableModel.Companion.DEFAULT_EMISSIVE_STRENGTH
import com.mapbox.maps.plugin.locationcomponent.model.AnimatableModel.Companion.DEFAULT_OPACITY
import com.mapbox.maps.plugin.locationcomponent.model.AnimatableModel.Companion.DEFAULT_ROTATION

/**
 * Create a [AnimatableModel] with the model uri and [ModelPart] that can be used with the location component.
 *
 * The [ModelPart] can be used separately to style the [AnimatableModel].
 *
 * @param modelUri the model uri
 * @param modelParts a list of [ModelPart] that provides node or material override to the model, which can be styled separately.
 *
 * @return the [AnimatableModel] that can be used to setup the [LocationPuck3D].
 */
@MapboxExperimental
fun LocationComponentPlugin.createAnimatableModel(
  modelUri: String,
  modelParts: List<ModelPart>,
): AnimatableModel {
  val locationComponentImpl = this as LocationComponentPluginImpl
  return AnimatableModel(
    modelUri = modelUri,
    modelParts = modelParts
  ).also { locationComponentImpl.bindToAnimatableModel(it) }
}

/**
 * A model part is the base class for either a [ModelMaterialPart] or [ModelNodePart].
 *
 * It contains context of the map, so can be used to interact with map feature states.
 *
 * @param featureStateId the feature state id to drive the associated part overrides.
 */
@MapboxExperimental
abstract class ModelPart(val featureStateId: String) {
  protected var updateFeatureState: (state: Value) -> Boolean = {
    // default implementation does not apply feature state, and return false to indicate update is not done
    false
  }

  internal fun bindTo(
    mapFeatureStateDelegate: (state: Value) -> Boolean
  ) {
    this.updateFeatureState = mapFeatureStateDelegate
  }
}

/**
 * Model node part can be transformed, e.g. rotated etc.
 *
 * @param featureStateId the unique feature state id to drive the associated part overrides in [nodeOverrides].
 * @param nodeOverrides the associated node ids from the model that is overridable.
 */
@MapboxExperimental
open class ModelNodePart(
  featureStateId: String,
  val nodeOverrides: List<String>,
) : ModelPart(featureStateId) {

  /**
   * Construct a simple [ModelNodePart] with one node override id, it will be used as the feature
   * state id for updating this node.
   *
   * @param nodeOverride the associated node id from the model that is overridable.
   */
  constructor(nodeOverride: String) : this(nodeOverride, listOf(nodeOverride))

  /**
   * The rotation of the node overrides. A list of 3 doubles are required, the 3 number represents
   * the rotation on x, y, z axis in degrees.
   */
  @MapboxExperimental
  var rotation: List<Double> = DEFAULT_ROTATION
    set(value) {
      field = value
      updateFeatureState(
        Value(
          hashMapOf(
            "$featureStateId-rotation" to Value(value.map(::Value))
          )
        )
      )
    }
}

/**
 * Model material part can be applied with color or emission effect.
 *
 * @param featureStateId the unique feature state id to drive the associated material part overrides in [materialOverrides].
 * @param materialOverrides the associated material ids from the model that is overridable.
 */
@MapboxExperimental
open class ModelMaterialPart(
  featureStateId: String,
  val materialOverrides: List<String>,
) : ModelPart(featureStateId) {

  /**
   * Construct a simple [ModelMaterialPart] with only one material override, the material override will
   * also be used as the feature state id for updating the material.
   *
   * @param materialOverride the associated material id from the model that is overridable.
   */
  constructor(materialOverride: String) : this(materialOverride, listOf(materialOverride))

  /**
   * The color of the [ModelMaterialPart].
   */
  @MapboxExperimental
  var color: String = DEFAULT_COLOR
    set(value) {
      field = value
      updateFeatureState(
        Value(
          hashMapOf(
            "$featureStateId-color" to Value(value),
          )
        )
      )
    }

  /**
   * The color mix intensity of the [ModelMaterialPart].
   *
   * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors.
   * Higher number will present a higher model-color contribution in mix.
   * Default value: 0. Value range: [0, 1]
   */
  @MapboxExperimental
  var colorMixIntensity: Double = DEFAULT_COLOR_MIX_INTENSITY
    set(value) {
      field = value
      updateFeatureState(
        Value(
          hashMapOf(
            "$featureStateId-color-mix-intensity" to Value(value)
          )
        )
      )
    }

  /**
   * Update color of the [ModelMaterialPart].
   *
   * Note: color-mix-intensity will be set to 1.0 automatically when the chosen color is set, you
   * can overwrite it by specifying [colorMixIntensity] or call [colorMixIntensity] afterwards.
   *
   * @param color the color to be set to the [ModelMaterialPart].
   */
  @MapboxExperimental
  fun updateColor(@ColorInt color: Int, colorMixIntensity: Double = 1.0) {
    this.color = ColorUtils.colorToRgbaString(color)
    this.colorMixIntensity = colorMixIntensity
  }
  /**
   * Reset the color to the default value.
   */
  @MapboxExperimental
  fun resetColor() {
    colorMixIntensity = 0.0
  }

  /**
   * The color emission of the [ModelMaterialPart].
   */
  @MapboxExperimental
  var emissiveStrength: Double = DEFAULT_EMISSIVE_STRENGTH
    set(value) {
      field = value
      updateFeatureState(
        Value(
          hashMapOf(
            "$featureStateId-emission" to Value(value)
          )
        )
      )
    }

  /**
   * The opacity of the [ModelMaterialPart].
   */
  @MapboxExperimental
  var opacity: Double = DEFAULT_OPACITY
    set(value) {
      field = value
      updateFeatureState(
        Value(
          hashMapOf(
            "$featureStateId-opacity" to Value(value)
          )
        )
      )
    }
}

/**
 * Animatable model represents a 3D model with customisable [ModelPart]s, it also can be used to
 * setup [LocationPuck3D] to work with location component.
 */
@MapboxExperimental
class AnimatableModel internal constructor(
  /**
   * The URI of the model.
   */
  val modelUri: String,
  private val modelParts: List<ModelPart>,
) {

  private var mapFeatureStateDelegate: MapFeatureStateDelegate? = null

  private fun updateFeatureState(state: Value): Boolean {
    mapFeatureStateDelegate?.setFeatureState(
      sourceId = MODEL_SOURCE,
      featureId = DEFAULT_MODEL_NAME,
      state = state
    ) {
      it.onError {
        logE(TAG, "Update feature state error: $it")
      }
    } ?: return false
    return true
  }

  internal fun bindTo(
    mapFeatureStateDelegate: MapFeatureStateDelegate
  ) {
    this.mapFeatureStateDelegate = mapFeatureStateDelegate
    modelParts.forEach {
      it.bindTo(::updateFeatureState)
    }
  }

  /**
   * The rotation of the model, except for the model parts defined in [ModelNodePart.nodeOverrides].
   */
  @MapboxExperimental
  var rotation: List<Double> = DEFAULT_ROTATION
    set(value) {
      field = value
      updateFeatureState(
        Value(
          hashMapOf(
            DEFAULT_ROTATION_FEATURE_STATE_KEY to literal(value)
          )
        )
      )
    }

  /**
   * The opacity of the model, except for the model parts defined in [ModelMaterialPart.materialOverrides].
   */
  @MapboxExperimental
  var opacity: Double = DEFAULT_OPACITY
    set(value) {
      field = value
      updateFeatureState(
        Value(
          hashMapOf(
            DEFAULT_OPACITY_FEATURE_STATE_KEY to Value(value)
          )
        )
      )
    }

  /**
   * The emissive strength of the model, except for the model parts defined in [ModelMaterialPart.materialOverrides].
   */
  @MapboxExperimental
  var emissiveStrength: Double = DEFAULT_EMISSIVE_STRENGTH
    set(value) {
      field = value
      updateFeatureState(
        Value(
          hashMapOf(
            DEFAULT_EMISSIVE_STRENGTH_FEATURE_STATE_KEY to Value(value)
          )
        )
      )
    }

  /**
   * The color mix intensity of the model, except for the model parts defined in [ModelMaterialPart.materialOverrides].
   */
  @MapboxExperimental
  var colorMixIntensity: Double = DEFAULT_COLOR_MIX_INTENSITY
    set(value) {
      field = value
      updateFeatureState(
        Value(
          hashMapOf(
            DEFAULT_COLOR_MIX_INTENSITY_FEATURE_STATE_KEY to Value(value)
          )
        )
      )
    }

  /**
   * The color of the model, except for the model parts defined in [ModelMaterialPart.materialOverrides].
   */
  @MapboxExperimental
  var color: String = DEFAULT_COLOR
    set(value) {
      field = value
      updateFeatureState(
        Value(
          hashMapOf(
            DEFAULT_COLOR_FEATURE_STATE_KEY to Value(value)
          )
        )
      )
    }

  /**
   * Create the [LocationPuck3D] with the model uri and necessary layer properties to
   * allow updating the [ModelPart] in the runtime.
   *
   * Note: You can use [LocationPuck3D.copy] method if you need further customising the [LocationPuck3D]'s
   * properties, but do not provide overrides to [LocationPuck3D.modelColor], [LocationPuck3D.modelColorMixIntensity],
   * [LocationPuck3D.modelRotation], [LocationPuck3D.modelEmissiveStrength], [LocationPuck3D.modelOpacity] or their expressions. Otherwise
   * it might disable the model parts customisation.
   *
   * @return [LocationPuck3D]
   */
  @SuppressLint("IncorrectNumberOfArgumentsInExpression")
  @MapboxExperimental
  fun getLocationPuck3D(): LocationPuck3D {
    return LocationPuck3D(
      modelUri = modelUri,
      modelRotationExpression = Expression.match {
        get(PART_KEY)
        modelParts.filterIsInstance<ModelNodePart>().forEach { nodePart ->
          nodePart.nodeOverrides.forEach {
            literal(it)
            switchCase {
              eq {
                typeofExpression {
                  featureState {
                    literal("${nodePart.featureStateId}-rotation")
                  }
                }
                literal("array<number, 3>")
              }
              featureState {
                literal("${nodePart.featureStateId}-rotation")
              }
              literal(nodePart.rotation)
            }
          }
        }
        switchCase {
          eq {
            typeofExpression {
              featureState {
                literal(DEFAULT_ROTATION_FEATURE_STATE_KEY)
              }
            }
            literal("array<number, 3>")
          }
          featureState {
            literal(DEFAULT_ROTATION_FEATURE_STATE_KEY)
          }
          literal(rotation)
        }
      }.toJson(),
      modelColorExpression = Expression.match {
        get(PART_KEY)
        modelParts.filterIsInstance<ModelMaterialPart>().forEach { materialPart ->
          materialPart.materialOverrides.forEach {
            literal(it)
            toColor {
              featureState {
                literal("${materialPart.featureStateId}-color")
              }
              literal(materialPart.color)
            }
          }
        }
        toColor {
          featureState {
            literal(DEFAULT_COLOR_FEATURE_STATE_KEY)
          }
          literal(color)
        }
      }.toJson(),
      modelColorMixIntensityExpression = Expression.match {
        get(PART_KEY)
        modelParts.filterIsInstance<ModelMaterialPart>().forEach { materialPart ->
          materialPart.materialOverrides.forEach {
            literal(it)
            number {
              featureState {
                literal("${materialPart.featureStateId}-color-mix-intensity")
              }
              literal(materialPart.colorMixIntensity)
            }
          }
        }
        number {
          featureState {
            literal(DEFAULT_COLOR_MIX_INTENSITY_FEATURE_STATE_KEY)
          }
          literal(colorMixIntensity)
        }
      }.toJson(),
      modelEmissiveStrengthExpression = Expression.match {
        get(PART_KEY)
        modelParts.filterIsInstance<ModelMaterialPart>().forEach { materialPart ->
          materialPart.materialOverrides.forEach {
            literal(it)
            number {
              featureState {
                literal("${materialPart.featureStateId}-emission")
              }
              literal(materialPart.emissiveStrength)
            }
          }
        }
        number {
          featureState {
            literal(DEFAULT_EMISSIVE_STRENGTH_FEATURE_STATE_KEY)
          }
          literal(emissiveStrength)
        }
      }.toJson(),
      modelOpacityExpression = Expression.match {
        get(PART_KEY)
        modelParts.filterIsInstance<ModelMaterialPart>().forEach { materialPart ->
          materialPart.materialOverrides.forEach {
            literal(it)
            number {
              featureState {
                literal("${materialPart.featureStateId}-opacity")
              }
              literal(materialPart.opacity)
            }
          }
        }
        number {
          featureState {
            literal(DEFAULT_OPACITY_FEATURE_STATE_KEY)
          }
          literal(opacity)
        }
      }.toJson(),
      materialOverrides = modelParts.filterIsInstance<ModelMaterialPart>()
        .flatMap { it.materialOverrides },
      nodeOverrides = modelParts.filterIsInstance<ModelNodePart>().flatMap { it.nodeOverrides }
    )
  }

  internal companion object {
    private const val TAG = "AnimatableModel"
    private const val PART_KEY = "part"
    private const val DEFAULT_ROTATION_FEATURE_STATE_KEY = "MBX_default_rotation"
    private const val DEFAULT_OPACITY_FEATURE_STATE_KEY = "MBX_default_opacity"
    private const val DEFAULT_EMISSIVE_STRENGTH_FEATURE_STATE_KEY = "MBX_default_emissive_strength"
    private const val DEFAULT_COLOR_MIX_INTENSITY_FEATURE_STATE_KEY = "MBX_default_color_mix_intensity"
    private const val DEFAULT_COLOR_FEATURE_STATE_KEY = "MBX_default_color"
    internal const val DEFAULT_COLOR = "#ffffff"
    internal const val DEFAULT_COLOR_MIX_INTENSITY = 0.0
    internal const val DEFAULT_EMISSIVE_STRENGTH = 0.0
    internal const val DEFAULT_OPACITY = 1.0
    internal val DEFAULT_ROTATION = listOf(0.0, 0.0, 0.0)
  }
}
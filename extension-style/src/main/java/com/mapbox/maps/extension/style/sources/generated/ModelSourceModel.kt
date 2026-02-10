// This file is generated.

package com.mapbox.maps.extension.style.sources.generated

import androidx.annotation.ColorInt
import androidx.annotation.UiThread
import com.mapbox.bindgen.Value
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.sources.Source
import com.mapbox.maps.extension.style.utils.ColorUtils.colorToRgbaString
import com.mapbox.maps.extension.style.utils.TypeUtils
import com.mapbox.maps.extension.style.utils.unwrapToTyped
import java.util.HashMap

/**
 *
 * Check the [online documentation](https://docs.mapbox.com/style-spec/reference/types#modelSourceModels).
 */
@UiThread
class ModelSourceModel(builder: Builder) {
  internal var source: Source? = null
  private val properties: HashMap<String, PropertyValue<*>> = builder.properties

  /**
   * The model's identifier.
   */
  val id: String = builder.id

  /**
   * An object defining custom properties of the model. Properties are accessible as feature properties in expressions.
   *
   * Example usage:
   * ```kotlin
   * modelSourceModel("car-1") {
   *   uri("https://example.com/car.glb")
   *   featureProperties(hashMapOf(
   *     "vehicleType" to "sedan",
   *     "fleetId" to 42
   *   ))
   * }
   * ```
   *
   * These properties can then be accessed in layer expressions:
   * ```kotlin
   * modelLayer("car-layer", "car-source") {
   *   modelColor(
   *     match {
   *       get { literal("vehicleType") }
   *       literal("sedan")
   *       rgba { literal(0.0); literal(0.0); literal(1.0); literal(1.0) }
   *       rgba { literal(1.0); literal(0.0); literal(0.0); literal(1.0) }
   *     }
   *   )
   * }
   * ```
   */
  val featureProperties: HashMap<String, Any>?
    /**
     * An object defining custom properties of the model. Properties are accessible as feature properties in expressions.
     *
     * @return featureProperties as HashMap<String, Any>
     */
    get() = getProperties()?.get("featureProperties")?.unwrapToTyped()

  /**
   * An object defining custom properties of the model. Properties are accessible as feature properties in expressions.
   *
   * @param value as HashMap<String, Any>
   */
  fun featureProperties(value: HashMap<String, Any>): ModelSourceModel = apply {
    setProperty(PropertyValue("featureProperties", value))
  }

  /**
   * An array of one or more model material names whose properties will be overridden from model layer paint properties.
   */
  val materialOverrideNames: List<String>?
    /**
     * An array of one or more model material names whose properties will be overridden from model layer paint properties.
     *
     * @return materialOverrideNames as List<String>
     */
    get() = getProperties()?.get("materialOverrideNames")?.unwrapToTyped()

  /**
   * An array of one or more model material names whose properties will be overridden from model layer paint properties.
   *
   * @param value as List<String>
   */
  fun materialOverrideNames(value: List<String>): ModelSourceModel = apply {
    setProperty(PropertyValue("materialOverrideNames", value))
  }

  /**
   * A collection of material overrides.
   */
  val materialOverrides: List<ModelMaterialOverride>?
    /**
     * A collection of material overrides.
     *
     * @return materialOverrides as List<ModelMaterialOverride>
     */
    get() = getProperties()?.get("materialOverrides")?.unwrapToModelMaterialOverrides()

  /**
   * A collection of material overrides.
   *
   * @param value as List<ModelMaterialOverride>
   */
  fun materialOverrides(value: List<ModelMaterialOverride>): ModelSourceModel = apply {
    val overridesMap = value.associateByTo(HashMap(), { it.name })
    setProperty(PropertyValue("materialOverrides", overridesMap))
  }

  /**
   * An array of one or more model node names whose transform will be overridden from model layer paint properties.
   */
  val nodeOverrideNames: List<String>?
    /**
     * An array of one or more model node names whose transform will be overridden from model layer paint properties.
     *
     * @return nodeOverrideNames as List<String>
     */
    get() = getProperties()?.get("nodeOverrideNames")?.unwrapToTyped()

  /**
   * An array of one or more model node names whose transform will be overridden from model layer paint properties.
   *
   * @param value as List<String>
   */
  fun nodeOverrideNames(value: List<String>): ModelSourceModel = apply {
    setProperty(PropertyValue("nodeOverrideNames", value))
  }

  /**
   * A collection of node overrides.
   */
  val nodeOverrides: List<ModelNodeOverride>?
    /**
     * A collection of node overrides.
     *
     * @return nodeOverrides as List<ModelNodeOverride>
     */
    get() = getProperties()?.get("nodeOverrides")?.unwrapToModelNodeOverrides()

  /**
   * A collection of node overrides.
   *
   * @param value as List<ModelNodeOverride>
   */
  fun nodeOverrides(value: List<ModelNodeOverride>): ModelSourceModel = apply {
    val overridesMap = value.associateByTo(HashMap(), { it.name })
    setProperty(PropertyValue("nodeOverrides", overridesMap))
  }

  /**
   * Orientation of the model in euler angles [x, y, z]. Default value: [0,0,0]. The unit of orientation is in degrees.
   */
  val orientation: List<Double>?
    /**
     * Orientation of the model in euler angles [x, y, z]. Default value: [0,0,0]. The unit of orientation is in degrees.
     *
     * @return orientation as List<Double>
     */
    get() = getProperties()?.get("orientation")?.unwrapToTyped()

  /**
   * Orientation of the model in euler angles [x, y, z]. Default value: [0,0,0]. The unit of orientation is in degrees.
   *
   * @param value as List<Double>
   */
  fun orientation(value: List<Double>): ModelSourceModel = apply {
    setProperty(PropertyValue("orientation", value))
  }

  /**
   * Position of the model in longitude and latitude [lng, lat]. Default value: [0,0]. Minimum value: [-180,-90]. Maximum value: [180,90].
   */
  val position: List<Double>?
    /**
     * Position of the model in longitude and latitude [lng, lat]. Default value: [0,0]. Minimum value: [-180,-90]. Maximum value: [180,90].
     *
     * @return position as List<Double>
     */
    get() = getProperties()?.get("position")?.unwrapToTyped()

  /**
   * Position of the model in longitude and latitude [lng, lat]. Default value: [0,0]. Minimum value: [-180,-90]. Maximum value: [180,90].
   *
   * @param value as List<Double>
   */
  fun position(value: List<Double>): ModelSourceModel = apply {
    setProperty(PropertyValue("position", value))
  }

  /**
   * A URL to a model resource. Supported protocols are `http:`, `https:`, and `mapbox://<Model ID>`.
   */
  val uri: String?
    /**
     * A URL to a model resource. Supported protocols are `http:`, `https:`, and `mapbox://<Model ID>`.
     *
     * @return uri as String
     */
    get() = getProperties()?.get("uri")?.unwrapToTyped()

  /**
   * A URL to a model resource. Supported protocols are `http:`, `https:`, and `mapbox://<Model ID>`.
   *
   * @param value as String
   */
  fun uri(value: String): ModelSourceModel = apply {
    setProperty(PropertyValue("uri", value))
  }

  @Suppress("UNCHECKED_CAST")
  internal fun getProperties(): HashMap<String, Value>? {
    val stylePropertyValue = source?.delegate?.getStyleSourceProperty(sourceId = source!!.sourceId, "models")
    return (stylePropertyValue?.value?.contents as? HashMap<String, Value>)?.get(id)?.contents as? HashMap<String, Value>
  }

  private fun setProperty(property: PropertyValue<*>) {
    properties[property.propertyName] = property
    updateProperties()
  }

  internal fun bindTo(source: Source) {
    this.source = source
    updateProperties()
  }

  private fun updateProperties() {
    source?.setProperty(PropertyValue("models", TypeUtils.wrapToValue(hashMapOf(id to this.properties.mapValues { it.value.value }))))
  }

  /**
   * Convert [ModelSourceModel] to a [Value] class to be passed to sources.
   */
  @JvmSynthetic
  internal fun toValue(): Value {
    return Value(HashMap(properties.mapValues { it.value.value }.toMutableMap()))
  }

  /**
   * Builder for ModelSourceModel.
   *
   * @param id the ID of the mode
   */
  class Builder(val id: String) {
    internal val properties = HashMap<String, PropertyValue<*>>()

    /**
     * An object defining custom properties of the model. Properties are accessible as feature properties in expressions.
     *
     * Example usage:
     * ```kotlin
     * modelSourceModel("car-1") {
     *   uri("https://example.com/car.glb")
     *   featureProperties(hashMapOf(
     *     "vehicleType" to "sedan",
     *     "fleetId" to 42
     *   ))
     * }
     * ```
     *
     * These properties can then be accessed in layer expressions:
     * ```kotlin
     * modelLayer("car-layer", "car-source") {
     *   modelColor(
     *     match {
     *       get { literal("vehicleType") }
     *       literal("sedan")
     *       rgba { literal(0.0); literal(0.0); literal(1.0); literal(1.0) }
     *       rgba { literal(1.0); literal(0.0); literal(0.0); literal(1.0) }
     *     }
     *   )
     * }
     * ```
     */
    fun featureProperties(value: HashMap<String, Any>): Builder = apply {
      val propertyValue = PropertyValue("featureProperties", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * An array of one or more model material names whose properties will be overridden from model layer paint properties.
     */
    fun materialOverrideNames(value: List<String>): Builder = apply {
      val propertyValue = PropertyValue("materialOverrideNames", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * A collection of material overrides.
     */
    fun materialOverrides(value: List<ModelMaterialOverride>): Builder = apply {
      val overridesMap = value.associateByTo(HashMap(), { it.name })
      val propertyValue = PropertyValue("materialOverrides", TypeUtils.wrapToValue(overridesMap))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * An array of one or more model node names whose transform will be overridden from model layer paint properties.
     */
    fun nodeOverrideNames(value: List<String>): Builder = apply {
      val propertyValue = PropertyValue("nodeOverrideNames", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * A collection of node overrides.
     */
    fun nodeOverrides(value: List<ModelNodeOverride>): Builder = apply {
      val overridesMap = value.associateByTo(HashMap(), { it.name })
      val propertyValue = PropertyValue("nodeOverrides", TypeUtils.wrapToValue(overridesMap))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Orientation of the model in euler angles [x, y, z]. Default value: [0,0,0]. The unit of orientation is in degrees.
     */
    fun orientation(value: List<Double>): Builder = apply {
      val propertyValue = PropertyValue("orientation", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Position of the model in longitude and latitude [lng, lat]. Default value: [0,0]. Minimum value: [-180,-90]. Maximum value: [180,90].
     */
    fun position(value: List<Double>): Builder = apply {
      val propertyValue = PropertyValue("position", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * A URL to a model resource. Supported protocols are `http:`, `https:`, and `mapbox://<Model ID>`.
     */
    fun uri(value: String): Builder = apply {
      val propertyValue = PropertyValue("uri", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Build the ModelSourceModel.
     *
     * @return the ModelSourceModel
     */
    fun build(): ModelSourceModel = ModelSourceModel(this)
  }
}

/**
 * Parse [Value] to a [ModelSourceModel].
 */
@Suppress("UNCHECKED_CAST")
@JvmSynthetic
internal fun Value.unwrapToModelSourceModels(): List<ModelSourceModel>? {
  return (contents as? HashMap<String, Value>)?.map { modelMap ->
    val contents = modelMap.value.contents as? HashMap<String, Value>
    val builder = ModelSourceModel.Builder(modelMap.key)

    if (contents == null) {
      return@map builder.build()
    }
    (contents["featureProperties"]?.unwrapToTyped<HashMap<String, Any>>())?.let { builder.featureProperties(it) }
    (contents["materialOverrideNames"]?.unwrapToTyped<List<String>>())?.let { builder.materialOverrideNames(it) }
    (contents["materialOverrides"]?.unwrapToModelMaterialOverrides())?.let { builder.materialOverrides(it) }
    (contents["nodeOverrideNames"]?.unwrapToTyped<List<String>>())?.let { builder.nodeOverrideNames(it) }
    (contents["nodeOverrides"]?.unwrapToModelNodeOverrides())?.let { builder.nodeOverrides(it) }
    (contents["orientation"]?.unwrapToTyped<List<Double>>())?.let { builder.orientation(it) }
    (contents["position"]?.unwrapToTyped<List<Double>>())?.let { builder.position(it) }
    (contents["uri"]?.unwrapToTyped<String>())?.let { builder.uri(it) }
    return@map builder.build()
  }
}

/**
 *
 * Check the [online documentation](https://docs.mapbox.com/style-spec/reference/types#modelMaterialOverrides).
 */
@UiThread
class ModelMaterialOverride private constructor(builder: Builder) : HashMap<String, Value>(builder.parameters) {
  internal val name: String = builder.name

  /**
   * Builder for ModelMaterialOverride.
   *
   * @param name the name of the override
   */
  class Builder(val name: String) {
    internal val parameters: HashMap<String, Value> = HashMap()

    /**
     * Override the tint color of the material.
     */
    fun modelColor(value: String): Builder = apply {
      parameters["model-color"] = TypeUtils.wrapToValue(value)
    }

    /**
     * Override the tint color of the material.
     *
     * @param modelColor value of modelColor as color int
     */
    fun modelColor(@ColorInt modelColor: Int): Builder = apply {
      parameters["model-color"] = TypeUtils.wrapToValue(colorToRgbaString(modelColor))
    }

    /**
     * Override the intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors.
     */
    fun modelColorMixIntensity(value: Double): Builder = apply {
      parameters["model-color-mix-intensity"] = TypeUtils.wrapToValue(value)
    }

    /**
     * Override strength of the emission of material. The unit of modelEmissiveStrength is in intensity.
     */
    fun modelEmissiveStrength(value: Double): Builder = apply {
      parameters["model-emissive-strength"] = TypeUtils.wrapToValue(value)
    }

    /**
     * Override the opacity of the material.
     */
    fun modelOpacity(value: Double): Builder = apply {
      parameters["model-opacity"] = TypeUtils.wrapToValue(value)
    }

  /**
   * Build the ModelMaterialOverride.
   *
   * @return the constructed ModelMaterialOverride
   */
  fun build(): ModelMaterialOverride = ModelMaterialOverride(this)
  }
}

/**
 * Parse [Value] to a [ModelMaterialOverride].
 */
@Suppress("UNCHECKED_CAST")
@JvmSynthetic
internal fun Value.unwrapToModelMaterialOverrides(): List<ModelMaterialOverride> {
  return (contents as HashMap<String, Value>).map { entry ->
    val builder = ModelMaterialOverride.Builder(entry.key)
    val overrideMap = entry.value.contents as HashMap<String, Value>
    (overrideMap["model-color"]?.unwrapToTyped<String>())?.let { builder.modelColor(it) }
    (overrideMap["model-color-mix-intensity"]?.unwrapToTyped<Double>())?.let { builder.modelColorMixIntensity(it) }
    (overrideMap["model-emissive-strength"]?.unwrapToTyped<Double>())?.let { builder.modelEmissiveStrength(it) }
    (overrideMap["model-opacity"]?.unwrapToTyped<Double>())?.let { builder.modelOpacity(it) }

    return@map builder.build()
  }
}

/**
 *
 * Check the [online documentation](https://docs.mapbox.com/style-spec/reference/types#modelNodeOverrides).
 */
@UiThread
class ModelNodeOverride private constructor(builder: Builder) : HashMap<String, Value>(builder.parameters) {
  internal val name: String = builder.name

  /**
   * Builder for ModelNodeOverride.
   *
   * @param name the name of the override
   */
  class Builder(val name: String) {
    internal val parameters: HashMap<String, Value> = HashMap()

    /**
     * Override the orientation of the model node in euler angles [x, y, z]. Default value: [0,0,0]. The unit of orientation is in degrees.
     */
    fun orientation(value: List<Double>): Builder = apply {
      parameters["orientation"] = TypeUtils.wrapToValue(value)
    }

  /**
   * Build the ModelNodeOverride.
   *
   * @return the constructed ModelNodeOverride
   */
  fun build(): ModelNodeOverride = ModelNodeOverride(this)
  }
}

/**
 * Parse [Value] to a [ModelNodeOverride].
 */
@Suppress("UNCHECKED_CAST")
@JvmSynthetic
internal fun Value.unwrapToModelNodeOverrides(): List<ModelNodeOverride> {
  return (contents as HashMap<String, Value>).map { entry ->
    val builder = ModelNodeOverride.Builder(entry.key)
    val overrideMap = entry.value.contents as HashMap<String, Value>
    (overrideMap["orientation"]?.unwrapToTyped<List<Double>>())?.let { builder.orientation(it) }

    return@map builder.build()
  }
}

/**
 * DSL function to create a [ModelSourceModel].
 *
 * @param id The unique identifier for this model
 * @param block The DSL configuration block
 * @return Configured [ModelSourceModel] instance
 */
fun modelSourceModel(id: String, block: ModelSourceModel.Builder.() -> Unit): ModelSourceModel =
  ModelSourceModel.Builder(id).apply(block).build()

/**
 * DSL function to create a [ModelMaterialOverride].
 *
 * @param name The name of the material to override
 * @param block The DSL configuration block
 * @return Configured [ModelMaterialOverride] instance
 */
fun modelMaterialOverride(name: String, block: ModelMaterialOverride.Builder.() -> Unit): ModelMaterialOverride =
  ModelMaterialOverride.Builder(name).apply(block).build()

/**
 * DSL function to create a [ModelNodeOverride].
 *
 * @param name The name of the node to override
 * @param block The DSL configuration block
 * @return Configured [ModelNodeOverride] instance
 */
fun modelNodeOverride(name: String, block: ModelNodeOverride.Builder.() -> Unit): ModelNodeOverride =
  ModelNodeOverride.Builder(name).apply(block).build()

// End of generated file.
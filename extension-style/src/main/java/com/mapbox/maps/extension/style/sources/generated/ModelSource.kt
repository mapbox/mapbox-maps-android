// This file is generated.

package com.mapbox.maps.extension.style.sources.generated

import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.StyleManager
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.sources.Source
import com.mapbox.maps.extension.style.types.SourceDsl
import com.mapbox.maps.extension.style.utils.TypeUtils
import com.mapbox.maps.extension.style.utils.silentUnwrap
import java.util.*

/**
 * A collection of 3D models
 *
 * @see [The online documentation](https://docs.mapbox.com/style-spec/reference/sources/#model)
 *
 */
class ModelSource(builder: Builder) : Source(builder.sourceId) {
  private val batched: Boolean
  private var initialModels: List<ModelSourceModel>? = null

  init {
    batched = builder.batched
    initialModels = builder.models
    sourceProperties.putAll(builder.properties)
    volatileSourceProperties.putAll(builder.volatileProperties)
  }

  /**
   * Get the type of the current source as a String.
   */
  override fun getType(): String {
    return if (batched) "batched-model" else "model"
  }

  /**
   * A URL to a TileJSON resource. Supported protocols are `http:`, `https:`, and `mapbox://<Tileset ID>`. Required if
   * `tiles` is not provided.
   */
  fun url(value: String): ModelSource = apply {
    setProperty(PropertyValue("url", TypeUtils.wrapToValue(value)))
  }

  /**
   * A URL to a TileJSON resource. Supported protocols are `http:`, `https:`, and `mapbox://<Tileset ID>`. Required if
   * `tiles` is not provided.
   */
  val url: String?
    /**
     * Get the Url property
     *
     * @return String
     */
    get() = getPropertyValue("url")

  /**
   * Maximum zoom level at which to create batched model tiles. Data from tiles at the maxzoom
   * are used when displaying the map at higher zoom levels.
   * Default value: 18.
   */
  fun maxzoom(value: Long = 18L): ModelSource = apply {
    setProperty(PropertyValue("maxzoom", TypeUtils.wrapToValue(value)))
  }

  /**
   * Maximum zoom level at which to create batched model tiles. Data from tiles at the maxzoom
   * are used when displaying the map at higher zoom levels.
   * Default value: 18.
   */
  val maxzoom: Long?
    /**
     * Get the Maxzoom property
     *
     * Use static method [ModelSource.defaultMaxzoom] to get the default property.
     *
     * @return Long
     */
    get() = getPropertyValue("maxzoom")

  /**
   * Minimum zoom level for which batched-model tiles are available
   * Default value: 0.
   */
  fun minzoom(value: Long = 0L): ModelSource = apply {
    setProperty(PropertyValue("minzoom", TypeUtils.wrapToValue(value)))
  }

  /**
   * Minimum zoom level for which batched-model tiles are available
   * Default value: 0.
   */
  val minzoom: Long?
    /**
     * Get the Minzoom property
     *
     * Use static method [ModelSource.defaultMinzoom] to get the default property.
     *
     * @return Long
     */
    get() = getPropertyValue("minzoom")

  /**
   * An array of one or more tile source URLs, as in the TileJSON spec. Requires `batched-model`
   * source type.
   */
  fun tiles(value: List<String>): ModelSource = apply {
    setProperty(PropertyValue("tiles", TypeUtils.wrapToValue(value)))
  }

  /**
   * An array of one or more tile source URLs, as in the TileJSON spec. Requires `batched-model`
   * source type.
   */
  val tiles: List<String>?
    /**
     * Get the Tiles property
     *
     * @return List<String>
     */
    get() = getPropertyValue("tiles")

  /**
   * Defines properties of 3D models in collection. Requires `model` source type.
   */
  fun models(value: List<ModelSourceModel>): ModelSource = apply {
    value.forEach { it.bindTo(this) }
  }

  /**
   * Defines properties of 3D models in collection. Requires `model` source type.
   */
  val models: List<ModelSourceModel>?
    /**
     * Get the Models property
     *
     * @return List<ModelSourceModel>
     */
    get() = getPropertyValue<List<ModelSourceModel>?>("models")
      .also { it?.forEach { it.source = this } }

  /**
   * Removes model from source.
   *
   * @param model Model to be removed
   */
  fun removeModel(model: ModelSourceModel) {
    removeModel(model.id)
  }

  /**
   * Removes model from the source.
   *
   * @param id The id of model to be removed
   */
  fun removeModel(id: String) {
    setProperty(PropertyValue("models", TypeUtils.wrapToValue(hashMapOf(id to Value.nullValue()))))
  }

  /**
   * Removes all models from the source.
   */
  fun removeAllModels() {
    setProperty(PropertyValue("models", Value.nullValue()))
  }

  override fun bindTo(delegate: MapboxStyleManager) {
    super.bindTo(delegate)

    initialModels?.forEach { it.bindTo(this) }
    initialModels = null
  }

  /**
   * Builder for ModelSource.
   *
   * @param sourceId the ID of the source
   */
  @SourceDsl
  class Builder(val sourceId: String) {
    internal val properties = HashMap<String, PropertyValue<*>>()
    // Properties that only settable after the source is added to the style.
    internal val volatileProperties = HashMap<String, PropertyValue<*>>()
    internal var batched: Boolean = false
    internal var models: List<ModelSourceModel>? = null

    /**
     * Configures source with batched-model type.
     * @param batched Whether the source should have batched-model type.
     */
    fun batched(batched: Boolean): Builder = apply {
      this.batched = batched
    }

    /**
     * A URL to a TileJSON resource. Supported protocols are `http:`, `https:`, and `mapbox://<Tileset ID>`. Required if
     * `tiles` is not provided.
     */
    fun url(value: String): Builder = apply {
      val propertyValue = PropertyValue("url", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Maximum zoom level at which to create batched model tiles. Data from tiles at the maxzoom
     * are used when displaying the map at higher zoom levels.
     * Default value: 18.
     */
    fun maxzoom(value: Long = 18L): Builder = apply {
      val propertyValue = PropertyValue("maxzoom", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Minimum zoom level for which batched-model tiles are available
     * Default value: 0.
     */
    fun minzoom(value: Long = 0L): Builder = apply {
      val propertyValue = PropertyValue("minzoom", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * An array of one or more tile source URLs, as in the TileJSON spec. Requires `batched-model`
     * source type.
     */
    fun tiles(value: List<String>): Builder = apply {
      val propertyValue = PropertyValue("tiles", TypeUtils.wrapToValue(value))
      properties[propertyValue.propertyName] = propertyValue
    }

    /**
     * Defines properties of 3D models in collection. Requires `model` source type.
     */
    fun models(value: List<ModelSourceModel>): Builder = apply {
      models = value
    }
    /**
     * Build the ModelSource.
     *
     * @return the ModelSource
     */
    fun build(): ModelSource = ModelSource(this)
  }

  /**
   * Static variables and methods.
   */
  companion object {

    /**
     * Maximum zoom level at which to create batched model tiles. Data from tiles at the maxzoom
     * are used when displaying the map at higher zoom levels.
     * Default value: 18.
     */
    val defaultMaxzoom: Long?
      /**
       * Get the Maxzoom property
       *
       * @return Long
       */
      get() = StyleManager.getStyleSourcePropertyDefaultValue("model", "maxzoom").silentUnwrap()

    /**
     * Minimum zoom level for which batched-model tiles are available
     * Default value: 0.
     */
    val defaultMinzoom: Long?
      /**
       * Get the Minzoom property
       *
       * @return Long
       */
      get() = StyleManager.getStyleSourcePropertyDefaultValue("model", "minzoom").silentUnwrap()
  }
}

/**
 * DSL function for [ModelSource].
 */
fun modelSource(id: String, block: ModelSource.Builder.() -> Unit): ModelSource =
  ModelSource.Builder(id).apply(block).build()

// End of generated file.
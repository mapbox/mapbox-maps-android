package com.mapbox.maps.extension.style.sources

import com.mapbox.bindgen.Value
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.sources.ModelSource.Builder.Companion.DEFAULT_MODEL_NAME
import com.mapbox.maps.extension.style.sources.ModelSource.Builder.Companion.MODELS
import com.mapbox.maps.extension.style.sources.ModelSource.Builder.Companion.ORIENTATION
import com.mapbox.maps.extension.style.sources.ModelSource.Builder.Companion.POSITION
import com.mapbox.maps.extension.style.sources.ModelSource.Builder.Companion.TYPE
import com.mapbox.maps.extension.style.sources.ModelSource.Builder.Companion.URL
import com.mapbox.maps.extension.style.utils.TypeUtils

/**
 * A Model data source.
 *
 */
class ModelSource internal constructor(
  builder: Builder
) : Source(builder.sourceId) {

  init {
    val models: HashMap<String, Value> = HashMap()
    builder.properties.forEach {
      models[it.key] = Value(it.value)
    }
    sourceProperties[MODELS] = PropertyValue(MODELS, models)
  }

  /**
   * Get the type of the current source as a String.
   */
  override fun getType(): String {
    return TYPE
  }

  /**
   * Get the model map with the modelName
   * @param modelName the model name
   */
  private fun getModelMap(modelName: String = DEFAULT_MODEL_NAME): HashMap<String, Value>? {
    val model: HashMap<String, HashMap<String, Value>>? = getPropertyValue(MODELS)
    model?.let {
      if (model.isNotEmpty()) {
        return model[modelName]
      }
    }
    return null
  }

  /**
   * Get the url for a model
   * @param modelName the model name
   */
  fun getURL(modelName: String = DEFAULT_MODEL_NAME): String? {
    getModelMap(modelName)?.let {
      return it[URL].toString()
    }
    return null
  }

  /**
   * Updates the URL of the model style source.
   *
   * @param url An URL for the model file.
   * @param modelName the name of the model that will be updated
   */
  fun setURL(url: String, modelName: String = DEFAULT_MODEL_NAME) {
    val property = hashMapOf(Pair(URL, TypeUtils.wrapToValue(url)))
    val updateModel = hashMapOf(Pair(modelName, Value(property)))
    setProperty(PropertyValue(MODELS, Value(updateModel)))
  }

  /**
   * Set the position of the model style source.
   *
   * @param modelName the name of the model that will be updated
   * @param value Model coordinates.
   */
  fun setPosition(value: List<Double>, modelName: String = DEFAULT_MODEL_NAME) {
    val property = hashMapOf(Pair(POSITION, Value(value.map { Value(it) })))
    val updateModel = hashMapOf(Pair(modelName, Value(property)))
    setProperty(PropertyValue(MODELS, Value(updateModel)))
  }

  /**
   * Get the position for the model
   * @param modelName the model name
   */
  fun getPosition(modelName: String = DEFAULT_MODEL_NAME): List<Double>? {
    getModelMap(modelName)?.let {
      return it[POSITION] as List<Double>
    }
    return null
  }

  /**
   * Set the orientation of the model style source.
   *
   * @param value Model coordinates.
   * @param modelName the name of the model that will be updated
   */
  fun setOrientation(value: List<Double>, modelName: String = DEFAULT_MODEL_NAME) {
    val property = hashMapOf(Pair(ORIENTATION, Value(value.map { Value(it) })))
    val updateModel = hashMapOf(Pair(modelName, Value(property)))
    setProperty(PropertyValue(MODELS, Value(updateModel)))
  }

  /**
   * Get the orientation for the model
   * @param modelName the model name
   */
  fun getOrientation(modelName: String = DEFAULT_MODEL_NAME): List<Double>? {
    getModelMap(modelName)?.let {
      return it[ORIENTATION] as List<Double>
    }
    return null
  }

  /**
   * Remove the model
   *
   * @param modelName the name of the model that will be removed
   */
  fun removeModel(modelName: String) {
    val updateModel = hashMapOf(Pair(modelName, Value("")))
    setProperty(PropertyValue(MODELS, Value(updateModel)))
  }

  /**
   * Add a new model
   *
   * @param modelName the name of the model that will be added
   * @param url the url of the model that will be added
   * @param position the position of the model that will be added
   * @param orientation the orientation of the model that will be added
   */
  fun addModel(modelName: String, url: String, position: List<Double>, orientation: List<Double>) {
    val properties = hashMapOf(
      Pair(URL, Value(url)),
      Pair(POSITION, Value(position.map { Value(it) })),
      Pair(Builder.ORIENTATION, Value(orientation.map { Value(it) }))
    )
    val model = hashMapOf(Pair(modelName, Value(properties)))

    setProperty(PropertyValue(MODELS, Value(model)))
  }

  /**
   * The Builder for ModelSource.
   *
   * @param sourceId the ID of the source
   */
  class Builder(val sourceId: String) {
    internal var properties: HashMap<String, HashMap<String, Value>> = HashMap()
    private fun getHashMap(): HashMap<String, Value> {
      return hashMapOf(
        Pair(URL, Value("")),
        Pair(POSITION, Value(listOf(0.0, 0.0).map { Value(it) })),
        Pair(ORIENTATION, Value(listOf(0.0, 0.0, 0.0).map { Value(it) }))
      )
    }

    /**
     * URL that points to the default model.
     */
    fun url(value: String) = apply {
      url(DEFAULT_MODEL_NAME, value)
    }

    /**
     * URL that points to an model with model name.
     */
    fun url(modelName: String, value: String) = apply {
      if (properties.containsKey(modelName)) {
        properties[modelName]!![URL] = TypeUtils.wrapToValue(value)
      } else {
        properties[modelName] = getHashMap().also {
          it[URL] = TypeUtils.wrapToValue(value)
        }
      }
    }

    /**
     * Position of default model specified in longitude, latitude pairs.
     */
    fun position(value: List<Double>) = apply {
      position(DEFAULT_MODEL_NAME, value)
    }

    /**
     * Position of the model with model name specified in longitude, latitude pairs.
     */
    fun position(modelName: String, value: List<Double>) = apply {
      if (properties.containsKey(modelName)) {
        properties[modelName]!![POSITION] = Value(value.map { Value(it) })
      } else {
        properties[modelName] = getHashMap().also {
          it[POSITION] = Value(value.map { Value(it) })
        }
      }
    }

    /**
     * Orientation of the default model specified in x, y, z in degree.
     * In practice +x axis is facing east, +z is facing south and +y is up
     */
    fun orientation(value: List<Double>) = apply {
      orientation(DEFAULT_MODEL_NAME, value)
    }

    /**
     * Orientation of the model with model name specified in x, y, z in degree.
     * In practice +x axis is facing east, +z is facing south and +y is up
     */
    fun orientation(modelName: String, value: List<Double>) = apply {
      if (properties.containsKey(modelName)) {
        properties[modelName]!![ORIENTATION] = Value(value.map { Value(it) })
      } else {
        properties[modelName] = getHashMap().also {
          it[ORIENTATION] = Value(value.map { Value(it) })
        }
      }
    }

    /**
     * Build the ModelSource.
     *
     * @return the ModelSource
     */
    fun build() = ModelSource(this)

    /**
     * Static variables and methods.
     */
    companion object {
      /** The name of default model*/
      const val DEFAULT_MODEL_NAME = "defaultModel"

      /** The key for models*/
      const val MODELS = "models"

      /** The key for source type*/

      const val TYPE = "model"

      /** The property key of url*/

      const val URL = "uri"

      /** The property key of position*/

      const val POSITION = "position"

      /** The property key of orientation*/

      const val ORIENTATION = "orientation"
    }
  }
}

/**
 * DSL function for [ModelSource].
 */
fun modelSource(id: String, block: ModelSource.Builder.() -> Unit): ModelSource =
  ModelSource.Builder(id).apply(block).build()
package com.mapbox.maps.plugin.location

import android.util.Log
import com.mapbox.bindgen.Value
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.plugin.delegates.MapStyleStateDelegate

internal class ModelLocationSourceWrapper(
  private val sourceId: String,
  private var url: String,
  position: List<Double>
) {

  private var sourceProperties = HashMap<String, Value>()
  private var styleDelegate: StyleManagerInterface? = null
  private var styleStateDelegate: MapStyleStateDelegate? = null

  init {
    val modelProperties = HashMap<String, Value>()
    modelProperties[URL] = Value(url)
    modelProperties[POSITION] = Value(position.map { Value(it) })
    modelProperties[ORIENTATION] = Value(listOf(0.0, 0.0, 0.0).map { Value(it) })

    val models = HashMap<String, Value>()
    models[DEFAULT_MODEL_NAME] = Value(modelProperties)

    sourceProperties["type"] = Value(TYPE)
    sourceProperties[MODELS] = Value(models)
  }

  fun bindTo(delegate: StyleManagerInterface, styleStateDelegate: MapStyleStateDelegate) {
    this.styleDelegate = delegate
    this.styleStateDelegate = styleStateDelegate
    val expected = delegate.addStyleSource(sourceId, toValue())
    expected.error?.let {
      Log.e(TAG, sourceProperties.toString())
      throw RuntimeException("Add source failed: $it")
    }
  }

  fun setPosition(value: List<Double>) {
    val property = hashMapOf(Pair(POSITION, Value(value.map { Value(it) })), Pair(URL, Value(url)))
    val updateModel = hashMapOf(Pair(DEFAULT_MODEL_NAME, Value(property)))
    updateProperty(MODELS, Value(updateModel))
  }

  fun toValue() = Value(sourceProperties)

  private fun updateProperty(propertyName: String, value: Value) {
    styleStateDelegate?.let {
      if (!it.isFullyLoaded()) {
        return
      }
    }
    sourceProperties[propertyName] = value
    styleDelegate?.let { styleDelegate ->
      val expected = styleDelegate.setStyleSourceProperty(
        sourceId,
        propertyName,
        value
      )
      expected.error?.let {
        throw RuntimeException("Set source property \"${propertyName}\" failed:\nError: $it\nValue set: $value")
      }
    }
  }

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

    private const val TAG = "Mbgl-ModelSourceWrapper"
  }
}
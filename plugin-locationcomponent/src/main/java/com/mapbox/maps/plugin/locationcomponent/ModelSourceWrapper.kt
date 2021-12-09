package com.mapbox.maps.plugin.locationcomponent

import android.util.Log
import com.mapbox.bindgen.Value
import com.mapbox.common.Logger
import com.mapbox.maps.MapboxLocationComponentException
import com.mapbox.maps.StyleManagerInterface

internal class ModelSourceWrapper(
  val sourceId: String,
  private var url: String,
  position: List<Double>
) {

  private var sourceProperties = HashMap<String, Value>()
  private var styleDelegate: StyleManagerInterface? = null

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

  fun bindTo(delegate: StyleManagerInterface) {
    this.styleDelegate = delegate
    val expected = delegate.addStyleSource(sourceId, toValue())
    expected.error?.let {
      Log.e(TAG, sourceProperties.toString())
      throw MapboxLocationComponentException("Add source failed: $it")
    }
  }

  fun setPosition(value: List<Double>) {
    val property = hashMapOf(Pair(POSITION, Value(value.map { Value(it) })), Pair(URL, Value(url)))
    val updateModel = hashMapOf(Pair(DEFAULT_MODEL_NAME, Value(property)))
    updateProperty(MODELS, Value(updateModel))
  }

  fun toValue() = Value(sourceProperties)

  private fun updateProperty(propertyName: String, value: Value) {
    sourceProperties[propertyName] = value
    styleDelegate?.let { styleDelegate ->
      if (styleDelegate.styleSourceExists(sourceId)) {
        val expected = styleDelegate.setStyleSourceProperty(
          sourceId,
          propertyName,
          value
        )
        expected.error?.let {
          throw MapboxLocationComponentException("Set source property \"${propertyName}\" failed:\nError: $it\nValue set: $value")
        }
      } else {
        Logger.w(
          TAG,
          "Skip updating source property $propertyName, source $sourceId not ready yet."
        )
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
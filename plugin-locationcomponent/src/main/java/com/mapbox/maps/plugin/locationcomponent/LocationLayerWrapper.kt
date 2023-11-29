package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.bindgen.Value
import com.mapbox.maps.*

internal open class LocationLayerWrapper(val layerId: String) {

  protected var layerProperties = HashMap<String, Value>()
  private var style: MapboxStyleManager? = null

  fun updateStyle(style: MapboxStyleManager) {
    this.style = style
  }

  fun bindTo(style: MapboxStyleManager, position: LayerPosition? = null) {
    this.style = style
    val expected = style.addPersistentStyleLayer(toValue(), position)
    expected.error?.let {
      throw MapboxLocationComponentException("Add layer failed: $it")
    }
  }

  protected fun updateProperty(propertyName: String, value: Value) {
    layerProperties[propertyName] = value
    style?.let { style ->
      val expected = style.setStyleLayerProperty(layerId, propertyName, value)
      expected.error?.let {
        logE(TAG, "Set layer property \"${propertyName}\" failed:\nError: $it\nValue set: $value")
      }
    }
  }

  fun toValue() = Value(layerProperties)

  fun visibility(visibility: Boolean) = updateProperty("visibility", Value(if (visibility) "visible" else "none"))

  companion object {
    private const val TAG = "MapboxLocationLayerWrapper"
  }
}
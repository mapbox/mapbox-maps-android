package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.bindgen.Value
import com.mapbox.maps.*

internal open class LocationLayerWrapper(val layerId: String) {

  protected var layerProperties = HashMap<String, Value>()
  private var style: Style? = null

  fun updateStyle(style: Style) {
    this.style = style
  }

  fun bindTo(style: Style, position: LayerPosition? = null) {
    this.style = style
    val expected = style.addPersistentStyleLayer(toValue(), position)
    expected.error?.let {
      throw MapboxLocationComponentException("Add layer failed: $it")
    }
  }

  protected fun updateProperty(propertyName: String, value: Value) {
    layerProperties[propertyName] = value
    style?.let { styleDelegate ->
      if (styleDelegate.styleLayerExists(layerId)) {
        val expected = styleDelegate.setStyleLayerProperty(layerId, propertyName, value)
        expected.error?.let {
          throw MapboxLocationComponentException("Set layer property \"${propertyName}\" failed:\n$it\n$value")
        }
      } else {
        logW(TAG, "Skip updating layer property $propertyName, layer $layerId not ready yet.")
      }
    }
  }

  fun toValue() = Value(layerProperties)

  fun visibility(visibility: Boolean) = updateProperty("visibility", Value(if (visibility) "visible" else "none"))

  companion object {
    private const val TAG = "MapboxLocationLayerWrapper"
  }
}
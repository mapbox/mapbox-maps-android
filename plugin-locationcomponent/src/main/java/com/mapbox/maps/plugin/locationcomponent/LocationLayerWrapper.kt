package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.bindgen.Value
import com.mapbox.common.Logger
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.MapboxLocationComponentException
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.extension.style.StyleInterface

internal open class LocationLayerWrapper(val layerId: String) {

  protected var layerProperties = HashMap<String, Value>()
  private var mapStyleDelegate: StyleManagerInterface? = null

  fun updateStyle(style: StyleInterface) {
    this.mapStyleDelegate = style
  }

  fun bindTo(mapStyleDelegate: StyleManagerInterface, position: LayerPosition? = null) {
    this.mapStyleDelegate = mapStyleDelegate
    val expected = mapStyleDelegate.addPersistentStyleLayer(toValue(), position)
    expected.error?.let {
      throw MapboxLocationComponentException("Add layer failed: $it")
    }
  }

  protected fun updateProperty(propertyName: String, value: Value) {
    layerProperties[propertyName] = value
    mapStyleDelegate?.let { styleDelegate ->
      if (styleDelegate.styleLayerExists(layerId)) {
        val expected = styleDelegate.setStyleLayerProperty(layerId, propertyName, value)
        expected.error?.let {
          throw MapboxLocationComponentException("Set layer property \"${propertyName}\" failed:\n$it\n$value")
        }
      } else {
        Logger.w(TAG, "Skip updating layer property $propertyName, layer $layerId not ready yet.")
      }
    }
  }

  fun toValue() = Value(layerProperties)

  fun visibility(visibility: Boolean) = updateProperty("visibility", Value(if (visibility) "visible" else "none"))

  companion object {
    private const val TAG = "MapboxLocationLayerWrapper"
  }
}
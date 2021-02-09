package com.mapbox.maps.plugin.location

import com.mapbox.bindgen.Value
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.StyleManagerInterface

internal open class LocationLayerWrapper(val layerId: String) {

  protected var layerProperties = HashMap<String, Value>()
  private var mapStyleDelegate: StyleManagerInterface? = null

  fun bindTo(
    mapStyleDelegate: StyleManagerInterface,
    position: LayerPosition? = null
  ) {
    this.mapStyleDelegate = mapStyleDelegate
    val expected = mapStyleDelegate.addStyleLayer(toValue(), position)
    expected.error?.let {
      throw RuntimeException("Add layer failed: $it")
    }
  }

  protected fun updateProperty(propertyName: String, value: Value) {
    mapStyleDelegate?.let {
      if (!it.isStyleFullyLoaded) {
        return
      }
    }

    layerProperties[propertyName] = value
    mapStyleDelegate?.let { styleDelegate ->
      val expected = styleDelegate.setStyleLayerProperty(layerId, propertyName, value)
      expected.error?.let {
        throw RuntimeException("Set layer property \"${propertyName}\" failed:\n$it\n$value")
      }
    }
  }

  fun toValue() = Value(layerProperties)

  fun visibility(visibility: Boolean) =
    updateProperty("visibility", Value(if (visibility) "visible" else "none"))
}
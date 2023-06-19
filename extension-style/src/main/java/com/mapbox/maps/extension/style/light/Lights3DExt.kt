package com.mapbox.maps.extension.style.light

import com.mapbox.bindgen.Value
import com.mapbox.common.toValue
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxStyleException
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.light.generated.AmbientLight
import com.mapbox.maps.extension.style.light.generated.DirectionalLight
import com.mapbox.maps.extension.style.utils.silentUnwrap
import com.mapbox.maps.logE
import kotlin.collections.HashMap

/**
 * Extension function to add 3D Lights to the Style.
 *
 * @param [ambientLight] The ambient light to be added
 * @param [directionalLight] The directional light to be added
 *
 */
@MapboxExperimental
fun Style.setup3DLights(ambientLight: AmbientLight, directionalLight: DirectionalLight) {
  val ambientLightParam = HashMap<String, Value>().apply {
    this[LIGHT_PROPERTIES] = convertPropertyMapToValue(ambientLight.lightProperties)
    this.putAll(ambientLight.internalLightProperties)
  }

  val directionalLightParam = HashMap<String, Value>().apply {
    this[LIGHT_PROPERTIES] = convertPropertyMapToValue(directionalLight.lightProperties)
    this.putAll(directionalLight.internalLightProperties)
  }

  ambientLight.delegate = this
  directionalLight.delegate = this

  val valueList = ArrayList<Value>()
  valueList.add(Value(ambientLightParam))
  valueList.add(Value(directionalLightParam))
  val expected = this.setStyleLights(Value(valueList))
  expected.error?.let {
    throw MapboxStyleException("Set lights3D failed: $it")
  }
}

/**
 * Extension function to add list of 3D lights.
 * Note: Only [AmbientLight] and [DirectionalLight] are supported.
 * Lights must contain one instance of [AmbientLight] and one instance [DirectionalLight].
 *
 * @param list of [Lights3D]
 */
@MapboxExperimental
fun Style.addLights3D(lights: List<Lights3D>) {
  val valueList = ArrayList<Value>()
  for (light in lights) {
    if (isLightSupported(light)) {
      val lightParams = HashMap<String, Value>().apply {
        this[LIGHT_PROPERTIES] = convertPropertyMapToValue(light.lightProperties)
        this.putAll(light.internalLightProperties)
      }.toValue()
      light.delegate = this
      valueList.add(lightParams)
    } else {
      throw MapboxStyleException("Light type $light not supported.")
    }
  }
  val expected = this.setStyleLights(Value(valueList))
  expected.error?.let {
    throw MapboxStyleException("Set lights3D failed: $it")
  }
}

private fun convertPropertyMapToValue(property: HashMap<String, PropertyValue<*>>): Value {
  val propertyMap = HashMap<String, Value>()
  property.forEach {
    propertyMap[it.key] = it.value.value
  }
  return propertyMap.toValue()
}

/**
 * Extension function to get 3D Light that has been applied to the style.
 *
 * @param lightId Id of 3D light.
 */
@MapboxExperimental
fun Style.getLights3D(lightId: String): Lights3D? {
  return when (val type = getStyleLightProperty(lightId, "type").silentUnwrap<String>()) {
    "ambient" -> AmbientLight(lightId)
    "directional" -> DirectionalLight(lightId)
    else -> {
      logE(TAG, "Lights3D type: $type unknown.")
      null
    }
  }?.also { result ->
    result.delegate = this
  }
}

@MapboxExperimental
private fun isLightSupported(light: Lights3D) = light is AmbientLight || light is DirectionalLight

@PublishedApi
internal const val TAG = "Mbgl-Lights3DUtils"
internal const val LIGHT_PROPERTIES = "properties"
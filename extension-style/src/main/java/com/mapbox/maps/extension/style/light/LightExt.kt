@file:JvmName("LightUtils")

package com.mapbox.maps.extension.style.light

import com.mapbox.bindgen.Value
import com.mapbox.common.toValue
import com.mapbox.maps.MapboxStyleException
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.light.generated.AmbientLight
import com.mapbox.maps.extension.style.light.generated.DirectionalLight
import com.mapbox.maps.extension.style.light.generated.FlatLight
import com.mapbox.maps.extension.style.utils.silentUnwrap
import com.mapbox.maps.logE
import kotlin.collections.HashMap

/**
 * Extension function to add dynamic light to the [Style].
 * dynamic light is built from [AmbientLight] and [DirectionalLight].
 *
 * @param [ambientLight] The ambient light to be added
 * @param [directionalLight] The directional light to be added
 */
fun Style.setLight(ambientLight: AmbientLight, directionalLight: DirectionalLight) {
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
    throw MapboxStyleException("Set dynamic light failed with error: $it")
  }
}

/**
 * Extension function to add the [FlatLight] to the [Style].
 *
 * @param [flatLight] The flat light to be added
 */
fun Style.setLight(flatLight: FlatLight) {
  val flatLightParam = HashMap<String, Value>().apply {
    this[LIGHT_PROPERTIES] = convertPropertyMapToValue(flatLight.lightProperties)
    this.putAll(flatLight.internalLightProperties)
  }
  flatLight.delegate = this
  val valueList = ArrayList<Value>()
  valueList.add(Value(flatLightParam))
  val expected = this.setStyleLights(Value(valueList))
  expected.error?.let {
    throw MapboxStyleException("Set flat light failed with error: $it")
  }
}

/**
 * Extension function to add list of lights.
 *
 * Note: not all variations of lights is supported.
 *
 * @param lights list of [Light]
 */
fun Style.setLights(lights: List<Light>) {
  val valueList = ArrayList<Value>()
  for (light in lights) {
    val lightParams = HashMap<String, Value>().apply {
      this[LIGHT_PROPERTIES] = convertPropertyMapToValue(light.lightProperties)
      this.putAll(light.internalLightProperties)
    }.toValue()
    light.delegate = this
    valueList.add(lightParams)
  }
  val expected = this.setStyleLights(Value(valueList))
  expected.error?.let {
    throw MapboxStyleException("setStyleLights failed with error: $it")
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
 * @param lightId Id of dynamic light.
 */
fun Style.getLight(lightId: String): Light? {
  return when (val type = getStyleLightProperty(lightId, "type").silentUnwrap<String>()) {
    "ambient" -> AmbientLight(lightId)
    "directional" -> DirectionalLight(lightId)
    "flat" -> FlatLight(lightId)
    else -> {
      logE(TAG, "Light type: $type unknown.")
      null
    }
  }?.also { result ->
    result.delegate = this
  }
}

/**
 * DSL function for creating [DynamicLight] instance.
 *
 * @param ambientLight the [AmbientLight] to set.
 * @param directionalLight the [DirectionalLight] to set.
 */
fun dynamicLight(
  ambientLight: AmbientLight,
  directionalLight: DirectionalLight
): DynamicLight = DynamicLight(
  ambientLight,
  directionalLight
)

@PublishedApi
internal const val TAG = "Mbgl-LightUtils"
internal const val LIGHT_PROPERTIES = "properties"
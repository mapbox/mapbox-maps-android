// This file is generated.
@file:JvmName("LightUtils")

package com.mapbox.maps.extension.style.light.generated

import com.mapbox.bindgen.Value
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.StyleInterface

/**
 * Extension function to get the light provided by the Style Extension.
 *
 * @return Light
 */
fun StyleInterface.getLight(): Light {
  return Light().also { it.bindTo(this) }
}

/**
 * Extension function to add a Light provided by the Style Extension to the Style.
 *
 * @param light The light to be added
 */
fun StyleInterface.setLight(light: StyleContract.StyleLightExtension) {
  light.bindTo(this)
}

/**
 * Removes light from style if it was set.
 */
fun StyleInterface.removeLight() {
  setStyleLight(Value.nullValue())
}

// End of generated file.
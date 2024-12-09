// This file is generated.
@file:JvmName("RainUtils")

package com.mapbox.maps.extension.style.precipitations.generated

import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.style.StyleContract

/**
 * Extension function to get the Rain provided by the Style Extension.
 *
 * @return Rain
 */
@MapboxExperimental
fun MapboxStyleManager.getRain(): Rain {
  return Rain().also { it.delegate = this }
}

/**
 * Extension function to set the Rain provided by the Style Extension to the Style.
 *
 * @param rain The rain to be set
 */
@MapboxExperimental
fun MapboxStyleManager.setRain(rain: StyleContract.StyleRainExtension) {
  rain.bindTo(this)
}

/**
 * Removes rain from style if it was set.
 */
@MapboxExperimental
fun MapboxStyleManager.removeRain() {
  setStyleRain(Value.nullValue())
}

// End of generated file.
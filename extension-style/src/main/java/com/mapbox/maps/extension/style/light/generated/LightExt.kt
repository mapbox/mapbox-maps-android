// This file is generated.
@file:JvmName("LightUtils")

package com.mapbox.maps.extension.style.light.generated

import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.StyleContract

/**
 * Extension function to get the light provided by the Style Extension.
 *
 * @return Light
 */
fun Style.getLight(): Light {
  return Light().also { it.delegate = this }
}

/**
 * Extension function to add a Light provided by the Style Extension to the Style.
 *
 * @param light The light to be added
 */
fun Style.setLight(light: StyleContract.StyleLightExtension) {
  light.bindTo(this)
}

// End of generated file.
// This file is generated.
@file:JvmName("LightUtils")

package com.mapbox.maps.extension.style.light.generated

import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.StyleInterface

/**
 * Extension function to get the light provided by the Style Extension.
 *
 * @return Light
 */
fun StyleInterface.getLight(): Light {
  return Light().also { it.delegate = this }
}

/**
 * Extension function to add a Light provided by the Style Extension to the Style.
 *
 * @param light The light to be added
 */
fun StyleInterface.setLight(light: StyleContract.StyleLightExtension) {
  light.bindTo(this)
}

// End of generated file.
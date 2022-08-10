// This file is generated.
@file:JvmName("AtmosphereUtils")

package com.mapbox.maps.extension.style.atmosphere.generated

import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.StyleInterface

/**
 * Extension function to get the atmosphere provided by the Style Extension.
 *
 * @return Atmosphere
 */
fun StyleInterface.getAtmosphere(): Atmosphere {
  return Atmosphere().also { it.delegate = this }
}

/**
 * Extension function to set the Atmosphere provided by the Style Extension to the Style.
 *
 * @param atmosphere The atmosphere to be set
 */
fun StyleInterface.setAtmosphere(atmosphere: StyleContract.StyleAtmosphereExtension) {
  atmosphere.bindTo(this)
}

// End of generated file.
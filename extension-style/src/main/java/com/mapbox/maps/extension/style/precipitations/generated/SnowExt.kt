// This file is generated.
@file:JvmName("SnowUtils")

package com.mapbox.maps.extension.style.precipitations.generated

import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.style.StyleContract

/**
 * Extension function to get the Snow provided by the Style Extension.
 *
 * @return Snow
 */
@MapboxExperimental
fun MapboxStyleManager.getSnow(): Snow {
  return Snow().also { it.delegate = this }
}

/**
 * Extension function to set the Snow provided by the Style Extension to the Style.
 *
 * @param snow The snow to be set
 */
@MapboxExperimental
fun MapboxStyleManager.setSnow(snow: StyleContract.StyleSnowExtension) {
  snow.bindTo(this)
}

/**
 * Removes snow from style if it was set.
 */
@MapboxExperimental
fun MapboxStyleManager.removeSnow() {
  setStyleSnow(Value.nullValue())
}

// End of generated file.
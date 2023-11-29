package com.mapbox.maps.extension.style.light

import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.light.generated.AmbientLight
import com.mapbox.maps.extension.style.light.generated.DirectionalLight

/**
 * Class representing dynamic light which is built with [AmbientLight] and [DirectionalLight].
 */
class DynamicLight internal constructor(
  private val ambientLight: AmbientLight,
  private val directionalLight: DirectionalLight,
) : StyleContract.StyleLightExtension {

  /**
   * Bind the light to the Style.
   *
   * @param delegate The style delegate
   */
  override fun bindTo(delegate: MapboxStyleManager) {
    delegate.setLight(ambientLight, directionalLight)
  }
}
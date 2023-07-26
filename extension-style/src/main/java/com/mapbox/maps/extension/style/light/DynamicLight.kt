package com.mapbox.maps.extension.style.light

import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.light.generated.AmbientLight
import com.mapbox.maps.extension.style.light.generated.DirectionalLight

/**
 * Class representing dynamic light which is built with [AmbientLight] and [DirectionalLight]
 */
@MapboxExperimental
class DynamicLight internal constructor(
  private val ambientLight: AmbientLight,
  private val directionalLight: DirectionalLight,
) : StyleContract.StyleLightExtension {

  /**
   * Bind the light to the Style.
   *
   * @param delegate The style delegate
   */
  override fun bindTo(delegate: Style) {
    delegate.setLight(ambientLight, directionalLight)
  }
}
package com.mapbox.maps.extension.compose.style.lights

import androidx.compose.runtime.Composable
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.compose.style.lights.generated.AmbientLightState
import com.mapbox.maps.extension.compose.style.lights.generated.DirectionalLightState
import com.mapbox.maps.extension.compose.style.lights.generated.FlatLightState
import com.mapbox.maps.extension.compose.style.lights.internal.MapboxLight

/**
 * Defines the lights of the style.
 */
public class LightsState private constructor(private val mapboxLight: MapboxLight) {
  /**
   * Set dynamic light to the style.
   *
   * Dynamic light includes one [DirectionalLightState] and one [AmbientLightState].
   *
   * Setting light will remove previous defined lights.
   *
   * @param [directionalLightState] The directional light to be added
   * @param [ambientLightState] The ambient light to be added
   */
  public constructor(
    directionalLightState: DirectionalLightState,
    ambientLightState: AmbientLightState
  ) : this(MapboxLight.DynamicLight(listOf(directionalLightState), ambientLightState))

  /**
   * Set the legacy [FlatLightState] to the style.
   *
   * Setting light will remove previous defined lights.
   *
   * @param flatLightState The legacy flat light to be set.
   */
  public constructor(
    flatLightState: FlatLightState
  ) : this(MapboxLight.FlatLight(flatLightState))

  @Composable
  internal fun BindToMap(mapboxMap: MapboxMap) {
    mapboxLight.BindToMap(mapboxMap)
  }

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Setting this LightsState does nothing to the map.
     */
    @JvmField
    internal val INITIAL: LightsState = LightsState(MapboxLight.NoOp)

    /**
     * Setting this [LightsState] restores the default light.
     */
    @JvmField
    public val DEFAULT: LightsState = LightsState(MapboxLight.Default)
  }
}
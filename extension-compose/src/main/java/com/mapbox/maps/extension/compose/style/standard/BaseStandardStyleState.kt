package com.mapbox.maps.extension.compose.style.standard

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.TransitionOptions
import com.mapbox.maps.extension.compose.style.atmosphere.generated.AtmosphereState
import com.mapbox.maps.extension.compose.style.lights.LightsState
import com.mapbox.maps.extension.compose.style.precipitations.generated.RainState
import com.mapbox.maps.extension.compose.style.precipitations.generated.SnowState
import com.mapbox.maps.extension.compose.style.projection.generated.Projection
import com.mapbox.maps.extension.compose.style.terrain.generated.TerrainState

/**
 * The base state holder for the Standard Style.
 */
@Stable
@MapboxExperimental
public open class BaseStandardStyleState protected constructor(
  initialProjection: Projection,
  initialAtmosphereState: AtmosphereState,
  initialRainState: RainState,
  initialSnowState: SnowState,
  initialTerrainState: TerrainState = TerrainState.INITIAL,
  initialLightsState: LightsState = LightsState.INITIAL,
  initialStyleTransition: TransitionOptions,
) {
  /**
   * The projection to be set to the map. Defaults to [Projection.INITIAL] meaning that projection value is taken from the Standard style definition.
   */
  public var projection: Projection by mutableStateOf(initialProjection)

  /**
   * The atmosphere to be set to the map. By default, no changes to the current atmosphere.
   */
  public var atmosphereState: AtmosphereState by mutableStateOf(initialAtmosphereState)

  /**
   * The snow effect to be set to the map. By default, no changes to the current snow.
   */
  @MapboxExperimental
  public var snowState: SnowState by mutableStateOf(initialSnowState)

  /**
   * The rain effect to be set to the map. By default, no changes to the current rain.
   */
  @MapboxExperimental
  public var rainState: RainState by mutableStateOf(initialRainState)

  /**
   * The terrain to be set to the map. Defaults to initial state meaning no custom terrain is added, default value is taken from Standard style definition.
   */
  public var terrainState: TerrainState by mutableStateOf(initialTerrainState)

  /**
   * The lights to be set to the map. By default, no changes to the current lights defined in style.
   */
  public var lightsState: LightsState by mutableStateOf(initialLightsState)

  /**
   * Transition options applied when loading the style.
   */
  public var styleTransition: TransitionOptions by mutableStateOf(initialStyleTransition)
}
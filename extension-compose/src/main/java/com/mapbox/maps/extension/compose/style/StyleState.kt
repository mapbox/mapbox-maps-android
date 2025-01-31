package com.mapbox.maps.extension.compose.style

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.TransitionOptions
import com.mapbox.maps.extension.compose.style.atmosphere.generated.AtmosphereState
import com.mapbox.maps.extension.compose.style.interactions.StyleInteractionsState
import com.mapbox.maps.extension.compose.style.lights.LightsState
import com.mapbox.maps.extension.compose.style.precipitations.generated.RainState
import com.mapbox.maps.extension.compose.style.precipitations.generated.SnowState
import com.mapbox.maps.extension.compose.style.projection.generated.Projection
import com.mapbox.maps.extension.compose.style.terrain.generated.TerrainState
import com.mapbox.maps.extension.style.utils.transition

/**
 * Create and remember a [StyleState] with init block.
 *
 * @param init the initialization block to be applied to the [StyleState] after created and remembered.
 *
 * @return [StyleState]
 */
@Composable
public inline fun rememberStyleState(crossinline init: StyleState.() -> Unit = {}): StyleState {
  return remember {
    StyleState()
  }.apply(init)
}

/**
 * The state holder for the Style.
 */
@OptIn(MapboxExperimental::class)
@Stable
public class StyleState internal constructor(
  initialStyleImportsConfig: StyleImportsConfig,
  initialStyleInteractionsState: StyleInteractionsState,
  initialProjection: Projection,
  initialAtmosphereState: AtmosphereState,
  initialRainState: RainState,
  initialSnowState: SnowState,
  initialTerrainState: TerrainState = TerrainState.INITIAL,
  initialLightsState: LightsState = LightsState.INITIAL,
  initialStyleTransition: TransitionOptions,
  initialColorTheme: StyleColorTheme,
) {
  /**
   * Construct a default [StyleState].
   */
  public constructor() : this(
    initialStyleImportsConfig = StyleImportsConfig(),
    initialStyleInteractionsState = StyleInteractionsState(),
    initialProjection = Projection.INITIAL,
    initialAtmosphereState = AtmosphereState(),
    initialRainState = RainState(),
    initialSnowState = SnowState(),
    initialTerrainState = TerrainState.INITIAL,
    initialLightsState = LightsState.INITIAL,
    initialStyleTransition = transition { },
    initialColorTheme = StyleColorTheme.STYLE_DEFAULT
  )

  /**
   * The style import configurations for all the style imports in the style. You can use [styleImportsConfig] to create it.
   */
  public var styleImportsConfig: StyleImportsConfig by mutableStateOf(initialStyleImportsConfig)

  /**
   * The [StyleInteractionsState] manages the map interactions defined for the style.
   */
  @MapboxExperimental
  public var styleInteractionsState: StyleInteractionsState by mutableStateOf(
    initialStyleInteractionsState
  )

  /**
   * The projection to be set to the map. Defaults to [Projection.INITIAL] meaning that projection value is taken from the [style] definition.
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
   * The terrain to be set to the map. Defaults to initial state meaning no custom terrain is added, default value is taken from [style] definition.
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

  /**
   * The color theme to be set to the map. Defaults to initial color theme meaning no custom theme is added, default value is taken from [style] definition.
   */
  @MapboxExperimental
  public var styleColorTheme: StyleColorTheme by mutableStateOf(initialColorTheme)
}
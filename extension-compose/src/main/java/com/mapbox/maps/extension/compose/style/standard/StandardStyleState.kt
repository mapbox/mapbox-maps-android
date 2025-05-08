package com.mapbox.maps.extension.compose.style.standard

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
import com.mapbox.maps.extension.compose.style.standard.generated.StandardStyleInteractionsState
import com.mapbox.maps.extension.compose.style.terrain.generated.TerrainState
import com.mapbox.maps.extension.style.utils.transition

/**
 * Create and remember a [StandardStyleState] with init block.
 *
 * @param init the initialization block to be applied to the [StandardStyleState] after created and remembered.
 *
 * @return [StandardStyleState]
 */
@Composable
public inline fun rememberStandardStyleState(crossinline init: StandardStyleState.() -> Unit = {}): StandardStyleState {
  return remember {
    StandardStyleState()
  }.apply(init)
}

/**
 * Experimental state holder for the Mapbox Standard Style.
 */
@OptIn(MapboxExperimental::class)
@Stable
public class StandardStyleState internal constructor(
  initialStyleInteractionsState: StandardStyleInteractionsState,
  initialProjection: Projection,
  initialAtmosphereState: AtmosphereState,
  initialRainState: RainState,
  initialSnowState: SnowState,
  initialTerrainState: TerrainState = TerrainState.INITIAL,
  initialLightsState: LightsState = LightsState.INITIAL,
  initialStyleTransition: TransitionOptions,
  initialConfigurationState: StandardStyleConfigurationState,
) : BaseStandardStyleState(
  initialProjection = initialProjection,
  initialAtmosphereState = initialAtmosphereState,
  initialRainState = initialRainState,
  initialSnowState = initialSnowState,
  initialTerrainState = initialTerrainState,
  initialLightsState = initialLightsState,
  initialStyleTransition = initialStyleTransition,
) {
  /**
   * Construct a default [StandardStyleState].
   */
  public constructor() : this(
    initialStyleInteractionsState = StandardStyleInteractionsState(),
    initialProjection = Projection.INITIAL,
    initialAtmosphereState = AtmosphereState(),
    initialRainState = RainState(),
    initialSnowState = SnowState(),
    initialTerrainState = TerrainState.INITIAL,
    initialLightsState = LightsState.INITIAL,
    initialStyleTransition = transition { },
    initialConfigurationState = StandardStyleConfigurationState()
  )

  /**
   * The [StyleInteractionsState] manages the map interactions defined for the style.
   */
  public var interactionsState: StandardStyleInteractionsState by mutableStateOf(
    initialStyleInteractionsState
  )

  /**
   * Configuration state options to be applied to Mapbox Standard Style.
   */
  public var configurationsState: StandardStyleConfigurationState by mutableStateOf(initialConfigurationState)
}
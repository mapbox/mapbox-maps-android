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
 * Create and remember a [ExperimentalStandardStyleState] with init block.
 *
 * @param init the initialization block to be applied to the [ExperimentalStandardStyleState] after created and remembered.
 *
 * @return [ExperimentalStandardStyleState]
 */
@Composable
@MapboxExperimental
public inline fun rememberExperimentalStandardStyleState(crossinline init: ExperimentalStandardStyleState.() -> Unit = {}): ExperimentalStandardStyleState {
  return remember {
    ExperimentalStandardStyleState()
  }.apply(init)
}

/**
 * Experimental state holder for the Mapbox Standard Style.
 */
@Stable
@MapboxExperimental
public class ExperimentalStandardStyleState internal constructor(
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
   * Construct a default [ExperimentalStandardStyleState].
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
  @MapboxExperimental
  public var interactionsState: StandardStyleInteractionsState by mutableStateOf(
    initialStyleInteractionsState
  )

  /**
   * Configuration state options to be applied to Mapbox Standard Style.
   */
  @MapboxExperimental
  public var configurationsState: StandardStyleConfigurationState by mutableStateOf(initialConfigurationState)
}
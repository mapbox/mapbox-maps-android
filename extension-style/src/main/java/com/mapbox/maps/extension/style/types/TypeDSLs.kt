package com.mapbox.maps.extension.style.types

import com.mapbox.maps.MapboxExperimental

/**
 * Dsl marker for Light.
 *
 * Controls the scope of the Light Dsl.
 */
@DslMarker
annotation class LightDsl

/**
 * Dsl marker for Layer.
 *
 * Controls the scope of the Layer Dsl.
 */
@DslMarker
annotation class LayersDsl

/**
 * Dsl marker for Source.
 *
 * Controls the scope of the Source Dsl.
 */
@DslMarker
annotation class SourceDsl

/**
 * Dsl marker for Expression.
 *
 * Controls the scope of the Expression Dsl.
 */
@DslMarker
annotation class ExpressionDsl

/**
 * Dsl marker for Terrain.
 *
 * Controls the scope of the Terrain Dsl.
 */
@DslMarker
annotation class TerrainDsl

/**
 * Dsl marker for Atmosphere.
 *
 * Controls the scope of the Atmosphere Dsl.
 */
@DslMarker
annotation class AtmosphereDsl

/**
 * Dsl marker for Snow.
 *
 * Controls the scope of the Snow Dsl.
 */
@DslMarker
@MapboxExperimental
annotation class SnowDsl

/**
 * Dsl marker for Rain.
 *
 * Controls the scope of the Rain Dsl.
 */
@DslMarker
@MapboxExperimental
annotation class RainDsl
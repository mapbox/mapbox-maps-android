package com.mapbox.maps.extension.compose.ornaments

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMapScopeMarker
import com.mapbox.maps.extension.compose.ornaments.attribution.MapAttributionScope
import com.mapbox.maps.extension.compose.ornaments.compass.MapCompassScope
import com.mapbox.maps.extension.compose.ornaments.indoorselector.MapIndoorSelectorScope
import com.mapbox.maps.extension.compose.ornaments.logo.MapLogoScope
import com.mapbox.maps.extension.compose.ornaments.scalebar.MapScaleBarScope

/**
 * Configures the ornaments rendered by [MapOrnamentsScope.Defaults].
 *
 * Each ornament is a slot: leave it untouched to render the standard ornament, set it to `null`
 * to hide it, or set it to your own composable to render that instead.
 *
 * As the set of ornaments enabled by default grows, new ones are added here as new slots rather
 * than as new parameters on [MapOrnamentsScope.Defaults], which keeps that function's signature
 * unchanged.
 */
@MapboxMapScopeMarker
@MapboxExperimental
public class MapDefaultOrnamentsScope internal constructor() {

  /**
   * The Mapbox Compass ornament, or `null` to hide it.
   */
  @MapboxExperimental
  public var compass: (@Composable MapCompassScope.() -> Unit)? = { Compass() }

  /**
   * The Mapbox ScaleBar ornament, or `null` to hide it.
   */
  @MapboxExperimental
  public var scaleBar: (@Composable MapScaleBarScope.() -> Unit)? = { ScaleBar() }

  /**
   * The Mapbox Logo ornament, or `null` to hide it.
   */
  @MapboxExperimental
  public var logo: (@Composable MapLogoScope.() -> Unit)? = { Logo() }

  /**
   * The Mapbox Attribution ornament, or `null` to hide it.
   */
  @MapboxExperimental
  public var attribution: (@Composable MapAttributionScope.() -> Unit)? = { Attribution() }
}

/**
 * A [MapOrnamentsScope] provides a scope for adding ornaments (compass, scale bar, logo,
 * attribution, indoor selector, and future ornaments) to a
 * [MapboxMap][com.mapbox.maps.extension.compose.MapboxMap] via its `ornaments` slot.
 *
 * Use [Defaults] to render the ornaments that Mapbox enables by default, hiding or replacing
 * individual ones through [MapDefaultOrnamentsScope]. Use the block functions ([compass],
 * [scaleBar], [logo], [attribution], [indoorSelector]) to add an ornament outside that set.
 *
 * Example:
 * ```
 * MapboxMap(
 *   ornaments = {
 *     Defaults { scaleBar = null }
 *     indoorSelector { IndoorSelector() }
 *   }
 * ) { /* content */ }
 * ```
 */
@MapboxMapScopeMarker
@MapboxExperimental
@Immutable
public class MapOrnamentsScope internal constructor(
  private val mapView: MapView,
  private val boxScope: BoxScope
) {

  /**
   * Add the [MapCompassScope.Compass] ornament to the map.
   */
  @MapboxExperimental
  @Composable
  public fun compass(content: @Composable MapCompassScope.() -> Unit) {
    MapCompassScope(mapView, boxScope).content()
  }

  /**
   * Add the [MapScaleBarScope.ScaleBar] ornament to the map.
   */
  @MapboxExperimental
  @Composable
  public fun scaleBar(content: @Composable MapScaleBarScope.() -> Unit) {
    MapScaleBarScope(mapView, boxScope).content()
  }

  /**
   * Add the [MapLogoScope.Logo] ornament to the map.
   */
  @MapboxExperimental
  @Composable
  public fun logo(content: @Composable MapLogoScope.() -> Unit) {
    MapLogoScope(boxScope).content()
  }

  /**
   * Add the [MapAttributionScope.Attribution] ornament to the map.
   */
  @MapboxExperimental
  @Composable
  public fun attribution(content: @Composable MapAttributionScope.() -> Unit) {
    MapAttributionScope(mapView, boxScope).content()
  }

  /**
   * Add the [MapIndoorSelectorScope.IndoorSelector] ornament to the map.
   */
  @MapboxExperimental
  @Composable
  public fun indoorSelector(content: @Composable MapIndoorSelectorScope.() -> Unit) {
    MapIndoorSelectorScope(mapView, boxScope).content()
  }

  /**
   * Renders the ornaments that Mapbox enables by default.
   *
   * Which ornaments those are may grow between releases. New ones are added as slots on
   * [MapDefaultOrnamentsScope] rather than as parameters here, so this function's signature does
   * not change as the default set grows.
   *
   * Use [configure] to hide an ornament (set its slot to `null`) or to render your own version
   * of it (set its slot to your composable):
   *
   * ```
   * Defaults {
   *   scaleBar = null
   *   compass = { Compass(resetToNorthUponClick = false) }
   * }
   * ```
   *
   * @param configure Configures which of the default ornaments render, and how.
   */
  @MapboxExperimental
  @Composable
  public fun Defaults(configure: MapDefaultOrnamentsScope.() -> Unit = {}) {
    val defaults = MapDefaultOrnamentsScope().apply(configure)
    defaults.compass?.let { compass(it) }
    defaults.scaleBar?.let { scaleBar(it) }
    defaults.logo?.let { logo(it) }
    defaults.attribution?.let { attribution(it) }
  }
}
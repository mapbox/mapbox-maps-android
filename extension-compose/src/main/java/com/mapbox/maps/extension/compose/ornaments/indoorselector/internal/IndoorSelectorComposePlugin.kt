package com.mapbox.maps.extension.compose.ornaments.indoorselector.internal

import com.mapbox.maps.IndoorManager
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.delegates.MapDelegateProvider

/**
 * Internal implementation of [IndoorSelectorComposePlugin], which simply holds the reference to
 * the [IndoorManager].
 */
@OptIn(MapboxExperimental::class, com.mapbox.annotation.MapboxExperimental::class)
internal class IndoorSelectorComposePlugin : MapPlugin {
  internal lateinit var indoorManager: IndoorManager
  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    indoorManager = delegateProvider.indoorManager
  }
}
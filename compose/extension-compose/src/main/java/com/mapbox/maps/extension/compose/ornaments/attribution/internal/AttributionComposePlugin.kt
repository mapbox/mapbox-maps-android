package com.mapbox.maps.extension.compose.ornaments.attribution.internal

import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.delegates.MapAttributionDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider

/**
 * Internal implementation of a [AttributionComposePlugin], which simply holds the reference to the
 * [MapAttributionDelegate].
 */
internal class AttributionComposePlugin : MapPlugin {
  internal lateinit var mapAttributionDelegate: MapAttributionDelegate
  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    mapAttributionDelegate = delegateProvider.mapAttributionDelegate
  }
}
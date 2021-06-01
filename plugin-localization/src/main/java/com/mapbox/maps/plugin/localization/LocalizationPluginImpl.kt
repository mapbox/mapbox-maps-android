package com.mapbox.maps.plugin.localization

import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.plugin.delegates.*
import java.util.*

/**
 * Impl class for LocalizationPlugin
 */
class LocalizationPluginImpl : LocalizationPlugin {

  override fun matchMapLanguageWithDeviceDefault(acceptFallback: Boolean) {
    TODO("Not yet implemented")
  }

  override fun setMapLanguage(language: Languages) {
    TODO("Not yet implemented")
  }

  override fun setMapLanguage(locale: Locale, acceptFallback: Boolean) {
    TODO("Not yet implemented")
  }

  /**
   * Called when the map is destroyed. Should be used to cleanup plugin resources for that map.
   */
  override fun cleanup() {
  }

  /**
   * Provides all map delegate instances.
   */
  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
  }

  override fun onStyleChanged(styleDelegate: StyleInterface) {
    TODO("Not yet implemented")
  }
}

/**
 * Extension function for MapView to get the localization plugin instance.
 *
 * @return Localization plugin instance
 */
@MapboxExperimental
fun MapPluginProviderDelegate.localization(): LocalizationPlugin {
  return this.getPlugin(LocalizationPluginImpl::class.java)!!
}
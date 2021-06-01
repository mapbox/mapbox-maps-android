package com.mapbox.maps.plugin.localization

import com.mapbox.geojson.Point
import com.mapbox.maps.CoordinateBounds
import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.MapStyleObserverPlugin
import java.util.*

/**
 * Useful plugin for quickly adjusting the maps language and the maps camera starting position.
 * You can either use [matchMapLanguageWithDeviceDefault(boolean acceptFallback)] to match the map language with
 * the one being currently used on the device. Using [setMapLanguage(Locale, boolean acceptFallback)] and it's
 * variants, you can also change the maps language at anytime to any of the supported languages.
 * <p>
 * The plugin uses a fallback logic in case there are missing resources
 * - if there is no available localization for a label, the plugin will use local name, if it's Latin script based,
 * otherwise English. Traditional Chinese falls back to Simplified Chinese before executing before mentioned logic.
 * </p>
 * The plugin only support Mapbox sources:
 * <ul>
 * <li>- mapbox.mapbox-streets-v6</li>
 * <li>- mapbox.mapbox-streets-v7</li>
 * <li>- mapbox.mapbox-streets-v8</li>
 * </ul>
 *
 */
interface LocalizationPlugin : MapPlugin, MapStyleObserverPlugin {

  /**
   * Initializing this plugin and then calling this method oftentimes will be the only thing you'll
   * need to quickly adjust the map language to the devices specified language.
   *
   * @param acceptFallback whether the locale should fallback to the first declared that matches the language,
   * the fallback locale can be added with [MapLocale#addMapLocale(Locale, MapLocale)], default is false.
   */
  fun matchMapLanguageWithDeviceDefault(acceptFallback: Boolean = false)

  /**
   * Set the map language directly by using one of the supported map languages found in
   * [Languages].
   *
   * @param language one of the support languages Mapbox uses
   */
  fun setMapLanguage(language: Languages)

  /**
   * If you'd like to set the map language to a specific locale, you can pass it in as a parameter
   * and MapLocale will try matching the information with one of the MapLocales found in its map.
   * If one isn't found, a null point exception will be thrown. To prevent this, ensure that the
   * locale you are trying to use, has a complementary [MapLocale] for it.
   *
   * @param locale a [Locale] which has a complementary [MapLocale] for it
   * @param acceptFallback whether the locale should fallback to the first declared that matches the language,
   * the fallback locale can be added with [MapLocale.addMapLocale], default is false.
   */
  fun setMapLanguage(locale: Locale, acceptFallback: Boolean = false)

}

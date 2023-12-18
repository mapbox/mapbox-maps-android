package com.mapbox.maps.extension.localization

import com.mapbox.maps.MapboxStyleManager
import java.util.*

/**
 * Extension function to localize style labels
 *
 * @param locale the locale that applied for localization
 * @param layerIds the id of layers that will localize on, default is null which means will localize all the feasible layers.
 *
 * @throws [RuntimeException] if current style is STANDARD which does not support localizing labels client-side runtime localization.
 */
@JvmOverloads
fun MapboxStyleManager.localizeLabels(locale: Locale, layerIds: List<String>? = null) {
  if (styleURI == "mapbox://styles/mapbox/standard") {
    throw RuntimeException(
      "Mapbox Standard style does not support client-side runtime localization." +
        " Consider using Mapbox internationalization capability instead: https://www.mapbox.com/blog/maps-internationalization-34-languages"
    )
  }
  setMapLanguage(locale, this, layerIds)
}
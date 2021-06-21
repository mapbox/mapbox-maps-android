package com.mapbox.maps.extension.localization

import com.mapbox.maps.extension.style.StyleInterface
import java.util.*

/**
 * Extension function to localize style labels
 */
fun StyleInterface.localizeLabels(locale: Locale) {
  setMapLanguage(locale, this)
}
package com.mapbox.maps.extension.style

import java.util.*

/**
 * Extension function to localize style labels
 */
fun StyleInterface.localizeLabels(locale: Locale){
  Localization().setMapLanguage(locale, this)
}

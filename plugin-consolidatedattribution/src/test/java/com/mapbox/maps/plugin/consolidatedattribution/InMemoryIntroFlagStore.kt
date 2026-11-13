package com.mapbox.maps.plugin.consolidatedattribution

/** Stands in for Common's settings, which need the native library. */
internal class InMemoryIntroFlagStore : IntroFlagStore {
  override var isSet: Boolean = false
}
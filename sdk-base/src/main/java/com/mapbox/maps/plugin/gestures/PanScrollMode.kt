package com.mapbox.maps.plugin.gestures

/**
 * The pan scroll mode that the gestures will take in account.
 */
enum class PanScrollMode {
  /** The map allows the user to scroll both horizontally and vertically.*/
  HorizontalAndVertical,

  /** The map allows the user to only scroll horizontally.*/
  Horizontal,

  /** The map allows the user to only scroll vertically.*/
  Vertical,
}
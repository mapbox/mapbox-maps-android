package com.mapbox.maps.plugin.location.listeneres;

/**
 * The Location Component exposes an API for listening to when the user long clicks on the location
 * layer icon visible on the map. when this event occurs, the {@link #onLocationComponentLongClick()} method
 * gets invoked.
 *
 * @deprecated Location Plugin is deprecated, use Location Component Plugin instead.
 */
@Deprecated
public interface OnLocationLongClickListener {

  /**
   * Called whenever user long clicks on the location layer drawn on the map.
   */
  void onLocationComponentLongClick();
}

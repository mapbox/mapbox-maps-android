package com.mapbox.maps.extension.style.layers

import com.mapbox.maps.LayerPosition
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.style.StyleInterface

/**
 * Bind the layer to the map controller persistently.
 *
 * Note: This is an experimental API and is a subject to change.
 *
 * Whenever a new style is being parsed and currently used style has persistent layers,
 * an engine will try to do following:
 *   - keep the persistent layer at its relative position
 *   - keep the source used by a persistent layer
 *   - keep images added through `addStyleImage` method
 *
 * In cases when a new style has the same layer, source or image resource, style's resources would be
 * used instead and `MapLoadingError` event will be emitted.
 *
 * @param delegate The style
 * @param position the position that the current layer is added to
 */
@MapboxExperimental
fun Layer.bindPersistentlyTo(delegate: StyleInterface, position: LayerPosition? = null) {
  this.delegate = delegate
  val expected = delegate.addPersistentStyleLayer(getCachedLayerProperties(), position)
  expected.error?.let {
    throw RuntimeException("Add persistent layer failed: $it")
  }
}

/**
 * Get the persistent property as Boolean.
 *
 * If true, the layer will be persisted across style changes.
 */
@MapboxExperimental
fun Layer.isPersistent(): Boolean? {
  return delegate?.isStyleLayerPersistent(layerId)?.value
}
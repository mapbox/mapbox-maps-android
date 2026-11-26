package com.mapbox.maps

import com.mapbox.common.Cancelable

/**
 * Shared no-op [Cancelable] for APIs that must return one but have nothing left to cancel
 */
internal object EmptyCancelable : Cancelable {
  override fun cancel() = Unit
}
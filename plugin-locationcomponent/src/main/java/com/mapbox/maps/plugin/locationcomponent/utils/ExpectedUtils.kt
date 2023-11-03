package com.mapbox.maps.plugin.locationcomponent.utils

import com.mapbox.bindgen.Expected
import com.mapbox.maps.MapboxLocationComponentException

/**
 * Internal function to check if a method invoke on Value succeeded, throws exception if not.
 */
@JvmSynthetic
internal inline fun <reified T> Expected<String, T>.take(): T {
  this.also {
    it.error?.let { err ->
      throw MapboxLocationComponentException(err)
    }
    it.value?.let { v ->
      return v
    }
  }
  throw MapboxLocationComponentException("Error in parsing expression.")
}
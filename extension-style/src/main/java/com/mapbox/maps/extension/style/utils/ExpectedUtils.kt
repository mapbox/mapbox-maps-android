package com.mapbox.maps.extension.style.utils

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None

/**
 * Internal function to check if a method invoke on StyleDelegate succeeded, throws exception if not.
 */
internal fun Expected<String, None>?.check() {
  this?.also {
    it.error?.let { err ->
      throw RuntimeException(err)
    }
  } ?: throw RuntimeException("Plugin is not added to Style yet.")
}

/**
 * Internal function to check if a method invoke on StyleDelegate succeeded, throws exception if not.
 */
internal inline fun <reified T> Expected<String, T>?.take(): T {
  this?.also {
    it.error?.let { err ->
      throw RuntimeException(err)
    }
    it.value?.let { v ->
      return v
    }
  }
  throw RuntimeException("Plugin is not added to Style yet.")
}
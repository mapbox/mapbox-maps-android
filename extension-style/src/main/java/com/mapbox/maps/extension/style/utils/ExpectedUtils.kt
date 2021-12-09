package com.mapbox.maps.extension.style.utils

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.maps.MapboxStyleException

/**
 * Internal function to check if a method invoke on StyleDelegate succeeded, throws exception if not.
 */
internal fun Expected<String, None>?.check() {
  this?.also {
    it.error?.let { err ->
      throw MapboxStyleException(err)
    }
  } ?: throw MapboxStyleException("Plugin is not added to Style yet.")
}

/**
 * Internal function to check if a method invoke on StyleDelegate succeeded, throws exception if not.
 */
internal inline fun <reified T> Expected<String, T>?.take(): T {
  this?.also {
    it.error?.let { err ->
      throw MapboxStyleException(err)
    }
    it.value?.let { v ->
      return v
    }
  }
  throw MapboxStyleException("Plugin is not added to Style yet.")
}
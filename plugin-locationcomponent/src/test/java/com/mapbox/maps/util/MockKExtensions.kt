package com.mapbox.maps.util

import io.mockk.MockKMatcherScope

// vararg capture isn't implemented, here's a workaround to collect it https://github.com/mockk/mockk/issues/432
inline fun <reified T : Any> MockKMatcherScope.captureVararg(
  capture: MutableCollection<T>
) = varargAll<T> {
  capture.add(it)
  true
}
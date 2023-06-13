package com.mapbox.maps

import org.robolectric.annotation.Implements

@Implements(Tracing::class)
class ShadowTracing {

  companion object {
    fun setTracingBackendType(type: TracingBackendType) {
    }
  }
}
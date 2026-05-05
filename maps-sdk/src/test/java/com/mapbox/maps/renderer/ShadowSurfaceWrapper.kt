package com.mapbox.maps.renderer

import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements

@Implements(SurfaceWrapper::class)
public class ShadowSurfaceWrapper {
  @Implementation
  fun __constructor__() {
    // no-op: skip native initialize()
  }
}
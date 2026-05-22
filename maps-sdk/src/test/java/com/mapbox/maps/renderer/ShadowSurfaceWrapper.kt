package com.mapbox.maps.renderer

import android.view.Surface
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements

@Implements(SurfaceWrapper::class)
public class ShadowSurfaceWrapper {
  @Implementation
  fun __constructor__() {
    // no-op: skip native initialize()
  }

  @Implementation
  fun setSurface(surface: Surface?) {
    // no-op: skip native nativeSetSurface
  }

  @Implementation
  fun getANativeWindow(): Long = STUB_NATIVE_WINDOW_PTR

  @Implementation
  fun releaseSurface() {
    // no-op: skip native nativeRelease
  }

  companion object {
    const val STUB_NATIVE_WINDOW_PTR: Long = 12345L
  }
}
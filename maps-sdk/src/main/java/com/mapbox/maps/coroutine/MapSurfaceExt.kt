package com.mapbox.maps.coroutine

import androidx.annotation.RestrictTo
import com.mapbox.maps.MapSurface
import kotlinx.coroutines.flow.StateFlow

@get:RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
val MapSurface.isRenderable: StateFlow<Boolean>
  @JvmSynthetic
  get() = mapController.isRenderable
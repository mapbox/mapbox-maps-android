@file:JvmName("MapboxTracingConstants")

package com.mapbox.maps

import androidx.annotation.RestrictTo

/**
 * Trace label prefix used by Maps SDK Android tracing call sites.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
const val MAPS_SDK_TRACE_PREFIX: String = "maps-sdk:"
@file:JvmName("MapboxConstants")

package com.mapbox.maps

import java.util.*

/**
 * Name of the database file.
 */
const val DATABASE_NAME = "mbx.db"

/**
 * The default cache size, which is 50MB
 */
const val DEFAULT_CACHE_SIZE = 1024 * 1024 * 50L

/**
 * Default Locale for data processing (ex: String.toLowerCase(com.mapbox.maps.getMAPBOX_LOCALE, "foo"))
 */
val MAPBOX_LOCALE: Locale = Locale.US

/**
 * Resource name used to lookup the Mapbox access token.
 */
const val MAPBOX_ACCESS_TOKEN_RESOURCE_NAME = "mapbox_access_token"

internal const val CORE_SHARED_LIBRARY_NAME = "mapbox-common"

internal const val MAP_SHARED_LIBRARY_NAME = "mapbox-maps"

/**
 * The currently supported minimum zoom level.
 */
internal const val MINIMUM_ZOOM = 0.0f

/**
 * The currently supported maximum zoom level.
 */
internal const val MAXIMUM_ZOOM = 25.5f

/**
 * The currently supported maximum bearing
 */
internal const val MAXIMUM_BEARING = 360.0

/**
 * The currently supported minimum bearing
 */
internal const val MINIMUM_BEARING = 0.0
@file:JvmName("MapboxConstants")

package com.mapbox.maps

import java.util.*

/**
 * The path to the map data folder. The DATA_PATH will be relative(appended) to the application's files directory.
 *
 * The implementation will use this folder for storing offline style packages and temporary data.
 *
 * The application must have sufficient permissions to create files within the provided directory.
 */
const val DATA_PATH = ".mapbox/maps/"

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
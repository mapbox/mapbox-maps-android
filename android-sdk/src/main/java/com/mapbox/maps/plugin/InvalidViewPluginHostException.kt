package com.mapbox.maps.plugin

/**
 * Exception thrown when a [ViewPlugin] is loaded in a context that doesn't have a view hierarchy associated.
 * @param message The exception message
 */
class InvalidViewPluginHostException(message: String) : Exception(message)
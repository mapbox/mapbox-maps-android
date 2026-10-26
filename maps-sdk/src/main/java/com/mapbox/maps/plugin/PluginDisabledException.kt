package com.mapbox.maps.plugin

/**
 * Exception thrown when accessing a plugin that the host intentionally disabled for the current map
 * by registering a [DisabledPlugin] placeholder in its place.
 *
 * @param message actionable description of why the plugin is unavailable.
 */
class PluginDisabledException(message: String) : RuntimeException(message)
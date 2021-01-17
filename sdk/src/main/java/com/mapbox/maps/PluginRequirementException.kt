package com.mapbox.maps

/**
 * This class is the subclass of RuntimeException,
 * will be used while there is one or more plugin are required but removed from gradle configuration.
 * Currently the required plugins are logo plugin and attribution plugin.
 */
class PluginRequirementException(pluginName: String) :
  RuntimeException("$pluginName is required! Please visit https://docs.mapbox.com/android/maps/guides/#attribution for more details.")
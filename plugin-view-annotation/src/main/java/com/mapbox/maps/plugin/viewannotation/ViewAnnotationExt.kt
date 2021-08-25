@file:JvmName("ViewAnnotationUtils")
package com.mapbox.maps.plugin.viewannotation

import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ViewAnnotationOptions
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

/**
 * Extension val for MapView to get the view annotation plugin instance.
 *
 * @return Map overlay plugin instance
 */
@MapboxExperimental
val MapPluginProviderDelegate.viewAnnotation: ViewAnnotationPlugin
  @JvmName("getViewAnnotation")
  get() = this.getPlugin(Plugin.MAPBOX_VIEW_ANNOTATION_PLUGIN_ID)!!
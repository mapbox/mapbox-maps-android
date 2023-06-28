@file:JvmName("AnnotationsUtils")

package com.mapbox.maps.plugin.annotation

import androidx.annotation.RestrictTo
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

/**
 * Extension val for MapView to get the Annotation plugin instance.
 */
val MapPluginProviderDelegate.annotations: AnnotationPlugin
  @JvmName("getAnnotations")
  get() = this.getPlugin(Plugin.MAPBOX_ANNOTATION_PLUGIN_ID)!!

/**
 * Static method to create instance of Mapbox annotation plugin.
 * @suppress
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
@JvmSynthetic
fun createAnnotationPlugin(): AnnotationPlugin {
  return AnnotationPluginImpl()
}
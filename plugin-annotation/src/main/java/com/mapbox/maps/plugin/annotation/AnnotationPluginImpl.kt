package com.mapbox.maps.plugin.annotation

import com.mapbox.maps.plugin.annotation.generated.CircleManager
import com.mapbox.maps.plugin.annotation.generated.FillManager
import com.mapbox.maps.plugin.annotation.generated.LineManager
import com.mapbox.maps.plugin.annotation.generated.SymbolManager
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate

/**
 * The impl class for AnnotationPlugin
 */
class AnnotationPluginImpl : AnnotationPlugin {
  private lateinit var delegateProvider: MapDelegateProvider
  private val managerList = mutableListOf<AnnotationManager<*, *, *, *, *, *>>()
  private var width = 0
  private var height = 0
  /**
   * Get an annotation manger
   *
   * @param type the type of annotation manger
   * @param belowLayerId the id of the layer above the annotation layer
   * @param touchAreaShiftX the scrolled left position of mapView
   * @param touchAreaShiftY the scrolled top position of mapView
   *
   * @return the annotation manger
   */
  override fun getAnnotationManager(
    type: AnnotationType,
    belowLayerId: String?,
    touchAreaShiftX: Int,
    touchAreaShiftY: Int
  ): AnnotationManager<*, *, *, *, *, *> {
    val manager = when (type) {
      AnnotationType.Fill -> FillManager(delegateProvider, belowLayerId, touchAreaShiftX, touchAreaShiftY)
      AnnotationType.Circle -> CircleManager(delegateProvider, belowLayerId, touchAreaShiftX, touchAreaShiftY)
      AnnotationType.Line -> LineManager(delegateProvider, belowLayerId, touchAreaShiftX, touchAreaShiftY)
      AnnotationType.Symbol -> SymbolManager(delegateProvider, belowLayerId, touchAreaShiftX, touchAreaShiftY)
    }
    manager.onSizeChanged(width, height)
    managerList.add(manager)
    return manager
  }

  /**
   * Invoked when MapView's width and height have changed.
   * @param width the width of mapView
   * @param height the height of mapView
   */
  override fun onSizeChanged(width: Int, height: Int) {
    this.width = width
    this.height = height
    managerList.forEach { it.onSizeChanged(width, height) }
  }

  /**
   * Provides all map delegate instances.
   */
  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    this.delegateProvider = delegateProvider
  }
}

/**
 * Extension function for MapView to get the Annotation plugin instance.
 *
 * @return Compass plugin instance
 */
fun MapPluginProviderDelegate.getAnnotationPlugin(): AnnotationPlugin {
  return this.getPlugin(AnnotationPluginImpl::class.java)!!
}
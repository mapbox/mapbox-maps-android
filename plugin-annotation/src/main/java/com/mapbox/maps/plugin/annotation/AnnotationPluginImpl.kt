package com.mapbox.maps.plugin.annotation

import android.view.View
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.plugin.annotation.generated.CircleManager
import com.mapbox.maps.plugin.annotation.generated.FillManager
import com.mapbox.maps.plugin.annotation.generated.LineManager
import com.mapbox.maps.plugin.annotation.generated.SymbolManager
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import java.lang.ref.WeakReference

/**
 * The impl class for AnnotationPlugin
 */
class AnnotationPluginImpl : AnnotationPlugin {
  private lateinit var delegateProvider: MapDelegateProvider
  private val managerList = mutableListOf<WeakReference<AnnotationManager<*, *, *, *, *, *>>>()
  private var width = 0
  private var height = 0

  /**
   * Create an annotation manger instance.
   *
   * @param mapView the mapView
   * @param type The type of he type of annotation manger
   * @param annotationConfig the configuration for AnnotationManager
   * @return the annotation manger
   */
  override fun createAnnotationManager(
    mapView: View,
    type: AnnotationType,
    annotationConfig: AnnotationConfig?
  ): AnnotationManager<*, *, *, *, *, *> {
    val manager = when (type) {
      AnnotationType.Fill -> FillManager(mapView, delegateProvider, annotationConfig)
      AnnotationType.Circle -> CircleManager(mapView, delegateProvider, annotationConfig)
      AnnotationType.Line -> LineManager(mapView, delegateProvider, annotationConfig)
      AnnotationType.Symbol -> SymbolManager(mapView, delegateProvider, annotationConfig)
    }
    manager.onSizeChanged(width, height)
    managerList.add(WeakReference(manager))
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
    managerList.forEach { it.get()?.onSizeChanged(width, height) }
  }

  /**
   * Called when a new Style is loaded.
   */
  override fun onStyleChanged(styleDelegate: StyleManagerInterface) {
    managerList.forEach { it.get()?.onStyleLoaded(styleDelegate) }
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
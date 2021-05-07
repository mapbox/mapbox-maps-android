package com.mapbox.maps.plugin.annotation

import android.view.View
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import java.lang.ref.WeakReference

/**
 * The impl class for AnnotationPlugin
 */
class AnnotationPluginImpl : AnnotationPlugin {
  private lateinit var delegateProvider: MapDelegateProvider
  private val managerList = mutableListOf<WeakReference<AnnotationManager<*, *, *, *, *, *, *>>>()
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
  ): AnnotationManager<*, *, *, *, *, *, *> {
    val manager = when (type) {
      AnnotationType.PolygonAnnotation -> PolygonAnnotationManager(mapView, delegateProvider, annotationConfig)
      AnnotationType.CircleAnnotation -> CircleAnnotationManager(mapView, delegateProvider, annotationConfig)
      AnnotationType.PolylineAnnotation -> PolylineAnnotationManager(mapView, delegateProvider, annotationConfig)
      AnnotationType.PointAnnotation -> PointAnnotationManager(mapView, delegateProvider, annotationConfig)
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
  override fun onStyleChanged(styleDelegate: StyleInterface) {
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
 * Extension val for MapView to get the Annotation plugin instance.
 */
val MapPluginProviderDelegate.annotations: AnnotationPlugin
  get() = this.getPlugin(AnnotationPluginImpl::class.java)!!
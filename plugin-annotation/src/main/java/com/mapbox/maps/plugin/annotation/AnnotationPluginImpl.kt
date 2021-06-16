package com.mapbox.maps.plugin.annotation

import android.view.View
import androidx.annotation.VisibleForTesting
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

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal val managerList = mutableListOf<WeakReference<AnnotationManager<*, *, *, *, *, *, *>>>()
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
      AnnotationType.PolygonAnnotation -> PolygonAnnotationManager(
        mapView,
        delegateProvider,
        annotationConfig
      )
      AnnotationType.CircleAnnotation -> CircleAnnotationManager(
        mapView,
        delegateProvider,
        annotationConfig
      )
      AnnotationType.PolylineAnnotation -> PolylineAnnotationManager(
        mapView,
        delegateProvider,
        annotationConfig
      )
      AnnotationType.PointAnnotation -> PointAnnotationManager(
        mapView,
        delegateProvider,
        annotationConfig
      )
    }
    manager.onSizeChanged(width, height)
    managerList.add(WeakReference(manager))
    return manager
  }

  /**
   * Removes an annotation manager, this will remove the underlying layer and source from the style.
   * A removed annotation manager will not be able to reuse anymore, users need to create new annotation manger
   * to add annotations.
   */
  override fun removeAnnotationManager(annotationManager: AnnotationManager<*, *, *, *, *, *, *>) {
    managerList.forEachIndexed { index, weakReference ->
      weakReference.get()?.let {
        if (it == annotationManager) {
          managerList.removeAt(index)
          annotationManager.onDestroy()
          return
        }
      }
    }
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
   *
   * @param styleDelegate
   */
  override fun onStyleChanged(styleDelegate: StyleInterface) {
    // no-ops
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
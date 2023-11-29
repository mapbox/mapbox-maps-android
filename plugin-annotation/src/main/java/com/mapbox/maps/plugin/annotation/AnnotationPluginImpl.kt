package com.mapbox.maps.plugin.annotation

import androidx.annotation.RestrictTo
import androidx.annotation.VisibleForTesting
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import java.lang.ref.WeakReference

/**
 * The impl class for AnnotationPlugin
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class AnnotationPluginImpl : AnnotationPlugin {
  private lateinit var delegateProvider: MapDelegateProvider

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal val managerList = mutableListOf<WeakReference<AnnotationManager<*, *, *, *, *, *, *>>>()
  private var width = 0
  private var height = 0

  /**
   * Create an annotation manager.
   *
   * @param type The type of the created annotation manager
   * @param annotationConfig Default is null, used for some custom configs
   * @return The created annotation manager
   */
  override fun createAnnotationManager(
    type: AnnotationType,
    annotationConfig: AnnotationConfig?
  ): AnnotationManager<*, *, *, *, *, *, *> {
    val manager = when (type) {
      AnnotationType.PolygonAnnotation -> PolygonAnnotationManager(
        delegateProvider,
        annotationConfig
      )
      AnnotationType.CircleAnnotation -> CircleAnnotationManager(
        delegateProvider,
        annotationConfig
      )
      AnnotationType.PolylineAnnotation -> PolylineAnnotationManager(
        delegateProvider,
        annotationConfig
      )
      AnnotationType.PointAnnotation -> PointAnnotationManager(
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
   * A removed annotation manager will not be able to reuse anymore, users need to create new annotation manager
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
   * @param style
   */
  override fun onStyleChanged(style: MapboxStyleManager) {
    // no-ops
  }

  /**
   * Called when the map is destroyed. Should be used to cleanup plugin resources for that map.
   */
  override fun cleanup() {
    managerList.forEach {
      it.get()?.onDestroy()
    }
    managerList.clear()
  }

  /**
   * Provides all map delegate instances.
   */
  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    this.delegateProvider = delegateProvider
  }
}
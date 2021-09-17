package com.mapbox.maps.plugin.viewannotation.core

import android.view.View
import com.mapbox.common.Logger
import com.mapbox.geojson.Point
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.delegates.*
import com.mapbox.maps.plugin.viewannotation.ViewAnnotationAnchor
import com.mapbox.maps.plugin.viewannotation.ViewAnnotationOptions
import com.mapbox.maps.plugin.viewannotation.ViewAnnotationPositionDescriptor
import com.mapbox.maps.plugin.viewannotation.ViewAnnotationsPositionCallback

class ViewAnnotationCore(
  delegateProvider: MapDelegateProvider
) {
  private val annotations = LinkedHashMap<String, EnhancedViewAnnotationOptions>()
  private val updateTempList = mutableListOf<ViewAnnotationPositionDescriptor>()
  private val updateList = mutableListOf<ViewAnnotationPositionDescriptor>()

  private val mapCameraManagerDelegate = delegateProvider.mapCameraManagerDelegate
  private val mapTransformDelegate = delegateProvider.mapTransformDelegate

  private val backupSymbolLayerManager by lazy {
    BackupSymbolLayerManager(
      annotations,
      delegateProvider
    )
  }

  fun setView(mapView: View) {
    backupSymbolLayerManager.createAnnotationManager(mapView)
  }

  fun addViewAnnotation(viewId: String, options: ViewAnnotationOptions) {
    annotations[viewId] = EnhancedViewAnnotationOptions(options)
    if (!options.allowViewAnnotationsCollision || options.featureIdentifier != null) {
      backupSymbolLayerManager.createBackupSymbol(viewId, options)
    }
  }

  fun updateViewAnnotation(viewId: String, options: ViewAnnotationOptions) {
    annotations[viewId]?.let {
      annotations[viewId] = EnhancedViewAnnotationOptions(options)
      if (!options.allowViewAnnotationsCollision || options.featureIdentifier != null) {
        backupSymbolLayerManager.updateBackupSymbol(viewId, options)
      }
    }
  }

  fun removeViewAnnotation(viewId: String) {
    backupSymbolLayerManager.removeBackupSymbol(viewId)
    annotations.remove(viewId)
  }

  fun calculateViewAnnotationsPosition(callback: ViewAnnotationsPositionCallback) {
    updateTempList.clear()
    updateList.clear()
    for (annotation in annotations) {
      calculateSimpleSingePosition(annotation.key, annotation.value)?.let {
        updateTempList.add(it)
      }
    }
    sortBySelected()
    callback.run(updateList)
  }

  private fun sortBySelected() {
    val iterator = updateTempList.iterator()
    while (iterator.hasNext()) {
      val descriptor = iterator.next()
      if (annotations[descriptor.identifier]?.options?.selected != true) {
        updateList.add(descriptor)
        iterator.remove()
      }
    }
    // we add leftover where actually 'selected' elements are left so that they are displayed on top
    updateList.addAll(updateTempList)
  }

  private fun calculateSimpleSingePosition(
    id: String,
    enhancedOptions: EnhancedViewAnnotationOptions
  ): ViewAnnotationPositionDescriptor? {
    val options = enhancedOptions.options
    val geometry = options.geometry
    if (geometry !is Point) {
      Logger.w(TAG, "View Annotation (id = $id) supports only point geometry for now!")
      return null
    }
    val pixel = mapCameraManagerDelegate.pixelForCoordinate(options.geometry as Point)
    var resultOffsetX = 0.0
    var resultOffsetY = 0.0
    options.anchor?.let {
      when(it) {
        ViewAnnotationAnchor.TOP -> {
          resultOffsetX = pixel.x - options.width / 2f
          resultOffsetY = pixel.y
        }
        ViewAnnotationAnchor.LEFT -> {
          resultOffsetX = pixel.x
          resultOffsetY = pixel.y - options.height / 2f
        }
        ViewAnnotationAnchor.BOTTOM -> {
          resultOffsetX = pixel.x - options.width / 2f
          resultOffsetY = pixel.y - options.height
        }
        ViewAnnotationAnchor.RIGHT -> {
          resultOffsetX = pixel.x - options.width
          resultOffsetY = pixel.y - options.height / 2f
        }
        ViewAnnotationAnchor.TOP_LEFT -> {
          resultOffsetX = pixel.x
          resultOffsetY = pixel.y
        }
        ViewAnnotationAnchor.BOTTOM_RIGHT -> {
          resultOffsetX = pixel.x - options.width
          resultOffsetY = pixel.y - options.height
        }
        ViewAnnotationAnchor.TOP_RIGHT -> {
          resultOffsetX = pixel.x - options.width
          resultOffsetY = pixel.y
        }
        ViewAnnotationAnchor.BOTTOM_LEFT -> {
          resultOffsetX = pixel.x
          resultOffsetY = pixel.y - options.height
        }
        ViewAnnotationAnchor.CENTER -> {
          resultOffsetX = pixel.x - options.width / 2f
          resultOffsetY = pixel.y - options.height / 2f
        }
      }
    }
    // apply offsets
    resultOffsetX += options.offsetX
    resultOffsetY -= options.offsetY
    // calculate if result coordinates belong to the viewport
    val visibleX = resultOffsetX >= -options.width && resultOffsetX <= mapTransformDelegate.getSize().width
    val visibleY = resultOffsetY >= -options.height && resultOffsetY <= mapTransformDelegate.getSize().height
    if (visibleX && visibleY) {
      return ViewAnnotationPositionDescriptor(
        id,
        ScreenCoordinate(resultOffsetX, resultOffsetY)
      )
    }
    return null
  }

  companion object {
    private const val TAG = "MbxCalloutView"
  }
}
package com.mapbox.maps.plugin.viewannotation.core

import com.mapbox.common.Logger
import com.mapbox.geojson.Point
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.delegates.MapFeatureQueryDelegate
import com.mapbox.maps.plugin.delegates.MapTransformDelegate
import com.mapbox.maps.plugin.viewannotation.ViewAnnotationAnchor
import com.mapbox.maps.plugin.viewannotation.ViewAnnotationOptions
import com.mapbox.maps.plugin.viewannotation.ViewAnnotationPositionDescriptor
import com.mapbox.maps.plugin.viewannotation.ViewAnnotationsPositionCallback

class ViewAnnotationCore(
  private val mapFeatureQueryDelegate: MapFeatureQueryDelegate,
  private val mapCameraManagerDelegate: MapCameraManagerDelegate,
  private val mapTransformDelegate: MapTransformDelegate,
) {

  private val annotations = LinkedHashMap<String, ViewAnnotationOptions>()
  private val updateList = mutableListOf<ViewAnnotationPositionDescriptor>()

  fun addViewAnnotation(viewId: String, options: ViewAnnotationOptions) {
    annotations[viewId] = options
  }

  fun updateViewAnnotation(viewId: String, options: ViewAnnotationOptions) {
    annotations[viewId]?.let {
      annotations[viewId] = options
    }
  }

  fun removeViewAnnotation(viewId: String) {
    annotations.remove(viewId)
  }

  fun calculateViewAnnotationsPosition(callback: ViewAnnotationsPositionCallback) {
    updateList.clear()
    for (annotation in annotations) {
      calculateSimpleSingePosition(annotation.key, annotation.value)?.let {
        updateList.add(it)
      }
    }
    callback.run(updateList)
  }

  private fun calculateSimpleSingePosition(
    id: String,
    options: ViewAnnotationOptions
  ): ViewAnnotationPositionDescriptor? {
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
    // calculate if result coordinates belong to viewport
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
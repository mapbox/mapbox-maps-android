package com.mapbox.maps.plugin.viewannotation

import android.view.View
import androidx.annotation.LayoutRes
import com.mapbox.geojson.Geometry
import com.mapbox.maps.ViewAnnotationOptions
import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.delegates.listeners.OnCameraChangeListener

interface ViewAnnotationPlugin: MapPlugin, OnCameraChangeListener {

  fun addViewAnnotation(
    @LayoutRes id: Int,
    type: ViewAnnotationType,
    options: ViewAnnotationOptions
  ): View

  fun addViewAnnotation(
    @LayoutRes id: Int,
    type: ViewAnnotationType,
    options: ViewAnnotationOptions,
    result: (View) -> Unit
  )

  fun removeViewAnnotation(view: View)

  // TODO revisit better public facing API here
  fun updateViewAnnotation(
    view: View,
    options: ViewAnnotationOptions
  )
}
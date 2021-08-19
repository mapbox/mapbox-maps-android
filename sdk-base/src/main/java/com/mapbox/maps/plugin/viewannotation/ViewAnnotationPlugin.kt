package com.mapbox.maps.plugin.viewannotation

import android.view.View
import androidx.annotation.LayoutRes
import com.mapbox.geojson.Geometry
import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.delegates.listeners.OnCameraChangeListener

interface ViewAnnotationPlugin: MapPlugin, OnCameraChangeListener {

  fun addViewAnnotation(
    @LayoutRes id: Int,
    type: ViewAnnotationType,
    geometry: Geometry,
    options: ViewAnnotationOptions
  ): View

  fun addViewAnnotation(
    @LayoutRes id: Int,
    type: ViewAnnotationType,
    geometry: Geometry,
    options: ViewAnnotationOptions,
    result: (View) -> Unit
  )

  fun removeViewAnnotation(view: View)

  fun updateViewAnnotation(
    view: View,
    options: ViewAnnotationOptions,
    geometry: Geometry?
  )
}
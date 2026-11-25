package com.mapbox.maps.extension.androidauto

import android.app.Presentation
import android.content.Context
import android.view.Display
import android.view.Surface
import androidx.car.app.SurfaceContainer
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapSurface
import com.mapbox.maps.MapView

/**
 * This provider is needed for creating unit tests.
 */
internal object MapSurfaceProvider {
  fun create(
    context: Context,
    surface: Surface,
    mapInitOptions: MapInitOptions
  ) = MapSurface(context, surface, mapInitOptions)

  fun createMapViewDisplay(
    context: Context,
    surfaceContainer: SurfaceContainer,
    mapInitOptions: MapInitOptions
  ) = CarMapViewDisplay.create(context, surfaceContainer, mapInitOptions)

  fun createFromMapView(mapView: MapView): MapSurface = MapSurface(mapView)

  fun createPresentation(context: Context, display: Display) = Presentation(context, display)

  fun createMapView(context: Context, mapInitOptions: MapInitOptions) = MapView(context, mapInitOptions)
}
package com.mapbox.maps.testapp.auto.car

import androidx.car.app.CarContext
import com.mapbox.bindgen.Value
import com.mapbox.maps.Style
import com.mapbox.maps.extension.androidauto.MapboxCarMapObserver
import com.mapbox.maps.extension.androidauto.MapboxCarMapSurface
import com.mapbox.maps.logE

/**
 * Example showing how you can add a sky layer that has a sun direction,
 * and adding a terrain layer to show mountains.
 */
class CarMapShowcase : MapboxCarMapObserver {

  private var mapboxCarMapSurface: MapboxCarMapSurface? = null

  override fun onAttached(mapboxCarMapSurface: MapboxCarMapSurface) {
    this.mapboxCarMapSurface = mapboxCarMapSurface
    loadMapStyle(mapboxCarMapSurface.carContext)
  }

  override fun onDetached(mapboxCarMapSurface: MapboxCarMapSurface) {
    this.mapboxCarMapSurface = null
  }

  fun loadMapStyle(carContext: CarContext) {
    val lightPreset = if (carContext.isDarkMode) LightPresets.NIGHT else LightPresets.DAY

    mapboxCarMapSurface?.mapSurface?.mapboxMap?.loadStyle(Style.STANDARD) { style ->
      style.setStyleImportConfigProperty(
        "basemap",
        "lightPreset",
        Value.valueOf(lightPreset.toString().lowercase())
      ).error?.let {
        logE(TAG, it)
      }
    }
  }

  private enum class LightPresets {
    DAY,
    DAWN,
    DUSK,
    NIGHT
  }

  companion object {
    private const val TAG = "CarMapShowcase"
  }
}
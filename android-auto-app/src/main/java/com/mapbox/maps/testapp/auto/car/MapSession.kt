package com.mapbox.maps.testapp.auto.car

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.content.res.Configuration
import androidx.car.app.*
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapSurface
import com.mapbox.maps.Style
import com.mapbox.maps.extension.androidauto.CompassWidget
import com.mapbox.maps.extension.androidauto.LogoWidget
import com.mapbox.maps.extension.androidauto.initMapSurface
import com.mapbox.maps.extension.style.layers.generated.skyLayer
import com.mapbox.maps.extension.style.layers.properties.generated.SkyType
import com.mapbox.maps.extension.style.sources.generated.rasterDemSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.extension.style.terrain.generated.terrain
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.testapp.auto.R

/**
 * Session class for the Mapbox Map sample app for Android Auto.
 */
class MapSession : Session() {
  private lateinit var mapSurface: MapSurface
  private val carCameraController = CarCameraController()

  override fun onCreateScreen(intent: Intent): Screen {
    val mapScreen = MapScreen(carContext)
    initMapSurface(
      scrollListener = carCameraController,
    ) { surface ->
      val logo = LogoWidget(carContext)
      val compass = CompassWidget(
        carContext,
        marginX = 26f,
        marginY = 120f,
      )

      mapSurface = surface
      carCameraController.init(
        mapSurface,
        EdgeInsets(
          carContext.resources.getDimensionPixelSize(R.dimen.map_padding).toDouble(),
          0.0,
          0.0,
          0.0
        )
      )
      mapScreen.setMapCameraController(carCameraController)
      loadStyle(surface)
      initLocationComponent(surface)

      surface.addWidget(logo)
      surface.addWidget(compass)

      surface.getMapboxMap().apply {
        addOnCameraChangeListener {
          compass.setRotation(this.cameraState.bearing.toFloat())
        }
      }
    }
    return if (carContext.checkSelfPermission(ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {
      carContext.getCarService(ScreenManager::class.java).push(mapScreen)
      RequestPermissionScreen(carContext)
    } else mapScreen
  }

  private fun loadStyle(surface: MapSurface) {
    surface.getMapboxMap().loadStyle(
      style(if (carContext.isDarkMode) Style.TRAFFIC_NIGHT else Style.TRAFFIC_DAY) {
        +rasterDemSource(SOURCE) {
          url(TERRAIN_URL_TILE_RESOURCE)
          // 514 specifies padded DEM tile and provides better performance than 512 tiles.
          tileSize(514)
        }
        +terrain(SOURCE)
        +skyLayer(SKY_LAYER) {
          skyType(SkyType.ATMOSPHERE)
          skyAtmosphereSun(listOf(-50.0, 90.2))
        }
      }
    )
  }

  private fun initLocationComponent(surface: MapSurface) {
    // Enable location component plugin with 3D location puck.
    surface.location.apply {
      // Show a 3D location puck
      locationPuck = CarLocationPuck.duckLocationPuckLowZoom
      enabled = true
      addOnIndicatorPositionChangedListener(carCameraController)
      addOnIndicatorBearingChangedListener(carCameraController)
    }
  }

  override fun onCarConfigurationChanged(newConfiguration: Configuration) {
    super.onCarConfigurationChanged(newConfiguration)
    loadStyle(mapSurface)
  }

  companion object {
    private const val SOURCE = "TERRAIN_SOURCE"
    private const val SKY_LAYER = "sky"
    private const val TERRAIN_URL_TILE_RESOURCE = "mapbox://mapbox.mapbox-terrain-dem-v1"
  }
}
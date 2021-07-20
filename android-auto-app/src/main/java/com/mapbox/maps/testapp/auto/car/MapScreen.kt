package com.mapbox.maps.testapp.auto.car

import androidx.car.app.CarContext
import androidx.car.app.CarToast
import androidx.car.app.Screen
import androidx.car.app.model.*
import androidx.car.app.navigation.model.NavigationTemplate
import androidx.core.graphics.drawable.IconCompat
import com.mapbox.maps.testapp.auto.R
import java.lang.ref.WeakReference

/**
 * Simple demo of how to show a Mapbox Map on the Android Auto screen.
 */
class MapScreen(carContext: CarContext) : Screen(carContext) {
  private var isInPanMode: Boolean = false
  private lateinit var carCameraController: WeakReference<CarCameraController>

  /**
   * Set the map camera controller, so that the UI elements(such as action button) in the template
   * can interact with the camera.
   */
  fun setMapCameraController(controller: CarCameraController) {
    carCameraController = WeakReference(controller)
  }

  override fun onGetTemplate(): Template {
    val builder = NavigationTemplate.Builder()
    builder.setBackgroundColor(CarColor.SECONDARY)

    builder.setActionStrip(
      ActionStrip.Builder()
        .addAction(
          Action.Builder()
            .setIcon(
              CarIcon.Builder(
                IconCompat.createWithResource(
                  carContext,
                  android.R.drawable.ic_menu_mylocation
                )
              ).build()
            )
            .setOnClickListener {
              carCameraController.get()?.focusOnLocationPuck()
            }
            .build()
        )
        .build()
    )
    // Set the map action strip with the pan and zoom buttons.
    val panIconBuilder = CarIcon.Builder(
      IconCompat.createWithResource(
        carContext,
        R.drawable.ic_pan_24
      )
    )
    if (isInPanMode) {
      panIconBuilder.setTint(CarColor.BLUE)
    }
    builder.setMapActionStrip(
      ActionStrip.Builder()
        .addAction(
          Action.Builder(Action.PAN)
            .setIcon(panIconBuilder.build())
            .build()
        )
        .addAction(
          Action.Builder()
            .setIcon(
              CarIcon.Builder(
                IconCompat.createWithResource(
                  carContext,
                  R.drawable.ic_zoom_out_24
                )
              ).build()
            )
            .setOnClickListener {
              // handle zoom out
              carCameraController.get()?.zoomBy(0.95)
            }
            .build()
        )
        .addAction(
          Action.Builder()
            .setIcon(
              CarIcon.Builder(
                IconCompat.createWithResource(
                  carContext,
                  R.drawable.ic_zoom_in_24
                )
              ).build()
            )
            .setOnClickListener {
              carCameraController.get()?.zoomBy(1.05)
            }
            .build()
        )
        .build()
    )

    // When the user enters the pan mode, remind the user that they can exit the pan mode by
    // pressing the select button again.
    builder.setPanModeListener { isInPanMode: Boolean ->
      if (isInPanMode) {
        CarToast.makeText(
          carContext,
          "Press Select to exit the pan mode",
          CarToast.LENGTH_LONG
        ).show()
      }
      this.isInPanMode = isInPanMode
      invalidate()
    }

    return builder.build()
  }
}
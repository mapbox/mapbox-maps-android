package com.mapbox.maps.testapp.auto.car

import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.ScreenManager
import androidx.car.app.model.Action
import androidx.car.app.model.CarColor
import androidx.car.app.model.MessageTemplate
import androidx.car.app.model.ParkedOnlyOnClickListener
import androidx.car.app.model.Template

/* Retry screen if the map failed to load. */
class RetryScreen(
  carContext: CarContext,
  private val session: MapSession,
  private val carMapShowcase: CarMapShowcase,
  private val message: String
) : Screen(carContext) {

  override fun onGetTemplate(): Template {
    val listener = ParkedOnlyOnClickListener.create {
      val screen = session.tryInit(carMapShowcase)
        ?: RetryScreen(carContext, session, carMapShowcase, "Map not yet loaded...")
      carContext.getCarService(ScreenManager::class.java)
        .push(screen)
      finish()
    }
    val action = Action.Builder()
      .setTitle("Retry loading the map")
      .setBackgroundColor(CarColor.GREEN)
      .setOnClickListener(listener)
      .build()
    return MessageTemplate.Builder(message)
      .addAction(action)
      .setHeaderAction(Action.BACK)
      .build()
  }
}
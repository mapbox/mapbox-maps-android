package com.mapbox.maps.testapp.auto.car

import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.SurfaceContainer
import androidx.car.app.model.Action
import androidx.car.app.model.SearchTemplate
import androidx.car.app.model.Template
import com.mapbox.maps.extension.androidauto.MapboxCarMap
import com.mapbox.maps.extension.androidauto.MapboxCarMapObserver

/**
 * This screen is to demonstrate a behavior where the map surface is destroyed and re-created.
 *
 * Android Auto can transition between [Template]s that do not include a [SurfaceContainer] for
 * rendering the map. The [SearchTemplate] is one example template to reproduce this behavior.
 * The [MapboxCarMap] does not need to be re-created, but when the surface is destroyed the
 * [MapboxCarMap] will detach all registered [MapboxCarMapObserver]s. The observers will be
 * attached after a new surface is created.
 */
class SearchScreen(carContext: CarContext) : Screen(carContext) {

  override fun onGetTemplate(): Template {
    val searchCallback = object : SearchTemplate.SearchCallback {
      override fun onSearchTextChanged(searchText: String) {}

      override fun onSearchSubmitted(searchText: String) {}
    }
    return SearchTemplate.Builder(searchCallback)
      .setHeaderAction(Action.BACK)
      .build()
  }
}
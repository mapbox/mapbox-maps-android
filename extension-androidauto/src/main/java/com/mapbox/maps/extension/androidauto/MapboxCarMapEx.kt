@file:kotlin.jvm.JvmName("MapboxCarMapEx")

package com.mapbox.maps.extension.androidauto

import androidx.car.app.Screen
import androidx.car.app.Session
import com.mapbox.maps.MapboxExperimental

/**
 * Shorthand extension for installing the map onto a [Session].
 */
@MapboxExperimental
fun Session.mapboxMapInstaller() = MapboxCarMapSessionInstaller(this)

/**
 * Shorthand extension for installing an experience onto a [Screen].
 */
@MapboxExperimental
fun Screen.mapboxMapInstaller(mapboxCarMap: MapboxCarMap) =
  MapboxCarMapScreenInstaller(this, mapboxCarMap)
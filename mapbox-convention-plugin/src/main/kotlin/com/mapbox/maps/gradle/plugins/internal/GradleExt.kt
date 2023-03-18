package com.mapbox.maps.gradle.plugins.internal

import org.gradle.api.model.ObjectFactory

internal inline fun <reified T : Any> ObjectFactory.newInstance(vararg parameters: Any): T =
  newInstance(T::class.java, *parameters)
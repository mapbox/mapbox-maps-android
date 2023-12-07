package com.mapbox.maps.gradle.plugins.internal

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.getByType

internal inline fun <reified T : Any> ObjectFactory.newInstance(vararg parameters: Any): T =
  newInstance(T::class.java, *parameters)

internal fun Project.getVersionsCatalog(name: String = "libs"): VersionCatalog {
  return getVersionsCatalogOrNull(name) ?: error("No versions catalog found!")
}

internal fun Project.getVersionsCatalogOrNull(name: String = "libs"): VersionCatalog? {
  return try {
    project.extensions.getByType<VersionCatalogsExtension>().named(name)
  } catch (ignored: Exception) {
    null
  }
}
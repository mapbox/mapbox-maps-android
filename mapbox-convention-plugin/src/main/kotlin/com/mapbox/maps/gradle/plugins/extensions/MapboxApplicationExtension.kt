package com.mapbox.maps.gradle.plugins.extensions

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project

public abstract class MapboxApplicationExtension : MapboxAndroidCommonExtension() {
  internal fun applyTo(project: Project) {
    project.applyRequiredPlugins()
    val applicationExtension = project.extensions.getByName("android") as ApplicationExtension
    applicationExtension.configureFlavors(project)
  }

  private fun Project.applyRequiredPlugins() {
    plugins.apply("com.android.application")
    plugins.apply("kotlin-android")
    plugins.apply("io.gitlab.arturbosch.detekt")
  }

  internal companion object {
    private const val NAME = "mapboxApplication"

    internal fun Project.mapboxApplicationExtension(): MapboxApplicationExtension =
      extensions.create(NAME, MapboxApplicationExtension::class.java)
  }
}
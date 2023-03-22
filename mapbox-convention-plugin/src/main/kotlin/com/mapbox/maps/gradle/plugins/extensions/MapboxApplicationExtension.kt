package com.mapbox.maps.gradle.plugins.extensions

import com.android.build.api.dsl.ApplicationExtension
import com.mapbox.AccessTokenExtension
import org.gradle.api.Project

public abstract class MapboxApplicationExtension : MapboxAndroidCommonExtension() {
  internal fun applyTo(project: Project) {
    project.applyRequiredPlugins()
    val applicationExtension = project.extensions.getByName("android") as ApplicationExtension
    applicationExtension.configureFlavors(project)

    project.configureAccessTokenExtension()
  }

  private fun Project.configureAccessTokenExtension() {
    val accessTokenExtension = extensions.getByType(AccessTokenExtension::class.java)
    accessTokenExtension.file = file("src/main/res/values/developer-config.xml").canonicalPath
  }

  private fun Project.applyRequiredPlugins() {
    plugins.apply("com.android.application")
    plugins.apply("kotlin-android")
    plugins.apply("io.gitlab.arturbosch.detekt")
    plugins.apply("com.mapbox.maps.token")
  }

  internal companion object {
    private const val NAME = "mapboxApplication"

    internal fun Project.mapboxApplicationExtension(): MapboxApplicationExtension =
      extensions.create(NAME, MapboxApplicationExtension::class.java)
  }
}
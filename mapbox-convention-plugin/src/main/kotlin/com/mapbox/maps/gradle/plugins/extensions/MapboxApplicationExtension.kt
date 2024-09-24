package com.mapbox.maps.gradle.plugins.extensions

import com.mapbox.AccessTokenExtension
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

public abstract class MapboxApplicationExtension @Inject constructor(objects: ObjectFactory) :
  MapboxDependenciesExtension(objects) {

  override fun applyTo(project: Project) {
    super.applyTo(project)
    project.applyRequiredPlugins()
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
package com.mapbox.maps.gradle.plugins

import com.mapbox.maps.gradle.plugins.extensions.MapboxApplicationExtension.Companion.mapboxApplicationExtension
import com.mapbox.maps.gradle.plugins.extensions.MapboxLibraryExtension.Companion.mapboxLibraryExtension
import com.mapbox.maps.gradle.plugins.extensions.MapboxRootExtension.Companion.mapboxRootExtension
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
internal class MapboxLibraryPlugin : Plugin<Project> {
  override fun apply(project: Project) {
    project.mapboxLibraryExtension().applyTo(project)
  }
}

@Suppress("unused")
internal class MapboxApplicationPlugin : Plugin<Project> {
  override fun apply(project: Project) {
    project.mapboxApplicationExtension().applyTo(project)
  }
}

@Suppress("unused")
internal class MapboxRootPlugin : Plugin<Project> {
  override fun apply(project: Project) {
    if (project != project.rootProject) {
      throw GradleException("Mapbox root plugin can only be applied to root project")
    }
    project.mapboxRootExtension().applyTo(project)
  }
}

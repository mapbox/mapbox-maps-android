package com.mapbox.maps.gradle.plugins

import com.mapbox.maps.gradle.plugins.extensions.MapboxApplicationExtension.Companion.mapboxApplicationExtension
import com.mapbox.maps.gradle.plugins.extensions.MapboxLibraryExtension.Companion.mapboxLibraryExtension
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

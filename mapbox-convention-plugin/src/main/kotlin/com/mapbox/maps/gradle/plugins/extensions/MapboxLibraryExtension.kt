package com.mapbox.maps.gradle.plugins.extensions

import com.android.build.gradle.LibraryExtension
import com.mapbox.maps.gradle.plugins.internal.newInstance
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

public abstract class MapboxLibraryExtension @Inject constructor(objects: ObjectFactory) {
  private val dokka = objects.newInstance<MapboxDokkaExtension>()

  /** Configure the inner DSL object, [MapboxDokkaExtension]. */
  public fun dokka(action: Action<MapboxDokkaExtension>) {
    action.execute(dokka)
  }

  internal fun applyTo(project: Project) {
    project.applyRequiredPlugins()
    val libraryExtension = project.extensions.getByName("android") as LibraryExtension
    libraryExtension.configurePublicResource(project)
    dokka.applyTo(project, libraryExtension)
  }

  private fun Project.applyRequiredPlugins() {
    plugins.apply("com.android.library")
    plugins.apply("kotlin-android")
    plugins.apply("io.gitlab.arturbosch.detekt")
  }

  internal companion object {
    private const val NAME = "mapboxLibrary"

    internal fun Project.mapboxLibraryExtension(): MapboxLibraryExtension =
      extensions.create(NAME, MapboxLibraryExtension::class.java)
  }
}

private fun LibraryExtension.configurePublicResource(project: Project) {
  sourceSets.all {
    // limit amount of exposed library resources
    res.srcDir("${project.projectDir}/src/$name/res-public")
  }
}

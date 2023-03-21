package com.mapbox.maps.gradle.plugins.extensions

import com.mapbox.maps.gradle.plugins.internal.newInstance
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

public abstract class MapboxRootExtension @Inject constructor(objects: ObjectFactory) {
  private val dokka = objects.newInstance<MapboxDokkaExtension>()

  /** Configure the inner DSL object, [MapboxDokkaExtension]. */
  public fun dokka(action: Action<MapboxDokkaExtension>) {
    action.execute(dokka)
  }

  internal fun applyTo(project: Project) {
    dokka.applyToRootProject(project)
  }

  internal companion object {
    private const val NAME = "mapboxRoot"

    internal fun Project.mapboxRootExtension(): MapboxRootExtension =
      extensions.create(NAME, MapboxRootExtension::class.java)
  }
}
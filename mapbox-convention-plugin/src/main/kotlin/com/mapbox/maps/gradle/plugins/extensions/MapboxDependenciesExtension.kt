package com.mapbox.maps.gradle.plugins.extensions

import com.mapbox.maps.gradle.plugins.internal.MapboxDependencies
import com.mapbox.maps.gradle.plugins.internal.newInstance
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory

public open class MapboxDependenciesExtension(objects: ObjectFactory) {

  private val glNative = objects.newInstance<MapboxDependencyExtension>(MapboxDependencies.GlNative)
  private val common = objects.newInstance<MapboxDependencyExtension>(MapboxDependencies.Common)

  /** Configure the inner DSL object, [MapboxDependencyExtension]. */
  public fun glNative(action: Action<MapboxDependencyExtension>? = null) {
    glNative.enabled = true
    action?.execute(glNative)
  }

  /** Configure the inner DSL object, [MapboxDependencyExtension]. */
  public fun common(action: Action<MapboxDependencyExtension>? = null) {
    common.enabled = true
    action?.execute(common)
  }

  internal open fun applyTo(project: Project) {
    glNative.applyTo(project)
    common.applyTo(project)
  }
}

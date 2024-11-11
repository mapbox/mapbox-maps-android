package com.mapbox.maps.gradle.plugins.extensions

import com.mapbox.maps.gradle.plugins.internal.BaseDependency
import com.mapbox.maps.gradle.plugins.internal.setDisallowChanges
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.property
import javax.inject.Inject

public abstract class MapboxDependencyExtension
@Inject
internal constructor(
  objects: ObjectFactory,
  private val dependency: BaseDependency
) {
  private val enabledProperty: Property<Boolean> = objects.property<Boolean>().convention(false)
  private val configurationProperty: ListProperty<String> =
    objects.listProperty<String>().convention(listOf("implementation"))

  /**
   * True if this dependency should be added.
   */
  @Suppress("MemberVisibilityCanBePrivate")
  internal var enabled: Boolean
    get() = enabledProperty.get()
    set(value) {
      enabledProperty.setDisallowChanges(value)
    }

  /**
   * The configuration name to use when defining the dependency for the project.
   *
   * See [configurations] to add multiple configurations.
   */
  @Suppress("MemberVisibilityCanBePrivate")
  public var configuration: String
    get() = configurationProperty.get().first()
    set(value) {
      configurationProperty.setDisallowChanges(listOf(value))
    }

  /**
   * The configuration names to use when defining the dependency for the project.
   */
  @Suppress("MemberVisibilityCanBePrivate")
  public var configurations: List<String>
    get() = configurationProperty.get()
    set(value) {
      configurationProperty.setDisallowChanges(value)
    }

  internal fun applyTo(project: Project) = project.afterEvaluate {
    if (enabled) {
      this@MapboxDependencyExtension.configurations.forEach { configuration ->
        dependency.add(this, configuration)
      }
    }
  }
}

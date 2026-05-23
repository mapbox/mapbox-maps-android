package com.mapbox.maps.gradle.plugins.internal

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.getByType

internal inline fun <reified T : Any> ObjectFactory.newInstance(vararg parameters: Any): T =
  newInstance(T::class.java, *parameters)

internal fun Project.getVersionCatalog(name: String = "libs"): VersionCatalog {
  return getVersionCatalogOrNull(name) ?: error("No versions catalog found!")
}

internal fun Project.getVersionCatalogOrNull(name: String = "libs"): VersionCatalog? = try {
  project.extensions.getByType<VersionCatalogsExtension>().named(name)
} catch (ignored: Exception) {
  null
}

internal fun <T> Property<T>.setDisallowChanges(value: T?) {
  set(value)
  disallowChanges()
}

internal fun <T> ListProperty<T>.setDisallowChanges(value: List<T>?) {
  set(value)
  disallowChanges()
}

/**
 * Checks if the `ndkMajor` property is present in the project and appends it to the given [value].
 */
internal fun Project.appendNdkIfNeeded(value: String): String {
  val ndkMajor: String? = project.findProperty("ndkMajor")?.toString()
  return if (ndkMajor != null && ndkMajor != findDefaultNdkMajor()) {
    "$value-ndk$ndkMajor"
  } else {
    value
  }
}

/**
 * Switches the gl-native artifact resolution from the OpenGL build (`android-core`) to the
 * Vulkan build (`android-core-vulkan`) when the `vulkanEnabled` gradle property is `true`.
 * Must be applied before [appendNdkIfNeeded] to produce the published ordering
 * `android-core-vulkan-ndk27`.
 */
internal fun Project.appendVulkanIfNeeded(value: String): String {
  return if (project.isVulkanEnabled()) "$value-vulkan" else value
}

internal fun Project.isVulkanEnabled(): Boolean =
  project.findProperty("vulkanEnabled")?.toString()?.toBoolean() == true

/**
 * @return the `defaultNdkMajor` version defined `projects/common/platform/android/gradle/libs.versions.toml`.
 */
private fun Project.findDefaultNdkMajor(): String =
  getVersionCatalog().findVersion("defaultNdkMajor").get().toString()

private fun Project.getVersionCatalog() =
  extensions.getByType(VersionCatalogsExtension::class.java).named("commonLibs")
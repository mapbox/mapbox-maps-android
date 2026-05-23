package com.mapbox.maps.gradle.plugins.internal

import org.gradle.api.Project

/**
 * @param group The dependency group ID
 * @param artifact The dependency artifact ID
 * @param supportsNdkVariant True if this dependency also publishes variants for different NDKs.
 * The NDK suffix is appended to the artifact ID.
 * @param supportsVulkanVariant True if this dependency also publishes a Vulkan renderer variant.
 * The `-vulkan` suffix is appended to the artifact ID before the NDK suffix when the
 * `vulkanEnabled` gradle property is `true`.
 */
internal abstract class BaseDependency(
  open val group: String,
  open val artifact: String,
  open val supportsNdkVariant: Boolean = false,
  open val supportsVulkanVariant: Boolean = false,
) {
  abstract fun resolveVersion(project: Project): String

  fun add(project: Project, configuration: String) {
    val version = resolveVersion(project)
    val artifactId = artifact
      .let { if (supportsVulkanVariant) project.appendVulkanIfNeeded(it) else it }
      .let { if (supportsNdkVariant) project.appendNdkIfNeeded(it) else it }
    val coordinates = "${group}:$artifactId:$version"
    project.dependencies.add(configuration, coordinates)
  }
}

internal data class Dependency(
  override val group: String,
  override val artifact: String,
  private val version: String,
  override val supportsNdkVariant: Boolean = false,
  override val supportsVulkanVariant: Boolean = false,
) : BaseDependency(group, artifact, supportsNdkVariant, supportsVulkanVariant) {
  override fun resolveVersion(project: Project): String = version
}

/**
 * @param versionRef Version reference. Declaration must be presented in `[versions]`.
 * @param catalogName The library version catalog name from where to fetch the version.
 */
internal data class VersionCatalogDependency(
  override val group: String,
  override val artifact: String,
  private val versionRef: String,
  private val catalogName: String = "libs",
  override val supportsNdkVariant: Boolean = false,
  override val supportsVulkanVariant: Boolean = false,
) : BaseDependency(group, artifact, supportsNdkVariant, supportsVulkanVariant) {

  override fun resolveVersion(project: Project): String =
    project.getVersionCatalog(catalogName).findVersion(versionRef).get().toString()
}

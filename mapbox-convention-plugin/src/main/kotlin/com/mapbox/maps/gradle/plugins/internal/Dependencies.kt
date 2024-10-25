package com.mapbox.maps.gradle.plugins.internal

import org.gradle.api.Project

/**
 * @param group The dependency group ID
 * @param artifact The dependency artifact ID
 * @param snapshotArtifact The dependency artifact ID to use if the version contains `-SNAPSHOT`
 * @param supportsNdkVariant True if this dependency also publishes variants for different NDKs.
 * The NDK will be suffixed to the artifact ID.
 */
internal abstract class BaseDependency(
  open val group: String,
  open val artifact: String,
  open val snapshotArtifact: String = artifact,
  open val supportsNdkVariant: Boolean = false,
) {
  abstract fun resolveVersion(project: Project): String

  fun add(project: Project, configuration: String) {
    val version = resolveVersion(project)
    var artifactId = if (version.contains("-SNAPSHOT")) {
      snapshotArtifact
    } else {
      artifact
    }
    if (supportsNdkVariant) {
      artifactId = project.appendNdkIfNeeded(artifactId)
    }
    val coordinates = "${group}:$artifactId:$version"
    project.dependencies.add(configuration, coordinates)
  }
}

internal data class Dependency(
  override val group: String,
  override val artifact: String,
  private val version: String,
  override val snapshotArtifact: String = artifact,
  override val supportsNdkVariant: Boolean = false,
) : BaseDependency(group, artifact, snapshotArtifact, supportsNdkVariant) {
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
  override val snapshotArtifact: String = artifact,
  override val supportsNdkVariant: Boolean = false,
) : BaseDependency(group, artifact, snapshotArtifact, supportsNdkVariant) {

  override fun resolveVersion(project: Project): String =
    project.getVersionCatalog(catalogName).findVersion(versionRef).get().toString()
}

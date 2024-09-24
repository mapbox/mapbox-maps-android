package com.mapbox.maps.gradle.plugins.internal

import org.gradle.api.Project

/**
 * @param group The dependency group ID
 * @param artifact The dependency artifact ID
 * @param snapshotArtifact The dependency artifact ID to use if the version contains `-SNAPSHOT`
 * @param sourceProject The Gradle project module name if this [Dependency] can also be built from
 * source within this Gradle project.
 * @param supportsNdkVariant True if this dependency also publishes variants for different NDKs.
 * The NDK will be suffixed to the artifact ID.
 */
internal abstract class BaseDependency(
  open val group: String,
  open val artifact: String,
  open val snapshotArtifact: String = artifact,
  open val sourceProject: String? = null,
  open val supportsNdkVariant: Boolean = false,
) {
  abstract fun resolveVersion(project: Project): String

  fun add(project: Project, configuration: String, buildFromSource: Boolean) {
    if (buildFromSource) {
      project.dependencies.add(configuration, project.project(sourceProject!!))
    } else {
      val version = resolveVersion(project)
      var artifactId = if (version.contains("-SNAPSHOT")) {
        snapshotArtifact
      } else {
        artifact
      }
      val ndkMajor: String? = project.findProperty("ndkMajor")?.toString()
      if (supportsNdkVariant && ndkMajor != null && project.findDefaultNdkMajor() != ndkMajor) {
        artifactId += "-ndk$ndkMajor"
      }
      val coordinates = "${group}:$artifactId:$version"
      project.dependencies.add(configuration, coordinates)
    }
  }

  /**
   * @return the `defaultNdkMajor` version defined `projects/common/platform/android/gradle/libs.versions.toml`.
   */
  private fun Project.findDefaultNdkMajor(): String? =
    getVersionCatalog("commonLibs").findVersion("defaultNdkMajor").orElse(null)?.toString()

}

internal data class Dependency(
  override val group: String,
  override val artifact: String,
  private val version: String,
  override val sourceProject: String? = null,
  override val snapshotArtifact: String = artifact,
  override val supportsNdkVariant: Boolean = false,
) : BaseDependency(group, artifact, snapshotArtifact, sourceProject, supportsNdkVariant) {
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
  override val sourceProject: String? = null,
  override val snapshotArtifact: String = artifact,
  override val supportsNdkVariant: Boolean = false,
) : BaseDependency(group, artifact, snapshotArtifact, sourceProject, supportsNdkVariant) {

  override fun resolveVersion(project: Project): String =
    project.getVersionCatalog(catalogName).findVersion(versionRef).get().toString()
}

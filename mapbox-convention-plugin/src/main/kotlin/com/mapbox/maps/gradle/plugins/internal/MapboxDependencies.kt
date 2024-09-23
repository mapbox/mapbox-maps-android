package com.mapbox.maps.gradle.plugins.internal

internal object MapboxDependencies {
  internal val Common = VersionCatalogDependency(
    group = "com.mapbox.common",
    artifact = "common",
    versionRef = "mapboxCommon",
    sourceProject = ":common",
    supportsNdkVariant = true
  )

  internal val GlNative = VersionCatalogDependency(
    group = "com.mapbox.maps",
    artifact = "android-core",
    versionRef = "mapboxGlNative",
    sourceProject = ":maps-core",
    snapshotArtifact = "android-core-internal",
    supportsNdkVariant = true
  )
}
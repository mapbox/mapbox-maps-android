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
    snapshotArtifact = "android-core", // use "android-core-internal" for snapshots older than TODO, ref: https://github.com/mapbox/mapbox-maps-android/pull/2499
    supportsNdkVariant = true
  )
}
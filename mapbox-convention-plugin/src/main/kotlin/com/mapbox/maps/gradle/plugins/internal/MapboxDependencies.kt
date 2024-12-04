package com.mapbox.maps.gradle.plugins.internal

internal object MapboxDependencies {
  internal val Common = VersionCatalogDependency(
    group = "com.mapbox.common",
    artifact = "common",
    versionRef = "mapboxCommon",
    supportsNdkVariant = true
  )

  internal val GlNative = VersionCatalogDependency(
    group = "com.mapbox.maps",
    // v11.9 snapshots or earlier should use `android-core-internal` as `artifact`.
    artifact = "android-core",
    versionRef = "mapboxGlNative",
    supportsNdkVariant = true
  )
}
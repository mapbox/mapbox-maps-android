// needed for Dokka 1.4
pluginManagement {
  plugins {
    id("com.google.devtools.ksp") version "1.5.21-1.0.0-beta05"
    kotlin("jvm") version "1.5.21" apply false
    kotlin("android") version "1.5.21" apply false
  }
  repositories {
    gradlePluginPortal()
    google()
    jcenter()
  }
}

include(":app", ":sdk", ":extension-style", ":plugin-lifecycle", ":plugin-overlay", ":extension-localization", ":plugin-compass", ":plugin-logo", ":plugin-scalebar", ":plugin-gestures", ":plugin-attribution", ":plugin-annotation", ":plugin-locationcomponent", ":plugin-animation", ":module-telemetry", ":sdk-base", ":extension-style-app", "extension-androidauto", "android-auto-app")
include(":test-processor")
rootProject.name="Mapbox Maps Android"
rootProject.buildFileName = "build.gradle.kts"
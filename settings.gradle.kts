// needed for Dokka 1.4
pluginManagement {
  repositories {
    gradlePluginPortal()
    jcenter()
  }
}

include(":app", ":sdk", ":extension-observable", ":extension-style", ":plugin-overlay", ":plugin-compass", ":plugin-logo", ":plugin-scalebar", ":plugin-gestures", ":plugin-attribution", ":plugin-annotation", ":plugin-location", ":plugin-locationcomponent", ":plugin-animation", ":module-telemetry", ":sdk-base", ":extension-style-app")
rootProject.name="Mapbox Maps Android"
rootProject.buildFileName = "build.gradle.kts"
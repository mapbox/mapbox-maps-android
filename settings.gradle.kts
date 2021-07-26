// needed for Dokka 1.4
pluginManagement {
  repositories {
    gradlePluginPortal()
    jcenter()
  }
}

include(":app", ":sdk", ":mapbox-lint", ":extension-style", ":plugin-lifecycle", ":plugin-overlay", ":extension-localization", ":plugin-compass", ":plugin-logo", ":plugin-scalebar", ":plugin-gestures", ":plugin-attribution", ":plugin-annotation", ":plugin-locationcomponent", ":plugin-animation", ":module-telemetry", ":sdk-base", ":extension-style-app", "extension-androidauto", "android-auto-app")
rootProject.name="Mapbox Maps Android"
rootProject.buildFileName = "build.gradle.kts"

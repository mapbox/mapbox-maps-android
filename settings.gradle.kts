// needed for Dokka 1.4
pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenCentral()
  }
  includeBuild("mapbox-convention-plugin")
}

include(
  ":android-auto-app",
  ":compose-app",
  ":app",
  ":extension-androidauto",
  ":extension-compose",
  ":extension-style",
  ":extension-style-lint-rules",
  ":extension-localization",
  ":extension-style-app",
  ":plugin-lifecycle-lint-rules",
  ":module-telemetry",
  ":plugin-animation",
  ":plugin-annotation",
  ":plugin-attribution",
  ":plugin-compass",
  ":plugin-gestures",
  ":plugin-lifecycle",
  ":plugin-locationcomponent",
  ":plugin-logo",
  ":plugin-overlay",
  ":plugin-scalebar",
  ":plugin-viewport",
  ":sdk",
  ":sdk-base"
)
rootProject.name = "Mapbox Maps Android"
rootProject.buildFileName = "build.gradle.kts"

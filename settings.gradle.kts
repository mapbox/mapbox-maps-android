fun isBuildFromSource(buildType: String): Boolean {
  val propertiesFile = file("local.properties")
  if (propertiesFile.exists()) {
    val properties = java.util.Properties()
    properties.load(propertiesFile.inputStream())
    return properties.getProperty(buildType)?.toBoolean() == true
  }

  return false
}

if (isBuildFromSource("buildFromSource")) {
  include(":maps-core")
  project(":maps-core").projectDir = File("./vendor/mapbox-gl-native-internal/internal/platform/android/sdk")
  if (isBuildFromSource("buildCommonFromSource")) {
    include(":common")
    project(":common").projectDir = File("./vendor/mapbox-gl-native-internal/internal/vendor/common/platform/android/common")
  }
}

// needed for Dokka 1.4
pluginManagement {
  repositories {
    gradlePluginPortal()
    jcenter()
  }
}

include(":app",":sdk", ":extension-observable", ":extension-style", ":plugin-overlay", ":plugin-compass",":plugin-logo",  ":plugin-scalebar", ":plugin-gestures", ":plugin-attribution", ":plugin-annotation",":plugin-location", ":plugin-animation", ":module-loader", ":module-telemetry", ":sdk-base", ":extension-style-app")
rootProject.name="Mapbox Maps Android"
rootProject.buildFileName = "build.gradle.kts"
pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
  }
  includeBuild("../mapbox-convention-plugin")
}

dependencyResolutionManagement {
  versionCatalogs {
    create("libs") {
      from(files("../gradle/libs.versions.toml"))
    }
    create("commonLibs") {
      from(files("../gradle/commonlibs.versions.toml"))
    }
    create("appsLibs") {
      from(files("gradle/apps.versions.toml"))
    }
  }
}

include(":extension-compose", ":compose-app")
rootProject.name = "Mapbox Maps Compose"
rootProject.buildFileName = "build.gradle.kts"

// `com.mapbox.maps:android` is always resolved as a Maven artifact (never as a composite
// build — mixing AGP versions across included builds is not allowed by AGP). To build
// against unpublished local maps-sdk changes, publish them to Maven Local first:
//   cd .. && ./gradlew publishToMavenLocal
// mavenLocal() is the first repository, so the locally published artifact wins.

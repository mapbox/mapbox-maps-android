buildscript {
  repositories {
    google()
    mavenCentral()
    maven {
      url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
      credentials {
        username = "mapbox"
        password = System.getenv("SDK_REGISTRY_TOKEN") ?: project.property("SDK_REGISTRY_TOKEN") as String
      }
      authentication {
        create<BasicAuthentication>("basic")
      }
    }
    maven {
      url = uri("https://plugins.gradle.org/m2/")
    }
    jcenter()
  }
  dependencies {
    classpath(Plugins.android)
    classpath(Plugins.kotlin)
    classpath(Plugins.jacoco)
    classpath(Plugins.license)
    classpath(Plugins.mapboxAccessToken)
    classpath(Plugins.mapboxSdkRegistry)
    classpath(Plugins.mapboxSdkVersionsPlugin)
  }
}

allprojects {
  repositories {
    google()
    mavenCentral()
    maven {
      url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
      credentials {
        username = "mapbox"
        password = System.getenv("SDK_REGISTRY_TOKEN") ?: project.property("SDK_REGISTRY_TOKEN") as String
      }
      authentication {
        create<BasicAuthentication>("basic")
      }
    }
    maven {
      url = uri("https://oss.jfrog.org/artifactory/oss-snapshot-local/")
    }
    jcenter()
  }
}

plugins {
  id(Plugins.dokkaId) version Versions.pluginDokka
  id(Plugins.binaryCompatibilityValidatorId) version Versions.pluginBinaryCompatibilityValidator
}
repositories {
  maven(url = "https://dl.bintray.com/kotlin/dokka")
}
tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
  outputDirectory.set(buildDir.resolve(this.name))
}
tasks.withType<Test> {
  maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
}

apiValidation {
  /**
   * Packages that are excluded from public API dumps even if they
   * contain public API.
   */
//  ignoredPackages.add("kotlinx.coroutines.internal")

  /**
   * Sub-projects that are excluded from API validation
   */
  ignoredProjects.addAll(listOf("extension-style-app", "android-auto-app", "app"))

  /**
   * Classes (fully qualified) that are excluded from public API dumps even if they
   * contain public API.
   */
  ignoredClasses.addAll(
    listOf(
      "com.mapbox.maps.extension.androidauto.BuildConfig",
      "com.mapbox.maps.extension.style.BuildConfig",
      "com.mapbox.maps.extension.localization.BuildConfig",
      "com.mapbox.maps.module.telemetry.BuildConfig",
      "com.mapbox.maps.plugin.camera.animation.BuildConfig",
      "com.mapbox.maps.plugin.annotation.BuildConfig",
      "com.mapbox.maps.plugin.attribution.BuildConfig",
      "com.mapbox.maps.plugin.compass.BuildConfig",
      "com.mapbox.maps.plugin.gestures.BuildConfig",
      "com.mapbox.maps.plugin.lifecycle.BuildConfig",
      "com.mapbox.maps.plugin.locationcomponent.BuildConfig",
      "com.mapbox.maps.plugin.logo.BuildConfig",
      "com.mapbox.maps.plugin.overlay.BuildConfig",
      "com.mapbox.maps.plugin.scalebar.BuildConfig",
      "com.mapbox.maps.plugin.viewport.BuildConfig",
      "com.mapbox.maps.BuildConfig",
      "com.mapbox.maps.base.BuildConfig"
    )
  )

  /**
   * Set of annotations that exclude API from being public.
   * Typically, it is all kinds of `@InternalApi` annotations that mark
   * effectively private API that cannot be actually private for technical reasons.
   */
//  nonPublicMarkers.add("com.mapbox.maps.MapboxExperimental")

  /**
   * Flag to programmatically disable compatibility validator
   */
  validationDisabled = false
}
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
    gradlePluginPortal()
  }
  dependencies {
    classpath(libs.plugin.gradle)
    classpath(libs.plugin.kotlin)
    classpath(libs.plugin.jacoco)
    classpath(libs.plugin.license)
    classpath(libs.plugin.mapbox.accessToken)
    classpath(libs.plugin.mapbox.sdkRegistry)
    classpath(libs.plugin.mapbox.sdkVersions)
    classpath(libs.plugin.pitest)
    classpath(libs.plugin.playPublisher)
    classpath(libs.plugin.gradleVersions)
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
    if (!isBuildingReleaseTag && !isTargettingReleaseBranch) {
      maven {
        url = uri("https://api.mapbox.com/downloads/v2/snapshots/maven")
        credentials {
          username = "mapbox"
          password = System.getenv("SDK_REGISTRY_TOKEN") ?: project.property("SDK_REGISTRY_TOKEN") as String
        }
        authentication {
          create<BasicAuthentication>("basic")
        }
      }
    }
    maven {
      url = uri("https://oss.jfrog.org/artifactory/oss-snapshot-local/")
    }
    maven {
      url = uri("https://jitpack.io")
    }
  }
}

plugins {
  id("com.mapbox.gradle.root")
  // the IDE mistakenly highlights `libs` as an error, see https://github.com/gradle/gradle/issues/22797
  alias(libs.plugins.detekt) apply false
  alias(libs.plugins.binaryCompatibilityValidatorId)
  // Used to print dependency tree of the task, useful to debug gradle tasks
  // Ticket to track adding this feature to gradle officially: https://github.com/gradle/gradle/issues/980
  alias(libs.plugins.taskTreeId)
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

/**
 * @return True if this build is part of Circleci job triggered from a release tag
 */
public val isBuildingReleaseTag = "^v[0-9]+\\.[0-9]+\\.[0-9]+.*\$".toRegex().matches(System.getenv("CIRCLE_TAG") ?: "")

/**
 * @return True if this build is part of Circleci job triggered from a PR that targets a release branch
 */
public val isTargettingReleaseBranch = "^v[0-9]+\\.[0-9]+\$".toRegex().matches(System.getenv("PR_TARGET_BRANCH") ?: "")


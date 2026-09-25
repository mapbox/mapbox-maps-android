buildscript {
  repositories {
    mavenLocal()
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
    classpath(libs.plugin.mapbox.ndk)
    classpath(libs.plugin.playPublisher)
    classpath(libs.plugin.gradleVersions)
  }
}

allprojects {
  repositories {
    mavenLocal()
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
      url = uri("https://api.mapbox.com/downloads/v2/snapshots/maven")
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
    maven {
      url = uri("https://jitpack.io")
    }
  }
}

// hack to fix unit test, see https://github.com/robolectric/robolectric/issues/5131#issuecomment-509631890.
subprojects {
  tasks.withType<Test> {
    maxParallelForks = 2
    forkEvery = 80
    maxHeapSize = "2048m"
    minHeapSize = "1024m"
  }
}

plugins {
  id("com.mapbox.gradle.root")
  // the IDE mistakenly highlights `libs` as an error, see https://github.com/gradle/gradle/issues/22797
  alias(libs.plugins.binaryCompatibilityValidatorId)
  // Used to print dependency tree of the task, useful to debug gradle tasks
  // Ticket to track adding this feature to gradle officially: https://github.com/gradle/gradle/issues/980
  alias(libs.plugins.taskTreeId)
}

apiValidation {
  /**
   * Sub-projects that are excluded from API validation
   */
  ignoredProjects.addAll(listOf("compose-app"))

  /**
   * Classes (fully qualified) that are excluded from public API dumps even if they
   * contain public API.
   */
  ignoredClasses.addAll(
    listOf(
      "com.mapbox.maps.extension.compose.BuildConfig"
    )
  )

  /**
   * Flag to programmatically disable compatibility validator
   */
  validationDisabled = false
}


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

  // Some plugins are only published in plugins.gradle.org (e.g. `japicmp` one)
  maven {
    url = uri("https://plugins.gradle.org/m2/")
  }
}

plugins {
  `kotlin-dsl`
}

gradlePlugin {
  plugins {
    register("MapboxRootPlugin") {
      id = "com.mapbox.gradle.root"
      implementationClass = "com.mapbox.maps.gradle.plugins.MapboxRootPlugin"
    }
    register("MapboxLibraryPlugin") {
      id = "com.mapbox.gradle.library"
      implementationClass = "com.mapbox.maps.gradle.plugins.MapboxLibraryPlugin"
    }
    register("MapboxApplicationPlugin") {
      id = "com.mapbox.gradle.application"
      implementationClass = "com.mapbox.maps.gradle.plugins.MapboxApplicationPlugin"
    }
  }
}

kotlin {
  explicitApi()
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
  kotlinOptions {
    kotlinOptions.allWarningsAsErrors = true
  }
}

dependencies {
  compileOnly(gradleApi())

  // compileOnly because we want to leave versioning to the consumers
  compileOnly(libs.plugin.gradle)
  implementation(libs.plugin.dokka)
  implementation(libs.plugin.japicmp)
  implementation(libs.plugin.jacoco)
  implementation(libs.plugin.mapbox.accessToken)
  implementation(libs.plugin.mapbox.sdkRegistry)
}
import org.gradle.kotlin.dsl.`kotlin-dsl`

repositories {
  google()
  mavenCentral()
}

plugins {
  `kotlin-dsl`
}

gradlePlugin {
  plugins {
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
  compileOnly(libs.gradle)
  compileOnly(libs.dokka)
}
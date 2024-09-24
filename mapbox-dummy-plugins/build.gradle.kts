repositories {
  google()
  mavenCentral()
}

plugins {
  `kotlin-dsl`
}

gradlePlugin {
  plugins {
    register("MapboxCommonNdkPlugin") {
      id = "com.mapbox.gradle.plugins.common.ndk"
      implementationClass = "com.mapbox.gradle.plugins.DummyCommonNdkPlugin"
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
}
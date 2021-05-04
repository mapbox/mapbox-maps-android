import org.jetbrains.dokka.gradle.DokkaTask

plugins {
  id("com.android.library")
  kotlin("android")
  kotlin("kapt")
  id("org.jetbrains.dokka")
}

val VERSION_NAME: String by project

android {
  compileSdkVersion(AndroidVersions.compileSdkVersion)
  defaultConfig {
    minSdkVersion(AndroidVersions.minSdkVersion)
    targetSdkVersion(AndroidVersions.targetSdkVersion)
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    buildConfigField("String", "MAPBOX_SDK_IDENTIFIER", String.format("\"%s\"", "mapbox-maps-android"))
    buildConfigField("String", "MAPBOX_SDK_VERSION", String.format("\"%s\"", VERSION_NAME))
    buildConfigField("String", "MAPBOX_VERSION_STRING", String.format("\"Mapbox/%s\"", VERSION_NAME))
    buildConfigField("String", "MAPBOX_EVENTS_USER_AGENT", String.format("\"mapbox-maps-android/%s\"", VERSION_NAME))
  }
}

dependencies {
  implementation(project(":sdk-base"))
  implementation(Dependencies.mapboxBase)
  implementation(Dependencies.mapboxAndroidTelemetry)
  compileOnly(Dependencies.mapboxAnnotations)
  kapt(Dependencies.mapboxAnnotationsProcessor)
  implementation(Dependencies.kotlin)
  testImplementation(Dependencies.junit)
  testImplementation(Dependencies.mockk)
  testImplementation(Dependencies.robolectric)
  androidTestImplementation(Dependencies.androidxTestRunner)
  androidTestImplementation(Dependencies.androidxJUnitTestRules)
  androidTestImplementation(Dependencies.androidxEspresso)
}

tasks.withType<DokkaTask>().configureEach {
  dokkaSourceSets {
    configureEach {
      reportUndocumented.set(true)
      // https://github.com/mapbox/mapbox-maps-android/issues/301#issuecomment-712736885
      failOnWarning.set(false)
    }
  }
}

project.apply {
  from("$rootDir/gradle/ktlint.gradle")
  from("$rootDir/gradle/lint.gradle")
  from("$rootDir/gradle/jacoco.gradle")
  from("$rootDir/gradle/sdk-registry.gradle")
  from("$rootDir/gradle/track-public-apis.gradle")
}
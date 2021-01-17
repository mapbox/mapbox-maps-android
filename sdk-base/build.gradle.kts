import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.dokka.gradle.DokkaTask

val buildCommonFromSource = gradleLocalProperties(rootDir).getProperty("buildCommonFromSource")?.toBoolean() == true
val buildMapsFromSource = gradleLocalProperties(rootDir).getProperty("buildFromSource")?.toBoolean() == true

plugins {
  id("com.android.library")
  kotlin("android")
  id("com.jaredsburrows.license")
  id("org.jetbrains.dokka")
  //id("com.mapbox.maps.token") #mapbox-android-gradle-plugins/issues/29
}

android {
  compileSdkVersion(AndroidVersions.compileSdkVersion)
  defaultConfig {
    minSdkVersion(AndroidVersions.minSdkVersion)
    targetSdkVersion(AndroidVersions.targetSdkVersion)
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
}

dependencies {
  implementation(Dependencies.kotlin)
  implementation(Dependencies.mapboxBase)
  implementation(Dependencies.androidxAnnotations)
  api(Dependencies.mapboxGestures)
  if (buildMapsFromSource) {
    api(project(":maps-core"))
    if (buildCommonFromSource) {
      api(project(":common"))
    } else {
      api(Dependencies.mapboxCoreCommon)
    }
  } else {
    api(Dependencies.mapboxGlNative)
    api(Dependencies.mapboxCoreCommon)
  }
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
}
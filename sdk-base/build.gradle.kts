import org.jetbrains.dokka.gradle.DokkaTask

project.apply(from = "../gradle/versions.gradle.kts")

plugins {
  id("com.android.library")
  kotlin("android")
  id("com.jaredsburrows.license")
  id("org.jetbrains.dokka")
  //id("com.mapbox.maps.token") #mapbox-android-gradle-plugins/issues/29
}

android {
  val androidSdkVersions = project.extra.get("androidSdkVersions") as HashMap<String, String>
  compileSdkVersion(androidSdkVersions["compileSdkVersion"]!!)
  defaultConfig {
    minSdkVersion(androidSdkVersions["minSdkVersion"])
    targetSdkVersion(androidSdkVersions["targetSdkVersion"])
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
}
val buildFromSource: String by project

dependencies {
  val dependencies = project.extra.get("dependencies") as HashMap<*, *>
  implementation(dependencies["kotlin"]!!)
  implementation(dependencies["mapboxBase"]!!)
  implementation(dependencies["androidxAnnotations"]!!)
  api(dependencies["mapboxGestures"]!!)
  if (buildFromSource.toBoolean()) {
    api(project(":maps-core"))
    api(project(":common"))
  } else {
    api(dependencies["mapboxGlNative"]!!)
    api(dependencies["mapboxCoreCommon"]!!)
  }

  testImplementation(dependencies["junit"]!!)
  testImplementation(dependencies["mockk"]!!)
  testImplementation(dependencies["androidxTestCore"]!!)
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
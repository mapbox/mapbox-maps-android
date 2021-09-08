import org.jetbrains.dokka.gradle.DokkaTask

project.apply(from = "../gradle/versions.gradle.kts")

plugins {
  id("com.android.library")
  kotlin("android")
  kotlin("kapt")
  id("org.jetbrains.dokka")
}

android {
  val androidSdkVersions = project.extra.get("androidSdkVersions") as HashMap<String, String>
  compileSdkVersion(androidSdkVersions["compileSdkVersion"]!!)
  defaultConfig {
    minSdkVersion(androidSdkVersions["minSdkVersion"])
    targetSdkVersion(androidSdkVersions["targetSdkVersion"])
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  testOptions {
    unitTests.apply {
      isIncludeAndroidResources = true
    }
  }
}

dependencies {
  val dependencies = project.extra.get("dependencies") as HashMap<*, *>
  implementation(project(":sdk-base"))
  implementation(dependencies["androidxInterpolators"]!!)
  implementation(dependencies["kotlin"]!!)
  implementation(dependencies["androidxCoreKtx"]!!)
  testImplementation(dependencies["junit"]!!)
  testImplementation(dependencies["mockk"]!!)
  testImplementation(dependencies["robolectric"]!!)
  androidTestImplementation(dependencies["androidxTestRunner"]!!)
  androidTestImplementation(dependencies["androidxJUnitTestRules"]!!)
  androidTestImplementation(dependencies["androidxEspresso"]!!)
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

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }

project.apply {
  from("$rootDir/gradle/ktlint.gradle")
  from("$rootDir/gradle/lint.gradle")
  from("$rootDir/gradle/jacoco.gradle")
  from("$rootDir/gradle/sdk-registry.gradle")
  from("$rootDir/gradle/track-public-apis.gradle")
}
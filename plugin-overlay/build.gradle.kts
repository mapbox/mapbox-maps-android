import org.jetbrains.dokka.gradle.DokkaTask

project.apply(from = "../gradle/versions.gradle.kts")

plugins {
  id("com.android.library")
  kotlin("android")
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
}

dependencies {
  val dependencies = project.extra.get("dependencies") as HashMap<*, *>
  implementation(project(":sdk-base"))
  implementation(dependencies["mapboxBase"]!!)
  implementation(dependencies["kotlin"]!!)
  implementation(dependencies["androidxAppCompat"]!!)
  implementation(dependencies["androidxCoreKtx"]!!)
  implementation(dependencies["androidxAnnotations"]!!)
  testImplementation(dependencies["junit"]!!)
  testImplementation(dependencies["mockk"]!!)
  testImplementation(dependencies["androidxTestCore"]!!)
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

project.apply {
  from("$rootDir/gradle/ktlint.gradle")
  from("$rootDir/gradle/lint.gradle")
  from("${rootDir}/gradle/jacoco.gradle")
  from("$rootDir/gradle/sdk-registry.gradle")
  from("$rootDir/gradle/track-public-apis.gradle")
}
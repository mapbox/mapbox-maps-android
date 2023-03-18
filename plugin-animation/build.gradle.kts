plugins {
  id("com.mapbox.gradle.library")
  kotlin("kapt")
}

android {
  compileSdk = AndroidVersions.compileSdkVersion
  defaultConfig {
    minSdk = AndroidVersions.minSdkVersion
    targetSdk = AndroidVersions.targetSdkVersion
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  testOptions {
    unitTests.apply {
      isIncludeAndroidResources = true
    }
  }
}

dependencies {
  implementation(project(":sdk-base"))
  implementation(Dependencies.androidxInterpolators)
  implementation(Dependencies.kotlin)
  implementation(Dependencies.androidxCoreKtx)
  testImplementation(Dependencies.junit)
  testImplementation(Dependencies.mockk)
  testImplementation(Dependencies.robolectric)
  androidTestImplementation(Dependencies.androidxTestRunner)
  androidTestImplementation(Dependencies.androidxJUnitTestRules)
  androidTestImplementation(Dependencies.androidxEspresso)
  detektPlugins(Dependencies.detektFormatting)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }

project.apply {
  from("$rootDir/gradle/ktlint.gradle")
  from("$rootDir/gradle/lint.gradle")
  from("$rootDir/gradle/jacoco.gradle")
  from("$rootDir/gradle/sdk-registry.gradle")
  from("$rootDir/gradle/track-public-apis.gradle")
  from("$rootDir/gradle/detekt.gradle")
  from("$rootDir/gradle/dependency-updates.gradle")
}
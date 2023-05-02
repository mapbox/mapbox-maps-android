plugins {
  id("com.mapbox.gradle.library")
  kotlin("kapt")
}

android {
  compileSdk = libs.versions.androidCompileSdkVersion.get().toInt()
  defaultConfig {
    minSdk = libs.versions.androidMinSdkVersion.get().toInt()
    targetSdk = libs.versions.androidTargetSdkVersion.get().toInt()
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
  implementation(libs.androidx.interpolators)
  implementation(libs.kotlin)
  implementation(libs.androidx.coreKtx)

  testImplementation(libs.bundles.base.dependenciesTests)
  androidTestImplementation(libs.bundles.base.dependenciesAndroidTests)
  detektPlugins(libs.detektFormatting)
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
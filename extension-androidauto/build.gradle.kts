plugins {
  id("com.mapbox.gradle.library")
}

android {
  compileSdk = libs.versions.autoCompileSdkVersion.get().toInt()
  defaultConfig {
    minSdk = libs.versions.autoMinSdkVersion.get().toInt()
    targetSdk = libs.versions.autoTargetSdkVersion.get().toInt()
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  testOptions {
    unitTests.apply {
      isIncludeAndroidResources = true
    }
  }
}

dependencies {
  api(libs.android.autoMapboxMapSdk)
  api(libs.googleCarAppLibrary)
  implementation(libs.kotlin)
  implementation(libs.androidx.coreKtx)
  implementation(libs.androidx.annotations)

  testImplementation(libs.bundles.base.dependenciesTests)
  testImplementation(libs.android.autoMapboxMapSdk)
  androidTestImplementation(libs.bundles.base.dependenciesAndroidTests)
  detektPlugins(libs.detektFormatting)
}

project.apply {
  from("$rootDir/gradle/ktlint.gradle")
  from("$rootDir/gradle/lint.gradle")
  from("$rootDir/gradle/jacoco.gradle")
  from("$rootDir/gradle/sdk-registry.gradle")
  from("$rootDir/gradle/track-public-apis.gradle")
  from("$rootDir/gradle/detekt.gradle")
  from("$rootDir/gradle/dependency-updates.gradle")
}
plugins {
  id("com.mapbox.gradle.library")
}

android {
  compileSdk = libs.versions.androidCompileSdkVersion.get().toInt()
  namespace = "com.mapbox.maps.plugin.gestures"
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
  kotlinOptions {
    jvmTarget = "1.8"
  }
}

mapboxLibrary {
  publish {
    group = "com.mapbox.plugin"
    artifactId = "maps-gestures"
    artifactTitle = "The gestures module for the Mapbox Maps SDK for Android"
    artifactDescription = artifactTitle
    sdkName = "mobile-maps-android-gestures"
  }
}

dependencies {
  api(libs.mapbox.gestures)
  implementation(project(":sdk-base"))
  implementation(libs.bundles.base.dependencies)

  testImplementation(libs.bundles.base.dependenciesTests)
  testImplementation(project(":plugin-animation"))
  androidTestImplementation(libs.bundles.base.dependenciesAndroidTests)
  detektPlugins(libs.detektFormatting)
}

project.apply {
  from("$rootDir/gradle/ktlint.gradle")
  from("$rootDir/gradle/lint.gradle")
  from("$rootDir/gradle/track-public-apis.gradle")
  from("$rootDir/gradle/detekt.gradle")
  from("$rootDir/gradle/dependency-updates.gradle")
}

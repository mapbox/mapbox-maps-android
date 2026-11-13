plugins {
  id("com.mapbox.gradle.library")
}

android {
  compileSdk = libs.versions.androidCompileSdkVersion.get().toInt()
  namespace = "com.mapbox.maps.plugin.consolidatedattribution"
  lint {
    targetSdk = libs.versions.androidTargetSdkVersion.get().toInt()
  }
  testOptions {
    targetSdk = libs.versions.androidTargetSdkVersion.get().toInt()
    unitTests.apply {
      isIncludeAndroidResources = true
    }
  }
  defaultConfig {
    minSdk = libs.versions.androidMinSdkVersion.get().toInt()
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
}

mapboxLibrary {
  publish {
    group = "com.mapbox.plugin"
    artifactId = "maps-consolidatedattribution"
    artifactTitle = "The consolidated attribution module for the Mapbox Maps SDK for Android"
    artifactDescription = artifactTitle
    sdkName = "mobile-maps-android-consolidatedattribution"
  }
}

dependencies {
  implementation(project(":sdk-base"))
  // For the shared attribution dialog shown when the pill is tapped.
  implementation(project(":plugin-attribution"))
  // To hide the logo while the pill stands in for it.
  implementation(project(":plugin-logo"))
  implementation(libs.bundles.base.dependencies)

  testImplementation(libs.bundles.base.dependenciesTests)
  androidTestImplementation(libs.bundles.base.dependenciesAndroidTests)
}

project.apply {
  from("$rootDir/gradle/ktlint.gradle.kts")
  from("$rootDir/gradle/lint.gradle")
  from("$rootDir/gradle/track-public-apis.gradle")
  from("$rootDir/gradle/dependency-updates.gradle")
}
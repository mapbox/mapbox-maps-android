plugins {
  id("com.mapbox.gradle.library")
}

android {
  compileSdk = libs.versions.androidCompileSdkVersion.get().toInt()
  namespace = "com.mapbox.maps.plugin.annotation"
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

mapboxLibrary {
  publish {
    group = "com.mapbox.plugin"
    artifactId = "maps-annotation"
    artifactTitle = "The annotation module for the Mapbox Maps SDK"
    artifactDescription = artifactTitle
    sdkName = "mobile-maps-android-annotation"
  }
}

dependencies {
  implementation(project(":sdk-base"))
  api(project(":extension-style"))
  implementation(libs.mapbox.annotations)
  implementation(libs.bundles.base.dependencies)
  testImplementation(project(":plugin-gestures"))

  testImplementation(libs.bundles.base.dependenciesTests)
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

plugins {
  id("com.mapbox.gradle.library")
}

android {
  compileSdk = libs.versions.androidCompileSdkVersion.get().toInt()
  namespace = "com.mapbox.maps.plugin.locationcomponent"
  defaultConfig {
    minSdk = libs.versions.androidMinSdkVersion.get().toInt()
    targetSdk = libs.versions.androidTargetSdkVersion.get().toInt()
    vectorDrawables.useSupportLibrary = true
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
}

mapboxLibrary {
  publish {
    group = "com.mapbox.plugin"
    artifactId = "maps-locationcomponent"
    artifactTitle = "The location component module for the Mapbox Maps SDK for Android"
    artifactDescription = artifactTitle
    sdkName = "mobile-maps-android-locationcomponent"
  }
}
dependencies {
  implementation(project(":sdk-base"))
  implementation(project(":extension-style"))
  implementation(libs.bundles.base.dependencies)
  implementation(libs.mapbox.javaGeoJSON)
  implementation(libs.coroutines)

  testImplementation(libs.bundles.base.dependenciesTests)
  testImplementation(libs.hamcrest)
  testImplementation(project(":plugin-gestures"))
  testImplementation(project(":plugin-animation"))
  testImplementation(libs.coroutinesTest)

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

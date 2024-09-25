plugins {
  id("com.mapbox.gradle.library")
  kotlin("kapt")
}

android {
  compileSdk = libs.versions.androidCompileSdkVersion.get().toInt()
  namespace = "com.mapbox.maps.module.telemetry"
  defaultConfig {
    minSdk = libs.versions.androidMinSdkVersion.get().toInt()
    targetSdk = libs.versions.androidTargetSdkVersion.get().toInt()
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
}

mapboxLibrary {
  publish {
    group = "com.mapbox.module"
    artifactId = "maps-telemetry"
    artifactTitle = "Telemetry for the Mapbox Maps SDK"
    artifactDescription = artifactTitle
    sdkName = "mobile-maps-android-telemetry"
  }
}

dependencies {
  implementation(project(":sdk-base"))
  implementation(libs.mapbox.base)
  compileOnly(libs.mapbox.annotations)
  kapt(libs.mapbox.annotationsProcessor)
  implementation(libs.kotlin)

  testImplementation(libs.bundles.base.dependenciesTests)
  androidTestImplementation(libs.bundles.base.dependenciesAndroidTests)
  androidTestImplementation(libs.androidx.testJUnit)
  detektPlugins(libs.detektFormatting)
}

project.apply {
  from("$rootDir/gradle/ktlint.gradle")
  from("$rootDir/gradle/lint.gradle")
  from("$rootDir/gradle/track-public-apis.gradle")
  from("$rootDir/gradle/detekt.gradle")
  from("$rootDir/gradle/dependency-updates.gradle")
}

plugins {
  id("com.mapbox.gradle.library")
}

android {
  compileSdk = libs.versions.androidCompileSdkVersion.get().toInt()
  defaultConfig {
    minSdk = libs.versions.androidMinSdkVersion.get().toInt()
    targetSdk = libs.versions.androidTargetSdkVersion.get().toInt()
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
}

dependencies {
  implementation(project(":sdk-base"))
  implementation(libs.mapbox.javaGeoJSON)
  implementation(libs.bundles.base.dependencies)

  testImplementation(libs.bundles.base.dependenciesTests)
  androidTestImplementation(libs.bundles.base.dependenciesAndroidTests)
  detektPlugins(libs.detektFormatting)
  lintPublish(project(":extension-style-lint-rules"))
}

project.apply {
  from("$rootDir/gradle/ktlint.gradle")
  from("$rootDir/gradle/lint.gradle")
  from("${rootDir}/gradle/jacoco.gradle")
  from("$rootDir/gradle/sdk-registry.gradle")
  from("$rootDir/gradle/track-public-apis.gradle")
  from("$rootDir/gradle/detekt.gradle")
  from("$rootDir/gradle/dependency-updates.gradle")
}

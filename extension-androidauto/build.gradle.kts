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

mapboxLibrary{
  jApiCmp{
    // The first release for extension-androidauto was 11.1.0 so we force it if the current one is "11.1.0-SNAPSHOT"
    if (project.hasProperty("VERSION_NAME") && project.property("VERSION_NAME") == "11.1.0-SNAPSHOT") {
      previousVersion = "11.1.0"
    }
  }
}

dependencies {
  api(project(":sdk"))
  api(libs.googleCarAppLibrary)
  implementation(libs.kotlin)
  implementation(libs.androidx.coreKtx)
  implementation(libs.androidx.annotations)

  testImplementation(libs.bundles.base.dependenciesTests)
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
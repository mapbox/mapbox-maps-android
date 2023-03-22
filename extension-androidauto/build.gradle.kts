plugins {
  id("com.mapbox.gradle.library")
}

android {
  compileSdk = AndroidVersions.AndroidAuto.compileSdkVersion
  defaultConfig {
    minSdk = AndroidVersions.AndroidAuto.minSdkVersion
    targetSdk = AndroidVersions.AndroidAuto.targetSdkVersion
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  testOptions {
    unitTests.apply {
      isIncludeAndroidResources = true
    }
  }
}

dependencies {
  api(Dependencies.androidAutoMapboxMapSdk)
  testImplementation(Dependencies.androidAutoMapboxMapSdk)

  api(Dependencies.googleCarAppLibrary)
  implementation(Dependencies.kotlin)
  implementation(Dependencies.androidxCoreKtx)
  implementation(Dependencies.androidxAnnotations)

  testImplementation(Dependencies.junit)
  testImplementation(Dependencies.mockk)
  testImplementation(Dependencies.androidxTestCore)
  testImplementation(Dependencies.robolectric)
  androidTestImplementation(Dependencies.androidxTestRunner)
  androidTestImplementation(Dependencies.androidxJUnitTestRules)
  androidTestImplementation(Dependencies.androidxEspresso)
  detektPlugins(Dependencies.detektFormatting)
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
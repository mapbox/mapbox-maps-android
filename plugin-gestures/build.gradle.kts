plugins {
  id("com.mapbox.gradle.library")
}

android {
  compileSdk = AndroidVersions.compileSdkVersion
  defaultConfig {
    minSdk = AndroidVersions.minSdkVersion
    targetSdk = AndroidVersions.targetSdkVersion
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

dependencies {
  api(Dependencies.mapboxGestures)
  implementation(project(":sdk-base"))
  implementation(Dependencies.mapboxBase)
  implementation(Dependencies.kotlin)
  implementation(Dependencies.androidxAppCompat)
  implementation(Dependencies.androidxCoreKtx)
  implementation(Dependencies.androidxAnnotations)
  testImplementation(Dependencies.junit)
  testImplementation(Dependencies.mockk)
  testImplementation(Dependencies.androidxTestCore)
  testImplementation(Dependencies.robolectric)
  testImplementation(project(":plugin-animation"))
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
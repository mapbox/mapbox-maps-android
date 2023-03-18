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
    unitTests.isReturnDefaultValues = true
  }
}

dependencies {
  implementation(project(":sdk-base"))
  implementation(Dependencies.mapboxBase)
  implementation(Dependencies.kotlin)
  implementation(Dependencies.androidxCoreKtx)
  implementation(Dependencies.androidxLifecycle)
  testImplementation(Dependencies.coroutinesTest)
  testImplementation(Dependencies.junit)
  testImplementation(Dependencies.mockk)
  testImplementation(Dependencies.androidxTestCore)
  testImplementation(Dependencies.robolectric)
  testImplementation(Dependencies.androidxArchCoreTest)
  testImplementation(Dependencies.androidxLifecycleRuntimeTesting)
  androidTestImplementation(Dependencies.androidxTestRunner)
  androidTestImplementation(Dependencies.androidxJUnitTestRules)
  androidTestImplementation(Dependencies.androidxEspresso)
  lintPublish(project(":mapbox-lint"))
  detektPlugins(Dependencies.detektFormatting)
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
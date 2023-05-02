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
  testOptions {
    unitTests.isReturnDefaultValues = true
  }
}

dependencies {
  implementation(project(":sdk-base"))
  implementation(libs.mapbox.base)
  implementation(libs.kotlin)
  implementation(libs.androidx.coreKtx)
  implementation(libs.androidx.lifecycle)

  testImplementation(libs.coroutinesTest)
  testImplementation(libs.bundles.base.dependenciesTests)
  testImplementation(libs.androidx.archCoreTest)
  testImplementation(libs.androidx.lifecycleRuntimeTesting)

  androidTestImplementation(libs.bundles.base.dependenciesAndroidTests)
  lintPublish(project(":plugin-lifecycle-lint-rules"))
  detektPlugins(libs.detektFormatting)
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
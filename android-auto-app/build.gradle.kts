plugins {
  id("com.mapbox.gradle.application")
}

val buildFromSource: String by project

android {
  compileSdk = libs.versions.autoCompileSdkVersion.get().toInt()
  defaultConfig {
    minSdk = libs.versions.autoMinSdkVersion.get().toInt()
    targetSdk = libs.versions.autoTargetSdkVersion.get().toInt()
    applicationId = "com.mapbox.maps.testapp.auto"
    versionCode = 1
    versionName = "0.1.0"
    multiDexEnabled = true
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    testInstrumentationRunnerArguments(mapOf("clearPackageData" to "true"))
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  testOptions {
    if (!project.hasProperty("android.injected.invoked.from.ide")) {
      execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }
  }

  packagingOptions {
    if (buildFromSource.toBoolean()) {
      jniLibs.pickFirsts.add("**/libc++_shared.so")
    }
  }
}

dependencies {
  implementation(project(":extension-androidauto"))
  implementation(libs.googleCarAppLibrary)

  // Please review the compatibility guide. This app is showcasing the latest features with latest Maps SDK main branch.
  // https://github.com/mapbox/mapbox-maps-android/tree/main/extension-androidauto#compatibility-with-maps-sdk-v11
  implementation(project(":sdk"))

  implementation(libs.kotlin)
  implementation(libs.androidx.appCompat)
  implementation(libs.androidx.coreKtx)
  implementation(libs.googleMaterialDesign)
  implementation(libs.androidx.constraintLayout)

  // By default, the Maps SDK uses the Android Location Provider to obtain raw location updates.
  // And with Android 11, the raw location updates might suffer from precision issue.
  // The Maps SDK also comes pre-compiled with support for the [Google's Fused Location Provider](https://developers.google.com/location-context/fused-location-provider)
  // if that dependency is available. This means, that if your target devices support Google Play
  // Services, [we recommend adding the Google Play Location Services dependency to your project](https://developers.google.com/android/guides/setup).
  implementation(libs.googlePlayServicesLocation)

  androidTestUtil(libs.androidx.orchestrator)

  androidTestImplementation(libs.bundles.base.dependenciesAndroidTests)
  androidTestImplementation(libs.androidx.jUnitTestRules)
  androidTestImplementation(libs.androidx.testJUnit)
  androidTestImplementation(libs.androidx.uiAutomator)
  testImplementation(libs.junit)
  detektPlugins(libs.detektFormatting)
}

project.apply {
  from("$rootDir/gradle/ktlint.gradle")
  from("$rootDir/gradle/lint.gradle")
  from("$rootDir/gradle/detekt.gradle")
  from("$rootDir/gradle/dependency-updates.gradle")
}
plugins {
  id("com.mapbox.gradle.library")
}

android {
  compileSdk = libs.versions.composeCompileSdkVersion.get().toInt()
  defaultConfig {
    minSdk = libs.versions.composeMinSdkVersion.get().toInt()
    targetSdk = libs.versions.composeTargetSdkVersion.get().toInt()
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  testOptions {
    unitTests.apply {
      isIncludeAndroidResources = true
    }
  }

  composeOptions {
    kotlinCompilerExtensionVersion = libs.versions.compose.get()
  }

  buildFeatures {
    compose = true
  }

  kotlinOptions {
    freeCompilerArgs += "-Xexplicit-api=strict"
  }
}

dependencies {
  dependencies {
    compileOnly(project(":sdk"))
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.material)

    implementation(libs.androidx.coreKtx)
    androidTestUtil(libs.androidx.orchestrator)
    androidTestImplementation(libs.androidx.testRunner)
    androidTestImplementation(libs.androidx.jUnitTestRules)
    androidTestImplementation(libs.androidx.testJUnit)
    androidTestImplementation(libs.androidx.espresso)
    androidTestImplementation(libs.androidx.uiAutomator)
    androidTestImplementation(project(":sdk"))
    androidTestImplementation(libs.compose.uiTest)
    debugImplementation(libs.compose.uiTestManifest)
    testImplementation(libs.junit)
    detektPlugins(libs.detektFormatting)
  }
}

project.apply {
  from("$rootDir/gradle/ktlint.gradle")
  from("$rootDir/gradle/lint.gradle")
  from("$rootDir/gradle/jacoco.gradle")
  from("$rootDir/gradle/sdk-registry.gradle")
//  from("$rootDir/gradle/track-public-apis.gradle")
  from("$rootDir/gradle/detekt.gradle")
  from("$rootDir/gradle/dependency-updates.gradle")
}